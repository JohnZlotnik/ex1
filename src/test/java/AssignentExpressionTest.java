import TextCalculator.AssignmentExpression;
import TextCalculator.InvalidExpressionFormatException;
import TextCalculator.VariablesData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Created by Dell on 30/10/2018.
 *
 */
public class AssignentExpressionTest {

    @Before
    public void prep(){
        VariablesData.getInstance().addVar("x", 1);
        VariablesData.getInstance().addVar("y", 2);
        VariablesData.getInstance().addVar("z", 3);
    }

    @Test
    public void testInc() throws InvalidExpressionFormatException {
        AssignmentExpression assignmentExpression = new AssignmentExpression("x = z++ - ++y");
        test(assignmentExpression, 0,3,4);
    }

    @Test
    public void testDec() throws InvalidExpressionFormatException {
        AssignmentExpression assignmentExpression = new AssignmentExpression("x = z-- + --y");
        test(assignmentExpression, 4,1,2);
    }

    @Test
    public void testMult() throws InvalidExpressionFormatException {
        AssignmentExpression assignmentExpression = new AssignmentExpression("x = z*y*10");
        test(assignmentExpression, 60,2,3);
    }

    @Test
    public void testDivide() throws InvalidExpressionFormatException {
        AssignmentExpression assignmentExpression = new AssignmentExpression("x = 18/y/z");
        test(assignmentExpression, 3,2,3);
    }

    @Test
    public void testPlus() throws InvalidExpressionFormatException {
        AssignmentExpression assignmentExpression = new AssignmentExpression("x = 3+y+z");
        test(assignmentExpression, 8,2,3);
    }

    @Test
    public void testMinus() throws InvalidExpressionFormatException {
        AssignmentExpression assignmentExpression = new AssignmentExpression("x = z-y-2");
        test(assignmentExpression, -1,2,3);
    }

    private void test(AssignmentExpression expression, int xVal, int yVal, int zVal) throws InvalidExpressionFormatException {
        expression.handle();
        Assert.assertEquals(VariablesData.getInstance().getVar("x"), xVal);
        Assert.assertEquals(VariablesData.getInstance().getVar("y"), yVal);
        Assert.assertEquals(VariablesData.getInstance().getVar("z"), zVal);
    }

}
