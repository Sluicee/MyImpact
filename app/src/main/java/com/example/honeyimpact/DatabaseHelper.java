package com.example.honeyimpact;

import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.util.Log;

import com.example.honeyimpact.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_PATH; // полный путь к базе данных
    private static String DB_NAME;
    private static final int SCHEMA = 1; // версия базы данных
    public static final String CHARS_TABLE = "characters"; // название таблицы персонажей в бд
    public static final String WEAPONS_TABLE = "weapons"; // название таблицы оружия в бд

    // названия столбцов базы персонажей
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_IMAGENAME = "ImageName";
    public static final String COLUMN_TITLE = "Title";
    public static final String COLUMN_ALLEGIANCE = "Allegiance";
    public static final String COLUMN_RARITY = "Rarity";
    public static final String COLUMN_WEAPONTYPE = "WeaponType";
    public static final String COLUMN_ELEMENT = "Element";
    public static final String COLUMN_BIRTHDAY = "Birthday";
    public static final String COLUMN_ASTROLABENAME = "AstrolabeName";
    public static final String COLUMN_CHINESESEIYUU = "ChineseSeiyuu";
    public static final String COLUMN_JAPANESESEIYUU = "JapaneseSeiyuu";
    public static final String COLUMN_ENGLISHSEIYUU = "EnglishSeiyuu";
    public static final String COLUMN_KOREANSEIYUU = "KoreanSeiyuu";
    public static final String COLUMN_INGAMEDESCRIPTION = "IngameDescription";
    public static final String COLUMN_COLOR = "Color";
    private final Context myContext;

    public DatabaseHelper(Context context) {
        super(context, context.getString(R.string.db_name), null, SCHEMA);
        DB_NAME = context.getString(R.string.db_name);
        this.myContext=context;
        DB_PATH =context.getFilesDir().getPath() + "/" + DB_NAME;
        Log.d("Path", "onCreate: " + DB_PATH);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {

    }

    public void create_db(){
        InputStream myInput = null;
        OutputStream myOutput = null;
        try {
            File file = new File(DB_PATH);
            if (!file.exists()) {
                Log.d("assets", "create_db: " + Arrays.toString(myContext.getAssets().list("")));
                //получаем локальную бд как поток
                myInput = myContext.getAssets().open(DB_NAME);
                // Путь к новой бд
                String outFileName = DB_PATH;

                // Открываем пустую бд
                myOutput = new FileOutputStream(outFileName);


                // побайтово копируем данные
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }

                myOutput.flush();
            }
        }
        catch(IOException ex){
            Log.d("DatabaseHelper", ex.getMessage());
        }
        finally {
            try{
                if(myOutput!=null) myOutput.close();
                if(myInput!=null) myInput.close();
            }
            catch(IOException ex){
                Log.d("DatabaseHelper", ex.getMessage());
            }
        }
    }
    public SQLiteDatabase open() throws SQLException {
        return SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }

}