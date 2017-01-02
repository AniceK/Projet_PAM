package khouvramany.wannago;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void connect (View view){
        EditText user = (EditText) findViewById(R.id.iduser);
        EditText pwd = (EditText) findViewById(R.id.passworduser);

        // Test ID existant ?

        // Si oui, password correct ?

        // Si non, création user.

        Intent prerun = new Intent(this, prerun.class);
        prerun.putExtra("user", user.getText().toString());
        prerun.putExtra("pwd", pwd.getText().toString());
        startActivity(prerun);
    }

    public boolean checkCredentials(String user, String password){
        boolean isCredentialsValid = false;

        //TO DO : do some checking stuff


        return isCredentialsValid;
    }

}
