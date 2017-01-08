package khouvramany.wannago;

import android.util.Log;

import java.util.Date;

/**
 * Created by gautiercouvrat on 07/01/2017.
 */

public class Run {
    private static final String TAG = "Run";
    private Date startDate;
    private long duration;
    private long distance;
    private long elevation;
    private String runner;

    public Run() {
        this.setStartDate(new Date(System.currentTimeMillis()));
        this.setDistance((long) 0);
        this.setDuration((long) 0);
        this.setRunner(null);
        this.setElevation((long) 0);
        Log.v(TAG,"new Run : "+ this.toString());
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public long getElevation() {
        return elevation;
    }

    public void setElevation(long elevation) {
        this.elevation = elevation;
    }

    public String getRunner() {
        return runner;
    }

    public void setRunner(String runner) {
        this.runner = runner;
    }

    @Override
    public String toString() {
        return "Run{" +
                "duration=" + duration +
                ", distance=" + distance +
                ", startDate=" + startDate +
                ", runner=" + runner +
                ", elevation=" + elevation +
                '}';
    }
}
