package com.example.aaron.fit3036_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Aaron on 8/12/2016.
 */
public class SceneSelect extends AppCompatActivity {

    private ImageView scene1, scene2, scene3, scene4;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.scene_select2);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        scene1 = (ImageView) findViewById(R.id.imageView);
        scene2 = (ImageView) findViewById(R.id.imageView2);
        scene3 = (ImageView) findViewById(R.id.imageView3);
        scene4 = (ImageView) findViewById(R.id.imageView4);

        scene1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = getIntent();
                String s = "scene1";
                i.putExtra("scene", s);
                setResult(RESULT_OK, i);

                finish();
            }
        });

        scene2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = getIntent();
                String s = "scene2";
                i.putExtra("scene", s);
                setResult(RESULT_OK, i);

                finish();
            }
        });

        scene3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = getIntent();
                String s = "scene3";
                i.putExtra("scene", s);
                setResult(RESULT_OK, i);

                finish();
            }
        });

        scene4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = getIntent();
                String s = "scene4";
                i.putExtra("scene", s);
                setResult(RESULT_OK, i);

                finish();
            }
        });
    }

    /**
     * back button for the action bar
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
