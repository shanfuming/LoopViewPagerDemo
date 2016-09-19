package com.cfox.loopviewpagerdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    private Button mBtnOpenLoop,mBtnOpenLoopList,mBtnOpenListRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = null;
        mBtnOpenLoop = (Button) findViewById(R.id.open_loop);
        mBtnOpenLoop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,LoopActivity.class);
                startActivity(intent);

            }
        });

        mBtnOpenLoopList = (Button) findViewById(R.id.open_loop_list);
        mBtnOpenLoopList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ListViewLoopActivity.class);
                startActivity(intent);
            }
        });

        mBtnOpenListRef = (Button) findViewById(R.id.open_loop_list_ref);
        mBtnOpenListRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ListViewRefreshActivity.class);
                startActivity(intent);
            }
        });

    }
}
