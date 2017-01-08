package khouvramany.wannago;

import android.content.ContentValues;
import android.content.Context;
import android.location.Location;
import android.util.Log;

/**
 * Created by gautiercouvrat on 08/01/2017.
 */

public class RunDB extends WannaGoDB {
    private static final String TAG = "RunDB";

    public RunDB(Context context) {
        super(context);
    }

    @Override
    public void open() {
        super.open();
        db = super.getDb();
    }

    public void insertRun(Run run){
        Log.d(TAG,"insertUser -- START");
        Log.d(TAG,"insertUser -- param user :"+ run.toString());

        this.open();
        ContentValues values = new ContentValues();
        values.put("start_date", String.valueOf(run.getStartDate()));
        values.put("user_id",run.getRunner());

        db.insert(MyDatabse.RUN_TABLE,null,values);
        close();
    }

    public void insertRunPosition(Run run, Location pos){
        Log.d(TAG,"insertUser -- START");
        Log.d(TAG,"insertUser -- param user :"+ run.toString() +"\n " +
                  "latitude : "+ pos.getLatitude()+" \n" +
                  "longitude : "+pos.getLatitude()
        );

        this.open();
        ContentValues values = new ContentValues();
        values.put("start_date", String.valueOf(run.getStartDate()));
        values.put("user_id",run.getRunner());

        db.insert(MyDatabse.RUN_DETAILS_TABLE,null,values);
        close();
    }

    public void insertRunPos(Run run){

    }


}
