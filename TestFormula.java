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

        Formula a = new AtomicFormula("a");
        Formula b = new AtomicFormula("b");
        Formula c = new AtomicFormula("c");
        Formula d = new AtomicFormula("d");


        CompoundFormula aANDb = new CompoundFormula(Connective.AND, a, b);             
        CompoundFormula bANDc = new CompoundFormula(Connective.AND, b, c);
        CompoundFormula notAandB = new CompoundFormula(Connective.NOT, aANDb);  
        CompoundFormula impl = new CompoundFormula(Connective.IMPLIES, c, notAandB);
        CompoundFormula f1 = new CompoundFormula(Connective.NOT, impl);        // ~(c -> ~(a & b))

        
        CompoundFormula notD = new CompoundFormula(Connective.NOT, d);
        CompoundFormula cOrNotD = new CompoundFormula(Connective.OR, c, notD);
        CompoundFormula left = new CompoundFormula(Connective.IMPLIES, aANDb, cOrNotD);
        CompoundFormula aORc = new CompoundFormula(Connective.OR, a, c);
        CompoundFormula bIMPLd = new CompoundFormula(Connective.IMPLIES, b, d);
        CompoundFormula notbIMPLd = new CompoundFormula(Connective.NOT, bIMPLd);
        CompoundFormula right = new CompoundFormula(Connective.AND, aORc, notbIMPLd);
        CompoundFormula f2 = new CompoundFormula(Connective.AND, left, right);
        
        
        CompoundFormula notC = new CompoundFormula(Connective.NOT, c);       
        CompoundFormula aIMPLb = new CompoundFormula(Connective.IMPLIES, a, b);   
        CompoundFormula notCIMPLb = new CompoundFormula(Connective.IMPLIES, notC, b);   
        CompoundFormula f3 = new CompoundFormula(Connective.IMPLIES, aIMPLb, notCIMPLb); // (a -> b) -> (~c -> b)

        
        CompoundFormula aORb = new CompoundFormula(Connective.OR, a, b);
        CompoundFormula f4 = new CompoundFormula(Connective.IFF, aORb, bANDc); // (a | b) <-> (b & c)
        

        CompoundFormula bIMPLc = new CompoundFormula(Connective.IMPLIES, b, c);
        CompoundFormula bimplCANDa = new CompoundFormula(Connective.AND, bIMPLc, a);
        CompoundFormula f5 = new CompoundFormula(Connective.OR, aANDb, bimplCANDa); // (a & b) | ((b -> c) & a)

        CompoundFormula g1 = new CompoundFormula(Connective.NOT, aIMPLb);
        CompoundFormula cANDd = new CompoundFormula(Connective.AND, c, d);
        CompoundFormula g2 = new CompoundFormula(Connective.NOT, new CompoundFormula(Connective.NOT, cANDd));
        CompoundFormula f6 = new CompoundFormula(Connective.NOT, new CompoundFormula(Connective.IFF, g1, g2));
            // ~( ~(a -> b) <-> ~~(c & d) )

        
        CompoundFormula impl2 = new CompoundFormula(Connective.IMPLIES, aANDb, cOrNotD);
        CompoundFormula taut = new CompoundFormula(Connective.OR, impl2, a);  // ((a & b) -> (c | ~d)) | a
        

        CompoundFormula unsat1 = new CompoundFormula(Connective.NOT, taut);  // ~(((a & b) -> (c | ~d)) | a)
        
    
        CompoundFormula notA = new CompoundFormula(Connective.NOT, a);             
        CompoundFormula unsat2 = new CompoundFormula(Connective.AND, a, notA); // a & ~a


        List<Formula> formulas = new ArrayList<>();
        formulas.add(f1);
        formulas.add(f2);
        formulas.add(f3);
        formulas.add(f4);
        formulas.add(f5);
        formulas.add(f6);
        formulas.add(taut);
        formulas.add(unsat1);
        formulas.add(unsat2);

        for (Formula f : formulas) {
            System.out.println("Your formula in input:");
            System.out.println(f);
            
            ClauseSet cnf = f.toCnf();
            
            System.out.println("\nThe result clause set is:");
            System.out.println(cnf);
            System.out.println();

            if (Resolution.isSatisfiable(cnf, false)) {
                System.out.println("SATISFIABLE");
            } else {
                System.out.println("UNSATISFIABLE");
            }

            System.out.println("_____________________________________________\n");
        }
    }
}
