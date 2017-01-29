package br.deinfo.ufrpe.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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
import br.deinfo.ufrpe.listeners.BookCopiesListener;
import br.deinfo.ufrpe.listeners.BookReferencesListener;
import br.deinfo.ufrpe.models.BookCopies;
import br.deinfo.ufrpe.models.BookCopy;

/**
 * Created by paulo on 29/01/2017.
 */

public class BookReferencesAsync extends AsyncTask<String[], Void, List<String>> {
    private ProgressDialog mLoading;
    private BookReferencesListener mListener;

    public BookReferencesAsync(Context context, BookReferencesListener listener) {
        mLoading = ProgressDialog.show(context, null, context.getString(R.string.loading), true);
        mListener = listener;
    }

    @Override
    protected void onPreExecute(){

    }

    @Override
    protected List<String> doInBackground(String[]... params) {
        List<String> texts = new ArrayList<>();

        try {
            String url = "http://ww2.bc.ufrpe.br/pergamum/biblioteca/index.php?rs=ajax_dados_referencia&rst=&rsrnd=1485710549900&rsargs[]=" + params[0][0];

            URLConnection connection = (new URL(url)).openConnection();
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
                Elements newsHeadlines = doc.select("table tr td div");

                for (int k = 0; k < newsHeadlines.size(); k++) {
                    texts.add(newsHeadlines.get(k).text());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return texts;
    }

    @Override
    protected void onPostExecute(List<String> texts) {
        super.onPostExecute(texts);

        mListener.referencesBook(texts);
        mLoading.dismiss();
    }
}

