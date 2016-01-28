package com.stella.pals.frontend.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.stella.pals.R;
import com.stella.pals.frontend.MainActivity;
import com.stella.pals.frontend.base.BaseActivity;
import com.stella.pals.frontend.base.BaseApplication;
import com.stella.pals.frontend.register.RegisterActivity;
import com.stella.pals.jobmanager.LoginJob;
import com.stella.pals.utils.StringUtil;

import org.jsoup.nodes.Document;

import de.greenrobot.event.EventBus;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_login;
    }

    @Override
    protected void initVariables() {
    }

    @Override
    protected void initListeners() {
        final TextInputLayout tilUsername = (TextInputLayout) findViewById(R.id.til_username);
        final TextInputLayout tilPassword = (TextInputLayout) findViewById(R.id.til_password);
        EditText etUsername = tilUsername.getEditText();
        EditText etPassword = tilPassword.getEditText();
        Button btnLogin = (Button) findViewById(R.id.btn_login);
        Button btnRegister = (Button) findViewById(R.id.btn_register);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEditTexts(tilUsername, tilPassword)) {
                    assert tilUsername.getEditText() != null;
                    String username = tilUsername.getEditText().getText().toString();
                    assert tilPassword.getEditText() != null;
                    String password = tilPassword.getEditText().getText().toString();

                    BaseApplication.getInstance().getNetworkJobManager().addJob(new LoginJob(username, password));
                }
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                ActivityCompat.startActivity(LoginActivity.this, intent, null);
            }
        });

        if (etUsername != null) {
            etUsername.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (tilUsername.isErrorEnabled()) {
                        tilUsername.setErrorEnabled(false);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
        if (etPassword != null) {
            etPassword.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (tilPassword.isErrorEnabled()) {
                        tilPassword.setErrorEnabled(false);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    private boolean checkEditTexts(TextInputLayout username, TextInputLayout password) {
        boolean noError = true;
        String usernameStr = "", passwordStr = "";
        EditText etUsername = username.getEditText();
        EditText etPassword = password.getEditText();

        if (etUsername != null && etPassword != null) {
            usernameStr = etUsername.getText().toString();
            passwordStr = etPassword.getText().toString();
        }

        if (StringUtil.isEmpty(usernameStr)) {
            username.setError(getString(R.string.em_please_enter_your_username_or_email));
            noError = false;
        }

        if (StringUtil.isEmpty(passwordStr)) {
            password.setError(getString(R.string.em_please_enter_a_password));
            noError = false;
        }

        return noError;
    }

    public void onEvent(Document document) {
        if (document.getElementById("topLogin") != null) {
            // Invalid username or password
            final TextInputLayout tilUsername = (TextInputLayout) findViewById(R.id.til_username);
            final TextInputLayout tilPassword = (TextInputLayout) findViewById(R.id.til_password);
            tilUsername.setError(getString(R.string.em_invalid_username_or_email));
            tilPassword.setError(getString(R.string.em_invalid_password));
        } else {
            // Logged in
            Intent intent = new Intent(this, MainActivity.class);
            ActivityCompat.startActivity(this, intent, null);
            ActivityCompat.finishAffinity(this);
        }
    }

    public void onEvent(Boolean success) {
        if (success) {
            showDialog();
        } else {
            dismissDialog();
        }
    }
}
