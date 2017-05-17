package br.paulomenezes.ufrpe.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import br.paulomenezes.ufrpe.R;
import br.paulomenezes.ufrpe.models.AVAUser;

/**
 * Created by paulo on 02/11/2016.
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {
    private List<AVAUser> mUsers;
    private Context mContext;

    public UsersAdapter(List<AVAUser> users) {
        mUsers = users;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_user, parent, false);

        if (mContext == null)
            mContext = parent.getContext();

        return new UsersAdapter.UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        final AVAUser user = mUsers.get(position);

        holder.mName.setText(user.getFullname());
        holder.mEmail.setText(user.getEmail());

        try {
            Picasso.with(mContext).load(user.getProfileimageurl()).into(holder.mImage);
        } catch (Exception e) {

        }
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        private CircularImageView mImage;
        private TextView mName, mEmail;

        public UserViewHolder(View itemView) {
            super(itemView);

            mImage = (CircularImageView) itemView.findViewById(R.id.image);
            mName = (TextView) itemView.findViewById(R.id.name);
            mEmail = (TextView) itemView.findViewById(R.id.email);
        }
    }
}
