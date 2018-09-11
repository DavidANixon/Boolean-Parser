package Parser;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class JUnitTest {
    @Test
    public void variableToListAndToStringTest() {
        Variable varA = Variable.build("a");
        System.out.println(varA.toString());
        String varADesiredToString = "a";
        assertEquals(varADesiredToString, varA.toList().toString());
    }

    @Test
    public void connectorToListAndToStringTest() {
        Connector connectorA = Connector.build(Type.AND);
        System.out.println(connectorA.toString());
        String varADesiredToString = "^";
        assertEquals(varADesiredToString, connectorA.toList().toString());
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

        System.out.println(booleanList.toString());
        assertEquals(desiredListString, booleanList.toString());
    }

    @Test
    public void booleanListConvenienceTest() {
        BooleanList booleanList = new BooleanList();
        String desiredListString = "a \u00AC b";

        booleanList.add("a");
        booleanList.add(Type.NOT);
        booleanList.add("b");

        assertEquals(desiredListString, booleanList.toString());
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void freezeTest() throws Exception {
        expectedException.expect(UnsupportedOperationException.class);
        expectedException.expectMessage("This list has been frozen and can no longer be modified.");
        BooleanList booleanList = new BooleanList();
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
}
