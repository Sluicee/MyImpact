package com.example.honeyimpact.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.honeyimpact.CharActivity;
import com.example.honeyimpact.DatabaseHelper;
import com.example.honeyimpact.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ListView charList;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;

    ImageView image;

    TextView regionNameTW, clock, regionTimeCountDown;
    RadioGroup regionChangeRG;
    RadioButton europeRB, naRB, asiaRB;

    String currentTimeText;
    String currentTimeZone = "UTC";
    DateFormat timeFormat;
    Date currentDate;
    boolean mActive;
    final Handler mHandler;
    long endMills = 10800000;
    String resetTime = "4:00:00";

    protected FragmentActivity mActivity;

    private final Runnable mRunnable = new Runnable() {
        public void run() {
            if (mActive) {
                if (clock != null) {
                    currentDate = new Date();
                    timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                    currentTimeText = timeFormat.format(currentDate);
                    try {
                        long timeLeftMills = endMills - timeFormat.parse(currentTimeText).getTime();
                        Date timeLeft = new Date(timeLeftMills);
                        regionTimeCountDown.setText(timeFormat.format(timeLeft));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    timeFormat.setTimeZone(TimeZone.getTimeZone(currentTimeZone));
                    currentTimeText = timeFormat.format(currentDate);
                    clock.setText(currentTimeText);
                }
                mHandler.postDelayed(mRunnable, 1000);
            }
        }
    };

    public HomeFragment() {
        mHandler = new Handler();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        regionNameTW = root.findViewById(R.id.regionName);
        regionTimeCountDown = root.findViewById(R.id.regionTimeCountDown);
        clock = root.findViewById(R.id.regionTime);
        regionChangeRG = root.findViewById(R.id.regionChangeRG);
        europeRB = root.findViewById(R.id.rbEurope);
        naRB = root.findViewById(R.id.rbNA);
        asiaRB = root.findViewById(R.id.rbAsia);


        startClock();

        regionChangeRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbEurope:
                        currentTimeZone = "Etc/UTC";
                        endMills = 10800000;
                        regionNameTW.setText(getResources().getString(R.string.europe));
                        break;
                    case R.id.rbNA:
                        currentTimeZone = "America/Chicago";
                        endMills = -54000000;
                        regionNameTW.setText(getResources().getString(R.string.na));
                        break;
                    case R.id.rbAsia:
                        currentTimeZone = "Asia/Shanghai";
                        endMills = -14400000;
                        regionNameTW.setText(getResources().getString(R.string.asia));
                        break;
                    default:
                        break;
                }
            }
        });

        return root;
    }

    private void startClock() {
        mActive = true;
        mHandler.post(mRunnable);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity){
            mActivity =(FragmentActivity) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

}