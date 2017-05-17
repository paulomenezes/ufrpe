package br.paulomenezes.ufrpe.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import br.paulomenezes.ufrpe.R;
import br.paulomenezes.ufrpe.adapters.BookCopiesAdapter;
import br.paulomenezes.ufrpe.async.BookCopiesAsync;
import br.paulomenezes.ufrpe.listeners.BookCopiesListener;
import br.paulomenezes.ufrpe.models.Book;
import br.paulomenezes.ufrpe.models.BookCopies;

/**
 * Created by phgm on 17/01/2017.
 */

public class BookCopiesFragment extends Fragment implements BookCopiesListener {

    private BookCopiesAdapter mBookCopiesAdapter;
    private List<BookCopies> mCopies = new ArrayList<>();

    private RecyclerView mRecycler;

    public BookCopiesFragment() {
    }

    public static BookCopiesFragment newInstance(Book book) {
        BookCopiesFragment fragment = new BookCopiesFragment();
        Bundle args = new Bundle();
        args.putParcelable("book", Parcels.wrap(book));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Book book = Parcels.unwrap(getArguments().getParcelable("book"));

        View view = inflater.inflate(R.layout.fragment_book_copies, container, false);

        mBookCopiesAdapter = new BookCopiesAdapter(mCopies);

        mRecycler = (RecyclerView) view.findViewById(R.id.recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecycler.setAdapter(mBookCopiesAdapter);

        String[] params = new String[1];
        params[0] = String.valueOf(book.getId());

        new BookCopiesAsync(getContext(), this).execute(params);

        return view;
    }

    @Override
    public void copiesBook(List<BookCopies> book) {
        mCopies.addAll(book);
        mBookCopiesAdapter.notifyDataSetChanged();
    }
}