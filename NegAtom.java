
/**
 * questa classe rappresenta un letterale dato da
 * un atomo negato.
 * 
 * Gli oggetti istanziati da questa classe sono immutabili
 */
public class NegAtom extends Literal{

    //COSTRUTTORI

    /**
     * @param name il nome dell'atomo negato
     */
    public NegAtom(String name) {
        super(name);
    }
    
    //METODI
    @Override
    public Literal getOpposite() {
        return new Atom(this.getName());
    }

    private boolean equals(NegAtom natm) {
        return this.getName().equals(natm.getName());
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof NegAtom) && (this.equals( (NegAtom) obj ));
    }

    /**
     * @return una rappresentazione dell'atomo negato, dove il
     *         simbolo not viene rappresentato con il carattere "~".
     */
    @Override
    public String toString() {
        return "~" + this.getName();
    }
}
