package elementsInteractifs;

import java.util.HashMap;

public abstract class PNJList {
	
	public static HashMap<String, PNJ> pnjList;

	public static void init() {
		pnjList = new HashMap<String, PNJ>();
	}

}
