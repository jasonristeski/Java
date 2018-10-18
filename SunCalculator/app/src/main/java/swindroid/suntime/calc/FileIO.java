package swindroid.suntime.calc;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.TimeZone;

import swindroid.suntime.R;


public class FileIO
{
    private Context fContext;

    public FileIO(Context aContext)
    {
        fContext = aContext;
    }

    public void initFile()
    {
        try
        {
            File lFile = new File(fContext.getFilesDir(),"data1.txt");
            InputStream lStream  = fContext.getResources().openRawResource(R.raw.data1);
            BufferedReader lData = new BufferedReader(new InputStreamReader(lStream));

            BufferedWriter lWriter = new BufferedWriter(new FileWriter(lFile));

            String lLine;
            while ((lLine = lData.readLine()) != null)
            {
                lWriter.write(lLine);
                lWriter.newLine();
                lWriter.flush();
            }
            lWriter.close();
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }

    public HashMap<String,GeoLocation> readFile()
    {
        HashMap<String,GeoLocation> lResult = new HashMap<String, GeoLocation>();
        try
        {
            FileInputStream lStream = fContext.openFileInput("data1.txt");
            BufferedReader lData = new BufferedReader(new InputStreamReader(lStream));
            String lLine;
            while ((lLine = lData.readLine()) != null)
            {
                String[] lLineData = lLine.split(",");
                //Log.d("Data",lLine);
                Double lLat = Double.parseDouble(lLineData[1]);
                Double lLong = Double.parseDouble(lLineData[2]);
                TimeZone lZone = TimeZone.getTimeZone(lLineData[3]);
                lResult.put(lLineData[0],new GeoLocation(lLineData[0],lLat,lLong,0,lZone));
            }

        }
        catch(FileNotFoundException e)
        {
            // File has become corrupt, create new and re-populate with default data
            // additional user data is gone.
            initFile();
            readFile();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
        return lResult;
    }

    public void writeToFile(GeoLocation aLocation)
    {
        try
        {
            FileOutputStream lFile = fContext.openFileOutput("data1.txt",Context.MODE_APPEND);
            BufferedWriter lWriter = new BufferedWriter( new OutputStreamWriter(lFile));
            lWriter.write(aLocation.getLocationName() + ",");
            lWriter.write(aLocation.getLatitude() + ",");
            lWriter.write(aLocation.getLongitude() + ",");
            lWriter.write(aLocation.getTimeZone().getID());
            lWriter.newLine();
            lWriter.flush();
            lWriter.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}

