package com.stella.pals.frontend.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.stella.pals.R;
import com.stella.pals.backend.api.APIConstants;
import com.stella.pals.backend.api.APIManager;
import com.stella.pals.backend.api.APIParams;
import com.stella.pals.frontend.MainActivity;
import com.stella.pals.frontend.base.BaseActivity;
import com.stella.pals.frontend.register.RegisterActivity;
import com.stella.pals.utils.ToastUtil;

public class LoginActivity extends BaseActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnRegister;

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
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);

        btnLogin = (Button) findViewById(R.id.btn_login);
        btnRegister = (Button) findViewById(R.id.btn_register);
    }

    @Override
    protected void initListeners() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new APIManager(LoginActivity.this, APIConstants.LOGIN,
                        APIParams.login(etUsername.getText().toString(),
                                etPassword.getText().toString())) {
                    @Override
                    public void onPostTask() {
                        if (mDocumentSoup.getElementById("topLogin") != null) {
                            // Invalid username or password
                            ToastUtil.makeShortToast(LoginActivity.this, getString(R.string.em_invalid_username_or_password));
                        } else {
                            // Logged in
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            ActivityCompat.startActivity(LoginActivity.this, intent, null);
                            ActivityCompat.finishAffinity(LoginActivity.this);
                        }
                    }

                    @Override
                    public void onPostFailTask() {
                        ToastUtil.makeShortToast(LoginActivity.this, "Died");
                    }
                }.setShowOverlay(true)
                        .execute();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                ActivityCompat.startActivity(LoginActivity.this, intent, null);
            }
        });
    }
}
