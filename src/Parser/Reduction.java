package Parser;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

final class Reduction {
    private final List<Type> pattern;

    private final Function<List<Symbol>, TreeSymbol> reduction;


    private Reduction(List<Type> pattern, Function<List<Symbol>, TreeSymbol> reduction) {
        this.pattern = pattern;
        this.reduction = reduction;
    }

    public static final Reduction build(List<Type> pattern, Function<List<Symbol>, TreeSymbol> reduction) {
        Objects.requireNonNull(pattern, "pattern cannot be null");
        Objects.requireNonNull(reduction, "reduction cannot be null");

        return new Reduction(pattern, reduction);
    }

    public final int size() {
        return pattern.size();
    }

    public final boolean matches(List<Type> typeList) {
        Iterator iterator1 = typeList.iterator();
        Iterator iterator2 = pattern.iterator();

        if(typeList.size() != pattern.size()) {
            return false;
        }

        while (iterator1.hasNext()) {
            if (!iterator1.next().equals(iterator2.next()))
                return false;
        }
        return true;
    }

    public final Symbol apply(List<Symbol> symbolList) {
        return reduction.apply(symbolList,);
    }
}
