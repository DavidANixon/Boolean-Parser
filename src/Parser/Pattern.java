package Parser;

import java.util.Arrays;
import java.util.List;

public enum Pattern {
    VARIABLE_PATTERN {
        public List<Type> getTypeList() {
            return Arrays.asList(Type.VARIABLE);
        }
    },
    EXPRESSION_PERENTHESIS_PATTERN {
        public List<Type> getTypeList() {
            return Arrays.asList(Type.OPEN, Type.EXPRESSION, Type.CLOSE);
        }
    },
    NOT_TERM_PATTERN {
        public List<Type> getTypeList() {
            return Arrays.asList(Type.NOT, Type.TERM);
        }
    },
    TERM_PATTERN {
        public List<Type> getTypeList() {
            return Arrays.asList(Type.TERM);
        }
    },
    EXPRESSION_AND_PATTERN {
        public List<Type> getTypeList() {
            return Arrays.asList(Type.EXPRESSION, Type.AND, Type.EXPRESSION);
        }
    },
    EXPRESSION_OR_PATTERN {
        public List<Type> getTypeList() {
            return Arrays.asList(Type.EXPRESSION, Type.OPEN, Type.EXPRESSION);
        }
    }
}
