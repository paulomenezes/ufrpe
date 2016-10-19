package br.deinfo.ufrpe.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.deinfo.ufrpe.BR;
import br.deinfo.ufrpe.R;
import br.deinfo.ufrpe.models.Assignments;
import br.deinfo.ufrpe.models.Classes;
import br.deinfo.ufrpe.models.Course;
import br.deinfo.ufrpe.utils.Functions;

/**
 * Created by phgm on 10/10/2016.
 */

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.CourseViewHolder> {

    private List<Course> mCourses;

    public CoursesAdapter(List<Course> courses) {
        mCourses = courses;
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_course, parent, false);

        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CourseViewHolder holder, int position) {
        final Course course = mCourses.get(position);

        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < course.getClasses().getSchedules().size(); i++) {
            if (course.getClasses().getSchedules().get(i).getDayOfWeek() == calendar.get(Calendar.DAY_OF_WEEK) - 1) {
                holder.mSchedule.setText(String.format("%s - %s",
                        course.getClasses().getSchedules().get(i).getTimeStart(),
                        course.getClasses().getSchedules().get(i).getTimeEnd()));
                break;
            }
        }

        if (course.getAssignments()  != null && course.getAssignments().length > 0) {
            course.setNextAssignment(course.getAssignments()[0].getName());
        }

        holder.getBinding().setVariable(BR.handler, this);
        holder.getBinding().setVariable(BR.course, course);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public void onItemClick(Course course) {
        if (course != null) {

        }
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding mBinding;
        private TextView mSchedule;

        CourseViewHolder(View itemView) {
            super(itemView);

            mBinding = DataBindingUtil.bind(itemView);
            mSchedule = (TextView) itemView.findViewById(R.id.schedule);
        }

        ViewDataBinding getBinding() {
            return mBinding;
        }
    }
}