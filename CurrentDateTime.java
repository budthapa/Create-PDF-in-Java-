package com.budthapa.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
//import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author budthapa
 */
public class CurrentDateTime {

    public Date getCurrentDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        //get current date time with Date()
        Date date = new Date();
        dateFormat.format(date);

//        //get current date time with Calendar()
//        Calendar cal = Calendar.getInstance();
//        System.out.println(dateFormat.format(cal.getTime()));
        return date;
    }
}
