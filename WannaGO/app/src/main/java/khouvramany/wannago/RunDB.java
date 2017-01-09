package khouvramany.wannago;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.location.Location;
import android.util.Log;

import java.util.Vector;

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

    public void insertRun(Run run){
        Log.d(TAG,"insertUser -- START");
        Log.d(TAG,"insertUser -- param run :"+ run.toString());

        this.open();
        ContentValues values = new ContentValues();
        values.put("start_date", String.valueOf(run.getStartDate()));
        values.put("duration",run.getDuration());
        values.put("distance",run.getDistance());
        values.put("elevation",run.getElevation());
        values.put("user_id",run.getRunner());


        db.insert(RUN_TABLE,null,values);
        close();
    }


    public void insertRunLocation(Run run, Location loc){
        Log.d(TAG,"insertUser -- START");
        Log.d(TAG,"insertUser -- param Location :"+ run.toString() +"\n " +
                "latitude : "+ loc.getLatitude()+" \n" +
                "longitude : "+loc.getLongitude()+" \n" +
                "altitude : "+loc.getAltitude()+" \n" +
                "speed : "+loc.getSpeed()+" \n" +
                "date : "+loc.getTime()+" \n" +
                "run_id "+run.getRunId()
        );

        this.open();
        ContentValues values = new ContentValues();
        values.put("latitude", loc.getLatitude());
        values.put("longitude",loc.getLongitude());
        values.put("altitude",loc.getAltitude());
        values.put("speed",loc.getSpeed());
        values.put("location_date",loc.getTime());
        values.put("user_id",run.getRunner());

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


    public void insertLocationsbyRun(Run run, Vector<Location> locations){

    }
}
