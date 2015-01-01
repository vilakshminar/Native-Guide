package com.balaji.android.nativeguide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.SimpleAdapter;

import com.google.android.gms.maps.model.LatLng;



public class SecondActivity extends Activity 
{
	 
    public static AutoCompleteTextView atvPlaces1,atvPlaces2;
    public static String src="",des="";
    PlacesTask placesTask;
    ParserTask parserTask;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
 
        atvPlaces1 = (AutoCompleteTextView) findViewById(R.id.atv_places1);
        atvPlaces2= (AutoCompleteTextView) findViewById(R.id.atv_places2);
        
        atvPlaces1.setThreshold(1);
        atvPlaces2.setThreshold(1);
 
        atvPlaces1.addTextChangedListener(new TextWatcher() {
 
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) 
            {
                placesTask = new PlacesTask();
                placesTask.execute(s.toString());
            }
 
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) 
            {
                
            }
 
            @Override
            public void afterTextChanged(Editable s) 
            {
                
            }
        });
        atvPlaces2.addTextChangedListener(new TextWatcher() {
        	 
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) 
            {
                placesTask = new PlacesTask();
                placesTask.execute(s.toString());
            }
 
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) 
            {
                
            }
 
            @Override
            public void afterTextChanged(Editable s) 
            {
                
            }
        });
 
    }
 
    private String downloadUrl(String strUrl) throws IOException
    {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        
        try
        {
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while( ( line = br.readLine()) != null)
            {
                sb.append(line);
            }
            data = sb.toString();
            br.close();
         }
        catch(Exception e)
        {
            Log.d("Exception while downloading url", e.toString());
        }
        finally
        {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
 
    private class PlacesTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... place) 
        {
            String data = "";
            String key = "key=AIzaSyBlruPARtDoM94u2D0L-SkMGLvWt51K9rs";
            String input="";
 
            try 
            {
                input = "input=" + URLEncoder.encode(place[0], "utf-8");
            } 
            catch (UnsupportedEncodingException e1) 
            {
                e1.printStackTrace();
            }
            
            String types = "types=geocode";
            String sensor = "sensor=false";
            String parameters = input+"&"+types+"&"+sensor+"&"+key;
            String output = "json";
            String url = "https://maps.googleapis.com/maps/api/place/autocomplete/"+output+"?"+parameters;
            
            try
            {
                data = downloadUrl(url);
            }
            catch(Exception e)
            {
                Log.d("Background Task",e.toString());
            }
            
            return data;
        }
 
        @Override
        protected void onPostExecute(String result) 
        {
            super.onPostExecute(result);
            parserTask = new ParserTask();
            parserTask.execute(result);
        }
    }
 
    private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String,String>>>
    {
 
        JSONObject jObject;
        @Override
        protected List<HashMap<String, String>> doInBackground(String... jsonData) 
        {
 
            List<HashMap<String, String>> places = null;
            PlaceJSONParser placeJsonParser = new PlaceJSONParser();
 
            try
            {
                jObject = new JSONObject(jsonData[0]);
                places = placeJsonParser.parse(jObject);
            }
            catch(Exception e)
            {
                Log.d("Exception",e.toString());
            }
            return places;
        }
 
        @Override
        protected void onPostExecute(List<HashMap<String, String>> result) 
        {
 
            String[] from = new String[] { "description"};
            int[] to = new int[] { android.R.id.text1 };
            SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), result, android.R.layout.simple_list_item_1, from, to);
            atvPlaces1.setAdapter(adapter);
            atvPlaces2.setAdapter(adapter);
        }
    }
    
    public void geocode(View view)
	{
    	String[] srcArray=new String[10],dstArray=new String[10];
		src=atvPlaces1.getText().toString();
		des=atvPlaces2.getText().toString();
		srcArray=src.split(",");
		dstArray=des.split(",");
		src=srcArray[0];
		des=dstArray[0];
		Log.i("Source address",src);
		Log.i("Destination address",des);
		MapFragmentActivity.SOURCE=getLatLongFromAddress(src);
		MapFragmentActivity.DEST=getLatLongFromAddress(des);
		
		Intent intent=new Intent(this,ThirdActivity.class);
		startActivity(intent);
	}

	
	public static LatLng getLatLongFromAddress(String youraddress) 
	{
	    String uri = "http://maps.google.com/maps/api/geocode/json?address=" +youraddress + "&sensor=false";
	    double lat=0,lng=0;
	    HttpGet httpGet = new HttpGet(uri);
	    HttpClient client = new DefaultHttpClient();
	    HttpResponse response;
	    StringBuilder stringBuilder = new StringBuilder();

	    try 
	    {
	        response = client.execute(httpGet);
	        HttpEntity entity = response.getEntity();
	        InputStream stream = entity.getContent();
	        int b;
	        while ((b = stream.read()) != -1) 
	        {
	            stringBuilder.append((char) b);
	        }
	    } 
	    catch (ClientProtocolException e) 
	    {
	        e.printStackTrace();
	    } 
	    catch (IOException e) {
	        e.printStackTrace();
	    }

	    JSONObject jsonObject = new JSONObject();
	    try 
	    {
	        jsonObject = new JSONObject(stringBuilder.toString());

	        lng = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
	            .getJSONObject("geometry").getJSONObject("location")
	            .getDouble("lng");

	        lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
	            .getJSONObject("geometry").getJSONObject("location")
	            .getDouble("lat");
	    } 
	    catch (JSONException e) 
	    {
	        e.printStackTrace();
	    }
	    
	    return new LatLng(lat,lng);

	}
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_second, menu);
        return true;
    }
}