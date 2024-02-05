import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Questa classe senza costruttori contiene dei metodi che implementano
 * l'algoritmo di risoluzione nella logica proposizionale per 
 * un insieme di clausole.
 */
public class Resolution {

    private static Map<Integer, Set<Integer>> visited;
    private static List<Step> trace;

    /**
     * Questo metodo statico verifica se un insieme di clausole 
     * è soddisfacibile o no.
     * 
     * 
     * @param s l'insieme di clausole da considerare per l'algoritmo
     * @param enableSteps
     * @return true - se s è soddisfacibile, false altrimenti.
     * @throws NullPointerException se s è null.
     * @throws IllegalArgumentException se s è vuoto.
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

        //non posso usare foreach perchè la lista viene modificata
        for (int i = 0; i < listCl.size(); i++) {

            Clause c1 = listCl.get(i);

            Set<Integer> valueSet = new HashSet<>();
            visited.put(c1.getIndex(), valueSet);
            
            for (int j = 0; j < listCl.size(); j++) {

                Clause c2 = listCl.get(j);

                if (!alreadyVisited(c1, c2)) {

                    Literal complemLit = getComplementaryLiterals(c1, c2);

                    if (complemLit != null) {
                        //aggiungi l'indice di c2 al set delle clausole già visitate da c1
                        valueSet.add(c2.getIndex());

                        Clause newClause = resolRule(c1, c2, complemLit);

                        //create a new step and insert the clauses and literal
                        Step step = new Step(c1, c2, newClause, complemLit);
                        trace.add(step);

                        /*
                        * se la clausola risolvente è vuota,
                        * allora abbiamo trovato una contraddizione che 
                        * dimostra che l'insieme s non è soddisfacibile.
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
         * se dopo aver analizzato tutte le coppie di clausole in s,
         * non trovo la contraddizione, allora s risulta soddisfacibile
         */
        return true;
    }

    /**
     *
     * Questo metodo controlla se due clausole hanno almeno un letterale
     * in comune in cui uno è l'opposto dell'altro, così da poter svolgere
     * la regola di risoluzione per le due clausole.
     * 
     * @param c1 la prima clausola.
     * @param c2 la seconda clausola.
     * @return true se c'è almeno un letterale complementare nelle clausole.
     *        
     */
    private static Literal getComplementaryLiterals(Clause c1, Clause c2) { 

        if (c1.equals(c2)) return null; //la regola di risoluzione non deve applicarsi alla stessa clausola

        return findComplementary(c1, c2);
    }

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
     * @param c1 la prima clausola.
     * @param c2 la seconda clausola.
     * @return true se le due clausole c1 e c2 sono già state
     *         confrontate in precedenza dal metodo resolution.
     *         false altrimenti.
     */
    private static boolean alreadyVisited(Clause c1, Clause c2) {
        int i1 = c1.getIndex();
        int i2 = c2.getIndex();

        if (visited.containsKey(i2)) {
            //caso dove scambio c1 con c2
            return (visited.get(i2)).contains(i1);
        }

        return (visited.get(i1)).contains(i2);
    }


    /**
     * 
     * Questo metodo implementa la regola di risoluzione,
     * in cui si considerano due clausole di premessa, si cancella una coppia
     * di letterali complementari nelle due clausole e si mettono in congiunzione
     * i letterali rimanenti, formando così la clausola risolvente.
     * 
     * @param c1 la prima clausola
     * @param c2 la seconda clausola
     * @param lit il letterale che deve essere eliminato dalla risolvente insieme
     *            al suo complementare.
     * @return la clausola ottenuta mettendo in disgiunzione le
     *         due clausole e cancellando la coppia di letterali lit e l'opposto di lit.
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
     * Questo metodo verifica se la clausola risolvente ottenuta con
     * la regola di risoluzione è una tautologia.
     * 
     * @param resolvent la clausola risolvente da verificare.
     * @return true - se la clausola resolvent è una tautologia
     *         (cioè non contiene letterali complementari).
     */
    private static boolean isTautology(Clause resolvent) {
        return findComplementary(resolvent, resolvent) != null;
    }

    private static void printTrace() {
        for (Step st : trace) {
            System.out.println(st.toString());
        }
    }
}
