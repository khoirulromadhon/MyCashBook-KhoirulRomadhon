package org.aplas.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import android.content.Intent;

public class RegisterActivity extends AppCompatActivity {
    EditText editTextUserName,editTextPassword,ulangiPassword;
    Button daftar;
    SqliteHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextUserName = findViewById(R.id.edittextUsername);
        editTextPassword = findViewById(R.id.edittextPassword);
        ulangiPassword = findViewById(R.id.edittextPassword2);
        daftar = findViewById(R.id.btnRegister);
        DB = new SqliteHelper(this);

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = editTextUserName.getText().toString();
                String password = editTextPassword.getText().toString();
                String ulangpassword = ulangiPassword.getText().toString();

                if (TextUtils.isEmpty(username) ||TextUtils.isEmpty(password))
                    Toast.makeText(RegisterActivity.this, "All fields Required", Toast.LENGTH_SHORT).show();
                else {
                    if (password.equals(ulangpassword)){
                        Boolean checkuser = DB.checkUsername(username);
                        if (checkuser==false){
                            Boolean insert = DB.insertData(username,password);
                            if (insert==true){
                                Toast.makeText(RegisterActivity.this, "Register Success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(RegisterActivity.this, "User already Exists", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(RegisterActivity.this, "Password Failed", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
    public void backLogin(View view){
        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(i);
    }
}