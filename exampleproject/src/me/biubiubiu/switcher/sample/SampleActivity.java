package me.biubiubiu.switcher.sample;

import me.biubiubiu.switcher.sample.R;

import android.util.*;
import android.widget.*;
import android.view.*;
import android.content.*;
import android.app.*;
import android.os.*;
import android.text.*;
import android.database.*;
import android.net.*;
import android.opengl.*;
import android.graphics.*;
import android.view.animation.*;
import android.text.TextUtils;

import java.util.*;
import me.biubiubiu.switcher.SlideImage;

public class SampleActivity extends Activity {

    private LinearLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        mContainer = (LinearLayout)findViewById(R.id.container);
        addImage(R.drawable.sample_10);
        addImage(R.drawable.sample_11);
        addImage(R.drawable.sample_12);
    }

    private void addImage(int res) {
        SlideImage si = new SlideImage(this);
        int gap = 8;
        int itemSize= 200;
        
        ImageView iv = si.getImage();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(itemSize, itemSize);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        iv.setLayoutParams(params);
        iv.setImageResource(res);
        si.setImageRes(res);
        iv.setPadding(gap, gap, 0, 0);

        mContainer.addView(si);
    }
}
