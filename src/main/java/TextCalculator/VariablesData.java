package TextCalculator;

import java.util.HashMap;

/**
 * singleton object that responsible for keeping all the variables during the calculation process
 *
 * if we try to perform invalid operation (e.g. getting a variables that does not exist), an exception will be thrown
 */
public class VariablesData {

    private static HashMap<String, Integer> variables;

    private static VariablesData instance;

    private VariablesData(){
         variables = new HashMap<>();
    }

    static{
        try{
            instance = new VariablesData();
        } catch(Exception e){
            throw new RuntimeException("Exception occured in creating singleton instance");
        }
    }

    public static VariablesData getInstance(){
        return instance;
    }

    public void addVar(String var, int val){
        variables.put(var, val);
    }

    public int getVar(String var) throws InvalidExpressionFormatException {
        if (variables.containsKey(var)){
            return variables.get(var);
        }
        throw new InvalidExpressionFormatException("var " + var + " does not exist");
    }

    public void incVar(String var) throws InvalidExpressionFormatException {
        if (variables.containsKey(var)){
            int newVal = variables.get(var) + 1;
            variables.put(var, newVal);
        }
        else {
            throw new InvalidExpressionFormatException("can't increment variable " + var + " since it is a new one");
        }
    }

    public void decVar(String var) throws InvalidExpressionFormatException {
        if (variables.containsKey(var)){
            int newVal = variables.get(var) - 1;
            variables.put(var, newVal);
        }
        else {
            throw new InvalidExpressionFormatException("can't increment variable " + var + " since it is a new one");
        }
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        boolean first = true;
        for (String var: variables.keySet()){
            if (first){
                first = false;
            }
            else {
                sb.append(",");
            }

            sb.append(var);
            sb.append("=");
            sb.append(variables.get(var));
        }
        sb.append(")");

        return sb.toString();
    }



}
