package com.example.aaron.fit3036_project;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button send_btn;
    private Button scene_select;
    private ImageButton mic_btn;
    private TextView display_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chosen_image_page);

        // Creating new components
        send_btn = (Button) findViewById(R.id.send_btn);
        scene_select = (Button) findViewById(R.id.back_btn);
        mic_btn = (ImageButton) findViewById(R.id.mic_btn);
        display_txt = (TextView) findViewById(R.id.display_txt);

        scene_select.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(MainActivity.this, SceneSelect.class);
                startActivityForResult(i, 1);
            }

        });

        mic_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                listeningDialog();
            }

        });
    }

    private void listeningDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.listening_dialog, null);
        final AlertDialog aDialog = new AlertDialog.Builder(this).create();
        ImageButton mic = (ImageButton) promptView.findViewById(R.id.mic_btn_listening);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(aDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = 1500;

        aDialog.setView(promptView);
        aDialog.show();
        aDialog.getWindow().setAttributes(lp);
    }
}
