package br.deinfo.ufrpe.start;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.parceler.Parcels;

import java.util.Collections;
import java.util.List;

import br.deinfo.ufrpe.MainActivity;
import br.deinfo.ufrpe.R;
import br.deinfo.ufrpe.databinding.ActivityLoginBinding;
import br.deinfo.ufrpe.models.Course;
import br.deinfo.ufrpe.models.SiteInfo;
import br.deinfo.ufrpe.models.Token;
import br.deinfo.ufrpe.models.User;
import br.deinfo.ufrpe.services.AVAService;
import br.deinfo.ufrpe.services.Requests;
import br.deinfo.ufrpe.utils.Data;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private User mUser;
    private AVAService mAVAServices;

    private RelativeLayout mRelativeLayout;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        if (getIntent().hasExtra("user")) {
            mUser = Parcels.unwrap(getIntent().getParcelableExtra("user"));

            binding.setUser(mUser);
            binding.setHandlers(this);

            mAVAServices = Requests.getInstance().getAVAService();
            mDatabase = FirebaseDatabase.getInstance().getReference();

            mRelativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        } else {
            finish();
        }
    }

    public void onClickSkip(View view) {

    }

    public void onClickAccess(View view) {
        Call<Token> object = mAVAServices.login(mUser.getUserName(), mUser.getPassword(), Requests.LOGIN_SERVICE);

        object.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                Token token = response.body();

                if (token != null && token.getToken() != null) {
                    mUser.setToken(token.getToken());

                    Call<SiteInfo> siteInfoCall = mAVAServices.getSiteInfo(Requests.FUNCTION_GET_SITE_INFO, mUser.getToken());
                    siteInfoCall.enqueue(new Callback<SiteInfo>() {
                        @Override
                        public void onResponse(Call<SiteInfo> call, Response<SiteInfo> response) {
                            SiteInfo siteInfo = response.body();

                            mUser.setAvaID(siteInfo.getUserid());

                            Call<List<Course>> coursesCall = mAVAServices.getUsersCourses(
                                    siteInfo.getUserid(),
                                    Requests.FUNCTION_GET_USER_COURSES,
                                    mUser.getToken());

                            coursesCall.enqueue(new Callback<List<Course>>() {
                                @Override
                                public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                                    List<Course> courses = response.body();

                                    Collections.reverse(courses);

                                    mUser.setCourses(courses);

                                    // mDatabase.child("users").child(mUser.getId()).setValue(mUser);

                                    Data.saveUser(LoginActivity.this, mUser);

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.putExtra(Data.KEY_USER, Parcels.wrap(mUser));
                                    startActivity(intent);
                                }

                                @Override
                                public void onFailure(Call<List<Course>> call, Throwable t) {
                                    Snackbar.make(mRelativeLayout, R.string.login_courses_error, Snackbar.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<SiteInfo> call, Throwable t) {
                            Snackbar.make(mRelativeLayout, R.string.login_siteinfo_error, Snackbar.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Snackbar.make(mRelativeLayout, R.string.login_failed, Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Snackbar.make(mRelativeLayout, R.string.login_failed, Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
