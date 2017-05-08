package br.paulomenezes.ufrpe.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.parceler.Parcels;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import br.paulomenezes.ufrpe.ModuleActivity;
import br.paulomenezes.ufrpe.R;
import br.paulomenezes.ufrpe.models.Course;
import br.paulomenezes.ufrpe.models.Event;
import br.paulomenezes.ufrpe.models.Modules;

/**
 * Created by paulo on 30/10/2016.
 */

public class CalendarEventAdapter extends RecyclerView.Adapter<CalendarEventAdapter.CalendarEventViewHolder> {

    private List<Event> mEvents;
    private List<Course> mCourse;
    private Context mContext;

    public CalendarEventAdapter(List<Course> course, List<Event> events) {
        mEvents = events;
        mCourse = course;
    }

    @Override
    public CalendarEventAdapter.CalendarEventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_calendar_event, parent, false);

        if (mContext == null)
            mContext = parent.getContext();

        return new CalendarEventAdapter.CalendarEventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CalendarEventAdapter.CalendarEventViewHolder holder, int position) {
        final Event event = mEvents.get(position);
        final Course course = getCourse(event.getCourseid());

        holder.mColor.setBackgroundColor(Color.parseColor(course.getNormalColor()));
        holder.mName.setText(event.getName());
        holder.mSubject.setText(course.getClasses().getName());

        Date date = new Date((long)event.getTimestart() * 1000);
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Modules module = new Modules();
                module.setModname(event.getModulename());
                module.setName(event.getName());
                module.setUrl("http://ava.ufrpe.br/mod/assign/view.php?id=" + event.getInstance());
                module.setId(String.valueOf(event.getInstance()));
                module.setDescription(event.getDescription());

                Intent intent = new Intent(mContext, ModuleActivity.class);
                intent.putExtra("module", Parcels.wrap(module));
                intent.putExtra("course", Parcels.wrap(course));
                intent.putExtra("dueDate", event.getTimestart());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mEvents == null ? 0 : mEvents.size();
    }

    private Course getCourse(int courseId) {
        for (int i = 0; i < mCourse.size(); i++) {
            if (courseId == mCourse.get(i).getId()) {
                return mCourse.get(i);
            }
        }

        return null;
    }

    class CalendarEventViewHolder extends RecyclerView.ViewHolder {
        private TextView mName;
        private TextView mSubject;
        private TextView mDate;
        private View mColor;

        CalendarEventViewHolder(View itemView) {
            super(itemView);

            mColor = itemView.findViewById(R.id.color);
            mName = (TextView) itemView.findViewById(R.id.name);
            mSubject = (TextView) itemView.findViewById(R.id.subject);
            mDate = (TextView) itemView.findViewById(R.id.date);
        }
    }
}

