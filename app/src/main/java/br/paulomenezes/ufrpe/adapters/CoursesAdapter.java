package br.paulomenezes.ufrpe.adapters;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.Calendar;
import java.util.List;

import br.paulomenezes.ufrpe.BR;
import br.paulomenezes.ufrpe.CourseActivity;
import br.paulomenezes.ufrpe.R;
import br.paulomenezes.ufrpe.models.Course;

/**
 * Created by phgm on 10/10/2016.
 */

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.CourseViewHolder> {

    private List<Course> mCourses;
    private Context mContext;

    public CoursesAdapter(List<Course> courses) {
        mCourses = courses;
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_course, parent, false);

        if (mContext == null)
            mContext = parent.getContext();

        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CourseViewHolder holder, int position) {
        final Course course = mCourses.get(position);

        if (course.getClasses() != null) {
            Calendar calendar = Calendar.getInstance();

            int today = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            if (today == 0 || today == 6)
                today = 1;

            boolean update = false;

            for (int i = 0; i < course.getClasses().getSchedules().size(); i++) {
                if (course.getClasses().getSchedules().get(i).getDayOfWeek() == today) {
                    holder.mSchedule.setText(String.format("%s - %s",
                            course.getClasses().getSchedules().get(i).getTimeStart(),
                            course.getClasses().getSchedules().get(i).getTimeEnd()));
                    update = true;
                    break;
                }
            }

            if (!update) {
                if (course.getClasses().getSchedules().size() == 0) {
                    holder.mScheduleLayout.setVisibility(View.GONE);
                } else {
                    String[] daysOfWeek = mContext.getResources().getStringArray(R.array.day_of_week);
                    String text = "";

                    for (int i = 0; i < course.getClasses().getSchedules().size(); i++) {
                        text += daysOfWeek[course.getClasses().getSchedules().get(i).getDayOfWeek()] + ", ";
                    }

                    holder.mSchedule.setText(text.substring(0, text.length() - 2));
                }
            }

            if (course.getAssignments() != null && course.getAssignments().length > 0) {
                course.setNextAssignment(course.getAssignments()[0].getName());
            }

            holder.mCard.setVisibility(View.VISIBLE);
            holder.mCard.setCardBackgroundColor(Color.parseColor(course.getNormalColor()));
            holder.mDark.setBackgroundColor(Color.parseColor(course.getDarkColor()));

            holder.getBinding().setVariable(BR.handler, this);
            holder.getBinding().setVariable(BR.course, course);
            holder.getBinding().executePendingBindings();

            holder.mHiddenTitle.setVisibility(View.GONE);
        } else {
            holder.mHiddenTitle.setVisibility(View.VISIBLE);
            holder.mCard.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public void onItemClick(Course course) {
        Intent intent = new Intent(mContext, CourseActivity.class);
        intent.putExtra("course", Parcels.wrap(course));
        mContext.startActivity(intent);
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding mBinding;
        private TextView mSchedule, mHiddenTitle;

        private CardView mCard;
        private LinearLayout mDark, mScheduleLayout;

        CourseViewHolder(View itemView) {
            super(itemView);

            mBinding = DataBindingUtil.bind(itemView);
            mSchedule = (TextView) itemView.findViewById(R.id.schedule);

            mCard = (CardView) itemView.findViewById(R.id.card);
            mDark = (LinearLayout) itemView.findViewById(R.id.dark);

            mHiddenTitle = (TextView) itemView.findViewById(R.id.hiddenTitle);
            mScheduleLayout = (LinearLayout) itemView.findViewById(R.id.scheduleLayout);
        }

        ViewDataBinding getBinding() {
            return mBinding;
        }
    }
}
