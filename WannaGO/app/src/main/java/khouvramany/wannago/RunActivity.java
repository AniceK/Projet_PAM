package khouvramany.wannago;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;

public class RunActivity extends AppCompatActivity {

    public static final String TAG = "RunActivity";

    //================================
    //       Variables for Activity
    //================================
    Chronometer chrono;
    Run run;

    //================================
    //       Variables for service
    //================================

    // link with service status
    boolean mBound = false;
    // Contains service address
    Messenger mService = null;

    //================================
    // Method onCreate : create instances
    //================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        chrono = (Chronometer) findViewById(R.id.chronometer);

        run = new Run();
        run.setRunner(getIntent().getStringExtra("user"));

        //Start chronometer
        chrono.start();
    }

    //================================
    // Method onStart : Bind to Service
    //================================

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart");

        // Bind to GPSTracker
        Intent intent = new Intent(this, GPSTracker.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    //================================
    // Method ServiceConnection : action after connection
    //================================

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            Log.d(TAG, "onServiceConnected: " + service);

            mBound = true;
            mService = new Messenger(service);

            Message msg = new Message();

            // Msg contain activity address so the service can respond
            msg.replyTo = mMessenger;

            // Send msg to service
            try {
                mService.send(msg);
            }catch (RemoteException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            // Unbound from GPSTracker
            mBound = false;
            mService = null;

            Log.d(TAG, "onServiceDisconnected");
        }
    };

    //================================
    //       Method for handler
    //================================

    class myHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Log.d(TAG, "Message handled: " + msg);

            Location location = (Location) msg.obj;

            Log.d(TAG, "new location received: " + location);
            // insert location in Run
            run.addLocation(location);
            //run.setDuration(run.getDuration() + chrono.get);
        }
    }

    final Messenger mMessenger = new Messenger(new myHandler());

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
        //postrun.putExtra("user",getIntent().getStringExtra("user"));
        postrun.putExtra("run", run);

        startActivity(postrun);
    }
}

