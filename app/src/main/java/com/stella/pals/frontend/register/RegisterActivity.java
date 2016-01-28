package com.stella.pals.frontend.register;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.stella.pals.R;
import com.stella.pals.frontend.base.BaseActivity;
import com.stella.pals.utils.StringUtil;

public class RegisterActivity extends BaseActivity {

    private RadioButton rbMale;
    private RadioButton rbFemale;
    private Spinner sCountry;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_register;
    }

    @Override
    protected void initVariables() {
        rbMale = (RadioButton) findViewById(R.id.rb_male);
        rbFemale = (RadioButton) findViewById(R.id.rb_female);
        sCountry = (Spinner) findViewById(R.id.s_country);
        btnRegister = (Button) findViewById(R.id.btn_register);
    }

    @Override
    protected void initLayout(Bundle savedInstanceState) {
        super.initLayout(savedInstanceState);
        ArrayAdapter<String> countryArray= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.country_array));
        countryArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sCountry.setAdapter(countryArray);
    }

    @Override
    protected void initListeners() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkFields();
            }
        });
//        btnRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (checkFields()) {
//                    new APIManager(RegisterActivity.this, APIConstants.SIGNUP,
//                            APIParams.signup(etUsername.getText().toString(),
//                                    etEmail.getText().toString(),
//                                    etPassword.getText().toString(),
//                                    "", "", "", "")) {
//                        @Override
//                        public void onPostTask() {
//                            Elements elements = documentSoup.getElementsByClass("suErr");
//
//                            for (Element ele : elements) {
//                                if (!ele.getElementsByAttributeValue("name", "username").isEmpty()) {
//                                    etUsername.setError(getString(R.string.em_username_has_been_taken));
//                                }
//
//                                if (!ele.getElementsByAttributeValue("name", "email").isEmpty()) {
//                                    etEmail.setError(getString(R.string.em_email_has_been_used_before));
//                                }
//
//                                if (!ele.getElementsByAttributeValue("name", "password").isEmpty()) {
//                                    etPassword.setError(getString(R.string.em_invalid_password));
//                                }
//
//                                // Birthday
//                                if (!ele.getElementsByAttributeValue("name", "day").isEmpty()) {
////                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////                                        etPassword.setTextColor(getResources().getColor(
////                                                R.color.red, null));
////                                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
////                                        etPassword.setTextColor(getResources().getColor(
////                                                R.color.red));
////                                    }
//                                } else {
////                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////                                        etPassword.setTextColor(getResources().getColor(
////                                                R.color.text_primary, null));
////                                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
////                                        etPassword.setTextColor(getResources().getColor(
////                                                R.color.text_primary));
////                                    }
//                                }
//
//                                // Sex
//                                if (!ele.getElementsByAttributeValue("name", "sex").isEmpty()) {
////                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////                                        etPassword.setTextColor(getResources().getColor(
////                                                R.color.red, null));
////                                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
////                                        etPassword.setTextColor(getResources().getColor(
////                                                R.color.red));
////                                    }
//                                } else {
////                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////                                        etPassword.setTextColor(getResources().getColor(
////                                                R.color.text_primary, null));
////                                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
////                                        etPassword.setTextColor(getResources().getColor(
////                                                R.color.text_primary));
////                                    }
//                                }
//
//                                // Country
//                                if (!ele.getElementsByAttributeValue("name", "country").isEmpty()) {
////                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////                                        etPassword.setTextColor(getResources().getColor(
////                                                R.color.red, null));
////                                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
////                                        etPassword.setTextColor(getResources().getColor(
////                                                R.color.red));
////                                    }
//                                } else {
////                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////                                        etPassword.setTextColor(getResources().getColor(
////                                                R.color.text_primary, null));
////                                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
////                                        etPassword.setTextColor(getResources().getColor(
////                                                R.color.text_primary));
////                                    }
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onPostFailTask() {
//
//                        }
//                    }.setShowDialog(true)
//                            .execute();
//                } else {
//
//                }
//            }
//        });
    }

    private boolean checkFields() {
        boolean fieldCheck = true;
        final TextInputLayout tilUsername = (TextInputLayout) findViewById(R.id.til_username);
        final TextInputLayout tilEmail = (TextInputLayout) findViewById(R.id.til_email);
        final TextInputLayout tilPassword = (TextInputLayout) findViewById(R.id.til_password);
        EditText etUsername = tilUsername.getEditText();
        EditText etEmail = tilEmail.getEditText();
        EditText etPassword = tilPassword.getEditText();
        String username = "";
        String email = "";
        String password = "";

        if (etUsername != null) {
            username = etUsername.toString();
        }
        if (etEmail != null) {
            email = etEmail.toString();
        }
        if (etPassword != null) {
            password = etPassword.toString();
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
