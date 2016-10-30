package br.deinfo.ufrpe.fragments;

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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import br.deinfo.ufrpe.R;
import br.deinfo.ufrpe.adapters.CalendarEventAdapter;
import br.deinfo.ufrpe.listeners.MainTitle;
import br.deinfo.ufrpe.models.Course;
import br.deinfo.ufrpe.models.Event;
import br.deinfo.ufrpe.services.AVAService;
import br.deinfo.ufrpe.services.Requests;
import br.deinfo.ufrpe.utils.Functions;
import br.deinfo.ufrpe.utils.MultipleDotSpan;
import br.deinfo.ufrpe.utils.Session;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by paulo on 27/10/2016.
 */

public class CalendarFragment extends Fragment {

    private br.deinfo.ufrpe.models.Calendar mCalendar;
    private List<Course> mSemesterCourses = new ArrayList<>();
    private HashMap<CalendarDay, List<Event>> mEvents = new HashMap<>();

    private MainTitle mMainTitle;

    public void setMainTitle(MainTitle mainTitle) {
        mMainTitle = mainTitle;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        AVAService avaServices = Requests.getInstance().getAVAService();

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        final TextView mMessage = (TextView) view.findViewById(R.id.message);

        final MaterialCalendarView calendarView = (MaterialCalendarView) view.findViewById(R.id.calendarView);
        calendarView.setTopbarVisible(false);
        calendarView.setSelectedDate(Calendar.getInstance());
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                if (mCalendar != null) {
                    if (mEvents.containsKey(date)) {
                        mMessage.setVisibility(View.GONE);

                        CalendarEventAdapter calendarEventAdapter = new CalendarEventAdapter(mSemesterCourses, mEvents.get(date));
                        recyclerView.setAdapter(calendarEventAdapter);
                        recyclerView.setVisibility(View.VISIBLE);
                    } else {
                        mMessage.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                }
            }
        });

        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                mMainTitle.updateTitle(String.format(Locale.ENGLISH, "%s, %d",
                        getResources().getStringArray(R.array.month)[date.getMonth()], date.getYear()));
            }
        });

        mMainTitle.updateTitle(String.format(Locale.ENGLISH, "%s, %d",
                getResources().getStringArray(R.array.month)[Calendar.getInstance().get(Calendar.MONTH)], Calendar.getInstance().get(Calendar.YEAR)));

        List<Course> courses = Session.getUser().getCourses();
        for (int i = 0; i < courses.size(); i++) {
            if (Functions.thisSemester(courses.get(i).getShortname())) {
                mSemesterCourses.add(courses.get(i));
            }
        }

        int[] events = new int[mSemesterCourses.size()];

        for (int i = 0; i < mSemesterCourses.size(); i++) {
            events[i] = mSemesterCourses.get(i).getId();
        }

        Call<br.deinfo.ufrpe.models.Calendar> calendarCall = avaServices.getCalendarEvents(1, 1, 0, 1480441941l,
                events, Requests.FUNCTION_GET_CALENDAR_EVENTS, Session.getUser().getToken());

        calendarCall.enqueue(new retrofit2.Callback<br.deinfo.ufrpe.models.Calendar>() {
            @Override
            public void onResponse(Call<br.deinfo.ufrpe.models.Calendar> call, Response<br.deinfo.ufrpe.models.Calendar> response) {
                mCalendar = response.body();

                HashMap<CalendarDay, List<String>> dates = new HashMap<>();

                for (int i = 0; i < mCalendar.getEvents().size(); i++) {
                    final Date date = new Date((long)mCalendar.getEvents().get(i).getTimestart() * 1000);
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

                if (mEvents.containsKey(CalendarDay.from(Calendar.getInstance()))) {
                    CalendarEventAdapter calendarEventAdapter = new CalendarEventAdapter(mSemesterCourses, mEvents.get(CalendarDay.from(Calendar.getInstance())));
                    recyclerView.setAdapter(calendarEventAdapter);
                } else {
                    mMessage.setVisibility(View.VISIBLE);
                }

                for (final Map.Entry<CalendarDay, List<String>> entry : dates.entrySet()) {
                    calendarView.addDecorator(new DayViewDecorator() {
                        @Override
                        public boolean shouldDecorate(CalendarDay day) {
                            return day.equals(entry.getKey());
                        }

                        @Override
                        public void decorate(DayViewFacade view) {
                            int[] colors = new int[entry.getValue().size()];
                            for (int i = 0; i < colors.length; i++) {
                                colors[i] = Color.parseColor(entry.getValue().get(i));
                            }
                            view.addSpan(new MultipleDotSpan(5, colors));
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<br.deinfo.ufrpe.models.Calendar> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return view;
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
