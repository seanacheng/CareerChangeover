package seanacheng.com.schwartzsurvey;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

public class MyDbHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "values_visualization.db";
    public static final String TABLE_NAME = "value_survey";
    private static final String COLUMN_ID = "id";
    public static final String COLUMN_VALUE = "value";
    private static final String COLUMN_SELF_EVAL = "personal results";
    private static final String COLUMN_EMPLOYER_EVAL = "employer results";
    private static final int DATABASE_VERSION = 1;

    public MyDbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase SQLiteDatabase) {
        createSQLiteTable(SQLiteDatabase);
        insertValues(SQLiteDatabase);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private void createSQLiteTable(SQLiteDatabase SQLiteDatabase) {
        String createDB = "create table if not exists "+TABLE_NAME+"("+COLUMN_ID+" integer primary key autoincrement, "+
                COLUMN_VALUE+" text not null, "+COLUMN_SELF_EVAL+" integer default 0, "+COLUMN_EMPLOYER_EVAL+" integer default 0);";

        SQLiteDatabase.execSQL(createDB);
    }

    private void insertValues(SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        String[] valuesList = { "EQUALITY (equal opportunity for all)",
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

        for (String value:valuesList) {
            values.put(COLUMN_VALUE,value);
            db.insert(TABLE_NAME,null,values);
        }
//        String insertValues = "insert into "+TABLE_NAME+" ("+COLUMN_VALUE+") values \n"+
//                "(\"EQUALITY (equal opportunity for all)\"),\n" +
//                "(\"SOCIAL POWER (control over others, dominance)\"),\n" +
//                "(\"PLEASURE (gratification of desires)\"),\n" +
//                "(\"FREEDOM (freedom of action and thought)\"),\n" +
//                "(\"SOCIAL ORDER (stability of society)\"),\n" +
//                "(\"AN EXCITING LIFE (stimulating experiences)\"),\n" +
//                "(\"POLITENESS (courtesy, good manners)\"),\n" +
//                "(\"WEALTH (material possessions, money)\"),\n" +
//                "(\"NATIONAL SECURITY (protection of my nation from enemies)\"),\n" +
//                "(\"RECIPROCATION OF FAVORS (avoidance of indebtedness)\"),\n" +
//                "(\"CREATIVITY (uniqueness, imagination)\"),\n" +
//                "(\"A WORLD AT PEACE (free of war and conflict)\"),\n" +
//                "(\"RESPECT FOR TRADITION (preservation of time honored customs)\"),\n" +
//                "(\"SELF-DISCIPLINE (self-restraint, resistance to temptation)\"),\n" +
//                "(\"FAMILY SECURITY (safety for loved ones)\"),\n" +
//                "(\"UNITY WITH NATURE (fitting into nature)\"),\n" +
//                "(\"A VARIED LIFE (filled with challenge, novelty and change)\"),\n" +
//                "(\"WISDOM (a mature understanding of life)\"),\n" +
//                "(\"AUTHORITY (the right to lead or command)\"),\n" +
//                "(\"A WORLD OF BEAUTY (beauty of nature and the arts)\"),\n" +
//                "(\"SOCIAL JUSTICE (correcting injustice, care for the weak)\"),\n" +
//                "(\"INDEPENDENT (self reliant, self sufficient)\"),\n" +
//                "(\"MODERATE (avoiding extremes of feeling and action)\"),\n" +
//                "(\"LOYAL (faithful to my friends, group)\"),\n" +
//                "(\"AMBITIOUS (hard working, aspiring)\"),\n" +
//                "(\"BROADMINDED (tolerant of different ideas and beliefs)\"),\n" +
//                "(\"HUMBLE (modest, self effacing)\"),\n" +
//                "(\"DARING (seeking adventure, risk)\"),\n" +
//                "(\"PROTECTING THE ENVIRONMENT (preserving nature)\"),\n" +
//                "(\"INFLUENTIAL (having an impact on people and events)\"),\n" +
//                "(\"HONORING OF PARENTS AND ELDERS (showing respect)\"),\n" +
//                "(\"CHOOSING OWN GOALS (selecting own purposes)\"),\n" +
//                "(\"CAPABLE (competent, effective, efficient)\"),\n" +
//                "(\"ACCEPTING MY PORTION IN LIFE (submitting to life\\'s circumstances)\"),\n" +
//                "(\"HONEST (genuine, sincere)\"),\n" +
//                "(\"PRESERVING MY PUBLIC IMAGE (protecting my \\\"face\\\")\"),\n" +
//                "(\"OBEDIENT (dutiful, meeting obligations)\"),\n" +
//                "(\"HELPFUL (working for the welfare of others)\"),\n" +
//                "(\"ENJOYING LIFE (enjoying food, sex, leisure, etc.)\"),\n" +
//                "(\"DEVOUT (holding to religious faith and belief)\"),\n" +
//                "(\"RESPONSIBLE (dependable, reliable)\"),\n" +
//                "(\"CURIOUS (interested in everything, exploring)\"),\n" +
//                "(\"FORGIVING (willing to pardon others)\"),\n" +
//                "(\"SUCCESSFUL (achieving goals)\"),\n" +
//                "(\"CLEAN (neat, tidy)\"),\n" +
//                "(\"SELF-INDULGENT (doing pleasant things)\");";
    }

    public void updateRank(String column, int id, int rank) {
        SQLiteDatabase db = this.getWritableDatabase();

    }
}
