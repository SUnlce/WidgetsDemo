package com.sexyuncle.viewanimation;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.sexyuncle.widgetsdemo.LogUtil;
import com.sexyuncle.widgetsdemo.R;

import java.lang.reflect.Field;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ViewFlipperActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.viewFlipper)
    ViewFlipper viewFlipper;
    @InjectView(R.id.number)
    TextView number;

    private static final int FLIPINTERVAL = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flipper);
        ButterKnife.inject(this);
        iniWidget();
    }

    private void iniWidget() {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        viewFlipper.setFlipInterval(FLIPINTERVAL);
        try {

            Field mHandler = viewFlipper.getClass().getDeclaredField("mHandler");
            android.os.Handler handler = new android.os.Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if (msg.what==1){
                        viewFlipper.showNext();
                        msg = obtainMessage(1);
                        sendMessageDelayed(msg, FLIPINTERVAL);
                        number.setText("现在显示的是"+(viewFlipper.getDisplayedChild()+1)+"张图片");
                    }
                }
            };
            mHandler.setAccessible(true);
            mHandler.set(viewFlipper,handler);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

}
