package khouvramany.wannago;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PreRunActivity extends AppCompatActivity {

    public String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prerun);

        //format welcome message
        TextView display_message = (TextView) findViewById(R.id.prerun_text);
        String prerun_string = String.format(getResources().getString(R.string.prerun_text), getIntent().getStringExtra("user"));
        display_message.setText(prerun_string);
    }

    // On Click "Go" button
    public void startRun(View view){

        //call RunActivity service
        Intent run = new Intent(this, RunActivity.class);
        run.putExtra("user", getIntent().getStringExtra("user"));
        run.putExtra("caller", this.getClass().getName());
        startActivity(run);
    }


}
