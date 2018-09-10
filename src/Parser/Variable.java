package Parser;

public final class Variable extends AbstractListSymbol implements ListSymbol {

    private final String representation;

    public final String getRepresentation() {
        return representation;
    }

    public Type getType() {
        return Type.VARIABLE;
    }

    private Variable (String variableRepresentation) {
        representation = variableRepresentation;
    }

    public static final Variable build (String variableRepresentation) {
        if (variableRepresentation != null)
            return new Variable(variableRepresentation);
        else
            throw new NullPointerException("Variable cannot be null.");
    }

    @Override
    public String toString() {
        return representation;
    }
}