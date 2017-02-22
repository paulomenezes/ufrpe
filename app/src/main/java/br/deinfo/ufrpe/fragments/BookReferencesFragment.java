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
import android.widget.TextView;

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
import br.deinfo.ufrpe.async.BookReferencesAsync;
import br.deinfo.ufrpe.listeners.BookCopiesListener;
import br.deinfo.ufrpe.listeners.BookDetailListener;
import br.deinfo.ufrpe.listeners.BookReferencesListener;
import br.deinfo.ufrpe.models.Book;
import br.deinfo.ufrpe.models.BookCopies;

/**
 * Created by phgm on 17/01/2017.
 */

public class BookReferencesFragment extends Fragment implements BookReferencesListener {

    private TextView mTitle, mReference;
    private List<String> mReferences;

    public static BookReferencesFragment newInstance(Book book) {
        BookReferencesFragment fragment = new BookReferencesFragment();
        Bundle args = new Bundle();
        args.putParcelable("book", Parcels.wrap(book));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Book book = Parcels.unwrap(getArguments().getParcelable("book"));

        View view = inflater.inflate(R.layout.fragment_book_references, container, false);

        mTitle = (TextView) view.findViewById(R.id.title);
        mReference = (TextView) view.findViewById(R.id.reference);

        String[] params = new String[1];
        params[0] = String.valueOf(book.getId());

        if (savedInstanceState != null)
            mReferences = Parcels.unwrap(savedInstanceState.getParcelable("references"));

        if (mReferences == null)
            new BookReferencesAsync(getContext(), this).execute(params);
        else
            referencesBook(mReferences);

        return view;
    }

    @Override
    public void referencesBook(List<String> references) {
        mReferences = references;

        mTitle.setText(references.get(0));
        mReference.setText(references.get(1));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("references", Parcels.wrap(mReferences));
    }
}