package br.deinfo.ufrpe;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.parceler.Parcels;

import java.sql.Date;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;

import br.deinfo.ufrpe.adapters.ForumAdapter;
import br.deinfo.ufrpe.databinding.ActivityModuleBinding;
import br.deinfo.ufrpe.models.Course;
import br.deinfo.ufrpe.models.Forum;
import br.deinfo.ufrpe.models.Modules;
import br.deinfo.ufrpe.services.AVAService;
import br.deinfo.ufrpe.services.Requests;
import br.deinfo.ufrpe.utils.Session;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModuleActivity extends AppCompatActivity {

    private AVAService mAvaService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityModuleBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_module);

        mAvaService = Requests.getInstance().getAVAService();

        final Modules module = Parcels.unwrap(getIntent().getParcelableExtra("module"));
        final Course course = Parcels.unwrap(getIntent().getParcelableExtra("course"));

        binding.setModule(module);

        setTitle(module.getName());

        CardView submission = (CardView) findViewById(R.id.submission);

        TextView mDescription = (TextView) findViewById(R.id.description);
        Button mAddURL = (Button) findViewById(R.id.addUrl);

        LinearLayout activityModule = (LinearLayout) findViewById(R.id.activity_module);
        final RecyclerView forum = (RecyclerView) findViewById(R.id.forum);

        switch (module.getModname()) {
            case "url":
                if (module.getDescription() != null)
                    mDescription.setText(Html.fromHtml(module.getDescription()));
                else
                    mDescription.setVisibility(View.GONE);

                if (module.getContents().length > 0) {
                    mAddURL.setText(module.getContents()[0].getFilename());
                    setButtonClick(mAddURL, module.getContents()[0].getFileurl());
                } else {
                    mAddURL.setVisibility(View.GONE);
                }
                break;
            case "assign":
                submission.setVisibility(View.VISIBLE);
                mAddURL.setText(getString(R.string.addAssign));
                setButtonClick(mAddURL, module.getUrl());

                TextView dueDate = (TextView) findViewById(R.id.dueDate);
                TextView timeRemaining = (TextView) findViewById(R.id.timeRemaining);

                if (course.getAssignments() != null) {
                    for (int i = 0; i < course.getAssignments().length; i++) {
                        if (course.getAssignments()[i].getCmid().equals(module.getId())) {
                            mDescription.setText(Html.fromHtml(course.getAssignments()[i].getIntro()));

                            Date date = new Date(Long.parseLong(course.getAssignments()[i].getDuedate()) * 1000);

                            long diff = date.getTime() - Calendar.getInstance().getTime().getTime();
                            long diffSeconds = diff / 1000 % 60;
                            long diffMinutes = diff / (60 * 1000) % 60;
                            long diffHours = diff / (60 * 60 * 1000) % 24;
                            long diffDays = diff / (24 * 60 * 60 * 1000);

                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(date);

                            int day = calendar.get(Calendar.DAY_OF_MONTH);
                            String month = getResources().getStringArray(R.array.month)[calendar.get(Calendar.MONTH)].toLowerCase();
                            int year = calendar.get(Calendar.YEAR);
                            int hour = calendar.get(Calendar.HOUR_OF_DAY);
                            int minute = calendar.get(Calendar.MINUTE);

                            String remaining = diffDays > 0 ? String.format("%d %s ", diffDays, getString(R.string.day)) : "";
                            remaining += diffHours > 0 ? String.format("%d %s ", diffHours, getString(R.string.hour)) : "";
                            if (diffHours <= 0) {
                                remaining += diffMinutes > 0 ? String.format("%d %s ", diffMinutes, getString(R.string.minute)) : "";
                                remaining += diffSeconds > 0 ? String.format("%d %s ", diffSeconds, getString(R.string.second)) : "";
                            }

                            if (remaining.replaceAll(" ", "").isEmpty() || remaining.replaceAll(" ", "").length() == 0) {
                                remaining = getString(R.string.timend);
                            }

                            dueDate.setText(String.format("%d de %s de %d às %d:%d", day, month, year, hour, minute));
                            timeRemaining.setText(remaining);
                            break;
                        }
                    }
                }
                break;
            case "label":
                mDescription.setText(Html.fromHtml(module.getDescription()));
                mAddURL.setVisibility(View.GONE);
                break;
            case "quiz":
                mDescription.setText(getString(R.string.contentUnavailable));
                mAddURL.setText(getString(R.string.goToSite));
                setButtonClick(mAddURL, module.getUrl());
                break;
            case "forum":
                activityModule.setVisibility(View.GONE);
                forum.setVisibility(View.VISIBLE);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                forum.setLayoutManager(linearLayoutManager);

                Call<Forum> getForum = mAvaService.getForum(module.getInstance(), "timemodified", 0, 10, Requests.FUNCTION_GET_DISCUSSIONS, Session.getUser().getToken());
                getForum.enqueue(new Callback<Forum>() {
                    @Override
                    public void onResponse(Call<Forum> call, Response<Forum> response) {
                        Forum discussions = response.body();

                        ForumAdapter adapter = new ForumAdapter(Arrays.asList(discussions.getDiscussions()));
                        forum.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<Forum> call, Throwable t) {

                    }
                });

                break;
            default:
                mDescription.setVisibility(View.GONE);
                mAddURL.setVisibility(View.GONE);
                break;
        }
    }

    private void setButtonClick(Button button, final String url) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        });
    }
}
