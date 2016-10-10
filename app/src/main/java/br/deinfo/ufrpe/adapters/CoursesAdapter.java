package br.deinfo.ufrpe.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.deinfo.ufrpe.BR;
import br.deinfo.ufrpe.R;
import br.deinfo.ufrpe.models.Classes;
import br.deinfo.ufrpe.models.Course;
import br.deinfo.ufrpe.utils.Functions;

/**
 * Created by phgm on 10/10/2016.
 */

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.CourseViewHolder> {

    private List<Course> mCourses;

    public CoursesAdapter(List<Course> courses, List<Classes> classes) {
        mCourses = new ArrayList<>();

        String semester = Calendar.getInstance().get(Calendar.YEAR) + "." + (Calendar.getInstance().get(Calendar.YEAR) > 6 ? 2 : 1);

        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getShortname().contains(semester)) {
                for (int j = 0; j < classes.size(); j++) {
                    if (classes.get(j).getCod().equals(courses.get(i).getShortname().split("-")[1])) {
                        courses.get(i).setClasses(classes.get(j));
                        break;
                    }
                }

                mCourses.add(courses.get(i));
            }
        }
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_course, parent, false);

        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        final Course course = mCourses.get(position);

        holder.getBinding().setVariable(BR.course, course);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding mBinding;

        CourseViewHolder(View itemView) {
            super(itemView);

            mBinding = DataBindingUtil.bind(itemView);
        }

        ViewDataBinding getBinding() {
            return mBinding;
        }
    }
}
