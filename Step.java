

public class Step {

    //STATIC FIELDS
    private static int count = 1;

    //FIELDS
    private int stepNumber;
    private Clause premise1;
    private Clause premise2;
    private Clause conclusion;
    private Literal complementary;
    private boolean isTautology; //is true if the conclusion is a tautology
    private boolean isAlreadyPresent; //is true if the conclusion is already present in the set. 

    //CONSTRUCTORS
    public Step(Clause premise1, Clause premise2, Clause conclusion, Literal complementary) {
        this.premise1 = premise1;
        this.premise2 = premise2;
        this.conclusion = conclusion;
        this.complementary = complementary;

        this.stepNumber = count;
        this.isTautology = false;
        this.isAlreadyPresent = false;
        count++;
    }

    //METHODS

    public void setTautology() {
        this.isTautology = true;
    }

    public void setAlreadyPresent() {
        this.isAlreadyPresent = true;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();

        res.append("STEP NUMBER " + stepNumber + ":\n");
        res.append("First premise: " + premise1.toString() + "\n");
        res.append("Second premise: " + premise2.toString() + "\n");
        res.append("Conclusion: " + conclusion.toString() + "\n");
        res.append("obtained by removing the literal \"" + complementary + "\" and its opposite.\n");

        if (isTautology) {
            res.append("The conclusion is DISCARDED because it is a tautology.\n");
        }

        if (isAlreadyPresent) {
            res.append("The conclusion is DISCARDED because it is already present in the set.\n");
        }

        res.append("__________________________________________________________\n");

        return res.toString();
    }
}
