package ca.phlyingwaylstudios.kaolin;

import android.content.Context;
import android.graphics.Color;

import com.parse.ParseObject;

import java.util.List;

/**
 * Created by il on 06/12/2015.
 */
public class KaolinObject extends ParseObject{

    private static final String LOG_TAG = "KaolinObject";
    public static final String ID = "objectId";
    public static final String NAME = "name";
    public static final String CODE = "code";
    public static final String COLOR = "color";
    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";
    public static final String PROJECT = "project";
    public static final String PHASE = "phase";
    public static final String TASK = "task";
    public static final String ROLE = "role";
    public static final String PERSON = "person";

    public boolean checkString(Context context, String testString, String key, List<? extends KaolinObject> list){
        if (null == testString || testString.isEmpty()){
            Util.showEmptyError(context, key);
            return false;
        } else {
            //for (KaolinObject p : list){
            for (int i = 0; i < list.size(); i++){
                if (testString.equals(list.get(i).getString(key))){
                    if (null == this.getObjectId()) {
                        Util.showSameError(context, key, testString);
                        return false;
                    } else if (!(this.hasSameId(list.get(i)))){
                        Util.showSameError(context, key, testString);
                        return false;
                    }
                }
            }
            return true;
        }
    }

    public boolean checkColor(Context context, String color, List<? extends KaolinObject> list){
        try {
            Color.parseColor(color);
        } catch (IllegalArgumentException e){
            Util.showInvalidColorError(context);
            return false;
        }
        for (int i = 0; i < list.size(); i++){
            if (color.equals(list.get(i).getString(COLOR))){
                if (null == this.getObjectId()) {
                    Util.showSameColorError(context);
                    return false;
                } else if (!(this.hasSameId(list.get(i)))){
                    Util.showSameColorError(context);
                    return false;
                }
            }
        }
        return true;
    }
}
