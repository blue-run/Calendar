package com.example.calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.calendar.adapter.RecyclerViewAdapter;
import com.example.calendar.eventsObject.Events;
import com.example.calendar.mydatabase.DBOpenHelper;
import com.example.calendar.params.DatabaseKeys;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private int selectedDay;
    private int selectedMonth;
    private int selectedYear;
    static DatePicker datePicker;
    DBOpenHelper dbOpenHelper;
    List<Events> eventsList = new ArrayList<>( );
    Calendar calendar = Calendar.getInstance( );
    TextView eventsCountTextView;
    LinearLayout countLinearLayout;

//    .................................................................................................................................
//                                                      ON CREATE METHOD START

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ///////////////////////////////////////////////////////////////////////////
        //------ Initialising Views-------
        ///////////////////////////////////////////////////////////////////////////
        datePicker = findViewById(R.id.date_picker);
        FloatingActionButton fabButton = findViewById(R.id.floatingActionButton);
        eventsCountTextView = findViewById(R.id.events_count);
        countLinearLayout = findViewById(R.id.count_linear_layout);


        selectedDay = datePicker.getDayOfMonth( );
        selectedMonth = datePicker.getMonth( );
        selectedYear = datePicker.getYear( );

        eventsByDate(String.valueOf(selectedDay), String.valueOf(selectedMonth), String.valueOf(selectedYear));
        setTextViewCount( );


        ///////////////////////////////////////////////////////////////////////////
        //------ onClickListener for FAB button to go to AddEvent Activity ------
        ///////////////////////////////////////////////////////////////////////////
        fabButton.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEventActivity.class);

                selectedDay = datePicker.getDayOfMonth( );
                selectedMonth = datePicker.getMonth( );
                selectedYear = datePicker.getYear( );

                Bundle b = new Bundle( );
                b.putInt("selectedDay", selectedDay);
                b.putInt("selectedMonth", selectedMonth);
                b.putInt("selectedYear", selectedYear);

                intent.putExtras(b);
                startActivity(intent);
            }
        });

        ///////////////////////////////////////////////////////////////////////////////
        //-------- onDateChangeListener to update values of selected date---------
        ///////////////////////////////////////////////////////////////////////////////

        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener( ) {

                    @Override
                    public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        selectedDay = dayOfMonth;
                        selectedMonth = month;
                        selectedYear = year;
                        eventsByDate(String.valueOf(selectedDay), String.valueOf(selectedMonth), String.valueOf(selectedYear));
                        setTextViewCount( );
                    }
                });

        ///////////////////////////////////////////////////////////////////////////////////////////////////
        //------ eventsCount TextView onClickListener to show EventsList via RecyclerView in a new Activity
        ///////////////////////////////////////////////////////////////////////////////////////////////////
        countLinearLayout.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {

                if (eventsList.size( ) > 0) {
                    Intent intent = new Intent(getApplicationContext( ), ShowEventList.class);
                    Bundle b = new Bundle( );
                    b.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) eventsList);
                    intent.putExtras(b);

                    startActivity(intent);
                }
            }
        });

    }
//                                                      ON CREATE METHOD END
//    ...............................................................................................................................

//    ...............................................................................................................................
//                                                       OPTIONS MENU START

    ///////////////////////////////////////////////////////////////////////////////
    //------- main menu inflated-------
    ///////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater( );
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    ///////////////////////////////////////////////////////////////////////////////
    //------ action on items selected from menu ------
    ///////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId( )) {
            case R.id.jump_to_date:
                Intent intent = new Intent(getApplicationContext( ), JumpToDateActivity.class);
                startActivity(intent);
                break;
            case R.id.current_date:
                datePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

//                                                       OPTIONS MENU END
//    ...............................................................................................................................


//    ...............................................................................................................................
//                                                  eventsByDate METHOD START

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    //--- this method get events in selected month and year and put the events details in eventsList---
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void eventsByDate(String _date, String _month, String _year) {
        eventsList.clear( );
        dbOpenHelper = new DBOpenHelper(getApplicationContext( ));
        SQLiteDatabase database = dbOpenHelper.getReadableDatabase( );
        Cursor cursor = dbOpenHelper.readEvents(_date, _month, _year, database);
        while (cursor.moveToNext( )) {
            String eventTitle = cursor.getString(cursor.getColumnIndex(DatabaseKeys.EVENT_TITLE));
            String eventDetails = cursor.getString(cursor.getColumnIndex(DatabaseKeys.EVENT_DETAILS));
            String time = cursor.getString(cursor.getColumnIndex(DatabaseKeys.TIME));
            String date = cursor.getString(cursor.getColumnIndex(DatabaseKeys.DATE));
            String month = cursor.getString(cursor.getColumnIndex(DatabaseKeys.MONTH));
            String year = cursor.getString(cursor.getColumnIndex(DatabaseKeys.YEAR));

            Events events = new Events(eventTitle, eventDetails, time, date, month, year);
            eventsList.add(events);
        }
        cursor.close( );
        dbOpenHelper.close( );
    }

//                                                  eventsByDate METHOD END
//    ...............................................................................................................................

    //    ...............................................................................................................................
//                                                      onResume METHOD START
    @Override
    protected void onResume() {
        super.onResume( );


        selectedDay = datePicker.getDayOfMonth( );
        selectedMonth = datePicker.getMonth( );
        selectedYear = datePicker.getYear( );

        eventsByDate(String.valueOf(selectedDay), String.valueOf(selectedMonth), String.valueOf(selectedYear));
        setTextViewCount( );
    }
//                                                      onResume METHOD END
//    ................................................................................................................................

//    ................................................................................................................................
//                                                      setTextViewCount METHOD START

    ///////////////////////////////////////////////////////////////////////////////////////////////
    //----method definition for setting event counts in the eventsCount textView ---------
    //////////////////////////////////////////////////////////////////////////////////////////////
    private void setTextViewCount() {
        if (eventsList.size( ) > 1) {
            eventsCountTextView.setText(eventsList.size( ) + " events found Tap EVENTS to see");
        } else if (eventsList.size( ) == 1) {
            eventsCountTextView.setText("1 event found Tap EVENTS to see");
        } else {
            eventsCountTextView.setText("No Events");
        }
    }

//                                                      setTextViewCount METHOD END
//    ................................................................................................................................
}

