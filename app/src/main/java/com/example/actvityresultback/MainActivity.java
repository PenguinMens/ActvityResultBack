package com.example.actvityresultback;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final int MAXNOTES = 4;

    Button detailButton;
    TextView[] notes;
    TextView noteCounter;
    int detailActivityChecked =0;
    String[] titles = new String[4];
    String[] bodyText = new String[4];
    public int notesCreated = 0;
    String name="";
    ActivityResultLauncher<Intent> detailActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {

                if(result.getResultCode()==RESULT_OK){

                    Intent intent = result.getData();
                    name = intent.getStringExtra("NAME");
                    detailActivityChecked =1;
                    for (int i = 0; i < notes.length; i++) {
                        if(notes[i].getVisibility() == View.INVISIBLE) {

                            createNote(notes[i],i);

                            break;
                        }
                    }
                }
            }
    );
    ActivityResultLauncher<Intent> editActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {

                if(result.getResultCode()==RESULT_OK){
                    MyDataManager myDataManager = MyDataManager.getInstance();
                    Intent intent = result.getData();
                    name = intent.getStringExtra("NAME");
                    int noteNum = myDataManager.getMeta();
                    titles[noteNum] = myDataManager.getTitle();
                    bodyText[noteNum] = myDataManager.getBody();
                    notes[noteNum].setText(myDataManager.getTitle());
                    detailActivityChecked =1;
//                    for (int i = 0; i < 4; i++) {
//                        if(notes[i].getVisibility() == View.INVISIBLE)
//                        {
//                            writeNote(note1,0);
//                        }
//                    }

//                    if(note1.getVisibility() == View.INVISIBLE)
//                        writeNote(note1,0);
//                    else if (note2.getVisibility() == View.INVISIBLE)
//                        writeNote(note2, 1);
//                    else if (note3.getVisibility() == View.INVISIBLE)
//                        writeNote(note3, 2);
//                    else if (note4.getVisibility() == View.INVISIBLE)
//                        writeNote(note4, 3);

//                    printButton.setAlpha(1.0f);
//                    printButton.setEnabled(true);
                }
            }
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        detailButton = findViewById(R.id.detailButton);
        notes = new TextView[]{ findViewById(R.id.note1),
                                findViewById(R.id.note2),
                                findViewById(R.id.note3),
                                findViewById(R.id.note4)};
        noteCounter = findViewById(R.id.errorText);
        if(savedInstanceState!=null) {
            titles = savedInstanceState.getStringArray("titles");
            bodyText = savedInstanceState.getStringArray("bodyText");
            for(int i = 0; i < MAXNOTES; i++)
            {
                if(titles[i] == null)
                    break;
                notes[i].setVisibility(View.VISIBLE);
                notes[i].setText(titles[i]);
                notesCreated++;
                String tempStr = String.format(Locale.getDefault(),"%d/%d notes created",notesCreated,MAXNOTES);
                noteCounter.setText(tempStr);
            }
        }
        detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(notesCreated < MAXNOTES)
                {
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    detailActivityLauncher.launch(intent);
                }
                else
                {
                    noteCounter.setTextColor(Color.RED);
                }


            }
        });

        notes[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editNote(0);
            }
        });
        notes[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editNote(1);
            }
        });
        notes[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editNote(2);
            }
        });
        notes[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editNote(3);
            }
        });
    }
    private void createNote(TextView note, int noteNumber)
    {
        notes[noteNumber].setVisibility(View.VISIBLE);
        MyDataManager myDataManager = MyDataManager.getInstance();
        note.setText(myDataManager.getTitle());
        titles[noteNumber] = myDataManager.getTitle();
        bodyText[noteNumber] = myDataManager.getBody();
        notesCreated++;
        String tempStr = String.format(Locale.getDefault(),"%d/%d notes created",notesCreated,MAXNOTES);
        noteCounter.setText(tempStr);
    }

    private void editNote(int noteNumber)
    {
        Intent intent = new Intent(MainActivity.this, PrintActivity.class);
        MyDataManager myDataManager = MyDataManager.getInstance();
        myDataManager.setTitle(titles[noteNumber]);
        myDataManager.setBody(bodyText[noteNumber]);
        myDataManager.setMeta(noteNumber);
        editActivityLauncher.launch(intent);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        outState.putStringArray("titles", titles);
        outState.putStringArray("bodyText", bodyText);

    }
}
