package com.example.hearthealthhelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ReadingListAdapter extends RecyclerView.Adapter<ReadingListAdapter.ReadingHolder> {
    private List<Reading> allReadings = new ArrayList<Reading>();
    private ReadingCardTouchListener readingCardTouchListener;

    public ReadingListAdapter(ReadingCardTouchListener readingCardTouchListener) {
        super();
        this.readingCardTouchListener = readingCardTouchListener;
    }

    @NonNull
    @Override
    public ReadingListAdapter.ReadingHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.reading_card, parent, false);
        return new ReadingHolder(itemView, readingCardTouchListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final ReadingHolder holder, final int position) {

        Reading currentReading =allReadings.get(position);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd | HH:mm");
        String formattedDate = format.format(currentReading.getDateMeasured());

        holder.heartRate.setText(Integer.toString(currentReading.getHeartRate()));
        holder.systolicPressure.setText(Integer.toString(currentReading.getSystolicPressure()));
        holder.diastolicPressure.setText(Integer.toString(currentReading.getDiastolicPressure()));
        holder.dateMeasured.setText(formattedDate);
        holder.comment.setText(currentReading.getComment());

        if(currentReading.isHealthy()) {
            holder.readingStatusButton.setImageResource(R.drawable.thumbs_up);
        } else {
            holder.readingStatusButton.setImageResource(R.drawable.thumb_down);
        }

        holder.readingStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readingCardTouchListener.onInfoButtonClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allReadings.size();
    }

    public void setAllReadings(List<Reading> readings)  {
        this.allReadings = readings;
        notifyDataSetChanged();
    }

    public Reading getReadingAt(int position) {
        return allReadings.get(position);
    }

    class ReadingHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        private TextView heartRate;
        private TextView systolicPressure;
        private TextView diastolicPressure;
        private TextView comment;
        private TextView dateMeasured;
        private FloatingActionButton readingStatusButton;

        ReadingCardTouchListener readingCardTouchListener;

        ReadingHolder(View itemView, ReadingCardTouchListener readingCardTouchListener) {
            super(itemView);
            this.readingCardTouchListener = readingCardTouchListener;

            itemView.setOnLongClickListener(this);

            this.readingStatusButton = itemView.findViewById(R.id.reading_status_button);
            this.heartRate = itemView.findViewById(R.id.heart_rate);
            this.dateMeasured = itemView.findViewById(R.id.reading_date);
            this.systolicPressure = itemView.findViewById(R.id.systolic_pressure);
            this.diastolicPressure = itemView.findViewById(R.id.diastolic_pressure);
            this.comment = itemView.findViewById(R.id.reading_comment);
            this.dateMeasured = itemView.findViewById(R.id.reading_date);
        }


        @Override
        public boolean onLongClick(View v) {
            readingCardTouchListener.onReadingLongClick(getAdapterPosition());
            return false;
        }
    }

    public interface ReadingCardTouchListener {
        void onReadingLongClick(int position);
        void onInfoButtonClick(int position);
    }
}
