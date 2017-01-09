package khouvramany.wannago;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class PostRunActivity extends AppCompatActivity implements OnMapReadyCallback {

    MapFragment mapFragment ;
    Run run ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postrun);

        TextView display_chrono = (TextView) findViewById(R.id.finish_time);
        Long postrun_chrono = getIntent().getLongExtra("chrono", 0);
        Long duration = ( SystemClock.elapsedRealtime() - postrun_chrono ) /1000;
        display_chrono.setText(String.valueOf(duration));

        TextView display_distance = (TextView) findViewById(R.id.finish_distance);
        Float postrun_distance = getIntent().getFloatExtra("distance", 0);
        display_distance.setText(String.valueOf(Math.round(postrun_distance)));

        TextView display_message = (TextView) findViewById(R.id.greetings);
        String postrun_string = String.format(getResources().getString(R.string.good_job), getIntent().getStringExtra("user"));
        display_message.setText(postrun_string);

        mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    public void runAgain(View view){
        finish();
    }

    public void changeUser(View view){
        // Call pre-RunActivity activity
        // go back to the existing PreRunActivity activity instead of creating a new one
        // close any other activities
        Intent main = new Intent(this, LoginActiviy.class);
        main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(main);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
                .title("Marker"));
    }
}
