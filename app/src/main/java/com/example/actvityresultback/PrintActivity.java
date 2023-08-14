package com.example.actvityresultback;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PrintActivity extends AppCompatActivity {
    EditText bodyEditText;
    EditText nameEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        nameEditText = findViewById(R.id.nameEditText);
        bodyEditText = findViewById(R.id.bodyEditText);
        Button backButton = findViewById(R.id.backButtonDetail);
        updateViewWithSingleTon();
        backButton.setText("Update");
        if(savedInstanceState!=null) {
            nameEditText.setText(savedInstanceState.getString("title"));
            bodyEditText.setText(savedInstanceState.getString("body"));
        }
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDataManager myDataManager = MyDataManager.getInstance();

                myDataManager.setTitle(nameEditText.getText().toString());
                myDataManager.setBody(bodyEditText.getText().toString());
                Intent intent =  new Intent();
                intent.putExtra("NAME",nameEditText.getText().toString());

                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
    private void updateViewWithSingleTon(){
        MyDataManager myDataManager = MyDataManager.getInstance();
        nameEditText.setText(myDataManager.getTitle());
        bodyEditText.setText(myDataManager.getBody());
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("body",bodyEditText.getText().toString());
        outState.putString("title",nameEditText.getText().toString());

    }
}