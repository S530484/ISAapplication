package com.example.s530472.team01isanwmsu;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.text.DateFormat;
import java.util.Calendar;

public class CarRide extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener{

    EditText date;
    EditText time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_ride);

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
        date = (EditText) findViewById(R.id.editText4);
        date.setText(currentdate);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        time=(EditText) findViewById(R.id.editText5);
        time.setText(i+":"+i1);
    }


    public void submitFunction(View v) {
        EditText name = (EditText) findViewById(R.id.editText);
        String username = name.getText().toString();
        EditText email = (EditText) findViewById(R.id.editText2);
        String emailid = email.getText().toString();
        EditText phnnum= (EditText) findViewById(R.id.editText3);
        String num = phnnum.getText().toString();
        EditText date = (EditText) findViewById(R.id.editText4);
        String datee = date.getText().toString();
        EditText time = (EditText) findViewById(R.id.editText5);
        String atime = time.getText().toString();
        EditText ftime = (EditText) findViewById(R.id.editText6);
        String times = ftime.getText().toString();

        boolean emailCheck = android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches();

        if(username.equals(""))
        {
            name.setError("Please enter the name");
        }
        if(emailid.equals(""))
        {
            email.setError("Please enter a email id");
        }
        if (emailCheck == false) {
            email.setError("Enter your correct email address");
        }
        if(datee.equals(""))
        {
            date.setError("Please enter the date ");
        }
        if(num.equals(""))
        {
            phnnum.setError("Please enter the phone number");
        }
        if(atime.equals(""))
        {
            time.setError("Please enter the arrival time");
        }
        if(times.equals(""))
        {
            ftime.setError("Please enter the flight number");
        }
        if(!username.equals("")&&!emailid.equals("")&&!datee.equals("")&&!num.equals("")&&!atime.equals("")&&!times.equals(""))
        {
            PickupInfo pickuser = new PickupInfo();
            pickuser.student_name = username;
            pickuser.arrival_date = datee;
            pickuser.arrival_time = atime;
            pickuser.student_contact = num;
            pickuser.student_emailid = emailid;
            pickuser.flightnum = times;

            Backendless.Data.of(PickupInfo.class).save(pickuser, new AsyncCallback<PickupInfo>() {
                @Override
                public void handleResponse(PickupInfo response) {
                    Log.d("DB", "Inserted values into table" + response);
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Log.e("MYAPP", "Server reported an error " + fault.getMessage());
                }
            });
            Intent chooser=null;
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setData(Uri.parse("mailto:"));
            String[] to= {"meghanamayaluri@gmail.com"};
            intent.putExtra(Intent.EXTRA_EMAIL,to);
            intent.putExtra(Intent.EXTRA_SUBJECT,"Intimation of a new airport pickup");
            intent.putExtra(Intent.EXTRA_TEXT,"Hi Admin,"+"\n"+"\n \t \t This is "+ username +" just to notify you regarding my airport pickup.Please go through my details"+
                    "and provide me sufficient details who comes to pick me up so that I can be in track of the person."+"\n"+"\nMy Details:" +
                    "\n Arrival date: "+datee+"\n Arrival time: "+atime+"\n Flight Number: "+times+"\n\n"+"Thank You"+"\n"+username);
            intent.setType("message/rfc822");
            chooser=Intent.createChooser(intent,"Send Email");
            startActivity(chooser);
        }
    }

    public void backFunction(View v)
    {
        Intent sub = new Intent();
        setResult(333, sub);
        finish();
    }
}
