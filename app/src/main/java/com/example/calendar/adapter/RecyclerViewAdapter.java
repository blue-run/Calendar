package com.example.calendar.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendar.R;
import com.example.calendar.eventsObject.Events;
import com.example.calendar.mydatabase.DBOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private static Context context;
    private static List<Events> eventsArrayList;
    private DBOpenHelper dbOpenHelper;

    public RecyclerViewAdapter(Context context, List<Events> eventsArrayList) {
        RecyclerViewAdapter.context = context;
        RecyclerViewAdapter.eventsArrayList = eventsArrayList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext( )).inflate(R.layout.card_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final Events events = eventsArrayList.get(position);
        holder.eventTitleText.setText(events.getEVENT_NAME( ));
        holder.detailsTitleText.setText(events.getEVENT_DETAILS( ));
        holder.eventTimeText.setText(events.getTIME( ));
        holder.delete.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                deleteAEvent(events.getEVENT_NAME( ), events.getEVENT_DETAILS( ), events.getTIME( ), events.getDATE( ), events.getMONTH( ), events.getYEAR( ));
                eventsArrayList.remove(position);
                notifyDataSetChanged( );
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventsArrayList.size( );
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView eventTitleText;
        TextView detailsTitleText;
        TextView eventTimeText;
        ImageView delete;

        private MyViewHolder(@NonNull View itemView) {
            super(itemView);
            eventTitleText = itemView.findViewById(R.id.event_title_in_card);
            detailsTitleText = itemView.findViewById(R.id.event_details_in_card);
            eventTimeText = itemView.findViewById(R.id.time_in_card);
            delete = itemView.findViewById(R.id.delete);

        }
    }

    private void deleteAEvent(String eventTitle, String eventDetails, String time, String date, String month, String year) {
        dbOpenHelper = new DBOpenHelper(context);
        SQLiteDatabase sqLiteDatabase = dbOpenHelper.getWritableDatabase( );
        dbOpenHelper.deleteEvent(eventTitle, eventDetails, time, date, month, year, sqLiteDatabase);
    }
}
