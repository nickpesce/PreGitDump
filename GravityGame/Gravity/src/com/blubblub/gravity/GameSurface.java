package com.blubblub.gravity;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback{

	SurfaceHolder holder;
	Game game;
	int frames = 0;
	long startTime = System.currentTimeMillis();
	//Canvas canvas;
	public GameSurface(Game game)
	{
		super(game);
		this.game = game;
		holder = getHolder();
	}

	public void draw()
	{
		try
		{
			
		if(!holder.getSurface().isValid())
			return;
		frames++;
		Canvas canvas = holder.lockCanvas();
		game.draw(canvas);
		canvas.drawText("FPS:"+frames/((System.currentTimeMillis() - startTime)*10^3), 0, 0, new Paint(Paint.ANTI_ALIAS_FLAG));
		//canvas.drawBitmap(bm, game.getSideBorders(), game.getVerticalBorders(), null);
		holder.unlockCanvasAndPost(canvas);
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		super.onSizeChanged(w, h, oldw, oldh);
		game.updateGameSize();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
	{
		draw();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) 
	{		
		draw();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
	}
}
