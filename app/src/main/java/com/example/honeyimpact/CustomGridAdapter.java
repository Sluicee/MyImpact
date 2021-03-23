package com.example.honeyimpact;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

public class CustomGridAdapter extends BaseAdapter {
    String[] ascensionMatsCount;
    int[] ascensionMatsImages;
    String color;
    Context context;
    LayoutInflater layoutInflater;

    public CustomGridAdapter(String[] ascensionMatsCount, int[] ascensionMatsImages, String color, Context context){
        this.ascensionMatsCount = ascensionMatsCount;
        this.ascensionMatsImages = ascensionMatsImages;
        this.color = color;
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return ascensionMatsImages.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.material_item, parent, false);

        }

        TextView matsCount = convertView.findViewById(R.id.materialCount);
        ImageView matsImage = convertView.findViewById(R.id.material);
        RelativeLayout imageRL = ((Activity) context).findViewById(R.id.gridItem);
        imageRL.setBackgroundResource(context.getResources().getIdentifier(color, "color",  context.getPackageName()));
        matsCount.setText(ascensionMatsCount[position]);
        matsImage.setImageResource(ascensionMatsImages[position]);

        return convertView;
    }
}
