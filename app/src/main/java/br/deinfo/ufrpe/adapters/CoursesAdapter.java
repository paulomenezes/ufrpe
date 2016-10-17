package br.deinfo.ufrpe.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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

    public CoursesAdapter(List<Course> courses, List<Classes> classes) {
        mCourses = new ArrayList<>();

        for (int i = 0; i < courses.size(); i++) {
            if (Functions.thisSemester(courses.get(i).getShortname())) {
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

        //if (course.getNextAssignment() != null)
        //    holder.mAssignmentView.setVisibility(View.VISIBLE);

        holder.getBinding().setVariable(BR.course, course);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public void setAssignments(int id, Assignments[] assignments) {
        for (int i = 0; i < mCourses.size(); i++) {
            if (mCourses.get(i).getId() == id) {
                mCourses.get(i).setAssignments(assignments);

                if (assignments != null && assignments.length > 0) {
                    mCourses.get(i).setNextAssignment(assignments[0].getName());
                }

                break;
            }
        }
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding mBinding;
        //private LinearLayout mAssignmentView;

        CourseViewHolder(View itemView) {
            super(itemView);

            mBinding = DataBindingUtil.bind(itemView);
            //mAssignmentView = (LinearLayout) itemView.findViewById(R.id.assignmentView);
        }

        ViewDataBinding getBinding() {
            return mBinding;
        }
    }
}
