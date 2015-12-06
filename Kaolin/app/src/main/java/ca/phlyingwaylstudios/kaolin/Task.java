package ca.phlyingwaylstudios.kaolin;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;

/**
 * Created by Samantha on 05/12/2015.
 */

@ParseClassName("Task")
public class Task extends ParseObject {

    private static final String LOG_TAG =  "Task";
    public static final String ID = "objectId";
    public static final String NAME = "name";
    public static final String PROJECTED_END = "projectedEnd";
    public static final String SKIP_WEEKENDS = "skipWeekends";
    public static final String PROJECT = "project";
    public static final String PHASE = "phase";
    public static final String START_DATES = "startDates";
    public static final String END_DATES = "endDates";

    public String getName(){
        return getString(NAME);
    }

    public void setName(String value) {
        put(NAME, value);
    }

    public Date getProjectedEnd(){
        return getDate(PROJECTED_END);
    }

    public void setProjectedEnd(Date date){
        put(PROJECTED_END, Util.removeTime(date));
    }

    public Boolean getSkipWeekends(){
        return getBoolean(SKIP_WEEKENDS);
    }

    public void setSkipWeekends(Boolean bool){
        put(SKIP_WEEKENDS, bool);
    }

    public ParseObject getProject(){
        return getParseObject(PROJECT);
    }

    public void setProject(Project project){
        put(PROJECT, project);
    }

    public ParseObject getPhase(){
        return getParseObject(PHASE);
    }

    public void setPhase(Phase phase){
        put(PHASE, phase);
    }

//    public Array getStartDates(){
//    }

}
