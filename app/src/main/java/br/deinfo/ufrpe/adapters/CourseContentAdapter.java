package br.deinfo.ufrpe.adapters;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.deinfo.ufrpe.BR;
import br.deinfo.ufrpe.ModuleActivity;
import br.deinfo.ufrpe.R;
import br.deinfo.ufrpe.models.Course;
import br.deinfo.ufrpe.models.CourseContent;
import br.deinfo.ufrpe.models.Function;
import br.deinfo.ufrpe.utils.Functions;
import br.deinfo.ufrpe.utils.Session;
import br.deinfo.ufrpe.utils.ULTagHandler;

/**
 * Created by phgm on 19/10/2016.
 */

public class CourseContentAdapter extends RecyclerView.Adapter<CourseContentAdapter.CourseContentViewHolder> {

    private Context mContext;
    private Course mCourse;
    private List<CourseContent> mCourseContent;

    private Map<String, String> mMimeTypes = new HashMap<>();

    public CourseContentAdapter(Context context, List<CourseContent> courseContentList, Course course) {
        this.mContext = context;
        this.mCourse = course;

        List<CourseContent> contentWithModules = new ArrayList<>();

        for (int i = 0; i < courseContentList.size(); i++) {
            if (courseContentList.get(i).getModules().length > 0)
                contentWithModules.add(courseContentList.get(i));
        }

        this.mCourseContent = contentWithModules;

        loadMimeTypes();
    }

