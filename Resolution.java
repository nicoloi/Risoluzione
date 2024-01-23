import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Questa classe senza costruttori contiene dei metodi che implementano
 * l'algoritmo di risoluzione nella logica proposizionale.
 */
public class Resolution {

    /*
     * mappa che ha come chiave un insieme di due clausole,
     * e come valore un booleano che viene impostato a true, 
     * quando la coppia di clausole viene visitata dall'algoritmo di risoluzione.
     */
    private static Map<Set<Clause>, Boolean> visited;

    /**
     * Questo metodo statico implementa l'algoritmo di risoluzione per
     * verificare se un insieme di clausole è soddisfacibile.
     * 
     * @param s l'insieme di clausole da considerare per l'algoritmo
     * @return true, se s è soddisfacibile, false altrimenti.
     */
    public static boolean resolution(ClauseSet s) {

        if (s.isEmpty()) return false;

        visited = new HashMap<>();
        List<Clause> listCl = new ArrayList<>(); 

        for (Clause c : s) {
            listCl.add(c);
        }

        //non posso usare foreach perchè la lista viene modificata
        for (int i = 0; i < listCl.size(); i++) {

            Clause c1 = listCl.get(i);

            for (int j = 0; j < listCl.size(); j++) {

                Clause c2 = listCl.get(j);

                if (!giaVisitato(c1, c2) && checkClause(c1, c2)) {
                    //imposta il gia visitato
                    Set<Clause> couple = new HashSet<>();
                    couple.add(c1);
                    couple.add(c2);
                    visited.put(couple, true);

                    Clause newClause = resolStep(c1, c2);

                    //TODO TOGLI PRINT
                    System.out.println("NUOVA CLAUS: " + newClause);

                    /*
                     * se la clausola risultante è vuota,
                     * allora abbiamo trovato una contraddizione che 
                     * dimostra che l'insieme s non è soddisfacibile.
                     */
                    if (newClause.isEmpty()) return false;

                    if (!listCl.contains(newClause)) {
                        //TODO
                        System.out.println("AGGIUNTA CLAUSOLA");
                        listCl.add(newClause); 
                    }
                }
            }
        }

        /*
         * se dopo aver analizzato tutte le coppie di clausole in s,
         * non trovo la contraddizione, allora s risulta soddisfacibile
         */
        return true; 
    }

    /**
     * 
     * Questo metodo controlla se due clausole hanno almeno un letterale
     * in comune in cui uno è l'opposto dell'altro.
     * 
     * @param c1 la prima clausola.
     * @param c2 la seconda clausola.
     * @return true se c'è almeno un letterale complementare nelle clausole.
     *        
     */
    private static boolean checkClause(Clause c1, Clause c2) {

        if (c1.equals(c2)) return false;

        for (Literal l1 : c1) {
            for (Literal l2 : c2) {
                if (l1.equals(l2.getOpposite())) return true;
            }
        }

        return false;
    }

    /**
     * 
     * @param c1 la prima clausola.
     * @param c2 la seconda clausola.
     * @return true se le due clausole c1 e c2 sono già state
     *         confrontate in precedenza dal metodo resolution.
     *         false altrimenti.
     */
    private static boolean giaVisitato(Clause c1, Clause c2) {
        Set<Clause> couple = new HashSet<>();
        couple.add(c1);
        couple.add(c2);

        if (visited.containsKey(couple)) {
            //TODO
            System.out.printf("LA CLAUSOLA: %s E LA CAUSOLA: %s GIA' VISITATE\n", c1, c2);
            return visited.get(couple);
        }

        return false;
    }


    /**
     * 
     * Questo metodo implementa il passo dell'algoritmo di risoluzione,
     * in cui si considerano due clausole di premessa, si cancellano 
     * i letterali complementari e si mettono insieme i letterali rimanenti, 
     * formando così la clausola risultante.
     * 
     * @param c1 la prima clausola
     * @param c2 la seconda clausola
     * @return la clausola ottenuta mettendo in disgiunzione le
     *         due clausole e cancellando i letterali complementari
     *         presenti.
     */
    private static Clause resolStep(Clause c1, Clause c2) {
        Clause result = new Clause();
        List<Literal> literals = new ArrayList<>();
        
        for (Literal l : c1) {
            literals.add(l);
        }

        for (Literal l : c2) {
            literals.add(l);
        }
        
        int i = 0;

        while (i < literals.size()) {

            Literal l1 = literals.get(i);
            boolean flag = false;

            for (Literal l2 : literals) {
                if (l1.equals(l2.getOpposite())) {
                    flag = true;
                    break;
                }
            }

            if (flag) {
                literals.remove(l1);
                literals.remove(l1.getOpposite());
                i = 0; //ripeto la scansione della lista da capo
            } else {
                i++; //vado avanti nella scansione
            }
        }

        for (Literal l : literals) {
            result.addLiteral(l);
        }

        return result;
    }
}
