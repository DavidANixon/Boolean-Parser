package Parser;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.security.PublicKey;

import static org.junit.Assert.assertEquals;

public class JUnitTest {

    private BooleanList booleanList = new BooleanList();
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
        String expected = "A";
        booleanList.add("A");
        State result = Parser.parse(booleanList);

        assertEquals(expected, result.getExpression().get(0).toString());
    }
}
