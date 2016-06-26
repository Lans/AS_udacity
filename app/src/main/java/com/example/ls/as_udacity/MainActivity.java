package com.example.ls.as_udacity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button1, button2, button3, button4, button5, button6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                Toast.makeText(this, "app1", Toast.LENGTH_SHORT).show();
            case R.id.button2:
                Toast.makeText(this, "app2", Toast.LENGTH_SHORT).show();
            case R.id.button3:
                Toast.makeText(this, "app3", Toast.LENGTH_SHORT).show();
            case R.id.button4:
                Toast.makeText(this, "app4", Toast.LENGTH_SHORT).show();
            case R.id.button5:
                Toast.makeText(this, "app5", Toast.LENGTH_SHORT).show();
            case R.id.button6:
                Toast.makeText(this, "app6", Toast.LENGTH_SHORT).show();

        }
    }
}
