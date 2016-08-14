package com.example.aaron.fit3036_project;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button send_btn;
    private Button scene_select;
    private ImageButton mic_btn;
    private TextView display_txt;
    private ImageView img_view;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private String serverIP = "192.168.0.20";
    private Socket connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chosen_image_page);

        // Creating new components
        send_btn = (Button) findViewById(R.id.send_btn);
        scene_select = (Button) findViewById(R.id.back_btn);
        mic_btn = (ImageButton) findViewById(R.id.mic_btn);
        display_txt = (TextView) findViewById(R.id.display_txt);
        img_view = (ImageView) findViewById(R.id.target_img);

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
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak");
                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);

                startActivityForResult(intent, 2);

//                new Thread(new Runnable() {
//                    public void run() {
//                        try {
//                            Socket socket = new Socket("192.168.0.20", 1234);
//                            DataOutputStream DOS = new DataOutputStream(socket.getOutputStream());
//                            DOS.writeUTF("HELLOOO");
//                            socket.close();
//                        }
//                        catch (IOException io){
//                            io.printStackTrace();
//                        }
//                    }
//                }).start();

            }

        });


    }

//    private void listeningDialog() {
//        LayoutInflater layoutInflater = LayoutInflater.from(this);
//        View promptView = layoutInflater.inflate(R.layout.listening_dialog, null);
//        final AlertDialog aDialog = new AlertDialog.Builder(this).create();
//        ImageButton mic = (ImageButton) promptView.findViewById(R.id.mic_btn_listening);
//
//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        lp.copyFrom(aDialog.getWindow().getAttributes());
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = 1500;
//
//        aDialog.setView(promptView);
//        aDialog.show();
//        aDialog.getWindow().setAttributes(lp);
//    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     *
     * When the an image is selected on the Scene Selection screen, this method invokes and take the data from the other activity.
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

            if (requestCode == 2){
                ArrayList<String> results;
                results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                float[] confidence = data.getFloatArrayExtra(RecognizerIntent.EXTRA_CONFIDENCE_SCORES);

                String tmp = results.toString() + " " + Math.round(confidence[0]*100) + "%";
                final String strToServer = results.toString();

                //display_txt.setText(tmp);

                new Thread(new Runnable() {
                    public void run() {
                        try {
                            Socket socket = new Socket(serverIP, 1234);
                            DataOutputStream DOS = new DataOutputStream(socket.getOutputStream());
                            DataInputStream DIS = new DataInputStream(socket.getInputStream());
                            DOS.writeUTF(strToServer);
                            String strFromServer = DIS.readUTF();
                            //display_txt.setText(strFromServer);
                            System.out.println(strFromServer);
                            socket.close();
                        }
                        catch (IOException io){
                            io.printStackTrace();
                        }
                    }
                }).start();
            }
        }
    }
}
