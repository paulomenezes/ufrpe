package br.paulomenezes.ufrpe.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import br.paulomenezes.ufrpe.R;
import br.paulomenezes.ufrpe.models.BookCopies;

/**
 * Created by paulo on 29/01/2017.
 */

public class BookCopiesAdapter extends RecyclerView.Adapter<BookCopiesAdapter.BookCopiesViewHolder> {

    private List<BookCopies> mBookCopies;
    private Context mContext;

    public BookCopiesAdapter(List<BookCopies> copies) {
        mBookCopies = copies;
    }

    @Override
    public BookCopiesAdapter.BookCopiesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_book_copies, parent, false);

        if (mContext == null)
            mContext = parent.getContext();

        return new BookCopiesAdapter.BookCopiesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BookCopiesAdapter.BookCopiesViewHolder holder, int position) {
        final BookCopies bookCopies = mBookCopies.get(position);

        holder.mLibrary.setText(bookCopies.getLibrary());
        holder.mQuantityAvailable.setText(String.valueOf(bookCopies.getAvailable()));
        holder.mLoaned.setText(String.valueOf(bookCopies.getLoaned()));
        holder.mTotal.setText(String.valueOf(bookCopies.getAvailable() + bookCopies.getLoaned()));
        holder.mSeeBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mBookCopies.size();
    }

    class BookCopiesViewHolder extends RecyclerView.ViewHolder {
        private TextView mLibrary, mQuantityAvailable, mLoaned, mTotal;
        private Button mSeeBooks;

        BookCopiesViewHolder(View itemView) {
            super(itemView);

            mLibrary = (TextView) itemView.findViewById(R.id.library);
            mQuantityAvailable = (TextView) itemView.findViewById(R.id.quantityAvailable);
            mLoaned = (TextView) itemView.findViewById(R.id.loaned);
            mTotal = (TextView) itemView.findViewById(R.id.total);

            mSeeBooks = (Button) itemView.findViewById(R.id.seeBooks);
        }
    }
}

