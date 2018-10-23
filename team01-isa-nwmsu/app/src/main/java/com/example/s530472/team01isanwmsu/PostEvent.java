package com.example.s530472.team01isanwmsu;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.text.DateFormat;
import java.util.Calendar;

public class PostEvent extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener{

    EditText date;
    EditText time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_event);
        Backendless.setUrl( Defaults.SERVER_URL );
        Backendless.initApp( getApplicationContext(), Defaults.APPLICATION_ID, Defaults.API_KEY );

        Button button = (Button) findViewById(R.id.pickbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v4.app.DialogFragment datePicker=new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");
            }
        });

        Button tbutton = (Button) findViewById(R.id.timebutton);
        tbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v4.app.DialogFragment timePicker=new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"time picker");
            }
        });
    }
    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, i);
        c.set(Calendar.MONTH, i1);
        c.set(Calendar.DAY_OF_MONTH, i2);

        String currentdate = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        date = (EditText) findViewById(R.id.dateEd);
        date.setText(currentdate);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        time=(EditText) findViewById(R.id.timeEd);
        time.setText(i+":"+i1);
    }

    public void submitFunction(View v) {
        EditText name=(EditText) findViewById(R.id.nameEd);
        EditText date=(EditText) findViewById(R.id.dateEd);
        EditText time=(EditText) findViewById(R.id.timeEd);
        EditText duration=(EditText) findViewById(R.id.durationEd);
        EditText location=(EditText) findViewById(R.id.locationEd);
        EditText desc=(EditText) findViewById(R.id.descriptioned);

        if(name.getText().toString().trim().equals(""))
        {
            Toast.makeText(getApplicationContext(), "Please enter the name of the event", Toast.LENGTH_SHORT).show();
        }
        if(date.getText().toString().trim().equals(""))
        {
            Toast.makeText(getApplicationContext(), "Please enter the date of the event", Toast.LENGTH_SHORT).show();
        }
        if(time.getText().toString().trim().equals(""))
        {
            Toast.makeText(getApplicationContext(), "Please enter the time the event takes place", Toast.LENGTH_SHORT).show();
        }
        if(duration.getText().toString().trim().equals(""))
        {
            Toast.makeText(getApplicationContext(), "Please enter the event duration", Toast.LENGTH_SHORT).show();
        }
        if(location.getText().toString().trim().equals(""))
        {
            Toast.makeText(getApplicationContext(), "Please enter event location", Toast.LENGTH_SHORT).show();
        }
        if(desc.getText().toString().trim().equals(""))
        {
            Toast.makeText(getApplicationContext(), "Please enter event location", Toast.LENGTH_SHORT).show();
        }
        if(!name.getText().toString().trim().equals("")&&!date.getText().toString().trim().equals("")&&
                !time.getText().toString().trim().equals("")&&!duration.getText().toString().trim().equals("")&&
                !location.getText().toString().trim().equals(""))
        {
            EventInfo event=new EventInfo();
            event.event_name=name.getText().toString().trim();
            event.event_date=date.getText().toString().trim();
            event.duration=duration.getText().toString().trim();
            event.event_time=time.getText().toString().trim();
            event.location=location.getText().toString().trim();
            event.event_description=desc.getText().toString();

            Backendless.Data.of(EventInfo.class).save(event, new AsyncCallback<EventInfo>() {
                @Override
                public void handleResponse(EventInfo response) {
                    Log.d("DB", "Inserted values into table" + response);
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Log.e("MYAPP", "Server reported an error " + fault.getMessage());
                }
            });
            Intent sub = new Intent();
            setResult(45,sub);
            Toast.makeText(getApplicationContext(), "Event Posted", Toast.LENGTH_SHORT).show();
            finish();
        }
    }


}
