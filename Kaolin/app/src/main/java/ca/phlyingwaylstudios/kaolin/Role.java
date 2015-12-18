package ca.phlyingwaylstudios.kaolin;

import com.parse.ParseClassName;

/**
 * Created by il on 06/12/2015.
 */

@ParseClassName("Role")
public class Role extends KaolinObject{

    private static final String LOG_TAG = "Role";
    //public static final String ID = "objectId";
    //public static final String NAME = "name";
    //public static final String RATE = "rate";

    public String getName(){
        return getString(NAME);
    }

    public void setName(String value) {
        put(NAME, value);
    }

    public double getRate(){
        return getDouble(RATE);
    }

    public void setRate(double rate){
        put(RATE, rate);
    }

    public String toString(){
        return getName();
    }
}
