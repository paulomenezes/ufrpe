package br.deinfo.ufrpe.start;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.deinfo.ufrpe.R;

public class SocialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);

        findViewById(R.id.btnSkip).setOnClickListener((v) -> startActivity(new Intent(this, ConfirmActivity.class)));
    }
}
