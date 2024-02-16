
public class ResolutionFormula {

    //STATIC FIELDS
    private static final Connective AND = Connective.AND;
    private static final Connective OR = Connective.OR;
    private static final Connective NOT = Connective.NOT;
    private static final Connective IMPLIES = Connective.IMPLIES;
    private static final Connective IFF = Connective.IFF;
    
    
    private static CompoundFormula parent = null; //this variable is used for the construction of the cnf
    private static Formula radixCNF = null; //contains the CNF formula used in the getClauseSet method
    private static boolean hasChanged; //this variable is set to true when, during the construction of the CNF,
                                       //a subformula of the radixCNF variable is modified.

    private static boolean leftSide; //this variable is used to see whether to modify the left
                                     // or right child of a subformula of the radixCNF variable
     

    //STATIC METHODS

    /**
     * 
     * @param f the formula to check if it is satisfiable or not.
     * @return true, if f is satisfiable, false otherwise.
     */
    public static boolean isSatisfiable(Formula f) {
        return Resolution.isSatisfiable(getClauseSet(f), false);
    }

    /**
     * this method converts a given formula into conjunctive normal form
     * and returns the corresponding set of clauses.
     * 
     * @param f the formula to convert into a set of clauses.
     * @return the set of clauses that corresponds to the CNF of the formula f.
     */
    public static ClauseSet getClauseSet(Formula f) {

        radixCNF = copy(f); //we used a copy to avoid side effects.

        do {
            hasChanged = false;
            parent = null;
            toCNF(radixCNF);
        } while (hasChanged); //we need to repeat the visit to the radixCNF variable while changes are made to it


        //now radixCNF contains the formula f converted to conjuntive normal form.
        ClauseSet res = new ClauseSet();
        Clause c = new Clause();
        res.addClause(c);

        createClauseSet(radixCNF, res, c.getIndex());

        return res;
    } 

    /**
     * this method applies the conversion to CNF of a given formula,
     * making a recursive visit to its tree structure, and applying 
     * the conversion rules when appropriate. For example De Morgan's laws
     * or material implication.
     * 
     * @param f the formula on which the conversion to CNF must be applied.
     *          (the global variable radixCNF and its subformulas)
     */
    private static void toCNF(Formula f) {

        if (f instanceof AtomicFormula) {
            return;
        }

        CompoundFormula cf = (CompoundFormula) f;
        Formula newFormula = null;

        switch (cf.getMainConnective()) {
            case IFF:
                newFormula = deleteIFF(cf);
                break;
            case IMPLIES:
                newFormula = applyMaterialImplication(cf);
                break;
            case NOT:
                Formula opposite = cf.getSubformulas()[0];

                if (opposite instanceof CompoundFormula) {
                    Connective oppositeConn = ((CompoundFormula) opposite).getMainConnective();

                    if (oppositeConn == NOT) {
                        newFormula = deleteDoubleNegation(cf);
                    } else if (oppositeConn == AND || oppositeConn == OR) {
                        newFormula = applyDeMorgan(cf);
                    }
                }
                break;
            case OR:
                newFormula = distributiveProperty(cf); //the check to make sure whether the distributive property 
                                                       //is applied or not, is performed within the method
                break;
            case AND:
                //do nothing
                break;
        }

        if (newFormula == null) { //if no changes are made to the cf node

            leftSide = true;

            parent = cf;

            Formula[] sub = cf.getSubformulas();

            toCNF(sub[0]);

            if (cf.getMainConnective() != NOT) {
                parent = cf; //the parent can change
                leftSide = false;
                toCNF(sub[1]);
            }
        } else {
            //we need to add the newFormula to the parent
            if (parent != null) {
                if (leftSide) {
                    parent.changeLeft(newFormula);
                } else {
                    parent.changeRight(newFormula);
                }
            } else {
                radixCNF = newFormula;
            }

            hasChanged = true; //set the global variable to true;
        }
    }


    /**
     * 
     * @param cf the formula to which the rule of material implication applies.
     * @return the formula to which the rule was applied.
     */
    private static Formula applyMaterialImplication(CompoundFormula cf) {

        Formula[] sub = cf.getSubformulas();

        //we need to negate the left side of implication
        CompoundFormula negateLeft = new CompoundFormula(NOT, sub[0], null);

        return new CompoundFormula(OR, negateLeft, sub[1]);
    }


    /**
     * this method applies the material implication rule on the IFF connective.
     * for example if we have the formula (a <-> b),
     * the formula (~a | b) & (~b | a) will be returned.
     * 
     * @param cf the formula to which the rule of material implication applies.
     * @return the formula to which the rule was applied.
     */
    private static Formula deleteIFF(CompoundFormula cf) {
        if (cf.getMainConnective() != IFF) return null;

        Formula[] sub = cf.getSubformulas();

        CompoundFormula left = new CompoundFormula(IMPLIES, sub[0], sub[1]);
        CompoundFormula right = new CompoundFormula(IMPLIES, sub[1], sub[0]);
        return new CompoundFormula(AND, applyMaterialImplication(left), applyMaterialImplication(right));
    }


