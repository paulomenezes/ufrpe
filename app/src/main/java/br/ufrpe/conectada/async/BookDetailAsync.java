package br.ufrpe.conectada.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.Html;

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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.ufrpe.conectada.R;
import br.ufrpe.conectada.listeners.BookDetailListener;

/**
 * Created by phgm on 17/01/2017.
 */

public class BookDetailAsync extends AsyncTask<String[], Void, Map<String, String>> {
    private ProgressDialog mLoading;
    private BookDetailListener mListener;

    public BookDetailAsync(Context context, BookDetailListener listener) {
        mLoading = ProgressDialog.show(context, null, context.getString(R.string.loading), true);
        mListener = listener;
    }

    @Override
    protected void onPreExecute(){

    }

    @Override
    protected Map<String, String> doInBackground(String[]... params) {
        Map<String, String> book = new HashMap<>();

        try {
            URLConnection connection = (new URL("http://ww2.bc.ufrpe.br/pergamum/biblioteca/index.php?rs=ajax_dados_acervo&rst=&rsrnd=" + new Date().getTime() + "&rsargs[]=" + params[0][0] + "&rsargs[]=")).openConnection();
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
                Elements newsHeadlines = doc.select("table tr");
                for (Element e: newsHeadlines) {
                    String key = e.select("td div strong").html();
                    String value = e.select("td div").get(1).html();

                    book.put(key, value.contains("</a>") ? value : Html.fromHtml(value).toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return book;
    }

    @Override
    protected void onPostExecute(Map<String, String> book) {
        super.onPostExecute(book);

        mListener.detailBook(book);
        mLoading.dismiss();
    }
}
