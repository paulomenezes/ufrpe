package br.paulomenezes.ufrpe.async;

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

import br.paulomenezes.ufrpe.R;
import br.paulomenezes.ufrpe.listeners.BookCopiesListener;
import br.paulomenezes.ufrpe.models.BookCopies;
import br.paulomenezes.ufrpe.models.BookCopy;

/**
 * Created by paulo on 29/01/2017.
 */

public class BookCopiesAsync extends AsyncTask<String[], Void, List<BookCopies>> {
    private ProgressDialog mLoading;
    private BookCopiesListener mListener;

    public BookCopiesAsync(Context context, BookCopiesListener listener) {
        mLoading = ProgressDialog.show(context, null, context.getString(R.string.loading), true);
        mListener = listener;
    }

    @Override
    protected void onPreExecute(){

    }

    @Override
    protected List<BookCopies> doInBackground(String[]... params) {
        List<BookCopies> book = new ArrayList<>();

        try {
            String url = "http://ww2.bc.ufrpe.br/pergamum/biblioteca/index.php?rs=ajax_dados_exemplar&rst=&rsrnd=1485710549900&rsargs[]=" + params[0][0] + "&rsargs[]=%2C";

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
                Elements newsHeadlines = doc.select("table");

                for (int k = 2; k < newsHeadlines.size(); k += 3) {
                    BookCopies copies = new BookCopies();
                    copies.setCopies(new ArrayList<BookCopy>());
                    copies.setLibrary(newsHeadlines.get(k).select("tr td").get(0).select("div").get(1).text().substring(2)); // newsHeadlines.get(k).select("tr td div").get(1).text().substring(2));
                    copies.setAvailable(Integer.parseInt(newsHeadlines.get(k + 1).select("tr td div font").get(0).text())); // newsHeadlines.get(k + 1).select("tr td div").text().split(":")[1].split("-")[0].replaceAll("\\s+", "")));
                    copies.setLoaned(Integer.parseInt(newsHeadlines.get(k + 1).select("tr td div font").get(1).text())); // newsHeadlines.get(k + 1).select("tr td div").text().split(":")[2].replaceAll("\\s+", "")));

                    for (int i = 1; i < newsHeadlines.get(k + 2).select("tr").size(); i++) {
                        Elements tds = newsHeadlines.get(k + 2).select("tr").get(i).select("td");
                        BookCopy bookCopy = new BookCopy(
                                tds.get(0).text(),
                                tds.get(1).text(),
                                tds.get(2).text(),
                                tds.get(3).text(),
                                tds.get(4).text(),
                                tds.get(5).text(),
                                tds.get(6).text());

                        copies.getCopies().add(bookCopy);
                    }

                    book.add(copies);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return book;
    }

    @Override
    protected void onPostExecute(List<BookCopies> book) {
        super.onPostExecute(book);

        mListener.copiesBook(book);
        mLoading.dismiss();
    }
}

