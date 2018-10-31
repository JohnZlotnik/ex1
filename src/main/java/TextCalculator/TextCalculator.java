package TextCalculator;

import java.util.List;

public class TextCalculator {


    /**
     *
     * @param assignmentExpressions - list of assignment expression to calculate
     *
     * @throws InvalidExpressionFormatException if one of the expression had invalid format
     *
     * print all the variables with their values after calculating the input expressions
     */
    public static String calculate(List<String> assignmentExpressions) throws InvalidExpressionFormatException {

        for (String expression: assignmentExpressions){
            AssignmentExpression ex = new AssignmentExpression(expression);
            ex.handle();
        }

        String res = VariablesData.getInstance().toString();
        System.out.println(res);
        return res;
    }

}
