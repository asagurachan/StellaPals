package com.stella.pals.frontend.register;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.telephony.TelephonyManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.stella.pals.R;
import com.stella.pals.frontend.base.BaseActivity;
import com.stella.pals.utils.StringUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Arrays;

@EActivity(R.layout.activity_register)
public class RegisterActivity extends BaseActivity {

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

    @Click(R.id.btn_register)
    protected void registerOnClick() {
        if (checkFields()) {
            // TODO: Implement registration
        }
    }

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

}
