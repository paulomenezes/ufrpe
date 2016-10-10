package br.deinfo.ufrpe.start;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import br.deinfo.ufrpe.MainActivity;
import br.deinfo.ufrpe.R;
import com.crashlytics.android.Crashlytics;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import org.parceler.Parcels;

import br.deinfo.ufrpe.models.User;
import br.deinfo.ufrpe.utils.Data;
import io.fabric.sdk.android.Fabric;

public class TipsActivity extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "uRHTNPaKrPhE3Xs8GF29dgeH9";
    private static final String TWITTER_SECRET = "i6RwkrXhAZ7LyURE8lXomplif9cr0OH59csstKF0WK0YBBF7ho";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        User user = Data.getUser(this);

        if (user != null) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(Data.KEY_USER, Parcels.wrap(user));
            startActivity(intent);
        }

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig), new Crashlytics());
        setContentView(R.layout.activity_tips);

        findViewById(R.id.btnStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TipsActivity.this, SocialActivity.class));
            }
        });
    }
}
