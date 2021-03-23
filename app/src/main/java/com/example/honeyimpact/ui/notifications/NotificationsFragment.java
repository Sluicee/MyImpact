package com.example.honeyimpact.ui.notifications;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.honeyimpact.CharActivity;
import com.example.honeyimpact.DatabaseHelper;
import com.example.honeyimpact.R;
import com.example.honeyimpact.WeaponActivity;
import com.example.honeyimpact.ui.notifications.NotificationsViewModel;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    GridView weaponList;
    int elementSize;
    float factor;
    RelativeLayout weaponElementRL;
    ImageView weaponElementIW;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;
    protected FragmentActivity mActivity;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        //view
        weaponList = root.findViewById(R.id.weapon_grid);
        DisplayMetrics displayMetrics = root.getContext().getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        if (dpWidth > 600) {
            elementSize = (int) (Math.round(dpWidth)) / 4;
            weaponList.setNumColumns(4);
        }
        else {
            elementSize = (int) (Math.round(dpWidth)) / 2;
            weaponList.setNumColumns(2);
        }

        factor = (getResources().getDisplayMetrics().density);
        Log.d("TAG", "getDisplayMetrics: " + factor);
        elementSize = (int)(elementSize * (factor));
        weaponList.setColumnWidth(elementSize);

        Log.d("TAG", "onCreateView: " + dpWidth);
        //обработка нажатия на элемент
        weaponList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(root.getContext(), WeaponActivity.class);
                intent.putExtra("id", id); //передача id для поиска по бд. id элемента во вью совпадает с id в бд
                startActivity(intent);
            }
        });

        databaseHelper = new DatabaseHelper(getActivity().getApplicationContext());
        // создаем базу данных
        databaseHelper.create_db();



        return root;
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

    @Override
    public void onResume() {
        super.onResume();
        // открываем подключение
        db = databaseHelper.open();
        //получаем данные из бд в виде курсора. курсор = запись
        userCursor = db.rawQuery("select * from " + DatabaseHelper.WEAPONS_TABLE, null);
        // определяем, какие столбцы из курсора будут выводиться в gridView
        String[] headers = new String[]{DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_IMAGENAME, DatabaseHelper.COLUMN_RARITY, DatabaseHelper.COLUMN_COLOR};
        // создаем адаптер, передаем в него курсор
        userAdapter = new SimpleCursorAdapter(mActivity.getApplicationContext(),
                R.layout.char_element_in_view, //разметка элемента
                userCursor,
                headers,
                new int[]{R.id.textView, R.id.imageView, R.id.stars, R.id.gridItem}, // идентификаторы соответствующих ресурсов интерфейса
                0) {
            @Override
            public View getView (int position, View convertView, ViewGroup parent) {
                final View view = super.getView(position, convertView, parent);
                weaponElementRL = view.findViewById(R.id.gridItem);
                weaponElementIW = view.findViewById(R.id.imageView);
                ConstraintLayout.LayoutParams paramsRL = new ConstraintLayout.LayoutParams(elementSize, elementSize);
                weaponElementRL.setLayoutParams(paramsRL);
                Log.d("TAG", "RL height: " + weaponElementRL.getLayoutParams().height + " width: " + weaponElementRL.getLayoutParams().width);
                RelativeLayout.LayoutParams paramsIW = new RelativeLayout.LayoutParams(elementSize, elementSize);
                weaponElementIW.setLayoutParams(paramsIW);
                Log.d("TAG", "IW height: " + weaponElementIW.getLayoutParams().height + " width: " + weaponElementIW.getLayoutParams().width);

                return view;
            }
        };
        //binder для заполнения imageview
        userAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue (View view, Cursor cursor, int columnIndex){
                //char avatar
                if (view.getId() == R.id.imageView) {
                    ImageView IV=(ImageView) view;
                    Log.d("TAG", "setViewValue: " + view);
                    Log.d("TAG", "setViewValue: " + cursor.getString(columnIndex));
                    int resID = mActivity.getApplicationContext().getResources().getIdentifier(cursor.getString(columnIndex), "drawable",  mActivity.getApplicationContext().getPackageName());
                    IV.setImageDrawable(mActivity.getApplicationContext().getResources().getDrawable(resID));
                    Log.d("TAG", "setViewValue: " + cursor.getString(columnIndex));
                    return true;
                }
                if (view.getId() == R.id.gridItem) {
                    RelativeLayout IV=(RelativeLayout) view;
                    view.setBackgroundResource(mActivity.getApplicationContext().getResources().getIdentifier(userCursor.getString(14), "color",  mActivity.getApplicationContext().getPackageName()));
                    return true;
                }
                //char rarity
                if (view.getId() == R.id.stars){
                    ImageView starView=(ImageView) view;
                    int resID = R.drawable.naimage;
                    switch (cursor.getString(columnIndex)){
                        case "1":
                            Log.d("RAR", "rar: 1");
                            resID = R.drawable.star1;
                            break;
                        case "2":
                            Log.d("RAR", "rar: 2");
                            resID = R.drawable.star2;
                            break;
                        case "3":
                            Log.d("RAR", "rar: 3");
                            resID = R.drawable.star3;
                            break;
                        case "4":
                            Log.d("RAR", "rar: 4");
                            resID = R.drawable.star4;
                            break;
                        case "5":
                            Log.d("RAR", "rar: 5");
                            resID = R.drawable.star5;
                            break;
                    }
                    starView.setImageDrawable(mActivity.getApplicationContext().getResources().getDrawable(resID));
                    Log.d("RAR index", "setViewValue: " + cursor.getString(columnIndex));
                    return true;
                }
                return false;
            }
        });
        weaponList.setAdapter(userAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        userCursor.close();
    }
}