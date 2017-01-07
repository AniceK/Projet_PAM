package khouvramany.wannago;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by gautiercouvrat on 02/01/2017.
 */

public abstract class WannaGoDB {
    private static final String TAG = "WannagoDB" ;
    private final int VERSION_DB = 1;
    protected static SQLiteDatabase db;
    protected static MyDatabse myDB;

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

}
