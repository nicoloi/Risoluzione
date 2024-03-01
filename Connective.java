/**
 * This enumeration represents the connectives of propositional logic
 */
public enum Connective {
    NOT,
    AND,
    OR,
    IMPLIES,
    IFF;       //if and only if


    @Override
    public String toString() {
        String res = "";

        switch (this) {
            case NOT:
                res = "~";
                break;
            case AND:
                res = "&";
                break;
            case IFF:
                res = "<->";
                break;
            case IMPLIES:
                res = "->";
                break;
            case OR:
                res = "|";
                break;
        }

        return res;
    }

    
}
