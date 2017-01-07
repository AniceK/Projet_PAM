package khouvramany.wannago;

import android.util.Log;


/**
 * Created by gautiercouvrat on 02/01/2017.
 */

public class User {
    private static final String TAG = "User";
    private int _id;
    private String _name;
    private String _password;

    public User() {
        this.setName(null);
        this.setPassword(null);
    }

    public User(String name, String pwd) {
        //this.setId();
        this.setName(name);
        this.setPassword(pwd);
        Log.d(TAG,"User --- User created :" +this.toString());
    }

    public User(int id, String name, String pwd){
        this.setId(id);
        this.setName(name);
        this.setPassword(pwd);
    }

    public String getPassword() {
        return _password;
    }

    public void setPassword(String password) {
        this._password = password;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public int getId() {
        return _id;
    }

    @Override
    public String toString(){
        return getId() +" ; "+ getName() +" ; "+getPassword();
    }
}
