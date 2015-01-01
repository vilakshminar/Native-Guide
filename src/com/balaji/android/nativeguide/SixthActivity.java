package com.balaji.android.nativeguide;

import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SixthActivity extends Activity implements TextToSpeech.OnInitListener 
{

	public static EditText directionsEditText;
	public static Button spkButton;
	private TextToSpeech tts;
	@Override
	
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sixth);
		
		tts=new TextToSpeech(this, this);
		
		spkButton=(Button)findViewById(R.id.btnspk);
		
		directionsEditText=(EditText)findViewById(R.id.directionsText);
		
		String temp=GMapV2Direction.DirectionString;
		directionsEditText.setText(android.text.Html.fromHtml(temp));
		//directionsEditText.setText("Hello");
		
		spkButton.setOnClickListener(new View.OnClickListener() {
			 
            @Override
            public void onClick(View arg0) {
                speakOut();
            }
 
        });
		
	}

	public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
 
    @Override
    public void onInit(int status) {
 
        if (status == TextToSpeech.SUCCESS) {
 
            int result = tts.setLanguage(Locale.US);
 
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                directionsEditText.setEnabled(true);
                speakOut();
            }
 
        } else {
            Log.e("TTS", "Initilization Failed!");
        }
 
    }
    
    private void speakOut()
    {
    	String dirtext=GMapV2Direction.DirectionString;
    	Log.i("Dirtext",dirtext);
    	String fintext=Html.fromHtml(dirtext).toString();
    	Log.i("Fintext", fintext);
        tts.speak(fintext, TextToSpeech.QUEUE_FLUSH, null);
    }
	

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_sixth, menu);
		return true;
	}

}
