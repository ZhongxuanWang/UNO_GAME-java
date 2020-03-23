public class Action extends Color {
	private int ac;

  	public Action(int num, int col) {
		super(col);
		ac = num;
	}

	public String getActionName() {
		switch(ac) {
			case 1:
				return "SKIP";
			case 2:
				return "REVERSE";
			case 3:
				return "DRAWTWO";
			default:
				return "ERROR";
		}
	}

	public int getIntAction() {
  		return ac;
	}

	public String toString() {
		return "Action: " + getActionName() + " Color: " + super.getStringColor();
	}
}