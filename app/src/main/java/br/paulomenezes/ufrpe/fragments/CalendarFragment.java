package br.paulomenezes.ufrpe.fragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import br.paulomenezes.ufrpe.R;
import br.paulomenezes.ufrpe.adapters.CalendarEventAdapter;
import br.paulomenezes.ufrpe.listeners.MainTitle;
import br.paulomenezes.ufrpe.models.Course;
import br.paulomenezes.ufrpe.models.Event;
import br.paulomenezes.ufrpe.models.User;
import br.paulomenezes.ufrpe.services.AVAService;
import br.paulomenezes.ufrpe.services.Requests;
import br.paulomenezes.ufrpe.utils.Functions;
import br.paulomenezes.ufrpe.utils.MultipleDotSpan;
import br.paulomenezes.ufrpe.utils.Session;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by paulo on 27/10/2016.
 */

public class CalendarFragment extends Fragment {

    private br.paulomenezes.ufrpe.models.Calendar mCalendar;
    private List<Course> mSemesterCourses = new ArrayList<>();
    private HashMap<CalendarDay, List<Event>> mEvents = new HashMap<>();
    private ProgressDialog mLoading;
    private MaterialCalendarView mCalendarView;
    private RecyclerView mRecyclerView;
    private TextView mMessage;

    private static MainTitle mMainTitle;

    public void setMainTitle(MainTitle mainTitle) {
        mMainTitle = mainTitle;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mMessage = (TextView) view.findViewById(R.id.message);

        mCalendarView = (MaterialCalendarView) view.findViewById(R.id.calendarView);
        mCalendarView.setTopbarVisible(false);
        mCalendarView.setSelectedDate(Calendar.getInstance());
        mCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                if (mCalendar != null) {
                    if (mEvents.containsKey(date)) {
                        mMessage.setVisibility(View.GONE);

                        CalendarEventAdapter calendarEventAdapter = new CalendarEventAdapter(mSemesterCourses, mEvents.get(date));
                        mRecyclerView.setAdapter(calendarEventAdapter);
                        mRecyclerView.setVisibility(View.VISIBLE);
                    } else {
                        mMessage.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.GONE);
                    }
                }
            }
        });

        mCalendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                mMainTitle.updateTitle(String.format(Locale.ENGLISH, "%s, %d",
                        getResources().getStringArray(R.array.month)[date.getMonth()], date.getYear()));
            }
        });

        mMainTitle.updateTitle(String.format(Locale.ENGLISH, "%s, %d",
                getResources().getStringArray(R.array.month)[Calendar.getInstance().get(Calendar.MONTH)], Calendar.getInstance().get(Calendar.YEAR)));

        User user = Session.getUser(getActivity());

        List<Course> courses = user.getCourses();
        for (int i = 0; i < courses.size(); i++) {
            if (Functions.thisSemester(user, courses.get(i).getShortname())) {
                mSemesterCourses.add(courses.get(i));
            }
        }

        return view;
    }

    private void loadEvents() {
        HashMap<CalendarDay, List<String>> dates = new HashMap<>();

        for (int i = 0; i < mCalendar.getEvents().size(); i++) {
            final Date date = new Date((long) mCalendar.getEvents().get(i).getTimestart() * 1000);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            CalendarDay withoutTime = CalendarDay.from(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            List<String> colors = new ArrayList<String>();
            List<Event> events = new ArrayList<Event>();

            if (dates.containsKey(withoutTime)) {
                colors = dates.get(withoutTime);
                events = mEvents.get(withoutTime);
            }

            colors.add(getColor(mCalendar.getEvents().get(i).getCourseid()));
            events.add(mCalendar.getEvents().get(i));

            dates.put(withoutTime, colors);
            mEvents.put(withoutTime, events);
        }

        if (mEvents.containsKey(mCalendarView.getSelectedDate())) {
            CalendarEventAdapter calendarEventAdapter = new CalendarEventAdapter(mSemesterCourses, mEvents.get(mCalendarView.getSelectedDate()));
            mRecyclerView.setAdapter(calendarEventAdapter);
        } else {
            mMessage.setVisibility(View.VISIBLE);
        }

        for (final Map.Entry<CalendarDay, List<String>> entry : dates.entrySet()) {
            mCalendarView.addDecorator(new DayViewDecorator() {
                @Override
                public boolean shouldDecorate(CalendarDay day) {
                    return day.equals(entry.getKey());
                }

                @Override
                public void decorate(DayViewFacade view) {
                    int[] colors = new int[entry.getValue().size()];
                    for (int i = 0; i < colors.length; i++) {
                        colors[i] = entry.getValue().get(i) != null ? Color.parseColor(entry.getValue().get(i)) : Color.BLACK;
                    }
                    view.addSpan(new MultipleDotSpan(5, colors));
                }
            });
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null && mCalendarView != null) {
            CalendarDay day = savedInstanceState.getParcelable("date");
            mCalendarView.setSelectedDate(day);

            br.paulomenezes.ufrpe.models.Calendar calendar = Parcels.unwrap(savedInstanceState.getParcelable("events"));
            if (calendar != null) {
                mCalendar = calendar;
                loadEvents();
            }
        } else {
            mLoading = ProgressDialog.show(getContext(), null, getString(R.string.loading), true);

            AVAService avaServices = Requests.getInstance().getAVAService();

            int[] events = new int[mSemesterCourses.size()];

            for (int i = 0; i < mSemesterCourses.size(); i++) {
                events[i] = mSemesterCourses.get(i).getId();
            }

            Call<br.paulomenezes.ufrpe.models.Calendar> calendarCall = avaServices.getCalendarEvents(1, 1, 0, 1480441941l,
                    events, Requests.FUNCTION_GET_CALENDAR_EVENTS, Session.getUser(getActivity()).getToken());

            calendarCall.enqueue(new retrofit2.Callback<br.paulomenezes.ufrpe.models.Calendar>() {
                @Override
                public void onResponse(Call<br.paulomenezes.ufrpe.models.Calendar> call, Response<br.paulomenezes.ufrpe.models.Calendar> response) {
                    mCalendar = response.body();

                    loadEvents();

                    mLoading.dismiss();
                }

                @Override
                public void onFailure(Call<br.paulomenezes.ufrpe.models.Calendar> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mCalendarView != null) {
            outState.putParcelable("date", mCalendarView.getSelectedDate());
            outState.putParcelable("events", Parcels.wrap(mCalendar));
        }
    }

    private String getColor(int courseId) {
        for (int i = 0; i < mSemesterCourses.size(); i++) {
            if (courseId == mSemesterCourses.get(i).getId()) {
                return mSemesterCourses.get(i).getNormalColor();
            }
        }

        return null;
    }
}
