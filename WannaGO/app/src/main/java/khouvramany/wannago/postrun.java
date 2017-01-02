package khouvramany.wannago;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class postrun extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postrun);
    }

    public void runAgain(View view){
        // Call pre-run activity
        Intent prerun = new Intent(this, prerun.class);
        startActivity(prerun);
    }

    public void changeUser(View view){
        // Call pre-run activity
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
    }
}
