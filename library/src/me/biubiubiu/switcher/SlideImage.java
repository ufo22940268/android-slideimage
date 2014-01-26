package me.biubiubiu.switcher;

import me.biubiubiu.switcher.R;

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
import android.animation.*;

import java.util.*;

public class SlideImage extends LinearLayout {

    private float mPressedY;
    private ViewGroup mContentView;
    private ImageView mPhotoView;
    private int mPhotoRes;
    private OnInsertListener mOnInsertListener;

    public SlideImage(Context context) {
        super(context);
        init();
    }

    public SlideImage(Context context, AttributeSet attr) {
        super(context, attr);
        init();
    }

    private void init() {
        mContentView = (ViewGroup)LayoutInflater
            .from(getContext()).inflate(R.layout.slide_image, this, false);
        mPhotoView = (ImageView)mContentView.findViewById(R.id.photo);
        addView(mContentView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("--------------------" + "onTouchEvent" + "--------------------");
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            mPressedY = event.getY();
        } else if (action == MotionEvent.ACTION_MOVE) {
            float delta = event.getY() - mPressedY;
            mContentView.setTranslationY(delta);
        } else if (action == MotionEvent.ACTION_UP) {
            //Check if the position over the half of the image.
            float delta = event.getY() - mPressedY;
            int height = mPhotoView.getHeight();
            if (Math.abs(delta) < height/2)  {
                //Reset to original position when it haven't over the half of the image.
                mContentView.animate().translationY(0);
            } else {
                //Scroll out of screen.
                float direction = delta/Math.abs(delta);
                if (mOnInsertListener != null) {
                    if (direction > 0) {
                        //Scroll down
                        mOnInsertListener.onInsertHidden(this);
                    } else {
                        //Scroll up
                        mOnInsertListener.onInsertFavorites(this);
                    }
                }

                float t =  height * direction;
                ViewPropertyAnimator animator = mContentView.animate();
                animator.setListener(mOverScrollListener);
                animator.translationY(t);
            }
        }

        return true;
    }

    public ImageView getImage() {
        return mPhotoView;
    }

    public void setImageRes(int res) {
        mPhotoRes = res;
    }

    public int getImageRes() {
        return mPhotoRes;
    }

    public void setOnInsertListener(OnInsertListener listener) {
        mOnInsertListener = listener;
    }


    public interface OnInsertListener {
        public void onInsertFavorites(SlideImage si);
        public void onInsertHidden(SlideImage si);
    }


    private Animator.AnimatorListener mOverScrollListener = new Animator.AnimatorListener() {

            public  void  onAnimationCancel(Animator animation) {
            }

            public  void  onAnimationEnd(Animator animation) {
                // SlideImage.this.setVisibility(View.GONE);
            }

            public  void  onAnimationRepeat(Animator animation) {
            }

            public  void  onAnimationStart(Animator animation) {
            }
        };
}
