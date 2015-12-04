package ca.phlyingwaylstudios.kaolin;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by il on 23/11/2015.
 */
public class ProjectOverviewArrayAdapter extends ArrayAdapter<Project> {

    private final Context context;
    private final List<Project> projects;

    public ProjectOverviewArrayAdapter(Context context, List<Project> projects) {
        super(context, -1, projects);
        this.context = context;
        this.projects = projects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.project_overview_list_item, parent, false);
        TextView nameTxt = (TextView) rowView.findViewById(R.id.nameTxt);
        TextView codeTxt = (TextView) rowView.findViewById(R.id.codeTxt);
        TextView startTxt = (TextView) rowView.findViewById(R.id.startDateTxt);
        TextView endTxt = (TextView) rowView.findViewById(R.id.endDateTxt);
        FrameLayout colorSwatch = (FrameLayout) rowView.findViewById(R.id.colorSwatch);
        nameTxt.setText(projects.get(position).getName());
        codeTxt.setText(projects.get(position).getCode());
        colorSwatch.setBackgroundColor(Color.parseColor(projects.get(position).getColor()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy");
        startTxt.setText(dateFormat.format(projects.get(position).getStartDate()));
        endTxt.setText(dateFormat.format(projects.get(position).getEndDate()));

        return rowView;
    }


}
