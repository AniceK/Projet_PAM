package khouvramany.wannago;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActiviy extends AppCompatActivity {

    private UserDB userDB;
    private static final String TAG = "LoginActiviy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userDB = new UserDB(this);
    }

    public void connect (View view){
        EditText user = (EditText) findViewById(R.id.iduser);
        EditText pwd = (EditText) findViewById(R.id.passworduser);

        if (user.getText().toString().matches("")) {
            Toast.makeText(this, "Please enter a username", Toast.LENGTH_LONG).show();
        }else if (pwd.getText().toString().matches("")) {
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_LONG).show();
        }else {

            User newbee = new User(user.getText().toString(), pwd.getText().toString());
            User target = userDB.getUserbyName(newbee.getName());

            // Create user if not existing
            if (target.getName() == null) {
                Log.v(TAG, "user :" + user.getText().toString() + " not retrieved in database");

                userDB.insertUser(newbee);
                Toast.makeText(this, "New user created", Toast.LENGTH_LONG).show();


                Intent prerun = new Intent(this, PreRunActivity.class);
                prerun.putExtra("user", newbee.getName());
                startActivity(prerun);
            } else {
                Log.v(TAG, "user :" + target.toString() + " found in database");
                // Refuse access if password is not valid
                if (!target.getPassword().equals(newbee.getPassword())) {
                    Log.v(TAG, "user :" + user.getText().toString() + " --  invalid password");

                    // Display the error on screen
                    Toast.makeText(this, "Invalid password", Toast.LENGTH_LONG).show();

                } else {
                    Intent prerun = new Intent(this, PreRunActivity.class);
                    prerun.putExtra("user", newbee.getName());
                    startActivity(prerun);
                }
            }
        }
    }
}
