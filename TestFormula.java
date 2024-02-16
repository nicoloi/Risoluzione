import java.util.ArrayList;
import java.util.List;

/**
 * this test class is used to test the satisfiability of some formulas 
 * that have already been written. 
 * 
 * for each formula, the test method prints to stdout: 
 * - the input formula.
 * - the corresponding set of clauses.
 * - and finally prints whether it is satisfiable or not.
 */
public class TestFormula {
    
    public static void test() {
        
        Connective AND = Connective.AND;
        Connective OR = Connective.OR;
        Connective NOT = Connective.NOT;
        Connective IMPLIES = Connective.IMPLIES;
        Connective IFF = Connective.IFF;
        
        
        Formula a = new AtomicFormula("a");
        Formula b = new AtomicFormula("b");
        Formula c = new AtomicFormula("c");
        Formula d = new AtomicFormula("d");
        
        CompoundFormula aANDb = new CompoundFormula(AND, a, b);             
        CompoundFormula bANDc = new CompoundFormula(AND, b, c);
        CompoundFormula notAandB = new CompoundFormula(NOT, aANDb, null);  
        CompoundFormula impl = new CompoundFormula(IMPLIES, c, notAandB);
        CompoundFormula f1 = new CompoundFormula(NOT, impl, null);        // ~(c -> ~(a & b))

        
        CompoundFormula notD = new CompoundFormula(NOT, d, null);
        CompoundFormula cOrNotD = new CompoundFormula(OR, c, notD);
        CompoundFormula left = new CompoundFormula(IMPLIES, aANDb, cOrNotD);
        CompoundFormula aORc = new CompoundFormula(OR, a, c);
        CompoundFormula bIMPLd = new CompoundFormula(IMPLIES, b, d);
        CompoundFormula notbIMPLd = new CompoundFormula(NOT, bIMPLd, null);
        CompoundFormula right = new CompoundFormula(AND, aORc, notbIMPLd);
        CompoundFormula f2 = new CompoundFormula(AND, left, right);
        
        
        CompoundFormula notC = new CompoundFormula(NOT, c, null);       
        CompoundFormula aIMPLb = new CompoundFormula(IMPLIES, a, b);   
        CompoundFormula notCIMPLb = new CompoundFormula(IMPLIES, notC, b);   
        CompoundFormula f3 = new CompoundFormula(IMPLIES, aIMPLb, notCIMPLb); // (a -> b) -> (~c -> b)

        
        CompoundFormula aORb = new CompoundFormula(OR, a, b);
        CompoundFormula f4 = new CompoundFormula(IFF, aORb, bANDc); // (a | b) <-> (b & c)
        

        CompoundFormula bIMPLc = new CompoundFormula(IMPLIES, b, c);
        CompoundFormula bimplCANDa = new CompoundFormula(AND, bIMPLc, a);
        CompoundFormula f5 = new CompoundFormula(OR, aANDb, bimplCANDa); // (a & b) | ((b -> c) & a)

        
        CompoundFormula impl2 = new CompoundFormula(IMPLIES, aANDb, cOrNotD);
        CompoundFormula taut = new CompoundFormula(OR, impl2, a);  // ((a & b) -> (c | ~d)) | a
        

        CompoundFormula unsat1 = new CompoundFormula(NOT, taut, null);  // ~(((a & b) -> (c | ~d)) | a)
        
    
        CompoundFormula notA = new CompoundFormula(NOT, a, null);             
        CompoundFormula unsat2 = new CompoundFormula(AND, a, notA); // a & ~a


        List<Formula> formulas = new ArrayList<>();
        formulas.add(f1);
        formulas.add(f2);
        formulas.add(f3);
        formulas.add(f4);
        formulas.add(f5);
        formulas.add(taut);
        formulas.add(unsat1);
        formulas.add(unsat2);

        for (Formula f : formulas) {
            System.out.println("Your formula in input:");
            System.out.println(f);

            ClauseSet cs = ResolutionFormula.getClauseSet(f);
            System.out.println("\nThe result clause set is:");
            System.out.println(cs);
            System.out.println();

            if (Resolution.isSatisfiable(cs, false)) {
                System.out.println("SATISFIABLE");
            } else {
                System.out.println("UNSATISFIABLE");
            }

            System.out.println("_____________________________________________\n");
        }
    }
}
