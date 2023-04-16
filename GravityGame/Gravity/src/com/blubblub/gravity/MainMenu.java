package com.blubblub.gravity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends Activity implements View.OnClickListener{
	
	Button bPlay;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		bPlay = (Button) findViewById(R.id.bPlay);
		bPlay.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.bPlay:
			startGame();
			break;
		case R.id.bAchievments:
			break;
		}
	}
	
	
	public void startGame()
	{
		Intent playIntent = new Intent((Context)this, Game.class);
		this.startActivity(playIntent);
		
	}
	
}
