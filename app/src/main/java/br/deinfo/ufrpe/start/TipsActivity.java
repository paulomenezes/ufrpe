package br.deinfo.ufrpe.start;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.deinfo.ufrpe.R;

public class TipsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        findViewById(R.id.btnStart).setOnClickListener((v) -> startActivity(new Intent(this, SocialActivity.class)));
    }
}
