package br.ufrpe.conectada.fragments;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import org.parceler.Parcels;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.ufrpe.conectada.BR;
import br.ufrpe.conectada.R;
import br.ufrpe.conectada.async.BookDetailAsync;
import br.ufrpe.conectada.listeners.BookDetailListener;
import br.ufrpe.conectada.models.Book;

/**
 * Created by phgm on 17/01/2017.
 */

public class BookDetailFragment extends Fragment implements BookDetailListener {
    private LinearLayout mBookSubjects;
    private Button mPrimaryAuthor, mSecondaryAuthor;
    private ViewDataBinding mBinding;

    private Map<String, String> mBook;

    public static BookDetailFragment newInstance(Book book) {
        BookDetailFragment fragment = new BookDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("book", Parcels.wrap(book));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Book book = Parcels.unwrap(getArguments().getParcelable("book"));

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_book_detail, container, false);

        mBookSubjects = (LinearLayout) mBinding.getRoot().findViewById(R.id.bookSubjects);
        mPrimaryAuthor = (Button) mBinding.getRoot().findViewById(R.id.primaryAuthor);
        mSecondaryAuthor = (Button) mBinding.getRoot().findViewById(R.id.secondaryAuthor);

        String[] params = new String[1];
        params[0] = String.valueOf(book.getId());

        if (savedInstanceState != null)
            mBook = Parcels.unwrap(savedInstanceState.getParcelable("book"));

        if (mBook == null)
            new BookDetailAsync(getContext(), this).execute(params);
        else
            detailBook(mBook);

        return mBinding.getRoot();
    }

    @Override
    public void detailBook(Map<String, String> book) {
        mBook = book;

        mBinding.setVariable(BR.map, book);
        mBinding.executePendingBindings();

        final Pattern pattern = Pattern.compile("<(\\w+)( +.+)*>(.+?)</\\1>");

        if (book.containsKey("Autor Principal")) {
            final Matcher matcher = pattern.matcher(book.get("Autor Principal"));
            if (matcher.find()) {
                mPrimaryAuthor.setText(matcher.group(3));
                mPrimaryAuthor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.putExtra("search", mPrimaryAuthor.getText());
                        intent.putExtra("type", "Autor");
                        getActivity().setResult(Activity.RESULT_OK, intent);
                        getActivity().finish();
                    }
                });
            }
        } else {
            mBinding.getRoot().findViewById(R.id.primaryAuthorView).setVisibility(View.GONE);
        }

        if (book.containsKey("Autor Secundário")) {
            final Matcher matcher = pattern.matcher(book.get("Autor Secundário"));
            if (matcher.find()) {
                mSecondaryAuthor.setText(matcher.group(3));
                mSecondaryAuthor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.putExtra("search", mSecondaryAuthor.getText());
                        intent.putExtra("type", "Autor");
                        getActivity().setResult(Activity.RESULT_OK, intent);
                        getActivity().finish();
                    }
                });
            }
        } else {
            mBinding.getRoot().findViewById(R.id.secondaryAuthorView).setVisibility(View.GONE);
        }

        String[] subjects = book.get("Assuntos").split("\n");
        for (String s: subjects) {
            final Matcher m = pattern.matcher(s);

            if (m.find()) {
                final Button button = (Button) getLayoutInflater(null).inflate(R.layout.button_accent, null);
                button.setText(m.group(3));
                button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.putExtra("search", button.getText());
                        intent.putExtra("type", "Assunto");

                        if (m.group(2).split("\\\\").length > 5) {
                            String number = m.group(2).split("\\\\")[4].replaceAll("\"", "");
                            intent.putExtra("number", number);
                        }

                        getActivity().setResult(Activity.RESULT_OK, intent);
                        getActivity().finish();
                    }
                });

                mBookSubjects.addView(button);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("book", Parcels.wrap(mBook));
    }
}