package com.example.amrarafa.zgo.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amrarafa.zgo.R;

public class SignInActivity extends AppCompatActivity {

    EditText username;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        username=(EditText)findViewById(R.id.username_edittext);
        password=(EditText)findViewById(R.id.password_edittext);
    }

    public void SignIn(View view){

        if (username.getText().toString().equals("")|| password.getText().toString().equals("")){
            Toast.makeText(this,"wrond username or password",Toast.LENGTH_SHORT).show();
        }

        else {
            Intent intent = new Intent(this, MapNavigationActivity.class);
            SharedPreferences preferences= getApplicationContext().getSharedPreferences("mypref",MODE_PRIVATE);
            SharedPreferences.Editor editor= preferences.edit();
            editor.putString("username",username.getText().toString());
            editor.putString("user","thisUser@gmail.com");
            editor.commit();
            startActivity(intent);
        }
    }
}
