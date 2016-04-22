package com.sexyuncle.viewanimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.sexyuncle.widgetsdemo.LogUtil;
import com.sexyuncle.widgetsdemo.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ViewAnimatorActivity extends AppCompatActivity {


    @InjectView(R.id.number)
    TextView number;
    private int mTouchSlop;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.viewAnimator)
    ViewAnimator viewAnimator;

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
        changeDisplayNumber();
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
                LogUtil.D("move x is %s , speed is %s", event.getX() - oldX, mTouchSlop * 2);
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
     number.setText("正在显示"+(viewAnimator.getDisplayedChild()+1)+"张图片");
    }
}
