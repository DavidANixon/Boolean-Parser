package Parser;


import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public enum Pattern {
    VARIABLE_PATTERN (Arrays.asList(Type.VARIABLE)){
        @Override
        public Function<List<Symbol>, TreeSymbol> getReductionFunction () {

            Function<List<Symbol>, TreeSymbol> reductionFunction = (variableInList) -> {
                return Term.build(variableInList.get(0));
            };

            return reductionFunction;
        }
    },

    EXPRESSION_PARENTHESES_PATTERN (Arrays.asList(Type.OPEN, Type.EXPRESSION, Type.CLOSE)){
        @Override
        public Function<List<Symbol>, TreeSymbol> getReductionFunction () {

            Function<List<Symbol>, TreeSymbol> reductionFunction = (ExpressionInParentheses) -> {
                return Term.build(ExpressionInParentheses.get(0));
            };

            return reductionFunction;
        }
    },

    NOT_TERM_PATTERN (Arrays.asList(Type.NOT, Type.TERM)){
        @Override
        public Function<List<Symbol>, TreeSymbol> getReductionFunction () {

            Function<List<Symbol>, TreeSymbol> reductionFunction = (singleTerm) -> {
                return Expression.build(false, singleTerm.get(0));
            };

            return reductionFunction;
        }
    },

    TERM_PATTERN (Arrays.asList(Type.TERM)) {
        @Override
        public Function<List<Symbol>, TreeSymbol> getReductionFunction () {

            Function<List<Symbol>, TreeSymbol> reductionFunction = (singleTerm) -> {
                return Expression.build(true, singleTerm.get(0));
            };

            return reductionFunction;
        }
    },

    EXPRESSION_AND_PATTERN (Arrays.asList(Type.EXPRESSION, Type.OPEN, Type.EXPRESSION)) {
        @Override
        public Function<List<Symbol>, TreeSymbol> getReductionFunction () {

            Function<List<Symbol>, TreeSymbol> reductionFunction = (leftAndRightExpression) -> {
                return Expression.build(false, leftAndRightExpression.get(0), leftAndRightExpression.get(1));
            };

            return reductionFunction;
        }
    },

    EXPRESSION_OR_PATTERN (Arrays.asList(Type.EXPRESSION, Type.OPEN, Type.EXPRESSION)) {
        @Override
        public Function<List<Symbol>, TreeSymbol> getReductionFunction () {

            Function<List<Symbol>, TreeSymbol> reductionFunction = (leftAndRightExpression) -> {
                return Expression.build(true, leftAndRightExpression.get(0), leftAndRightExpression.get(1));
            };

            return reductionFunction;
        }
    };

    private Function<List<Symbol>, TreeSymbol> reductionFunction;
    private List<Type> typeList;

    public List<Type> getTypeList() {
       return typeList;
    }

    public Function getReductionFunction () {
        throw new UnsupportedOperationException("Cannot call getReductionFunction without specifying pattern");
    }

    Pattern (List<Type> typeList) {
        this.typeList = typeList;
    }
}
