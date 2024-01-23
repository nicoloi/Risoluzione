
/**
 * questa classe rappresenta un letterale dato da
 * un atomo non negato.
 * 
 * Gli oggetti istanziati da questa classe sono immutabili
 */
public class Atom extends Literal {

    //COSTRUTTORI

    /**
     * @param name il nome dell'atomo
     */
    public Atom(String name) {
        super(name);
    }
    
    //METODI

    @Override
    public Literal getOpposite() {
        return new NegAtom(this.getName());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Atom)) return false;

        Atom a = (Atom)obj;

        return this.getName().equals(a.getName());
    }

    /**
     * 
     * @return una rappresentazione in stringa del letterale, mediante il suo nome
     */
    @Override
    public String toString() {
        return this.getName();
    }
}
