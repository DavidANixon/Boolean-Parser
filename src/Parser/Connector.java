package Parser;

public final class Connector extends AbstractListSymbol implements ListSymbol  {

    private final Type type;

    public final Type getType() {
        return type;
    }

    private Connector(Type connectorType) {
        type = connectorType;
    }

    public static final Connector build(Type type) {
        if (type == null) {
            throw new NullPointerException("Connector cannot be null");
        }
        if (isValidConnectorType(type)) {
            return new Connector(type);
        }
        else
            throw new IllegalArgumentException("Connector cannot take type " + type.toString() +
                    ". Connector only take types OR, AND, NOT, OPEN, CLOSE");
    }

    public String toString() {
        switch (type) {
            case OR: return "\u2228";
            case AND: return "\u005E";
            case NOT: return "\u00AC";
            case OPEN: return "OPEN";
            case CLOSE: return "CLOSE";
            default: return "Unknown connector type";
        }
    }

    private static boolean isValidConnectorType(Type inputType) {
        if (inputType.equals(Type.OR) ||
                inputType.equals(Type.AND) ||
                inputType.equals(Type.NOT) ||
                inputType.equals(Type.OPEN) ||
                inputType.equals(Type.CLOSE))
            return true;
        else
            return false;
    }


}
