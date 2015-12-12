package ca.phlyingwaylstudios.kaolin;

import android.content.Context;
import android.graphics.Color;

import com.parse.ParseObject;

import java.util.Calendar;
import java.util.Date;
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
    protected Date startDateHolder;
    protected Date endDateHolder;

    protected boolean checkString(Context context, String testString, String key, List<? extends KaolinObject> list){
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

    protected boolean checkColor(Context context, String color, List<? extends KaolinObject> list){
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

    protected boolean checkDates(Context context, Date startDate, Date endDate){
        if (startDate.after(endDate)){
            Util.showDateOrderError(context);
            return false;
        } else {
            return true;
        }
    }

    protected void adjustForWeekends(){
        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        startCal.setTime(startDateHolder);
        endCal.setTime(endDateHolder);
        if (startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
            startCal.add(Calendar.DATE, 2);
        } else if (startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            startCal.add(Calendar.DATE, 1);
        }
        if (endCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
            endCal.add(Calendar.DATE, -1);
        } else if (endCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            endCal.add(Calendar.DATE, -2);
        }
        startDateHolder = startCal.getTime();
        endDateHolder = endCal.getTime();
    }

//    public List<?> collectConnected(String key, List<? extends KaolinObject> list){
//        List<KaolinObject> newList = new ArrayList<KaolinObject>();
//        for (int i = 0; i < list.size(); i++){
//            if (list.get(i).getParseObject(key) == this){
//                newList.add(list.get(i));
//            }
//        }
//        return newList;
//    }
}
