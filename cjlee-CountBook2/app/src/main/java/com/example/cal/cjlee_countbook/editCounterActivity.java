package com.example.cal.cjlee_countbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class editCounterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_counter_layout);
        // Get the Intent that started this activity and extract the counter name
        final Intent intent = getIntent();
        final String counterName = intent.getStringExtra("counterName");

        //extract the curval ,initval and comment
        final String curVal = intent.getStringExtra("curVal");
        String initVal = intent.getStringExtra("initVal");
        String comment1 = intent.getStringExtra("comment");
        final Integer position = intent.getIntExtra("position",0);


        // Capture the layout's editText and set the string called name as its text
        final EditText nameEt = (EditText) findViewById(R.id.name);
        nameEt.setText(counterName, TextView.BufferType.EDITABLE);

        //set the curval to the proper edittext
        final EditText curEt = (EditText) findViewById(R.id.current);
        curEt.setText(curVal, TextView.BufferType.EDITABLE);

        //set the inital val to the proper edittext
        final EditText initEt = (EditText) findViewById(R.id.initial);
        initEt.setText(initVal, TextView.BufferType.EDITABLE);

        //set the comment to the proper edittext
        final EditText commentEt = (EditText) findViewById(R.id.comment);
        commentEt.setText(comment1, TextView.BufferType.EDITABLE);


        Button enterButton = (Button)findViewById(R.id.entButton);

        enterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                try {
                    String countName = nameEt.getText().toString();
                }
                catch(Exception e) {
                    nameEt.setText("Enter a name", TextView.BufferType.EDITABLE);
                    return;}
                try {
                    Number initVal = Integer.parseInt(initEt.getText().toString());
                }
                catch(Exception f) {
                    initEt.setText("Enter a number", TextView.BufferType.EDITABLE);
                    return;
                }
                try {
                    Number curVal = Integer.parseInt(curEt.getText().toString());
                }
                catch(Exception g) {
                    curEt.setText("Enter a number", TextView.BufferType.EDITABLE);
                    return;
                }

                //get the necessary information for the counter from the user
                String countName = nameEt.getText().toString();
                Log.d("et name:",countName);
                //String countDate = edit2.getText().toString()
                String initVal2=initEt.getText().toString();
                String curVal2 = curEt.getText().toString();
                String comment2 = commentEt.getText().toString();
                DateFormat counterDate = new SimpleDateFormat("yyyy-MM-dd");
                Calendar date = Calendar.getInstance();
                String today= counterDate.format(date.getTime());


                Intent intent2 = new Intent();
                intent2.putExtra("cName",countName);
                intent2.putExtra("counterDate", today);
                intent2.putExtra("curVal", curVal2);
                intent2.putExtra("initVal", initVal2);
                intent2.putExtra("comment", comment2);
                intent2.putExtra("pos1", position);


                setResult(3,intent2);
                finish();
            }
        });

    }
}
