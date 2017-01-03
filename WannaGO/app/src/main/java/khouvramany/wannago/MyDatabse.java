package khouvramany.wannago;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gautiercouvrat on 02/01/2017.
 */

public class MyDatabse extends SQLiteOpenHelper {

    private final static String CRE_TAB_USER ="create table Users("+
            "user_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
            "user_name text not null,"+
            "user_pwd text not null"+
            ");"
            ;

    private final static String CRE_TAB_RUN ="create table Run("+
            "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
            "isbn text not null,"+
            "titre text not null"+
            ");"
            ;

    private final static String CRE_TAB_RUN_POS ="create table Run_positions("+
            "pos_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
            "pos_coord text not null,"+
            "pos_date text not null,"+
            "run_id INTEGER,"+
            "FOREIGN KEY(run_id) REFERENCES Run(run_id)"+
            ");"
            ;

    public MyDatabse(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CRE_TAB_USER);
        sqLiteDatabase.execSQL(CRE_TAB_RUN);
        sqLiteDatabase.execSQL(CRE_TAB_RUN_POS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP database");
        onCreate(sqLiteDatabase);
    }
}
