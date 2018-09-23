package Parser;

import java.util.Objects;

public final class Connector extends AbstractListSymbol implements ListSymbol  {

    private final Type type;
    public final Type getType() {
        return type;
    }

    private Connector(Type connectorType) {
        type = connectorType;
    }

    /**
     * Use this method to construct a new Connector Symbol.
     * @param type The type of Constructor to be built. A constructor ca/n only be type OR, AND, NOT, OPEN, or CLOSE.
     * @return A new Connector object of the given type, if the type is valid.
     */
    public static final Connector build(Type type) {
        Objects.requireNonNull(type, "Connector cannot be null.");

        if (type.isValidConnectorType()) {
            return new Connector(type);
        }
        else
            throw new IllegalArgumentException("Connector cannot take type " + type.toString() +
                    ". Connector only take types OR, AND, NOT, OPEN, and CLOSE");
    }

    public final long complexity() {
        if (type.doesAddComplexity()) {
            return 1;
        }
        else
            return 0;
    }

    /**
     * Use this program to return the string representation of the Connector.
     * @return The Unicode String representation of the Connector
     */
    @Override
    public String toString() {
       return type.toString();
    }
}
