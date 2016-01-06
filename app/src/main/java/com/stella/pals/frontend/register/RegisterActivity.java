package com.stella.pals.frontend.register;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
//        etUsername = (MaterialEditText) findViewById(R.id.et_username);
//        etEmail = (MaterialEditText) findViewById(R.id.et_email);
//        etPassword = (MaterialEditText) findViewById(R.id.et_password);
        rbMale = (RadioButton) findViewById(R.id.rb_male);
        rbFemale = (RadioButton) findViewById(R.id.rb_female);
        sCountry = (Spinner) findViewById(R.id.s_country);
        btnRegister = (Button) findViewById(R.id.btn_register);
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
//                            Elements elements = mDocumentSoup.getElementsByClass("suErr");
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
//                    }.setShowOverlay(true)
//                            .execute();
//                } else {
//
//                }
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean checkFields() {
        boolean fieldCheck = true;
        final TextInputLayout tilUsername = (TextInputLayout) findViewById(R.id.til_username);
        final TextInputLayout tilEmail = (TextInputLayout) findViewById(R.id.til_email);
        final TextInputLayout tilPassword = (TextInputLayout) findViewById(R.id.til_password);
        String username = "";
        String email = "";
        String password = "";

        try {
            username = tilUsername.getEditText().getText().toString();
            email = tilEmail.getEditText().getText().toString();
            password = tilPassword.getEditText().getText().toString();
        } catch (NullPointerException e) {
            Log.e(TAG, e.getMessage(), e);
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
