package br.deinfo.ufrpe.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.deinfo.ufrpe.R;
import br.deinfo.ufrpe.models.Message;
import br.deinfo.ufrpe.utils.Functions;
import br.deinfo.ufrpe.utils.Session;
import br.deinfo.ufrpe.utils.ULTagHandler;

/**
 * Created by paulo on 01/11/2016.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<Message> mMessages = new ArrayList();
    private Context mContext;

    public ChatAdapter(List<Message> messages) {
        mMessages = messages;
    }

    @Override
    public ChatAdapter.ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_chat, parent, false);

        if (mContext == null)
            mContext = parent.getContext();

        return new ChatAdapter.ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatAdapter.ChatViewHolder holder, int position) {
        final Message msg = mMessages.get(position);

        Date date = new Date((long)msg.getTimecreated() * 1000);
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/M/yyyy");
        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");

        holder.mText.setText(Html.fromHtml(msg.getSmallmessage(), null, new ULTagHandler()));
        holder.mTime.setText(formatTime.format(date));
        holder.mDate.setText(formatDate.format(date));

        if (msg.getUseridfrom() == Session.getUser().getAvaID()) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(Functions.dpToPx(mContext, 48), Functions.dpToPx(mContext, 8), Functions.dpToPx(mContext, 16), Functions.dpToPx(mContext, 8));
            holder.mLayout.setLayoutParams(params);
            holder.mLayout.setBackground(mContext.getDrawable(R.drawable.shape_chat_mine));
        } else {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(Functions.dpToPx(mContext, 16), Functions.dpToPx(mContext, 8), Functions.dpToPx(mContext, 48), Functions.dpToPx(mContext, 8));
            holder.mLayout.setLayoutParams(params);
            holder.mLayout.setBackground(mContext.getDrawable(R.drawable.shape_chat));
        }
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {
        private TextView mText, mTime, mDate;
        private RelativeLayout mLayout;

        public ChatViewHolder(View itemView) {
            super(itemView);

            mLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayout);
            mText = (TextView) itemView.findViewById(R.id.text);
            mTime = (TextView) itemView.findViewById(R.id.time);
            mDate = (TextView) itemView.findViewById(R.id.date);
        }
    }
}
