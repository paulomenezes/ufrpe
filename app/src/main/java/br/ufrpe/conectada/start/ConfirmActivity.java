package br.ufrpe.conectada.start;

import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import br.ufrpe.conectada.R;
import br.ufrpe.conectada.databinding.ActivityConfirmBinding;
import br.ufrpe.conectada.models.User;

public class ConfirmActivity extends AppCompatActivity {

    private Spinner mSpUnit;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityConfirmBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_confirm);

        if (getIntent().hasExtra("user")) {
            mUser = Parcels.unwrap(getIntent().getParcelableExtra("user"));

            mSpUnit = (Spinner) findViewById(R.id.spUnit);

            binding.setUser(mUser);
            binding.setHandlers(this);
        } else {
            finish();
        }
    }

    @BindingAdapter({"bind:imageUrl", "bind:error"})
    public static void loadImage(ImageView view, String url, Drawable error) {
        try {
            int resource = Integer.parseInt(url);
            Picasso.with(view.getContext()).load(resource).error(error).into(view);
        } catch (Exception e) {
            Picasso.with(view.getContext()).load(url).error(error).into(view);
        }
    }

    public void onClickConfirm(View view) {
        if (mUser.getFirstName() == null || mUser.getFirstName().length() == 0) {
            Snackbar.make(findViewById(R.id.relativeLayout), R.string.error_select_name, Snackbar.LENGTH_LONG).show();
        } else if (mSpUnit.getSelectedItemId() == 0) {
            Snackbar.make(findViewById(R.id.relativeLayout), R.string.error_select_unit, Snackbar.LENGTH_LONG).show();
        } else {
            mUser.setUnit(mSpUnit.getSelectedItemPosition());

            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("user", Parcels.wrap(mUser));
            startActivity(intent);
        }
    }
}
