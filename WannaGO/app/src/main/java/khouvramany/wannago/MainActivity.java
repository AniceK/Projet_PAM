package khouvramany.wannago;

import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private WannaGoDB wannaGoDB;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wannaGoDB = new WannaGoDB(this);
    }

    public void connect (View view){
        EditText user = (EditText) findViewById(R.id.iduser);
        EditText pwd = (EditText) findViewById(R.id.passworduser);

        User newbee = new User(user.getText().toString(),pwd.getText().toString());
        User target = wannaGoDB.getUserbyName(newbee.getName());

        wannaGoDB.open();
        // Create user if not existing
        if (target.getName() == null){

            wannaGoDB.insertUser(newbee);

            Intent prerun = new Intent(this, prerun.class);
            prerun.putExtra("user", user.getText().toString());
            startActivity(prerun);
        } else {
            // Refuse access if password is not valid
            if ( target.getPassword() != newbee.getPassword()) {

                // Display the error on screen

            } else {
                Intent prerun = new Intent(this, prerun.class);
                prerun.putExtra("user", newbee.getName());
                startActivity(prerun);
            }
        }
        wannaGoDB.close();
    }

    public boolean checkCredentials(String name, String password) {
        boolean isCredentialsValid = false;

        wannaGoDB.open();
        User target = wannaGoDB.getUserbyName(name);

        Log.d(TAG,"Target pwd :" +target.getPassword());

        if (password == target.getPassword()) isCredentialsValid = true;
        wannaGoDB.close();

        return isCredentialsValid;
    }

}
