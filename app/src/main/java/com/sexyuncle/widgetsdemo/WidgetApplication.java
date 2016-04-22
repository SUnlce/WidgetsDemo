package com.sexyuncle.widgetsdemo;

import android.app.Application;

import com.sexyuncle.viewanimation.ViewAnimatorActivity;
import com.sexyuncle.viewanimation.ViewFlipperActivity;

import java.util.HashMap;

/**
 * Created by dev-sexyuncle on 16/4/22.
 */
public class WidgetApplication extends Application{

    private static WidgetApplication mInstance;
    private HashMap<String,Class> activityMap = new HashMap<>();
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initActivitys();
    }

    void initActivitys(){
        activityMap.put("ViewAnimator", ViewAnimatorActivity.class);
        activityMap.put("ViewFlipper",ViewFlipperActivity.class);
    }
    public static WidgetApplication getInstance() {
        return mInstance;
    }

    public Class getActivityClass(String tag){
        return activityMap.get(tag);
    }

}
