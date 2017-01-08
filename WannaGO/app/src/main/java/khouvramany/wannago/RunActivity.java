package khouvramany.wannago;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;

public class RunActivity extends AppCompatActivity {

    Chronometer chrono;

    public float fullDistance = 0;
    public int update = 0;

    public LocationManager locationManager;
    public LocationListener locationListener;

    public Location oldLocation = new Location("");
    public Location newLocation = new Location("");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        chrono = (Chronometer) findViewById(R.id.chronometer);

        String name = getIntent().getStringExtra("user");
        Log.d("tag",name);

        //Start chronometer
        chrono.start();

        // Initiate Location
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                float distance;

                Log.v("location", "\n" + location.getLatitude() + " " + location.getLongitude() + " " + location.getAltitude());

                newLocation.setLatitude(location.getLatitude());
                newLocation.setLongitude(location.getLongitude());
                newLocation.setAltitude(location.getAltitude());

                // Insert location.getLatitude(), location.getLongitude() and (optionally) location.getAltitude() in Database

                // locations initialisation
                if(update == 0){
                    Log.v("location", "First location");

                    oldLocation.setLatitude(location.getLatitude());
                    oldLocation.setLongitude(location.getLongitude());
                    oldLocation.setAltitude(location.getAltitude());

                    // No movement case
                }else if(oldLocation.getLatitude() == newLocation.getLatitude() &&
                        oldLocation.getLongitude() == newLocation.getLongitude()){
                    Log.v("location", "No movement");

                    // Movement case
                }else{
                    Log.v("location", "Old : Lat = " + oldLocation.getLatitude() + ", Long = " + oldLocation.getLongitude());
                    Log.v("location", "New : Lat = " + newLocation.getLatitude() + ", Long = " + newLocation.getLongitude());

                    // Distance since last location
                    distance = oldLocation.distanceTo(newLocation);

                    // Set the new location as default
                    oldLocation.setLatitude(newLocation.getLatitude());
                    oldLocation.setLongitude(newLocation.getLongitude());
                    oldLocation.setAltitude(newLocation.getAltitude());

                    Log.v("location", "Distance = " + fullDistance + " + " + distance + " = " + (fullDistance + distance) + " m");

                    // Total distance
                    fullDistance += distance;
                }

                update++;

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                }, 123);
            }
        }

        //start listening location : every 5 seconds or 10 meters
        locationManager.requestLocationUpdates("gps", 5000, 10, locationListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case 123:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    locationManager.requestLocationUpdates("gps", 5000, 10, locationListener);
        }
    }

    public void stopRun(View view){
        chrono.stop();
        // Call post RunActivity activity
        Intent postrun = new Intent(this, PostrunActivity.class);
        postrun.putExtra("user",getIntent().getStringExtra("user"));
        postrun.putExtra("distance", fullDistance);
        postrun.putExtra("chrono", chrono.getBase());
        startActivity(postrun);
    }

    @Override
    protected void onResume() {
        chrono.setBase(SystemClock.elapsedRealtime());
        chrono.start();
        super.onPostResume();
    }
}
