package br.deinfo.ufrpe.fragments;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.TextView;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import br.deinfo.ufrpe.R;
import br.deinfo.ufrpe.adapters.LibraryAdapter;
import br.deinfo.ufrpe.listeners.MainTitle;
import br.deinfo.ufrpe.models.Book;

/**
 * Created by paulo on 14/01/2017.
 */

public class LibraryFragment extends Fragment {

    private List<Book> mBooks = new ArrayList<>();
    private ProgressDialog mLoading;

    private RadioButton[] mTypes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_library, container, false);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        mLoading = new ProgressDialog(getActivity());
        mLoading.setTitle(getString(R.string.loading));
        mLoading.setIndeterminate(true);

        final EditText searchField = (EditText) view.findViewById(R.id.search);
        searchField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    actionId == EditorInfo.IME_ACTION_DONE ||
                    event.getAction() == KeyEvent.ACTION_DOWN &&
                    event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

                    InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    search(view, searchField.getText().toString());
                    return true; // consume.
                }
                return false; // pass on to other listeners.
            }
        });


        CompoundButton.OnCheckedChangeListener mListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                search(view, searchField.getText().toString());
            }
        };

        mTypes = new RadioButton[4];
        mTypes[0] = (RadioButton) view.findViewById(R.id.all);
        mTypes[0].setOnCheckedChangeListener(mListener);

        mTypes[1] = (RadioButton) view.findViewById(R.id.title);
        mTypes[1].setOnCheckedChangeListener(mListener);

        mTypes[2] = (RadioButton) view.findViewById(R.id.author);
        mTypes[2].setOnCheckedChangeListener(mListener);

        mTypes[3] = (RadioButton) view.findViewById(R.id.subject);
        mTypes[3].setOnCheckedChangeListener(mListener);

        return view;
    }

    private void search(View view, String search) {
        mBooks.clear();
        mLoading.show();

        String type = "L";
        if (mTypes[1].isChecked())
            type = "T";
        else if (mTypes[2].isChecked())
            type = "A";
        else if (mTypes[3].isChecked())
            type = "S";
        else
            type = "L";

        try {
            URLConnection connection = (new URL("http://ww2.bc.ufrpe.br/pergamum/biblioteca/index.php?rs=ajax_resultados&rst=&rsrnd=1484429529453&rsargs[]=20&rsargs[]=0&rsargs[]=" + type + "&rsargs[]=" + search + "&rsargs[]=&rsargs[]=%2C&rsargs[]=palavra&rsargs[]=&rsargs[]=&rsargs[]=&rsargs[]=&rsargs[]=&rsargs[]=&rsargs[]=&rsargs[]=obra_formatado&rsargs[]=587a98a2ae0f0&rsargs[]=&rsargs[]=&rsargs[]=&rsargs[]=")).openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();

            // Read and store the result line by line then return the entire string.
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "ISO-8859-1"));
            StringBuilder html = new StringBuilder();
            for (String line; (line = reader.readLine()) != null; ) {
                html.append(line.replaceAll("\\'", "\'"));
            }
            in.close();

            Document doc = Jsoup.parse(html.toString());
            if (doc != null) {
                Elements newsHeadlines = doc.select("a");
                for (Element e: newsHeadlines) {
                    if (e.toString().contains("carrega_dados_acervo")) {
                        String title = e.text();
                        String img = "";
                        Element table = e.parent().parent().parent().parent().parent().parent();
                        Elements imgs = table.getElementsByTag("img");
                        if (imgs != null && imgs.size() > 0 && imgs.get(0).attributes().size() > 2) {
                            img = imgs.get(0).attributes().get("src").replaceAll("\\'", "").replaceAll("\\\\", "");
                        }

                        String author = e.parent().childNodes().size() > 5 ? e.parent().childNodes().get(5).outerHtml() : "";

                        mBooks.add(new Book(title, img, author));
                    }
                }
            }

            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
            recyclerView.setAdapter(new LibraryAdapter(mBooks));
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

            mLoading.dismiss();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
