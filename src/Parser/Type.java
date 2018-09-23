package Parser;

public enum Type {
    VARIABLE,
    TERM,
    EXPRESSION,
    OR {
        @Override
        public String toString() {
            return "\u2228";
        }

        @Override
        public boolean isValidConnectorType() {
            return true;
        }

        @Override
        public boolean doesAddComplexity() {
            return true;
        }
    },
    AND {
        @Override
        public String toString() {
            return "\u005E";
        }
        @Override
        public boolean isValidConnectorType() {
            return true;
        }
        @Override
        public boolean doesAddComplexity() {
            return true;
        }
    },
    NOT {
        @Override
        public String toString() {
            return "\u00AC";
        }

        @Override
        public boolean isValidConnectorType() {
            return true;
        }
    },
    OPEN {
        @Override
        public String toString() {
            return "\u0028";
        }
        public boolean isValidConnectorType() {
            return true;
        }
    },
    CLOSE {
        @Override
        public String toString() {
            return "\u0029";
        }

        @Override
        public boolean isValidConnectorType() {
            return true;
        }
    };

    public boolean isValidConnectorType() {
        return false;
    }

    public boolean doesAddComplexity() {
        return false;
    }
}
