package item;
import javax.swing.text.html.HTMLDocument.Iterator;

import javafx.scene.shape.Arc;
public abstract class Classe_Armure extends Iterator
{
	
	public static void main( String[] args ) {

	abstract class Combattant {
	    abstract void combattre();
	}

	abstract class Arme extends Combattant {
	    private Combattant _component;

	    protected Arme( Combattant component ) {
	      _component = component;
	    }

	    @Override
	    public void combattre() {
	        _component.combattre();
	    }
	}

	final class Epee extends Arme {
	    public Epee( Combattant component ) {
	        super(component);
	    }

	    @Override
	    public void combattre() {
	        super.combattre();
	        System.out.println(" avec une épée");
	    }
	}

	final class Arc extends Arme {
	    public Arc( Combattant component ) {
	        super(component);
	    }

	    @Override
	    public void combattre() {
	        super.combattre();
	        System.out.println(" avec un arc");
	    }
        
     }
  }
}
