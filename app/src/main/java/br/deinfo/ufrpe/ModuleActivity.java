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
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import org.parceler.Parcels;

import java.sql.Date;
import java.util.Arrays;
import java.util.Calendar;

import br.deinfo.ufrpe.adapters.ForumAdapter;
import br.deinfo.ufrpe.databinding.ActivityModuleBinding;
import br.deinfo.ufrpe.models.Course;
import br.deinfo.ufrpe.models.Forum;
import br.deinfo.ufrpe.models.Modules;
import br.deinfo.ufrpe.services.AVAService;
import br.deinfo.ufrpe.services.Requests;
import br.deinfo.ufrpe.utils.Functions;
import br.deinfo.ufrpe.utils.Session;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModuleActivity extends AppCompatActivity {

    private AVAService mAvaService;
    private TextView mDescription;
    private TextView dueDate;
    private TextView timeRemaining;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityModuleBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_module);

        mAvaService = Requests.getInstance().getAVAService();

        final Modules module = Parcels.unwrap(getIntent().getParcelableExtra("module"));
        final Course course = Parcels.unwrap(getIntent().getParcelableExtra("course"));

        final int dueModuleDate = getIntent().getIntExtra("dueDate", -1);

        binding.setModule(module);

        setTitle(module.getName());
        Functions.actionBarColor(this, course.getNormalColor(), course.getDarkColor());

        CardView submission = (CardView) findViewById(R.id.submission);

        mDescription = (TextView) findViewById(R.id.description);
        final Button mAddURL = (Button) findViewById(R.id.addUrl);

        final RecyclerView forum = (RecyclerView) findViewById(R.id.forum);
        final ScrollView scroll = (ScrollView) findViewById(R.id.scroll);

        switch (module.getModname()) {
            case "url":
                if (module.getDescription() != null) {
                    mDescription.setText(Html.fromHtml(module.getDescription()));
                    mDescription.setMovementMethod(LinkMovementMethod.getInstance());
                } else
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

                dueDate = (TextView) findViewById(R.id.dueDate);
                timeRemaining = (TextView) findViewById(R.id.timeRemaining);

                if (dueModuleDate > -1) {
                    if (course.getAssignments() != null) {
                        for (int i = 0; i < course.getAssignments().length; i++) {
                            if (course.getAssignments()[i].getId().equals(module.getId())) {
                                drawModule(course.getAssignments()[i].getIntro(), Long.parseLong(course.getAssignments()[i].getDuedate()));
                                setButtonClick(mAddURL, "http://ava.ufrpe.br/mod/assign/view.php?id=" + course.getAssignments()[i].getCmid());
                                break;
                            }
                        }
                    } else {
                        drawModule(module.getDescription(), dueModuleDate);
                        mAddURL.setVisibility(View.GONE);
                    }
                } else if (course.getAssignments() != null) {
                    for (int i = 0; i < course.getAssignments().length; i++) {
                        if (course.getAssignments()[i].getCmid().equals(module.getId())) {
                            drawModule(course.getAssignments()[i].getIntro(), Long.parseLong(course.getAssignments()[i].getDuedate()));
                            setButtonClick(mAddURL, "http://ava.ufrpe.br/mod/assign/view.php?id=" + course.getAssignments()[i].getCmid());
                            break;
                        }
                    }
                }
                break;
            case "label":
                mDescription.setText(Html.fromHtml(module.getDescription()));
                mDescription.setMovementMethod(LinkMovementMethod.getInstance());
                mAddURL.setVisibility(View.GONE);
                break;
            case "quiz":
                mDescription.setText(getString(R.string.contentUnavailable));
                mAddURL.setText(getString(R.string.goToSite));
                setButtonClick(mAddURL, module.getUrl());
                break;
            case "forum":
                forum.setVisibility(View.VISIBLE);
                scroll.setVisibility(View.GONE);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                forum.setLayoutManager(linearLayoutManager);

                Call<Forum> getForum = mAvaService.getDiscussion(module.getInstance(), "timemodified", 0, 10, Requests.FUNCTION_GET_DISCUSSIONS, Session.getUser().getToken());
                getForum.enqueue(new Callback<Forum>() {
                    @Override
                    public void onResponse(Call<Forum> call, Response<Forum> response) {
                        Forum discussions = response.body();

                        if (discussions.getDiscussions().length > 0) {
                            ForumAdapter adapter = new ForumAdapter(course, Arrays.asList(discussions.getDiscussions()));
                            forum.setAdapter(adapter);
                        } else {
                            mDescription.setText(R.string.noDiscussions);

                            forum.setVisibility(View.GONE);
                            scroll.setVisibility(View.VISIBLE);
                            mAddURL.setVisibility(View.GONE);
                        }
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

    private void drawModule(String description, long dateDue) {
        mDescription.setText(Html.fromHtml(description));
        mDescription.setMovementMethod(LinkMovementMethod.getInstance());

        Date date = new Date(dateDue * 1000);

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
        remaining += diffMinutes > 0 ? String.format("%d %s ", diffMinutes, getString(R.string.minute)) : "";
        if (diffMinutes > 0) {
            remaining += diffSeconds > 0 ? String.format("%d %s ", diffSeconds, getString(R.string.second)) : "";
        }

        if (remaining.replaceAll(" ", "").isEmpty() || remaining.replaceAll(" ", "").length() == 0) {
            remaining = getString(R.string.timend);
        }

        dueDate.setText(String.format("%d de %s de %d às %d:%d", day, month, year, hour, minute));
        timeRemaining.setText(remaining);
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
