package br.ufrpe.conectada.adapters;

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

import java.util.List;

import br.ufrpe.conectada.BookDetailActivity;
import br.ufrpe.conectada.R;
import br.ufrpe.conectada.models.Book;

/**
 * Created by paulo on 15/01/2017.
 */

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.LibraryViewHolder> {

    private List<Book> mBooks;
    private Context mContext;

    public LibraryAdapter(Activity activity, List<Book> discussions) {
        mBooks = discussions;
        mContext = activity;
    }

    @Override
    public LibraryAdapter.LibraryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_book, parent, false);

        return new LibraryAdapter.LibraryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final LibraryAdapter.LibraryViewHolder holder, int position) {
        final Book book = mBooks.get(position);

        if (book.getImage() != null && book.getImage().length() > 0) {
            holder.mImageView.setVisibility(View.VISIBLE);

            try {
                Picasso.with(mContext)
                        .load(book.getImage())
                        .into(holder.mImageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            holder.mImageView.setVisibility(View.GONE);
        }

        holder.mTitle.setText(book.getTitle());
        holder.mAuthor.setText(book.getAuthor());

        holder.mContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, BookDetailActivity.class);
                intent.putExtra("book", Parcels.wrap(book));
                ((Activity) mContext).startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }

    class LibraryViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout mContainer;
        private ImageView mImageView;
        private TextView mTitle, mAuthor;

        LibraryViewHolder(View itemView) {
            super(itemView);

            mContainer = (LinearLayout) itemView.findViewById(R.id.container);
            mImageView = (ImageView) itemView.findViewById(R.id.image);
            mTitle = (TextView) itemView.findViewById(R.id.title);
            mAuthor = (TextView) itemView.findViewById(R.id.author);
        }
    }
}

