package Parser;
import java.util.*;

abstract class AbstractTreeSymbol implements TreeSymbol {
    private final Type structure;
    public final Type getStructure() {
        return structure;
    }

    protected AbstractTreeSymbol(Type structure) {
        this.structure = structure;
    }

    protected static final void validateSubexpression (Symbol subexpression,
                                                       Set<Type> expectedTypes,
                                                       String subexpressionDescription) {
        Objects.requireNonNull(subexpression, subexpression.toString() + " (" + subexpressionDescription +
                ") cannot be null");
        if (!expectedTypes.contains(subexpression.getType()))
            throw new IllegalArgumentException(subexpression.toString() + " ("+ subexpressionDescription +
                    ") does not match any of the expected types");
    }

    protected static final void validateSubexpression (Symbol subexpression,
                                                       Type expectedType,
                                                       String subexpressionDescription) {
        validateSubexpression(subexpression, Collections.singleton(expectedType), subexpressionDescription);
    }
}
