package khouvramany.wannago;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by gautiercouvrat on 02/01/2017.
 */

public class WannaGoDB {
    private static final String TAG = "WannagoDB" ;
    private final int VERSION_DB = 1;
    private SQLiteDatabase db;
    private MyDatabse myDB;

    public WannaGoDB(Context context){
        myDB = new MyDatabse(context,"WannaGO",null, VERSION_DB);
    }
    
    public void open(){
        db = myDB.getWritableDatabase();
    }
    
    public  void close(){
        db.close();
    }
    
    public SQLiteDatabase getDb(){return db;}

    public long insertUser(User user){
        ContentValues values = new ContentValues();
        values.put("user_name", user.getName());
        values.put("user_pwd",user.getPassword());

        Log.d(TAG,"new user : "+user.get_id()+" - "+user.getName() + " - "+user.getPassword());
        return db.insert("Users",null,values);
    }

    public User getUserbyName (String name){
        User result = new User();

        String[] whereArgs = new String[]{ name };
        try {
            Cursor cursor = db.rawQuery("SELECT user_id, user_name, user_pwd FROM Users WHERE user_name = ?", whereArgs);
            result = cursorToUser(cursor);
            Log.d(TAG, "fetching data")  ;
        } catch (Exception e){
            e.getMessage();
            e.printStackTrace();
        }

        return result;
    }

    private User cursorToUser(Cursor cursor){
        if ( cursor.getCount() == 0 ) return new User();

        cursor.moveToFirst();
        User user = new User();

        user.set_id(cursor.getInt(0));
        user.setName(cursor.getString(1));
        user.setPassword(cursor.getString(2));
        cursor.close();

        return user;
    }
}
