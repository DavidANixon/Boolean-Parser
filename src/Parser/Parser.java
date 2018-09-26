package Parser;

import java.util.*;

public final class Parser{

    private static final HashSet<Type> potentialReduction = new HashSet<Type>(List.of(Type.VARIABLE,
            Type.EXPRESSION, Type.CLOSE, Type.TERM));

    private static final HashSet<Reduction> reductions = new HashSet<Reduction>();

    private static final void populateReductionsSet() {
        for (Pattern pattern: Pattern.values()) {
            reductions.add(pattern.getTypeList(), ;
        }
    }

    public static final State parse(BooleanList input) {
        List<Symbol> workingList = new ArrayList<Symbol>();
        for (Symbol symbol : input) {
            workingList.add(symbol);

        }

        //build state
    }
}
