import java.util.Objects;

/**
 * Questa classe astratta rappresenta un letterale nella logica proposizionale,
 * che può essere un atomo oppure un atomo negato.
 */
public abstract class Literal {

    //ATTRIBUTI
    private final String name; //rappresenta il nome del letterale

    //COSTRUTTORI

    /**
     * @param name il nome del letterale
     * @throws NullPointerException se il parametro name è null
     * @throws IllegalArgumentException se il parametro name è una stringa vuota
     * 
     * questo costruttore serve alle sottoclassi per istanziare il letterale,
     * con il nome che viene fornito come parametro.
     */
    protected Literal(String name) {
        Objects.requireNonNull(name);

        if (name.equals("")) {
            throw new IllegalArgumentException("il nome non può essere vuoto");
        }

        this.name = name;
    }

    //METODI
    
    /**
     * @return il nome del letterale.
     */
    public String getName() {
        return name;
    }

    /**
     * @return l'opposto del letterale this. se il letterale è istanza
     *         della classe Atom restituisce l'istanza NegAtom corrispondente,
     *         e viceversa. 
     */
    public abstract Literal getOpposite();

    @Override
    public int hashCode() {
        return 1;
    }
}