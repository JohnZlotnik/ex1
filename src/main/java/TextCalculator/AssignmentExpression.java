package TextCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AssignmentExpression {

    private String expression;

    private static final Pattern expressionPattern = Pattern.compile("([a-zA-Z]+)([\\+|-]?=)(.*)");
    private static final Pattern varPattern = Pattern.compile("[a-zA-Z]");

    private static final Pattern incPatternBefore = Pattern.compile("\\+\\+([a-zA-Z]+)");
    private static final Pattern incPatternAfter = Pattern.compile("([a-zA-Z]+)\\+\\+");
    private static final Pattern decPatternBefore = Pattern.compile("--([a-zA-Z]+)");
    private static final Pattern decPatternAfter = Pattern.compile("([a-zA-Z]+)--");

    private static final Pattern multPattern = Pattern.compile("\\d+\\*\\d+[*\\d]*");
    private static final Pattern dividePattern = Pattern.compile("\\d+/\\d+[/\\d]*");
    private static final Pattern plusPattern = Pattern.compile("\\d+\\+\\d+[+\\d]*");
    private static final Pattern minusPattern = Pattern.compile("\\d+-\\d+[-\\d]*");

    private List<String> toInc;
    private List<String> toDec;
    private String left, assignmentSign, right;


    public AssignmentExpression(String expression){
        this.expression = expression;
        this.toDec = new ArrayList<>();
        this.toInc = new ArrayList<>();

    }

    /**
     * calculate the input expression and update the relevant variables
     *
     * @throws InvalidExpressionFormatException if the expression is not in a valid assignment expression format
     */
    public void handle() throws InvalidExpressionFormatException {
        expression = expression.replaceAll("\\s", "");
        parseExpression();

        handleDecrementOpAfter();
        handleDecrementOpBefore();
        handleIncrementOpAfter();
        handleIncrementOpBefore();
        replaceVariables();
        handleMultiplyOp();
        handleDivideOp();
        handlePlusOp();
        handleMinusOp();

        int rightNum = Integer.valueOf(right);
        int newVal;
        switch (assignmentSign){
            case "+=":
                newVal = VariablesData.getInstance().getVar(left)+rightNum;
                break;
            case "-=":
                newVal = VariablesData.getInstance().getVar(left)-rightNum;
                break;
            case "=":
                newVal = rightNum;
                break;
            default:
                throw new InvalidExpressionFormatException("invalid assigment expression: " + assignmentSign);
        }

        VariablesData.getInstance().addVar(left, newVal);

        for (String var: toInc){
            VariablesData.getInstance().incVar(var);
        }

        for (String var: toDec){
            VariablesData.getInstance().decVar(var);
        }
    }


    private void parseExpression() throws InvalidExpressionFormatException {
        Matcher m = expressionPattern.matcher(expression);
        if (m.find()){
            left = m.group(1);
            assignmentSign = m.group(2);
            right = m.group(3);
        }
        else {
            throw new InvalidExpressionFormatException("expression " + expression + " has invalid format");
        }
    }

    private void replaceVariables() throws InvalidExpressionFormatException {
        Matcher m = varPattern.matcher(right);
        while (m.find()){
            String var = m.group();
            int val = VariablesData.getInstance().getVar(var);
            right = right.replaceFirst(var, String.valueOf(val));
        }
    }

    private void  handleMultiplyOp(){
        Matcher m = multPattern.matcher(right);

        while (m.find()){
            String match = m.group(0);
            String[] nums = match.split("\\*");
            int sum = Integer.valueOf(nums[0]);
            for (int i = 1; i < nums.length; i++){
                sum *= Integer.valueOf(nums[i]);
            }
            right = right.replace(match, String.valueOf(sum));
        }

    }

    private void handleDivideOp(){
        Matcher m = dividePattern.matcher(right);

        while (m.find()){
            String match = m.group(0);
            String[] nums = match.split("/");
            int sum = Integer.valueOf(nums[0]);
            for (int i = 1; i < nums.length; i++){
                sum /= Integer.valueOf(nums[i]);
            }
            right = right.replace(match, String.valueOf(sum));
        }
    }

    private void handlePlusOp(){
        Matcher m = plusPattern.matcher(right);

        while (m.find()){
            String match = m.group(0);
            String[] nums = match.split("\\+");
            int sum = 0;
            for (String num: nums){
                sum += Integer.valueOf(num);
            }
            right = right.replace(match, String.valueOf(sum));
        }
    }

    private void handleMinusOp(){
        Matcher m = minusPattern.matcher(right);

        while (m.find()){
            String match = m.group(0);
            String[] nums = match.split("\\-");
            int sum = Integer.valueOf(nums[0]);
            for (int i = 1; i < nums.length; i++){
                sum -= Integer.valueOf(nums[i]);
            }
            right = right.replace(match, String.valueOf(sum));
        }
    }


    private void handleIncrementOpBefore() throws InvalidExpressionFormatException {
        Matcher m = incPatternBefore.matcher(right);
        String var  = null;
        while (m.find()){
            var = m.group(1);
            VariablesData.getInstance().incVar(var);
        }
        if (var != null)
            right = m.replaceAll(var);
    }

    private void handleIncrementOpAfter() throws InvalidExpressionFormatException {
        Matcher m = incPatternAfter.matcher(right);
        String var  = null;
        while (m.find()){
            var = m.group(1);
            this.toInc.add(var);
        }
        if (var != null)
            right = m.replaceAll(var);
    }


    private void handleDecrementOpBefore() throws InvalidExpressionFormatException {
        Matcher m = decPatternBefore.matcher(right);
        String var  = null;
        while (m.find()){
            var = m.group(1);
            VariablesData.getInstance().decVar(var);
        }
        if (var != null)
            right = m.replaceAll(var);
    }

    private void handleDecrementOpAfter() throws InvalidExpressionFormatException {
        Matcher m = decPatternAfter.matcher(right);
        String var  = null;
        while (m.find()){
            var = m.group(1);
            toDec.add(var);
        }
        if (var != null)
            right = m.replaceAll(var);
    }



}
