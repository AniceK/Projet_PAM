package khouvramany.wannago;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class prerun extends AppCompatActivity {

    public String user;
    public LocationManager locationManager;
    public LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prerun);

        //format welcome message
        TextView display_message = (TextView) findViewById(R.id.prerun_text);
        String prerun_string = String.format(getResources().getString(R.string.prerun_text), getIntent().getStringExtra("user"));
        display_message.setText(prerun_string);

        // Initiate Location
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.v("location", "\n" + location.getLatitude() + " " + location.getLongitude() + " " + location.getAltitude());

                // Insert location.getLatitude(), location.getLongitude() and (optionally) location.getAltitude() in Database

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
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case 123:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    locationManager.requestLocationUpdates("gps", 10000, 10, locationListener);
        }
    }

    // On Click "Go" button
    public void startRun(View view){

        //start listening location : every 10 seconds or 10 meters
        locationManager.requestLocationUpdates("gps", 1000, 10, locationListener);

        //call run service
        Intent run = new Intent(this, run.class);
        run.putExtra("user", getIntent().getStringExtra("user"));
        startActivity(run);
    }


}
