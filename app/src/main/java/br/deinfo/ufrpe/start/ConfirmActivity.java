package br.deinfo.ufrpe.start;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.squareup.picasso.Picasso;

import br.deinfo.ufrpe.R;
import br.deinfo.ufrpe.databinding.ActivityConfirmBinding;
import br.deinfo.ufrpe.models.User;

public class ConfirmActivity extends AppCompatActivity {

    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityConfirmBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_confirm);

        if (getIntent().hasExtra("user")) {
            mUser = getIntent().getParcelableExtra("user");

            binding.setUser(mUser);
            binding.setHandlers(this);
        } else {
            finish();
        }
    }

    @BindingAdapter({"bind:imageUrl", "bind:error"})
    public static void loadImage(ImageView view, String url, Drawable error) {
        Picasso.with(view.getContext()).load(url).error(error).into(view);
    }

    @BindingAdapter({"bind:selection"})
    public static void setSelection(Spinner spinner, int position) {
        spinner.setSelection(position);
    }

    public void onClickConfirm(View view) {
        if (mUser != null) {

        }
    }
}
