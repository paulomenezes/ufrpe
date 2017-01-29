package br.deinfo.ufrpe.fragments;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.deinfo.ufrpe.BR;
import br.deinfo.ufrpe.R;
import br.deinfo.ufrpe.adapters.BookCopiesAdapter;
import br.deinfo.ufrpe.async.BookCopiesAsync;
import br.deinfo.ufrpe.async.BookDetailAsync;
import br.deinfo.ufrpe.listeners.BookCopiesListener;
import br.deinfo.ufrpe.listeners.BookDetailListener;
import br.deinfo.ufrpe.models.Book;
import br.deinfo.ufrpe.models.BookCopies;
import br.deinfo.ufrpe.models.BookCopy;

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