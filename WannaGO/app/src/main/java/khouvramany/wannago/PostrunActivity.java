package khouvramany.wannago;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Iterator;
import java.util.Vector;

public class PostRunActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "PostRunActivity";
    private static final int MAP_PADDING = 50;
    private MapFragment mapFragment;
    private Run run;
    private RunDB runDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postrun);

        Log.d(TAG, "onCreate");

        run = (Run) getIntent().getParcelableExtra("run");
        Log.d(TAG, "onCreate -- run :" + run.toString() +" ; "+ run.getLocations().size());

        TextView display_chrono = (TextView) findViewById(R.id.finish_time);
        TextView display_distance = (TextView) findViewById(R.id.finish_distance);
        TextView display_elevation = (TextView) findViewById(R.id.finish_elevation);
        TextView display_avg_speed = (TextView) findViewById(R.id.finish_average_speed);
        TextView display_max_speed = (TextView) findViewById(R.id.finish_max_speed);
        TextView display_message = (TextView) findViewById(R.id.greetings);

        String postrun_string = String.format(getResources().getString(R.string.good_job), run.getRunner());

        try {
            display_chrono.setText(String.valueOf(run.getDuration()/1000));
            display_distance.setText(String.format(getResources().getString(R.string.distance_made),run.getDistance()));
            display_elevation.setText(String.format(getResources().getString(R.string.distance_made),run.getElevation()));
            display_avg_speed.setText(String.format(getResources().getString(R.string.speed_value),run.getAvgSpeed()));
            display_max_speed.setText(String.format(getResources().getString(R.string.speed_value),run.getMaxSpeed()));
            display_message.setText(postrun_string);
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        runDB = new RunDB(this);
        runDB.insertRun(run);
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
        Vector<Location> locations = run.getLocations();
        Iterator it = locations.iterator();

        if (locations.size() != 0) {
            PolylineOptions polylineOpt = new PolylineOptions();
            MarkerOptions markerOpt = new MarkerOptions();
            LatLngBounds.Builder builder = new LatLngBounds.Builder();

            // set starting loaction marker
            markerOpt.position(new LatLng(locations.firstElement().getLatitude(),locations.firstElement().getLongitude()))
                    .title(getResources().getString(R.string.starting_location));
            googleMap.addMarker(markerOpt);

            // set ending location marker
            googleMap.addMarker(markerOpt.position(new LatLng(run.getLastLocation().getLatitude(),run.getLastLocation().getLongitude()))
                    .title(getResources().getString(R.string.ending_location)));

            while (it.hasNext()) {
                Location loc = (Location)it.next();
                LatLng pos = new LatLng(loc.getLatitude(), loc.getLongitude());
                Log.v(TAG, "onMapReady -- while : add " + pos.toString());

                builder.include(pos);
                polylineOpt.add(pos);
            }

            LatLngBounds bounds = builder.build();
            polylineOpt.width(5).color(Color.BLUE).geodesic(true);
            Polyline polyline =  googleMap.addPolyline(polylineOpt);
            polyline.setVisible(true);

            googleMap.setLatLngBoundsForCameraTarget(bounds);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds,MAP_PADDING));
        }
    }

}
