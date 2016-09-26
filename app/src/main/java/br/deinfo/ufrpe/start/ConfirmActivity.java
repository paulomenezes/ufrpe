package br.deinfo.ufrpe.start;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.squareup.picasso.Picasso;

import br.deinfo.ufrpe.R;
import br.deinfo.ufrpe.models.User;

public class ConfirmActivity extends AppCompatActivity {

    private User mUser;
    private ImageView mIvPicture;
    private EditText mEtFirstName, mEtLastName;
    private Spinner mSpUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConfirmActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_confirm);

        mIvPicture = (ImageView) findViewById(R.id.ivPicture);
        mEtFirstName = (EditText) findViewById(R.id.etFirstName);
        mEtLastName = (EditText) findViewById(R.id.etLastName);
        mSpUnit = (Spinner) findViewById(R.id.spUnit);

        if (getIntent().hasExtra("user")) {
            mUser = getIntent().getParcelableExtra("user");

        } else {
            finish();
        }
    }

    @BindingAdapter({"bind:imageUrl", "bind:error"})
    public static void loadImage(ImageView view, String url, Drawable error) {
        Picasso.with(view.getContext()).load(url).error(error).into(view);
    }
}
