public class Color extends Card {

	private int color;
	private String string_color;

	private Color() {}

	public Color(int c) {
		color = c;
		setStringColor();
	}

	public Color(int num, int c) {
		super(num);
		color = c;
		setStringColor();
	}

	public void setStringColor() {
		switch(color) {
			case 1:
				string_color = "Red";
				break;
			case 2:
				string_color = "Green";
				break;
			case 3:
				string_color = "Blue";
				break;
			case 4:
				string_color = "Yellow";
				break;
		}
	}
	
	public String getStringColor() {
		return string_color;
	}

	public String toString() {
		return super.toString() + " Color: " + string_color;
	}
}