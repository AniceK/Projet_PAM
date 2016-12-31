package khouvramany.wannago;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.widget.Chronometer;

public class run extends AppCompatActivity {

    Chronometer chrono;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.run);

        chrono = (Chronometer) findViewById(R.id.chronometer);

        //Start chronometer
        chrono.start();

        //call GPS Tracker Service
    }

    public void stopRun(View view){
        chrono.stop();
        // Call post run activity
        Intent postrun = new Intent(this, postrun.class);
        startActivity(postrun);
    }
}
