package com.blubblub.gravity;

public interface Collidable {
	public void onHit(Collidable c);
	public boolean collided(Collidable c);
	public Shape getAABB();
}
