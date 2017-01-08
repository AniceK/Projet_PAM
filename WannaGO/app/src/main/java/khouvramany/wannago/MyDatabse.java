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


    private final static String CRE_TAB_USER ="create table "+USER_TABLE+" ("+
            "user_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
            "user_name text not null,"+
            "user_pwd text not null"+
            ");"
            ;

    private final static String CRE_TAB_RUN ="create table "+RUN_TABLE+" ("+
            "run_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
            "start_date text not null,"+
            "user_id text not null"+
            "FOREIGN KEY(user_id) REFERENCES "+USER_TABLE+"(user_id)"+
            ");"
            ;

    private final static String CRE_TAB_RUN_DETAILS ="create table "+ RUN_DETAILS_TABLE +" ("+
            "pos_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
            "pos_coord text not null,"+
            "pos_date text not null,"+
            "run_id INTEGER,"+
            "FOREIGN KEY(run_id) REFERENCES "+RUN_TABLE+"(run_id)"+
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
