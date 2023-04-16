package com.blubblub.gravity;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
//TODO screen ratio compared to game ratio/junk space
//TODO Portriat/landscape?
//TODO on screenSizeListener
//TODO bounding shapes
public class Game extends Activity implements OnClickListener, OnTouchListener{
	
	private ArrayList<Drawable> drawables = new ArrayList<Drawable>();
	private ArrayList<Collidable> collidables = new ArrayList<Collidable>();
	private ArrayList<Updatable> updatables = new ArrayList<Updatable>();
	//Copies of the lists to avoid concurrrent modifications
	volatile ArrayList<Drawable> uDrawables = new ArrayList<Drawable>();
	volatile ArrayList<Collidable> uCollidables = new ArrayList<Collidable>();
	volatile ArrayList<Updatable> uUpdatables = new ArrayList<Updatable>();
	int width, height;
	static final int PERFERED_WIDTH = 540;
	static final int PERFERED_HEIGHT = 960;
	int sideBorders = 0, verticalBorders = 0;
	Random random;
	WakeLock WL;
	PowerManager PM;
	GameSurface surface;

	TiltManager tiltManager;
	
	//Canvas canvas;
	//Bitmap bitmap;
	GameLoop gameLoop;
	WorldProvider worldProvider;
	int timeToPlatform;
	boolean platformNeeded = true;
	int difficulty;
	Ball ball;
	int speed;
	Paint paint = new Paint();
	Circle bFire, bWater, bAir, bEarth;
	Bitmap iFire, iWater, iAir, iEarth;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		//wake-lock
		PM = (PowerManager)getSystemService(Context.POWER_SERVICE);
		WL = PM.newWakeLock(PowerManager.FULL_WAKE_LOCK, "poop");
		//starts wake-lock
		WL.acquire();		 
		
