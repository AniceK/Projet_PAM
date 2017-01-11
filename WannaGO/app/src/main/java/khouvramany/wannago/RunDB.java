package khouvramany.wannago;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import java.util.Iterator;
import java.util.Vector;

import javax.xml.datatype.Duration;

import static khouvramany.wannago.MyDatabse.*;

/**
 * Created by gautiercouvrat on 08/01/2017.
 */

public class RunDB extends WannaGoDB {
    private static final String TAG = "RunDB";

    public RunDB(Context context) {
        super(context);
    }

    @Override
    public void open() {
        super.open();
        db = super.getDb();
    }

    public void insertBasicRun(Run run){
        Log.d(TAG,"insertBasicRun -- START");
        Log.d(TAG,"insertBasicRun -- param run :"+ run.toString());

        ContentValues values = new ContentValues();
        values.put(RUN_START_DATE, String.valueOf(run.getStartDate()));
        values.put(RUN_DURATION,run.getDuration());
        values.put(RUN_DISTANCE,run.getDistance());
        values.put(RUN_ELEVATION,run.getElevation());
        values.put(USER_ID,run.getRunner());

        this.open();
        db.insert(RUN_TABLE,null,values);
        close();
    }

    public void insertRun(Run run){
        Log.d(TAG,"insertRun -- START");
        Log.d(TAG,"insertRun -- param run :"+ run.toString());

        insertBasicRun(run);

        ContentValues values = new ContentValues();
        Vector<Location> locations = run.getLocations();
        Iterator<Location> it = locations.iterator();
        Location curLoc ;

        while (it.hasNext()){
            curLoc = it.next();
            values.put(RUN_DETAILS_LATITUDE, curLoc.getLatitude());
            values.put(RUN_DETAILS_LONGITUDE, curLoc.getLatitude());
            values.put(RUN_DETAILS_ALTITUDE, curLoc.getLatitude());
            values.put(RUN_DETAILS_SPEED, curLoc.getLatitude());
            values.put(RUN_DETAILS_DATE, curLoc.getLatitude());
            values.put(RUN_ID,run.getRunner());
        }

        this.open();
        db.insert(RUN_DETAILS_TABLE,null,values);
        close();
    }

    //inspired by http://stackoverflow.com/questions/15415623/android-database-to-array
    public Vector<Location> findLocationsbyRun(Run run){
        Vector<Location> resultSet = new Vector<>();

        String[] whereArgs = new String[]{String.valueOf(run.getRunId())};

        try {
            Cursor cursor = db.rawQuery("SELECT "
                            + RUN_DETAILS_ID
                            +","+ RUN_DETAILS_LATITUDE
                            +","+ RUN_DETAILS_LONGITUDE
                            +","+ RUN_DETAILS_ALTITUDE
                            +","+ RUN_DETAILS_SPEED
                            +","+ RUN_DETAILS_DATE
                            +" FROM "+ USER_TABLE
                            +" WHERE "+ RUN_ID+" like ? "
                    ,whereArgs );

            resultSet = cursorToLocations(cursor);
            Log.d(TAG,"findLocationbyRun -- query result = "+ resultSet.toString());

        } catch (Exception e){
            e.getMessage();
            e.printStackTrace();
        }

        return resultSet;
    }

    private Vector<Location> cursorToLocations(Cursor cursor){
        //if the result set is empty
        if ( cursor.getCount() == 0 ) return null;

        Vector<Location> locations = new Vector<>();
        Location location = new Location(Context.LOCATION_SERVICE);

        try {
            while (cursor.moveToNext()){
                location.setLatitude(cursor.getDouble(1));
                location.setLongitude(cursor.getDouble(2));
                location.setAltitude(cursor.getDouble(3));
                location.setSpeed(cursor.getFloat(4));
                location.setTime(cursor.getLong(5));

                locations.addElement(location);
            }
        } finally {
            cursor.close();
        }

        return locations;
    }

    //public Run findRunbyUser(User){}
}
