package br.deinfo.ufrpe.fragments;

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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import br.deinfo.ufrpe.R;
import br.deinfo.ufrpe.adapters.AcademicCalendarEventAdapter;
import br.deinfo.ufrpe.listeners.MainTitle;
import br.deinfo.ufrpe.models.Course;
import br.deinfo.ufrpe.services.AVAService;
import br.deinfo.ufrpe.services.Requests;
import br.deinfo.ufrpe.utils.Functions;
import br.deinfo.ufrpe.utils.MultipleDotSpan;
import br.deinfo.ufrpe.utils.Session;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by paulo on 10/03/2017.
 */

public class AcademicCalendarFragment extends Fragment {

    private br.deinfo.ufrpe.models.Calendar mCalendar;
    private List<Course> mSemesterCourses = new ArrayList<>();
    private HashMap<CalendarDay, List<String>> mAcademicEvents = new HashMap<>();
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
                    if (mAcademicEvents.containsKey(date)) {
                        mMessage.setVisibility(View.GONE);

                        AcademicCalendarEventAdapter calendarEventAdapter = new AcademicCalendarEventAdapter(date, mAcademicEvents.get(mCalendarView.getSelectedDate()));
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

        List<Course> courses = Session.getUser(getActivity()).getCourses();
        for (int i = 0; i < courses.size(); i++) {
            if (Functions.thisSemester(courses.get(i).getShortname())) {
                mSemesterCourses.add(courses.get(i));
            }
        }

        return view;
    }

    private void loadEvents() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(getActivity().getResources().openRawResource(R.raw.calendar)));

        // do reading, usually loop until end of file reading
        StringBuilder sb = new StringBuilder();
        String mLine = reader.readLine();

        int month = 1;
        int year = 2017;

        while (mLine != null) {
            String[] line = mLine.split(";");
            mLine = reader.readLine();

            int dayStart = 1;
            int dayEnd = 1;
            boolean period = false;

            if (line.length == 1) {
                String[] date = line[0].split("/");
                month = Integer.parseInt(date[0]) - 1;
                year = Integer.parseInt(date[1]);

                continue;
            } else {
                String[] range = line[0].split("-");
                if (range.length == 1) {
                    period = false;
                    dayStart = Integer.parseInt(range[0]);
                } else {
                    period = true;
                    dayStart = Integer.parseInt(range[0]);
                    dayEnd = Integer.parseInt(range[1]);
                }
            }

            if (!period) {
                CalendarDay calendarDay = CalendarDay.from(year, month, dayStart);

                if (mAcademicEvents.containsKey(calendarDay)) {
                    mAcademicEvents.get(calendarDay).add(line[1]);
                } else {
                    List<String> eventTexts = new ArrayList<>();
                    eventTexts.add(line[1]);

                    mAcademicEvents.put(calendarDay, eventTexts);
                }
            } else {
                for (int day = dayStart; day <= dayEnd; day++) {
                    CalendarDay calendarDay = CalendarDay.from(year, month, day);

                    if (mAcademicEvents.containsKey(calendarDay)) {
                        mAcademicEvents.get(calendarDay).add(line[1]);
                    } else {
                        List<String> eventTexts = new ArrayList<>();
                        eventTexts.add(line[1]);

                        mAcademicEvents.put(calendarDay, eventTexts);
                    }
                }
            }
        }
        reader.close();

        if (mAcademicEvents.containsKey(mCalendarView.getSelectedDate())) {
            AcademicCalendarEventAdapter calendarEventAdapter = new AcademicCalendarEventAdapter(mCalendarView.getSelectedDate(), mAcademicEvents.get(mCalendarView.getSelectedDate()));
            mRecyclerView.setAdapter(calendarEventAdapter);
        } else {
            mMessage.setVisibility(View.VISIBLE);
        }

        for (final Map.Entry<CalendarDay, List<String>> entry : mAcademicEvents.entrySet()) {
            mCalendarView.addDecorator(new DayViewDecorator() {
                @Override
                public boolean shouldDecorate(CalendarDay day) {
                    return day.equals(entry.getKey());
                }

                @Override
                public void decorate(DayViewFacade view) {
                    int[] colors = new int[entry.getValue().size()];
                    for (int i = 0; i < colors.length; i++) {
                        colors[i] = Color.WHITE;
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
            try {
                CalendarDay day = savedInstanceState.getParcelable("date");
                mCalendarView.setSelectedDate(day);

                br.deinfo.ufrpe.models.Calendar calendar = Parcels.unwrap(savedInstanceState.getParcelable("events"));
                if (calendar != null) {
                    mCalendar = calendar;
                    loadEvents();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            mLoading = ProgressDialog.show(getContext(), null, getString(R.string.loading), true);

            AVAService avaServices = Requests.getInstance().getAVAService();

            int[] events = new int[mSemesterCourses.size()];

            for (int i = 0; i < mSemesterCourses.size(); i++) {
                events[i] = mSemesterCourses.get(i).getId();
            }

            Call<br.deinfo.ufrpe.models.Calendar> calendarCall = avaServices.getCalendarEvents(1, 1, 0, 1480441941l,
                    events, Requests.FUNCTION_GET_CALENDAR_EVENTS, Session.getUser(getActivity()).getToken());

            calendarCall.enqueue(new retrofit2.Callback<br.deinfo.ufrpe.models.Calendar>() {
                @Override
                public void onResponse(Call<br.deinfo.ufrpe.models.Calendar> call, Response<br.deinfo.ufrpe.models.Calendar> response) {
                    mCalendar = response.body();

                    try {
                        loadEvents();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    mLoading.dismiss();
                }

                @Override
                public void onFailure(Call<br.deinfo.ufrpe.models.Calendar> call, Throwable t) {
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

