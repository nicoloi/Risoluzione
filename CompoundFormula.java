import java.util.Objects;

/**
 * this class represents a compound formula. which consists of a formula
 * obtained using a connective of propositional logic on other formulas 
 * which can be atomic or themselves composite.
 */
public class CompoundFormula extends Formula {

    //FIELDS
    private final Connective mainConnective;
    private Formula[] subFormulas; //there are only two subformulas or only one in the case of the "not" connective


    //CONSTRUCTORS
    
    /**
     * 
     * Use this constructor only for binary connectives.
     * 
     * @param mainConnective the main connective of this
     * @param f1 the subformula that is located to the left of the connective
     * @param f2 the subformula that is located to the right of the connective
     * @throws IllegalArgumentException if mainConnective is NOT
     * @throws NullPointerException if the formulas f1 or f2 are null
     */
    public CompoundFormula(Connective mainConnective, Formula f1, Formula f2) {
        
        if (mainConnective == Connective.NOT) {
            throw new IllegalArgumentException("The 'NOT' connective is unary");
        }

        Objects.requireNonNull(f1, "one or both formulas are null");
        Objects.requireNonNull(f2, "one or both formulas are null");  
        subFormulas = new Formula[2];
        subFormulas[0] = f1;
        subFormulas[1] = f2;

        this.mainConnective = mainConnective;
    }

    /**
     * Use this constructor only for NOT connective.
     * 
     * @param mainConnective the main connective 
     * @param f the formula to be negated
     * @throws IllegalArgumentException if mainConnective is a binary connective.
     * @throws NullPointerException if formula f is null
     */
    public CompoundFormula(Connective mainConnective, Formula f) {

        if (mainConnective != Connective.NOT) {
            throw new IllegalArgumentException("You must call this constructor only for NOT connective.");
        }

        Objects.requireNonNull(f, "The formula to be negated is null");
        subFormulas = new Formula[1];
        subFormulas[0] = f;

        this.mainConnective = mainConnective;
    }


    //METHODS

    /**
     * 
     * @return an array that contains the subformulas of the formula this.
     */
    public Formula[] getSubformulas() {
        return subFormulas;
    }

    /**
     *
     * @return the left subformula of this compound formula.
     */
    public Formula getLeftSubformula() {
        return this.subFormulas[0];
    }

    /**
     * 
     * @return the right subformula of this compound formula.
     * @throws UnsupportedOperationException if the main connective is NOT.
     */
    public Formula getRightSubformula() {
        if (this.mainConnective == Connective.NOT) {
            throw new UnsupportedOperationException("this method is not supported for NOT connective");
        }

        return this.subFormulas[1];
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

        if (this.mainConnective == Connective.NOT) {
            res.append("~" + subFormulas[0].toString());
        } else {
            res.append("(" + subFormulas[0].toString() + " " + this.mainConnective.toString() 
                + " " + subFormulas[1].toString() + ")");
        }

        return res.toString();
    }


    @Override
    public ClauseSet toCnf() {

        switch (this.mainConnective) {
            case NOT:
                Formula f1 = this.getLeftSubformula();

                if (f1 instanceof AtomicFormula) {
                    Literal l1 = ( (AtomicFormula)f1).toLiteral();
                    Literal oppL1 = l1.getOpposite();

                    return new ClauseSet( new Clause(oppL1) );    
                } else {
                    CompoundFormula cf1 = (CompoundFormula) f1;
                    Connective mainC = cf1.getMainConnective();

                    if(mainC == Connective.NOT) {
                        //double negation
                        Formula g1 = cf1.getLeftSubformula();

                        return g1.toCnf();
                    } else if(mainC == Connective.OR){ 
                        //De Morgan on ~(g1 | g2)
                        Formula g1 = cf1.getLeftSubformula();   
                        Formula g2 = cf1.getRightSubformula();
                        Formula g  = new CompoundFormula( Connective.AND, 
                            new CompoundFormula (Connective.NOT, g1), new CompoundFormula(Connective.NOT, g2));     // ~g1 & ~g2
                        
                        return g.toCnf();
                    } else if (mainC == Connective.AND) {
                        //De Morgan on ~(g1 & g2)
                        Formula g1 = cf1.getLeftSubformula();   
                        Formula g2 = cf1.getRightSubformula();
                        Formula g = new CompoundFormula(Connective.OR, 
                            new CompoundFormula(Connective.NOT, g1), new CompoundFormula(Connective.NOT, g2));     // ~g1 | ~g2
                        
                        return g.toCnf();
                    } else if (mainC == Connective.IMPLIES) {
                        // ~(g1 -> g2)
                        Formula notG1 = new CompoundFormula(Connective.NOT, cf1.getLeftSubformula());
                        CompoundFormula or = new CompoundFormula(Connective.OR, notG1, cf1.getRightSubformula());

                        return (new CompoundFormula(Connective.NOT, or)).toCnf();
                    } else {
                        // ~(g1 <-> g2)
                        Formula notG1 = new CompoundFormula(Connective.NOT, cf1.getLeftSubformula());
                        Formula notG2 = new CompoundFormula(Connective.NOT, cf1.getRightSubformula());
                        Formula left = new CompoundFormula(Connective.OR, notG1, cf1.getRightSubformula());
                        Formula right = new CompoundFormula(Connective.OR, cf1.getLeftSubformula(), notG2);

                        Formula iff = new CompoundFormula(Connective.AND, left, right);

                        return (new CompoundFormula(Connective.NOT, iff)).toCnf();
                    }

                }
            case IMPLIES:
                // (g1 -> g2)  ===>  ~g1 | g2
                Formula notG1 = new CompoundFormula(Connective.NOT, this.getLeftSubformula());
                CompoundFormula cf = new CompoundFormula(Connective.OR, notG1, this.getRightSubformula());

                return cf.toCnf();
            case IFF:
                // (g1 <-> g2)  ===>  (~g1 | g2) & (g1 | ~g2)
                notG1 = new CompoundFormula(Connective.NOT, this.getLeftSubformula());
                Formula notG2 = new CompoundFormula(Connective.NOT, this.getRightSubformula());
                Formula left = new CompoundFormula(Connective.OR, notG1, this.getRightSubformula());
                Formula right = new CompoundFormula(Connective.OR, this.getLeftSubformula(), notG2);

                return (new CompoundFormula(Connective.AND, left, right)).toCnf();
            case AND:
                Formula g1 = this.getLeftSubformula();   
                Formula g2 = this.getRightSubformula();
                ClauseSet cs1 = g1.toCnf();
                ClauseSet cs2 = g2.toCnf();  
                cs1.union(cs2);

                return cs1;
            case OR:
                g1 = this.getLeftSubformula();
                g2 = this.getRightSubformula();

                cs1 = g1.toCnf();
                cs2 = g2.toCnf();

                ClauseSet csResult = new ClauseSet();

                for (Clause c1 : cs1) {
                    for (Clause c2 : cs2) {
                        csResult.add(Clause.union(c1, c2));
                    }
                }

                return csResult;
        }

        throw new Error("An error occurred while executing the program");
    }
}
