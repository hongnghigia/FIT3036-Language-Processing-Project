package com.example.aaron.fit3036_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * Created by long on 18/09/2016.
 */
public class IPSettings extends AppCompatActivity {

    EditText ip;
    CheckBox cb;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ip_settings);
        Intent intent = getIntent();

        ip = (EditText) findViewById(R.id.ip_edit);
        ip.setText(intent.getStringExtra("serverIP"));

        cb = (CheckBox) findViewById(R.id.ip_checkbox);
        cb.setChecked(intent.getExtras().getBoolean("showIP"));
    }

    /**
     * cancel and save button for the action bar
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = getIntent();
        switch(item.getItemId()){
            case R.id.cancel_ip:
                this.finish();
                return true;
            case R.id.save_ip:
                String newIP = ip.getText().toString();
                intent.putExtra("newIP", newIP);
                boolean checked = cb.isChecked();
                intent.putExtra("checked", checked);
                setResult(RESULT_OK, intent);

                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_ip, menu);
        return true;
    }
}
