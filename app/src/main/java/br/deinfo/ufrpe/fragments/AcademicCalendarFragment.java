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
import br.deinfo.ufrpe.async.AcademicCalendarAsync;
import br.deinfo.ufrpe.listeners.AcademicCalendarListener;
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

public class AcademicCalendarFragment extends Fragment implements AcademicCalendarListener {

    private HashMap<CalendarDay, List<String>> mAcademicEvents = new HashMap<>();
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
                if (mAcademicEvents != null) {
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

        return view;
    }

    @Override
    public void loadEvents(HashMap<CalendarDay, List<String>> events) {
        mAcademicEvents = events;

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

                HashMap<CalendarDay, List<String>> calendar = Parcels.unwrap(savedInstanceState.getParcelable("events"));
                if (calendar != null) {
                    loadEvents(calendar);
                } else {
                    new AcademicCalendarAsync(getActivity(), this).execute();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            new AcademicCalendarAsync(getActivity(), this).execute();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mCalendarView != null) {
            outState.putParcelable("date", mCalendarView.getSelectedDate());
            outState.putParcelable("events", Parcels.wrap(mAcademicEvents));
        }
    }
}

