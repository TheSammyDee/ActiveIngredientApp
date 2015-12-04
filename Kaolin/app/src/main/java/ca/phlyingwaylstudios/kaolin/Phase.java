package ca.phlyingwaylstudios.kaolin;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;
import java.util.List;

@ParseClassName("Phase")
public class Phase extends ParseObject {

    private static final String LOG_TAG = "Phase";
    public static final String ID = "objectId";
    public static final String NAME = "name";
    public static final String COLOR = "color";
    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";
    public static final String PROJECT = "project";

    public boolean setUpPhase(Context context, String name, String color, List<Phase> phaseList){
        if (checkName(context, name, phaseList)&& checkColor(context, color, phaseList)){
            setName(name);
            setColor(color);
            return true;
        } else {
            return false;
        }
    }

    public boolean checkName(Context context, String name, List<Phase> phaseList){
        if (null == name || name.isEmpty()){
            Util.showEmptyNameError(context);
            return false;
        } else {
            for (Phase p : phaseList){
                Log.d(LOG_TAG, p.getName() + " vs " + name);
                if (name.equals(p.getName())){
                    if (null == this.getObjectId()) {
                        Util.showSameNameError(context);
                        return false;
                    } else if (!(this.getObjectId().equals(p.getObjectId()))){
                        Util.showSameNameError(context);
                        return false;
                    }
                }
            }
            return true;
        }
    }

    public boolean checkColor(Context context, String color, List<Phase> phaseList){
        try {
            Color.parseColor(color);
        } catch (IllegalArgumentException e){
            Util.showInvalidColorError(context);
            return false;
        }
        for (Phase p : phaseList){
            if (color.equals(p.getColor())){
                if (null == this.getObjectId()) {
                    Util.showSameColorError(context);
                    return false;
                } else if (!(this.getObjectId().equals(p.getObjectId()))){
                    Util.showSameColorError(context);
                    return false;
                }
            }
        }
        return true;
    }

    public String getName(){
        return getString(NAME);
    }

    public void setName(String value) {
        put(NAME, value);
    }

    public String getColor(){
        return getString(COLOR);
    }

    public void setColor(String value) {
        put(COLOR, value);
    }

    public Date getStartDate(){
        return getDate(START_DATE);
    }

    public void setStartDate(Date date){
        put(START_DATE, date);
    }

    public Date getEndDate(){
        return getDate(END_DATE);
    }

    public void setEndDate(Date date){
        put(END_DATE, date);
    }

    public String getProject(){
        return getString(PROJECT);
    }

    public void setProject(Project project){
        put(PROJECT, project);
    }
}
