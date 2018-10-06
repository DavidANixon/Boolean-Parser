package Parser;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.security.PublicKey;
import java.text.StringCharacterIterator;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class JUnitTest {

    @Test
    public void variableToListAndToStringTest() {
        Variable varA = Variable.build("a");
        String varADesiredToString = "a";
        assertEquals("Testing Variable.build('a')", varADesiredToString, varA.toList().toString());
    }

    @Test
    public void connectorToListAndToStringTest() {
        Connector connectorA = Connector.build(Type.AND);
        String varADesiredToString = "^";
        assertEquals("Testing Connector.build(Type.AND)", varADesiredToString, connectorA.toList().toString());
    }

    @Test
    public void booleanListToStringTest() {
        BooleanList booleanList = new BooleanList();
        String desiredListString = "a ^ b";

        Variable varA = Variable.build("a");
        booleanList.add(varA);

        Connector connectorAND = Connector.build(Type.AND);
        booleanList.add(connectorAND);

        Variable varB = Variable.build("b");
        booleanList.add(varB);

        assertEquals("Testing Boolean toString of 'a ^ b'", desiredListString, booleanList.toString());
    }

    @Test
    public void booleanListConvenienceTest() {
        BooleanList booleanList = new BooleanList();

        String desiredListString = "a \u00AC b";

        booleanList.add("a");
        booleanList.add(Type.NOT);
        booleanList.add("b");

        assertEquals("Testing convenience methods on add('a'), add(Type.NOT), and add('b')", desiredListString, booleanList.toString());
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void freezeTest() throws Exception {
        BooleanList booleanList = new BooleanList();

        expectedException.expect(UnsupportedOperationException.class);
        expectedException.expectMessage("This list has been frozen and can no longer be modified.");
        Variable varA = Variable.build("a");
        booleanList.add(varA);

        booleanList.freeze();

        //Adding this Connector should throw the exception because the BooleanList was just frozen
        Connector connectorAND = Connector.build(Type.AND);
        booleanList.add(connectorAND);
    }

    @Test
    public void connectorInvalidTypeTest() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Connector cannot take type " + Type.VARIABLE +
                ". Connector only take types OR, AND, NOT, OPEN, and CLOSE");

        //Connector cannot take type Variable so this should throw an exception
        Connector.build(Type.VARIABLE);
    }

    @Test
    public void connectorNullTypeTest() throws Exception {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("Connector cannot be null.");

        Connector.build(null);
    }

    @Test
    public void booleanListComplexityTest() {
        BooleanList booleanList = new BooleanList();

        long desiredLong = 2;

        Variable varA = Variable.build("a");
        booleanList.add(varA);

        Connector connectorAND = Connector.build(Type.AND);
        booleanList.add(connectorAND);

        Variable varB = Variable.build("b");
        booleanList.add(varB);

        Connector connectorOR = Connector.build(Type.OR);
        booleanList.add(connectorOR);

        assertEquals(desiredLong, booleanList.complexity());
    }

    @Test
    public void termToString() {
        String desiredString = "varA";

        Term term = Term.build(Variable.build("varA"));

        assertEquals(desiredString, term.toString());
    }

    @Test
    public void expressionSingleTermToString() {
        String desiredString = "A";

        Expression expression = Expression.build(true, Term.build(Variable.build("A")));
        assertEquals(desiredString, expression.toString());
    }

    @Test
    public void expressionSingleNotToString() {
        String desiredString = "\u00AC A";

        Expression expression = Expression.build(false, Term.build(Variable.build("A")));
        assertEquals(desiredString, expression.toString());
    }

    @Test
    public void expressionJoinTermsToString() {
        String desiredString = "A ^ B";
        Expression expression1 = Expression.build(true, Term.build(Variable.build("A")));
        Expression expression2 = Expression.build(true, Term.build(Variable.build("B")));

        Expression expression = Expression.build(true, expression1, expression2);
        assertEquals(desiredString, expression.toString());
    }

    @Test
    public void testVariableParse() {
        BooleanList booleanList = new BooleanList();

        String expected = "A";
        booleanList.add("A");
        State result = Parser.parse(booleanList);

        assertEquals(expected, result.getExpression().get(0).toString());
    }

    @Test
    public void testExpressionOrParse() {
        BooleanList booleanList = new BooleanList();

        String expected = "A \u2228 B";
        booleanList = buildExpressionToList(false, "A", "B", booleanList);

        State result = Parser.parse(booleanList);
        assertEquals(expected, result.getExpression().get(0).toString());
    }

    @Test
    public void testExpressionParse() {
        BooleanList booleanList = new BooleanList();

        String expected = "A \u005E B";

        booleanList = buildExpressionToList(true, "A", "B", booleanList);
        State result = Parser.parse(booleanList);

        assertEquals(expected, result.getExpression().get(0).toString());
    }

    @Test
    public void testNotTermParse() {
        BooleanList booleanList;

        String expected = "\u00AC A";
        Expression expression = Expression.build(false, Term.build(Variable.build("A")));

        booleanList = expression.toList();
        State result = Parser.parse(booleanList);

        assertEquals(expected, result.getExpression().get(0).toString());
    }

    @Test
    public void testParenthesisExpression() {
        BooleanList booleanList;
        String expected = "\u0028 A \u005E B \u0029";

        Expression expression1 = Expression.build(true, Term.build(Variable.build("A")));
        Expression expression2 = Expression.build(true, Term.build(Variable.build("B")));
        Expression expression = Expression.build(true, expression1, expression2);
        Term term = Term.build(expression);

        booleanList = term.toList();
        State result = Parser.parse(booleanList);

        assertEquals(expected, result.getExpression().get(0).toString());
    }

    @Test
    public void testTermSimplify() {
        BooleanList booleanList = new BooleanList();

        String expected = "A \u005E B";

        booleanList.add(Type.OPEN);
        booleanList.add(Type.OPEN);
        booleanList.add(Type.OPEN);
        booleanList.add(Type.OPEN);
        booleanList.add("A");
        booleanList.add(Type.AND);
        booleanList.add("B");
        booleanList.add(Type.CLOSE);
        booleanList.add(Type.CLOSE);
        booleanList.add(Type.CLOSE);
        booleanList.add(Type.CLOSE);

       Term term = Term.build(Parser.parse(booleanList).getExpression().get(0));

       assertEquals(expected, term.simplified().toString());
    }

    @Test
    public void testExpressionSimplify() {
        BooleanList booleanList = new BooleanList();

        String expected = "\u0028 A \u005E B \u0029";

        booleanList.add(Type.OPEN);
        booleanList.add(Type.OPEN);
        booleanList.add(Type.OPEN);
        booleanList.add(Type.OPEN);
        booleanList.add("A");
        booleanList.add(Type.AND);
        booleanList.add("B");
        booleanList.add(Type.CLOSE);
        booleanList.add(Type.CLOSE);
        booleanList.add(Type.CLOSE);
        booleanList.add(Type.CLOSE);

        Expression expression = Expression.build(true, Term.build(Parser.parse(booleanList).getExpression().get(0)));

        assertEquals(expected, expression.simplified().toString());
    }

    private BooleanList buildExpressionToList(boolean isConjunction,String A, String B, BooleanList bl) {
        Expression expression1 = Expression.build(true, Term.build(Variable.build("A")));
        Expression expression2 = Expression.build(true, Term.build(Variable.build("B")));

        Expression expression = Expression.build(isConjunction, expression1, expression2);
        bl.add(expression.toList());
        return bl;
    }

}
