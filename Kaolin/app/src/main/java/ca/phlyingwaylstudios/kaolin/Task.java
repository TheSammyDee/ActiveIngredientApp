package ca.phlyingwaylstudios.kaolin;

import android.content.Context;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public static final String UNSCHEDULED = "unscheduled";
//    public static final String PROJECT = "project";
//    public static final String PHASE = "phase";
    public static final String START_DATES = "startDates";
    public static final String END_DATES = "endDates";

    private ArrayList<Assignment> assignments = new ArrayList<>();
    private ArrayList<Material> materials = new ArrayList<>();


    public boolean setUpTask(Context context, String name, Phase phase, Boolean skipWeekends,
                             Boolean unscheduled, Date startDate, Date endDate, int base, List<Task> taskList){
        if (checkString(context, name, NAME, taskList)){
            startDateHolder = Util.removeTime(startDate);
            endDateHolder = Util.removeTime(endDate);
            if (!unscheduled){
                if (skipWeekends){
                    adjustForWeekends();
                }
                if (!checkDates(context, startDateHolder, endDateHolder)){
                    return false;
                }
            }
            setName(name);
            setPhase(phase);
            setSkipWeekends(skipWeekends);
            setUnscheduled(unscheduled);
            addStartDate(startDateHolder, base);
            addEndDate(endDateHolder, base);
            return true;
        } else {
            return false;
        }
    }

    public List<Assignment> collectAssignments(List<Assignment> list){
        List<Assignment> newList = new ArrayList<Assignment>();
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).getTask() == this){
                newList.add(list.get(i));
                newList.get(newList.size()-1).tempHoldingIndex = i;
            }
        }
        return newList;
    }

    public List<Material> collectMaterials(List<Material> list){
        List<Material> newList = new ArrayList<Material>();
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).getTask() == this){
                newList.add(list.get(i));
                newList.get(newList.size()-1).tempHoldingIndex = i;
            }
        }
        return newList;
    }

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

    public Boolean getUnscheduled(){
        return getBoolean(UNSCHEDULED);
    }

    public void setUnscheduled(Boolean bool){
        put(UNSCHEDULED, bool);
    }

    public Project getProject(){
        return (Project)getParseObject(PROJECT);
    }

    public void setProject(Project project){
        put(PROJECT, ParseObject.createWithoutData(Project.class, project.getObjectId()));
    }

    public Phase getPhase(){
        return (Phase)getParseObject(PHASE);
    }

    public void setPhase(Phase phase){
        put(PHASE, ParseObject.createWithoutData(Phase.class, phase.getObjectId()));
    }

    public ArrayList<Date> getStartDates(){
        return (ArrayList<Date>)get(START_DATES);
    }

    public void setStartDates(ArrayList<Date> startDates){
        put(START_DATES, startDates);
    }

    public void addStartDate(Date date, int base){
        setStartDates(positionDateInArray(date, base, getStartDates()));

    }

    private ArrayList<Date> positionDateInArray(Date date, int base, ArrayList<Date> array){
        if (array.size() == 0 || array.size() == base) {
            array.add(Util.removeTime(date));
            return array;
        } else if (array.size() > base){
            array.remove(base);
            array.add(base, date);
            return array;
        } else {
            Date oldDate = array.get(array.size()-1);
            while (array.size() < base){
                array.add(oldDate);
            }
            array.add(date);
            return array;
        }
    }

    public ArrayList<Date> getEndDates(){
        return (ArrayList<Date>)get(END_DATES);
    }

    public void setEndDates(ArrayList<Date> endDates){
        put(END_DATES, endDates);
    }

    public void addEndDate(Date date, int base){
        setEndDates(positionDateInArray(date, base, getEndDates()));
    }

    public Date getStartDate(int baselineNum){
        return getStartDates().get(baselineNum);
    }

    public Date getEndDate(int baselineNum){
        return getEndDates().get(baselineNum);
    }

    public Date getNewestStartDate(){
        return getStartDate(getStartDates().size() - 1);
    }

    public Date getNewestEndDate(){
        return getEndDate(getEndDates().size() - 1);
    }

    public ArrayList<Assignment> getAssignments(){
        return assignments;
    }

    public void setAssignments(ArrayList<Assignment> array){
        assignments = array;
    }

    public void addAssignment(Assignment a){
        assignments.add(a);
    }

    public void addAssignment(int i, Assignment a){
        assignments.add(i, a);
    }

    public void removeAssignment(int i){
        assignments.remove(i);
    }

    public int numOfAssignments(){
        return assignments.size();
    }

    public Assignment getAssignment(int i){
        return assignments.get(i);
    }

    public void saveAssignment(int i, Assignment a){
        if (i >= numOfAssignments()){
            addAssignment(a);
        } else {
            removeAssignment(i);
            addAssignment(i, a);
        }
    }

    public ArrayList<Material> getMaterials(){
        return materials;
    }

    public void setMaterials(ArrayList<Material> array){
        materials = array;
    }

    public void addMaterial(Material m){
        materials.add(m);
    }

    public void addMaterial(int i, Material m){
        materials.add(i, m);
    }

    public void removeMaterial(int i){
        materials.remove(i);
    }

    public int numOfMaterials(){
        return materials.size();
    }

    public Material getMaterial(int i){
        return materials.get(i);
    }

    public void saveMaterial(int i, Material m){
        if (i >= numOfMaterials()){
            addMaterial(m);
        } else {
            removeMaterial(i);
            addMaterial(i, m);
        }
    }

    public String toString(){
        return getName();
    }

}
