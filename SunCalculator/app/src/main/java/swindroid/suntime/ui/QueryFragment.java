package swindroid.suntime.ui;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import swindroid.suntime.R;
import swindroid.suntime.calc.AstronomicalCalendar;
import swindroid.suntime.calc.GeoLocation;

/**
 * Created by jason on 28/10/2016.
 */
public class QueryFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private View fView;

    private EditText fStartDateText;
    private EditText fEndDateText;
    private Calendar fStartCalendar;
    private Calendar fEndCalender;

    private DatePickerDialog fDialog; // Required multiple genreation cases override

    private HashMap<String,GeoLocation> fMap = new HashMap<String,GeoLocation>();
    private AstronomicalCalendar fCal;

    private RowAdapter fAdapter;

    private ArrayList<String> fResult = new ArrayList<String>();


    private QueryListener fListener;

    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState)
    {

        fView = inflater.inflate(R.layout.query_fragment,container,false);
        initializeCal();
        initializeUI();
        initializeList();
        return  fView;
    }
        @Override
        public void onAttach(Context context)
        {
            super.onAttach(context);
            if(context instanceof QueryListener )
            {
                fListener = (QueryListener) context;
            }
            else
            {
                throw new IllegalStateException("Error: illegal state" + context.toString());
            }
        }

        @Override
        public void onDetach()
        {
            super.onDetach();
            fListener = null;
        }

    private void initializeCal()
    {
        fStartCalendar = Calendar.getInstance();
        fEndCalender = Calendar.getInstance();
    }

    // Search bar "Location List"
    public void initializeList()
    {
        fMap = fListener.populateSearchList();
        AutoCompleteTextView lView = (AutoCompleteTextView) fView.findViewById(R.id.LocationList);
        ArrayAdapter<String> lAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_dropdown_item_1line,new ArrayList<String>(fMap.keySet()));

        lView.setAdapter(lAdapter);
        lView.setOnItemClickListener(this);
    }

    private void initializeUI()
    {
        ListView lList = (ListView) fView.findViewById(R.id.ResultList);
        fAdapter = new RowAdapter(getActivity().getApplicationContext(),R.layout.row,new ArrayList<String>());
        lList.setAdapter(fAdapter);

        fCal = new AstronomicalCalendar(new GeoLocation());
        display();
        // Set up button listener
        // Uses same Listener as EditText; functionality based on type of view
        Button lGenerate = (Button) fView.findViewById(R.id.GenerateButton);
        lGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                generate();
            }
        });

        // Calendar Init
        fStartDateText = (EditText) fView.findViewById(R.id.StartDateText);
        fEndDateText = (EditText) fView.findViewById(R.id.EndDateText);
        fStartDateText.setInputType(InputType.TYPE_NULL);
        fEndDateText.setInputType(InputType.TYPE_NULL);
        fStartDateText.setOnClickListener(this);
        fEndDateText.setOnClickListener(this);



        Calendar lCal = Calendar.getInstance();
        int lYear = lCal.get(Calendar.YEAR);
        int lMonth = lCal.get(Calendar.MONTH);
        int lDay = lCal.get(Calendar.DAY_OF_MONTH);
        fDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                if(fStartDateText.hasFocus())
                {
                    fStartCalendar.set(year,monthOfYear,dayOfMonth);
                    fStartDateText.setText(formatDate("dd-MM-yyyy",fStartCalendar.getTime()));
                    Log.d("START",formatDate("dd-MM-yyyy",fStartCalendar.getTime()));
                }
                else
                {
                    fEndCalender.set(year,monthOfYear,dayOfMonth);
                    fEndDateText.setText(formatDate("dd-MM-yyyy",fEndCalender.getTime()));
                    Log.d("END",formatDate("dd-MM-yyyy",fEndCalender.getTime()));
                }
            }
        }, lYear, lMonth, lDay);
    }

    private String formatDate(String aRegex,Date aDate)
    {
        SimpleDateFormat lDateFormatter = new SimpleDateFormat(aRegex);
        return lDateFormatter.format(aDate);
    }

    private void generate()
    {
        fResult.clear();
        fAdapter.clear();
        fCal.setCalendar((Calendar)fStartCalendar.clone());
        fCal.getCalendar().add(Calendar.DATE,-1);
        Log.d("START Calendar",fStartCalendar.getTime().toString());
        Log.d("End Calendar",fEndCalender.getTime().toString());
        Log.d("Query Calendar",fCal.getCalendar().getTime().toString());
        while(fCal.getCalendar().getTime().before(fEndCalender.getTime()))
        {
            fCal.getCalendar().add(Calendar.DATE,1);
            fResult.add(formatDate("dd-MM-yyyy",fCal.getCalendar().getTime()) +
                    "," + formatDate("HH:mm",fCal.getSunrise()) + "," + formatDate("HH:mm",fCal.getSunset()));

        }
        Log.d("START Calendar",fStartCalendar.getTime().toString());
        Log.d("End Calendar",fEndCalender.getTime().toString());
        Log.d("Query Calendar",fCal.getCalendar().getTime().toString());
        fCal.getCalendar().clear();
        fCal.setCalendar(fStartCalendar);
        fListener.updateLocation(fCal.getGeoLocation(),fResult);
        fAdapter.addAll(fResult);
    }


    private void display()
    {
        TextView lSelection = (TextView) fView.findViewById(R.id.LocationText);
        lSelection.setText(fCal.getGeoLocation().getLocationName());
    }



    public void share()
    {
        Intent lShare = new Intent(Intent.ACTION_SEND);
        lShare.setType("text/plain");
        lShare.putExtra(Intent.EXTRA_SUBJECT,fCal.getGeoLocation().getLocationName() + "sunrise/sunset");
        lShare.putExtra(Intent.EXTRA_TEXT,"Location: " + fCal.getGeoLocation().getLocationName()
                 +fResult.toString());
        startActivity(Intent.createChooser(lShare,getResources().getString(R.string.share)));
    }



    public void clear()
    {
        fAdapter.clear();

    }

    @Override
    public void onClick(View v)
    {
        if (v instanceof EditText)
            fDialog.show();
        else
            throw new IllegalStateException(v.toString() + " :method called from an illegal state");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        fCal.setGeoLocation(fMap.get(parent.getItemAtPosition(position)));
        display();
    }


    public interface QueryListener
    {
        public void updateLocation(GeoLocation aLocation,ArrayList<String> lResult);
        public HashMap<String,GeoLocation> populateSearchList();

    }

}
