package seanacheng.com.schwartzsurvey;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MyDbHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "values_visualization.db";
    private static final String TABLE_NAME = "value_survey";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_VALUE = "value";
    public static final String COLUMN_SELF_EVAL = "personal_results";
    public static final String COLUMN_EMPLOYER_EVAL = "employer_results";
    private static final int DATABASE_VERSION = 1;

    public MyDbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        createSQLiteTable(database);
        insertValues(database);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    private void createSQLiteTable(SQLiteDatabase SQLiteDatabase) {
        String createDB = "create table if not exists "+TABLE_NAME+"("+COLUMN_ID+" integer primary key autoincrement, "+
                COLUMN_VALUE+" text not null, "+COLUMN_SELF_EVAL+" integer default -1, "+COLUMN_EMPLOYER_EVAL+" integer default -1);";
        SQLiteDatabase.execSQL(createDB);
    }

    private void insertValues(SQLiteDatabase db) {

        String[] valuesArray = { "EQUALITY (equal opportunity for all)",
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
                                "SELF-INDULGENT (doing pleasant things)"
        };
        ContentValues values = new ContentValues();

        for (String valueString:valuesArray) {
            values.put(COLUMN_VALUE,valueString);
            db.insert(TABLE_NAME,null,values);
        }
    }

    public List<Value> getValuesList () {
        List<Value> values = new ArrayList<>();
        String selectQuery = "select * from "+TABLE_NAME+";";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Value value = new Value();
                value.setID(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                value.setValue(cursor.getString(cursor.getColumnIndex(COLUMN_VALUE)));
                value.setSelfRank(cursor.getInt(cursor.getColumnIndex(COLUMN_SELF_EVAL)));
                value.setEmployerRank(cursor.getInt(cursor.getColumnIndex(COLUMN_EMPLOYER_EVAL)));

                values.add(value);
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();

        return values;
    }

    public void updateRank(List<Value> valuesList, String col) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        int rank=-1;

        for (Value val:valuesList) {
            rank = val.getRank(col);
            content.put(col, rank);
            db.update(TABLE_NAME, content, COLUMN_ID+"=?", new String[]{String.valueOf(val.getID())});
        }

        db.close();

    }
}
