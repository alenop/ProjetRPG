package rpgapp.data.elementInteractifs;

public class Equipment extends Item {
	private int stat;
	public Equipment(int stat,String name, String image) {
		super(name, image);
		this.setStat(stat);
		// TODO Auto-generated constructor stub
	}
	public int getStat() {
		return stat;
	}
	public void setStat(int stat) {
		this.stat = stat;
	}
	
	
}
