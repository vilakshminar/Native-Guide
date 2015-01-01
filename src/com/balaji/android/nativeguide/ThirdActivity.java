package com.balaji.android.nativeguide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.RadioButton;

public class ThirdActivity extends Activity 
{

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_third);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_third, menu);
		return true;
	}
	
	public void onRadioButtonClicked(View view)
	{
		boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) 
        {
            case R.id.drivingRadio:
                if (checked)
                    MapFragmentActivity.MODE="driving";
                break;
            case R.id.walkingRadio:
                if (checked)
                	MapFragmentActivity.MODE="walking";
                break;
            case R.id.bicyclingRadio:
                if (checked)
                    MapFragmentActivity.MODE="bicycling";
                break;
            case R.id.transitRadio:
                if (checked)
                    MapFragmentActivity.MODE="transit";
                break;
        }
        Intent intent=new Intent(this,FourthActivity.class);
        startActivity(intent);
	}

}
