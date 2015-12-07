package ca.phlyingwaylstudios.kaolin;

import android.content.Context;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by il on 25/11/2015.
 */
public class Util {

    public static final String IS_NEW = "isNew";

    public static void showFindError(Context c){
        Toast.makeText(c, R.string.findError, Toast.LENGTH_LONG).show();
    }

    public static void showSaveError(Context c){
        Toast.makeText(c, R.string.saveError, Toast.LENGTH_LONG).show();
    }

    public static void showEmptyError(Context context, String field){
        Toast.makeText(context, "The " + field + " cannot be empty", Toast.LENGTH_LONG).show();
    }

    public static void showSameError(Context context, String field, String entry){
        Toast.makeText(context, "The " + field + " " + entry + " has already been used", Toast.LENGTH_LONG).show();
    }

    public static void showEmptyNameError(Context c){
        Toast.makeText(c, "Name cannot be empty", Toast.LENGTH_LONG).show();
    }

    public static void showSameNameError(Context context){
        Toast.makeText(context, "Name has already been used", Toast.LENGTH_LONG).show();
    }

    public static void showInvalidColorError(Context context){
        Toast.makeText(context, "Not a valid color", Toast.LENGTH_LONG).show();
    }

    public static void showSameColorError(Context context){
        Toast.makeText(context, "Color has already been used", Toast.LENGTH_LONG).show();
    }

    public static Date removeTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

}
