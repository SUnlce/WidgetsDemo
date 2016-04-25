package com.sexyuncle.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.sexyuncle.widgetsdemo.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dev-sexyuncle on 16/4/25.
 */
public class CycleSlipView extends ViewFlipper {

    private List<Bitmap> bitmaps = new ArrayList<>();//滑动列表源

    private static final int DEFAULT_BITMAP_ID = R.mipmap.ic_launcher;

    private AdapterView.OnItemClickListener mOnItemClickListener;
    private static  final long FLIPINTERVAL = 3000;
    private OnDisplayItemChangeListener mOnDisplayItemChangeListener;

    private boolean isFromUser = false;


    public CycleSlipView(Context context) {
        super(context);
    }

    public CycleSlipView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * @param bitmapList bitmap列表
     */
    public void setBitmapList(List<Bitmap> bitmapList){
        bitmaps = bitmapList;
        init();
    }

    public void setBitampResIds(int[]resIds){
        for (int i = 0; i < resIds.length; i++) {
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeResource(getContext().getResources(),resIds[i]);
            }catch (Resources.NotFoundException e){
                bitmap = BitmapFactory.decodeResource(getContext().getResources(), DEFAULT_BITMAP_ID);
            }finally {
                bitmaps.add(i,bitmap);
            }

        }
        init();
    }

    public void setBitmapFilePathList(String [] paths){
        for (int i = 0; i <paths.length; i++) {
            Bitmap bitmap = null;
            try {
                bitmap = getBitmapFormPath(paths[i]);
            } catch (Exception e) {
                bitmap = BitmapFactory.decodeResource(getContext().getResources(), DEFAULT_BITMAP_ID);
            }finally {
                bitmaps.add(i,bitmap);
            }
        }
        init();
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public void setOnDiaplayItemChangeListener(OnDisplayItemChangeListener listener) {
        this.mOnDisplayItemChangeListener = listener;
    }

    private void init() {
        setInAnimation(AnimationUtils.loadAnimation(getContext(),android.R.anim.fade_in));
        setOutAnimation(AnimationUtils.loadAnimation(getContext(),android.R.anim.fade_out));
        for (int i = 0; i <bitmaps.size() ; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageBitmap(bitmaps.get(i));
            imageView.setOnClickListener(new SimpleClickListener(i));
            this.addView(imageView, i, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        try {

            Field mHandler = ViewFlipper.class.getDeclaredField("mHandler");
            android.os.Handler handler = new android.os.Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if (msg.what==1&&isFromUser==false){
                        showNext();
                        msg = obtainMessage(1);
                        sendMessageDelayed(msg, FLIPINTERVAL);
                        if (mOnDisplayItemChangeListener!=null)
                            mOnDisplayItemChangeListener.onDisplayItemChange(getCurrentView(),getDisplayedChild());
                        Toast.makeText(getContext(),"显示第"+(getDisplayedChild()+1)+"张图",Toast.LENGTH_SHORT).show();
                    }
                }
            };
            mHandler.setAccessible(true);
            mHandler.set(this,handler);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        setAutoStart(true);
    }


    public interface OnDisplayItemChangeListener{
        void onDisplayItemChange(View child,int position);
    }
    private class SimpleClickListener implements OnClickListener{

        private final int position;

        public SimpleClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener!=null)
                mOnItemClickListener.onItemClick(null,v,position,position);
        }
    }


    Bitmap getBitmapFormPath(String path) throws Exception{
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path,options);
        int wScale = getWidth()/options.outWidth;
        int hScale = getHeight()/options.outHeight;
        options.inSampleSize = wScale<=hScale?wScale:hScale;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }


}
