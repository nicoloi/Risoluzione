

public class Test {
    public static void main(String[] args) {

        Clause c1 = new Clause();
        c1.addLiteral(new Atom("a"));
        c1.addLiteral(new NegAtom("b"));
        c1.addLiteral(new Atom("c"));
        
        Clause c2 = new Clause();
        c2.addLiteral(new Atom("a"));
        c2.addLiteral(new Atom("b"));
        
        Clause c3 = new Clause();
        c3.addLiteral(new NegAtom("a"));
        c3.addLiteral(new Atom("b"));

        Clause c4 = new Clause();
        c4.addLiteral(new Atom("c"));

        ClauseSet f = new ClauseSet();
        f.addClause(c1);
        f.addClause(c2);
        f.addClause(c3);
        f.addClause(c4);

        
        boolean sodd = Resolution.resolution(f);

        if (sodd) {
            System.out.println("\nf e' soddisfacibile");
        } else {
            System.out.println("\nf NON e' soddisfacibile");
        }
    }
}
