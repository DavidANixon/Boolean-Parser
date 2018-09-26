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
        public TreeSymbol reduceFunction(Variable variable) {
            return Term.build(variable);
        }
    },
    EXPRESSION_PERENTHESIS_PATTERN {
        @Override
        public List<Type> getTypeList() {
            return Arrays.asList(Type.OPEN, Type.EXPRESSION, Type.CLOSE);
        }
        @Override
        public TreeSymbol reduceFunction (Expression expression) {
            return Term.build(expression);
        }
    },
    NOT_TERM_PATTERN {
        @Override
        public List<Type> getTypeList() {
            return Arrays.asList(Type.NOT, Type.TERM);
        }
        public TreeSymbol reduceFunction (Term term) {
            return Expression.build(false, term);
        }

    },
    TERM_PATTERN {
        @Override
        public List<Type> getTypeList() {
            return Arrays.asList(Type.TERM);
        }
        @Override
        public TreeSymbol reduceFunction (Term term) {
            return Expression.build(true, term);
        }
    },
    EXPRESSION_AND_PATTERN {
        @Override
        public List<Type> getTypeList() {
            return Arrays.asList(Type.EXPRESSION, Type.AND, Type.EXPRESSION);
        }
        @Override
        public TreeSymbol reduceFunction (Expression expression1, Expression expression2) {
            return Expression.build(false, expression1, expression2);
        }
    },
    EXPRESSION_OR_PATTERN {
        @Override
        public List<Type> getTypeList() {
            return Arrays.asList(Type.EXPRESSION, Type.OPEN, Type.EXPRESSION);
        }
        @Override
        public TreeSymbol reduceFunction (Expression expression1, Expression expression2) {
            return Expression.build(true, expression1, expression2);
        }
    };

    public List<Type> getTypeList() {
        throw new UnsupportedOperationException("Cannot call type list without pattern specified.");
    }
    public TreeSymbol reduceFunction (Variable variable) {
        throw new UnsupportedOperationException("Cannot call reduce function without pattern specified.");
    }
    public TreeSymbol reduceFunction (Expression expression) {
        throw new UnsupportedOperationException("Cannot call reduce function without pattern specified.");
    }
    public TreeSymbol reduceFunction (Term term) {
        throw new UnsupportedOperationException("Cannot call reduce function without pattern specified.");
    }
    public TreeSymbol reduceFunction (Expression expression1, Expression expression2) {
        throw new UnsupportedOperationException("Cannot call reduce function without pattern specified.");
    }

}
