package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnInsertData;
    Button  btnRetreiveData;
Button btnLocalisation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInsertData = findViewById(R.id.btnInsertData);
        btnRetreiveData = findViewById(R.id.btnRetreiveData);
        btnLocalisation=findViewById(R.id.btnLocalisation);
        btnInsertData.setOnClickListener(this);
        btnRetreiveData.setOnClickListener(this);
        btnLocalisation.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btnInsertData:
                startActivity(new Intent(MainActivity.this,InsertingDataActivity.class));
                break;

            case R.id.btnRetreiveData:
                startActivity(new Intent(MainActivity.this, RetrieveDataActivity.class));
                break;
            case R.id.btnLocalisation:
                startActivity(new Intent(MainActivity.this,MapsActivity.class));
                break;


        }

    }
}