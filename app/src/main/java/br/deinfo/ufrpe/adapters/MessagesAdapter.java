package br.deinfo.ufrpe.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import br.deinfo.ufrpe.R;
import br.deinfo.ufrpe.models.Message;

/**
 * Created by paulo on 31/10/2016.
 */

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder> {

    private HashMap<Integer, List<Message>> mMessages = new HashMap<>();
    private Context mContext;

    public MessagesAdapter(List<Message> messages) {
        for (int i = 0; i < messages.size(); i++) {
            if (!mMessages.containsKey(messages.get(i).getUseridfrom())) {
                mMessages.put(messages.get(i).getUseridfrom(), new ArrayList<Message>());
            }

            List<Message> msg = mMessages.get(messages.get(i).getUseridfrom());
            msg.add(messages.get(i));

            mMessages.put(messages.get(i).getUseridfrom(), msg);
        }
    }

    @Override
    public MessagesAdapter.MessagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_messages, parent, false);

        if (mContext == null)
            mContext = parent.getContext();

        return new MessagesAdapter.MessagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessagesAdapter.MessagesViewHolder holder, int position) {
        List<Message> msg = mMessages.get(mMessages.keySet().toArray()[position]);

        holder.mName.setText(msg.get(0).getUserfromfullname());
        holder.mMessage.setText(msg.get(0).getSubject());

        Date date = new Date((long)msg.get(0).getTimecreated() * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        holder.mDate.setText(sdf.format(date));
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    public class MessagesViewHolder extends RecyclerView.ViewHolder {
        private CircularImageView mImage;
        private TextView mName, mMessage, mDate;

        public MessagesViewHolder(View itemView) {
            super(itemView);

            mImage = (CircularImageView) itemView.findViewById(R.id.image);
            mName = (TextView) itemView.findViewById(R.id.name);
            mMessage = (TextView) itemView.findViewById(R.id.messages);
            mDate = (TextView) itemView.findViewById(R.id.date);
        }
    }
}
