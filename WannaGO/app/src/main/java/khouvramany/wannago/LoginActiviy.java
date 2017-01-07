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
        setContentView(R.layout.activity_main);
        userDB = new UserDB(this);
    }

    public void connect (View view){
        EditText user = (EditText) findViewById(R.id.iduser);
        EditText pwd = (EditText) findViewById(R.id.passworduser);

        userDB.open();
        User newbee = new User(user.getText().toString(),pwd.getText().toString());
        User target = userDB.getUserbyName(newbee.getName());

        // Create user if not existing
        if (target.getName() == null){
            Log.v(TAG,"user :"+user.getText().toString()+" not retrieved in database");

            userDB.insertUser(newbee);
            Toast.makeText(this,"New user created",Toast.LENGTH_LONG).show();


            Intent prerun = new Intent(this, PreRunActivity.class);
            prerun.putExtra("user", user.getText().toString());
            startActivity(prerun);
        } else {
            Log.v(TAG,"user :"+target.toString()+" found in database");
            // Refuse access if password is not valid
            if ( !target.getPassword().equals(newbee.getPassword()) )   {
                Log.v(TAG,"user :"+user.getText().toString()+" --  invalid password");
                Log.v(TAG,"user :"+user.getText().toString()+" --  TARGET           NEWBE");
                Log.v(TAG,"user :"+user.getText().toString()+" --  "+target.getPassword()+"     !=      "+newbee.getPassword());
                // Display the error on screen

                Toast.makeText(this,"Invalid password",Toast.LENGTH_LONG).show();

            } else {
                Intent prerun = new Intent(this, PreRunActivity.class);
                prerun.putExtra("user", newbee.getName());
                startActivity(prerun);
            }
        }
        userDB.close();
    }

    public boolean checkCredentials(String name, String password) {
        boolean isCredentialsValid = false;

        userDB.open();
        User target = userDB.getUserbyName(name);

        Log.d(TAG,"Target pwd :" +target.getPassword());

        if (password.equals(target.getPassword())) isCredentialsValid = true;
        userDB.close();

        return isCredentialsValid;
    }

}
