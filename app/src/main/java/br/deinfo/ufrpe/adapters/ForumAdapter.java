package br.deinfo.ufrpe.adapters;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.deinfo.ufrpe.BR;
import br.deinfo.ufrpe.CourseActivity;
import br.deinfo.ufrpe.R;
import br.deinfo.ufrpe.models.Course;
import br.deinfo.ufrpe.models.Discussions;
import br.deinfo.ufrpe.utils.Session;

/**
 * Created by phgm on 26/10/2016.
 */

public class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.ForumViewHolder> {

    private List<Discussions> mDiscussions;
    private Context mContext;

    public ForumAdapter(List<Discussions> discussions) {
        mDiscussions = discussions;
    }

    @Override
    public ForumAdapter.ForumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_discussion, parent, false);

        if (mContext == null)
            mContext = parent.getContext();

        return new ForumAdapter.ForumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ForumAdapter.ForumViewHolder holder, int position) {
        final Discussions discussion = mDiscussions.get(position);

        Picasso.with(mContext)
                .load(discussion.getUsermodifiedpictureurl() + "&token=" + Session.getUser().getToken())
                .into(holder.mImageView);

        holder.mMessage.setText(Html.fromHtml(discussion.getMessage()));

        Date date = new Date(discussion.getCreated() * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        holder.mDate.setText(sdf.format(date));

        holder.getBinding().setVariable(BR.discussion, discussion);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mDiscussions.size();
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

