package khouvramany.wannago;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

public class GPSTracker extends Service {

    //================================
    //       Variables for service
    //================================

    private final IBinder mBinder = new LocalBinder();

    //================================
    //       Variables for location
    //================================

    public LocationManager locationManager;

    private static final int LOCATION_INTERVAL = 1000;
    private static final String TAG = "GPSTracker";
    private static final float LOCATION_DISTANCE = 10;

    //================================
    //       Methods for service
    //================================

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        initializeLocationManager();

        try {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[1]);
        } catch (SecurityException ex) {
            Log.e(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.e(TAG, "network provider does not exist, " + ex.getMessage());
        }

        try {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[0]);
        } catch (SecurityException ex) {
            Log.e(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.e(TAG, "gps provider does not exist " + ex.getMessage());
        }

    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        if (locationManager != null) {
            for (LocationListener mLocationListener : mLocationListeners) {
                try {
                    locationManager.removeUpdates(mLocationListener);
                } catch (SecurityException ex) {
                    Log.d(TAG, "fail to request location update, ignore", ex);
                } catch (Exception ex) {
                    Log.d(TAG, "fail to remove location listners, ignore", ex);
                }
            }
        }
    }

    // Allows activity to call public methods of this service
    public class LocalBinder extends Binder {
        GPSTracker getService(){
            return GPSTracker.this;
        }
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    //================================
    //       Methods for location
    //================================

    private class LocationListener implements android.location.LocationListener{
        Location mLastLocation;
        LocationListener(String provider) {
            Log.d(TAG, "LocationListener " + provider);
            mLastLocation = new Location(provider);
        }
        @Override
        public void onLocationChanged(Location location) {
            Log.d(TAG, "onLocationChanged: " + location);
            mLastLocation.set(location);
        }

        @Override
        public void onStatusChanged(String provider, int i, Bundle bundle) {
            Log.d(TAG, "onStatusChanged: " + provider);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.d(TAG, "onProviderDisabled: " + provider);
            /*Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);*/
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.d(TAG, "onProviderEnabled: " + provider);
        }
    }

    LocationListener[] mLocationListeners = new LocationListener[] {
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };

    private void initializeLocationManager() {
        Log.d(TAG, "initializeLocationManager");
        if (locationManager == null) {
            locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }
}


/*============== OLD VERSION =============
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
        };

        //start listening location : every 5 seconds or 10 meters
        locationManager.requestLocationUpdates("gps", 5000, 10, locationListener);
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                }, 123);
            }
        }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case 123:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    locationManager.requestLocationUpdates("gps", 5000, 10, locationListener);
        }
    }
 */