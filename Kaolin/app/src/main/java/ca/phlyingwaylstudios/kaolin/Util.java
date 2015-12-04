package ca.phlyingwaylstudios.kaolin;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by il on 25/11/2015.
 */
public class Util {

    public static void showFindError(Context c){
        Toast.makeText(c, R.string.findError, Toast.LENGTH_LONG).show();
    }

    public static void showSaveError(Context c){
        Toast.makeText(c, R.string.saveError, Toast.LENGTH_LONG).show();
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
}
