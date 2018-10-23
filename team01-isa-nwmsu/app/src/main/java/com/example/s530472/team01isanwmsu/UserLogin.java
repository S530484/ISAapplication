package com.example.s530472.team01isanwmsu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class UserLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
    }

    public void carRide(View v){
        Intent car = new Intent(this,CarRide.class);
        startActivityForResult(car,123);
    }
    public void eventFun(View v){
        Intent event = new Intent(this,EventFun.class);
        startActivityForResult(event,123);
    }
    public void chatBox(View v){
        Intent chat = new Intent(this,ChatBox.class);
        startActivityForResult(chat,123);
    }

    public void feedback(View v){
        Intent fd = new Intent(this,FeedbackForm.class);
        startActivityForResult(fd,123);
    }

    public void backFunction(View v) {
        Intent prev = new Intent();
        setResult(333, prev);
        finish();
    }
}
