import java.util.Objects;

/**
 * this class represents a compound formula. which consists of a formula
 * obtained using a connective of propositional logic on other formulas 
 * which can be atomic or themselves composite.
 */
public class CompoundFormula extends Formula {

    //STATIC FIELDS
    private static final Connective NOT = Connective.NOT;
    // private static final Connective AND = Connective.AND;
    // private static final Connective OR = Connective.OR;
    // private static final Connective IMPLIES = Connective.IMPLIES;
    // private static final Connective IFF = Connective.IFF;

    //FIELDS
    private Connective mainConnective;
    private Formula[] subFormulas; //there are only two subformulas or only one in the case of the "not" connective


    //CONSTRUCTORS
    
    /**
     * Construct a new instance of the compound formula.
     * 
     * @param mainConnective the main connective of this
     * @param f1 the subformula that is located to the left of the connective
     * @param f2 the subformula that is located to the right of the connective
     * @throws IllegalArgumentException if the formula f2 is not null in the case of NOT connective
     * @throws NullPointerException if the formulas f1 or f2 are null in the other connective
     */
    public CompoundFormula(Connective mainConnective, Formula f1, Formula f2) {
        
        if (mainConnective == NOT) {

            if (f2 != null) {
                throw new IllegalArgumentException("The 'NOT' connective is unary");
            }

            Objects.requireNonNull(f1, "the formula to be negated is null");

            subFormulas = new Formula[1];
            subFormulas[0] = f1;
        } else {

            Objects.requireNonNull(f1, "one or both formulas are null");
            Objects.requireNonNull(f2, "one or both formulas are null");

            subFormulas = new Formula[2];
            subFormulas[0] = f1;
            subFormulas[1] = f2;
        }
        
        this.mainConnective = mainConnective;
    }

    //METHODS

    /**
     * 
     * @return an array that contains the subformulas of the formula this.
     */
    public Formula[] getSubformulas() {
        return subFormulas.clone();
    }

    /**
     * 
     * @return the main connective of the formula this.
     */
    public Connective getMainConnective() {
        return mainConnective;
    }


    
    @Override
    public String toString() {

        StringBuilder res = new StringBuilder();

        switch (this.mainConnective) {
            case NOT:
                res.append("~" + subFormulas[0].toString());
                break;
            case AND:
                res.append("(" + subFormulas[0].toString() + " & " + subFormulas[1].toString() + ")");
                break;
            case IFF:
                res.append("(" + subFormulas[0].toString() + " <-> " + subFormulas[1].toString() + ")");
                break;
            case IMPLIES:
                res.append("(" + subFormulas[0].toString() + " -> " + subFormulas[1].toString() + ")");
                break;
            case OR:
                res.append("(" + subFormulas[0].toString() + " | " + subFormulas[1].toString() + ")");
                break;
        }


        return res.toString();
    }



    @Override
    public boolean isSatisfiable() {
        return ResolutionFormula.isSatisfiable(this);
    }

    /**
     * 
     * @param newFormula the new subformula to be added to the left of the connective. 
     *        if the connective is NOT, we can only use this method to change the subformula
     */
    public void changeLeft(Formula newFormula) {
        Objects.requireNonNull(newFormula);

        this.subFormulas[0] = newFormula;
    }

    /**
     * 
     * @param newFormula the new subformula to be inserted to the right of the connective.
     * @throws UnsupportedOperationException if the main connective is NOT.
     */
    public void changeRight(Formula newFormula) {
        if (this.mainConnective == NOT) {
            throw new UnsupportedOperationException("this method is not supported for NOT connective");
        }

        Objects.requireNonNull(newFormula);

        this.subFormulas[1] = newFormula;
    }
}
