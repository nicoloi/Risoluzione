import java.util.Scanner;

public class Test {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("inserisci una serie di clausole (una per ogni riga) con i letterali separati da spazi.");
        System.out.println("Il carattere '!' inserito prima di un letterale rappresenta la sua negazione");
        System.out.println("Per terminare inserisci il carattere '.' in una nuova riga\n");

        ClauseSet f = new ClauseSet();
        String line = sc.nextLine();

        while (!line.equals(".")) {
            Clause c = new Clause();
            String[] literals = line.split(" ");

            for (String litName : literals) {
                if (!litName.equals("")) {
                    if (litName.charAt(0) == '!') {
                        c.addLiteral(new NegAtom(litName.substring(1)));
                    } else {
                        c.addLiteral(new Atom(litName));
                    }
                }
            }
            f.addClause(c);
            line = sc.nextLine();
        }

        sc.close();

        System.out.println("\nSet di clausole in input:\n" + f);
        
        boolean sodd = Resolution.resolution(f);

        if (sodd) {
            System.out.println("\nIl set di clausole e' SODDISFACIBILE\n");
        } else {
            System.out.println("\nIl set di clausole e' INSODDISFACIBILE\n");
        }

    }
}
