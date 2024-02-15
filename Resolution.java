import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * This class without constructors contains methods that implement 
 * the resolution method in propositional logic for a set of clauses.
 */
public class Resolution {

    private static Map<Integer, Set<Integer>> visited;
    private static List<Step> trace;

    /**
     * This static method checks whether a set of clauses
     * is satisfiable or not.
     * 
     * 
     * @param s the set of clauses to consider for the resolution method.
     * @param enableSteps the boolean variable used to indicate whether 
     *        or not to print the list of steps applied by the resolution method.
     * 
     * @return true, if s is satisfiable, false otherwise.
     * @throws NullPointerException if s is null.
     * @throws IllegalArgumentException if s is empty.
     */
    public static boolean isSatisfiable(ClauseSet s, boolean enableSteps) {

        Objects.requireNonNull(s);
        if (s.isEmpty()) throw new IllegalArgumentException("the clause set in input is empty");

        visited = new HashMap<>();
        trace = new ArrayList<>();
        List<Clause> listCl = new ArrayList<>(); 

        for (Clause c : s) {
            listCl.add(c);
        }

        for (int i = 0; i < listCl.size(); i++) {

            Clause c1 = listCl.get(i);

            Set<Integer> valueSet = new HashSet<>();
            visited.put(c1.getIndex(), valueSet);
            
            for (int j = 0; j < listCl.size(); j++) {

                Clause c2 = listCl.get(j);

                if (!alreadyVisited(c1, c2)) {

                    Literal complemLit = getComplementaryLiterals(c1, c2);

                    if (complemLit != null) {
                        //adds the index of c2 to the set of clauses already visited by c1
                        valueSet.add(c2.getIndex());

                        Clause newClause = resolRule(c1, c2, complemLit);

                        //create a new step and insert the clauses and literal
                        Step step = new Step(c1, c2, newClause, complemLit);
                        trace.add(step);

                        /*
                        * if the resolving clause is empty, then we have found a contradiction 
                        * which proves that the set s is not satisfiable.
                        */
                        if (newClause.isEmpty()) {
                            if (enableSteps) printTrace();

                            return false;
                        } 

                        if (isTautology(newClause)) {
                            step.setTautology();
                        } else if (listCl.contains(newClause)) {
                            step.setAlreadyPresent();
                        } else {
                            listCl.add(newClause);
                        }
                    }
                }
            }
        }

        if (enableSteps) printTrace();

        /*
         * if after analyzing all the pairs of clauses in s, 
         * the contradiction is not found, then s is satisfiable
         */
        return true;
    }

    /**
     *
     * This method checks whether two clauses have at least one literal in common
     * where one is the opposite of the other, so that the resolution rule for the 
     * two clauses can be executed.
     * 
     * @param c1 the first clause
     * @param c2 the second clause
     * @return the literal in common, that is complementary in the two clauses.
     * @return null, if the literal to search is not present.     
     */
    private static Literal getComplementaryLiterals(Clause c1, Clause c2) { 

        if (c1.equals(c2)) return null; //the resolution rule need not apply to the same clause

        return findComplementary(c1, c2);
    }

    //find the complementary literal.
    private static Literal findComplementary(Clause c1, Clause c2) {
        for (Literal l1 : c1) {
            for (Literal l2 : c2) { 
                if (l1.equals(l2.getOpposite())) return l1;
            }
        }

        return null;
    }

    /**
     * 
     * @param c1 the first clause.
     * @param c2 the second clause.
     * @return true, if the two clauses c1 and c2 are already compared previously
     *         by the resolution method. false otherwise.
     */
    private static boolean alreadyVisited(Clause c1, Clause c2) {
        int i1 = c1.getIndex();
        int i2 = c2.getIndex();

        if (visited.containsKey(i2)) {
            //case where switch c1 with c2.
            return (visited.get(i2)).contains(i1);
        }

        return (visited.get(i1)).contains(i2);
    }


    /**
     * 
     * This method implements the resolution rule, in which two premise clauses are considered,
     * a pair of complementary literals in the two clauses are deleted, 
     * and the remaining literals are put into logical disjunction, 
     * thus forming the resolving clause.
     * 
     * @param c1 the first clause.
     * @param c2 the second clause.
     * @param lit the literal that must be deleted, with its opposite. 
     * @return the clause obtained by disjuncting the two clauses and 
     *         deleting the pair of literals
     */
    private static Clause resolRule(Clause c1, Clause c2, Literal lit) {
        Clause result = new Clause();
        List<Literal> literals = new ArrayList<>();
        
        for (Literal l : c1) {
            literals.add(l);
        }

        for (Literal l : c2) {
            literals.add(l);
        }

        literals.remove(lit);
        literals.remove(lit.getOpposite());

        for (Literal l : literals) {
            result.addLiteral(l);
        }
        
        return result;
    }

    /**
     * This method checks whether the resolution clause 
     * obtained with the resolution rule is a tautology.
     * 
     * @param resolvent the clause to be checked.
     * @return true, if the resolvent clause is a tautology 
     *         (i.e. does not contain complementary literals).
     *         false otherwise.
     */
    private static boolean isTautology(Clause resolvent) {
        return findComplementary(resolvent, resolvent) != null;
    }

    /**
     * prints the trace of the steps that have been performed by the resolution method.
     */
    private static void printTrace() {
        for (Step st : trace) {
            System.out.println(st.toString());
        }
    }
}
