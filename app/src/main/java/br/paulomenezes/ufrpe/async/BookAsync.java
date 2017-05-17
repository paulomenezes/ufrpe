package br.paulomenezes.ufrpe.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

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
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.paulomenezes.ufrpe.R;
import br.paulomenezes.ufrpe.listeners.BookListener;
import br.paulomenezes.ufrpe.models.Book;

/**
 * Created by paulo on 16/01/2017.
 */

public class BookAsync extends AsyncTask<String[], Void, List<Book>> {
    private ProgressDialog mLoading;

    private BookListener mListener;

    public BookAsync(Context context, BookListener listener) {
        mLoading = ProgressDialog.show(context, null, context.getString(R.string.loading), true);
        mListener = listener;
    }

    @Override
    protected void onPreExecute(){

    }

    @Override
    protected List<Book> doInBackground(String[]... params) {
        List<Book> books = new ArrayList<>();

        try {
            String url = "http://ww2.bc.ufrpe.br/pergamum/biblioteca/index.php?rs=ajax_resultados&rst=&rsrnd=" + new Date().getTime() + "&rsargs[]=50&rsargs[]=" + params[0][2] + "&rsargs[]=" + params[0][0] + "&rsargs[]=" + params[0][1] + "&rsargs[]=&rsargs[]=%2C&rsargs[]=palavra&rsargs[]=&rsargs[]=&rsargs[]=&rsargs[]=&rsargs[]=&rsargs[]=" + (params[0][3] != null ? params[0][3] : "") + "&rsargs[]=&rsargs[]=obra_formatado&rsargs[]=587d40e95f249&rsargs[]=&rsargs[]=&rsargs[]=&rsargs[]=";

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

                        int id = 0;

                        final Pattern pattern = Pattern.compile("carrega_dados_acervo\\((.+?)\\);");
                        final Matcher matcher = pattern.matcher(e.toString());

                        if (matcher.find())
                            id = Integer.parseInt(matcher.group(1).replaceAll("\"", "").replaceAll("\\\\", ""));

                        books.add(new Book(id, title, img, author));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return books;
    }

    @Override
    protected void onPostExecute(List<Book> books) {
        super.onPostExecute(books);

        mListener.downloadBooks(books);
        mLoading.dismiss();
    }
}
