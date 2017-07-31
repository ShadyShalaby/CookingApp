package com.demo.cooking.activities;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.cooking.R;
import com.demo.cooking.network.NetworkManager;
import com.demo.cooking.utilities.PreferenceManager;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Response;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

    @BindView(R.id.btn_signIn_action)
    Button btn_signIn;
    @BindView(R.id.txt_email)
    EditText txt_email;
    @BindView(R.id.txt_password)
    EditText txt_password;
    @BindView(R.id.txt_signUp)
    TextView txt_signUp;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (PreferenceManager.getCustomerData(this) != null) {
            startActivity(HomeActivity.getIntent(this));
        }

        ButterKnife.bind(this);

        attachListeners();
    }

    private void attachListeners() {
        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validLoginData())
                    callLoginWebService();
            }
        });

        txt_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(SignUpActivity.getIntent(MainActivity.this));
            }
        });
    }

    private void callLoginWebService() {
        showProgress();
        NetworkManager.getInstance().userLogin(
                MainActivity.this,
                NetworkManager.buildUserLoginParams(txt_email.getText().toString().trim(), txt_password.getText().toString().trim()),
                new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> result) {
                        if (e != null) {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        } else if (result.getHeaders().code() == 401) {
                            Toast.makeText(MainActivity.this, "Invalid email or password", Toast.LENGTH_LONG).show();
                        } else if (result.getHeaders().code() == 200) {
                            Log.i(MainActivity.class.getName(), "Login Result " + result.getResult());
                            PreferenceManager.setCustomerData(MainActivity.this, result.getResult());
                            startActivity(HomeActivity.getIntent(MainActivity.this));
                        } else
                            Toast.makeText(MainActivity.this, "Error occurred, please try again", Toast.LENGTH_LONG).show();
                        hideProgress();
                    }
                });
    }

    private boolean validLoginData() {

        String email = txt_email.getText().toString();
        String pass = txt_password.getText().toString();

        if (TextUtils.isEmpty(email.trim()))
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_LONG).show();
        else if (!Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches())
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_LONG).show();
        else if (TextUtils.isEmpty(pass.trim()))
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_LONG).show();
        else if (pass.length() < 8)
            Toast.makeText(this, "The password must be 8 characters or more", Toast.LENGTH_LONG).show();
        else
            return true;

        return false;
    }

    public void showProgress() {
        if (dialog == null) {
            dialog = new Dialog(this, R.style.dialog_theme);
            dialog.setCancelable(false);
        }
        if (!dialog.isShowing()) {
            mProgressBar.setVisibility(View.VISIBLE);
            dialog.show();
        }
    }

    public void hideProgress() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

}
