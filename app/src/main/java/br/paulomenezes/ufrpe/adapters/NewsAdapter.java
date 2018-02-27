package br.paulomenezes.ufrpe.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import br.paulomenezes.ufrpe.BookDetailActivity;
import br.paulomenezes.ufrpe.NewsPageActivity;
import br.paulomenezes.ufrpe.R;
import br.paulomenezes.ufrpe.models.Book;
import br.paulomenezes.ufrpe.models.NewsContentDTO;
import br.paulomenezes.ufrpe.models.NewsDTO;

/**
 * Created by paulo on 15/01/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<NewsContentDTO> mNews;
    private Context mContext;

    public NewsAdapter(Activity activity, List<NewsContentDTO> news) {
        mNews = news;
        mContext = activity;
    }

    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_book, parent, false);

        return new NewsAdapter.NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NewsAdapter.NewsViewHolder holder, int position) {
        final NewsContentDTO news = mNews.get(position);

        if (news.getSmallImageUrl() != null && news.getSmallImageUrl().length() > 0) {
            holder.mImageView.setVisibility(View.VISIBLE);

            try {
                Picasso.with(mContext).setLoggingEnabled(true);

                String[] parts = news.getSmallImageUrl().split("/");
                String url = "";
                for (int i = 0 ; i < parts.length; i++) {
                    if (i == 0 ) {
                        url += parts[i] + "/";
                    } else {
                        url += URLEncoder.encode(parts[i], StandardCharsets.UTF_8.toString()) + "/";
                    }
                }

                url = url.substring(0, url.length() - 1);

                Picasso.with(mContext)
                        .load(url)
                        .into(holder.mImageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //holder.mImageView.setVisibility(View.GONE);
        }

        holder.mTitle.setText(news.getTitle());
        holder.mAuthor.setText(news.getAuthorName());

        holder.mContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, NewsPageActivity.class);
                intent.putExtra("news", Parcels.wrap(news));
                ((Activity) mContext).startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout mContainer;
        private ImageView mImageView;
        private TextView mTitle, mAuthor;

        NewsViewHolder(View itemView) {
            super(itemView);

            mContainer = (LinearLayout) itemView.findViewById(R.id.container);
            mImageView = (ImageView) itemView.findViewById(R.id.image);
            mTitle = (TextView) itemView.findViewById(R.id.title);
            mAuthor = (TextView) itemView.findViewById(R.id.author);
        }
    }
}

