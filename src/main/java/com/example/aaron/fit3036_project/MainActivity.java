package com.example.aaron.fit3036_project;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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
                            DOS.writeUTF(strToServer);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
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
                // add code for clearing image
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
                } else {
                    if (scene_number.equals("scene2")) {
                        img_view.setImageResource(0);
                        img_view.setImageResource(R.drawable.scene2);
                    } else {
                        if (scene_number.equals("scene3")) {
                            img_view.setImageResource(0);
                            img_view.setImageResource(R.drawable.scene3);
                        } else {
                            if (scene_number.equals("scene4")) {
                                img_view.setImageResource(0);
                                img_view.setImageResource(R.drawable.scene4);
                            }
                        }
                    }
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

//                new Thread(new Runnable() {
//                    public void run() {
//                        try {
//                            Socket socket = new Socket(serverIP, 1234);
//                            DataOutputStream DOS = new DataOutputStream(socket.getOutputStream());
//                            DOS.writeUTF(strToServer);
//
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    DialogGenerator.feedbackView(MainActivity.this, serverIP);
//                                }
//                            });
//
//                            socket.close();
//                        } catch (IOException io) {
//                            io.printStackTrace();
//                        }
//                    }
//                }).start();
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
}