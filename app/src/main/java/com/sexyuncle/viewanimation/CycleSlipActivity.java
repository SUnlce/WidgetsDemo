package com.sexyuncle.viewanimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.sexyuncle.view.CycleSlipView;
import com.sexyuncle.widgetsdemo.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CycleSlipActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.cycleSlipView)
    CycleSlipView cycleSlipView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycle_slip);
        ButterKnife.inject(this);
        initWidget();
    }
    void initWidget(){
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setTitle(CycleSlipView.class.getSimpleName());
        setSupportActionBar(toolbar);
        int []resIds = new int[]{R.drawable.p1,R.drawable.p2,R.drawable.p3,R.drawable.p4};
        cycleSlipView.setBitampResIds(resIds);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

}
