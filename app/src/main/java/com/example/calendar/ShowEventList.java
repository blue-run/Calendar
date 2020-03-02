package com.example.calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.calendar.adapter.RecyclerViewAdapter;
import com.example.calendar.eventsObject.Events;

import java.util.ArrayList;
import java.util.List;

public class ShowEventList extends AppCompatActivity {

    private RecyclerView eventsRecyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    List<Events> showEventsList = new ArrayList<>( );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event_list);
        setTitle("Events");

        showEventsList = this.getIntent( ).getExtras( ).getParcelableArrayList("list");

        eventsRecyclerView = findViewById(R.id.recycler_view_);
        eventsRecyclerView.setHasFixedSize(true);
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext( )));
        recyclerViewAdapter = new RecyclerViewAdapter(getApplicationContext( ), showEventsList);
        eventsRecyclerView.setAdapter(recyclerViewAdapter);

    }
}
