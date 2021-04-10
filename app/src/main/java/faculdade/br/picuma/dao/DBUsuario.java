package faculdade.br.picuma.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import faculdade.br.picuma.util.Constantes;

public class DBUsuario extends SQLiteOpenHelper {

    private static DBUsuario instance;

    public static DBUsuario getInstance(Context context) {

        if (instance == null) {

            instance = new DBUsuario(context, Constantes.DB_NAME, null, Constantes.DB_VERSION);

        }

        return instance;
    }

    public DBUsuario(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constantes.DB_SQL_DROP_TABLE);
        db.execSQL(Constantes.DB_SQL_CREATE_TABLE_EMPRESA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
