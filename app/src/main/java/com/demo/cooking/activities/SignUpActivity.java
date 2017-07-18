package com.demo.cooking.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.cooking.R;
import com.demo.cooking.models.Customer;
import com.demo.cooking.network.NetworkManager;
import com.demo.cooking.utilities.PreferenceManager;
import com.google.gson.Gson;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Response;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends Activity {

    @BindView(R.id.btn_signUp_action)
    Button btn_signUp;
    @BindView(R.id.txt_userName)
    EditText txt_userName;
    @BindView(R.id.txt_email)
    EditText txt_email;
    @BindView(R.id.txt_password)
    EditText txt_password;
    @BindView(R.id.txt_signIn)
    TextView txt_signIn;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    private Dialog dialog;

    public static Intent getIntent(Context context) {
        return new Intent(context, SignUpActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);

        txt_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
            }
        });

        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validSignUpData())
                    callSignUpWebService();
            }
        });
    }

    private boolean validSignUpData() {
        String userName = txt_userName.getText().toString();
        String email = txt_email.getText().toString();
        String pass = txt_password.getText().toString();

        if (TextUtils.isEmpty(userName.trim()))
            Toast.makeText(this, "Please enter your user name", Toast.LENGTH_LONG).show();
        else if (TextUtils.isEmpty(email.trim()))
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_LONG).show();
        else if (email.trim().length() < 7)
            Toast.makeText(this, "The email must be 7 characters or more", Toast.LENGTH_LONG).show();
        else if (!Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches())
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_LONG).show();
        else if (TextUtils.isEmpty(pass.trim()))
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_LONG).show();
        else if (pass.trim().length() < 8)
            Toast.makeText(this, "The password must be 8 characters or more", Toast.LENGTH_LONG).show();
        else
            return true;

        return false;
    }

    private void callSignUpWebService() {
        showProgress();
        NetworkManager.getInstance().userSignUp(
                this,
                NetworkManager.buildUserSignUpParams(
                        txt_userName.getText().toString().trim(),
                        txt_email.getText().toString().trim(),
                        txt_password.getText().toString().trim()
                ),
                new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> result) {
                        if (e != null)
                            Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        else if (result.getHeaders().code() == 409) {
                            Toast.makeText(SignUpActivity.this, "This email is already registered!!", Toast.LENGTH_LONG).show();
                        } else if (result.getHeaders().code() == 200) {
                            String customerJson = new Gson().toJson(new Customer(txt_userName.getText().toString().trim(),
                                    txt_email.getText().toString().trim()));
                            PreferenceManager.setCustomerData(SignUpActivity.this, customerJson);
                            startActivity(HomeActivity.getIntent(SignUpActivity.this));
                        } else
                            Toast.makeText(SignUpActivity.this, "Error occurred, please try again", Toast.LENGTH_LONG).show();
                        hideProgress();
                    }
                }
        );
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
