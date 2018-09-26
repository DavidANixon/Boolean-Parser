package Parser;


import java.util.Arrays;
import java.util.List;

public enum Pattern {
    VARIABLE_PATTERN {
        @Override
        public List<Type> getTypeList() {
            return Arrays.asList(Type.VARIABLE);
        }
        @Override
        public TreeSymbol reduceFunction(List<Symbol> singleTerm) {
            return Term.build(singleTerm.get(0));
        }
    },
    EXPRESSION_PERENTHESIS_PATTERN {
        @Override
        public List<Type> getTypeList() {
            return Arrays.asList(Type.OPEN, Type.EXPRESSION, Type.CLOSE);
        }
        @Override
        public TreeSymbol reduceFunction (List<Symbol> singleExpression) {
            return Term.build(singleExpression.get(0));
        }
    },
    NOT_TERM_PATTERN {
        @Override
        public List<Type> getTypeList() {
            return Arrays.asList(Type.NOT, Type.TERM);
        }
        public TreeSymbol reduceFunction (List<Symbol> singleTerm) {
            return Expression.build(false, singleTerm.get(0));
        }
    },
    TERM_PATTERN {
        @Override
        public List<Type> getTypeList() {
            return Arrays.asList(Type.TERM);
        }
        @Override
        public TreeSymbol reduceFunction (List<Symbol> singleTerm) {
            return Expression.build(true, singleTerm.get(0));
        }
    },
    EXPRESSION_AND_PATTERN {
        @Override
        public List<Type> getTypeList() {
            return Arrays.asList(Type.EXPRESSION, Type.AND, Type.EXPRESSION);
        }
        @Override
        public TreeSymbol reduceFunction (List<Symbol> leftAndRightExpression) {
            return Expression.build(false, leftAndRightExpression.get(0), leftAndRightExpression.get(1));
        }
    },
    EXPRESSION_OR_PATTERN {
        @Override
        public List<Type> getTypeList() {
            return Arrays.asList(Type.EXPRESSION, Type.OPEN, Type.EXPRESSION);
        }
        @Override
        public TreeSymbol reduceFunction (List<Symbol> leftAndRightExpression) {
            return Expression.build(true, leftAndRightExpression.get(0), leftAndRightExpression.get(1));
        }
    };

    public List<Type> getTypeList() {
        throw new UnsupportedOperationException("Cannot call type list without pattern specified.");
    }
    public TreeSymbol reduceFunction (List<Symbol> symbolList) {
        throw new UnsupportedOperationException("Cannot call reduce function without pattern specified.");
    }

}
