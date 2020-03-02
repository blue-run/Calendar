package com.example.calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class JumpToDateActivity extends AppCompatActivity {

    private EditText dayEditText;
    private EditText monthEditText;
    private EditText yearEditText;


    //    ................................................................................................................................
//                                                              ON CREATE START
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump_to_date);
        setTitle("Set Date");

        dayEditText = findViewById(R.id.dayEditText);
        monthEditText = findViewById(R.id.monthEditText);
        yearEditText = findViewById(R.id.yearEditText);

    }

//                                                               ON CREATE END
//    ....................................................................................................................................

//    ....................................................................................................................................
//                                                            goClicked METHOD START

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //------- method is called when go button is clicked----------------
    //------- go clicked method will take day, month and year from user and jump to that date-----
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void goClicked(View view) {

        //-------------if ediText fields are empty then show Toast message "fields cannot be empty"-------------
        if (yearEditText.getText( ).toString( ).equals("") || monthEditText.getText( ).toString( ).equals("") || dayEditText.getText( ).toString( ).equals("")) {

            Toast.makeText(getBaseContext( ), "fields cannot be empty", Toast.LENGTH_LONG).show( );
        }

        //-------------else if all fields are filled get the input from user and store in variables-------------
        else {
            int year = Integer.parseInt(yearEditText.getText( ).toString( ));
            int month = Integer.parseInt(monthEditText.getText( ).toString( )) - 1;
            int day = Integer.parseInt(dayEditText.getText( ).toString( ));

            //------------- check for the months which do not have days less than 31 and less than 30(Feb)-----
            //--------- show Toast if entry of month exceeds the real no days of that month ----------
            if ((day > 29 && month == 1) || (day > 30 && month == 3) || (day > 30 && month == 5) || (day > 30 && month == 8) || (day > 30 && month == 10)) {
                Toast.makeText(getBaseContext( ), "this month don't have " + day + " days", Toast.LENGTH_SHORT).show( );
            }
            //---------- if month have entered day then go to the date --------
            else {
                try {

                    //------------- year must be greater than 1899 (calendar has years from 1900)-------
                    if (year > 1899 && month < 12 && day < 32) {
                        MainActivity.datePicker.updateDate(year, month, day);  // update the date in calendar and go to entered date
                        finish( );
                    }
                    //----------- if the date does not exist show "Enter valid Date"----------------
                    else {
                        Toast.makeText(getBaseContext( ), "Enter Valid Date", Toast.LENGTH_LONG).show( );
                    }
                } catch (Exception e) {
                    e.printStackTrace( );
                    Toast.makeText(getBaseContext( ), e.getMessage( ), Toast.LENGTH_LONG).show( );
                }
            }

        }

    }

//                                                          goClicked METHOD END
//    ....................................................................................................................................
}
