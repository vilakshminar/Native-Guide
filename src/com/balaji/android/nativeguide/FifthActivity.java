package com.balaji.android.nativeguide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.RadioButton;

public class FifthActivity extends Activity 
{
	public static String MAPTYPE="";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fifth);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_fifth, menu);
		return true;
	}
	
	public void onRadioButtonClicked(View view)
	{
		boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) 
        {
            case R.id.mapNormalRadio:
                if (checked)
                    MAPTYPE="GoogleMap.MAP_TYPE_NORMAL";
                break;
            case R.id.mapSatelliteRadio:
                if (checked)
                	MAPTYPE="GoogleMap.MAP_TYPE_SATELLITE";
                break;
            case R.id.mapTerrainRadio:
                if (checked)
                	MAPTYPE="GoogleMap.MAP_TYPE_TERRAIN";
                break;
            case R.id.mapHybridRadio:
                if (checked)
                	MAPTYPE="GoogleMap.MAP_TYPE_HYBRID";
                break;
        }
        Intent intent=new Intent(this,MapFragmentActivity.class);
		startActivity(intent);
	}

}
