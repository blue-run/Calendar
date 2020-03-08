package com.example.calendar.mydatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.calendar.params.DatabaseKeys;

public class DBOpenHelper extends SQLiteOpenHelper {

    //    -------- executable strings -----------
    private static final String CREATE_TABLE =
            "CREATE TABLE " + DatabaseKeys.TABLE_NAME +
                    " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DatabaseKeys.EVENT_TITLE + " TEXT, " +
                    DatabaseKeys.EVENT_DETAILS + " TEXT, " +
                    DatabaseKeys.DATE + " TEXT, " +
                    DatabaseKeys.MONTH + " TEXT, " +
                    DatabaseKeys.YEAR + " TEXT, " +
                    DatabaseKeys.TIME + " TEXT)";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + DatabaseKeys.TABLE_NAME;

    //    -------- constructor for the Database----------
    public DBOpenHelper(@Nullable Context context) {
        super(context, DatabaseKeys.DB_NAME, null, DatabaseKeys.DB_VERSION);
    }

    //    -------- create table --------------
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    //    --------- onUpgrade delete the old table and create the new upgraded version ------
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    //    ------- Key Value Pairs of event details to save in their respective columns -----------
    public void saveEvent(String eventTitle, String eventDetails, String time, String date, String month, String year, SQLiteDatabase sqLiteDatabase) {
        ContentValues contentValues = new ContentValues( );
        contentValues.put(DatabaseKeys.EVENT_TITLE, eventTitle);
        contentValues.put(DatabaseKeys.EVENT_DETAILS, eventDetails);
        contentValues.put(DatabaseKeys.TIME, time);
        contentValues.put(DatabaseKeys.DATE, date);
        contentValues.put(DatabaseKeys.MONTH, month);
        contentValues.put(DatabaseKeys.YEAR, year);
        sqLiteDatabase.insert(DatabaseKeys.TABLE_NAME, null, contentValues);
    }

    //    ----------- get all the events for the selected day,month,year from database using cursor ----------
    public Cursor readEvents(String date, String month, String year, SQLiteDatabase sqLiteDatabase) {
        String[] columns = {DatabaseKeys.EVENT_TITLE, DatabaseKeys.EVENT_DETAILS, DatabaseKeys.TIME, DatabaseKeys.DATE, DatabaseKeys.MONTH, DatabaseKeys.YEAR};
        String selection = 
            DatabaseKeys.DATE + "=? and " 
            + DatabaseKeys.MONTH + "=? and " 
            + DatabaseKeys.YEAR + "=?";
        String[] selectionArgs = {date, month, year};
        return sqLiteDatabase.query(DatabaseKeys.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
    }
    // --------------- to delete selected events ----------------

    public void deleteEvent(String eventTitle, String eventDetails, String time, String date, String month, String year, SQLiteDatabase sqLiteDatabase) {
        String selection =
                DatabaseKeys.EVENT_TITLE + "=? and "
                        + DatabaseKeys.EVENT_DETAILS + "=? and "
                        + DatabaseKeys.TIME + "=? and "
                        + DatabaseKeys.DATE + "=? and " 
                        + DatabaseKeys.MONTH + "=? and "
                        + DatabaseKeys.YEAR + "=?";
        String[] selectionArgs = {eventTitle, eventDetails, time, date, month, year};
        sqLiteDatabase.delete(DatabaseKeys.TABLE_NAME, selection, selectionArgs);

    }
}
