package com.example.cal.cjlee_countbook;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Cal on 9/23/2017.
 */

/**
 */
public class CounterListItem {


    public String counterName;
    public String counterDate;
    public Number initVal;
    public Number curVal;
    public String Comment1;

    public CounterListItem(String counterName, String counterDate, Number initVal, Number curVal, String Comment1){
        this.counterName= counterName;
        this.counterDate = counterDate;
        this.initVal = initVal;
        this.curVal = curVal;
        this.Comment1= Comment1;

    }

    public String getCounterName() {
        return counterName;
    }

    public void setCounterName(String counterName) {
        this.counterName = counterName;
    }

    public String getCounterDate() {

        return counterDate;
    }

    public void setCounterDate(String counterDate) {
        this.counterDate = counterDate;
    }

    public Number getCurVal() {
        return curVal;
    }

    public void setCurVal(Number curVal) {
        this.curVal = curVal;
    }

    public String getComment() {
        return Comment1;
    }

    public void setComment(String comment) {
        this.Comment1 = Comment1;
    }

    public Number getInitVal() {
        return initVal;
    }

    public void setInitVal(Number initVal) {
        this.initVal = initVal;
    }
}

