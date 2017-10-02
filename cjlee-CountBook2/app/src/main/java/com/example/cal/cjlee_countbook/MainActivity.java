package com.example.cal.cjlee_countbook;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import static android.provider.Telephony.Mms.Part.FILENAME;

/**
 * this is the main activity class of the app, user will be able to enter counter information on this page and view their inputted information
 * in the listview which will show their entries
 */
public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "file.sav";
    private ListView lv;
    List<CounterListItem>counterList = new ArrayList<CounterListItem>();
    CustomAdapter cAdpt;
    Button EnterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //get the id of the edittext boxes which the user will input their data
        final EditText edit1 = (EditText)findViewById(R.id.editText);
        // final EditText edit2 = (EditText)findViewById(R.id.editText2);
        final EditText edit3 = (EditText)findViewById(R.id.editText3);
        final EditText edit4 = (EditText)findViewById(R.id.editText4);
        final EditText edit5 = (EditText)findViewById(R.id.editText5);


        EnterButton = (Button)findViewById(R.id.button1);
//onclick listener for the enter button which also error checks to make sure that no blank strings are entered in the spaces although in countname a blank string will go through otherwise
        //for curval and initval you cannot enter a blank string since that isnt a number format
        EnterButton.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                try {
                    String countName = edit1.getText().toString();
                }
                catch(Exception e) {
                    edit1.setText("Enter a name", TextView.BufferType.EDITABLE);
                    return;}
                try {
                    Number initVal = Integer.parseInt(edit3.getText().toString());
                }
                    catch(Exception f) {
                        edit3.setText("Enter a number", TextView.BufferType.EDITABLE);
                        return;
                }
                try {
                    Number curVal = Integer.parseInt(edit4.getText().toString());
                }
                catch(Exception g) {
                    edit4.setText("Enter a number", TextView.BufferType.EDITABLE);
                    return;
                }

                //get the necessary information for the counter from the user
                String countName = edit1.getText().toString();

                //String countDate = edit2.getText().toString()
                Number initVal=Integer.parseInt(edit3.getText().toString());
                Number curVal = Integer.parseInt(edit4.getText().toString());
                String Comment1 = edit5.getText().toString();
                DateFormat counterDate = new SimpleDateFormat("yyyy-MM-dd");
                Calendar date = Calendar.getInstance();
                String today= counterDate.format(date.getTime());

                counterList.add(new CounterListItem(countName,today,initVal,curVal,Comment1));

                cAdpt.notifyDataSetChanged();
                saveInFile();

            }
        });
    }

    /**
     * when the editcounter activity is done, we will call this method to get the intent from editcounter which contains the edited info inputted by the user , and adds it to the counterlist
     * at the specified position with the edited data
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //cAdpt.onActivityResult(requestCode,resultCode,data);

        // Check which request it is that we're responding to


        if (resultCode == 3) {
            // Get the editied data intent from edit counter activity

            final Intent editIntent = data;
            final String counterName2 = editIntent.getStringExtra("cName");


            String today2 = editIntent.getStringExtra("counterDate");

            //extract the curval ,initval and comment
            Number curVal2 = Integer.parseInt(editIntent.getStringExtra("curVal"));
            Number initVal2 = Integer.parseInt(editIntent.getStringExtra("initVal"));
            String comment2 = editIntent.getStringExtra("comment");
            Integer position = (editIntent.getIntExtra("pos1", 2));

            counterList.set(position, new CounterListItem(counterName2, today2, initVal2, curVal2, comment2));
            cAdpt.notifyDataSetChanged(); // notify the adapter that data has changed

            saveInFile();
        }
    }

// next 3 methods taken from lonelytwitter code : https://github.com/joshua2ua/lonelyTwitter/blob/master/app/src/main/java/ca/ualberta/cs/lonelytwitter/LonelyTwitterActivity.java
    @Override
    protected void onStart() {
        super.onStart();
        ListView lv = (ListView)findViewById(R.id.CounterList1);

        loadFromFile();
        cAdpt= new CustomAdapter(counterList,this);

        lv.setAdapter(cAdpt);
    }

///taken from the lonely twitter code from lab
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();
            // Taken from http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            // 2017-01-26 17:53:59
            counterList = gson.fromJson(in, new TypeToken<ArrayList<CounterListItem>>(){}.getType());

            fis.close();
        } catch (FileNotFoundException e) {
            counterList = new ArrayList<CounterListItem>();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
///taken from lonely twitter code look up for link
    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();

            gson.toJson(counterList, out);

            out.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}




