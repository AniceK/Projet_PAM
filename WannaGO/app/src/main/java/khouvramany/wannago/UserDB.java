package khouvramany.wannago;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by gautiercouvrat on 07/01/2017.
 */

public class UserDB extends WannaGoDB {

    private static  SQLiteDatabase db ;
    private static  String TAG = "UserDB" ;


    public UserDB(Context context) {
        super(context);
    }

    @Override
    public void open() {
        super.open();
        db = super.getDb();
    }

    @Override
    public void close() {
        super.close();
    }

    public void insertUser(User user){
        Log.d(TAG,"insertUser -- START");
        Log.d(TAG,"insertUser -- param user :"+ user.toString());

        this.open();
        ContentValues values = new ContentValues();
        values.put("user_name", user.getName());
        values.put("user_pwd",user.getPassword());

        db.insert(MyDatabse.USER_TABLE,null,values);
        close();
    }

    public User getUserbyName (String name){
        Log.d(TAG,"getUserbyName -- START");
        Log.d(TAG,"getUserbyName -- param name = "+name);

        open();
        User result = new User();
        String[] whereArgs = new String[]{ name };

        try {
            Cursor cursor = db.rawQuery("SELECT user_id, user_name, user_pwd FROM Users WHERE user_name like ?", whereArgs);
            result = cursorToUser(cursor);
            Log.d(TAG,"getUserbyName -- query result = "+ result.toString());
        } catch (Exception e){
            e.getMessage();
            e.printStackTrace();
        }

        close();
        Log.d(TAG,"getUserbyName -- END");
        return result;
    }

    private User cursorToUser(Cursor cursor){
        if ( cursor.getCount() == 0 ) return new User();

        cursor.moveToFirst();
        User user = new User();

        user.setId(cursor.getInt(0));
        user.setName(cursor.getString(1));
        user.setPassword(cursor.getString(2));
        cursor.close();

        return user;
    }


}
