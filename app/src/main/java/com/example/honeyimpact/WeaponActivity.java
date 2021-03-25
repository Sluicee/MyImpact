package com.example.honeyimpact;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.icu.text.CaseMap;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.Arrays;

public class WeaponActivity extends AppCompatActivity {

    TextView weaponName,
            DescriptionTitle, DescriptionTW,
            WeaponTypeTitle, WeaponTypeTW,
            BaseAttackTitle, BaseAttackTW,
            SecStatTitle, SecStatTW,
            BaseAttack90Title, BaseAttack90TW,
            SecStat90Title, SecStat90TW,
            PassiveTitle, PassiveTW;
    ImageView weaponImage, weaponStars;

    String bgcolor;

    GridView gridViewAscension;
    String[] ascensionMatsCount;
    int[] ascensionMatsImages = new int[10];

    ListView constList;

    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    long userId=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weapon);

        weaponName = findViewById(R.id.weaponName);
        DescriptionTitle = findViewById(R.id.DescriptionTitle);
        weaponImage = findViewById(R.id.weaponImage);
        weaponStars = findViewById(R.id.weaponStars);
        DescriptionTW = findViewById(R.id.DescriptionTW);
        BaseAttackTitle = findViewById(R.id.BaseAttackTitle);
        WeaponTypeTitle = findViewById(R.id.WeaponTypeTitle);
        WeaponTypeTW = findViewById(R.id.WeaponTypeTW);
        SecStatTitle = findViewById(R.id.SecStatTitle);
        SecStatTW = findViewById(R.id.SecStatTW);
        BaseAttackTW = findViewById(R.id.BaseAttackTW);
        BaseAttack90Title = findViewById(R.id.BaseAttack90Title);
        BaseAttack90TW = findViewById(R.id.BaseAttack90TW);
        SecStat90Title = findViewById(R.id.SecStat90Title);
        SecStat90TW = findViewById(R.id.SecStat90TW);
        PassiveTitle = findViewById(R.id.PassiveTitle);
        PassiveTW = findViewById(R.id.PassiveTW);

        gridViewAscension = findViewById(R.id.AscensionMats);
        CustomGridAdapter customGridAdapterAscension;

        TextView[] weaponDescription = {
                WeaponTypeTW,DescriptionTW,BaseAttackTW,SecStatTW,BaseAttack90TW,SecStat90TW,PassiveTW,
        };

        sqlHelper = new DatabaseHelper(this);
        db = sqlHelper.open();

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            userId = extras.getLong("id");
        }

        userCursor = db.rawQuery("select * from " + DatabaseHelper.WEAPONS_TABLE + " where " +
                DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(userId)});
        userCursor.moveToFirst();

        setTitle(userCursor.getString(1));

        weaponName.setText(userCursor.getString(1));
        int resID = getApplicationContext().getResources().getIdentifier(userCursor.getString(2), "drawable",  getApplicationContext().getPackageName());
        weaponImage.setImageDrawable(getApplicationContext().getResources().getDrawable(resID));
        switch (userCursor.getString(3)){
            case "1":
                Log.d("RAR", "rar: 4");
                weaponStars.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.star1));
                ascensionMatsCount = new String[]{"1", "3", "1", "0", "5", "6", "0", "3", "5", "0"};
                bgcolor = "itemGrey";
                break;
            case "2":
                Log.d("RAR", "rar: 4");
                weaponStars.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.star2));
                ascensionMatsCount = new String[]{"1", "3", "1", "0", "6", "8", "0", "5", "7", "0"};
                bgcolor = "itemGreen";
                break;
            case "3":
                Log.d("RAR", "rar: 4");
                weaponStars.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.star3));
                ascensionMatsCount = new String[]{"2", "6", "6", "3", "10", "12", "18", "6", "10", "12"};
                bgcolor = "itemBlue";
                break;
            case "4":
                Log.d("RAR", "rar: 4");
                weaponStars.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.star4));
                ascensionMatsCount = new String[]{"3", "9", "9", "4", "15", "18", "27", "10", "15", "18"};
                bgcolor = "itemPurple";
                break;
            case "5":
                Log.d("RAR", "rar: 5");
                weaponStars.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.star5));
                ascensionMatsCount = new String[]{"5", "14", "14", "6", "23", "27", "41", "15", "23", "27"};
                bgcolor = "itemGold";
                break;
        }

        int descI = 0;
        int descIcolmn = 4;
        while (descI <= 6){
            weaponDescription[descI].setText(userCursor.getString(descIcolmn));
            descI += 1;
            descIcolmn++;
        }

        ascensionMatsImages[0] = this.getResources().getIdentifier(userCursor.getString(11), "drawable",  this.getPackageName());
        ascensionMatsImages[1] = this.getResources().getIdentifier(userCursor.getString(11) + "3", "drawable",  this.getPackageName());
        ascensionMatsImages[2] = this.getResources().getIdentifier(userCursor.getString(11) + "4", "drawable",  this.getPackageName());
        ascensionMatsImages[3] = this.getResources().getIdentifier(userCursor.getString(11) + "5", "drawable",  this.getPackageName());

        ascensionMatsImages[4] = this.getResources().getIdentifier(userCursor.getString(12), "drawable",  this.getPackageName());
        ascensionMatsImages[5] = this.getResources().getIdentifier(userCursor.getString(12) + "3", "drawable",  this.getPackageName());
        ascensionMatsImages[6] = this.getResources().getIdentifier(userCursor.getString(12) + "4", "drawable",  this.getPackageName());

        ascensionMatsImages[7] = this.getResources().getIdentifier(userCursor.getString(13), "drawable",  this.getPackageName());
        ascensionMatsImages[8] = this.getResources().getIdentifier(userCursor.getString(13) + "2", "drawable",  this.getPackageName());
        ascensionMatsImages[9] = this.getResources().getIdentifier(userCursor.getString(13) + "3", "drawable",  this.getPackageName());

        Log.d("MAT", "ascension: " + Arrays.toString(ascensionMatsImages));

        customGridAdapterAscension = new CustomGridAdapter(ascensionMatsCount, ascensionMatsImages, bgcolor, this);
        gridViewAscension.setAdapter(customGridAdapterAscension);
        gridViewAscension.setEnabled(false);

        userCursor.close();

    }

}