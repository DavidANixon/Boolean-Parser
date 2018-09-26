package Parser;

import java.util.*;

public final class Parser{

    public static final State parse(BooleanList input) {
        List<Symbol> workingList = new ArrayList<Symbol>();
        for (Symbol symbol : input) {
            workingList.add(symbol);

            for (Pattern pattern : Pattern.values()) {
                Reduction.build(pattern, pattern.reduceFunction(pattern.getTypeList()));
            }
        }

        //build state
    }

    public final TreeSymbol reductionFunction(List<Symbol> symbolList) {

    }

}
