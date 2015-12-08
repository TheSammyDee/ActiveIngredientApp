package ca.phlyingwaylstudios.kaolin;

import com.parse.ParseClassName;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Samantha on 05/12/2015.
 */

@ParseClassName("Task")
public class Task extends KaolinObject{

    private static final String LOG_TAG =  "Task";
//    public static final String ID = "objectId";
//    public static final String NAME = "name";
    public static final String PROJECTED_END = "projectedEnd";
    public static final String SKIP_WEEKENDS = "skipWeekends";
//    public static final String PROJECT = "project";
//    public static final String PHASE = "phase";
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

    public Project getProject(){
        return (Project)getParseObject(PROJECT);
    }

    public void setProject(Project project){
        put(PROJECT, project);
    }

    public Phase getPhase(){
        return (Phase)getParseObject(PHASE);
    }

    public void setPhase(Phase phase){
        put(PHASE, phase);
    }

    public ArrayList<Date> getStartDates(){
        return (ArrayList<Date>)get(START_DATES);
    }

    public void setStartDates(ArrayList<Date> startDates){
        put(START_DATES, startDates);
    }

    public void addStartDate(Date date){
        add(START_DATES, Util.removeTime(date));
    }
    public ArrayList<Date> getEndDates(){
        return (ArrayList<Date>)get(END_DATES);
    }

    public void setEndDates(ArrayList<Date> endDates){
        put(END_DATES, endDates);
    }

    public void addEndDate(Date date){
        add(END_DATES, Util.removeTime(date));
    }

    public String toString(){
        return getName();
    }

}
