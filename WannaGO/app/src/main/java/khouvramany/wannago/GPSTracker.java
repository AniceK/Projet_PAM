package khouvramany.wannago;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;

public class GPSTracker extends Service {

    //================================
    //       Variables for service
    //================================

    //private final IBinder mBinder = new LocalBinder();

    //================================
    //       Variables for location
    //================================

    public LocationManager locationManager;

    private static final int LOCATION_INTERVAL = 1000;
    private static final String TAG = "GPSTracker";
    private static final float LOCATION_DISTANCE = 10;


    //================================
    //       Method for handler
    //================================

    class myHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            messageActivity = msg.replyTo;
            Log.d(TAG, "Message handled: " + msg);
        }
    }

    // Messenger received in order to respond
    Messenger messageActivity;
    // Messenger to bind
    final Messenger mMessenger = new Messenger(new myHandler());

    //================================
    //       Methods for service
    //================================

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        initializeLocationManager();

        try {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    new LocationListener(LocationManager.GPS_PROVIDER));
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

    @Override
    public IBinder onBind(Intent arg0) {
        return mMessenger.getBinder();
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

            // Set location in msg
            Message msg = new Message();
            msg.obj = location;

            // Send msg to activity
            try {
                messageActivity.send(msg);
            }catch (RemoteException e){
                e.printStackTrace();
            }

        }

        @Override
        public void onStatusChanged(String provider, int i, Bundle bundle) {
            Log.d(TAG, "onStatusChanged: " + provider);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.d(TAG, "onProviderDisabled: " + provider);
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
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