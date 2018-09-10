package Parser;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class JUnitTest {
    @Test
    public void variableTest() {
        Variable varA = Variable.build("OR");
        System.out.println(varA.toString());
        String varADesiredToString = "a";
        assertEquals(varA.toList().toString(), varADesiredToString);

    }
}
