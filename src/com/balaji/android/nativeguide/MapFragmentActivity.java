package com.balaji.android.nativeguide;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
 
public class MapFragmentActivity extends FragmentActivity 
{
	
	public static LatLng SOURCE,DEST;
	public static String MODE="";
	
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_fragment);
        if(MODE.equals("driving"))                
        	findDirections(SOURCE.latitude,SOURCE.longitude,DEST.latitude,DEST.longitude, GMapV2Direction.MODE_DRIVING );
        else if(MODE.equals("walking"))                
        	findDirections(SOURCE.latitude,SOURCE.longitude,DEST.latitude,DEST.longitude, GMapV2Direction.MODE_WALKING );
        /*else if(MODE.equals("bicycling"))                
        	findDirections(SOURCE.latitude,SOURCE.longitude,DEST.latitude,DEST.longitude, GMapV2Direction.MODE_BICYCLING );
        else if(MODE.equals("transit"))                
        	findDirections(SOURCE.latitude,SOURCE.longitude,DEST.latitude,DEST.longitude, GMapV2Direction.MODE_TRANSIT);*/
        
    }
    
    @SuppressWarnings("unchecked")
	public void findDirections(double fromPositionDoubleLat, double fromPositionDoubleLong, double toPositionDoubleLat, double toPositionDoubleLong, String mode)
    {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(GetDirectionsAsyncTask.USER_CURRENT_LAT, String.valueOf(fromPositionDoubleLat));
        map.put(GetDirectionsAsyncTask.USER_CURRENT_LONG, String.valueOf(fromPositionDoubleLong));
        map.put(GetDirectionsAsyncTask.DESTINATION_LAT, String.valueOf(toPositionDoubleLat));
        map.put(GetDirectionsAsyncTask.DESTINATION_LONG, String.valueOf(toPositionDoubleLong));
        map.put(GetDirectionsAsyncTask.DIRECTIONS_MODE, mode);
     
        GetDirectionsAsyncTask asyncTask = new GetDirectionsAsyncTask(this);
        asyncTask.execute(map);
    }
    
    @SuppressWarnings("unused")
	public void handleGetDirectionsResult(ArrayList<LatLng> directionPoints)
    {
        Polyline newPolyline;
        GoogleMap mMap = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        
        if(FifthActivity.MAPTYPE.equals("GoogleMap.MAP_TYPE_NORMAL"))
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        else if(FifthActivity.MAPTYPE.equals("GoogleMap.MAP_TYPE_SATELLITE"))
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        else if(FifthActivity.MAPTYPE.equals("GoogleMap.MAP_TYPE_TERRAIN"))
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        else if(FifthActivity.MAPTYPE.equals("GoogleMap.MAP_TYPE_HYBRID"))
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        
        Marker sourceMarker=mMap.addMarker(new MarkerOptions()
        					.position(SOURCE)
        					.title(SecondActivity.src)
        					.snippet("This is the source location"));
        Marker destMarker=mMap.addMarker(new MarkerOptions()
        					.position(DEST)
        					.title(SecondActivity.des)
        					.snippet("This is the destination location"));
        
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SOURCE, 15));
        
        PolylineOptions rectLine = new PolylineOptions().width(3).color(Color.BLUE);
        for(int i = 0 ; i < directionPoints.size() ; i++)
        {
            rectLine.add(directionPoints.get(i));
        }
        newPolyline = mMap.addPolyline(rectLine);
    }
    public void displayDirections(View view)
    {
    	Intent intent=new Intent(this,SixthActivity.class);
    	startActivity(intent);
    }
}

