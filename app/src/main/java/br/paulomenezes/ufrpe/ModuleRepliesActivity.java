package br.paulomenezes.ufrpe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import org.parceler.Parcels;

import java.util.Arrays;

import br.paulomenezes.ufrpe.adapters.ForumPostAdapter;
import br.paulomenezes.ufrpe.models.Course;
import br.paulomenezes.ufrpe.models.Discussions;
import br.paulomenezes.ufrpe.models.ForumPosts;
import br.paulomenezes.ufrpe.services.AVAService;
import br.paulomenezes.ufrpe.services.Requests;
import br.paulomenezes.ufrpe.utils.ComparePosts;
import br.paulomenezes.ufrpe.utils.Functions;
import br.paulomenezes.ufrpe.utils.Session;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModuleRepliesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_replies);

        final Discussions discussion = Parcels.unwrap(getIntent().getParcelableExtra("discussion"));
        final Course course = Parcels.unwrap(getIntent().getParcelableExtra("course"));

        setTitle(discussion.getName());
        Functions.actionBarColor(this, course.getNormalColor(), course.getDarkColor());

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        AVAService mAvaService = Requests.getInstance().getAVAService();

        Call<ForumPosts> getForum = mAvaService.getDiscussionPosts(discussion.getDiscussion(), Requests.FUNCTION_GET_DISCUSSIONS_POSTS, Session.getUser(this).getToken());
        getForum.enqueue(new Callback<ForumPosts>() {
            @Override
            public void onResponse(Call<ForumPosts> call, Response<ForumPosts> response) {
                ForumPosts discussions = response.body();

                Arrays.sort(discussions.getPosts(), new ComparePosts());

                ForumPostAdapter adapter = new ForumPostAdapter(course, Arrays.asList(discussions.getPosts()));
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ForumPosts> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
