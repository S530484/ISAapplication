package com.example.s530472.team01isanwmsu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.IDataStore;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroupType;
    private RadioButton radioButtonType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Backendless.setUrl( Defaults.SERVER_URL );
        Backendless.initApp( getApplicationContext(), Defaults.APPLICATION_ID, Defaults.API_KEY );
    }
    public void loginFunction(View v){
        radioGroupType = (RadioGroup) findViewById(R.id.radioGroup);
        int select_id=radioGroupType.getCheckedRadioButtonId();
        if(select_id!=-1)
        {
            radioButtonType=(RadioButton)findViewById(select_id);
            final String s=radioButtonType.getText().toString();
            final EditText email = (EditText) findViewById(R.id.emailid);
            final String emailID = email.getText().toString();
            final EditText password = (EditText) findViewById(R.id.password);
            final String passcode = password.getText().toString();

            if (emailID.length() > 0 && !emailID.matches("\\s+")) {

                if (passcode.length() > 0 && !passcode.matches("\\s+")) {

                    IDataStore<SignupInfo> userStorage = Backendless.Data.of(SignupInfo.class);
                    DataQueryBuilder query = DataQueryBuilder.create();
                    String value="email='"+emailID+"'";
                    query.setWhereClause(value);
                    userStorage.find(query, new AsyncCallback<List<SignupInfo>>() {

                        @Override
                        public void handleResponse(List<SignupInfo> response) {
                            Log.d("Printing : ", "user Details: " + response);

                            String test = response.toString();
                            Log.d("Printing : ", "user test: " + test);

                            switch (s) {

                                case "Admin":
                                    if (emailID.equals("isa@gmail.com") && passcode.equals("Isa@9") ){
                                        Intent i = new Intent(getApplicationContext(), AdminLogin.class);
                                        startActivity(i);
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(), "Please enter valid credentials", Toast.LENGTH_SHORT).show();
                                    }

                                    break;
                                case "User":
                                    if ((test.contains(emailID) && test.contains(passcode))){
                                        Intent i1 = new Intent(getApplicationContext(), UserLogin.class);
                                        i1.putExtra("email",emailID);
                                        startActivity(i1);
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(), "Please enter valid credentials", Toast.LENGTH_SHORT).show();
                                    }
                                    break;
                            }

                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Log.e("MYAPP", "Server reported an error " + fault.getMessage());
                        }
                    });

                }
                else {
                    password.setError("Please enter password");
                }

            }

            else {
                email.setError("Please enter email address");
            }}
        else {
            Toast.makeText(MainActivity.this,
                    "Please select either Admin or User", Toast.LENGTH_SHORT).show();
        }

    }

    public void signUpFunction(View v){
        Intent usernew = new Intent(this,UserSignup.class);
        startActivity(usernew);

    }

}