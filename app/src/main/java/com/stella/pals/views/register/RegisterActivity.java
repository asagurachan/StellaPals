package com.stella.pals.views.register;

import android.content.Context;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.telephony.TelephonyManager;
import android.transition.Transition;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.stella.pals.R;
import com.stella.pals.utils.StringUtil;
import com.stella.pals.views.base.BaseActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Arrays;

@EActivity(R.layout.activity_register)
public class RegisterActivity extends BaseActivity {

    //region ViewByIds
    @ViewById(R.id.til_username)
    TextInputLayout tilUsername;
    @ViewById(R.id.til_email)
    TextInputLayout tilEmail;
    @ViewById(R.id.til_password)
    TextInputLayout tilPassword;
    @ViewById(R.id.rg_sex)
    RadioGroup rgSex;
    @ViewById(R.id.rb_male)
    RadioButton rbMale;
    @ViewById(R.id.rb_female)
    RadioButton rbFemale;
    @ViewById(R.id.s_country)
    Spinner sCountry;
    @ViewById(R.id.btn_register)
    Button btnRegister;
    //endregion

    //region AfterViews
    @AfterViews
    protected void initCountrySpinner() {
        ArrayAdapter<String> countryArray = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.country_array));
        countryArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sCountry.setAdapter(countryArray);

        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String countryIso = telephonyManager.getSimCountryIso().toUpperCase();

        if (StringUtil.isEmpty(countryIso)) {
            switch (telephonyManager.getPhoneType()) {
                case TelephonyManager.PHONE_TYPE_GSM:
                case TelephonyManager.PHONE_TYPE_SIP:
                    countryIso = telephonyManager.getNetworkCountryIso();
                    break;
            }
        }

        if (StringUtil.isNotEmpty(countryIso)) {
            String[] countryCodeArray = getResources().getStringArray(R.array.country_code_array);
            int position = Arrays.asList(countryCodeArray).indexOf(countryIso);
            if (position != -1) {
                sCountry.setSelection(position);
            }
        }
    }

    @AfterViews
    protected void initSex() {
        rgSex.check(rbMale.getId());
    }
    //endregion

    //region OnClicks
    @Click(R.id.btn_register)
    protected void registerOnClick() {
        if (checkFields()) {
            // TODO: Implement registration
        }
    }
    //endregion

    private boolean checkFields() {
        boolean fieldCheck = true;
        EditText etUsername = tilUsername.getEditText();
        EditText etEmail = tilEmail.getEditText();
        EditText etPassword = tilPassword.getEditText();
        String username = "";
        String email = "";
        String password = "";

        if (etUsername != null) {
            username = etUsername.getText().toString();
        }
        if (etEmail != null) {
            email = etEmail.getText().toString();
        }
        if (etPassword != null) {
            password = etPassword.getText().toString();
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            fieldCheck = false;
            tilEmail.setError(getString(R.string.em_please_enter_a_valid_email));
        }
        if (StringUtil.isEmpty(email)) {
            fieldCheck = false;
            tilEmail.setError(getString(R.string.em_please_enter_a_email));
        }
        if (StringUtil.isEmpty(username)) {
            fieldCheck = false;
            tilUsername.setError(getString(R.string.em_please_enter_a_username));
        }
        if (StringUtil.isEmpty(password)) {
            fieldCheck = false;
            tilPassword.setError(getString(R.string.em_please_enter_a_password));
        }
        if (!rbMale.isChecked() && !rbFemale.isChecked()) {
            fieldCheck = false;
        }
        return fieldCheck;
    }

    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(enterAnim, exitAnim);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getEnterTransition().addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {
                    tilUsername.setHint("");
                    tilPassword.setHint("");
                    tilEmail.setHint("");
                }

                @Override
                public void onTransitionEnd(Transition transition) {
                    tilUsername.setHint(getString(R.string.h_username));
                    tilPassword.setHint(getString(R.string.h_password));
                    tilEmail.setHint(getString(R.string.h_email));
                }

                @Override
                public void onTransitionCancel(Transition transition) {

                }

                @Override
                public void onTransitionPause(Transition transition) {

                }

                @Override
                public void onTransitionResume(Transition transition) {

                }
            });

            getWindow().getReturnTransition().addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {
                    tilUsername.setHint("");
                    tilPassword.setHint("");
                    tilEmail.setHint("");
                }

                @Override
                public void onTransitionEnd(Transition transition) {
                }

                @Override
                public void onTransitionCancel(Transition transition) {

                }

                @Override
                public void onTransitionPause(Transition transition) {

                }

                @Override
                public void onTransitionResume(Transition transition) {

                }
            });
        }
    }


}
