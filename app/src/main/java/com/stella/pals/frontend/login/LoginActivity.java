package com.stella.pals.frontend.login;

import android.support.design.widget.TextInputLayout;
import android.widget.Button;
import android.widget.EditText;

import com.stella.pals.R;
import com.stella.pals.event.FailedEvent;
import com.stella.pals.event.IntentEvent;
import com.stella.pals.event.SuccessEvent;
import com.stella.pals.frontend.MainActivity_;
import com.stella.pals.frontend.base.BaseActivity;
import com.stella.pals.frontend.base.BaseApplication;
import com.stella.pals.frontend.register.RegisterActivity_;
import com.stella.pals.interfaces.MyPrefs_;
import com.stella.pals.job.JobCodes;
import com.stella.pals.job.LoginJob;
import com.stella.pals.utils.StringUtil;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.TextChange;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.greenrobot.eventbus.Subscribe;

@EActivity(R.layout.activity_login)
public class LoginActivity extends BaseActivity {

    @App
    BaseApplication application;

    @Pref
    MyPrefs_ myPrefs;

    @ViewById(R.id.til_username)
    TextInputLayout tilUsername;
    @ViewById(R.id.til_password)
    TextInputLayout tilPassword;
    @ViewById(R.id.btn_login)
    Button btnLogin;
    @ViewById(R.id.btn_register)
    Button btnRegister;

    @AfterInject
    public void clearCookies() {
//        application.clearCookies();
    }

    @Click(R.id.btn_login)
    public void loginOnClick() {
        if (checkEditTexts(tilUsername, tilPassword)) {
            String username = "";
            String password = "";
            if (tilUsername.getEditText() != null) {
                username = tilUsername.getEditText().getText().toString().trim();
            }
            if (tilPassword.getEditText() != null) {
                password = tilPassword.getEditText().getText().toString();
            }

            showProgressOverlay(getString(R.string.pt_logging_in));
            BaseApplication.getInstance().getNetworkJobManager().addJob(new LoginJob(username, password));
        }
    }

    @Click(R.id.btn_register)
    public void registerOnClick() {
        onIntentEvent(new IntentEvent(RegisterActivity_.intent(this)));
    }

    @TextChange(R.id.et_username)
    public void usernameOnTextChange(CharSequence s, int start, int before, int count) {
        if (tilUsername.isErrorEnabled()) {
            tilUsername.setErrorEnabled(false);
        }
    }

    @TextChange(R.id.et_password)
    public void passwordOnTextChange(CharSequence s, int start, int before, int count) {
        if (tilPassword.isErrorEnabled()) {
            tilPassword.setErrorEnabled(false);
        }
    }

    @Subscribe
    public void onSuccessEvent(SuccessEvent event) {
        if (event.code == JobCodes.LOGIN) {
            String username = "";
            if (tilUsername.getEditText() != null) {
                username = tilUsername.getEditText().getText().toString().trim();
            }
            myPrefs.lastUser().put(username);

            onIntentEvent(new IntentEvent(MainActivity_.intent(this), true));
            dismissProgressOverlay();
        }
    }

    @Subscribe
    public void onFailedEvent(FailedEvent event) {
        if (event.code == JobCodes.LOGIN) {
            tilUsername.setError(getString(R.string.em_invalid_username_or_email));
            tilPassword.setError(getString(R.string.em_invalid_password));
            dismissProgressOverlay();
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

}
