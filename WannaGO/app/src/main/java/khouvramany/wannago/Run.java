package khouvramany.wannago;

import android.location.Location;
import android.location.LocationManager;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by gautiercouvrat on 07/01/2017.
 */

public class Run implements Parcelable {
    private static final String TAG = "Run";
    private int runId ;
    private long startDate;
    private double duration;
    private double distance;
    private double elevation;
    private String runner;
    private Vector<Location> locations;

    public Run() {
        this.setStartDate(0);
        this.setDistance(0);
        this.setDuration(0);
        this.setRunner(null);
        this.setElevation(0);
        locations = new Vector<Location>();

        Log.v(TAG,"new Run : "+ this.toString());
    }

    public Run(Vector<Location> locations, double duration, double distance, double elevation, String runner, long startDate, int runId) {
        this.locations = locations;
        this.duration = duration;
        this.distance = distance;
        this.elevation = elevation;
        this.runner = runner;
        this.startDate = startDate;
        this.runId = runId;
    }

    protected Run(Parcel in) {
        runId = in.readInt();
        startDate = in.readLong();
        duration = in.readDouble();
        distance = in.readDouble();
        elevation = in.readDouble();
        runner = in.readString();
        locations = new Vector<Location>();
        locations = new Vector<>();
        in.readList(locations,null);
    }

    public static final Creator<Run> CREATOR = new Creator<Run>() {
        @Override
        public Run createFromParcel(Parcel in) {
            return new Run(in);
        }

        @Override
        public Run[] newArray(int size) {
            return new Run[size];
        }
    };

    public float getAvgSpeed(){
        if (locations.size() == 0 ) return 0f ;
        Iterator<Location> it = locations.iterator();
        Float sumSpeed = 0f;
        Float avgSpeed = 0f;

        while (it.hasNext()){
            sumSpeed += it.next().getSpeed();
        }
        try {
            avgSpeed = sumSpeed/locations.size();
        } catch (Exception e){
            e.getMessage();
            e.printStackTrace();
        }
        return avgSpeed;
    }

    public float getMaxSpeed(){
        if (locations.size() == 0 ) return 0f ;
        Iterator<Location> it = locations.iterator();
        Float max = 0f;
        Float curSpeed;

        while (it.hasNext()){
            curSpeed = it.next().getSpeed();
            max = curSpeed > max ? curSpeed : max ;
        }
        return max;
    }

    public int getRunId() {
        return runId;
    }

    public void setRunId(int runId) {
        this.runId = runId;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public double getElevation() {
        return elevation;
    }

    public void setElevation(double elevation) {
        this.elevation = elevation;
    }

    public String getRunner() {
        return runner;
    }

    public void setRunner(String runner) {
        this.runner = runner;
    }

    //get the umpteenth Location point
    public Location getLocation(int i){
        return locations.get(i);
    }

    public void addLocation(Location location){
        Log.d(TAG, "addLocation: " + location);

        if (locations.size() == 0 ) setStartDate( location.getTime());
        locations.addElement(location);
    }

    public Vector<Location> getLocations() {
        return locations;
    }

    public Location getLastLocation(){
        return locations.lastElement();
    }

    public void setLocations(Vector<Location> locations) {
        this.locations = locations;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(runId);
        parcel.writeLong(startDate);
        parcel.writeDouble(duration);
        parcel.writeDouble(distance);
        parcel.writeDouble(elevation);
        parcel.writeString(runner);
        parcel.writeList(locations);
    }
}
