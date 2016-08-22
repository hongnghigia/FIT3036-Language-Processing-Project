package com.example.aaron.fit3036_project;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.io.DataOutputStream;
import java.net.Socket;

/**
 * Created by Aaron on 8/22/2016.
 */
public abstract class DialogGenerator {
    private static String score;
    private static ImageButton iv1;
    private static ImageButton iv2;
    private static ImageButton iv3;

    /**
        Feedback view generator
     */
    public static void feedbackView(Context This, String server) {
        final String serverIP = server;

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(This);
        dialogBuilder.setTitle("Please rate these results");
        LinearLayout ll = new LinearLayout(This);
        ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0));
        ll.setOrientation(LinearLayout.HORIZONTAL);
        final String scorePrefix = "Score: ";
        final String scoreSuffix = "/3";
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        View v = new View(This);
        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, This.getResources().getDisplayMetrics());
        v.setLayoutParams(new LinearLayout.LayoutParams(width, 0, 1));
        ll.addView(v);

        iv1 = new ImageButton(This);
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

        View v1 = new View(This);
        v1.setLayoutParams(new LinearLayout.LayoutParams(width, 0, 1));
        ll.addView(v1);

        iv2 = new ImageButton(This);
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

        View v2 = new View(This);
        v2.setLayoutParams(new LinearLayout.LayoutParams(width, 0, 1));
        ll.addView(v2);

        iv3 = new ImageButton(This);
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

        View v3 = new View(This);
        v3.setLayoutParams(new LinearLayout.LayoutParams(width, 0, 1));
        ll.addView(v3);

        LinearLayout llv = new LinearLayout(This);
        llv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0));
        llv.setOrientation(LinearLayout.VERTICAL);

        View v4 = new View(This);
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
    New dialog description
     */
    public static void IpChangingDialog(Context This){
        AlertDialog.Builder builder = new AlertDialog.Builder(This);
        LinearLayout layout = new LinearLayout(This);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0));

        final EditText text_box = new EditText(This);
        Button submit = new Button(This);

        layout.addView(text_box);
        layout.addView(submit);

        AlertDialog IPdialog = builder.create();
        IPdialog.show();

        submit.setOnClickListener(new View.OnClickListener(){
            protected String returnValue;

            @Override
            public void onClick(View v){
                if(!text_box.getText().toString().equals("")){
                    returnValue = text_box.getText().toString();
                }
            }
        });
    }
}
