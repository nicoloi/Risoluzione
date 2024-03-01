/**
 * this abstract class is used to represent a formula in propositional logic. 
 * A formula can be atomic or composite.
 */
public abstract class Formula {
    
    /**
     * converts this formula to conjunctive normal form.
     * 
     * @return the clauseset representing this formula converted to CNF.
     */
    public abstract ClauseSet toCnf();
}
