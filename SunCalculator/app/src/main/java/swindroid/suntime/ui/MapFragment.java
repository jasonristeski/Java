package swindroid.suntime.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import swindroid.suntime.R;
import swindroid.suntime.calc.GeoLocation;


public class MapFragment extends Fragment implements OnMapReadyCallback
{

    private View fView;

    private GoogleMap fMap;
    private Marker fMarker;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        fView = inflater.inflate(R.layout.map_fragment,container,false);
        return fView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment lMap = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        lMap.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        fMap = googleMap;
        fMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

    }

    public void clear()
    {
        if(fMarker != null)
            fMarker.remove();
    }

    public void addMarker(GeoLocation aLocation, ArrayList<String> aResult)
    {
        if(fMarker != null)
            fMarker.remove();

        String lResult = TextUtils.join("\n\r",aResult);
        fMarker = fMap.addMarker(new MarkerOptions().position(new LatLng(aLocation.getLatitude(),aLocation.getLongitude())).title(aLocation.getLocationName().toString()).snippet(lResult));
        fMap.moveCamera((CameraUpdateFactory.newLatLngZoom(new LatLng(aLocation.getLatitude(),aLocation.getLongitude()),9)));
    }
}
