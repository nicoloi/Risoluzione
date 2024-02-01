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

        // System.out.println("inserisci una serie di clausole (una per ogni riga) con i letterali separati da spazi.");
        // System.out.println("Il carattere '~' inserito prima di un letterale rappresenta la sua negazione");
        // System.out.println("Per terminare inserisci il carattere '.' in una nuova riga\n");

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

        System.out.println("\nYour set in input:\n" + f + "\n");
        
        boolean sodd = Resolution.isSatisfiable(f);

        if (sodd) {
            System.out.println("\nSATISFIABLE");
        } else {
            System.out.println("\nUNSATISFIABLE");
        }
    }
}
