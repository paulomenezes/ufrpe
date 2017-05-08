package br.paulomenezes.ufrpe.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import br.paulomenezes.ufrpe.R;
import br.paulomenezes.ufrpe.models.Course;
import br.paulomenezes.ufrpe.utils.Functions;

/**
 * Created by paulo on 11/03/2017.
 */

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {

    private HashMap<String, List<Course>> mCourses = new HashMap<>();
    private Context mContext;

    public SubjectAdapter(HashMap<String, List<Course>> courses) {
        mCourses = courses;
    }

    @Override
    public SubjectAdapter.SubjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_subject, parent, false);

        if (mContext == null)
            mContext = parent.getContext();

        return new SubjectAdapter.SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SubjectAdapter.SubjectViewHolder holder, int position) {
        final List<Course> msg = mCourses.get(mCourses.keySet().toArray()[position]);

        holder.mYear.setText(msg.get(0).getFullname().split(" \\| ")[0]);
        holder.mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        for (Course course: msg) {
            TextView textView = new TextView(mContext);
            textView.setText(course.getFullname().split(" \\| ")[1]);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(holder.mContent.getLayoutParams());
            layoutParams.setMargins(Functions.dpToPx(mContext, 16), Functions.dpToPx(mContext, 8), Functions.dpToPx(mContext, 16), Functions.dpToPx(mContext, 8));

            textView.setLayoutParams(layoutParams);

            holder.mContent.addView(textView);
        }
    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public class SubjectViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout mTitle;
        private LinearLayout mContent;
        private TextView mYear;

        public SubjectViewHolder(View itemView) {
            super(itemView);

            mTitle = (RelativeLayout) itemView.findViewById(R.id.title);
            mContent = (LinearLayout) itemView.findViewById(R.id.content);
            mYear = (TextView) itemView.findViewById(R.id.year);
        }
    }
}

