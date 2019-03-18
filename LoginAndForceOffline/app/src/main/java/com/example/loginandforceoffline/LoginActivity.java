package com.example.loginandforceoffline;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {
    private EditText accountEdit = null;
    private EditText passwordEdit = null;

    private CheckBox checkBoxRemeberPassword = null;
    private Button btnLogin = null;

    private SharedPreferences sharedPreferences = null;
    private SharedPreferences.Editor editor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        accountEdit =findViewById(R.id.account_edittext);
        passwordEdit = findViewById(R.id.password_edittext);
        btnLogin = findViewById(R.id.btn_login);
        checkBoxRemeberPassword = findViewById(R.id.checkbox_remeber_password);

        boolean isRemeber = sharedPreferences.getBoolean("remeber_password", false);

        if (isRemeber){
            String account = sharedPreferences.getString("account", "");
            String password = sharedPreferences.getString("password", "");

            accountEdit.setText(account);
            passwordEdit.setText(password);

            checkBoxRemeberPassword.setChecked(true);
        }


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();

                if (AccountUtil.isVild(account, password)){
                    editor = sharedPreferences.edit();
                    if (checkBoxRemeberPassword.isChecked()){
                        editor.putBoolean("remeber_password", true);
                        editor.putString("account", account);
                        editor.putString("password", password);
                    }else{
                        editor.clear();
                    }
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "account or password is invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
