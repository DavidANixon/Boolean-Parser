package Parser;

import java.util.Optional;

public final class Expression extends AbstractTreeSymbol {

    private final Type type;
    private final Symbol leftSubexpression, rightSubexpression;

    final Symbol getLeftSubexpression() {
        return leftSubexpression;
    }
    final Symbol getRightSubexpression() {
        return rightSubexpression;
    }

    @Override
    public Type getType() {
        return type;
    }

    /**
     * Constructor for Expressions, call build to access this method
     * @param structure The key type of this Expression that shapes its structure
     * @param leftSubexpression
     * @param rightSubexpression
     */
    private Expression(Type structure, Symbol leftSubexpression, Symbol rightSubexpression) {
        super(structure);
        this.leftSubexpression = leftSubexpression;
        this.rightSubexpression = rightSubexpression;
        type = Type.EXPRESSION;
    }

    /**
     * This variant builds singular terms and negated terms
     * @param isPositive false if the term is being negated
     * @param subexpression a term to be built
     * @return a new Expression
     */
    public static final Expression build(boolean isPositive, Symbol subexpression) {
        validateSubexpression(subexpression, Type.TERM,
                "An expression of either a single term or a negation and a term");
        if (isPositive)
            return new Expression(Type.TERM, subexpression, null);
        else
            return new Expression(Type.NOT, subexpression, null);
    }

    /**
     * This variant combines two subexpressions over an AND or an OR
     * @param isConjunction true for AND, false for OR
     * @param leftSubexpression
     * @param rightSubexpression
     * @return a new Expression joining the two subExpressions with the appropriate connector
     */
    public static final Expression build(boolean isConjunction, Symbol leftSubexpression, Symbol rightSubexpression) {
        validateSubexpression(leftSubexpression, Type.EXPRESSION,
                "The left subexpression");
        validateSubexpression(rightSubexpression, Type.EXPRESSION,
                "The right subexpression");
        if (isConjunction)
            return new Expression(Type.AND, leftSubexpression, rightSubexpression);
        else
            return new Expression(Type.OR, leftSubexpression, rightSubexpression);
    }

    /**
     * Converts this Expression to a boolean list
     * @return a boolean list consisting of the symbols of this expression
     */
    @Override
    public BooleanList toList() {
        BooleanList booleanList = new BooleanList();
        if (getStructure().equals(Type.TERM)) {
            booleanList.add(leftSubexpression.toList());
            return booleanList;
        }
        else if (getStructure().equals(Type.NOT)) {
            booleanList.add(Type.NOT);
            booleanList.add(leftSubexpression.toList());
            return booleanList;
        }
        else if (combinesLeftAndRightSubexpressions()){
            booleanList.add(leftSubexpression.toList());
            booleanList.add(getStructure());
            booleanList.add(rightSubexpression.toList());
            return booleanList;
        }
        else
            throw new UnsupportedOperationException("Cannot convert the given expression to BooleanList.");
    }

    /**
     * Computes the complexity of this expression
     * @return a long representation of the complexity
     */
    public final long complexity() {
        long complex = 0;

        if (rightSubexpression != null) {
            complex += rightSubexpression.complexity();

            if (getStructure().doesAddComplexity())
                complex++;
        }

        return complex + leftSubexpression.complexity();
    }

    @Override
    public String toString() {
        return this.toList().toString();
    }


    /**
     * Returns a simplified version of the current object, or just the object if it cannot be further simplified
     * @return s simplified object
     */
    @Override
    public Symbol simplified() {
        if (leftSubexpression.subterm().isPresent()) {
            return Expression.build(true, Term.build(leftSubexpression.subterm().get()));
        }
        else
            return this;
    }

    /**
     * recursively finds the subterm of the current expression
     * @return the subterm of this expression
     */
    @Override
    public Optional<Symbol> subterm() {

        if (getStructure() == Type.TERM) {
            return leftSubexpression.subterm();
        }
        else
            return Optional.of(this);
    }

    private boolean combinesLeftAndRightSubexpressions() {
        return (getStructure().equals(Type.AND) || getStructure().equals(Type.OR));
    }

}
