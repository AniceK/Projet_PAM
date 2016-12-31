package khouvramany.wannago;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class prerun extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prerun);

        TextView display_message = (TextView) findViewById(R.id.prerun_text);

        //get extras
        Intent intent = getIntent();
        String user = intent.getStringExtra("user");

        //format welcome message
        String welcome = String.format(getResources().getString(R.string.welcome_message),user);

        display_message.setText(welcome);
    }

    public void startRun(View view){

        //call run service
        Intent run = new Intent(this, run.class);
        startActivity(run);
    }


}
