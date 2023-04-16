import java.awt.*;
public class Ghost extends Tetrino
{
	Tetrino copy;
	public Ghost(Tetrino copy)
	{
		super(copy.type, 0, 0, copy.map);
		super.color = Color.LIGHT_GRAY;
		this.copy = copy;
		setX(copy.getX());
		setDirection(copy.getDirection());
		setLowestPos();
	}

	public void setLowestPos()
	{
		for(int i = 0; i < 20; i++)
		{
			setLocation(getX(), i);
			if(map.checkForBottomCollision((Tetrino)this))
			{
				return;
			}
		}
	}
}