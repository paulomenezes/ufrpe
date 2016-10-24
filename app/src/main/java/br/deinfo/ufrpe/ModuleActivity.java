package br.deinfo.ufrpe;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.parceler.Parcels;

import br.deinfo.ufrpe.databinding.ActivityConfirmBinding;
import br.deinfo.ufrpe.databinding.ActivityModuleBinding;
import br.deinfo.ufrpe.models.Modules;

public class ModuleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityModuleBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_module);

        final Modules module = Parcels.unwrap(getIntent().getParcelableExtra("module"));
        binding.setModule(module);

        setTitle(module.getName());

        CardView submission = (CardView) findViewById(R.id.submission);

        TextView mDescription = (TextView) findViewById(R.id.description);
        Button mAddURL = (Button) findViewById(R.id.addUrl);
        if (module.getModname().equals("url")) {
            submission.setVisibility(View.GONE);

            if (module.getDescription() != null)
                mDescription.setText(Html.fromHtml(module.getDescription()));
            else
                mDescription.setVisibility(View.GONE);

            if (module.getContents().length > 0) {
                mAddURL.setText(module.getContents()[0].getFilename());
                mAddURL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(module.getContents()[0].getFileurl()));
                        startActivity(browserIntent);
                    }
                });
            } else {
                mAddURL.setVisibility(View.GONE);
            }
        } else {
            mDescription.setVisibility(View.GONE);
            mAddURL.setVisibility(View.GONE);
        }
    }
}
