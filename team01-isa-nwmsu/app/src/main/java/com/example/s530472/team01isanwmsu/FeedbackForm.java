package com.example.s530472.team01isanwmsu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.IDataStore;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.security.Signature;
import java.util.List;

public class FeedbackForm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_form);
        Backendless.setUrl( Defaults.SERVER_URL );
        Backendless.initApp( getApplicationContext(), Defaults.APPLICATION_ID, Defaults.API_KEY );
    }

    public void goBack(View v){

        EditText phonenumber = (EditText) findViewById(R.id.contactp);
        EditText msg = (EditText) findViewById(R.id.suggestions);

        final String phone= phonenumber.getText().toString();
        final String mssg = msg.getText().toString();


        if (phone.trim().equals("")||phone.matches("\\s+")) {
            phonenumber.setError("Enter Phone Number");
        }

        if (mssg.equals("")||mssg.matches("\\s+")||mssg.isEmpty()) {
            msg.setError("Enter Feedback");
        }
        else
        {
            IDataStore<SignupInfo> userStorage = Backendless.Data.of(SignupInfo.class);
            DataQueryBuilder query = DataQueryBuilder.create();
            String value="phonenumber='"+phone+"'";
            query.setWhereClause(value);
            Log.d("print","phonenum"+value);
            userStorage.find(query, new AsyncCallback<List<SignupInfo>>() {
                @Override
                public void handleResponse(List<SignupInfo> response) {
                    Log.d("print","phonenum"+response);
                    String num = response.toString();
                    if(num.contains(phone)){
                        FeedbackInfo feedback = new FeedbackInfo();
                        feedback.phonenumber=phone;
                        feedback.feedbackmsg=mssg;
                        Backendless.Data.of( FeedbackInfo.class ).save(feedback, new AsyncCallback<FeedbackInfo>() {

                            @Override
                            public void handleResponse(FeedbackInfo response) {
                                Log.d("DB","Inserted values into table"+response);
                                Toast.makeText(getApplicationContext(),"Submitted...Thank you for your feedback",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                Log.e( "MYAPP", "Server error" + fault.getMessage() );
                            }
                        });
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Please Enter the user Number registered",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void handleFault(BackendlessFault fault) {

                    Log.e("MYAPP", "Server reported an error " + fault.getMessage());
                }

            });
            Intent go = new Intent();
            setResult(333,go);
            finish();

        }
    }

}
