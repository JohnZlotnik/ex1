import TextCalculator.InvalidExpressionFormatException;
import TextCalculator.TextCalculator;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by Dell on 30/10/2018.
 */
public class TextCalculatorTest {



    @Test
    public void sanity() throws InvalidExpressionFormatException {
        ArrayList<String> expressions = new ArrayList<>();
        expressions.add("i = 0");
        expressions.add("j = ++i");
        expressions.add("x = i++ + 5");
        expressions.add("y = 5 + 3 * 10");
        expressions.add("i += y");

        String expectedResult = "(x=6,i=37,y=35,j=1)";
        String actualResult = TextCalculator.calculate(expressions);

        Assert.assertEquals(expectedResult, actualResult);
    }
}