		random = new Random();
		tiltManager = new TiltManager(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		updateGameSize();
		
		//TODO radius
		bFire = new Circle(width/6 , height - (height/10), width/10);
		bWater = new Circle(width/6 * 2, height - (height/10), width/10);
		bEarth = new Circle(width/6 * 4, height - (height/10), width/10);
		bAir = new Circle(width/6 * 5, height - (height/10), width/10);

		iFire = BitmapFactory.decodeResource(getResources(), R.drawable.plain1);
		iWater = BitmapFactory.decodeResource(getResources(), R.drawable.plain0);
		iAir = BitmapFactory.decodeResource(getResources(), R.drawable.plain2);
		iEarth = BitmapFactory.decodeResource(getResources(), R.drawable.plain3);
		
		
	//	bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		//canvas = new Canvas(bitmap);
		surface = new GameSurface(this);
		surface.setOnTouchListener(this);
		
		

		
		setContentView(surface);
		setUpGame();
		gameLoop = new GameLoop(this);
		gameLoop.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

	@Override
	protected void onPause() 
	{
		super.onPause();
		gameLoop.stop();
		WL.release();
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		gameLoop.start();
		WL.acquire();
	}
	
	@Override
	protected void onStop() 
	{
		super.onStop();
		gameLoop.stop();
		WL.release();
		//if(bitmap != null)
		//	bitmap.recycle();
	}

	@Override
	public void onClick(View v) 
	{
		if(v.equals(bFire))
		{
			ball.setElement(Element.FIRE);
		}
		else if(v.equals(bWater))
		{
			
		}
	}
	
	public void updateAvgRoll()
	{
		
	}
	
	public void update()
	{
		for(Updatable u : uUpdatables)
			u.update();
		
		tiltManager.update();
		ball.setX((int)(ball.getX() + tiltManager.getTilt()));

		for(Collidable c: uCollidables)
		{
			if(c != ball && c.collided(ball))
			{
				c.onHit(ball);
				ball.onHit(c);
			}
		}
		timeToPlatform --;
		if(timeToPlatform <= 0)
		{
			platformNeeded = true;
			timeToPlatform = random.nextInt((int)(1000.0/speed))+20;
		}
		if(platformNeeded)
		{
			platformNeeded = false;
			for(Platform p : worldProvider.getNextPlatform(difficulty))
			{
				p.add();
			}
			difficulty++;
		}
	}
	
	public void draw(Canvas canvas)
	{
		canvas.drawRGB(0, 69, 69);
		for(Drawable d : uDrawables)
		{
			canvas.drawBitmap(d.getBitmap(), (float)d.getX(), (float)d.getY(), paint);
		}
		//Buttons
		canvas.drawBitmap(iFire, bFire.getX(), bFire.getY(), paint);
		canvas.drawBitmap(iWater, bWater.getX(), bWater.getY(), paint);
		canvas.drawBitmap(iEarth, bEarth.getX(), bEarth.getY(), paint);
		canvas.drawBitmap(iAir, bAir.getX(), bAir.getY(), paint);

		//surface.draw(bitmap);
	}
	
	/**
	 * return the number that the actual dimensions must be multiplied by to become the desired dimensions.
	 * @return
	 */
	private float getScaleFactor()
	{
		float actualHeight = (float)getMetrics().heightPixels;
		float actualWidth = (float)getMetrics().widthPixels;
		float scale = Math.min(actualHeight/PERFERED_HEIGHT, actualWidth/PERFERED_WIDTH);
		return scale;
	}
	
	public void updateGameSize()
	{
		int newHeight = (int)(getScaleFactor()*PERFERED_HEIGHT);
		int newWidth = (int)(getScaleFactor()*PERFERED_WIDTH);
		height = newHeight;
		width = newWidth;
		sideBorders = ((int)(getMetrics().widthPixels - width))/2;
		verticalBorders = ((int)(getMetrics().heightPixels - height))/2;
	}
	/**
	 * returns the space between the edge of the phone and the game space on either side.
	 * @return
	 */
	public int getSideBorders()
	{
		return sideBorders;
	}
	
	public int getVerticalBorders()
	{
		return verticalBorders;
	}
	
	public void addDrawable(Drawable drawable)
	{
		drawables.add(drawable);
		uDrawables = new ArrayList<Drawable>(drawables);
	}
	
	public void addUpdatable(Updatable updatable)
	{
		updatables.add(updatable);
		uUpdatables = new ArrayList<Updatable>(updatables);

	}
	
	public void addCollidable(Collidable collidable)
	{
		collidables.add(collidable);
		uCollidables = new ArrayList<Collidable>(collidables);
	}
	
	public void removeDrawable(Drawable drawable)
	{
		drawables.remove(drawable);
		uDrawables = new ArrayList<Drawable>(drawables);
	}
	
	public void removeUpdatable(Updatable updatable)
	{
		updatables.remove(updatable);
		uUpdatables = new ArrayList<Updatable>(updatables);

	}
	
	public void removeCollidable(Collidable collidable)
	{
		collidables.remove(collidable);
		uCollidables = new ArrayList<Collidable>(collidables);

	}
	
	
	private void setUpGame()
	{
		worldProvider = new WorldProvider(this);
		speed = 20;
		difficulty = 1;
		timeToPlatform = 60;
		ball = new Ball(this, width/2 - 10, 100, width/10);
		ball.add();
		surface.draw();
	}
	
	
	public DisplayMetrics getMetrics()
	{
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int x = (int) (event.getX() - getSideBorders());
		int y = (int) (event.getY() - getVerticalBorders());
		System.out.println(x + " , " + y);
		if(bFire.contains(x, y))
		{
			ball.setElement(Element.FIRE);
		}
		else if(bWater.contains(x, y))
		{
			ball.setElement(Element.WATER);
		}
		else if(bEarth.contains(x, y))
		{
			ball.setElement(Element.EARTH);
		}
		else if(bAir.contains(x, y))
		{
			ball.setElement(Element.AIR);
		}
		return false;
	}
	
	public void gameOver()
	{
		gameLoop.stop();
		WL.release();
		Intent playIntent = new Intent((Context)this, MainMenu.class);
		this.startActivity(playIntent);
		this.finish();
	}


}
