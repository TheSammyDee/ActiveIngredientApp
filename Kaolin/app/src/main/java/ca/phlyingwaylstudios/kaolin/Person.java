package ca.phlyingwaylstudios.kaolin;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by il on 06/12/2015.
 */

@ParseClassName("Person")
public class Person extends ParseObject{

    private static final String LOG_TAG = "Person";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String DAILY_HOURS = "dailyHours";

    public String getFirstName(){
        return getString(FIRST_NAME);
    }

    public void setFirstName(String name){
        put(FIRST_NAME, name);
    }

    public String getLastName(){
        return getString(LAST_NAME);
    }

    public void setLastName(String name){
        put(LAST_NAME, name);
    }

    public String getName(){
        return getFirstName() + " " + getLastName();
    }

    public Float getDailyHours(){
        return (Float)getNumber(DAILY_HOURS);
    }

    public void setDailyHours(Number hours){
        put(DAILY_HOURS, hours);
    }
}
