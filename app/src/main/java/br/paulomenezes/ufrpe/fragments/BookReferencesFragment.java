package br.paulomenezes.ufrpe.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.List;

import br.paulomenezes.ufrpe.R;
import br.paulomenezes.ufrpe.async.BookReferencesAsync;
import br.paulomenezes.ufrpe.listeners.BookReferencesListener;
import br.paulomenezes.ufrpe.models.Book;

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