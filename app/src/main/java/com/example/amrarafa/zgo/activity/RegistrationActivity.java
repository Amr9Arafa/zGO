package com.example.amrarafa.zgo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amrarafa.zgo.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    EditText emailEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        emailEditText=(EditText)findViewById(R.id.email_edittext);
    }

    public void register(View view){

        if (isEmailValid(emailEditText.getText().toString())){

            //check if user already exist
            //check if user name and password are not empty

            Intent intent= new Intent(RegistrationActivity.this,MapNavigationActivity.class);
            startActivity(intent);



        }
        else{
            //invalid email
            Toast.makeText(this,"Invalid Email!!",Toast.LENGTH_SHORT).show();
        }

    }
    public void signIn(View view){

        Intent intent= new Intent(RegistrationActivity.this,SignInActivity.class);
        startActivity(intent);
    }


    public boolean isEmailValid(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
    }
}