    @Override
    public CourseContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_course_content, parent, false);

        return new CourseContentAdapter.CourseContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CourseContentViewHolder holder, int position) {
        final CourseContent courseContent = mCourseContent.get(position);

        if (mCourseContent.size() == 1)
            openModule(holder, courseContent);

        closeModule(holder);
        holder.mContentTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean visible = (Boolean) holder.mContentModules.getTag();
                if (visible) {
                    closeModule(holder);
                } else {
                    openModule(holder, courseContent);
                }
            }
        });


        holder.getBinding().setVariable(BR.content, courseContent);
        holder.getBinding().executePendingBindings();
    }

    private void closeModule(final CourseContentViewHolder holder) {
        holder.mContentModules.removeAllViews();

        holder.mContentModules.setTag(false);
        holder.mContentModules.setVisibility(View.GONE);
        holder.mArrow.setImageDrawable(mContext.getDrawable(R.drawable.ic_keyboard_arrow_down_grey_24dp));
    }

    private void openModule(final CourseContentViewHolder holder, final CourseContent courseContent) {
        if (courseContent.getSummary() != null && courseContent.getSummary().length() > 0) {
            TextView textView = new TextView(mContext);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(Functions.dpToPx(mContext, 16),
                    Functions.dpToPx(mContext, 8),
                    Functions.dpToPx(mContext, 16),
                    Functions.dpToPx(mContext, 8));

            textView.setLayoutParams(params);
            textView.setText(Html.fromHtml(courseContent.getSummary(), null, new ULTagHandler()));
            textView.setMovementMethod(LinkMovementMethod.getInstance());

            holder.mContentModules.addView(textView);
        }

        for (int i = 0; i < courseContent.getModules().length; i++) {
            View module = holder.mView.inflate(mContext, R.layout.list_item_course_content_module, null);

            LinearLayout icon = (LinearLayout) module.findViewById(R.id.icon);
            String[] nameSplit = courseContent.getModules()[i].getContents() != null ? courseContent.getModules()[i].getContents()[0].getFilename().split("\\.") : null;
            String format = nameSplit != null ? nameSplit[nameSplit.length - 1] : null;

            if ((format != null && mMimeTypes.containsKey(format)) &&
                    (courseContent.getModules()[i].getModname().equals("file") ||
                    courseContent.getModules()[i].getModname().equals("resource"))) {

                ImageView imageView = new ImageView(mContext);
                imageView.setImageResource(mContext.getResources().getIdentifier(mMimeTypes.get(format), "drawable", mContext.getPackageName()));
                icon.addView(imageView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            } else {
                SVGImageView imageView = new SVGImageView(mContext);
                imageView.setImageAsset(String.format("%s.svg", courseContent.getModules()[i].getModname()));
                icon.addView(imageView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }

            TextView name = (TextView) module.findViewById(R.id.name);
            name.setText(courseContent.getModules()[i].getName());

            final int k = i;

            module.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (courseContent.getModules()[k].getModname().equals("resource")) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse(courseContent.getModules()[k].getContents()[0].getFileurl() + "&token=" + Session.getUser(mContext).getToken()));
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

    @Override
    public int getItemCount() {
        return mCourseContent.size();
    }

    private void loadMimeTypes() {
        mMimeTypes.put("xxx", "unknown");
        mMimeTypes.put("3gp", "quicktime");
        mMimeTypes.put("aac", "audio");
        mMimeTypes.put("accdb", "base");
        mMimeTypes.put("ai", "eps");
        mMimeTypes.put("aif", "audio");
        mMimeTypes.put("aiff", "audio");
        mMimeTypes.put("aifc", "audio");
        mMimeTypes.put("applescript", "text");
        mMimeTypes.put("asc", "sourcecode");
        mMimeTypes.put("asm", "sourcecode");
        mMimeTypes.put("au", "audio");
        mMimeTypes.put("avi", "avi");
        mMimeTypes.put("bmp", "bmp");
        mMimeTypes.put("c", "sourcecode");
        mMimeTypes.put("cct", "flash");
        mMimeTypes.put("cpp", "sourcecode");
        mMimeTypes.put("cs", "sourcecode");
        mMimeTypes.put("css", "text");
        mMimeTypes.put("csv", "spreadsheet");
        mMimeTypes.put("dv", "quicktime");
        mMimeTypes.put("dmg", "unknown");
        mMimeTypes.put("doc", "document");
        mMimeTypes.put("docx", "document");
        mMimeTypes.put("docm", "document");
        mMimeTypes.put("dotx", "document");
        mMimeTypes.put("dotm", "document");
        mMimeTypes.put("dcr", "flash");
        mMimeTypes.put("dif", "quicktime");
        mMimeTypes.put("dir", "flash");
        mMimeTypes.put("dxr", "flash");
        mMimeTypes.put("eps", "eps");
        mMimeTypes.put("epub", "epub");
        mMimeTypes.put("fdf", "pdf");
        mMimeTypes.put("flv", "flash");
        mMimeTypes.put("f4v", "flash");
        mMimeTypes.put("gif", "gif");
        mMimeTypes.put("gtar", "archive");
        mMimeTypes.put("tgz", "archive");
        mMimeTypes.put("gz", "archive");
        mMimeTypes.put("gzip", "archive");
        mMimeTypes.put("h", "sourcecode");
        mMimeTypes.put("hpp", "sourcecode");
        mMimeTypes.put("hqx", "archive");
        mMimeTypes.put("htc", "markup");
        mMimeTypes.put("html", "html");
        mMimeTypes.put("xhtml", "html");
        mMimeTypes.put("htm", "html");
        mMimeTypes.put("ico", "image");
        mMimeTypes.put("ics", "text");
        mMimeTypes.put("isf", "isf");
        mMimeTypes.put("ist", "isf");
        mMimeTypes.put("java", "sourcecode");
        mMimeTypes.put("jcb", "markup");
        mMimeTypes.put("jcl", "markup");
        mMimeTypes.put("jcw", "markup");
        mMimeTypes.put("jmt", "markup");
        mMimeTypes.put("jmx", "markup");
        mMimeTypes.put("jpe", "jpeg");
        mMimeTypes.put("jpeg", "jpeg");
        mMimeTypes.put("jpg", "jpeg");
        mMimeTypes.put("jqz", "markup");
        mMimeTypes.put("js", "text");
        mMimeTypes.put("latex", "text");
        mMimeTypes.put("m", "sourcecode");
        mMimeTypes.put("mbz", "moodle");
        mMimeTypes.put("mdb", "base");
        mMimeTypes.put("mov", "quicktime");
        mMimeTypes.put("movie", "quicktime");
        mMimeTypes.put("m3u", "mp3");
        mMimeTypes.put("mp3", "mp3");
        mMimeTypes.put("mp4", "mpeg");
        mMimeTypes.put("m4v", "mpeg");
        mMimeTypes.put("m4a", "mp3");
        mMimeTypes.put("mpeg", "mpeg");
        mMimeTypes.put("mpe", "mpeg");
        mMimeTypes.put("mpg", "mpeg");
        mMimeTypes.put("odt", "writer");
        mMimeTypes.put("ott", "writer");
        mMimeTypes.put("oth", "oth");
        mMimeTypes.put("odm", "writer");
        mMimeTypes.put("odg", "draw");
        mMimeTypes.put("otg", "draw");
        mMimeTypes.put("odp", "impress");
        mMimeTypes.put("otp", "impress");
        mMimeTypes.put("ods", "calc");
        mMimeTypes.put("ots", "calc");
        mMimeTypes.put("odc", "chart");
        mMimeTypes.put("odf", "math");
        mMimeTypes.put("odb", "base");
        mMimeTypes.put("odi", "draw");
        mMimeTypes.put("oga", "audio");
        mMimeTypes.put("ogg", "audio");
        mMimeTypes.put("ogv", "video");
        mMimeTypes.put("pct", "image");
        mMimeTypes.put("pdf", "pdf");
        mMimeTypes.put("php", "sourcecode");
        mMimeTypes.put("pic", "image");
        mMimeTypes.put("pict", "image");
        mMimeTypes.put("png", "png");
        mMimeTypes.put("pps", "powerpoint");
        mMimeTypes.put("ppt", "powerpoint");
        mMimeTypes.put("pptx", "powerpoint");
        mMimeTypes.put("pptm", "powerpoint");
        mMimeTypes.put("potx", "powerpoint");
        mMimeTypes.put("potm", "powerpoint");
        mMimeTypes.put("ppam", "powerpoint");
        mMimeTypes.put("ppsx", "powerpoint");
        mMimeTypes.put("ppsm", "powerpoint");
        mMimeTypes.put("ps", "pdf");
        mMimeTypes.put("qt", "quicktime");
        mMimeTypes.put("ra", "audio");
        mMimeTypes.put("ram", "audio");
        mMimeTypes.put("rhb", "markup");
        mMimeTypes.put("rm", "audio");
        mMimeTypes.put("rmvb", "video");
        mMimeTypes.put("rtf", "text");
        mMimeTypes.put("rtx", "text");
        mMimeTypes.put("rv", "audio");
        mMimeTypes.put("sh", "sourcecode");
        mMimeTypes.put("sit", "archive");
        mMimeTypes.put("smi", "text");
        mMimeTypes.put("smil", "text");
        mMimeTypes.put("sqt", "markup");
        mMimeTypes.put("svg", "image");
        mMimeTypes.put("svgz", "image");
        mMimeTypes.put("swa", "flash");
        mMimeTypes.put("swf", "flash");
        mMimeTypes.put("swfl", "flash");
        mMimeTypes.put("sxw", "writer");
        mMimeTypes.put("stw", "writer");
        mMimeTypes.put("sxc", "calc");
        mMimeTypes.put("stc", "calc");
        mMimeTypes.put("sxd", "draw");
        mMimeTypes.put("std", "draw");
        mMimeTypes.put("sxi", "impress");
        mMimeTypes.put("sti", "impress");
        mMimeTypes.put("sxg", "writer");
        mMimeTypes.put("sxm", "math");
        mMimeTypes.put("tar", "archive");
        mMimeTypes.put("tif", "tiff");
        mMimeTypes.put("tiff", "tiff");
        mMimeTypes.put("tex", "text");
        mMimeTypes.put("texi", "text");
        mMimeTypes.put("texinfo", "text");
        mMimeTypes.put("tsv", "text");
        mMimeTypes.put("txt", "text");
        mMimeTypes.put("wav", "wav");
        mMimeTypes.put("webm", "video");
        mMimeTypes.put("wmv", "wmv");
        mMimeTypes.put("asf", "wmv");
        mMimeTypes.put("xdp", "pdf");
        mMimeTypes.put("xfd", "pdf");
        mMimeTypes.put("xfdf", "pdf");
        mMimeTypes.put("xls", "spreadsheet");
        mMimeTypes.put("xlsx", "spreadsheet");
        mMimeTypes.put("xlsm", "spreadsheet");
        mMimeTypes.put("xltx", "spreadsheet");
        mMimeTypes.put("xltm", "spreadsheet");
        mMimeTypes.put("xlsb", "spreadsheet");
        mMimeTypes.put("xlam", "spreadsheet");
        mMimeTypes.put("xml", "markup");
        mMimeTypes.put("xsl", "markup");
        mMimeTypes.put("zip", "archive");
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
