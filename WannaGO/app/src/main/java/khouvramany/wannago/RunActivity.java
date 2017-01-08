package khouvramany.wannago;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;

public class RunActivity extends AppCompatActivity {
    //================================
    //       Variables for Activity
    //================================
    Chronometer chrono;
    Run run;

    //================================
    //       Variables for service
    //================================

    GPSTracker mGPSTracker;
    boolean mBound = false;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            // Bound to GPSTracker and get LocalService instance
            GPSTracker.LocalBinder binder = (GPSTracker.LocalBinder) service;
            mGPSTracker = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            // Unbound from GPSTracker
            mBound = false;
        }
    };

    //================================
    // Method onCreate : create instances
    //================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        run = new Run();
        chrono = (Chronometer) findViewById(R.id.chronometer);

        String name = getIntent().getStringExtra("user");
        Log.d("tag", name);

        //Start chronometer
        chrono.start();
    }

    //================================
    // Method onStart : Bind to Service
    //================================

    @Override
    protected void onStart() {
        super.onStart();

        // Bind to GPSTracker
        Intent intent = new Intent(this, GPSTracker.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    //================================
    // Method onStop : Unbind
    //================================

    @Override
    protected void onStop() {
        super.onStop();

        // Unbind from GPSTracker
        if(mBound){
            unbindService(mConnection);
            mBound = false;
        }
    }

    //================================
    // Method onResume : resume Chrono
    //================================

    @Override
    protected void onResume() {
        chrono.setBase(SystemClock.elapsedRealtime());
        chrono.start();
        super.onPostResume();
    }


    //================================
    // Method stopRun : go to postRun
    //================================

    // Button "I'm done" clicked
    public void stopRun(View view){
        chrono.stop();

        // Call post RunActivity activity
        Intent postrun = new Intent(this, PostRunActivity.class);
        postrun.putExtra("user",getIntent().getStringExtra("user"));
        postrun.putExtra("chrono", chrono.getBase());
        startActivity(postrun);
    }
}
