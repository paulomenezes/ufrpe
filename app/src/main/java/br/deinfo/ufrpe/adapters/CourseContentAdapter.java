package br.deinfo.ufrpe.adapters;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.deinfo.ufrpe.BR;
import br.deinfo.ufrpe.R;
import br.deinfo.ufrpe.models.CourseContent;

/**
 * Created by phgm on 19/10/2016.
 */

public class CourseContentAdapter extends RecyclerView.Adapter<CourseContentAdapter.CourseContentViewHolder> {

    private List<CourseContent> mCourseContent;

    public CourseContentAdapter(List<CourseContent> courseContentList) {
        this.mCourseContent = courseContentList;
    }

    @Override
    public CourseContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_course_content, parent, false);

        return new CourseContentAdapter.CourseContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CourseContentViewHolder holder, int position) {
        CourseContent courseContent = mCourseContent.get(position);

        holder.getBinding().setVariable(BR.content, courseContent);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mCourseContent.size();
    }

    class CourseContentViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding mBinding;

        CourseContentViewHolder(View itemView) {
            super(itemView);

            mBinding = DataBindingUtil.bind(itemView);
        }

        ViewDataBinding getBinding() {
            return mBinding;
        }
    }
}
