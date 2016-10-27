package br.deinfo.ufrpe.adapters;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.caverock.androidsvg.SVGImageView;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import br.deinfo.ufrpe.BR;
import br.deinfo.ufrpe.ModuleActivity;
import br.deinfo.ufrpe.R;
import br.deinfo.ufrpe.models.Course;
import br.deinfo.ufrpe.models.CourseContent;
import br.deinfo.ufrpe.models.Function;
import br.deinfo.ufrpe.utils.Functions;
import br.deinfo.ufrpe.utils.Session;

/**
 * Created by phgm on 19/10/2016.
 */

public class CourseContentAdapter extends RecyclerView.Adapter<CourseContentAdapter.CourseContentViewHolder> {

    private Context mContext;
    private Course mCourse;
    private List<CourseContent> mCourseContent;

    public CourseContentAdapter(Context context, List<CourseContent> courseContentList, Course course) {
        this.mContext = context;
        this.mCourse = course;

        List<CourseContent> contentWithModules = new ArrayList<>();

        for (int i = 0; i < courseContentList.size(); i++) {
            if (courseContentList.get(i).getModules().length > 0)
                contentWithModules.add(courseContentList.get(i));
        }

        this.mCourseContent = contentWithModules;
    }

    @Override
    public CourseContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_course_content, parent, false);

        return new CourseContentAdapter.CourseContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CourseContentViewHolder holder, int position) {
        final CourseContent courseContent = mCourseContent.get(position);

        holder.mContentTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean visible = (Boolean) holder.mContentModules.getTag();
                if (visible) {
                    holder.mContentModules.removeAllViews();

                    holder.mContentModules.setTag(false);
                    holder.mContentModules.setVisibility(View.GONE);
                    holder.mArrow.setImageDrawable(mContext.getDrawable(R.drawable.ic_keyboard_arrow_down_grey_24dp));
                } else {
                    if (courseContent.getSummary() != null && courseContent.getSummary().length() > 0) {
                        TextView textView = new TextView(mContext);

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.setMargins(Functions.dpToPx(mContext, 16),
                                Functions.dpToPx(mContext, 8),
                                Functions.dpToPx(mContext, 16),
                                Functions.dpToPx(mContext, 8));

                        textView.setLayoutParams(params);
                        textView.setText(Html.fromHtml(courseContent.getSummary()));

                        holder.mContentModules.addView(textView);
                    }

                    for (int i = 0; i < courseContent.getModules().length; i++) {
                        View module = holder.mView.inflate(mContext, R.layout.list_item_course_content_module, null);

                        LinearLayout icon = (LinearLayout) module.findViewById(R.id.icon);
                        SVGImageView imageView = new SVGImageView(mContext);
                        imageView.setImageAsset(String.format("%s.svg", courseContent.getModules()[i].getModname()));
                        icon.addView(imageView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                        TextView name = (TextView) module.findViewById(R.id.name);
                        name.setText(courseContent.getModules()[i].getName());

                        final int k = i;

                        module.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (courseContent.getModules()[k].getModname().equals("resource")) {
                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                                            Uri.parse(courseContent.getModules()[k].getContents()[0].getFileurl() + "&token=" + Session.getUser().getToken()));
                                    mContext.startActivity(browserIntent);
                                } else {
                                    Intent intent = new Intent(mContext, ModuleActivity.class);
                                    intent.putExtra("course", Parcels.wrap(mCourse));
                                    intent.putExtra("module", Parcels.wrap(courseContent.getModules()[k]));
                                    mContext.startActivity(intent);
                                }
                            }
                        });
                        holder.mContentModules.addView(module);
                    }

                    holder.mContentModules.setTag(true);
                    holder.mContentModules.setVisibility(View.VISIBLE);
                    holder.mArrow.setImageDrawable(mContext.getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp));
                }
            }
        });

        holder.getBinding().setVariable(BR.content, courseContent);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mCourseContent.size();
    }

    class CourseContentViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding mBinding;
        private LinearLayout mContentModules;
        private ImageView mArrow;
        private View mView;

        private RelativeLayout mContentTitle;

        CourseContentViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            mBinding = DataBindingUtil.bind(itemView);
            mArrow = (ImageView) itemView.findViewById(R.id.arrow);
            mContentTitle = (RelativeLayout) itemView.findViewById(R.id.contentTitle);

            mContentModules = (LinearLayout) itemView.findViewById(R.id.contentModules);
            mContentModules.setTag(false);
        }

        ViewDataBinding getBinding() {
            return mBinding;
        }
    }
}
