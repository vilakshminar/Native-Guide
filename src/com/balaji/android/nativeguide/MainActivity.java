package com.balaji.android.nativeguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.RadioButton;


 
public class MainActivity extends FragmentActivity 
{
	
	public static String SELECTED_LANG="";
 
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    public void onRadioButtonClicked(View view) 
    {
        boolean checked = ((RadioButton) view).isChecked();
        
        switch(view.getId()) {
            case R.id.tamilRadio:
                if (checked)
                    SELECTED_LANG="tamil";
                break;
            case R.id.hindiRadio:
                if (checked)
                	SELECTED_LANG="hindi";
                break;
            case R.id.malayalamRadio:
                if (checked)
                	SELECTED_LANG="malayalam";
                break;
            case R.id.kannadaRadio:
                if (checked)
                	SELECTED_LANG="kannada";
                break;
            case R.id.teluguRadio:
                if (checked)
                	SELECTED_LANG="telugu";
                break;
            case R.id.marathiRadio:
                if (checked)
                	SELECTED_LANG="marathi";
                break;
            
        }
        Intent intent=new Intent(this,SecondActivity.class);
        startActivity(intent);
    }
}

