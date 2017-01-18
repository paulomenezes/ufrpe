package br.deinfo.ufrpe.fragments;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.deinfo.ufrpe.BR;
import br.deinfo.ufrpe.BookDetailActivity;
import br.deinfo.ufrpe.R;
import br.deinfo.ufrpe.async.BookDetailAsync;
import br.deinfo.ufrpe.databinding.ActivityModuleBinding;
import br.deinfo.ufrpe.listeners.BookDetailListener;
import br.deinfo.ufrpe.models.Book;

/**
 * Created by phgm on 17/01/2017.
 */

public class BookDetailFragment extends Fragment implements BookDetailListener {
    private LinearLayout mBookSubjects;
    private Button mSecondaryAuthor;
    private ViewDataBinding mBinding;

    public BookDetailFragment() {
    }

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
        mSecondaryAuthor = (Button) mBinding.getRoot().findViewById(R.id.secondaryAuthor);

        String[] params = new String[1];
        params[0] = String.valueOf(book.getId());

        new BookDetailAsync(getContext(), this).execute(params);

        return mBinding.getRoot();
    }

    @Override
    public void detailBook(Map<String, String> book) {
        mBinding.setVariable(BR.map, book);
        mBinding.executePendingBindings();

        final Pattern pattern = Pattern.compile("<(\\w+)( +.+)*>(.+?)</\\1>");
        final Matcher matcher = pattern.matcher(book.get("Autor Secundário"));
        if (matcher.find())
            mSecondaryAuthor.setText(matcher.group(3));

        String[] subjects = book.get("Assuntos").split("\n");
        for (String s: subjects) {
            final Pattern p = Pattern.compile("<(\\w+)( +.+)*>(.+?)</\\1>");
            final Matcher m = pattern.matcher(s);

            if (m.find()) {
                Button button = (Button) getLayoutInflater(null).inflate(R.layout.button_accent, null);
                button.setText(m.group(3));
                button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                mBookSubjects.addView(button);
            }
        }
    }
}