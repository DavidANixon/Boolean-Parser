package Parser;

import java.util.List;

public final class State {
    private final boolean correct;
    private final List<Symbol> workingList;

    private State (boolean correct, List<Symbol> workingList) {
        this.correct = correct;
        this.workingList = workingList;
    }

    static final State build(List<Symbol> workingList) {
        boolean correct = false;

        if (workingList.size() == 1 && workingList.get(0).getType().equals(Type.EXPRESSION)) {
            correct = true;
        }
        return new State(correct, workingList);
    }

    public final boolean isCorrect() {
        return correct;
    }

    public final List<Symbol> getWorkingList() {
        return List.copyOf(workingList);
    }

    /**
     * checks if the working list is in the correct format and returns it if it does
     * @return a copy of the working list
     */
    public final List<Symbol> getExpression() {
        if (isCorrect())
            return getWorkingList();
        else
            throw new UnsupportedOperationException("The working list does not contain a single expression " +
                    "and is therefore incorrect.");
    }
}
