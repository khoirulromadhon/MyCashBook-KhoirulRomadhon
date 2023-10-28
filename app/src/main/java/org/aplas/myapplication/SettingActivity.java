package org.aplas.myapplication;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {
    Button btnsimpan,btnkembali;
    EditText pass, newpass, username;
    SqliteHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        username = findViewById(R.id.edittextUsername);
        pass = findViewById(R.id.edittextPassword);
        newpass  = findViewById(R.id.edittextPassword2);
        btnsimpan = findViewById(R.id.btnSave);
        btnkembali = findViewById(R.id.btnKembali);
        DB = new SqliteHelper(this);

        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pass.getText().toString().equals("") || newpass.getText().toString().equals("")) {
                    Toast.makeText(SettingActivity.this, "All fields Required", Toast.LENGTH_SHORT).show();
                } else {

                    Boolean checkuserpass = DB.checkUsernamePassword(username.getText().toString(),pass.getText().toString());

                    if (checkuserpass==false) {
                        Toast.makeText(SettingActivity.this, "Your Password is wrong", Toast.LENGTH_SHORT).show();

                    } else {
                        Boolean isUpdated = DB.updatePass(username.getText().toString(), newpass.getText().toString());

                        if(isUpdated) {
                            Toast.makeText(SettingActivity.this, "Your Password already changed", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                        } else {
                            Toast.makeText(SettingActivity.this, "Your Password failed to change", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        btnkembali.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SettingActivity.this, BerandaActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
