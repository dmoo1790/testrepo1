package com.example.calc1;

import android.app.Activity;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener
{
	Button btn;
	TextView textBox, textTotal, textFlag;
	Button[] numButtons;
	int[] IDs = new int[10];
	String[] keys = new String[10];
	int total = 0;
	String buttonText = "C Equals Plus Minus";
	String[] buttons = buttonText.split(" ");
	boolean newValue = true;
	AudioManager audioMan;
	String savedText;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initKeys();
        textBox = (TextView) findViewById(R.id.textView1);
        textTotal = (TextView) findViewById(R.id.textTotal);
//        textFlag = (TextView) findViewById(R.id.textFlag);
    }

	@Override
	protected void onSaveInstanceState(Bundle outState) 
	{
		super.onSaveInstanceState(outState);
		outState.putInt("total", total);
		outState.putBoolean("newValue", newValue);
	}

    @Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) 
    {
		super.onRestoreInstanceState(savedInstanceState);
		total = savedInstanceState.getInt("total");
		newValue = savedInstanceState.getBoolean("newValue");
	}

	private void initKeys()
    {
        audioMan = (AudioManager) getSystemService(AUDIO_SERVICE);
        audioMan.loadSoundEffects();	// Do we need this?
        String btnID;
        int resID;
        for (int i = 0; i < 10; i++)
        {
        	btnID = "btn" + i;
        	resID = getResources().getIdentifier(btnID, "id", "com.example.calc1");
        	btn = (Button) findViewById(resID);
        	btn.setOnClickListener(this);
        	keys[i] = String.valueOf(i);
        	IDs[i] = resID;
        }
        for (String s : buttons)
        {
        	btnID = "btn" + s;
        	resID = getResources().getIdentifier(btnID, "id", "com.example.calc1");
        	btn = (Button) findViewById(resID);
        	btn.setOnClickListener(this);
        }
    }

	@Override
	public void onClick(View v) 
	{
//        savedText = textTotal.getText().toString();
//        total = Integer.parseInt(savedText.toString());
//        savedText = textFlag.getText().toString();
//        if (savedText == "false")
//        	newValue = false;
		audioMan.playSoundEffect(AudioManager.FX_KEY_CLICK, 0.5f);
		String theKeyPressed = "NaN";
		int resID = v.getId();
		for (int i = 0; i < 10; i++)
		{
			if (v.getId() == IDs[i])
			{
				theKeyPressed = keys[i];
				break;
			}
		}
		if (theKeyPressed != "NaN")
			if (newValue)
			{
				textBox.setText(theKeyPressed);
				newValue = false;
//				textFlag.setText("false");
			}
			else
			{
				textBox.append(theKeyPressed);
			}
		else
			switch (resID)
			{
				case R.id.btnC:	clearTotal(); 
					break;
				case R.id.btnPlus: addToTotal();
					break;
				case R.id.btnMinus: subtractFromTotal();
					break;
				case R.id.btnEquals: finalTotal();
			}
			
	}
    
	private void clearTotal()
	{
		textBox.setText("0");
		total = 0;
		newValue = true;
//		textFlag.setText("true");
		textTotal.setText(String.valueOf(total));
	}
	
	private void addToTotal()
	{
		int currentNum = Integer.parseInt(textBox.getText().toString());
		if (!newValue)
			total += currentNum;
		textBox.setText(String.valueOf(total));
		newValue = true;
//		textFlag.setText("true");
		textTotal.setText(String.valueOf(total));
	}

	private void subtractFromTotal()
	{
		int currentNum = Integer.parseInt(textBox.getText().toString());
		if (!newValue)
			total += currentNum;
		textBox.setText("-");
		newValue = false;
//		textFlag.setText("false");
		textTotal.setText(String.valueOf(total));
	}

	private void finalTotal()
	{
		int currentNum = Integer.parseInt(textBox.getText().toString());
		total += currentNum;
		textBox.setText(String.valueOf(total));
		newValue = true;
//		textFlag.setText("true");
		textTotal.setText(String.valueOf(total));
	}
}
