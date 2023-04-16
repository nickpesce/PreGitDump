package com.blubblub.gravity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class TiltManager implements SensorEventListener{
	
	private final static int SENSITIVITY = 1000;
	Game game;
	
	SensorManager sensorManager;
	Sensor accelerometer;
	Sensor magnet;
	float[] accelerometerValues;
	float[] magnetValues;
	float[] lastTilts;
	float avgTilt = 0;
	int oldestTilt;
	
	public TiltManager(Game game)
	{
		this.game = game;
		sensorManager = (SensorManager) game.getSystemService(Context.SENSOR_SERVICE);
		accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		magnet = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		lastTilts = new float[SENSITIVITY];

		sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
		sensorManager.registerListener(this, magnet, SensorManager.SENSOR_DELAY_GAME);
	}
	
	public void update()
	{
		
		if(magnetValues != null && accelerometerValues != null)
		{
			float[] rm = new float[9];
			float[] orientation = new float[3];
			SensorManager.getRotationMatrix(rm, null, accelerometerValues, magnetValues);
			SensorManager.getOrientation(rm, orientation);
			addToAverage(orientation[2]*30);
		}
		
	}
	
	public float getTilt()
	{
		return avgTilt;
	}
	
	public void addToAverage(float tilt)
	{
		for(int i = 0; i < lastTilts.length - 1; i++)
		{
			lastTilts[i+1] = lastTilts[i];
		}
		lastTilts[0] = tilt;
		avgTilt = getAverage(lastTilts);
	}
	
	public float getAverage(float[] array)
	{
		int total = 0;
		int n = 0;
		for(int i = 0; i < array.length; i++)
		{
			if(array[i] != 0)
			{
				total += array[i];
				n++;
			}
		}
		if(n != 0)
			return total/n;
		return 0;
	}
	public void onResume()
	{
		sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
		sensorManager.registerListener(this, magnet, SensorManager.SENSOR_DELAY_GAME);
	}
	public void onPause()
	{
		sensorManager.unregisterListener(this);
	}
	
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		
	}

	@Override
	public void onSensorChanged(SensorEvent event)
	{
		if(event.sensor.equals(accelerometer))
		{
			accelerometerValues = event.values;
		}
		else if(event.sensor.equals(magnet))
		{
			magnetValues = event.values;
		}
	}
}
