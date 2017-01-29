package br.deinfo.ufrpe.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import br.deinfo.ufrpe.R;
import br.deinfo.ufrpe.adapters.LibraryAdapter;
import br.deinfo.ufrpe.async.BookAsync;
import br.deinfo.ufrpe.listeners.BookListener;
import br.deinfo.ufrpe.models.Book;

/**
 * Created by paulo on 14/01/2017.
 */

public class LibraryFragment extends Fragment implements BookListener {

    private List<Book> mBooks = new ArrayList<>();

    private RadioButton[] mTypes;
    private TextView mNobooks;
    private RecyclerView mRecyclerView;

    private EditText mSearchField;

    private boolean mNoSearch = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_library, container, false);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        mSearchField = (EditText) view.findViewById(R.id.search);
        mSearchField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    actionId == EditorInfo.IME_ACTION_DONE ||
                    actionId == EditorInfo.IME_ACTION_NEXT ||
                    (event != null &&
                     event.getAction() == KeyEvent.ACTION_DOWN &&
                     event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {

                    InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (getActivity().getCurrentFocus() != null)
                        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    search();
                    return true; // consume.
                }

                return false; // pass on to other listeners.
            }
        });


        ((RadioGroup) view.findViewById(R.id.group)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (!mNoSearch) {
                    search();
                }

                mNoSearch = false;
            }
        });

        mTypes = new RadioButton[4];
        mTypes[0] = (RadioButton) view.findViewById(R.id.all);
        mTypes[1] = (RadioButton) view.findViewById(R.id.title);
        mTypes[2] = (RadioButton) view.findViewById(R.id.author);
        mTypes[3] = (RadioButton) view.findViewById(R.id.subject);

        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        mRecyclerView.setAdapter(new LibraryAdapter(getActivity(), mBooks));
        mRecyclerView.setLayoutManager(linearLayout);
//        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayout) {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                search(mSearchField.getText().toString(), page + 1);
//            }
//        });

        mNobooks = (TextView) view.findViewById(R.id.noBooks);

        return view;
    }

    private void search() {
        search(null);
    }

    private void search(String number) {
        String search = mSearchField.getText().toString();

        mBooks.clear();

        String type = "L";
        if (mTypes[1].isChecked())
            type = "T";
        else if (mTypes[2].isChecked())
            type = "A";
        else if (mTypes[3].isChecked())
            type = "S";

        String query = type;
        try {
            query = URLEncoder.encode(search, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] params = new String[4];
        params[0] = type;
        params[1] = query;
        params[2] = "0";
        params[3] = number;

        new BookAsync(getContext(), this).execute(params);
    }

    @Override
    public void downloadBooks(List<Book> books) {
        mBooks.addAll(books);

        mRecyclerView.setAdapter(new LibraryAdapter(getActivity(), mBooks));

        if (mBooks.size() == 0) {
            mNobooks.setVisibility(View.VISIBLE);
        } else {
            mNobooks.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            String search = data.getStringExtra("search");
            String type = data.getStringExtra("type");
            String number = data.getStringExtra("number");

            mNoSearch = true;

            mSearchField.setText(search);
            if (type.equals("Autor")) {
                mTypes[2].setChecked(true);
            } else if (type.equals("Assunto")) {
                mTypes[3].setChecked(true);
            }

            search(number);
        }
    }
}
