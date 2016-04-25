package com.sexyuncle.widgetsdemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @InjectView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private CategoryAdapter adapter = new CategoryAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initWidget();
    }

    /**
     * @description 初始化控件
     */
    void initWidget(){
        mCollapsingToolbarLayout.setTitle(getString(R.string.app_name));
        mToolbar.setTitle(R.string.app_name);
        setSupportActionBar(mToolbar);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnRecyclerViewItemClickListener(new MyRecyclerViewItemClickListener());
    }

    private class MyRecyclerViewItemClickListener implements CategoryAdapter.OnRecyclerViewItemClickListener {

        @Override
        public void onItemClick(View childView, int position) {
            LogUtil.D("位置 %d 被点击了~",position);
            Class clazz = ((WidgetApplication)getApplication()).getActivityClass(adapter.getItem(position));
            if (clazz!=null) {
                Intent intent = new Intent(MainActivity.this,clazz);
                startActivity(intent);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            //跳转到浏览器
            Uri uri = Uri.parse("https://github.com/SUnlce");
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
