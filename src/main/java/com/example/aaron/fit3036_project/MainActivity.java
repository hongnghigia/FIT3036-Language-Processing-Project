package com.example.aaron.fit3036_project;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.speech.RecognizerIntent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.aaron.fit3036_project.DialogGenerator;

import org.w3c.dom.Text;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button send_btn;
    private Button scene_select;
    private ImageButton mic_btn;
    private EditText text_box;
    private TextView display_txt;
    private ImageView img_view;
    private String serverIP = "192.168.1.32";
    private TextView ip_view;
    private String score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chosen_image_page);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Creating new components
        send_btn = (Button) findViewById(R.id.send_btn);
        scene_select = (Button) findViewById(R.id.back_btn);
        mic_btn = (ImageButton) findViewById(R.id.mic_btn);
        display_txt = (TextView) findViewById(R.id.display_txt);
        img_view = (ImageView) findViewById(R.id.target_img);
        text_box = (EditText) findViewById(R.id.editText);
        ip_view = (TextView) findViewById(R.id.iptextview);
        ip_view.setText(serverIP);

        scene_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SceneSelect.class);
                startActivityForResult(i, 1);

            }

        });

        ip_view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });


        mic_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak");
                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);

                startActivityForResult(intent, 2);
            }
        });

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String strToServer = text_box.getText().toString();
                display_txt.setText(strToServer);
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            Socket socket = new Socket(serverIP, 1234);
                            final DataOutputStream DOS = new DataOutputStream(socket.getOutputStream());
                            DOS.writeUTF(strToServer);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    DialogGenerator.feedbackView(MainActivity.this, serverIP);
                                }
                            });

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
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
                    img_view.setImageResource(R.mipmap.scene1);
                } else {
                    if (scene_number.equals("scene2")) {
                        img_view.setImageResource(0);
                        img_view.setImageDrawable(getResources().getDrawable(R.mipmap.scene2));
                    } else {
                        if (scene_number.equals("scene3")) {
                            img_view.setImageResource(0);
                            img_view.setImageDrawable(getResources().getDrawable(R.mipmap.scene3));
                        } else {
                            if (scene_number.equals("scene4")) {
                                img_view.setImageResource(0);
                                img_view.setImageDrawable(getResources().getDrawable(R.mipmap.scene4));
                            }
                        }
                    }
                }
            }

            // when using the mic and result
            if (requestCode == 2) {
                ArrayList<String> results;
                results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                //float[] confidence = data.getFloatArrayExtra(RecognizerIntent.EXTRA_CONFIDENCE_SCORES);

                for (String s : results) {
                    System.out.println(s);
                }

                String temp_var = results.toString();
                final String strToServer = temp_var.substring(1, temp_var.length() - 1);

                //display_txt.setText('"' + strToServer + '"' + " - " + Math.round(confidence[0] * 100) + "%");

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
                        } catch (IOException io) {
                            io.printStackTrace();
                        }
                    }
                }).start();
            }
        }
    }
}