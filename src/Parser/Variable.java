package Parser;

import java.util.Objects;
import static Parser.Type.VARIABLE;

public final class Variable extends AbstractListSymbol implements TreeSymbol {

    private final String representation;

    public final String getRepresentation() {
        return representation;
    }

    public final Type getType() {
        return VARIABLE;
    }

    private Variable (String variableRepresentation) {
        representation = variableRepresentation;
    }

    /**
     * Use this method to build a new Variable Symbol.
     * @param variableRepresentation The String representation of the variable
     * @return A new Variable object with the given  String representation
     */
    public static final Variable build (String variableRepresentation) {
        return new Variable(Objects.requireNonNull(variableRepresentation, "Variable cannot be null."));
    }

    public final long complexity() {
        return 0;
    }

    public Type getStructure() {
        return VARIABLE;
    }

    @Override
    public String toString() {
        return getRepresentation();
    }
}