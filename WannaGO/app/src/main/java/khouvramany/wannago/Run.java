package khouvramany.wannago;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

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
    private int distance;
    private double elevation;
    private String runner;
    private Vector<Location> locations;

    public Run() {
        this.setStartDate(0);
        this.setDuration(0);
        this.setRunner(null);
        this.setElevation(0);
        locations = new Vector<Location>();

        Log.v(TAG,"new Run : "+ this.toString());
    }

    public Run(Vector<Location> locations, double duration, int distance, double elevation, String runner, long startDate, int runId) {
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
        distance = in.readInt();
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
        Float avgSpeed = 0f;

        if (locations.size() == 0 ) return avgSpeed ;

        Iterator<Location> it = locations.iterator();
        Float sumSpeed = 0f;

        while (it.hasNext()){
            sumSpeed += it.next().getSpeed();
        }

        avgSpeed = sumSpeed/locations.size();

        Log.v(TAG,"getAvgSpeed :"+avgSpeed);
        return Math.round(avgSpeed);
    }

    public float getMaxSpeed(){
        Float max = 0f;

        if (locations.size() == 0 ) return max ;
        Iterator<Location> it = locations.iterator();
        Float curSpeed;

        while (it.hasNext()){
            curSpeed = it.next().getSpeed();
            Log.d(TAG,"get max speed : "+curSpeed);
            max = curSpeed > max ? curSpeed : max ;
        }

        Log.v(TAG,"getAvgSpeed :"+max);
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

    public int getDistance() {
        int distance = 0;
        if (locations.size() < 2 ) return distance ;
        Iterator<Location> it = locations.iterator();
        Location oldLoc ;
        Location newloc ;

        while (it.hasNext()){
            oldLoc = it.next();
            newloc = it.next();
            distance+=oldLoc.distanceTo(newloc);
        }

        Log.v(TAG,"getDistance :"+distance);
        return Math.round(distance);
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public int getElevation() {
        int elevation = 0;

        if (locations.size() < 2 ) return elevation ;

        Iterator<Location> it = locations.iterator();
        Location oldLoc;
        Location newloc;

        while (it.hasNext()){
            oldLoc = it.next();
            newloc = it.next();
            Log.v(TAG,"getElevation : elevation "+elevation +"+"+Math.abs(oldLoc.getAltitude() - newloc.getAltitude()));

            elevation+= Math.abs(oldLoc.getAltitude() - newloc.getAltitude()) ;
        }

        Log.v(TAG,"getElevation :"+elevation);
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
        parcel.writeInt(distance);
        parcel.writeDouble(elevation);
        parcel.writeString(runner);
        parcel.writeList(locations);
    }
}
