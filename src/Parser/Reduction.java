package Parser;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

final class Reduction {
    public final List<Type> pattern;
    private final Function<List<Symbol>, TreeSymbol> reductionFunction;

    private Reduction(List<Type> pattern, Function<List<Symbol>, TreeSymbol> reductionFunction) {
        this.pattern = pattern;
        this.reductionFunction = reductionFunction;
    }

    public static final Reduction build(List<Type> pattern, Function<List<Symbol>, TreeSymbol> reduction) {
        Objects.requireNonNull(pattern, "pattern cannot be null");
        Objects.requireNonNull(reduction, "reductionFunction cannot be null");

        return new Reduction(pattern, reduction);
    }

    public final int size() {
        return pattern.size();
    }

    /**
     * Use to determine if this reduction patter matches a given pattern
     * @param typeList the list to be compared against the pattern
     * @return a boolean representing whether the two match
     */
    public final boolean matches(List<Type> typeList) {
        return typeList.equals(pattern);
    }

    /**
     * Use to apply this reduction's function
     * @param symbolList the list to be reduced
     * @return a single Symbol representing the reduced list
     */
    public final Symbol apply(List<Symbol> symbolList) {
        return reductionFunction.apply(symbolList);
    }
}
