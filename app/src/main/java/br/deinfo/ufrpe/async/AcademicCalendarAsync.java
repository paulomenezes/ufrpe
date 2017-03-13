package br.deinfo.ufrpe.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.deinfo.ufrpe.R;
import br.deinfo.ufrpe.listeners.AcademicCalendarListener;

/**
 * Created by phgm on 12/03/2017.
 */

public class AcademicCalendarAsync extends AsyncTask<Void, Void, HashMap<CalendarDay, List<String>>> {
    private Context mContext;
    private AcademicCalendarListener mListener;
    private ProgressDialog mLoading;

    public AcademicCalendarAsync(Context context, AcademicCalendarListener listener) {
        mContext = context;
        mListener = listener;

        mLoading = ProgressDialog.show(mContext, null, mContext.getString(R.string.loading), true);
    }

    @Override
    protected HashMap<CalendarDay, List<String>> doInBackground(Void... voids) {
        HashMap<CalendarDay, List<String>> mAcademicEvents = new HashMap<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(mContext.getResources().openRawResource(R.raw.calendar)));

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
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mAcademicEvents;
    }

    @Override
    protected void onPostExecute(HashMap<CalendarDay, List<String>> calendarDayListHashMap) {
        super.onPostExecute(calendarDayListHashMap);

        mLoading.dismiss();
        mListener.loadEvents(calendarDayListHashMap);
    }
}
