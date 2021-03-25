package com.example.honeyimpact;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.icu.text.CaseMap;
import android.os.Build;
import android.os.Bundle;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class CharActivity extends AppCompatActivity {

    TextView charName, charTitle,
            charAllegianceTitle, charAllegianceTW,
            WeaponTypeTitle, WeaponTypeTW,
            ElementTitle, ElementTW,
            BDayTitle, BDayTW,
            AstrolabeNameTitle, AstrolabeNameTW,
            ChineseSeiyuuTitle, ChineseSeiyuuTW,
            JapSeiyuuTitle, JapSeiyuuTW,
            EngSeiyuuTitle, EngSeiyuuTW,
            KorSeiyuuTitle,KorSeiyuuTW,
            InGameDescTitle, InGameDescTW;
    ImageView charImage, charStars;

    GridView gridViewAscension;
    GridView gridViewTalant;
    String[] ascensionMatsCount = {"18", "30", "36", "168", "1", "9", "9", "6", "46"};
    String[] talantMatsCount = {"9", "63", "114", "18", "18", "66", "93", "3"};
    int[] ascensionMatsImages = new int[9];
    int[] talantMatsImages = new int[8];
    String color;

    ListView constList;

    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    long userId=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_char);

        charName = findViewById(R.id.charName);
        charTitle = findViewById(R.id.charTitle);
        charImage = findViewById(R.id.charImage);
        charStars = findViewById(R.id.charStars);
        charAllegianceTitle = findViewById(R.id.AllegianceTitle);
        charAllegianceTW = findViewById(R.id.AllegianceTW);
        WeaponTypeTitle = findViewById(R.id.WeaponTypeTitle);
        WeaponTypeTW = findViewById(R.id.WeaponTypeTW);
        ElementTitle = findViewById(R.id.ElementTitle);
        ElementTW = findViewById(R.id.ElementTW);
        BDayTitle = findViewById(R.id.BDayTitle);
        BDayTW = findViewById(R.id.BDayTW);
        AstrolabeNameTitle = findViewById(R.id.AstrolabeNameTitle);
        AstrolabeNameTW = findViewById(R.id.AstrolabeNameTW);
        ChineseSeiyuuTitle = findViewById(R.id.ChineseSeiyuuTitle);
        ChineseSeiyuuTW = findViewById(R.id.ChineseSeiyuuTW);
        JapSeiyuuTitle = findViewById(R.id.JapSeiyuuTitle);
        JapSeiyuuTW = findViewById(R.id.JapSeiyuuTW);
        EngSeiyuuTitle = findViewById(R.id.EngSeiyuuTitle);
        EngSeiyuuTW = findViewById(R.id.EngSeiyuuTW);
        KorSeiyuuTitle = findViewById(R.id.KorSeiyuuTitle);
        KorSeiyuuTW = findViewById(R.id.KorSeiyuuTW);
        InGameDescTitle = findViewById(R.id.InGameDescTitle);
        InGameDescTW = findViewById(R.id.InGameDescTW);

        gridViewAscension = findViewById(R.id.AscensionMats);
        gridViewTalant = findViewById(R.id.TalantMats);
        CustomGridAdapter customGridAdapterAscension;
        CustomGridAdapter customGridAdapterTalant;

        constList = findViewById(R.id.constList);

        TextView[] charDescription = {
            WeaponTypeTW,ElementTW,BDayTW,AstrolabeNameTW,ChineseSeiyuuTW,JapSeiyuuTW,EngSeiyuuTW,KorSeiyuuTW,InGameDescTW
        };

        sqlHelper = new DatabaseHelper(this);
        db = sqlHelper.open();

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            userId = extras.getLong("id");
        }

        userCursor = db.rawQuery("select * from " + DatabaseHelper.CHARS_TABLE + " where " +
                DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(userId)});
        userCursor.moveToFirst();

        setTitle(userCursor.getString(1));

        color = userCursor.getString(21);

        charName.setText(userCursor.getString(1));
        int resID = getApplicationContext().getResources().getIdentifier(userCursor.getString(2), "drawable",  getApplicationContext().getPackageName());
        charImage.setImageDrawable(getApplicationContext().getResources().getDrawable(resID));
        charTitle.setText(userCursor.getString(3));
        charAllegianceTitle.setText(userCursor.getColumnName(4));
        charAllegianceTW.setText(userCursor.getString(4));
        switch (userCursor.getString(5)){
            case "4":
                Log.d("RAR", "rar: 4");
                charStars.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.star4));
                break;
            case "5":
                Log.d("RAR", "rar: 5");
                charStars.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.star5));
                break;
        }

        int descI = 0;
        int descIcolmn = 6;
        while (descI <= 8){
            charDescription[descI].setText(userCursor.getString(descIcolmn));
            descI += 1;
            descIcolmn++;
        }

        ascensionMatsImages[0] = this.getResources().getIdentifier(userCursor.getString(20), "drawable",  this.getPackageName());
        Log.d("MAT", "1: " + userCursor.getString(20));
        ascensionMatsImages[1] = this.getResources().getIdentifier(userCursor.getString(20) + "2", "drawable",  this.getPackageName());
        Log.d("MAT", "2: " + userCursor.getString(20) + "2");
        ascensionMatsImages[2] = this.getResources().getIdentifier(userCursor.getString(20) + "3", "drawable",  this.getPackageName());
        Log.d("MAT", "3: " + userCursor.getString(20) + "3");
        ascensionMatsImages[3] = this.getResources().getIdentifier(userCursor.getString(16), "drawable",  this.getPackageName());
        Log.d("MAT", "4: " + userCursor.getString(16));
        ascensionMatsImages[4] = this.getResources().getIdentifier(userCursor.getString(21), "drawable",  this.getPackageName());
        Log.d("MAT", "5: " + userCursor.getString(21));
        ascensionMatsImages[5] = this.getResources().getIdentifier(userCursor.getString(21) + "3", "drawable",  this.getPackageName());
        Log.d("MAT", "6: " + userCursor.getString(21) + "3");
        ascensionMatsImages[6] = this.getResources().getIdentifier(userCursor.getString(21) + "4", "drawable",  this.getPackageName());
        Log.d("MAT", "7: " + userCursor.getString(21) + "4");
        ascensionMatsImages[7] = this.getResources().getIdentifier(userCursor.getString(21) + "5", "drawable",  this.getPackageName());
        Log.d("MAT", "8: " + userCursor.getString(21) + "5");
        ascensionMatsImages[8] = this.getResources().getIdentifier(userCursor.getString(18), "drawable",  this.getPackageName());
        Log.d("MAT", "9: " + userCursor.getString(18));

        Log.d("MAT", "ascension: " + Arrays.toString(ascensionMatsImages));

        customGridAdapterAscension = new CustomGridAdapter(ascensionMatsCount, ascensionMatsImages, color, this);
        gridViewAscension.setAdapter(customGridAdapterAscension);
        gridViewAscension.setEnabled(false);

        talantMatsImages[0] = this.getResources().getIdentifier(userCursor.getString(22), "drawable", this.getPackageName());
        talantMatsImages[1] = this.getResources().getIdentifier(userCursor.getString(22) + "3", "drawable", this.getPackageName());
        talantMatsImages[2] = this.getResources().getIdentifier(userCursor.getString(22) + "4", "drawable", this.getPackageName());
        talantMatsImages[3] = this.getResources().getIdentifier(userCursor.getString(24), "drawable", this.getPackageName());
        talantMatsImages[4] = this.getResources().getIdentifier(userCursor.getString(23), "drawable", this.getPackageName());
        talantMatsImages[5] = this.getResources().getIdentifier(userCursor.getString(23) + "2", "drawable", this.getPackageName());
        talantMatsImages[6] = this.getResources().getIdentifier(userCursor.getString(23) + "3", "drawable", this.getPackageName());
        talantMatsImages[7] = this.getResources().getIdentifier("crown", "drawable", this.getPackageName());

        Log.d("MAT", "talant: " + Arrays.toString(talantMatsImages));

        customGridAdapterTalant = new CustomGridAdapter(talantMatsCount, talantMatsImages, color, this);
        gridViewTalant.setAdapter(customGridAdapterTalant);
        gridViewTalant.setEnabled(false);

        String[] consts = new String[6];
        int constI = 0;
        int constIcolmn = 25;
        while (constIcolmn <= 30){
            consts[constI] = userCursor.getString(constIcolmn);
            constI ++;
            constIcolmn++;
        }

        ArrayAdapter<String> constListAdapter = new ArrayAdapter<String>(this, R.layout.constellation_list_item, R.id.constText, consts);
        constList.setAdapter(constListAdapter);

        userCursor.close();

    }

}