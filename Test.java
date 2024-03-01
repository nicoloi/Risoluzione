import java.util.Scanner;

/**
 * This class of tests is used to test the functioning of the resolution method 
 * in a set of clauses or in formulas.
 * The set of clauses to be tested is taken from standard input. Subsequently, 
 * the textual representation of the set of clauses entered in the input is shown 
 * on standard output, and finally a string is shown that says whether the set 
 * is satisfiable or not. 
 * if you write "trace" in the command line, the list of
 * steps of the resolution method is printed on standard output.
 * 
 * if you write "formula" in the command line, then the test present
 * in the TestFormula class is executed.
 * In this case, the resolution method test is performed on some 
 * already written formulas.
 */
public class Test {

    public static void main(String[] args) {

        if (args.length != 0 && args[0].equals("formula")) {
            TestFormula.test();
            return;
        }

        Scanner sc = new Scanner(System.in);
        ClauseSet f = new ClauseSet();

        while (sc.hasNext()) {
            Clause c = new Clause();
            String[] literals = sc.nextLine().split(" ");

            for (String litName : literals) {
                if (!litName.equals("")) {
                    if (litName.charAt(0) == '~') {
                        c.add(new NegAtom(litName.substring(1)));
                    } else {
                        c.add(new Atom(litName));
                    }
                }
            }

            if (!c.isEmpty()) {
                f.add(c);
            }
        }

        sc.close();


        boolean enableSteps = false;
        if (args.length != 0 && args[0].equals("trace")) {
            enableSteps = true;
        }

        System.out.println("\nYour set in input:\n" + f + "\n");
        
        boolean sodd = Resolution.isSatisfiable(f, enableSteps);

        if (sodd) {
            System.out.println("\nSATISFIABLE");
        } else {
            System.out.println("\nUNSATISFIABLE");
        }
    }
}
