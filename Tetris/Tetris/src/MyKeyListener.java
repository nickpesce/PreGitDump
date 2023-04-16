import java.awt.event.*;
public class MyKeyListener implements KeyListener{

	Map map;
	public MyKeyListener(Map map)
	{
		this.map = map;
	}

	@Override
	public void keyPressed(KeyEvent arg0)
	{
		switch(arg0.getKeyCode())
		{
		case KeyEvent.VK_UP:
			map.currentTetrino.rotate(true);
			map.update();
			break;
		case KeyEvent.VK_RIGHT:
			if(!map.isCollidedOnRight())
			{
				map.currentTetrino.moveRight(1);
				map.update();
			}
			break;
		case KeyEvent.VK_DOWN:
			if(!map.checkForBottomCollision(map.currentTetrino))
			{
				map.update();
			}
			break;
		case KeyEvent.VK_LEFT:
			if(!map.isCollidedOnLeft())
			{
				map.currentTetrino.moveLeft(1);
				map.update();
			}
			break;
	    case KeyEvent.VK_CONTROL:
	        map.holdCurrent();
	        break;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}

}
