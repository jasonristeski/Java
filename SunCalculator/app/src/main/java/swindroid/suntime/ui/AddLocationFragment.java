package swindroid.suntime.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import swindroid.suntime.R;

/**
 * Created by jason on 28/10/2016.
 */
public class AddLocationFragment extends Fragment implements View.OnClickListener {
    private View fView;
    private AddLocationListener fListener;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        fView = inflater.inflate(R.layout.add_fragment,container,false);
        initializeUI();
        return fView;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if(context instanceof AddLocationListener)
            fListener = (AddLocationListener) context;
        else
            throw new IllegalStateException("Error: illegal state" + context.toString());

    }


    @Override
    public void onDetach()
    {
        super.onDetach();
        fListener = null;
    }

    @Override
    public void onClick(View v)
    {
        TextView lName = (TextView) fView.findViewById(R.id.NameText);
        TextView lLat = (TextView) fView.findViewById(R.id.LatText);
        TextView lLong = (TextView) fView.findViewById(R.id.LongText);
        TextView lGMT = (TextView) fView.findViewById(R.id.GMTText);
        String[] lResult = new String[4];
        lResult[0] = lName.getText().toString();
        lResult[1] = lLat.getText().toString();
        lResult[2] = lLong.getText().toString();
        lResult[3] = lGMT.getText().toString();
        fListener.updateFile(lResult);
        lName.setText("");
        lLat.setText("");
        lLong.setText("");
        lGMT.setText("");
    }

    private void initializeUI()
    {
        Button lButton = (Button) fView.findViewById(R.id.addLocationButton);
        lButton.setOnClickListener(this);
    }




    public interface AddLocationListener
    {
        public void updateFile(String[] aData);

    }
}
