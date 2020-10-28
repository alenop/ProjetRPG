package elementsInteractifs;

import com.sun.org.apache.bcel.internal.classfile.Code;

public class Coffre {
	private int a;
	private int b;

	public void detruire1() {
		System.out.println("-- cle " + this + " " + a + " " + b);
	}

	public Cle cle;

	public Coffre(Cle c) {
		cle = c;
		System.out.println("++ coffre CLE " + this + " " + cle.a + " " + Cle.b);
	}

	public Coffre(int n) {
		cle = new Cle();
		System.out.println("++ coffre ENTIER " + this + " " + cle.a + " " + Cle.b);
	}

	public Coffre(Code c) {
		cle = new Cle();
		System.out.println("++ coffre COFFRE " + this + " " + cle.a + " " + Cle.b);
	}
	
	public void detruire() {
		cle.detruire();
		System.out.println("-- coffre " + this);
	}
	
	public static void main(String[] args) {
		Cle cle1 = new Cle();
		Coffre c1 = new Coffre(cle1);
		Coffre c2 = new Coffre(3);
		c2.detruire1();
		c1.detruire1();
		cle1.detruire();
	}
}
