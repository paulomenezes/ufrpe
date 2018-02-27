package br.paulomenezes.ufrpe;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import br.paulomenezes.ufrpe.models.NewsContentDTO;

public class NewsPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NewsContentDTO news = Parcels.unwrap(getIntent().getParcelableExtra("news"));

        ImageView imageView = findViewById(R.id.image);
        Picasso.with(this).setLoggingEnabled(true);
        Picasso.with(this).load(news.getLargeImageUrl()).into(imageView);

        toolbar.setTitle(news.getTitle());
    }
}
