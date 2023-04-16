package com.blubblub.gravity;

import android.graphics.Bitmap;

public interface Drawable {
	public Bitmap getBitmap();
	public int getX();
	public int getY();
	public boolean isVisible();
}
