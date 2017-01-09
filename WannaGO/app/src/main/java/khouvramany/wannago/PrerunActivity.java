package khouvramany.wannago;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PreRunActivity extends AppCompatActivity {

    public String user;

    private static final String[] PERMISSIONS={
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prerun);

        //format welcome message
        TextView display_message = (TextView) findViewById(R.id.prerun_text);
        String prerun_string = String.format(getResources().getString(R.string.prerun_text), getIntent().getStringExtra("user"));
        display_message.setText(prerun_string);

        // Ask permission to access location of device
        if (!canAccessLocation()){
            ActivityCompat.requestPermissions(this, PERMISSIONS, 123);
        }
    }

    // On Click "Go" button
    public void startRun(View view){

        //call RunActivity service
        Intent run = new Intent(this, RunActivity.class);
        run.putExtra("user", getIntent().getStringExtra("user"));
        run.putExtra("caller", this.getClass().getName());
        startActivity(run);
    }

    //================================
    // Methods for Location Access
    //================================

    private boolean canAccessLocation() {
        return(hasPermission(Manifest.permission.ACCESS_FINE_LOCATION));
    }

    private boolean hasPermission(String perm) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return(PackageManager.PERMISSION_GRANTED==checkSelfPermission(perm));
        }else{
            return(PackageManager.PERMISSION_GRANTED==checkSelfPermission(perm));
        }
    }

}
