package com.example.hearthealthhelper;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "reading_table")
@TypeConverters({Converters.class})
public class Reading implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private Date dateMeasured;
    private int systolicPressure;
    private int diastolicPressure;
    private int heartRate;
    private String comment;

    public Reading(Date dateMeasured, int systolicPressure, int diastolicPressure, int heartRate, String comment) {
        setDateMeasured(dateMeasured);
        setSystolicPressure(systolicPressure);
        setDiastolicPressure(diastolicPressure);
        setHeartRate(heartRate);
        setComment(comment);
    }

    public Boolean isHealthy() {
        if(systolicPressure < 90 || systolicPressure > 140 || diastolicPressure < 60 || diastolicPressure > 90) {
            return false;
        } else {
            return true;
        }
    }

    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Date getDateMeasured() {
        return dateMeasured;
    }
    public void setDateMeasured(Date dateMeasured) {
        this.dateMeasured = dateMeasured;
    }
    public int getSystolicPressure() {
        return systolicPressure;
    }
    public void setSystolicPressure(int systolicPressure) {
        this.systolicPressure = systolicPressure;
    }
    public int getDiastolicPressure() {
        return diastolicPressure;
    }
    public void setDiastolicPressure(int diastolicPressure) {
        this.diastolicPressure = diastolicPressure;
    }
    public int getHeartRate() {
        return heartRate;
    }
    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
}
