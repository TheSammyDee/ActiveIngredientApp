package ca.phlyingwaylstudios.kaolin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Samantha on 30/12/2015.
 */
public class AddWorkArrayAdapter extends ArrayAdapter<Assignment> {

    private final Context context;
    private List<Assignment> assignments;
    private ArrayList<WorkHours> workHoursList;
    private Date currentDate;
    private int workHoursNum;
    private boolean countByOne = true;
    private int count = 10;

    public AddWorkArrayAdapter(Context context, List<Assignment> assignments, Date currentDate){
        super(context, -1, assignments);
        this.context = context;
        this.assignments = assignments;
        this.currentDate = currentDate;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.add_work_list_item, parent, false);
        TextView personNameTxt = (TextView) rowView.findViewById(R.id.personNameTxt);
        TextView roleNameTxt = (TextView) rowView.findViewById(R.id.roleNameTxt);
        final TextView workWholeTxt = (TextView) rowView.findViewById(R.id.workWholeTxt);
        final TextView workDecimalTxt = (TextView) rowView.findViewById(R.id.workDecimalTxt);
        final TextView remainWholeTxt = (TextView) rowView.findViewById(R.id.remainWholeTxt);
        final TextView remainDecimalTxt = (TextView) rowView.findViewById(R.id.remainDecimalTxt);
        Button workAddWholeBtn = (Button) rowView.findViewById(R.id.workAddWholeBtn);
        Button workSubWholeBtn = (Button) rowView.findViewById(R.id.workSubWholeBtn);
        final Button workAddDecimalBtn = (Button) rowView.findViewById(R.id.workAddDecimalBtn);
        final Button workSubDecimalBtn = (Button) rowView.findViewById(R.id.workSubDecimalBtn);
        Button remainAddWholeBtn = (Button) rowView.findViewById(R.id.remainAddWholeBtn);
        Button remainSubWholeBtn = (Button) rowView.findViewById(R.id.remainSubWholeBtn);
        final Button remainAddDecimalBtn = (Button) rowView.findViewById(R.id.remainAddDecimalBtn);
        final Button remainSubDecimalBtn = (Button) rowView.findViewById(R.id.remainSubDecimalBtn);
        final Button swapDecimalBtn = (Button) rowView.findViewById(R.id.swapDecimalBtn);
        personNameTxt.setText(assignments.get(position).getPerson().getName());
        roleNameTxt.setText(assignments.get(position).getRole().getName());
        workHoursList = assignments.get(position).getWorkHourses();
        double hours;
        if (hoursExistForDay()){
            hours = workHoursList.get(workHoursNum).getHours();
            workWholeTxt.setText(String.valueOf((int)hours));
            workDecimalTxt.setText(String.valueOf(Util.decimalsOf(hours)));
        }
        hours = assignments.get(position).getHoursRemaining();
        remainWholeTxt.setText(String.valueOf((int)hours));
        remainDecimalTxt.setText(String.valueOf(Util.decimalsOf(hours)));

        workAddWholeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(workWholeTxt.getText().toString());
                num++;
                workWholeTxt.setText(String.valueOf(num));
                num = Integer.parseInt(remainWholeTxt.getText().toString());
                if (0 == num) {
                    remainDecimalTxt.setText("0");
                } else {
                    num--;
                    remainWholeTxt.setText(String.valueOf(num));
                }
            }
        });
        workSubWholeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(workWholeTxt.getText().toString());
                if (0 == num){
                    int workDecNum = Integer.parseInt(workDecimalTxt.getText().toString());
                    if (workDecNum != 0) {
                        int remainDecNumber = Integer.parseInt(remainDecimalTxt.getText().toString())
                                + workDecNum;
                        if (remainDecNumber >= 100){
                            int remainWholeNum = Integer.parseInt(remainWholeTxt.getText().toString());
                            remainWholeNum++;
                            remainWholeTxt.setText(String.valueOf(remainWholeNum));
                            remainDecNumber = remainDecNumber - 100;
                        }
                        remainDecimalTxt.setText(String.valueOf(remainDecNumber));
                        workDecimalTxt.setText("0");
                    }
                } else {
                    num--;
                    workWholeTxt.setText(String.valueOf(num));
                }
                num = Integer.parseInt(remainWholeTxt.getText().toString());
                num++;
                remainWholeTxt.setText(String.valueOf(num));
            }
        });
        workAddDecimalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(workDecimalTxt.getText().toString());
                num = num + count;
                if (num >= 100){
                    int wholeNum = Integer.parseInt(workWholeTxt.getText().toString());
                    wholeNum++;
                    workWholeTxt.setText(String.valueOf(wholeNum));
                    num = num - 100;
                }
                workDecimalTxt.setText(String.valueOf(num));
                int remainDecNum = Integer.parseInt(remainDecimalTxt.getText().toString());
                remainDecNum = remainDecNum - count;
                if (remainDecNum < 0){
                    int remainWholeNum = Integer.parseInt(remainWholeTxt.getText().toString());
                    if (0 == remainWholeNum){
                        remainDecNum = 0;
                    } else {
                        remainWholeNum--;
                        remainWholeTxt.setText(String.valueOf(remainWholeNum));
                        remainDecNum = remainDecNum + 100;
                    }
                }
                remainDecimalTxt.setText(String.valueOf(remainDecNum));
            }
        });
        workSubDecimalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(workDecimalTxt.getText().toString());
                num = num - count;
                if (num < 0){
                    int wholeNum = Integer.parseInt(workWholeTxt.getText().toString());
                    if (0 == wholeNum){
                        num = 0;
                    } else {
                        wholeNum--;
                        workWholeTxt.setText(String.valueOf(wholeNum));
                        num = num + 100;
                    }
                }
                workDecimalTxt.setText(String.valueOf(num));
                int remainDecNum = Integer.parseInt(remainDecimalTxt.getText().toString());
                remainDecNum = remainDecNum + count;
                if (remainDecNum >= 100){
                    int remainWholeNum = Integer.parseInt(remainWholeTxt.getText().toString());
                    remainWholeNum++;
                    remainWholeTxt.setText(String.valueOf(remainWholeNum));
                    remainDecNum = remainDecNum - 100;
                }
                remainDecimalTxt.setText(String.valueOf(remainDecNum));
            }
        });
        remainAddWholeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(remainWholeTxt.getText().toString());
                num++;
                remainWholeTxt.setText(String.valueOf(num));
            }
        });
        remainSubWholeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(remainWholeTxt.getText().toString());
                if (0 == num){
                    int decNum = Integer.parseInt(remainDecimalTxt.getText().toString());
                    if (decNum > 0){
                        remainDecimalTxt.setText("0");
                    }
                } else {
                    num--;
                    remainWholeTxt.setText(String.valueOf(num));
                }
            }
        });
        remainAddDecimalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(remainDecimalTxt.getText().toString());
                num = num + count;
                if (num >= 100){
                    int wholeNum = Integer.parseInt(remainWholeTxt.getText().toString());
                    wholeNum++;
                    remainWholeTxt.setText(String.valueOf(wholeNum));
                    num = num - 100;
                }
                remainDecimalTxt.setText(String.valueOf(num));
            }
        });
        remainSubDecimalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(remainDecimalTxt.getText().toString());
                num = num - count;
                if (num < 0){
                    int wholeNum = Integer.parseInt(remainWholeTxt.getText().toString());
                    if (0 == wholeNum){
                        num = 0;
                    } else {
                        wholeNum--;
                        remainWholeTxt.setText(String.valueOf(wholeNum));
                        num = num + 100;
                    }
                }
                remainDecimalTxt.setText(String.valueOf(num));
            }
        });
        swapDecimalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countByOne = !countByOne;
                String toAdd;
                String toSwap;
                if (countByOne){
                    count = 10;
                    toAdd = "1";
                    toSwap = "25";
                } else {
                    count = 25;
                    toAdd = "25";
                    toSwap = "1";
                }
                workAddDecimalBtn.setText("+0." + toAdd);
                workSubDecimalBtn.setText("-0." + toAdd);
                remainAddDecimalBtn.setText("+0." + toAdd);
                remainSubDecimalBtn.setText("-0." + toAdd);
                swapDecimalBtn.setText("+/- 0." + toSwap);
            }
        });

        return rowView;
    }

    private boolean hoursExistForDay(){
        for (int i = 0; i < workHoursList.size(); i++){
            if (workHoursList.get(i).getDate().after(currentDate)){
                workHoursNum = i;
                return false;
            } else if (workHoursList.get(i).getDate().equals(currentDate)){
                workHoursNum = i;
                return true;
            }
        }
        workHoursNum = workHoursList.size();
        return false;
    }
}
