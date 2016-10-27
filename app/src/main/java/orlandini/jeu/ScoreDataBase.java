package orlandini.jeu;

/**
 * Created by Singu_Admin on 23/10/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;

public class ScoreDataBase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "game";
    private static final String TABLE_SCORE = "score";

    private static final String KEY_ID_SCORE = "_id";
    private static final String KEY_SCORE = "score_value";

    private final ArrayList<String> listeScore = new ArrayList<>();

    public ScoreDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }//Create table

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create the score table
        String CREATE_SCORE_TABLE = "CREATE TABLE " + TABLE_SCORE + "("
                + KEY_ID_SCORE + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_SCORE + " INTEGER" + ")";

        //execution
        db.execSQL(CREATE_SCORE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORE);
        onCreate(db);
    }

    //add score in the table
    public void addScore(int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SCORE, score); //add score value in the content value

        db.insert(TABLE_SCORE, null, values); //insert in the database
        db.close();
    }

    //get all scores from the table score
    public ArrayList<String> getAllScores(){

    String selectQuery = "SELECT * FROM " + TABLE_SCORE;

    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);

    if(cursor.moveToFirst())

    {
        while (!cursor.isAfterLast()) {
            String score = cursor.getString(cursor.getColumnIndex(KEY_SCORE));
            listeScore.add(score);

            cursor.moveToNext();
        }
    }

    return listeScore;
}

    //on récupère la top value de la table des scores
    public String getTopScore(){
        ArrayList<Integer> maliste;
        int score = 0;
        if(!getAllIntegerScore().isEmpty()){
            maliste = getAllIntegerScore();
            score = Collections.max(maliste);
        }
        return String.valueOf(score);
    }

    //on get les 5 meilleurs scores
    public ArrayList<String> getFiveBestScores(){
        String selectQuery = "SELECT TOP 5 " + KEY_SCORE + "FROM " + TABLE_SCORE + "order by desc";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                String score = cursor.getString(cursor.getColumnIndex(KEY_SCORE));
                listeScore.add(score);

                cursor.moveToNext();
            }
        }
        return listeScore;
    }

    //transforme la liste des scores en integer
    private ArrayList<Integer> getAllIntegerScore(){
        ArrayList<Integer> mynewlist = new ArrayList<>();
        if(!this.getAllScores().isEmpty()){
            ArrayList<String> maliste = getAllScores();

            for(String myInt : maliste){
                mynewlist.add(Integer.valueOf(myInt));
            }
        }

        return mynewlist;
    }
}