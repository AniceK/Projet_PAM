package khouvramany.wannago;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gautiercouvrat on 02/01/2017.
 */

public class MyDatabse extends SQLiteOpenHelper {
    public static final String USER_TABLE = "Users";
    public static final String RUN_TABLE = "Run";
    public static final String RUN_DETAILS_TABLE = "Run_details";
    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_PASSWORD = "user_pwd";
    public static final String RUN_ID = "run_id";
    public static final String RUN_START_DATE = "start_date";
    public static final String RUN_DURATION = "duration";
    public static final String RUN_DISTANCE = "distance";
    public static final String RUN_ELEVATION = "elevation";
    public static final String RUN_DETAILS_ID = "location_id";
    public static final String RUN_DETAILS_LATITUDE = "latitude";
    public static final String RUN_DETAILS_LONGITUDE = "longitude";
    public static final String RUN_DETAILS_ALTITUDE = "altitude";
    public static final String RUN_DETAILS_SPEED = "speed";
    public static final String RUN_DETAILS_DATE = "location_date";


    private final static String CRE_TAB_USER ="create table "+USER_TABLE+" ("+
            USER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            USER_NAME+" text not null,"+
            USER_PASSWORD+" text not null"+
            ");"
            ;

    private final static String CRE_TAB_RUN ="create table "+RUN_TABLE+" ("+
            RUN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            RUN_START_DATE+" text not null,"+
            RUN_DURATION+" integer not null,"+
            RUN_DISTANCE+" integer not null,"+
            RUN_ELEVATION+" integer not null,"+
            USER_ID+" integer not null,"+
            "FOREIGN KEY("+USER_ID+") REFERENCES "+USER_TABLE+"("+USER_ID+")"+
            ");"
            ;

    private final static String CRE_TAB_RUN_DETAILS ="create table "+ RUN_DETAILS_TABLE +" ("+
            RUN_DETAILS_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            RUN_DETAILS_LATITUDE+" real not null,"+
            RUN_DETAILS_LONGITUDE+" real not null,"+
            RUN_DETAILS_ALTITUDE+" real not null,"+
            RUN_DETAILS_SPEED+" text not null,"+
            RUN_DETAILS_DATE+" text not null,"+
            RUN_ID+" integer not null,"+
            "FOREIGN KEY("+RUN_ID+") REFERENCES "+RUN_TABLE+"("+RUN_ID+")"+
            ");"
            ;

    public MyDatabse(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CRE_TAB_USER);
        sqLiteDatabase.execSQL(CRE_TAB_RUN);
        sqLiteDatabase.execSQL(CRE_TAB_RUN_DETAILS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP database");
        onCreate(sqLiteDatabase);
    }
}
