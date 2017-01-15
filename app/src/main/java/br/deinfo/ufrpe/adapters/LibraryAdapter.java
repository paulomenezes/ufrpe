package br.deinfo.ufrpe.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.deinfo.ufrpe.R;
import br.deinfo.ufrpe.models.Book;

/**
 * Created by paulo on 15/01/2017.
 */

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.LibraryViewHolder> {

    private List<Book> mBooks;
    private Context mContext;

    public LibraryAdapter(List<Book> discussions) {
        mBooks = discussions;
    }

    @Override
    public LibraryAdapter.LibraryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_book, parent, false);

        if (mContext == null)
            mContext = parent.getContext();

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
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }

    class LibraryViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;
        private TextView mTitle, mAuthor;

        LibraryViewHolder(View itemView) {
            super(itemView);

            mImageView = (ImageView) itemView.findViewById(R.id.image);
            mTitle = (TextView) itemView.findViewById(R.id.title);
            mAuthor = (TextView) itemView.findViewById(R.id.author);
        }
    }
}