    /**
     * applies de Morgan's laws on the negation of OR, or on the negation of AND.
     * 
     * @param cf the formula to which the rule of De Morgan applies.
     * @return the formula to which the rule was applied.
     */
    private static Formula applyDeMorgan(CompoundFormula cf) {

        CompoundFormula opposite = (CompoundFormula) cf.getSubformulas()[0];
        Formula[] sub = opposite.getSubformulas();

        if (opposite.getMainConnective() == OR) {
            CompoundFormula negateLeft = new CompoundFormula(NOT, sub[0], null);
            CompoundFormula negateRight = new CompoundFormula(NOT, sub[1], null);

            return new CompoundFormula(AND, negateLeft, negateRight);

        } else if (opposite.getMainConnective() == AND) {
            CompoundFormula negateLeft = new CompoundFormula(NOT, sub[0], null);
            CompoundFormula negateRight = new CompoundFormula(NOT, sub[1], null);

            return new CompoundFormula(OR, negateLeft, negateRight);
        }

        return null; 
    }


    /**
     * removes the double negation, for example if we have the formula (~~a),
     * the method returns a
     * 
     * @param cf the formula to which the rule of removal the double negation applies.
     * @return the formula to which the rule was applied.
     */
    private static Formula deleteDoubleNegation(CompoundFormula cf) {

        CompoundFormula opposite = (CompoundFormula) cf.getSubformulas()[0];
        return opposite.getSubformulas()[0];
    }


    /**
     * this method checks whether the distributive property rule can be applied
     * to the given formula. If this is true, it applies the rule, 
     * otherwise it returns null
     * 
     * @param cf the formula to which the rule of distributive property applies.
     * @return the formula to which the rule was applied.
     * @return null, if if the rule cannot be applied.
     */
    private static Formula distributiveProperty(CompoundFormula cf) {

        Formula left = cf.getSubformulas()[0];
        Formula right = cf.getSubformulas()[1];

        if ((left instanceof CompoundFormula) && (((CompoundFormula)left).getMainConnective() == AND)) {
            //case (a & b) | c
            CompoundFormula leftCf = (CompoundFormula)left;

            Formula[] sub = leftCf.getSubformulas();

            //apply the distributive property
            CompoundFormula f1 = new CompoundFormula(OR, sub[0], right);
            CompoundFormula f2 = new CompoundFormula(OR, sub[1], right);

            return new CompoundFormula(AND, f1, f2);
        } 
        
        if ((right instanceof CompoundFormula) && (((CompoundFormula)right).getMainConnective() == AND)) {
            //case a | (b & c)
            CompoundFormula rightCf = (CompoundFormula)right;

            Formula[] sub = rightCf.getSubformulas();
        
            //apply the distributive property
            CompoundFormula f1 = new CompoundFormula(OR, sub[0], left);
            CompoundFormula f2 = new CompoundFormula(OR, sub[1], left);

            return new CompoundFormula(AND, f1, f2);
        }

        return null;
    }

    /**
     * 
     * @param f the formula to copy
     * @return a copy of formula f, in this way the formula f is not modified by the toCNF() method
     */
    private static Formula copy(Formula f) {
        if (f instanceof AtomicFormula) {
            return new AtomicFormula(((AtomicFormula)f).getName());
        }

        CompoundFormula cf = (CompoundFormula) f;
        
        if (cf.getMainConnective() == NOT) {
            return new CompoundFormula(NOT, cf.getSubformulas()[0], null);
        }

        Formula[] sub = cf.getSubformulas();

        return new CompoundFormula(cf.getMainConnective(), sub[0], sub[1]);
    }



    /**
     * this method visits the tree structure of the CNF formula, 
     * to insert its clauses into the input clauseset as the
     * second parameter of the method.
     * 
     * @param cnf the formula in conjunctive normal form.
     * @param cs the clause set to be filled.
     * @param index the index of the clause to which to add the literals.
     */
    private static void createClauseSet(Formula cnf, ClauseSet cs, int index) {
        
        if (cnf instanceof AtomicFormula) {
            cs.getByIndex(index).addLiteral(((AtomicFormula)cnf).getLiteral());
        } else {
            CompoundFormula cf = (CompoundFormula) cnf;
            Formula[] sub = cf.getSubformulas();

            switch (cf.getMainConnective()) {
                case AND:
                    createClauseSet(sub[0], cs, index);
                    Clause newClause = new Clause();
                    cs.addClause(newClause);

                    createClauseSet(sub[1], cs, newClause.getIndex());
                    break;
                case OR:
                    createClauseSet(sub[0], cs, index);
                    createClauseSet(sub[1], cs, index);
                    break;
                case NOT:
                    if (!(sub[0] instanceof AtomicFormula)) {
                        throw new IllegalArgumentException("the formula in input is not a CNF");
                    }

                    cs.getByIndex(index).addLiteral(((AtomicFormula)sub[0]).getLiteral().getOpposite());
                    break;
                default:
                    throw new IllegalArgumentException("the formula in input is not a CNF");
                
            }
        }
    }
}
