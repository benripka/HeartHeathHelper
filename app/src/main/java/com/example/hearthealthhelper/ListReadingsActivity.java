package com.example.hearthealthhelper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ListReadingsActivity extends AppCompatActivity implements ReadingListAdapter.ReadingCardTouchListener {

    public static final int NEW_READING_REQUEST = 1;
    public static final int EDIT_READING_REQUEST = 2;

    private ReadingViewModel readingViewModel;
    private ReadingListAdapter readingListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton newReadingButton =findViewById(R.id.new_reading_button);

        newReadingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewReadingActivity();
            }
        });

        RecyclerView readingList =findViewById(R.id.reading_list);
        readingList.setLayoutManager(new LinearLayoutManager(this));
        readingList.hasFixedSize();

        readingListAdapter = new ReadingListAdapter(this);
        readingList.setAdapter(readingListAdapter);


        readingViewModel = ViewModelProviders.of(this).get(ReadingViewModel.class);
        readingViewModel.getAllReadings().observe(this, new Observer<List<Reading>>() {
            @Override
            public void onChanged(List<Reading> readings) {
                readingListAdapter.setAllReadings(readings);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == NEW_READING_REQUEST && resultCode == Activity.RESULT_OK) {

            Reading newReading = (Reading) data.getSerializableExtra("reading");
            readingViewModel.insert(newReading);
            readingListAdapter.notifyDataSetChanged();
        }
        else if (requestCode == EDIT_READING_REQUEST && resultCode == Activity.RESULT_OK) {
            Reading modifiedReading = (Reading) data.getSerializableExtra("reading");
            readingViewModel.update(modifiedReading);
            readingListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onReadingLongClick(int position) {
        showOptionsDialogue(position);
    }

    @Override
    public void onInfoButtonClick(int position) {
        showInfoDialogue(position);
    }
    private void showInfoDialogue(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Heart Health Information")
                .setCancelable(true)
                .setView(getLayoutInflater().inflate(R.layout.reading_info_popup, null));

        if(readingListAdapter.getReadingAt(position).isHealthy()) {
            builder.setMessage(R.string.good_reading_message);
        } else {
            builder.setMessage(R.string.bad_reading_message);
        }

        builder.create().show();
    }

    private void startNewReadingActivity() {
        Intent intent = new Intent(this, EditReadingActivity.class);
        startActivityForResult(intent, NEW_READING_REQUEST);
    }

    private void showOptionsDialogue(final int position) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.modify_reading_dialog_title)
                .setMessage(R.string.modify_reading_dialog_message)
                .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == AlertDialog.BUTTON_POSITIVE) {
                            StartEditReadingActivity(readingListAdapter.getReadingAt(position));
                            Toast.makeText(ListReadingsActivity.this, "You clicked edit", Toast.LENGTH_SHORT).show();
                        }
                    }})
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == AlertDialog.BUTTON_NEGATIVE) {
                            readingViewModel.delete(readingListAdapter.getReadingAt(position));
                            readingListAdapter.notifyDataSetChanged();
                            Toast.makeText(ListReadingsActivity.this, "Reading Deleted", Toast.LENGTH_SHORT).show();
                        }
                    }})
                .setCancelable(true)
                .create()
                .show();
    }

    void StartEditReadingActivity(Reading reading) {
        Intent intent = new Intent(this, EditReadingActivity.class);
        intent.putExtra("reading", reading);
        startActivityForResult(intent, EDIT_READING_REQUEST);
    }
}
