

public class Step {

    //STATIC FIELDS
    private static int count = 1;

    //FIELDS
    private int stepNumber;
    private Clause premise1;
    private Clause premise2;
    private Clause conclusion;
    private Literal complementary;
    private boolean isTautology;

    //CONSTRUCTORS
    public Step(Clause premise1, Clause premise2, Clause conclusion, Literal complementary) {
        this.premise1 = premise1;
        this.premise2 = premise2;
        this.conclusion = conclusion;
        this.complementary = complementary;

        this.stepNumber = count;
        this.isTautology = false;
        count++;
    }

    //METHODS

    public void setTautology() {
        isTautology = true;
    }



    @Override
    public String toString() {
        //deve stampare il numero dello step corrente.
        //deve stampare le due clausole di premessa e la clausola risolvente
        //specificando qual è il letterale complementare nelle due clausole
        //ed infine nel caso in cui la risolvente fosse una taut. deve dire che viene scartata.
        StringBuilder res = new StringBuilder();

        res.append("STEP NUMBER " + stepNumber + ":\n");
        res.append("First premise: " + premise1.toString() + "\n");
        res.append("Second premise: " + premise2.toString() + "\n");
        res.append("Conclusion: " + conclusion.toString() + "\n");
        res.append("obtained by removing the literal \"" + complementary + "\" and its opposite.\n");

        if (isTautology) {
            res.append("The conclusion is DISCARDED because it is a tautology.\n");
        }

        res.append("__________________________________________________________\n");


        return res.toString();
    }
}
