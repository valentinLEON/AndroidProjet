package orlandini.jeu;

/**
 * Created by Singu_Admin on 23/10/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class ScoreDataBase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "game";
    private static final String TABLE_SCORE = "score";

    private static final String KEY_ID_SCORE = "_id";
    private static final String KEY_SCORE = "score_value";

    public ScoreDataBase(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        //create the score table
        String CREATE_SCORE_TABLE = "CREATE TABLE " + TABLE_SCORE + "("
                + KEY_ID_SCORE + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_SCORE + " TEXT" + ")";

        //execution
        db.execSQL(CREATE_SCORE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORE);
        onCreate(db);
    }

    public void addScore(int score){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SCORE, score); //add score value in the content value

        db.insert(TABLE_SCORE, null, values); //insert in the database
        db.close();
    }

    public String[] getAllScores(){

        String selectQuery = "SELECT * FROM " + TABLE_SCORE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        int i = 0;
        String[] data = new String[cursor.getCount()];
        while (cursor.moveToNext()){
            data[i] = cursor.getString(1);
            i = i++;
        }

        cursor.close();
        db.close();

        return data;
    }
}
