package com.example.cal.cjlee_countbook;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.TextViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.R.attr.data;
import static android.R.attr.value;

/**
 * extends the array adapter class so the user will be able to see more things in the listview entry item
 * adds multiple textviews and buttons
 */

public class CustomAdapter extends ArrayAdapter<CounterListItem> {
    private List<CounterListItem> entries;
    private Context context;

    public CustomAdapter(List<CounterListItem> counterList,Context context1){
        super(context1,R.layout.img_row_layout, counterList);
            this.entries = counterList;
        this.context=context1;
    }


    public int getCount(){
        return entries.size();
    }
    public long getItemId(int position){
        return entries.get(position).hashCode();
    }

    public CounterListItem getItem(int position){
        return entries.get(position);
    }


    /**
     * inflates the view so that their are 4 textviews and 4 buttons that the user can see,
     * adds a increase ,decrease ,reset and delete button
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(final int position, View convertView, final ViewGroup parent){
        ItemHolder holder = new ItemHolder();

        if(convertView== null){
           LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           convertView = inflater.inflate(R.layout.img_row_layout, parent, false);


       }

            final CounterListItem clr = entries.get(position);

        //find the textviews in the xml file and gets the id
        TextView counterNameView = (TextView)convertView.findViewById(R.id.TextView);
        TextView counterDateView = (TextView)convertView.findViewById(R.id.TextView2);
        TextView initValView = (TextView)convertView.findViewById(R.id.TextView3);
        final TextView curValView = (TextView)convertView.findViewById(R.id.TextView4);
        TextView Comment1View = (TextView)convertView.findViewById(R.id.TextView5);

        //gets and sets the necessary information for the textview within the listview entry
        if (counterNameView!= null) counterNameView.setText(clr.getCounterName());


        if (counterDateView!= null) {


            counterDateView.setText(clr.getCounterDate());
        }
        if (initValView!= null) initValView.setText(clr.getInitVal().toString());
        if (curValView!= null) curValView.setText(clr.getCurVal().toString());
        if (Comment1View!= null) Comment1View.setText(clr.getComment());

        //onclicklistener for incbutton which will increase the current value by 1 on the clicked counter
       Button incButton = (Button)convertView.findViewById(R.id.incButton);

        //lets the item in listview be clickable and if clicked will send the user to the edit counter activity
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity main = (MainActivity) context;
                Intent myIntent = new Intent(main,editCounterActivity.class);

                //put the extras into the intent so name,curval ,init val and comment
                myIntent.putExtra("counterName", clr.getCounterName());
                myIntent.putExtra("counterDate", clr.getCounterDate());

                myIntent.putExtra("curVal", clr.getCurVal().toString());
                myIntent.putExtra("initVal", clr.getInitVal().toString());
                myIntent.putExtra("comment", clr.getComment());
                myIntent.putExtra("position", position);

                main.startActivityForResult(myIntent,2);
            }
        });

        incButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    CounterListItem clr = entries.get(position);
                    int curVal = Integer.parseInt(String.valueOf(clr.getCurVal()));
                    curVal= curVal+1; // increase the current value by 1
                    clr.setCurVal(curVal);
                curValView.setText(clr.getCurVal().toString()); // change the curvalview text to new value that was obtained
                notifyDataSetChanged();
                }
        });

        //onclicklistener for decbutton which will decrease the current value by 1 on the clicked counter
        Button decButton = (Button)convertView.findViewById(R.id.decButton);

        decButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CounterListItem clr = entries.get(position);
                int curVal = Integer.parseInt(String.valueOf(clr.getCurVal()));
                if (curVal==0) return;
                curVal= curVal-1; // subtract the current value by 1
                clr.setCurVal(curVal); // set the current value to new value
                curValView.setText(clr.getCurVal().toString());
                notifyDataSetChanged();
            }
        });

        //onclicklistener for resbutton which will reset the current value to the initial value on the clicked counter
        Button resButton = (Button)convertView.findViewById(R.id.resButton);

        resButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CounterListItem clr = entries.get(position); // get the position of the clicked counter that will be used
                int curVal = Integer.parseInt(String.valueOf(clr.getCurVal())); //will get the string of curval from textview and turn it into int
                int initVal = Integer.parseInt(String.valueOf(clr.getInitVal()));
                curVal= initVal;
                clr.setCurVal(curVal);
                curValView.setText(clr.getCurVal().toString());
                notifyDataSetChanged();
            }
        });

        //onclicklistener for delButton which will delete the specified counter from the listview and list
        Button delButton = (Button)convertView.findViewById(R.id.delButton);

        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CounterListItem clr = entries.get(position); // get the position of the clicked counter that will be used
                entries.remove(clr);
                notifyDataSetChanged();
                }
        });





    return convertView;
    }


    private static class ItemHolder{
        public TextView counterNameView;
        public TextView counterDateView;
        public TextView initValView;
        public TextView curValView;
        public TextView Comment1View;

    }


}
