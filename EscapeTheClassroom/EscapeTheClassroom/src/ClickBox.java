
public class ClickBox extends Rectangle {

	private int id;
	public ClickBox(int x, int y, int width, int height, int id) {
		super(x, y, width, height);
		this.id = id;
	}
	
	public int getId()
	{
		return id;
	}

}
