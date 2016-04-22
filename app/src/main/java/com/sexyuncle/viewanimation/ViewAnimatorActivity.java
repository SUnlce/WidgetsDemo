package com.sexyuncle.viewanimation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ViewAnimator;
import android.widget.ViewFlipper;

import com.sexyuncle.widgetsdemo.LogUtil;
import com.sexyuncle.widgetsdemo.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ViewAnimatorActivity extends AppCompatActivity {


    private int mTouchSlop;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.viewAnimator)
    ViewAnimator viewAnimator;
    @InjectView(R.id.number)
    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_animator);
        ButterKnife.inject(this);
        initWidget();
        mTouchSlop = ViewConfiguration.get(getApplicationContext()).getScaledTouchSlop();
    }

    void initWidget() {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        for (int i = 1; i <= 4; i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            int id = (i == 1) ? R.drawable.p1 : (i == 2) ? R.drawable.p2 : (i == 3) ? R.drawable.p3 : R.drawable.p4;
            imageView.setImageResource(id);
            viewAnimator.addView(imageView, i - 1, new ViewAnimator.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        seekBar.setMax(viewAnimator.getChildCount());
        seekBar.setOnSeekBarChangeListener(new MySeekbarListener());
        changeDisplayNumber();
    }

    private class MySeekbarListener implements SeekBar.OnSeekBarChangeListener{

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            int id = (progress == 0) ? R.drawable.p1 : (progress == 1) ? R.drawable.p2 : (progress == 2) ? R.drawable.p3 : R.drawable.p4;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(getApplication().getResources(),id,options);
            int w_scale = 20/options.outWidth;
            int h_scale = 20/options.outHeight;
            options.inSampleSize = ((w_scale<h_scale)?w_scale:h_scale);
            options.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),id,options);
            Drawable drawable = new BitmapDrawable(bitmap);
            seekBar.setThumb(drawable);
            viewAnimator.setDisplayedChild(progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private int oldX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                oldX = (int) event.getX();
                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (event.getX() - oldX < -2 * mTouchSlop) {
                    viewAnimator.showNext();
                    changeDisplayNumber();
                }
                if (event.getX() - oldX > 2 * mTouchSlop) {
                    viewAnimator.showPrevious();
                    changeDisplayNumber();
                }
                oldX = 0;
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * @description 改变指示选项
     */
    void changeDisplayNumber(){
        seekBar.setProgress(viewAnimator.getDisplayedChild());
    }
}
