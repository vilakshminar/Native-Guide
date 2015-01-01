package com.balaji.android.nativeguide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class FourthActivity extends Activity 
{
	public static EditText wp1,wp2,wp3,wp4,wp5,wp6;
	public static int wpCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fourth);
		
		wp1=(EditText)findViewById(R.id.firstWPText);
		wp2=(EditText)findViewById(R.id.secondWPText);
		wp3=(EditText)findViewById(R.id.thirdWPText);
		wp4=(EditText)findViewById(R.id.fourthWPText);
		wp5=(EditText)findViewById(R.id.fifthWPText);
		wp6=(EditText)findViewById(R.id.sixthWPText);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_fourth, menu);
		return true;
	}
	public void onPause()
	{
		super.onPause();
		wpCount=0;
		if(!wp1.getText().toString().equals(""))
			wpCount++;
		if(!wp2.getText().toString().equals(""))
			wpCount++;
		if(!wp3.getText().toString().equals(""))
			wpCount++;
		if(!wp4.getText().toString().equals(""))
			wpCount++;
		if(!wp5.getText().toString().equals(""))
			wpCount++;
		if(!wp6.getText().toString().equals(""))
			wpCount++;
		
	}
	public void moveNext(View view)
	{
		Intent intent=new Intent(this,FifthActivity.class);
		startActivity(intent);
	}

}
