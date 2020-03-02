package com.example.calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.calendar.mydatabase.DBOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddEventActivity extends AppCompatActivity {

    private String time;
    TextView setEventTime;
    private String day;
    private String month;
    private String year;

//    ..................................................................................................................................
//                                                           ON CREATE START

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        final EditText setAboutEvent = findViewById(R.id.about_event_edittext);
        final EditText setEventTitle = findViewById(R.id.set_event_title_edittext);
        setEventTime = findViewById(R.id.set_event_time_editText);

        //////////////////////////////////////////////////////////////////////////////////////////
        //--- Cancel Button to cancel adding event and get back to home activity -----
        /////////////////////////////////////////////////////////////////////////////////////////
        ImageView cancelImage = findViewById(R.id.cancel_image);
        cancelImage.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                finish( );
            }
        });

        //////////////////////////////////////////////////////////////////////////////////////////
        //--- ToolBar SetUp and onClickListener for toolbar options-----
        /////////////////////////////////////////////////////////////////////////////////////////
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.add_event_menu);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener( ) {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId( ) == R.id.save) {
                    if (setEventTitle.getText( ).toString( ).equals("") || setEventTime.getText( ).toString( ).equals("")) {
                        Toast.makeText(getApplicationContext( ), "Title and Time cannot be empty", Toast.LENGTH_LONG).show( );
                    } else {
                        saveEvent(setEventTitle.getText( ).toString( ), setAboutEvent.getText( ).toString( ), time, day, month, year);
                        finish( );
                    }
                }
                return false;
            }
        });

        //////////////////////////////////////////////////////////////////////////////////////////
        //---- showTimePicker method called  onClicking setEventTime textView -----
        /////////////////////////////////////////////////////////////////////////////////////////
        setEventTime.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                showTimePicker( );
            }
        });

        //////////////////////////////////////////////////////////////////////////////////////////
        //------ getting selected Date from the previous activity using bundle via Intent-------
        /////////////////////////////////////////////////////////////////////////////////////////
        Intent intent = getIntent( );
        Bundle b = intent.getExtras( );
        day = String.valueOf(b.getInt("selectedDay"));
        month = String.valueOf(b.getInt("selectedMonth"));
        year = String.valueOf(b.getInt("selectedYear"));

    }

//                                                               ON CREATE END
//    ...................................................................................................................................

//    ...................................................................................................................................
//                                                              Time Picker

    ///////////////////////////////////////////////////////////////////////////////////////////////
    //                      ------ showTimePicker method defined ------
    //--this method builds a TimePickerDialog and sets the selected time in the setEventTime textView--
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public void showTimePicker() {
        Calendar mCurrentTime = Calendar.getInstance( );
        int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mCurrentTime.get(Calendar.MINUTE);

        TimePickerDialog.OnTimeSetListener timePickerDialog = new TimePickerDialog.OnTimeSetListener( ) {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                String strMinute = String.valueOf(minute);
                if (minute < 10) {
                    strMinute = "0" + strMinute;
                }
                time = hourOfDay + ":" + strMinute;
                setEventTime.setText(time);
            }
        };

        TimePickerDialog timePickerDialog1 = new TimePickerDialog(AddEventActivity.this,
                android.R.style.Theme_Material_Light_Dialog, timePickerDialog, hour, minute, true);

        timePickerDialog1.getWindow( );
        timePickerDialog1.show( );
    }

//                                                             Time Picker End
//    ..................................................................................................................................

//    ..................................................................................................................................
//                                                          saveEvent METHOD START

    //////////////////////////////////////////////////////////////////////////////////////////
    //--- Add event to database -----
    //////////////////////////////////////////////////////////////////////////////////////////
    private void saveEvent(String eventTitle, String eventDetails, String time, String date, String month, String year) {
        DBOpenHelper dbOpenHelper = new DBOpenHelper(getApplicationContext( ));
        SQLiteDatabase database = dbOpenHelper.getWritableDatabase( );
        dbOpenHelper.saveEvent(eventTitle, eventDetails, time, date, month, year, database);
        dbOpenHelper.close( );
        Toast.makeText(getApplicationContext( ), "event saved", Toast.LENGTH_SHORT).show( );
    }
//                                                          saveEvent METHOD END
//    ..................................................................................................................................

}
