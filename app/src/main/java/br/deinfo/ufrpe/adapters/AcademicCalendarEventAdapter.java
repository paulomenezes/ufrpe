package br.deinfo.ufrpe.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import org.parceler.Parcels;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import br.deinfo.ufrpe.ModuleActivity;
import br.deinfo.ufrpe.R;
import br.deinfo.ufrpe.models.Course;
import br.deinfo.ufrpe.models.Event;
import br.deinfo.ufrpe.models.Modules;

/**
 * Created by paulo on 10/03/2017.
 */

public class AcademicCalendarEventAdapter extends RecyclerView.Adapter<AcademicCalendarEventAdapter.AcademicCalendarEventViewHolder> {

    private List<String> mEvents;
    private Context mContext;
    private CalendarDay mDate;

    public AcademicCalendarEventAdapter(CalendarDay date, List<String> events) {
        mEvents = events;
        mDate = date;
    }

    @Override
    public AcademicCalendarEventAdapter.AcademicCalendarEventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_calendar_event, parent, false);

        if (mContext == null)
            mContext = parent.getContext();

        return new AcademicCalendarEventAdapter.AcademicCalendarEventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AcademicCalendarEventAdapter.AcademicCalendarEventViewHolder holder, int position) {
        final String event = mEvents.get(position);

        holder.mName.setText(event);
        holder.mColor.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));

        Date date = mDate.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        holder.mDate.setText(sdf.format(date));

        holder.itemView.post(new TimerTask() {
            @Override
            public void run() {
                int h = holder.itemView.getHeight();

                ViewGroup.LayoutParams params = holder.mColor.getLayoutParams();
                params.height = h;
                holder.mColor.setLayoutParams(params);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mEvents == null ? 0 : mEvents.size();
    }

    class AcademicCalendarEventViewHolder extends RecyclerView.ViewHolder {
        private TextView mName;
        private TextView mSubject;
        private TextView mDate;
        private View mColor;

        AcademicCalendarEventViewHolder(View itemView) {
            super(itemView);

            mColor = itemView.findViewById(R.id.color);
            mName = (TextView) itemView.findViewById(R.id.name);
            mSubject = (TextView) itemView.findViewById(R.id.subject);
            mDate = (TextView) itemView.findViewById(R.id.date);
        }
    }
}


