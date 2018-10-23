package com.example.s530472.team01isanwmsu;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.IDataStore;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class AssignPickup extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    TextView details;
    ArrayList<String> list;
    String currentdate;
    String selectedData;
    String emailaddress;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_pickup);
        Backendless.setUrl( Defaults.SERVER_URL );
        Backendless.initApp( getApplicationContext(), Defaults.APPLICATION_ID, Defaults.API_KEY );

        Button button = (Button) findViewById(R.id.pickdate);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v4.app.DialogFragment datePicker=new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");
            }
        });

        list=new ArrayList<>();
        list.add("Karun  (682)201-8530");
        list.add("Naveen  (636)466-5584");
        list.add("Rohith  (660)233-2732");
        list.add("Yeshwanth   (660)528-0080");
        list.add("Naresh   (660)781-0987");
        list.add("Vipul   (648)678-0104");

        ListAdapter arrayAdapter;
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.BLUE);
                return view;
            }
        };

        ListView listItems;
        listItems = (ListView)findViewById(R.id.riderDetails);
        listItems.setAdapter(arrayAdapter);

        listItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectedData=list.get(position);
            }
        });
    }

    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, i);
        c.set(Calendar.MONTH, i1);
        c.set(Calendar.DAY_OF_MONTH, i2);

        currentdate = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
    }

    public void getFunction(View v)
    {
        details=(TextView)findViewById(R.id.userDetails);
        details.setSelected(true);

        if(currentdate!=null) {

            IDataStore<PickupInfo> userStorage = Backendless.Data.of(PickupInfo.class);
            DataQueryBuilder query = DataQueryBuilder.create();
            String value = "arrival_date='" + currentdate + "'";
            query.setWhereClause(value);
            userStorage.find(query, new AsyncCallback<List<PickupInfo>>() {

                @Override
                public void handleResponse(List<PickupInfo> response) {
                    Log.d("Printing : ", "user Details: " + response);

                    String test = response.toString();
                    Log.d("Printing : ", "user test: " + test);

                    if (test.contains("Emailid:")) {
//                    System.out.print(test.contains("Emailid:"));
                        int emindex = test.indexOf("Emailid:");
                        int start = emindex + "Emailid:".length();
                        int end = test.indexOf("Flight details:");
                        emailaddress = test.substring(start, end);
                    }

                    if (test.contains("Name:")) {
                        int emindex = test.indexOf("Name:");
                        int start = emindex + "Name:".length();
                        int end = test.indexOf("Arrival time:");
                        userName = test.substring(start, end);
                    }

                    if (test.contains(currentdate))
                        details.setText(test.substring(1, test.length() - 1));
                    else details.setText("No arrivals");
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Log.e("MYAPP", "Server reported an error " + fault.getMessage());
                }
            });
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Please pick a date to get the arrival details", Toast.LENGTH_SHORT).show();
        }

    }
    public void assignFunction(View v)
    {
        if(selectedData!=null && currentdate!=null && !details.getText().toString().equals("No arrivals")) {
            Intent chooser = null;
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setData(Uri.parse("mailto:"));
            String[] to = {emailaddress.trim()};
            intent.putExtra(Intent.EXTRA_EMAIL, to);
            intent.putExtra(Intent.EXTRA_SUBJECT, "Ride Details for your airport pickup");
            intent.putExtra(Intent.EXTRA_TEXT, "Hi " + userName + "," + "\n\nYou have been assigned a ride. Please find the " +
                    "details of the rider.\n\n" + "Rider Details: \n" + selectedData + "\n\nThank You\nAdmin");
            intent.setType("message/rfc822");
            chooser = Intent.createChooser(intent, "Send Email: \n");
            startActivity(chooser);
        }
        if(details.getText().toString().equals("No arrivals"))
        {
            Toast.makeText(getApplicationContext(), "There are no arrivals for the selected date please pick another date", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Please select a rider name to assign to the students", Toast.LENGTH_SHORT).show();
        }
    }

    public void goBack(View v) {
        Intent prev = new Intent();
        setResult(333, prev);
        finish();
    }

}
