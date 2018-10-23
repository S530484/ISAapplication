package com.example.s530472.team01isanwmsu;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.IDataStore;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class EventFun extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    EditText edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_fun);
        Backendless.setUrl( Defaults.SERVER_URL );
        Backendless.initApp( getApplicationContext(), Defaults.APPLICATION_ID, Defaults.API_KEY );

        Button button = (Button) findViewById(R.id.pickbutton1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v4.app.DialogFragment datePicker=new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");
            }
        });
    }
    public void submitFunction(View v) {
        Intent sub = new Intent();
        setResult(333, sub);
        finish();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,i);
        c.set(Calendar.MONTH,i1);
        c.set(Calendar.DAY_OF_MONTH,i2);

        String currentdate = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        edt=(EditText) findViewById(R.id.edate);
        edt.setText(currentdate);

    }

    public void showEvent(View v)
    {
        final TextView evinfo=(TextView)findViewById(R.id.eventinfo);
        edt=(EditText)findViewById(R.id.edate);
        final String eventDate=edt.getText().toString();
        IDataStore<EventInfo> userStorage = Backendless.Data.of(EventInfo.class);
        DataQueryBuilder query = DataQueryBuilder.create();
        String value="event_date='"+eventDate+"'";
        query.setWhereClause(value);
        userStorage.find(query, new AsyncCallback<List<EventInfo>>() {

            @Override
            public void handleResponse(List<EventInfo> response) {
                Log.d("Printing : ", "user Details: " + response);

                String test = response.toString();
                Log.d("Printing : ", "user test: " + test);

                if (test.contains(eventDate)) evinfo.setText(test.substring(1,test.length()-1));
                else evinfo.setText("No events");
            }
            @Override
            public void handleFault(BackendlessFault fault) {
                Log.e("MYAPP", "Server reported an error " + fault.getMessage());
            }
        });
    }

}
