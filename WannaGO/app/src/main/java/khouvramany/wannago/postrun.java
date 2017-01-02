package khouvramany.wannago;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class postrun extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postrun);

        TextView display_message = (TextView) findViewById(R.id.greetings);
        String postrun_string = String.format(getResources().getString(R.string.good_job), getIntent().getStringExtra("user"));
        display_message.setText(postrun_string);
    }

    public void runAgain(View view){
        finish();
    }

    public void changeUser(View view){
        // Call pre-run activity
        // go back to the existing prerun activity instead of creating a new one
        // close any other activities
        Intent main = new Intent(this, MainActivity.class);
        main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(main);
    }
}
