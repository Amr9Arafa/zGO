package com.example.amrarafa.zgo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.amrarafa.zgo.R;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }

    public void SignIn(View view){
        Intent intent=new Intent(this,MapNavigationActivity.class);
        startActivity(intent);
    }
}
