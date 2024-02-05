import java.util.Scanner;

/**
 * Questa classe di test permette di testare il funzionamento del metodo di risoluzione.
 * Il set di clausole da testare viene preso da standard input. Successivamente viene mostrato
 * su standard output la rappresentazione testuale del set di clausole immesso in input (così da
 * vedere se non sono stati fatti errori nell'input), ed infine viene mostrata una stringa che dice
 * se il set è soddisfacibile o meno. 
 */
public class Test {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ClauseSet f = new ClauseSet();

        while (sc.hasNext()) {
            Clause c = new Clause();
            String[] literals = sc.nextLine().split(" ");

            for (String litName : literals) {
                if (!litName.equals("")) {
                    if (litName.charAt(0) == '~') {
                        c.addLiteral(new NegAtom(litName.substring(1)));
                    } else {
                        c.addLiteral(new Atom(litName));
                    }
                }
            }

            if (!c.isEmpty()) {
                f.addClause(c);
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
