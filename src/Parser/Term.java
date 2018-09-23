package Parser;

import java.util.HashSet;
import java.util.List;

public final class Term extends AbstractTreeSymbol{

    private static final HashSet<Type> EXPECTED_TERM_TYPES = new HashSet<>(List.of(Type.VARIABLE, Type.EXPRESSION));
    private final Symbol term;
    private final Type type;

    private Term (Symbol subexpression) {
        super(subexpression.getType());
        type = Type.TERM;
        term = subexpression;
    }

    final Symbol getTerm() {
        return term;
    }

    @Override
    public Type getType() {
        return type;
    }

    public static final Term build (Symbol subexpression) {
        validateSubexpression(subexpression,
                EXPECTED_TERM_TYPES,
                "This is being called from the Term build class");
        return new Term(subexpression);
    }

    public final BooleanList toList() {
        if (getTerm().getType().equals(Type.VARIABLE)) {
            return term.toList();
        }
        else if (getTerm().getType().equals(Type.EXPRESSION)){
            BooleanList booleanList = new BooleanList();
            booleanList.add(Type.OPEN);
            booleanList.add(term.toList());
            booleanList.add((Type.CLOSE));
            return booleanList;
        }
        else
            throw new UnsupportedOperationException("Cannot convert term to list.");
    }

    @Override
    public String toString() {
        return getTerm().toString();
    }

    public final long complexity() {
        return getTerm().complexity();
    }
}
