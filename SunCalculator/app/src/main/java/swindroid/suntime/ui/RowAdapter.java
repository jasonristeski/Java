package swindroid.suntime.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import swindroid.suntime.R;


public class RowAdapter extends ArrayAdapter<String>
{

    static class ViewHolder
    {
        private TextView fDate;
        private TextView fSunrise;
        private TextView fSunset;
    }

    private Context fContext;

    public RowAdapter(Context context, int resource,ArrayList<String> aData)
    {
        super(context, resource, aData);
        fContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        ViewHolder lHolder;
        if(convertView == null)
        {
            convertView = LayoutInflater.from(fContext).inflate(R.layout.row,parent,false);
            lHolder = new ViewHolder();
            lHolder.fDate = (TextView) convertView.findViewById(R.id.rowDate);
            lHolder.fSunrise = (TextView) convertView.findViewById(R.id.rowSunrise);
            lHolder.fSunset = (TextView) convertView.findViewById(R.id.rowSunset);
            convertView.setTag(lHolder);
        }
        else
            lHolder = (ViewHolder) convertView.getTag();

        String[] lData = getItem(position).split(",");
        lHolder.fDate.setText( "Date: " + lData[0]);
        lHolder.fSunrise.setText("Sunrise: " + lData[1]);
        lHolder.fSunset.setText("Sunset: "+ lData[2]);

        return convertView;
    }

}
