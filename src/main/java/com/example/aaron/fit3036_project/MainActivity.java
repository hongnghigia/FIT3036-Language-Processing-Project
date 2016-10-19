package com.example.aaron.fit3036_project;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.speech.RecognizerIntent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ImageButton send_btn;
    private ImageButton mic_btn;
    private EditText text_box;
    private TextView display_txt;
    private TextView display_speech;
    private String speech_txt;
    private String strToServer;
    private ImageView img_view;
    private String serverIP = "192.168.0.2";
    private TextView ip_view;
    private boolean showIP = true;
    private String currentImage = "image1.kb";
    private String item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chosen_image_page);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Creating new components
        send_btn = (ImageButton) findViewById(R.id.send_btn);
        mic_btn = (ImageButton) findViewById(R.id.mic_btn);
        display_txt = (TextView) findViewById(R.id.display_txt);
        display_speech = (TextView) findViewById(R.id.speech_text);
        img_view = (ImageView) findViewById(R.id.target_img);
        text_box = (EditText) findViewById(R.id.editText);
        ip_view = (TextView) findViewById(R.id.ip_display);
        ip_view.setText(serverIP);

        ip_view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });

        mic_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (display_speech.getText().toString().equals("")) {
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak");
                    intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);

                    startActivityForResult(intent, 2);
                }
                else {
                    display_speech.setText("");
                    speech_txt = "";
                    mic_btn.setBackgroundResource(R.drawable.mic);
                }
            }
        });

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!text_box.getText().toString().equals("")) {
                    strToServer = text_box.getText().toString();
                    display_txt.setText(strToServer);
                }
                else if(!display_speech.getText().toString().equals("")) {
                    strToServer = speech_txt;
                    display_txt.setText(strToServer.substring(0, strToServer.indexOf(",")));
                }
                else {
                    return;
                }

                new Thread(new Runnable() {
                    public void run() {
                        try {
                            Socket socket = new Socket(serverIP, 1234);
                            DataOutputStream DOS = new DataOutputStream(socket.getOutputStream());
                            DataInputStream DIS = new DataInputStream(socket.getInputStream());
                            DOS.writeUTF(currentImage + ";" + strToServer);
                            item = DIS.readUTF();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    highlightObject();
                                    DialogGenerator.feedbackView(MainActivity.this, serverIP);
                                }
                            });

                            socket.close();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    /**
     * actions for the menu items: change ip, change scene, clear
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.clear_screen:
                text_box.setText("");
                display_txt.setText("");
                display_speech.setText("");
                speech_txt = "";
                strToServer = "";
                mic_btn.setBackgroundResource(R.drawable.mic);
                switch(currentImage) {
                    case "image1.kb":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene1);
                        break;
                    case "image2.kb":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene2);
                        break;
                    case "image3.kb":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene3);
                        break;
                    case "image4.kb":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene4);
                        break;
                }
                break;
            case R.id.change_scene:
                Intent i = new Intent(this, SceneSelect.class);
                startActivityForResult(i, 1);
                break;
            case R.id.change_ip:
                Intent ip = new Intent(this, IPSettings.class);
                ip.putExtra("serverIP", serverIP);
                ip.putExtra("showIP", showIP);
                startActivityForResult(ip, 3);
                break;
        }
        return true;
    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data        When the an image is selected on the Scene Selection screen, this method invokes and take the data from the other activity.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == 1) {
                TextView selected = new TextView(this);
                selected.setText(data.getStringExtra("scene"));
                String scene_number = selected.getText().toString();

                if (scene_number.equals("scene1")) {
                    img_view.setImageResource(0);
                    img_view.setImageResource(R.drawable.scene1);
                    currentImage = "image1.kb";
                }
                else if (scene_number.equals("scene2")) {
                    img_view.setImageResource(0);
                    img_view.setImageResource(R.drawable.scene2);
                    currentImage = "image2.kb";
                }
                else if (scene_number.equals("scene3")) {
                    img_view.setImageResource(0);
                    img_view.setImageResource(R.drawable.scene3);
                    currentImage = "image3.kb";
                }
                else if (scene_number.equals("scene4")) {
                    img_view.setImageResource(0);
                    img_view.setImageResource(R.drawable.scene4);
                    currentImage = "image4.kb";
                }
            }

            // when using the mic and result
            if (requestCode == 2) {
                ArrayList<String> results;
                results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                float[] confidence = data.getFloatArrayExtra(RecognizerIntent.EXTRA_CONFIDENCE_SCORES);

                String tmp = results.toString();
                tmp = tmp.substring(1, tmp.length() - 1);
                speech_txt = tmp;

                List<String> items = Arrays.asList(tmp.split("\\s*,\\s*"));
                for (int i = 0; i < confidence.length; i++) {
                    confidence[i] = Math.round(confidence[i] * 100);
                }

                String printSpeech = "";
                for (int i = 0; i < items.size(); i++) {
                    printSpeech = printSpeech + items.get(i) + " - [" + confidence[i] + "%]\n";
                }
                display_speech.setText(printSpeech);
                mic_btn.setBackgroundResource(R.drawable.mic2);
            }

            // setting ip
            if (requestCode == 3) {
                serverIP = data.getStringExtra("newIP");
                ip_view.setText(serverIP);

                showIP = data.getExtras().getBoolean("checked");
                if (showIP) {
                    ip_view.setVisibility(View.VISIBLE);
                }
                else {
                    ip_view.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar, menu);
        return true;
    }

    private boolean highlightObject() {
        if (item.contains("warning1_")) {
            String[] warning = item.split("_");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(warning[1]);
            AlertDialog alert = builder.create();
            alert.show();
            return true;
        }
        switch(currentImage) {
            case "image1.kb":
                switch(item) {
                    case "brown_table1":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene1_1);
                        break;
                    case "orange_ball2":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene1_2);
                        break;
                    case "red_plate3":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene1_3);
                        break;
                    case "black_portrait4":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene1_4);
                        break;
                    case "black_portrait5":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene1_5);
                        break;
                    case "brown_desktop6":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene1_6);
                        break;
                    case "brown_desktop7":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene1_7);
                        break;
                    case "brown_plant8":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene1_8);
                        break;
                    case "brown_plant9":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene1_9);
                        break;
                    case "white_laptop10":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene1_10);
                        break;
                }
                break;

            case "image2.kb":
                switch(item) {
                    case "brown_table1":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene2_1);
                        break;
                    case "purple_ball2":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene2_2);
                        break;
                    case "blue_plate3":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene2_3);
                        break;
                    case "green_plate4":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene2_4);
                        break;
                    case "purple_ball5":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene2_5);
                        break;
                    case "red_screwdriver6":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene2_6);
                        break;
                    case "red_plate7":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene2_7);
                        break;
                    case "white_glass8":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene2_8);
                        break;
                    case "white_wineglass9":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene2_9);
                        break;
                    case "green_plate10":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene2_10);
                        break;
                    case "brown_hammer11":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene2_11);
                        break;
                    case "white_microwave12":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene2_12);
                        break;
                    case "red_plate13":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene2_13);
                        break;
                }
                break;

            case "image3.kb":
                switch(item) {
                    case "yellow_table1":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene3_1);
                        break;
                    case "blue_plate2":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene3_2);
                        break;
                    case "blue_plate3":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene3_3);
                        break;
                    case "blue_plate4":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene3_4);
                        break;
                    case "blue_plate5":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene3_5);
                        break;
                    case "blue_plate6":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene3_6);
                        break;
                    case "blue_plate7":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene3_7);
                        break;
                }
                break;

            case "image4.kb":
                switch(item) {
                    case "yellow_table1":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene4_1);
                        break;
                    case "purple_chest2":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene4_2);
                        break;
                    case "pink_ball3":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene4_3);
                        break;
                    case "green_bookcase4":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene4_4);
                        break;
                    case "pink_ball5":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene4_5);
                        break;
                    case "blue_chest6":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene4_6);
                        break;
                    case "brown_bookcase7":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene4_7);
                        break;
                    case "brown_plant8":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene4_8);
                        break;
                    case "brown_stool9":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene4_9);
                        break;
                    case "brown_stool10":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene4_10);
                        break;
                    case "brown_stool11":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene4_11);
                        break;
                    case "brown_hammer12":
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene4_12);
                        break;
                }
                break;
        }
        return true;
    }
}