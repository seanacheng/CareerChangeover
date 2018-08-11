package seanacheng.com.schwartzsurvey;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.util.Log;

public class MyDbHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "values_visualization.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_RANK = "value_survey";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_VALUE = "value";
    public static final String COLUMN_SELF_EVAL = "personal_results";
    public static final String COLUMN_EMPLOYER_EVAL = "employer_results";

    private static final String TABLE_SCORE = "value_survey_score";
    private static final String COLUMN_DIMENSION = "dimension";
    private static final String COLUMN_PERSONAL_SCORE = "personal_score";
    private static final String COlUMN_EMPLOYER_SCORE = "employer_score";

    public MyDbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        createSQLiteTables(database);
        insertValues(database);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) { }

    private void createSQLiteTables(SQLiteDatabase SQLiteDatabase) {
        // Creates table in database to store user selected ranks
        String createRankTable = "create table if not exists "+TABLE_RANK+"("+
                COLUMN_ID+" integer primary key autoincrement, "+
                COLUMN_VALUE+" text not null, "+
                COLUMN_DIMENSION+" text not null, "+
                COLUMN_SELF_EVAL+" integer default -1, "+
                COLUMN_EMPLOYER_EVAL+" integer default -1);";
        SQLiteDatabase.execSQL(createRankTable);

        // Creates table in database to store calculated results
        String createScoreTable = "create table if not exists "+TABLE_SCORE+"("+
                COLUMN_ID+" integer primary key autoincrement, "+
                COLUMN_DIMENSION+" text not null, "+
                COLUMN_PERSONAL_SCORE+" float default -1.0, "+
                COlUMN_EMPLOYER_SCORE+" float default -1.0);";
        SQLiteDatabase.execSQL(createScoreTable);
    }

    private void insertValues(SQLiteDatabase db) {
        // Inserts arrays of values and dimensions into tables
        String[] valuesArray = {"EQUALITY (equal opportunity for all)",
                                "SOCIAL POWER (control over others, dominance)",
                                "PLEASURE (gratification of desires)",
                                "FREEDOM (freedom of action and thought)",
                                "SOCIAL ORDER (stability of society)",
                                "AN EXCITING LIFE (stimulating experiences)",
                                "POLITENESS (courtesy, good manners)",
                                "WEALTH (material possessions, money)",
                                "NATIONAL SECURITY (protection of my nation from enemies)",
                                "RECIPROCATION OF FAVORS (avoidance of indebtedness)",
                                "CREATIVITY (uniqueness, imagination)",
                                "A WORLD AT PEACE (free of war and conflict)",
                                "RESPECT FOR TRADITION (preservation of time and honored customs)",
                                "SELF-DISCIPLINE (self-restraint, resistance to temptation)",
                                "FAMILY SECURITY (safety for loved ones)",
                                "UNITY WITH NATURE (fitting into nature)",
                                "A VARIED LIFE (filled with challenge, novelty and change)",
                                "WISDOM (a mature understanding of life)",
                                "AUTHORITY (the right to lead or command)",
                                "A WORLD OF BEAUTY (beauty of nature and the arts)",
                                "SOCIAL JUSTICE (correcting injustice, care for the weak)",
                                "INDEPENDENT (self-reliant, self-sufficient)",
                                "MODERATE (avoiding extremes of feeling & action)",
                                "LOYAL (faithful to my friends, group)",
                                "AMBITIOUS (hard-working, aspiring)",
                                "BROADMINDED (tolerant of different ideas and beliefs)",
                                "HUMBLE (modest, self-effacing)",
                                "DARING (seeking adventure, risk)",
                                "PROTECTING THE ENVIRONMENT (preserving nature)",
                                "INFLUENTIAL (having an impact on people and events)",
                                "HONORING OF PARENTS AND ELDERS (showing respect)",
                                "CHOOSING OWN GOALS (selecting own purposes)",
                                "CAPABLE (competent, effective, efficient)",
                                "ACCEPTING MY PORTION IN LIFE (submitting to life's circumstances)",
                                "HONEST (genuine, sincere)",
                                "PRESERVING MY PUBLIC IMAGE (protecting my 'face')",
                                "OBEDIENT (dutiful, meeting obligations)",
                                "HELPFUL (working for the welfare of others)",
                                "ENJOYING LIFE (enjoying food, sex, leisure, etc.)",
                                "DEVOUT (holding to religious faith & belief)",
                                "RESPONSIBLE (dependable, reliable)",
                                "CURIOUS (interested in everything, exploring)",
                                "FORGIVING (willing to pardon others)",
                                "SUCCESSFUL (achieving goals)",
                                "CLEAN (neat, tidy)",
                                "SELF-INDULGENT (doing pleasant things)"};

        String[] valueDimensionsArray = {"Universalism","Power","Hedonism","Self-Direction","Security",
                "Stimulation","Conformity","Power","Security","Security","Self-Direction","Universalism",
                "Tradition","Conformity","Security","Universalism", "Stimulation","Universalism","Power",
                "Universalism","Universalism","Self-Direction","Tradition","Benevolence","Achievement",
                "Universalism","Tradition","Stimulation", "Universalism","Achievement","Conformity",
                "Self-Direction","Achievement","Tradition", "Benevolence","Power","Conformity","Benevolence",
                "Hedonism","Tradition","Benevolence","Self-Direction","Benevolence","Achievement","Security","Hedonism"};

        String[] dimensionsArray = {"Conformity","Tradition","Benevolence","Universalism","Self-Direction",
                "Stimulation","Hedonism","Achievement","Power","Security"};

        ContentValues values = new ContentValues();
        // fills rank table with content values
        for (int i=0;i<valuesArray.length;i++) {
            values.put(COLUMN_VALUE,valuesArray[i]);
            values.put(COLUMN_DIMENSION,valueDimensionsArray[i]);
            db.insert(TABLE_RANK,null,values);
        }

        values.clear();
        // fills score table with content values
        for (String dimension:dimensionsArray) {
            values.put(COLUMN_DIMENSION,dimension);
            db.insert(TABLE_SCORE,null,values);
        }
    }

    public Value[] getValuesArray() {
        // Retrieves values from database into an array of Value objects
        Value[] values = new Value[46];
        String selectQuery = "select * from "+TABLE_RANK+";";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            int i=0;
            do {
                Value value = new Value();
                value.setID(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                value.setValue(cursor.getString(cursor.getColumnIndex(COLUMN_VALUE)));
                value.setDimension(cursor.getString(cursor.getColumnIndex(COLUMN_DIMENSION)));
                value.setSelfRank(cursor.getInt(cursor.getColumnIndex(COLUMN_SELF_EVAL)));
                value.setEmployerRank(cursor.getInt(cursor.getColumnIndex(COLUMN_EMPLOYER_EVAL)));

                values[i]=value;
                i++;
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();

        return values;
    }

    public void updateRank(Value[] valuesArray, String col) {
        // Updates database with new ranks from an array of Values objects
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        int rank;

        for (Value val:valuesArray) {
            rank = val.getRank(col);
            content.put(col, rank);
            db.update(TABLE_RANK, content, COLUMN_ID+"=?", new String[]{String.valueOf(val.getID())});
        }
        db.close();
    }

    public void calculateScore(Value[] valuesArray, String col) {
        // Calculates scores using array of Value objects
        Result[] resultsArray = getResultsArray();
        for (Result result:resultsArray) {
            double rankSum = 0;
            double num = 0;
            for (Value value : valuesArray) {
                if (result.getValueDimension().equals(value.getDimension())) {
                    rankSum += value.getRank(col);
                    num++;
                }
            }
            double avgRank = rankSum/num;
            result.setScore(avgRank, col);
        }

        // Updates database with new scores from an array of Result objects
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();

        if (col.startsWith("personal")) {
            col=COLUMN_PERSONAL_SCORE;
        } else if (col.startsWith("employer")) {
            col=COlUMN_EMPLOYER_SCORE;
        }

        for (Result res:resultsArray) {
            double score = res.getScore(col);
            content.put(col,score);
            db.update(TABLE_SCORE,content,COLUMN_DIMENSION+"=?",new String[]{String.valueOf(res.getValueDimension())});
        }
        db.close();
    }

    public Result[] getResultsArray() {
        // Retrieves values from database into an array of Result objects
        Result[] results = new Result[10];
        String selectQuery = "select * from "+TABLE_SCORE+";";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            int i=0;
            do {
                Result result = new Result();
                result.setValueDimension(cursor.getString(cursor.getColumnIndex(COLUMN_DIMENSION)));
                result.setPersonalScore(cursor.getFloat(cursor.getColumnIndex(COLUMN_PERSONAL_SCORE)));
                result.setEmployerScore(cursor.getFloat(cursor.getColumnIndex(COlUMN_EMPLOYER_SCORE)));

                results[i]=result;
                i++;
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();

        return results;

    }


}
