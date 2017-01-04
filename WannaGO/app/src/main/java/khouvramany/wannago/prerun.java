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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prerun);

        //format welcome message
        TextView display_message = (TextView) findViewById(R.id.prerun_text);
        String prerun_string = String.format(getResources().getString(R.string.prerun_text), getIntent().getStringExtra("user"));
        display_message.setText(prerun_string);
    }

    // On Click "Go" button
    public void startRun(View view){

        //call run service
        Intent run = new Intent(this, run.class);
        run.putExtra("user", getIntent().getStringExtra("user"));
        startActivity(run);
    }


}
