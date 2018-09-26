package Parser;

public enum Type {
    VARIABLE (false, false),
    TERM (false, false),
    EXPRESSION (false, false),
    OR (true, false) {
        @Override
        public String toString() {
            return "\u2228";
        }
    },
    AND (true, false) {
        @Override
        public String toString() {
            return "\u005E";
        }
    },
    NOT (true, false)  {
        @Override
        public String toString() {
            return "\u00AC";
        }
    },
    OPEN (true, false) {
        @Override
        public String toString() {
            return "\u0028";
        }
    },
    CLOSE (true, false){
        @Override
        public String toString() {
            return "\u0029";
        }
    };

    Type(boolean isValidConnectorType, boolean doesAddComplexity) {
        this.isValidConnectorType = isValidConnectorType;
        this.doesAddComplexity = doesAddComplexity;
    }

    private boolean doesAddComplexity;

    private boolean isValidConnectorType;

    public boolean doesAddComplexity() {
        return doesAddComplexity;
    }

    public boolean isValidConnectorType() {
        return isValidConnectorType;
    }
}
