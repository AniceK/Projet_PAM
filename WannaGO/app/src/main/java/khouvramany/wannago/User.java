package khouvramany.wannago;

import android.util.Log;

import static android.R.attr.name;

/**
 * Created by gautiercouvrat on 02/01/2017.
 */

public class User {
    private static final String TAG = "User";
    private int _id;
    private String _name;
    private String _password;

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_id() {
        return _id;
    }

    public User() {}

    public User(String name, String pwd) {
        _name = name;
        _password = pwd;

        Log.d(TAG,"User created :"+ _name+" ; "+ _password);
    }

    public User(int id, String name, String pwd){
        _name = name;
        _password = pwd;
        _id = id;
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
}
