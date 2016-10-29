package orlandini.jeu;

/**
 * Created by Singu_Admin on 23/10/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
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

    private final ArrayList<Integer> listeScore = new ArrayList<>();
    private final Integer[] tabTopFiveScore = new Integer[5];

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
    public ArrayList<Integer> getAllScores(){

    String selectQuery = "SELECT * FROM " + TABLE_SCORE;

    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);

    if(cursor.moveToFirst())

    {
        while (!cursor.isAfterLast()) {
            String score = cursor.getString(cursor.getColumnIndex(KEY_SCORE));
            listeScore.add(Integer.parseInt(score));

            cursor.moveToNext();
        }
    }
        cursor.close();
    return listeScore;
}

    //on récupère la top value de la table des scores
    public String getTopScore(){
        int score = 0;
        if(!getAllScores().isEmpty()){
            score = Collections.max(getAllScores());
        }
        return String.valueOf(score);
    }

    //on get les 5 meilleurs scores
    public Integer[] getFiveBestScores(){

        int i = 0;
        //requete qui récupère les 5 meilleurs scores
        String selectQuery = "SELECT * FROM " + TABLE_SCORE + " ORDER BY " + KEY_SCORE + " DESC LIMIT 5";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                String score = cursor.getString(cursor.getColumnIndex(KEY_SCORE));
                tabTopFiveScore[i] = Integer.parseInt(score);
                cursor.moveToNext();
                i++;
            }
        }
        cursor.close();
        return tabTopFiveScore;
    }

    public void deleteAllScore(){
        String selectQuery = "DELETE FROM " + TABLE_SCORE;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(selectQuery);
    }
}