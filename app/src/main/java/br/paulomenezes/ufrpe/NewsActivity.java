package br.paulomenezes.ufrpe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import br.paulomenezes.ufrpe.adapters.NewsAdapter;
import br.paulomenezes.ufrpe.models.NewsDTO;
import br.paulomenezes.ufrpe.services.PorAquiRequests;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        final RecyclerView recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Call<NewsDTO> news = PorAquiRequests.getInstance().getService().getSiteInfo(19, 0);
        news.enqueue(new Callback<NewsDTO>() {
            @Override
            public void onResponse(Call<NewsDTO> call, Response<NewsDTO> response) {
                NewsAdapter adapter = new NewsAdapter(NewsActivity.this, response.body().getContent());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<NewsDTO> call, Throwable t) {

            }
        });
    }
}
