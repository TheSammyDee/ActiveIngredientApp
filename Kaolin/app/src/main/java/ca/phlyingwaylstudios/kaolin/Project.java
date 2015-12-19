package ca.phlyingwaylstudios.kaolin;

import android.content.Context;
import android.util.Log;

import com.parse.ParseClassName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ParseClassName("Project")
public class Project extends KaolinObject {

    private static final String LOG_TAG = "Project";
//    public static final String ID = "objectId";
//    public static final String NAME = "name";
//    public static final String CODE = "code";
//    public static final String COLOR = "color";
//    public static final String START_DATE = "startDate";
//    public static final String END_DATE = "endDate";
    public static final String BASE = "base";
    public static final String IS_TEMPLATE = "isTemplate";

    private List<Task> tasks;

    public boolean setUpProject(Context context, String name, String code, String color,
                                Date startDate, List<Project> projectList){
        //if (checkName(context, name, projectList) && checkCode(context, code, projectList) && checkColor(context, color, projectList)){
        if (checkString(context, name, NAME, projectList) &&
                checkString(context, code, CODE, projectList) &&
                checkColor(context, color, projectList)){
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

//    public boolean checkName(Context context, String name, List<Project> projectList){
//        if (null == name || name.isEmpty()){
//            Util.showEmptyNameError(context);
//            return false;
//        } else {
//            for (Project p : projectList){
//                if (name.equals(p.getName())){
//                    if (null == this.getObjectId()) {
//                        Util.showSameNameError(context);
//                        return false;
//                    } else if (!(this.getObjectId().equals(p.getObjectId()))){
//                        Util.showSameNameError(context);
//                        return false;
//                    }
//                }
//            }
//            return true;
//        }
//    }

//    public boolean checkCode(Context context, String code, List<Project> projectList){
//        if (null == code || code.isEmpty()){
//            Toast.makeText(context, "Code cannot be empty", Toast.LENGTH_LONG).show();
//            return false;
//        } else {
//            for (Project p : projectList){
//                if (code.equals(p.getCode())){
//                    if (null == this.getObjectId()) {
//                        Toast.makeText(context, "Code has already been used", Toast.LENGTH_LONG).show();
//                        return false;
//                    } else if (!(this.getObjectId().equals(p.getObjectId()))){
//                        Toast.makeText(context, "Code has already been used", Toast.LENGTH_LONG).show();
//                        return false;
//                    }
//                }
//            }
//            return true;
//        }
//    }

//    public boolean checkColor(Context context, String color, List<Project> projectList){
//        try {
//            Color.parseColor(color);
//        } catch (IllegalArgumentException e){
//            Util.showInvalidColorError(context);
//            return false;
//        }
//        for (Project p : projectList){
//            if (color.equals(p.getColor())){
//                if (null == this.getObjectId()) {
//                    Util.showSameColorError(context);
//                    return false;
//                } else if (!(this.getObjectId().equals(p.getObjectId()))){
//                    Util.showSameColorError(context);
//                    return false;
//                }
//            }
//        }
//        return true;
//    }

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
        put(START_DATE, Util.removeTime(date));
    }

    public Date getEndDate(){
        return getDate(END_DATE);
    }

    public void setEndDate(Date date){
        put(END_DATE, Util.removeTime(date));
    }

    public int getBase(){
        return getInt(BASE);
    }

    public void setBase(int base){
        put(BASE, base);
    }

    public List<Task> getTasks(){
        return tasks;
    }

    public void setTasks(List<Task> array){
        tasks = sortTasks(array);
    }

    private List<Task> sortTasks(List<Task> list){
        List<Task> newList = new ArrayList<Task>();
        newList.add(list.get(0));
        Log.d(LOG_TAG, list.get(0).getName() + " added, " + list.size() + " remaining");
        list.remove(0);
        while (list.size() > 0){
            Log.d(LOG_TAG, "looping for list size " + list.size());
            for (int i = 0; i <= newList.size(); i++){
                Log.d(LOG_TAG, "comparing newList-" + i + " vs next in list");
                if (newList.size() == i){
                    newList.add(list.get(0));
                    list.remove(0);
                    break;
                } else if (newList.get(i).getNewestStartDate().after(list.get(0).getNewestStartDate())){
                    newList.add(i, list.get(0));
                    list.remove(0);
                    break;
                } else if (newList.get(i).getNewestStartDate().equals(list.get(0).getNewestStartDate())){
                    if (newList.get(i).getNewestEndDate().after(list.get(0).getNewestEndDate())){
                        newList.add(i, list.get(0));
                        list.remove(0);
                        break;
                    }
                }
            }
        }
        return newList;
    }

    public void addTask(Task t){
        tasks.add(t);
    }

    public void addTask(int i, Task t){
        tasks.add(i, t);
    }

    public void removeTask(int i){
        tasks.remove(i);
    }

    public int numOfTasks(){
        return tasks.size();
    }

    public Task getTask(int i){
        return tasks.get(i);
    }

    public void saveTask(int i, Task t){
        if (i >= numOfTasks()){
            addTask(t);
        } else {
            removeTask(i);
            addTask(i, t);
        }
    }

    public boolean isTemplate(){
        return getBoolean(IS_TEMPLATE);
    }

    public void setIsTemplate(boolean bool){
        put(IS_TEMPLATE, bool);
    }
}
