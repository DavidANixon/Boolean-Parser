package Parser;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

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

    /**
     * Calls the Term constructor after validating the input type
     * @param subexpression the expression to wrap the term around
     * @return a new Term containing the subexpression
     */
    public static final Term build (Symbol subexpression) {
        validateSubexpression(subexpression,
                EXPECTED_TERM_TYPES,
                "This is being called from the Term build class");
        return new Term(subexpression);
    }

    /**
     * Converts this Term to a Boolean List
     * @return a new Boolean List containing this Term
     */
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

    /**
     * returns a new Term that has been simplified
     * @return a new, simplified term
     */
    public final Symbol simplified() {
        if (this.subterm().isPresent())
            return Term.build(this.subterm().get());

        else
            return Term.build(term);
    }

    /**
     * recursively finds the subterm of this term
     * @return the subterm of this term
     */
    @Override
    public final Optional<Symbol> subterm() {
        return term.subterm();
    }

}
