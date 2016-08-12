package com.example.aaron.fit3036_project;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;

/**
 * Created by Aaron on 8/12/2016.
 */
public class SceneSelect extends Activity{

    private Image scene1, scene2, scene3, scene4;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.scene_select);
    }
}
