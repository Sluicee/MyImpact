package com.example.honeyimpact;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class customlistview extends ListView {

    public customlistview(Context context) {
        super(context);
    }

    public customlistview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public customlistview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public customlistview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
