package com.example.s530472.team01isanwmsu;

import android.content.Intent;
import android.graphics.RadialGradient;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UserSignup extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);
        Backendless.setUrl( Defaults.SERVER_URL );
        Backendless.initApp( getApplicationContext(), Defaults.APPLICATION_ID, Defaults.API_KEY );
    }

    public void submitFunction(View v) {
        EditText firstName = (EditText) findViewById(R.id.Fname);
        EditText lastName = (EditText) findViewById(R.id.Lname);
        EditText mail = (EditText) findViewById(R.id.email);
        EditText phNum = (EditText) findViewById(R.id.contact);
        EditText password = (EditText) findViewById(R.id.password);
        EditText confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        boolean emailCheck = android.util.Patterns.EMAIL_ADDRESS.matcher(mail.getText().toString().trim()).matches();

        // Check which radio button was clicked
//

        if (firstName.getText().toString().length() < 0) {
            firstName.setError("Please enter first name");
        }

        if (lastName.getText().toString().trim().equals("")) {
            lastName.setError("Please enter last name");
        }
        if (mail.getText().toString().trim().equals("")) {
            mail.setError("Email is required!");
        }
        if (emailCheck == false) {
            mail.setError("Enter your correct email address");
        }
        if (phNum.getText().toString().trim().equals("")) {
            phNum.setError("Phone number is required!");
        }
        if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Password doesn't match..re-confirm your password ", Toast.LENGTH_SHORT).show();
        }
        if(isValidPassword(password.getText().toString())==false){
            password.setError("Password must contain one Caps letter, number and special character");
        }
        if (firstName.getText().toString().length() > 0 && (!lastName.getText().toString().trim().equals("")) && (!mail.getText().toString().trim().equals("")) &&
                (!phNum.getText().toString().trim().equals("")) && emailCheck == true&&
                isValidPassword(password.getText().toString())==true) {
            SignupInfo registerUser = new SignupInfo();
            registerUser.fname = firstName.getText().toString();
            registerUser.lname = lastName.getText().toString();
            registerUser.email = mail.getText().toString();
            registerUser.phonenumber = phNum.getText().toString();
            registerUser.password = password.getText().toString();

            Backendless.Data.of(SignupInfo.class).save(registerUser, new AsyncCallback<SignupInfo>() {
                @Override
                public void handleResponse(SignupInfo response) {
                    Log.d("DB", "Inserted values into table" + response);
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Log.e("MYAPP", "Server reported an error " + fault.getMessage());
                }
            });
            Intent prev = new Intent(this, MainActivity.class);
            startActivity(prev);
            Toast.makeText(getApplicationContext(), "Sign up done successfully ", Toast.LENGTH_SHORT).show();
        }
    }

        public boolean isValidPassword(final String password) {

            Pattern pattern;
            Matcher matcher;

            final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

            pattern = Pattern.compile(PASSWORD_PATTERN);
            matcher = pattern.matcher(password);

            return matcher.matches();

        }

}
