package br.paulomenezes.ufrpe.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.paulomenezes.ufrpe.BR;
import br.paulomenezes.ufrpe.R;
import br.paulomenezes.ufrpe.models.Course;
import br.paulomenezes.ufrpe.models.Posts;
import br.paulomenezes.ufrpe.utils.Session;
import br.paulomenezes.ufrpe.utils.ULTagHandler;

/**
 * Created by paulo on 30/10/2016.
 */

public class ForumPostAdapter extends RecyclerView.Adapter<ForumPostAdapter.ForumViewHolder> {

    private List<Posts> mPosts;
    private Course mCourse;
    private Context mContext;

    public ForumPostAdapter(Course course, List<Posts> posts) {
        mPosts = posts;
        mCourse = course;
    }

    @Override
    public ForumPostAdapter.ForumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_discussion_post, parent, false);

        if (mContext == null)
            mContext = parent.getContext();

        return new ForumPostAdapter.ForumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ForumPostAdapter.ForumViewHolder holder, int position) {
        final Posts post = mPosts.get(position);

        try {
            Picasso.with(mContext)
                    .load(post.getUserpictureurl() + "&token=" + Session.getUser(mContext).getToken())
                    .error(R.drawable.noprofile)
                    .into(holder.mImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.mMessage.setText(Html.fromHtml(post.getMessage(), null, new ULTagHandler()));
        holder.mMessage.setMovementMethod(LinkMovementMethod.getInstance());

        Date date = new Date((long)post.getCreated() * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        holder.mDate.setText(sdf.format(date));

        holder.getBinding().setVariable(BR.post, post);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    class ForumViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding mBinding;

        private CircularImageView mImageView;
        private TextView mDate;
        private TextView mMessage;

        ForumViewHolder(View itemView) {
            super(itemView);

            mBinding = DataBindingUtil.bind(itemView);

            mImageView = (CircularImageView) itemView.findViewById(R.id.userpicture);
            mDate = (TextView) itemView.findViewById(R.id.date);
            mMessage = (TextView) itemView.findViewById(R.id.message);
        }

        ViewDataBinding getBinding() {
            return mBinding;
        }
    }
}

