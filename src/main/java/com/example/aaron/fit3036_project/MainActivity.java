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

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

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
    private String serverIP = "192.168.0.8";
    private ImageButton iv1;
    private ImageButton iv2;
    private ImageButton iv3;
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

        scene_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SceneSelect.class);
                startActivityForResult(i, 1);

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
                                    feedbackView();
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

    public void feedbackDialog() {
        // Dialog alert asking for feedback
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        final String[] choices = {"Good", "Okay", "Bad"};
        dialogBuilder.setTitle("Rate these results");

        // what to do when an option is clicked
        dialogBuilder.setSingleChoiceItems(choices, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int selection){
                try {
                    Socket socket = new Socket(serverIP, 1234);
                    DataOutputStream DOS = new DataOutputStream(socket.getOutputStream());
                    DOS.writeUTF("Feedback: " + choices[selection] + "\n");
                    socket.close();
                }
                catch (Exception io) {
                    io.printStackTrace();
                }
                dialog.dismiss();
            }
        });

        // initialise alert dialog
        AlertDialog fbDialog = dialogBuilder.create();
        Window window = fbDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        window.setAttributes(wlp);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        fbDialog.show();
    }

    public void feedbackView() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        dialogBuilder.setTitle("Please rate these results");
        LinearLayout ll = new LinearLayout(MainActivity.this);
        ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0));
        ll.setOrientation(LinearLayout.HORIZONTAL);
        final String scorePrefix = "Score: ";
        final String scoreSuffix = "/3";
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        View v = new View(MainActivity.this);
        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
        v.setLayoutParams(new LinearLayout.LayoutParams(width, 0, 1));
        ll.addView(v);

        iv1 = new ImageButton(MainActivity.this);
        iv1.setBackgroundResource(R.drawable.face1cgray);
        iv1.setLayoutParams(p);
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score = "1";
                iv1.setBackgroundResource(R.drawable.face1c);
                iv2.setBackgroundResource(R.drawable.face2cgray);
                iv3.setBackgroundResource(R.drawable.face3cgray);
            }
        });
        ll.addView(iv1);

        View v1 = new View(MainActivity.this);
        v1.setLayoutParams(new LinearLayout.LayoutParams(width, 0, 1));
        ll.addView(v1);

        iv2 = new ImageButton(MainActivity.this);
        iv2.setBackgroundResource(R.drawable.face2cgray);
        iv1.setLayoutParams(p);
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score = "2";
                iv1.setBackgroundResource(R.drawable.face1cgray);
                iv2.setBackgroundResource(R.drawable.face2c);
                iv3.setBackgroundResource(R.drawable.face3cgray);
            }
        });
        ll.addView(iv2);

        View v2 = new View(MainActivity.this);
        v2.setLayoutParams(new LinearLayout.LayoutParams(width, 0, 1));
        ll.addView(v2);

        iv3 = new ImageButton(MainActivity.this);
        iv3.setBackgroundResource(R.drawable.face3cgray);
        iv1.setLayoutParams(p);
        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score = "3";
                iv1.setBackgroundResource(R.drawable.face1cgray);
                iv2.setBackgroundResource(R.drawable.face2cgray);
                iv3.setBackgroundResource(R.drawable.face3c);
            }
        });
        ll.addView(iv3);

        View v3 = new View(MainActivity.this);
        v3.setLayoutParams(new LinearLayout.LayoutParams(width, 0, 1));
        ll.addView(v3);

        LinearLayout llv = new LinearLayout(MainActivity.this);
        llv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0));
        llv.setOrientation(LinearLayout.VERTICAL);

        View v4 = new View(MainActivity.this);
        v4.setLayoutParams(new LinearLayout.LayoutParams(20, 100));
        llv.addView(v4);
        llv.addView(ll);

        dialogBuilder.setView(llv);

        dialogBuilder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                try {
                    Socket socket = new Socket(serverIP, 1234);
                    DataOutputStream DOS = new DataOutputStream(socket.getOutputStream());
                    DOS.writeUTF(scorePrefix + score + scoreSuffix);
                    socket.close();
                } catch (Exception io) {
                    io.printStackTrace();
                }
            }
        });

        AlertDialog fbDialog = dialogBuilder.create();
        Window window = fbDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        window.setAttributes(wlp);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        fbDialog.show();
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
                                    feedbackView();
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