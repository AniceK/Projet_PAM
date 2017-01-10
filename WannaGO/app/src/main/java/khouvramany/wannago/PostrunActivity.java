package khouvramany.wannago;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.SystemClock;
import android.support.annotation.StringDef;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Iterator;

public class PostRunActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "PostRunActivity";
    private MapFragment mapFragment;
    private Run run;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postrun);

        Log.d(TAG, "onCreate");

        run = (Run) getIntent().getParcelableExtra("run");
        Log.d(TAG, "onCreate -- run :" + run.toString() +" ; "+ run.getLocations().size());

        TextView display_chrono = (TextView) findViewById(R.id.finish_time);
        TextView display_distance = (TextView) findViewById(R.id.finish_distance);
        TextView display_avg_speed = (TextView) findViewById(R.id.finish_average_speed);
        TextView display_message = (TextView) findViewById(R.id.greetings);

        String postrun_string = String.format(getResources().getString(R.string.good_job), run.getRunner());

        try {
            display_chrono.setText(String.valueOf(run.getStartDate()));
            display_distance.setText(String.valueOf(run.getDistance()));
            display_message.setText(postrun_string);
            display_avg_speed.setText(String.valueOf(run.getAvgSpeed()));
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }


        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void runAgain(View view) {
        finish();
    }

    public void changeUser(View view) {
        // Call pre-RunActivity activity
        // go back to the existing PreRunActivity activity instead of creating a new one
        // close any other activities
        Intent main = new Intent(this, LoginActiviy.class);
        main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(main);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.v(TAG, "onMapReady");
        LatLng pos;
        Iterator it = run.getLocations().iterator();
        PolylineOptions polylineOpt = new PolylineOptions();

        while (it.hasNext()) {
            Location loc = (Location)it.next();
            pos = new LatLng(loc.getLatitude(), loc.getLongitude());
            Log.v(TAG, "onMapReady -- while : add " + pos.toString());

            polylineOpt.add(pos);
        }
        polylineOpt.width(5).color(Color.BLUE).geodesic(true);
        Polyline polyline =  googleMap.addPolyline(polylineOpt);
        polyline.setVisible(true);

    }

}
