package Parser;

import java.util.*;

public final class Parser{

    private static final HashSet<Type> potentialReduction = new HashSet<Type>(List.of(Type.VARIABLE,
            Type.EXPRESSION, Type.CLOSE, Type.TERM));

    private static final HashSet<Reduction> reductions = new HashSet<Reduction>();


    public static final State parse(BooleanList input) {
        List<Symbol> workingList = new ArrayList<Symbol>();
        for (Symbol symbol : input) {
            workingList.add(symbol);

            if (potentialReduction.contains(symbol.getType())) {

            }
        }
    }
}
