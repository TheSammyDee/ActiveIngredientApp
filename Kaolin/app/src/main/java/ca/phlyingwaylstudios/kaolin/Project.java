package ca.phlyingwaylstudios.kaolin;

import android.content.Context;
import android.graphics.Color;
import android.widget.Toast;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;
import java.util.List;

@ParseClassName("Project")
public class Project extends ParseObject {

    private static final String LOG_TAG = "Project";
    public static final String ID = "objectId";
    public static final String NAME = "name";
    public static final String CODE = "code";
    public static final String COLOR = "color";
    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";

    public boolean setUpProject(Context context, String name, String code, String color, Date startDate, List<Project> projectList){
        if (checkName(context, name, projectList) && checkCode(context, code, projectList) && checkColor(context, color, projectList)){
            setName(name);
            setCode(code);
            setColor(color);
            setStartDate(startDate);
            setEndDate(startDate);
            return true;
        } else {
            return false;
        }
    }

    public boolean checkName(Context context, String name, List<Project> projectList){
        if (null == name || name.isEmpty()){
            Util.showEmptyNameError(context);
            return false;
        } else {
            for (Project p : projectList){
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

    public boolean checkCode(Context context, String code, List<Project> projectList){
        if (null == code || code.isEmpty()){
            Toast.makeText(context, "Code cannot be empty", Toast.LENGTH_LONG).show();
            return false;
        } else {
            for (Project p : projectList){
                if (code.equals(p.getCode())){
                    if (null == this.getObjectId()) {
                        Toast.makeText(context, "Code has already been used", Toast.LENGTH_LONG).show();
                        return false;
                    } else if (!(this.getObjectId().equals(p.getObjectId()))){
                        Toast.makeText(context, "Code has already been used", Toast.LENGTH_LONG).show();
                        return false;
                    }
                }
            }
            return true;
        }
    }

    public boolean checkColor(Context context, String color, List<Project> projectList){
        try {
            Color.parseColor(color);
        } catch (IllegalArgumentException e){
            Util.showInvalidColorError(context);
            return false;
        }
        for (Project p : projectList){
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

    public String getCode(){
        return getString(CODE);
    }

    public void setCode(String value) {
        put(CODE, value);
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
}
