package comparador.com.comparadordecarros.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Carlos on 11/08/2015.
 */
public class DatabaseManager {

    private Context context;
    private SQLiteDatabase myDB;

    private String DB_NAME = "comparacarros";
    private String TABLE_NAME_CARROS = "Carros";

    private String TABLE_NAME_MOTORES = "Motores";

    public DatabaseManager(Context context) {
        setContext(this.context);

        setMyDB(context.openOrCreateDatabase(getDB_NAME(),context.MODE_PRIVATE,null));

    }

    public void Creator(){
         /* Create a Database. */
        try {

             /* Create a Table in the Database. */
            getMyDB().execSQL("CREATE TABLE IF NOT EXISTS "
                    + getTABLE_NAME_CARROS()
                    + "(\"id\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE ," +
                    " \"id_motor\" INTEGER NOT NULL , " +
                    " \"modelo\" VARCHAR NOT NULL , " +
                    "\"marca\" VARCHAR, " +
                    "\"ano\" INTEGER, " +
                    "\"img\" VARCHAR, " +
                    "\"preco\" DOUBLE, " +
                    "\"preco_revisao\" DOUBLE, " +
                    "\"preco_seguro\" DOUBLE);");

            getMyDB().execSQL("CREATE TABLE IF NOT EXISTS "
                    + getTABLE_NAME_MOTORES()
                    + "(\"id\" INTEGER PRIMARY KEY  NOT NULL  UNIQUE ," +
                    "\"hp\" INTEGER, " +
                    "\"cilindrada\" DOUBLE, " +
                    "\"tipo\" VARCHAR, " +
                    "\"etan\" DOUBLE, " +
                    "\"gas\" DOUBLE);");



        }
        catch(Exception e) {
            Log.e("Error", "Error", e);
        }
    }

    public void Insert(String[][] dados, String table_name, String fields){

        String result;

        for (int i = 0; i < dados.length; i++){

             result = ("" +Arrays.deepToString(dados[i])).
                    replaceAll("(^.|.$)", "  ").replace(", ", "  , " );


            /* Insert data to a Table*/
            getMyDB().execSQL("INSERT INTO "
                    + table_name
                    + " (" + fields + ")"
                   + " VALUES (" + result + ");");

        }


    }

    public Cursor Select(String query){

         /*retrieve data from database */
        Cursor mCursor = getMyDB().rawQuery(query, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor; //
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public SQLiteDatabase getMyDB() {
        return myDB;
    }

    public void setMyDB(SQLiteDatabase myDB) {
        this.myDB = myDB;
    }

    public String getDB_NAME() {
        return DB_NAME;
    }

    public void setDB_NAME(String DB_NAME) {
        this.DB_NAME = DB_NAME;
    }

    public String getTABLE_NAME_CARROS() {
        return TABLE_NAME_CARROS;
    }

    public void setTABLE_NAME_CARROS(String TABLE_NAME_CARROS) {
        this.TABLE_NAME_CARROS = TABLE_NAME_CARROS;
    }

    public String getTABLE_NAME_MOTORES() {
        return TABLE_NAME_MOTORES;
    }

}
