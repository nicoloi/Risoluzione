/**
 * this abstract class is used to represent a formula in propositional logic. 
 * A formula can be atomic or composite.
 */
public abstract class Formula {
    
    /**
     * 
     * @return true, if the formula this is satisfiable. False otherwise.
     */
    public abstract boolean isSatisfiable();
}
