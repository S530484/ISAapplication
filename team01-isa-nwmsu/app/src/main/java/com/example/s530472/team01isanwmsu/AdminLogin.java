package com.example.s530472.team01isanwmsu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AdminLogin extends AppCompatActivity {
//This is admin login
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
    }
    public void postEvent(View v){
        Intent post = new Intent(this,PostEvent.class);
        startActivityForResult(post,123);
    }
    public void assign(View v){
        Intent asn = new Intent(this,AssignPickup.class);
        startActivityForResult(asn,123);
    }
    public void goBack(View v){
        Intent bk= new Intent();
        setResult(333,bk);
        finish();
    }
}
