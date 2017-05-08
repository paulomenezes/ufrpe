package br.ufrpe.conectada.start;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.TwitterAuthProvider;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import br.ufrpe.conectada.R;
import br.ufrpe.conectada.databinding.ActivitySocialBinding;
import br.ufrpe.conectada.models.User;
import io.fabric.sdk.android.DefaultLogger;
import io.fabric.sdk.android.Fabric;

public class SocialActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, FacebookCallback<LoginResult> {

    private GoogleApiClient mGoogleApiClient;
    private CallbackManager mCallbackManager;

    private RelativeLayout mRelativeLayout;

    private int RC_SIGN_IN = 1;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private TwitterAuthClient mTwitterAuthClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        TwitterAuthConfig twitterConfig = new TwitterAuthConfig(getString(R.string.twitter_key), getString(R.string.twitter_secret));

        Fabric fabric = new Fabric.Builder(this)
                .kits(new TwitterCore(twitterConfig))
                .logger(new DefaultLogger(Log.DEBUG))
                .debuggable(true)
                .build();

        Fabric.with(fabric);

        ActivitySocialBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_social);
        binding.setHandlers(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("FDBc2aD7j-0iy3R2kCXTt5qr")
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mCallbackManager = CallbackManager.Factory.create();
        mRelativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);

        LoginManager.getInstance().registerCallback(mCallbackManager, this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    login(new User(firebaseUser));
                }
            }
        };

        mTwitterAuthClient = new TwitterAuthClient();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Snackbar.make(mRelativeLayout, R.string.login_failed, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                final GoogleSignInAccount account = result.getSignInAccount();

                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                mAuth.signInWithCredential(credential)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Snackbar.make(mRelativeLayout, R.string.login_failed, Snackbar.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else {
                Snackbar.make(mRelativeLayout, R.string.login_failed, Snackbar.LENGTH_SHORT).show();
            }
        } else {
            mTwitterAuthClient.onActivityResult(requestCode, resultCode, data);
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        AuthCredential credential = FacebookAuthProvider.getCredential(loginResult.getAccessToken().getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Snackbar.make(mRelativeLayout, R.string.login_failed, Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException error) {
        Snackbar.make(mRelativeLayout, R.string.login_failed, Snackbar.LENGTH_SHORT).show();
    }

    public void onClickFacebook(View view) {
        List<String> permissions = new ArrayList<>();
        permissions.add("public_profile");
        permissions.add("email");
        LoginManager.getInstance().logInWithReadPermissions(this, permissions);
    }

    public void onClickTwitter(View view) {
        mTwitterAuthClient.authorize(SocialActivity.this, new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                AuthCredential credential = TwitterAuthProvider.getCredential(result.data.getAuthToken().token, result.data.getAuthToken().secret);

                mAuth.signInWithCredential(credential)
                        .addOnCompleteListener(SocialActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Snackbar.make(mRelativeLayout, R.string.login_failed, Snackbar.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

            @Override
            public void failure(TwitterException exception) {
                Snackbar.make(mRelativeLayout, R.string.login_failed, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    public void onClickGooglePlus(View view) {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void onClickSkip(View view) {
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Snackbar.make(mRelativeLayout, R.string.login_failed, Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void login(User user) {
        Intent intent = new Intent(this, ConfirmActivity.class);
        intent.putExtra("user", Parcels.wrap(user));

        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mAuthListener != null)
            mAuth.removeAuthStateListener(mAuthListener);
    }
}
