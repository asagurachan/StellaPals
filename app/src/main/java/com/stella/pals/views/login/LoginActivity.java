package com.stella.pals.views.login;

import android.support.design.widget.TextInputLayout;
import android.widget.Button;
import android.widget.EditText;

import com.stella.pals.R;
import com.stella.pals.events.FailedEvent;
import com.stella.pals.events.IntentEvent;
import com.stella.pals.events.SuccessEvent;
import com.stella.pals.interfaces.MyPrefs_;
import com.stella.pals.jobs.JobCodes;
import com.stella.pals.jobs.LoginJob;
import com.stella.pals.utils.StringUtil;
import com.stella.pals.views.MainActivity_;
import com.stella.pals.views.base.BaseActivity;
import com.stella.pals.views.base.BaseApplication;
import com.stella.pals.views.register.RegisterActivity_;

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

    //region ViewByIds
    @ViewById(R.id.til_username)
    TextInputLayout tilUsername;
    @ViewById(R.id.til_password)
    TextInputLayout tilPassword;
    @ViewById(R.id.btn_login)
    Button btnLogin;
    @ViewById(R.id.btn_register)
    Button btnRegister;
    //endregion

    @AfterInject
    public void clearCookies() {
//        application.clearCookies();
    }

    //region OnClicks
    @Click(R.id.btn_login)
    public void loginOnClick() {
        if (checkEditTexts()) {
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
    //endregion

    //region OnTextChanges
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
    //endregion

    private boolean checkEditTexts() {
        boolean noError = true;
        String usernameStr = "";
        String passwordStr = "";
        EditText etUsername = tilUsername.getEditText();
        EditText etPassword = tilPassword.getEditText();

        if (etUsername != null && etPassword != null) {
            usernameStr = etUsername.getText().toString();
            passwordStr = etPassword.getText().toString();
        }

        if (StringUtil.isEmpty(usernameStr)) {
            tilUsername.setError(getString(R.string.em_please_enter_your_username_or_email));
            noError = false;
        }

        if (StringUtil.isEmpty(passwordStr)) {
            tilPassword.setError(getString(R.string.em_please_enter_a_password));
            noError = false;
        }

        return noError;
    }

    //region Events
    @Subscribe
    @Override
    protected void onSuccessEvent(SuccessEvent event) {
        if (event.code == JobCodes.LOGIN) {
            String username = "";
            String password = "";
            if (tilUsername.getEditText() != null) {
                username = tilUsername.getEditText().getText().toString().trim();
            }
            if (tilPassword.getEditText() != null) {
                password = tilPassword.getEditText().getText().toString().trim();
            }
            myPrefs.lastUser().put(username);
            myPrefs.pass().put(password);

            onIntentEvent(new IntentEvent(MainActivity_.intent(this), true));
            dismissProgressOverlay();
        }
    }

    @Subscribe
    @Override
    protected void onFailedEvent(FailedEvent event) {
        if (event.code == JobCodes.LOGIN) {
            tilUsername.setError(getString(R.string.em_invalid_username_or_email));
            tilPassword.setError(getString(R.string.em_invalid_password));
            dismissProgressOverlay();
        }
    }
    //endregion

}
