package com.example.aaron.fit3036_project;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by Aaron on 8/12/2016.
 */
public class SceneSelect extends Activity{

    private ImageView scene1, scene2, scene3, scene4;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.scene_select);

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
}
