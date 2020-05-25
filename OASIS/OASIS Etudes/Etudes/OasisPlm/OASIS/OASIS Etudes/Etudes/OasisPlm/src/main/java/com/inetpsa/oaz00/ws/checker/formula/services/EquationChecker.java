/*
 * Author: U224106
 * Creation: 18 déc. 2014
 */
package com.inetpsa.oaz00.ws.checker.formula.services;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Properties;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.inetpsa.oaz00.ws.checker.model.ResultCodeType;
import com.inetpsa.oaz00.ws.scilab.utils.Constants;

/**
 * The Class EquationChecker.
 * 
 * @author U224106
 */
public class EquationChecker {

    /** The Scilab functions checker. */
    private static EquationFunctions ecFunctions = new EquationFunctions();

    /** The Scilab operators checker. */
    private static EquationOperators ecOperators = new EquationOperators();

    /** The Scilab separators checker. */
    private static EquationSeparators ecSeparators = new EquationSeparators();

    /** The Scilab constants checker. */
    private static EquationConstants ecConstants = new EquationConstants();

    /** Half connector properties */
    private static Properties HALF_CONNECTOR_PROPERTIES = Constants.getHalfConnectorProperties();

    /** The logger. */
    private static Logger logger = Logger.getLogger(EquationChecker.class);

    /**
     * Checking the formula/reportFormula is correct or not.
     * 
     * @param formula The string to be analyze
     * @param reportFormula The string to be analyze.
     * @param ecVariables Used to set the variables used in the formula to be analyze.
     * @return BigInteger value.
     * @throws EquationException The equation exception
     */
    public static BigInteger checkEquation(String formula, String reportFormula, EquationVariables ecVariables) throws EquationException {
        // Checking whether the formula is empty or not.
        checkEmptyFormula(formula, reportFormula);
        // Trim the leading and trailing white spaces present in the formula and reportFormula.
        formula = formula.trim();
        reportFormula = reportFormula.trim();
        // The first character of the formula cannot be a Separator or an Operator except + and -.
        // The last character of the formula cannot be a Separator or an Operator.
        checkInvalidFormula(formula, reportFormula);
        // The number of opening brackets is different from the number of closing ones: a distinction is made between parentheses () and square
        // brackets[].
        checkInconsistentBrackets(reportFormula);
        // Check for a block of brackets is empty.
        checkEmptyBlock(reportFormula);
        // Checking for a string followed by a closing bracket. Must be: nothing, an operator or a separator.
        checkCloseBlock(reportFormula);
        // Check separators: if (, [ or + just before, or / or * just after.
        checkSeparators(reportFormula);
        // Missing Operator and Seperator.
        checkMissingOperatorSeperator(reportFormula);
        // Invalid function check.
        checkFunction(formula, reportFormula);
        // Check for more than one decimal dot in a sequence of numbers.
        checkNumberFormat(reportFormula);
        // The entire constraints check.
        checkMultipleConstraints(0, reportFormula, ecVariables);
        // Check for , in numbers
        // checkCommaInNumbers(reportFormula);

        /** VCOPLMPSA-27483: Code modification START. Date: 17-October-2016 */
        // Check for report formula can not contain # symbol.
        checkInvalidReportFormula(reportFormula);
        // Check to handle invalid pi and E combinations
        checkForPiAndE(formula, reportFormula);
        // Check report formula must have operator between two operands or contributors.
        checkMissingOperatorForFormula(formula, reportFormula);
        // Check to find invalid contributor present in the formula.
        checkInvalideContributor(formula, reportFormula);
        /** VCOPLMPSA-27483: Code modification END. Date: 17-October-2016 */

        return ResultCodeType.FORMULA_OK.value();
    }

    /**
     * Check to find invalid operator position between pi and E
     * 
     * @param formula
     * @param reportFormula
     * @throws EquationException
     * @author E495366:Gajanan Kharat
     * @param reportFormula
     */
    private static void checkForPiAndE(String formula, String reportFormula) throws EquationException {
        String formulaValidate = formula;
        boolean isPiE = formulaValidate.contains(Constants.SYMBOL_PI + Constants.SYMBOL_E);
        boolean isEPi = formulaValidate.contains(Constants.SYMBOL_E + Constants.SYMBOL_PI);
        if (isPiE) {
            throw new EquationException(ResultCodeType.INVALID_OPERATOR_POSITION.value(), HALF_CONNECTOR_PROPERTIES.getProperty("INVALID_OPERATOR_POSITION"),
                    new BigInteger(Integer.toString(reportFormula.indexOf(Constants.SYMBOL_PI + Constants.SYMBOL_E) + 1)));
        }
        if (isEPi) {
            throw new EquationException(ResultCodeType.INVALID_OPERATOR_POSITION.value(), HALF_CONNECTOR_PROPERTIES.getProperty("INVALID_OPERATOR_POSITION"),
                    new BigInteger(Integer.toString(reportFormula.indexOf(Constants.SYMBOL_E + Constants.SYMBOL_PI) + 1)));
        }
    }

    /**
     * Check to find invalid contributor in the formula.
     * 
     * @param formula
     * @param reportFormula
     * @throws EquationException
     * @author E495366: Gajanan Kharat
     */
    private static void checkInvalideContributor(String formula, String reportFormula) throws EquationException {
        String invalideContri = formula.trim();
        invalideContri = invalideContri.replaceAll("\\#(.*?)\\#", Constants.SYMBOL_HASH);
        invalideContri = invalideContri.replaceAll("\\+|\\-|\\^|\\/|\\*|\\(|\\)|\\[|\\]", Constants.SYMBOL_HASH);
        for (String function : Constants.setOfFuns) {
            invalideContri = invalideContri.replaceAll(function, Constants.SYMBOL_HASH);
        }
        invalideContri = invalideContri.replaceAll(Constants.SYMBOL_PI, Constants.SYMBOL_HASH);
        invalideContri = invalideContri.replaceAll(Constants.SYMBOL_E, Constants.SYMBOL_HASH);
        invalideContri = invalideContri.replaceAll("\\" + Constants.SEPARATOR_COMMA, Constants.SYMBOL_HASH);
        String[] tokens = invalideContri.split(Constants.SYMBOL_HASH);
        int totalTokens = tokens.length;
        for (int i = 0; i < totalTokens; i++) {
            String token = tokens[i].trim();
            if (!token.isEmpty()) {
                String tokenMatch = tokens[i].trim();
                boolean isNumber = tokenMatch.matches("(\\d+)(\\.*\\d*)?");
                if (!isNumber) {
                    char invalidChar = tokenMatch.charAt(0);
                    int invalidPosition = 0;
                    int invalideCharPostion = 0;
                    if (tokenMatch.length() >= 2) {
                        invalidPosition = formula.indexOf(tokenMatch);
                        invalideCharPostion = getIvalideCharPostion(invalidChar, invalidPosition, formula, reportFormula);
                    } else {
                        invalideCharPostion = reportFormula.indexOf(invalidChar);
                    }
                    throw new EquationException(ResultCodeType.INVALID_CONTRIBUTOR_REFERENCE.value(), HALF_CONNECTOR_PROPERTIES.getProperty("INVALID_CONTRIBUTOR_REFERENCE"),
                            new BigInteger(Integer.toString(invalideCharPostion + 1)));
                }
            }
        }
    }

    /**
     * To check whether report formula contains operator at proper position or not.
     * 
     * @param formulaToCheck
     * @param reportFormula
     * @throws EquationException
     * @author E495366: Gajanan Kharat
     */
    private static void checkMissingOperatorForFormula(String formulaToCheck, String reportFormula) throws EquationException {
        String formula = formulaToCheck.replaceAll("\\s", "");
        int lenghtFormula = formula.length() - 1;
        int count = 0;
        char[] charArrayFormula = formula.toCharArray();
        for (int index = 0; index <= lenghtFormula; index++) {
            // To check first character is # or not
            if (index == 0 && charArrayFormula[index] == '#') {
                count++;
                continue;
            }
            // To check last character is # or not.
            if (index == lenghtFormula && charArrayFormula[index] == '#') {
                break;
            }
            if (charArrayFormula[index] == '#') {
                count++;
                if (count > 0 && count % 2 == 0) {
                    if (isInvalideOprPosition(charArrayFormula[index + 1])) {
                        continue;
                    }
                    char c = charArrayFormula[index + 1];
                    int ivalideCharPostion = reportFormula.indexOf(c);
                    // To get the total number of invalid character matches in the report formula.
                    int invalidCharMatches = getInvalidCharMatchesCount(c, reportFormula);
                    if (invalidCharMatches > 1) {
                        int position = index;
                        // To get the invalid character position in the report formula by calling this function only if matches found more than once.
                        ivalideCharPostion = getIvalideCharPostion(c, position, formula, reportFormula);
                    }
                    throw new EquationException(ResultCodeType.INVALID_OPERATOR_POSITION.value(), HALF_CONNECTOR_PROPERTIES.getProperty("INVALID_OPERATOR_POSITION"),
                            new BigInteger(Integer.toString(ivalideCharPostion)));
                }
                if (count > 0 && count % 2 != 0) {
                    if (isInvalideOprPosition(charArrayFormula[index - 1])) {
                        continue;
                    }
                    char c = charArrayFormula[index - 1];
                    int ivalideCharPostion = reportFormula.indexOf(c);
                    // To get the total number of invalid character matches in the report formula.
                    int invalidCharMatches = getInvalidCharMatchesCount(c, reportFormula);
                    if (invalidCharMatches > 1) {
                        int position = index;
                        // To get the invalid character position in the report formula by calling this function only if matches found more than once.
                        ivalideCharPostion = getIvalideCharPostion(c, position, formula, reportFormula);
                    }
                    throw new EquationException(ResultCodeType.INVALID_OPERATOR_POSITION.value(), HALF_CONNECTOR_PROPERTIES.getProperty("INVALID_OPERATOR_POSITION"),
                            new BigInteger(Integer.toString(ivalideCharPostion + 1)));
                }
            }
        }
    }

    /**
     * To get the invalid character matches count in the report formula.
     * 
     * @param c
     * @param reportFormula
     * @return int: Return total number of character match count present in the report formula.
     * @author E495366:Gajanan Kharat
     */
    private static int getInvalidCharMatchesCount(char matchChar, String reportFormula) {
        String strPattern = "[^" + matchChar + "]*" + matchChar;
        Pattern pattern = Pattern.compile(strPattern);
        Matcher matcher = pattern.matcher(reportFormula);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    /**
     * To get the invalid character position found in the report formula.
     * 
     * @param invalidChar
     * @param formula
     * @param reportFormula
     * @return int: Invalid character position found in the report formula.
     * @author E495366: Gajanan Kharat
     */
    private static int getIvalideCharPostion(char invalidChar, int position, String strFormula, String reportFormula) {
        String formula = strFormula;
        // List to include mathematical operator sequence which are present in the formula for later to have one to one mapping with report formula operator sequence.
        List<String> listFormulaOprts = new ArrayList<String>();
        // Here we are replacing all the occurrences of "UR-" from the report formula to XXX.
        formula = formula.replaceAll("[A-Z]{2}-", "XXX");
        char[] charArrayFormula = formula.toCharArray();
        for (int i = 0; i < position; i++) {
            String str = String.valueOf(charArrayFormula[i]);
            if (Constants.listOfMathOprs.contains(str)) {
                listFormulaOprts.add(str);
            }
        }
        int sizeListFormula = listFormulaOprts.size();
        int lengthValidation = 0;
        int invalidePosition = 0;
        char[] arrayReportFormula = reportFormula.toCharArray();
        // Here we have formula operator sequence in list listFormulaOprts
        for (int index = 0; index < arrayReportFormula.length; index++) {
            String str = String.valueOf(arrayReportFormula[index]);
            if (Constants.listOfMathOprs.contains(str)) {
                String operator = listFormulaOprts.get(lengthValidation);
                if (operator.equals(listFormulaOprts.get(lengthValidation))) {
                    // Here we are matching one to one operator relation ship between formula and report formula.
                    lengthValidation++;
                    continue;
                }
            }
            if (sizeListFormula == lengthValidation) {
                // Operator sequence matching between formula and report formula is over.
                StringBuilder stringBuilder = new StringBuilder(reportFormula);
                String substring = stringBuilder.substring(index, reportFormula.length());
                int indexOf = substring.indexOf(invalidChar);
                invalidePosition = index + indexOf;
                System.out.println("Index: " + invalidePosition);
                break;
            }
        }
        return invalidePosition;
    }

    /**
     * To check operator present in between two operands or not
     * 
     * @param operator: operator to check
     * @return boolean: flag
     * @author E495366: Gajanan Kharat
     */
    private static boolean isInvalideOprPosition(char operator) {
        boolean flag = true;
        switch (operator) {
        case '+':
            break;
        case '-':
            break;
        case '*':
            break;
        case '/':
            break;
        case '^':
            break;
        case '(':
            break;
        case ')':
            break;
        case '[':
            break;
        case ']':
            break;
        case '{':
            break;
        case '}':
            break;
        case ' ':
            break;
        case ',':
            break;
        default:
            flag = false;
            break;
        }
        return flag;
    }

    /**
     * The entire constraints check.
     * 
     * @param indexOfFormula Index of Formula string in actual formula
     * @param formula The string to be analyze.
     * @param ecVariables Used to set the variables used in the formula to be analyze.
     * @return true, if successful else false.
     * @throws EquationException The equation exception
     */
    private static boolean checkMultipleConstraints(int indexOfFormula, String formula, EquationVariables ecVariables) throws EquationException {
        int iChar = 0;
        boolean b = false;
        boolean hasNoOperator = true;

        // Checking whether the operator is present at the end of a string.
        checkOperators(indexOfFormula, formula);

        // Loop is used to check the errors in the formula
        for (iChar = 0; iChar < formula.length(); iChar++) {
            char c = formula.charAt(iChar);

            if (iChar == (formula.length() - 1)) {

                // Check last character of the analyzed string. It must be a constant, a variable or a number
                try {
                    b = checkEnd(formula, ecVariables);
                } catch (EquationException ee) {
                    if (hasNoOperator)
                        b = true;
                    else
                        throw ee;
                }

            } else {
                // Pattern for checking the two successive operator.
                Pattern pattern = Pattern.compile("[*+-/^]+[\\s]*[*+-/^]");
                Matcher matcher = pattern.matcher(formula);
                if (matcher.find())
                    throw new EquationException(ResultCodeType.INVALID_OPERATOR_POSITION.value(), HALF_CONNECTOR_PROPERTIES.getProperty("INVALID_OPERATOR_POSITION"),
                            BigInteger.valueOf(matcher.end()));
                // Operator analysis
                if (ecOperators.isOperator(new Character(c).toString())) {
                    hasNoOperator = false;
                    if (checkOperator(formula, iChar)) {
                        indexOfFormula = indexOfFormula + iChar + 1;
                        b = doubleCheck(indexOfFormula, formula.substring(0, iChar), formula.substring(iChar + 1), ecVariables);
                        break;
                    }
                }
                // Opening brackets block check
                if (c == '(' || c == '[') {
                    if (checkOpenBloc(formula, iChar)) {
                        int lastPos = getLast(c, formula.substring(iChar));
                        indexOfFormula = indexOfFormula + iChar + 1;
                        b = doubleCheck(indexOfFormula, formula.substring(iChar + 1, lastPos + iChar), formula.substring(lastPos + iChar + 1), ecVariables);
                        break;
                    }
                }
                // Pattern for checking the two successive seperator.
                Pattern pattern1 = Pattern.compile("[,;][\\s]*[,;]");
                Matcher matcher1 = pattern1.matcher(formula);
                if (matcher1.find())
                    throw new EquationException(ResultCodeType.INVALID_SEPARATOR_POSITION.value(), HALF_CONNECTOR_PROPERTIES.getProperty("INVALID_SEPARATOR_POSITION"),
                            BigInteger.valueOf(matcher1.end()));
                // Separators "," or ";" check
                if (ecSeparators.isSeparators(new Character(c).toString())) {
                    if (checkSeparator(formula, iChar)) {
                        indexOfFormula = indexOfFormula + iChar + 1;
                        b = doubleCheck(indexOfFormula, formula.substring(0, iChar), formula.substring(iChar + 1), ecVariables);
                        break;
                    }
                }
            }
        }
        // If all the checks are performed successfully return true of false.
        return b;
    }

    /**
     * Check comma in Numbers
     * 
     * @param reportFormula The string to be analyze.
     * @throws EquationException
     */
    private static void checkCommaInNumbers(String reportFormula) throws EquationException {
        Pattern pattern = Pattern.compile("[\\d]+[,][\\d]+");
        Matcher matcher = pattern.matcher(reportFormula);
        if (matcher.find()) {
            throw new EquationException(ResultCodeType.INVALID_COMMA_IN_NUMBER.value(), HALF_CONNECTOR_PROPERTIES.getProperty("INVALID_COMMA_IN_NUMBER"),
                    BigInteger.valueOf(matcher.start() + matcher.group().substring(1).indexOf(",") + 2));
        }
    }

    /**
     * Check for empty formula.
     * 
     * @param formula The string to be analyze.
     * @param reportFormula The string to be analyze.
     * @throws EquationException The equation exception.
     */
    private static void checkEmptyFormula(String formula, String reportFormula) throws EquationException {
        // Checking whether the formula is empty or not.
        if (formula == null || formula.length() == 0 || formula.equals(Constants.EMPTY_STRING) || formula.isEmpty() || reportFormula == null || reportFormula.equals(Constants.EMPTY_STRING)
                || reportFormula.isEmpty() || reportFormula.length() == 0) {
            throw new EquationException(ResultCodeType.EMPTY_FORMULA.value(), HALF_CONNECTOR_PROPERTIES.getProperty("EMPTY_FORMULA"));
        }
    }

    /**
     * Check for the invalid formula.
     * 
     * @param formula The string to be analyze.
     * @param reportFormula The string to be analyze.
     * @throws EquationException The equation exception.
     */
    private static void checkInvalidFormula(String formula, String reportFormula) throws EquationException {
        BigInteger indexPosition = new BigInteger("1");
        /** VCOPLMPSA-27483: Code modification START. Date: 17-November-2016 */
        List<Character> operatorsAndSeparators = Arrays.asList(',', ';', '*', '/', '~', '!', '@', '^', '&', '?', '|', '.', '=', '<', '>', '\\');
        /** VCOPLMPSA-27483: Code modification START. Date: 17-November-2016 */
        // Check whether formula contains the special character at the beginning.
        if (operatorsAndSeparators.contains(formula.charAt(0))) {
            throw new EquationException(ResultCodeType.INVALID_FORMULA.value(), HALF_CONNECTOR_PROPERTIES.getProperty("INVALID_FORMULA"), indexPosition);
        }
        // Check whether formula contains the special character at the end.
        else if (operatorsAndSeparators.contains(formula.charAt(formula.length() - 1))) {
            indexPosition = new BigInteger(Integer.toString(reportFormula.length()));
            throw new EquationException(ResultCodeType.INVALID_FORMULA.value(), HALF_CONNECTOR_PROPERTIES.getProperty("INVALID_FORMULA"), indexPosition);
        }
    }

    /**
     * Check for the missing operator and separator.
     * 
     * @param reportFormula The string to be analyze.
     * @throws EquationException The equation exception.
     */
    private static void checkMissingOperatorSeperator(String reportFormula) throws EquationException {
        // Pattern for checking the closing bracket followed by opening bracket or constant.
        Pattern pattern0 = Pattern.compile("[)\\]]\\s*[(\\[]|[)\\]]\\s*\\w+|[0-9]\\s*[(\\[]");
        Matcher matcher0 = pattern0.matcher(reportFormula);
        if (matcher0.find()) {
            throw new EquationException(ResultCodeType.MISSING_OPERATOR_OR_SEPARATOR.value(), HALF_CONNECTOR_PROPERTIES.getProperty("MISSING_OPERATOR_OR_SEPARATOR"),
                    BigInteger.valueOf(matcher0.end()));
        }
        // Pattern for checking the two successful contributors.
        Pattern pattern1 = Pattern.compile("(\\w+(-)\\d+)\\s*(\\w+(-)\\d+)");
        Matcher matcher1 = pattern1.matcher(reportFormula);
        if (matcher1.find()) {
            // Getting the matcher string.
            String errorPattern = matcher1.group();
            // Getting the index from report formula.
            int errorPosition = errorPattern.indexOf(" ") + 1;
            throw new EquationException(ResultCodeType.MISSING_OPERATOR_OR_SEPARATOR.value(), HALF_CONNECTOR_PROPERTIES.getProperty("MISSING_OPERATOR_OR_SEPARATOR"),
                    BigInteger.valueOf(matcher1.start() + errorPosition));
        }
        // Pattern for checking the contributor followed by opening bracket.
        Pattern pattern2 = Pattern.compile("(\\w+(-)\\d+)\\s*[(\\[]");
        Matcher matcher2 = pattern2.matcher(reportFormula);
        if (matcher2.find()) {
            throw new EquationException(ResultCodeType.MISSING_OPERATOR_OR_SEPARATOR.value(), HALF_CONNECTOR_PROPERTIES.getProperty("MISSING_OPERATOR_OR_SEPARATOR"),
                    BigInteger.valueOf(matcher2.end()));
        }
        // Pattern for checking the closing bracket followed by contributors.
        Pattern pattern3 = Pattern.compile("[)\\]]\\s*(\\w+(-)\\d+)");
        Matcher matcher3 = pattern3.matcher(reportFormula);
        if (matcher3.find()) {
            throw new EquationException(ResultCodeType.MISSING_OPERATOR_OR_SEPARATOR.value(), HALF_CONNECTOR_PROPERTIES.getProperty("MISSING_OPERATOR_OR_SEPARATOR"),
                    BigInteger.valueOf(matcher3.start() + 1));
        }
    }

    /**
     * Check for the invalid function.
     * 
     * @param formula The string to be analyze.
     * @param reportFormula The string to be analyze.
     * @throws EquationException The equation exception.
     */
    private static void checkFunction(String formula, String reportFormula) throws EquationException {
        // Pattern for checking the invalid function name.
        Pattern funNamePattern = Pattern.compile("[a-zA-Z0-9]+[\\s]*[(]");
        Matcher matchFunNamePattern = funNamePattern.matcher(reportFormula);
        while (matchFunNamePattern.find()) {
            String fun = matchFunNamePattern.group().substring(0, matchFunNamePattern.group().length() - 1);
            fun = fun.trim();
            if (!ecFunctions.isFunction(fun)) {
                throw new EquationException(ResultCodeType.INVALID_FUNCTION.value(), HALF_CONNECTOR_PROPERTIES.getProperty("INVALID_FUNCTION"), BigInteger.valueOf(reportFormula.indexOf(fun) + 1));
            }
        }
        // Pattern for finding successive seperators in the given reportFormula.
        Pattern pattern0 = Pattern.compile("(,\\s*,)|(;\\s*;)");
        Matcher matcher0 = pattern0.matcher(reportFormula);
        if (matcher0.find()) {
            throw new EquationException(ResultCodeType.INVALID_SEPARATOR_POSITION.value(), HALF_CONNECTOR_PROPERTIES.getProperty("INVALID_SEPARATOR_POSITION"),
                    BigInteger.valueOf(matcher0.start() + 1));
        }
        // Pattern for finding the function name in a given reportFormula.
        Pattern pattern1 = Pattern.compile("[A-Za-z]+|log10");
        Matcher matcher1 = pattern1.matcher(formula);
        while (matcher1.find()) {
            String var = matcher1.group();
            if (ecFunctions.isFunction(var)) {
                // Pattern for checking the function preceded by the opening bracket block or not in the reportFormula.
                Pattern pattern2 = Pattern.compile(var + "(?![\\s]*[(])");
                Matcher matcher2 = pattern2.matcher(formula);
                if (matcher2.find()) {
                    throw new EquationException(ResultCodeType.INVALID_FUNCTION.value(), HALF_CONNECTOR_PROPERTIES.getProperty("INVALID_FUNCTION"), BigInteger.valueOf(reportFormula.indexOf(var) + 1));
                }
            }
        }
        // Pattern for checking whether the function min/max contains a separator in between two contributors or not in a reportFormula.
        Pattern pattern3 = Pattern.compile("(max|min)(?![^,;]+[,;][^,;]+)");
        Matcher matcher3 = pattern3.matcher(reportFormula);
        while (matcher3.find()) {
            throw new EquationException(ResultCodeType.INVALID_FUNCTION.value(), HALF_CONNECTOR_PROPERTIES.getProperty("INVALID_FUNCTION"), BigInteger.valueOf(matcher3.start() + 1));
        }

    }

    /**
     * Check brackets inconsistency.
     * 
     * @param reportFormula The string to be analyze.
     * @throws EquationException the equation exception.
     */
    private static void checkInconsistentBrackets(String reportFormula) throws EquationException {
        Stack<Integer> lifoRoundBrackets = new Stack<Integer>();
        Stack<Integer> lifoSquareBrackets = new Stack<Integer>();
        for (int item = 0; item < reportFormula.length(); item++) {
            char character = reportFormula.charAt(item);
            if (character == '(') {
                lifoRoundBrackets.push(item);
            } else if (character == ')') {
                try {
                    lifoRoundBrackets.pop();
                } catch (EmptyStackException emptyStack) {
                    throw new EquationException(ResultCodeType.BRACKETS_INCONSISTENT.value(), HALF_CONNECTOR_PROPERTIES.getProperty("BRACKETS_INCONSISTENT"),
                            new BigInteger(Integer.toString(item + 1)));
                }
            } else if (character == '[')
                lifoSquareBrackets.push(item);
            else if (character == ']')
                try {
                    lifoSquareBrackets.pop();
                } catch (Exception e) {
                    throw new EquationException(ResultCodeType.BRACKETS_INCONSISTENT.value(), HALF_CONNECTOR_PROPERTIES.getProperty("BRACKETS_INCONSISTENT"),
                            new BigInteger(Integer.toString(item + 1)));
                }
        }
        if (!lifoRoundBrackets.isEmpty())
            throw new EquationException(ResultCodeType.BRACKETS_INCONSISTENT.value(), HALF_CONNECTOR_PROPERTIES.getProperty("BRACKETS_INCONSISTENT"),
                    new BigInteger(Integer.toString(lifoRoundBrackets.firstElement())));
    }

    /**
     * Check to special character # in report formula
     * 
     * @param reportFormula: The string to be analyze.
     * @throws EquationException: The equation exception.
     * @author E495366: Gajanan Kharat
     */
    private static void checkInvalidReportFormula(String reportFormula) throws EquationException {
        if (reportFormula.contains(Constants.SYMBOL_HASH)) {
            throw new EquationException(ResultCodeType.INVALID_FORMULA.value(), HALF_CONNECTOR_PROPERTIES.getProperty("INVALID_FORMULA"),
                    new BigInteger(Integer.toString(reportFormula.indexOf(Constants.SYMBOL_HASH) + 1)));
        }
    }

    /**
     * Check empty block.
     * 
     * @param reportFormula The string to be analyze.
     * @throws EquationException The equation exception.
     */
    private static void checkEmptyBlock(String reportFormula) throws EquationException {
        // Pattern for checking the empty bracket block.
        Pattern pattern = Pattern.compile("(\\(\\s*\\)|\\[\\s*\\])");
        Matcher matcher = pattern.matcher(reportFormula);
        if (matcher.find()) {
            throw new EquationException(ResultCodeType.EMPTY_BRACKETS_BLOCK.value(), HALF_CONNECTOR_PROPERTIES.getProperty("EMPTY_BRACKETS_BLOCK"),
                    new BigInteger(Integer.toString(reportFormula.indexOf(matcher.group()) + 1)));
        }
    }

    /**
     * Check string preceding an opening bracket. Must be: nothing, a function, an operator or E.
     * 
     * @param formula The string to analyze
     * @param bracketIndex The index of the opening bracket
     * @return true, if successful else false.
     * @throws EquationException the equation exception
     */
    private static boolean checkOpenBloc(String formula, int bracketIndex) throws EquationException {
        BigInteger indexPosition;
        String subString = formula.substring(0, bracketIndex);
        subString = subString.trim();
        if (subString.length() != 0 && !ecFunctions.isFunction(subString) && !ecOperators.isOperator(subString) && !isE(subString)) {
            indexPosition = new BigInteger(Integer.toString(bracketIndex));
            throw new EquationException(ResultCodeType.INVALID_BRACKET_POSITION.value(), HALF_CONNECTOR_PROPERTIES.getProperty("INVALID_BRACKET_POSITION"), indexPosition);
        }
        return true;
    }

    /**
     * Check string following a closing bracket. Must be: nothing, an operator or a separator.
     * 
     * @param reportFormula The string to be analyze.
     * @throws EquationException The equation exception
     */
    private static void checkCloseBlock(String reportFormula) throws EquationException {
        Pattern pattern = Pattern.compile("(\\)])+\\w");
        Matcher matcher = pattern.matcher(reportFormula);
        if (matcher.find()) {
            throw new EquationException(ResultCodeType.INVALID_BRACKET_POSITION.value(), HALF_CONNECTOR_PROPERTIES.getProperty("INVALID_BRACKET_POSITION"),
                    new BigInteger(Integer.toString(reportFormula.indexOf(reportFormula.substring(matcher.start())))));
        }
    }

    /**
     * Check operator: no successive operator (+, -, *, / or ^).
     * 
     * @param formula The string to analyze
     * @param characterIndex The index of the first operator
     * @return true, if successful else false.
     * @throws EquationException
     */
    private static boolean checkOperator(String formula, int characterIndex) throws EquationException {
        // 2 following operators
        for (int i = characterIndex; i < formula.length(); i++) {
            if (ecOperators.isOperator(new Character(formula.charAt(i)).toString())) {
                char ch = formula.charAt(i + 1);
                if (ecOperators.isOperator(new Character(ch).toString())) {
                    BigInteger indexPosition = new BigInteger(Integer.toString(i + 1));
                    throw new EquationException(ResultCodeType.INVALID_OPERATOR_POSITION.value(), HALF_CONNECTOR_PROPERTIES.getProperty("INVALID_OPERATOR_POSITION"), indexPosition);
                }
            }
        }
        return true;
    }

    /**
     * Check operators: Whether present at the end of a string.
     * 
     * @param indexOfFormula Index of Formula string in actual formula
     * @param formula The string to analyze
     * @throws EquationException
     */

    private static void checkOperators(int indexOfFormula, String formula) throws EquationException {
        BigInteger bigIntPosition;
        // Operator at the end of a string.
        Pattern pattern = Pattern.compile("(\\+|-|\\^|/|\\*)" + "$");
        Matcher matcher = pattern.matcher(formula);
        if (matcher.find()) {
            bigIntPosition = new BigInteger(Integer.toString(indexOfFormula + formula.length()));
            throw new EquationException(ResultCodeType.INVALID_OPERATOR_POSITION.value(), HALF_CONNECTOR_PROPERTIES.getProperty("INVALID_OPERATOR_POSITION"), bigIntPosition);
        }
    }

    /**
     * Check separator: no successive separator (, or ;).
     * 
     * @param formula The string to analyze
     * @param seperatorIndex The index of the first separator
     * @return true, if successful else false.
     * @throws EquationException
     */
    private static boolean checkSeparator(String formula, int seperatorIndex) throws EquationException {
        BigInteger bigIntPosition;
        // 2 following separators
        for (int i = seperatorIndex; i < formula.length(); i++) {

            if (ecSeparators.isSeparators(new Character(formula.charAt(i)).toString())) {
                char ch = formula.charAt(i + 1);
                if (ecSeparators.isSeparators(new Character(ch).toString())) {
                    bigIntPosition = new BigInteger(Integer.toString(i + 1));
                    throw new EquationException(ResultCodeType.INVALID_SEPARATOR_POSITION.value(), HALF_CONNECTOR_PROPERTIES.getProperty("INVALID_SEPARATOR_POSITION"), bigIntPosition);
                }
            }
        }
        return true;
    }

    /**
     * Check separator: Whether present just before (, [ or + just before, or / or * just after.
     * 
     * @param reportFormula The string to analyze
     * @return true, if successful else false.
     * @throws EquationException
     */
    private static boolean checkSeparators(String reportFormula) throws EquationException {
        BigInteger bigIntPosition;
        // Checking whether the reportFormula contains opening bracket followed by operator.
        Pattern pattern = Pattern.compile("[(\\[][\\s]*[/*\\^]");
        Matcher matcher = pattern.matcher(reportFormula);
        if (matcher.find()) {
            bigIntPosition = new BigInteger(Integer.toString(reportFormula.indexOf(matcher.group()) + matcher.group().length()));
            throw new EquationException(ResultCodeType.INVALID_OPERATOR_POSITION.value(), HALF_CONNECTOR_PROPERTIES.getProperty("INVALID_OPERATOR_POSITION"), bigIntPosition);
        }
        // Pattern for checking the opening bracket followed by separator in a reportFormula .
        Pattern pattern1 = Pattern.compile("[(\\[][\\s]*[,;]");
        Matcher matcher1 = pattern1.matcher(reportFormula);
        if (matcher1.find()) {
            bigIntPosition = new BigInteger(Integer.toString(reportFormula.indexOf(matcher1.group()) + matcher1.group().length()));
            throw new EquationException(ResultCodeType.INVALID_SEPARATOR_POSITION.value(), HALF_CONNECTOR_PROPERTIES.getProperty("INVALID_SEPARATOR_POSITION"), bigIntPosition);
        }
        // Pattern for checking the operator/separator followed by separator/operator in a given reportFormula.
        Pattern pattern2 = Pattern.compile("[*+-/^]+\\s*[.;]|[,;]+\\s*[*/^]");
        Matcher matcher2 = pattern2.matcher(reportFormula);
        if (matcher2.find()) {
            bigIntPosition = new BigInteger(Integer.toString(reportFormula.indexOf(matcher2.group()) + 1));
            throw new EquationException(ResultCodeType.INVALID_SEPARATOR_POSITION.value(), HALF_CONNECTOR_PROPERTIES.getProperty("INVALID_SEPARATOR_POSITION"), bigIntPosition);
        }
        return true;
    }

    /**
     * Check for more than one decimal dot in a sequence of numbers.
     * 
     * @param reportFormula The string to be analyze.
     * @throws EquationException
     */
    private static void checkNumberFormat(String reportFormula) throws EquationException {
        BigInteger decimalPosition;
        // Pattern for checking more than one decimal dot in a sequence of numbers.
        Pattern pattern = Pattern.compile("([0-9]*\\.+)\\w+\\.+");
        Matcher matcher = pattern.matcher(reportFormula);
        if (matcher.find()) {
            decimalPosition = new BigInteger(Integer.toString(reportFormula.indexOf(matcher.group()) + 1));
            throw new EquationException(ResultCodeType.INVALID_NUMBER_FORMAT.value(), HALF_CONNECTOR_PROPERTIES.getProperty("INVALID_NUMBER_FORMAT"), decimalPosition);
        }
        // Pattern for checking that decimal separator is not a ","
        // Pattern patternNoComa = Pattern.compile(".*\\d,\\d.*");
        // Matcher matcherNoComa = patternNoComa.matcher(reportFormula);
        // if (matcherNoComa.find()) {
        // decimalPosition = new BigInteger(Integer.toString(reportFormula.indexOf(matcherNoComa.group()) + 1));
        // throw new EquationException(ResultCodeType.INVALID_NUMBER_FORMAT.value(), HALF_CONNECTOR_PROPERTIES.getProperty("INVALID_NUMBER_FORMAT"),
        // decimalPosition);
        // }

    }

    /**
     * Double check on the left and on the right side.
     * 
     * @param indexOfFormula Index of Formula string in actual formula
     * @param left The left string.
     * @param right The right string.
     * @param ecVariables Used to set the variables used in the formula to be analyze.
     * @return true, if successful else false.
     * @throws EquationException
     */
    private static boolean doubleCheck(int indexOfFormula, String left, String right, EquationVariables ecVariables) throws EquationException {
        boolean b = false;
        try {
            // Left and Right checks
            b = checkMultipleConstraints(indexOfFormula, left, ecVariables);
            if (b) {
                b = checkMultipleConstraints(indexOfFormula, right, ecVariables);
                return b;
            }
        } catch (EquationException ee) {
            ee.toString();
            throw ee;
        }
        return b;
    }

    /**
     * Gets the closing bracket.
     * 
     * @param cB The opening bracket
     * @param formula The string to analyze
     * @return The closing bracket position
     */
    private static int getLast(char cB, String formula) {
        int iChar = 0;
        int iLevel = 0;
        char cF = ' ';
        if (cB == '(') {
            cF = ')';
        } else if (cB == '[') {
            cF = ']';
        }
        for (iChar = 1; iChar < formula.length(); iChar++) {
            char c = formula.charAt(iChar);
            if (c == cB)
                iLevel++;
            else if (c == cF) {
                iLevel--;
                if (iLevel < 0)
                    // Found
                    break;
            }
        }
        return iChar;
    }

    /**
     * Check end (only alphanumeric characters).
     * 
     * @param formula the string to analyze
     * @param ecVariables Used to set the variables used in the formula to be analyze.
     * @return true, if successful
     * @throws EquationException the equation exception
     */
    private static boolean checkEnd(String formula, EquationVariables ecVariables) throws EquationException {
        // Not a constant and not exponential value (1E10)
        if (!ecConstants.isConstant(formula) && !isE(formula)) {
            // A variable: to be added to the list of variables
            if (isVar(formula)) {
                if (!ecVariables.isVariable(formula)) {
                    ecVariables.addVariable(formula);
                }
            } else
            // Check if it's a number.
            if (isNum(formula)) {
                try {
                    Double.valueOf(formula).doubleValue();
                } catch (NumberFormatException nfe) {
                    throw new EquationException(ResultCodeType.INVALID_NUMBER_FORMAT.value(), "Invalid number: " + formula);
                }
            } else {
                throw new EquationException(ResultCodeType.INVALID_FUNCTION.value(), "Invalid string: " + formula);
            }
        }
        return true;
    }

    /**
     * Checks if the parameter is following xxxE format.
     * 
     * @param s the string to analyze
     * @return true, if is e
     */
    private static boolean isE(String s) {
        // Pattern for finding the exponent value.
        Pattern pattern = Pattern.compile("\\d*(\\.)?\\d*E$");
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }

    /**
     * Checks if the parameter is following 1234.56789 format.
     * 
     * @param s the string to analyze
     * @return true, if is numeric
     */
    private static boolean isNum(String s) {
        // Pattern for finding a number.
        Pattern pattern = Pattern.compile("(\\d|\\.)*");
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }

    /**
     * Checks if the parameter is a variable.
     * 
     * @param s the s
     * @return true, if is a variable.
     */
    private static boolean isVar(String s) {
        // Pattern for finding a variable.
        Pattern pattern = Pattern.compile("\\D+(\\w)*");
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }

    /*
     * ______________________________________________________________________________________________
     * 
     * MAIN ______________________________________________________________________________________________
     */
    /**
     * The main method - Unitary test.
     * 
     * @param args the arguments
     * @throws EquationException
     */
    public static void main(String[] args) {
        EquationVariables ecVariables = new EquationVariables();
        String s = "";
        try {
            if (EquationChecker.checkEquation(s, s, ecVariables) == ResultCodeType.FORMULA_OK.value()) {
                logger.debug(s + " " + ResultCodeType.FORMULA_OK + "\n");
            }
        } catch (EquationException ee) {
            logger.error(s + " Error desc = " + ee.getErrorDescription() + " Error status = " + ee.getErrorStatus() + " Error pos = " + ee.getPosition() + "\n");
        }
        s = "Sqrt #UR-0000211#";
        try {
            if (EquationChecker.checkEquation(s, "Sqrt UR1-0000211", ecVariables) == ResultCodeType.FORMULA_OK.value()) {
                logger.debug(s + " " + ResultCodeType.FORMULA_OK + "\n");
            }
        } catch (EquationException ee) {
            logger.error(s + " Error desc = " + ee.getErrorDescription() + " Error status = " + ee.getErrorStatus() + " Error pos = " + ee.getPosition() + "\n");
        }
        s = "Sqrt (#UR-0000211#)";
        try {
            if (EquationChecker.checkEquation(s, "Sqrt (UR1-0000211)", ecVariables) == ResultCodeType.FORMULA_OK.value()) {
                logger.debug(s + " " + ResultCodeType.FORMULA_OK + "\n");
            }
        } catch (EquationException ee) {
            logger.error(s + " Error desc = " + ee.getErrorDescription() + " Error status = " + ee.getErrorStatus() + " Error pos = " + ee.getPosition() + "\n");
        }
        s = "sqrt(#UR-0000211#)";
        try {
            if (EquationChecker.checkEquation(s, "sqrt(UR1-0000211)", ecVariables) == ResultCodeType.FORMULA_OK.value()) {
                logger.debug(s + " " + ResultCodeType.FORMULA_OK + "\n");
            }
        } catch (EquationException ee) {
            logger.error(s + " Error desc = " + ee.getErrorDescription() + " Error status = " + ee.getErrorStatus() + " Error pos = " + ee.getPosition() + "\n");
        }
        s = "Sqrt #UR-0000211#*#UR-0000212#";
        try {
            if (EquationChecker.checkEquation(s, "Sqrt UR-0000211*UR-0000212", ecVariables) == ResultCodeType.FORMULA_OK.value()) {
                logger.debug(s + " " + ResultCodeType.FORMULA_OK + "\n");
            }
        } catch (EquationException ee) {
            logger.error(s + " Error desc = " + ee.getErrorDescription() + " Error status = " + ee.getErrorStatus() + " Error pos = " + ee.getPosition() + "\n");
        }
        s = "min(#UR-0000211#)";
        try {
            if (EquationChecker.checkEquation(s, "min(UR1-0000211)", ecVariables) == ResultCodeType.FORMULA_OK.value()) {
                logger.debug(s + " " + ResultCodeType.FORMULA_OK + "\n");
            }
        } catch (EquationException ee) {
            logger.error(s + " Error desc = " + ee.getErrorDescription() + " Error status = " + ee.getErrorStatus() + " Error pos = " + ee.getPosition() + "\n");
        }

        s = "sqrt( ( #UR-0000211#*#UR-0000212# ) ^2 ( sqrt(#UR-0000221#^2+#UR-0000222#^2) ) ^2+ ( #UR-0031221#+#UR-0031222# ) ^2+#UR-0031222#^2)";
        try {
            if (EquationChecker.checkEquation(s, "sqrt( ( UR1-0000211*UR1-0000212 ) ^2 ( sqrt(UR1-0000221^2+UR1-0000222^2) ) ^2+ ( UR1-0031221+UR1-0031222 ) ^2+UR1-0031222^2)",
                    ecVariables) == ResultCodeType.FORMULA_OK.value()) {
                logger.debug(s + " " + ResultCodeType.FORMULA_OK + "\n");
            }
        } catch (EquationException ee) {
            logger.error(s + " Error desc = " + ee.getErrorDescription() + " Error status = " + ee.getErrorStatus() + " Error pos = " + ee.getPosition() + "\n");
        }
        s = " sqrt( ( #UR-0000211#*#UR-0000212#  ^2 ) sqrt(#UR-0000221#^2+#UR-0000222#^2) ) ^2+ ( (#UR-0031221#+#UR-0031222# ) ^2+#UR-0031222#^2)";
        try {
            if (EquationChecker.checkEquation(s, " sqrt( ( UR1-0000211*UR1-0000212  ^2 ) sqrt(UR1-0000221^2+UR1-0000222^2) ) ^2+ ( (UR1-0031221+UR1-0031222 ) ^2+UR1-0031222^2)",
                    ecVariables) == ResultCodeType.FORMULA_OK.value()) {
                logger.debug(s + " " + ResultCodeType.FORMULA_OK + "\n");
            }
        } catch (EquationException ee) {
            logger.error(s + " Error desc = " + ee.getErrorDescription() + " Error status = " + ee.getErrorStatus() + " Error pos = " + ee.getPosition() + "\n");
        }
        s = "max( #UR-0000211# ; #UR-0000212# ) ";
        try {
            if (EquationChecker.checkEquation(s, "max( UR1-0000211 ; UR1-0000212 ) ", ecVariables) == ResultCodeType.FORMULA_OK.value()) {
                logger.debug(s + " " + ResultCodeType.FORMULA_OK + "\n");
            }
        } catch (EquationException ee) {
            logger.error(s + " Error desc = " + ee.getErrorDescription() + " Error status = " + ee.getErrorStatus() + " Error pos = " + ee.getPosition() + "\n");
        }
        s = "sqrt( ( #UR-0000211#*#UR-0000212# ) ^2+ ( * sqrt(#UR-0000221#^2+#UR-0000222#^2) ) ^2+ ( #UR-0031221#+#UR-0031222# ) ^2+#UR-0031222#^2)";
        try {
            if (EquationChecker.checkEquation(s, "sqrt( ( UR1-0000211*UR1-0000212 ) ^2+ ( * sqrt(UR1-0000221^2+UR1-0000222^2) ) ^2+ ( UR1-0031221+UR1-0031222 ) ^2+UR1-0031222^2)",
                    ecVariables) == ResultCodeType.FORMULA_OK.value()) {
                logger.debug(s + " " + ResultCodeType.FORMULA_OK + "\n");
            }
        } catch (EquationException ee) {
            logger.error(s + " Error desc = " + ee.getErrorDescription() + " Error status = " + ee.getErrorStatus() + " Error pos = " + ee.getPosition() + "\n");
        }
        s = " sqrt( ( #UR-0000211#*#UR-0000212#  ^2 ) sqrt(#UR-0000221#^2+#UR-0000222#^2) ) ^2+ ( (#UR-0031221#+#UR-0031222# ) ^2+#UR-0031222#^2)";
        try {
            if (EquationChecker.checkEquation(s, " sqrt( ( UR1-0000211*UR1-0000212  ^2 ) sqrt(UR1-0000221^2+UR1-0000222^2) ) ^2+ ( (UR1-0031221+UR1-0031222 ) ^2+UR1-0031222^2)",
                    ecVariables) == ResultCodeType.FORMULA_OK.value()) {
                logger.debug(s + " " + ResultCodeType.FORMULA_OK + "\n");
            }
        } catch (EquationException ee) {
            logger.error(s + " Error desc = " + ee.getErrorDescription() + " Error status = " + ee.getErrorStatus() + " Error pos = " + ee.getPosition() + "\n");
        }
        s = "sqrt( #UR-0000211#*#UR-0000212# ^2 * 20) + sqrt(#UR-0000221#^2+#UR-0000222#^2) ^2+  (#UR-0031221#+#UR-0031222# ) ^2+#UR-0031222#^2";
        try {
            if (EquationChecker.checkEquation(s, " sqrt( UR1-0000211*UR1-0000212 ^2 *20) + sqrt(UR1-0000221^2+UR1-0000222^2) ^2+ (UR1-0031221+UR1-0031222 ) ^2+UR1-0031222^2",
                    ecVariables) == ResultCodeType.FORMULA_OK.value()) {
                logger.debug(s + " " + ResultCodeType.FORMULA_OK + "\n");
            }
        } catch (EquationException ee) {
            logger.error(s + " Error desc = " + ee.getErrorDescription() + " Error status = " + ee.getErrorStatus() + " Error pos = " + ee.getPosition() + "\n");
        }
        s = " min ( (100.2/2) ; 200.2/4 ) * max (aaa , bbb+ccc)  *   min (  #UR-0000211# #UR-0000212#   )";
        try {
            if (EquationChecker.checkEquation(s, " min ( (100.2/2) ; 200.2/4 ) * max (aaa , bbb+ccc)  *   min (  UR1-0000211 UR1-0000212   )", ecVariables) == ResultCodeType.FORMULA_OK.value()) {
                logger.debug(s + " " + ResultCodeType.FORMULA_OK + "\n");
            }
        } catch (EquationException ee) {
            logger.error(s + " Error desc = " + ee.getErrorDescription() + " Error status = " + ee.getErrorStatus() + " Error pos = " + ee.getPosition() + "\n");
        }
        s = "sqrt( ( #UR-0000211#*#UR-0000212#  ^2 )(sqrt(#UR-0000221#^2+#UR-0000222#^2)) ) ^2+ ( (#UR-0031221#+#UR-0031222# ) ^2+#UR-0031222#^2)";
        try {
            if (EquationChecker.checkEquation(s, "sqrt( ( UR1-0000211*UR1-0000212  ^2 )(sqrt(UR1-0000221^2+UR1-0000222^2)) ) ^2+ ( (UR1-0031221+UR1-0031222 ) ^2+UR1-0031222^2)",
                    ecVariables) == ResultCodeType.FORMULA_OK.value()) {
                logger.debug(s + " " + ResultCodeType.FORMULA_OK + "\n");
            }
        } catch (EquationException ee) {
            logger.error(s + " Error desc = " + ee.getErrorDescription() + " Error status = " + ee.getErrorStatus() + " Error pos = " + ee.getPosition() + "\n");
        }
        s = "( #UR-0000211#**#UR-0000212#  ^2)";
        try {
            if (EquationChecker.checkEquation(s, "( UR-0000211**UR-0000212 ^2)", ecVariables) == ResultCodeType.FORMULA_OK.value()) {
                logger.debug(s + " " + ResultCodeType.FORMULA_OK + "\n");
            }
        } catch (EquationException ee) {
            logger.error(s + " Error desc = " + ee.getErrorDescription() + " Error status = " + ee.getErrorStatus() + " Error pos = " + ee.getPosition() + "\n");
        }
        s = "sqrt( ( #UR-0000211#*#UR-0000212# ) ^2+ ( sqrt(#UR-0000221#^2+#UR-0000222#^2) ) ^2+ ( #UR-0031221#+#UR-0031222# ) ^2+#UR-0031222#^2) + -5";
        try {
            if (EquationChecker.checkEquation(s, "sqrt( ( UR-0000211*UR-0000212 ) ^2+ ( sqrt(UR-0000221^2+UR-0000222^2) ) ^2+ ( UR-0031221+UR-0031222 ) ^2+UR-0031222^2) + -5",
                    ecVariables) == ResultCodeType.FORMULA_OK.value()) {
                logger.debug(s + " " + ResultCodeType.FORMULA_OK + "\n");
            }
        } catch (EquationException ee) {
            logger.error(s + " Error desc = " + ee.getErrorDescription() + " Error status = " + ee.getErrorStatus() + " Error pos = " + ee.getPosition() + "\n");
        }
        s = "min(#UR-0000211#;;#UR-0000212#)";
        try {
            if (EquationChecker.checkEquation(s, "min(UR1-0000211;;UR1-0000212)", ecVariables) == ResultCodeType.FORMULA_OK.value()) {
                logger.debug(s + " " + ResultCodeType.FORMULA_OK + "\n");
            }
        } catch (EquationException ee) {
            logger.error(s + " Error desc = " + ee.getErrorDescription() + " Error status = " + ee.getErrorStatus() + " Error pos = " + ee.getPosition() + "\n");
        }
        s = "sqrt[ ( #UR-0000211# #UR-0000212# ) ^2] +sqrt(#UR-0000221#^2+#UR-0000222#2) ^2+ ( #UR-0031221#+#UR-0031222# ) ^2+#UR-0031222#^2";
        try {
            if (EquationChecker.checkEquation(s, "sqrt[ ( UR1-0000211 UR1-0000212 ) ^2] +sqrt(UR1-0000221^2+UR1-00002222) ^2+ ( UR1-0031221+UR1-0031222 ) ^2+UR1-0031222^2",
                    ecVariables) == ResultCodeType.FORMULA_OK.value()) {
                logger.debug(s + " " + ResultCodeType.FORMULA_OK + "\n");
            }
        } catch (EquationException ee) {
            logger.error(s + " Error desc = " + ee.getErrorDescription() + " Error status = " + ee.getErrorStatus() + " Error pos = " + ee.getPosition() + "\n");
        }
        s = "min(#UR-0000211#;+200;#UR-0000212#-2,0,0)";
        try {
            if (EquationChecker.checkEquation(s, "min(UR-0000211;+2.00;UR-0000212 -2,0,0)", ecVariables) == ResultCodeType.FORMULA_OK.value()) {
                logger.debug(s + " " + ResultCodeType.FORMULA_OK + "\n");
            }
        } catch (EquationException ee) {
            logger.error(s + " Error desc = " + ee.getErrorDescription() + " Error status = " + ee.getErrorStatus() + " Error pos = " + ee.getPosition() + "\n");
        }
        s = "maximum( ( #UR1-0000211#*#UR1-0000212# ) ^2 ; ( sqrt(#UR1-0000221#^2+#UR1-0000222#^2) ) ^2";
        try {
            if (EquationChecker.checkEquation(s, "maximum( ( UR-0000211*UR-0000212 ) ^2 ; ( sqrt(UR-0000221^2+UR-0000222^2) ) ^2", ecVariables) == ResultCodeType.FORMULA_OK.value()) {
                logger.debug(s + " " + ResultCodeType.FORMULA_OK + "\n");
            }
        } catch (EquationException ee) {
            logger.error(s + " Error desc = " + ee.getErrorDescription() + " Error status = " + ee.getErrorStatus() + " Error pos = " + ee.getPosition() + "\n");
        }
        s = "Max( ; ( #UR-0000211#*#UR-0000212# ) ^2 ; ( sqrt(#UR-0000221#^2+#UR-0000222#^2) ) ^2 ; ( #UR-0031221#+#UR-0031222# ) ^2 ; #UR-0031222#^2)";
        try {
            if (EquationChecker.checkEquation(s, "Max( ; ( UR1-0000211*UR1-0000212 ) ^2 ; ( sqrt(UR1-0000221^2+UR1-0000222^2) ) ^2 ; ( UR1-0031221+UR1-0031222 ) ^2 ; UR1-0031222^2)",
                    ecVariables) == ResultCodeType.FORMULA_OK.value()) {
                logger.debug(s + " " + ResultCodeType.FORMULA_OK + "\n");
            }
        } catch (EquationException ee) {
            logger.error(s + " Error desc = " + ee.getErrorDescription() + " Error status = " + ee.getErrorStatus() + " Error pos = " + ee.getPosition() + "\n");
        }
        s = "Max( ( #UR-0000211#*#UR-0000212# ) ^2 ; ( sqrt(#UR-0000221#^2+#UR-0000222#^2) ) ^2 ; ( #UR-0031221#+#UR-0031222# ) ^2 ; , #UR-0031222#^2)";
        try {
            if (EquationChecker.checkEquation(s, "Max( ( UR1-0000211*UR1-0000212 ) ^2 ; ( sqrt(UR1-0000221^2+UR1-0000222^2) ) ^2 ; ( UR1-0031221+UR1-0031222 ) ^2 ; , UR1-0031222^2)",
                    ecVariables) == ResultCodeType.FORMULA_OK.value()) {
                logger.debug(s + " " + ResultCodeType.FORMULA_OK + "\n");
            }
        } catch (EquationException ee) {
            logger.error(s + " Error desc = " + ee.getErrorDescription() + " Error status = " + ee.getErrorStatus() + " Error pos = " + ee.getPosition() + "\n");
        }
        s = "min(#UR-0000211#;+2.0.0;#UR-0000212#;-20)";
        try {
            if (EquationChecker.checkEquation(s, "min(UR1-0000211;+2.0.0;UR1-0000212;-20)", ecVariables) == ResultCodeType.FORMULA_OK.value()) {
                logger.debug(s + " " + ResultCodeType.FORMULA_OK + "\n");
            }
        } catch (EquationException ee) {
            logger.error(s + " Error desc = " + ee.getErrorDescription() + " Error status = " + ee.getErrorStatus() + " Error pos = " + ee.getPosition() + "\n");
        }

        s = "sqrt( ( #UR-0000211#*#UR-0000212# ) ^2+ ( sqrt(#UR-0000221#^2+#UR-0000222#^2) ) ^2+ ( #UR-0031221#+#UR-0031222# ) ^2+#UR-0031222#^2)";
        try {
            if (EquationChecker.checkEquation(s, "sqrt( ( UR-0000211*UR-0000212 ) ^2+ ( sqrt(UR-0000221^2+UR-0000222^2) ) ^2+ ( UR-0031221+UR-0031222 ) ^2+UR-0031222^2)",
                    ecVariables) == ResultCodeType.FORMULA_OK.value()) {
                logger.debug(s + " " + ResultCodeType.FORMULA_OK + "\n");
            }
        } catch (EquationException ee) {
            logger.error(s + " Error desc = " + ee.getErrorDescription() + " Error status = " + ee.getErrorStatus() + " Error pos = " + ee.getPosition() + "\n");
        }
        s = "sqrt( ( #UR-0000211#*#UR-0000212# ) ^2+ ( sqrt(#UR-0000221#^2-#UR-0000222#^2) ) ^2+ ( #UR-0031221#+#UR-0031222# ) /2+#UR-0031222#^2)";
        try {
            if (EquationChecker.checkEquation(s, "sqrt( ( UR-0000211*UR-0000212 ) ^2+ ( sqrt(UR-0000221^2-UR-0000222^2) ) ^2+ ( UR-0031221+UR-0031222 ) /2+UR-0031222^2)",
                    ecVariables) == ResultCodeType.FORMULA_OK.value()) {
                logger.debug(s + " " + ResultCodeType.FORMULA_OK + "\n");
            }
        } catch (EquationException ee) {
            logger.error(s + " Error desc = " + ee.getErrorDescription() + " Error status = " + ee.getErrorStatus() + " Error pos = " + ee.getPosition() + "\n");
        }
        s = "* sqrt( ( #UR-0000211#*#UR-0000212# ) ^2+ ( sqrt(#UR-0000221#^2-#UR-0000222#^2) ) ^2+ ( #UR-0031221#/#UR-0031222# ) ^2+#UR-0031222#^2)";
        try {
            if (EquationChecker.checkEquation(s, "* sqrt( ( UR-0000211*UR-0000212 ) ^2+ ( sqrt(UR-0000221^2-UR-0000222^2) ) ^2+ ( UR-0031221/UR-0031222 ) ^2+UR-0031222^2)",
                    ecVariables) == ResultCodeType.FORMULA_OK.value()) {
                logger.debug(s + " " + ResultCodeType.FORMULA_OK + "\n");
            }
        } catch (EquationException ee) {
            logger.error(s + " Error desc = " + ee.getErrorDescription() + " Error status = " + ee.getErrorStatus() + " Error pos = " + ee.getPosition() + "\n");
        }
        s = "sqrt( ( #UR-0000211#*#UR-0000212# ) ^2+ ( sqrt(#UR-0000221#^2+#UR-0000222#^2) ) ^2+ ( #UR-0031221#+#UR-0031222# ) ^2+#UR-0031222#^2) ,";
        try {
            if (EquationChecker.checkEquation(s, "sqrt( ( UR-0000211*UR-0000212 ) ^2+ ( sqrt(UR-0000221^2+UR-0000222^2) ) ^2+ ( UR-0031221+UR-0031222 ) ^2+UR-0031222^2) ,",
                    ecVariables) == ResultCodeType.FORMULA_OK.value()) {
                logger.debug(s + " " + ResultCodeType.FORMULA_OK + "\n");
            }
        } catch (EquationException ee) {
            logger.error(s + " Error desc = " + ee.getErrorDescription() + " Error status = " + ee.getErrorStatus() + " Error pos = " + ee.getPosition() + "\n");
        }
        s = "+ sqrt( ( #UR-0000211#*#UR-0000212# ) ^2+ ( sqrt(#UR-0000221#^2+#UR-0000222#^2) ) ^2+ ( #UR-0031221#+#UR-0031222# ) ^2+#UR-0031222#^2)";
        try {
            if (EquationChecker.checkEquation(s, "+ sqrt( ( UR-0000211*UR-0000212 ) ^2+ ( sqrt(UR-0000221^2+UR-0000222^2) ) ^2+ ( UR-0031221+UR-0031222 ) ^2+UR-0031222^2)",
                    ecVariables) == ResultCodeType.FORMULA_OK.value()) {
                logger.debug(s + " " + ResultCodeType.FORMULA_OK + "\n");
            }
        } catch (EquationException ee) {
            logger.error(s + " Error desc = " + ee.getErrorDescription() + " Error status = " + ee.getErrorStatus() + " Error pos = " + ee.getPosition() + "\n");
        }
        s = "sqrt( ( #UR-0000211#*#UR-0000212# ] ] ^2+ [ sqrt(#UR-0000221#^2+#UR-0000222#^2) ] ^2+ ( #UR-0031221#+#UR-0031222# ) ^2+#UR-0031222#^2]";
        try {
            if (EquationChecker.checkEquation(s, "sqrt( ( UR-0000211*UR-0000212 ] ] ^2+ [ sqrt(UR-0000221^2+UR-0000222^2) ] ^2+ ( UR-0031221+UR-0031222 ) ^2+UR-0031222^2]",
                    ecVariables) == ResultCodeType.FORMULA_OK.value()) {
                logger.debug(s + " " + ResultCodeType.FORMULA_OK + "\n");
            }
        } catch (EquationException ee) {
            logger.error(s + " Error desc = " + ee.getErrorDescription() + " Error status = " + ee.getErrorStatus() + " Error pos = " + ee.getPosition() + "\n");
        }
        s = "sqrt( ( #UR-0000211#*#UR-0000212# ) ^2+ [ sqrt(#UR-0000221#^2+#UR-0000222#^2) ] ^2+ ( #UR-0031221#+#UR-0031222# ) ^2+#UR-0031222#^2)";
        try {
            if (EquationChecker.checkEquation(s, "sqrt( ( UR-0000211*UR-0000212 ) ^2+ [ sqrt(UR-0000221^2+UR-0000222^2) ] ^2+ ( UR-0031221+UR-0031222 ) ^2+UR-0031222^2]",
                    ecVariables) == ResultCodeType.FORMULA_OK.value()) {
                logger.debug(s + " " + ResultCodeType.FORMULA_OK + "\n");
            }
        } catch (EquationException ee) {
            logger.error(s + " Error desc = " + ee.getErrorDescription() + " Error status = " + ee.getErrorStatus() + " Error pos = " + ee.getPosition() + "\n");
        }
        s = "sqrt( (#UR-0000211#*#UR-0000212# )) ^2+ [ sqrt(#UR-0000221#^2+#UR-0000222#^2) ] ^2+ ( #UR-0031221#+#UR-0031222# ) ^2+#UR-0031222#^2]";
        try {
            if (EquationChecker.checkEquation(s, "sqrt( (UR-0000211*UR-0000212 )) ^2+ [ sqrt(UR-0000221^2+UR-0000222^2) ] ^2+ ( UR-0031221+UR-0031222 ) ^2+UR-0031222^2]",
                    ecVariables) == ResultCodeType.FORMULA_OK.value()) {
                logger.debug(s + " " + ResultCodeType.FORMULA_OK + "\n");
            }
        } catch (EquationException ee) {
            logger.error(s + " Error desc = " + ee.getErrorDescription() + " Error status = " + ee.getErrorStatus() + " Error pos = " + ee.getPosition() + "\n");
        }
        s = "sqrt[[#UR-0000211#*#UR-0000212# ] ^2+ [ sqrt(#UR-0000221#^2+#UR-0000222#^2) ] ^2+ ( #UR-0031221#+#UR-0031222# ) ^2+#UR-0031222#^2]";
        try {
            if (EquationChecker.checkEquation(s, "sqrt[[UR-0000211*UR-0000212 ] ^2+ [ sqrt(UR-0000221^2+UR-0000222^2) ] ^2+ ( UR-0031221+UR-0031222 ) ^2+UR-0031222^2]",
                    ecVariables) == ResultCodeType.FORMULA_OK.value()) {
                logger.debug(s + " " + ResultCodeType.FORMULA_OK + "\n");
            }
        } catch (EquationException ee) {
            logger.error(s + " Error desc = " + ee.getErrorDescription() + " Error status = " + ee.getErrorStatus() + " Error pos = " + ee.getPosition() + "\n");
        }
        s = "sqrt( ( #UR-0000211#*#UR-0000212# ) ^2+ [] + ( sqrt(#UR-0000221#^2+#UR-0000222#^2) ) ^2+ ( #UR-0031221#+#UR-0031222# ) ^2+#UR-0031222#^2)";
        try {
            if (EquationChecker.checkEquation(s, "sqrt( ( UR-0000211*UR-0000212 ) ^2+ [] + ( sqrt(UR-0000221^2+UR-0000222^2) ) ^2+ ( UR-0031221+UR-0031222 ) ^2+UR-0031222^2)",
                    ecVariables) == ResultCodeType.FORMULA_OK.value()) {
                logger.debug(s + " " + ResultCodeType.FORMULA_OK + "\n");
            }
        } catch (EquationException ee) {
            logger.error(s + " Error desc = " + ee.getErrorDescription() + " Error status = " + ee.getErrorStatus() + " Error pos = " + ee.getPosition() + "\n");
        }
        s = "sqrt( ( #UR-0000211#*#UR-0000212# ) ^2+ () + ( sqrt(#UR-0000221#^2+#UR-0000222#^2) ) ^2+ ( #UR-0031221#+#UR-0031222# ) ^2+#UR-0031222#^2)";
        try {
            if (EquationChecker.checkEquation(s, "sqrt( ( UR-0000211*UR-0000212 ) ^2+ () + ( sqrt(UR-0000221^2+UR-0000222^2) ) ^2+ ( UR-0031221+UR-0031222 ) ^2+UR-0031222^2)",
                    ecVariables) == ResultCodeType.FORMULA_OK.value()) {
                logger.debug(s + " " + ResultCodeType.FORMULA_OK + "\n");
            }
        } catch (EquationException ee) {
            logger.error(s + " Error desc = " + ee.getErrorDescription() + " Error status = " + ee.getErrorStatus() + " Error pos = " + ee.getPosition() + "\n");
        }
        s = "sqrt( ( #UR-0000211#*#UR-0000212# ) ^2 ( sqrt(#UR-0000221#^2+#UR-0000222#^2) ) ^2+ ( #UR-0031221#+#UR-0031222# ) ^2+#UR-0031222#^2)";
        try {
            if (EquationChecker.checkEquation(s, "sqrt( ( UR-0000211*UR-0000212 ) ^2 ( sqrt(UR-0000221^2+UR-0000222^2) ) ^2+ ( UR-0031221+UR-0031222 ) ^2+UR-0031222^2)",
                    ecVariables) == ResultCodeType.FORMULA_OK.value()) {
                logger.debug(s + " " + ResultCodeType.FORMULA_OK + "\n");
            }
        } catch (EquationException ee) {
            logger.error(s + " Error desc = " + ee.getErrorDescription() + " Error status = " + ee.getErrorStatus() + " Error pos = " + ee.getPosition() + "\n");
        }
        s = "sqrt( ( #UR-0000211#*#UR-0000212# ) ^2+ ( * sqrt(#UR-0000221#^2+#UR-0000222#^2) ) ^2+ ( #UR-0031221#+#UR-0031222# ) ^2+#UR-0031222#^2)";
        try {
            if (EquationChecker.checkEquation(s, "sqrt( ( UR-0000211*UR-0000212 ) ^2+ ( * sqrt(UR-0000221^2+UR-0000222^2) ) ^2+ ( UR-0031221+UR-0031222 ) ^2+UR-0031222^2)",
                    ecVariables) == ResultCodeType.FORMULA_OK.value()) {
                logger.debug(s + " " + ResultCodeType.FORMULA_OK + "\n");
            }
        } catch (EquationException ee) {
            logger.error(s + " Error desc = " + ee.getErrorDescription() + " Error status = " + ee.getErrorStatus() + " Error pos = " + ee.getPosition() + "\n");
        }
        s = "sqrt] ( #UR-0000211#*#UR-0000212# ) ^2+ ( sqrt(#UR-0000221#^2+#UR-0000222#^2) ) ^2+ ( #UR-0031221#+#UR-0031222# ) ^2+#UR-0031222#^2]";
        try {
            if (EquationChecker.checkEquation(s, "sqrt] ( UR-0000211*UR-0000212 ) ^2+ ( sqrt(UR-0000221^2+UR-0000222^2) ) ^2+ ( UR-0031221+UR-0031222 ) ^2+UR-0031222^2]",
                    ecVariables) == ResultCodeType.FORMULA_OK.value()) {
                logger.debug(s + " " + ResultCodeType.FORMULA_OK + "\n");
            }
        } catch (EquationException ee) {
            logger.error(s + " Error desc = " + ee.getErrorDescription() + " Error status = " + ee.getErrorStatus() + " Error pos = " + ee.getPosition() + "\n");
        }
        s = "sqrt( ( #UR-0000211#*#UR-0000212#  ^2* ) sqrt(#UR-0000221#^2+#UR-0000222#^2) ) ^2+ ( (#UR-0031221#+#UR-0031222# ) ^2+#UR-0031222#^2)";
        try {
            if (EquationChecker.checkEquation(s, "sqrt( ( UR-0000211*UR-0000212  ^2* ) sqrt(UR-0000221^2+UR-0000222^2) ) ^2+ ( (UR-0031221+UR-0031222 ) ^2+UR-0031222^2)",
                    ecVariables) == ResultCodeType.FORMULA_OK.value()) {
                logger.debug(s + " " + ResultCodeType.FORMULA_OK + "\n");
            }
        } catch (EquationException ee) {
            logger.error(s + " Error desc = " + ee.getErrorDescription() + " Error status = " + ee.getErrorStatus() + " Error pos = " + ee.getPosition() + "\n");
        }
        s = "sqrt( ( 01A01-211*01A01-212 ) 2+ ( sqrt(01A01-221^2+01A01-222^2) ) ^2+ ( 01A01-31221+01A01-31222 ) ^2+01A01-31222^2)";
        try {
            if (EquationChecker.checkEquation(s, "sqrt( ( 01A01-211*01A01-212 ) 2+ ( sqrt(01A01-221^2+01A01-222^2) ) ^2+ ( 01A01-31221+01A01-31222 ) ^2+01A01-31222^2)",
                    ecVariables) == ResultCodeType.FORMULA_OK.value()) {
                logger.debug(s + " " + ResultCodeType.FORMULA_OK + "\n");
            }
        } catch (EquationException ee) {
            logger.error(s + " Error desc = " + ee.getErrorDescription() + " Error status = " + ee.getErrorStatus() + " Error pos = " + ee.getPosition() + "\n");
        }
        s = "max( ( #UR-0000211#*#UR-0000212# ) ^2 )";
        try {
            if (EquationChecker.checkEquation(s, "max( ( UR-0000211*UR-0000212 ) ^2 )", ecVariables) == ResultCodeType.FORMULA_OK.value()) {
                logger.debug(s + " " + ResultCodeType.FORMULA_OK + "\n");
            }
        } catch (EquationException ee) {
            logger.error(s + " Error desc = " + ee.getErrorDescription() + " Error status = " + ee.getErrorStatus() + " Error pos = " + ee.getPosition() + "\n");
        }
        s = "abs (( #UR-0000211# #UR-0000212# ) ^2) +sqrt(#UR-0000221#^2+#UR-0000222#^2) ^2+ ( #UR-0031221##UR-0031222# ) ^2+#UR-0031222#^2";
        try {
            if (EquationChecker.checkEquation(s, "abs (( UR-0000211 UR-0000212 ) ^2) +sqrt(UR-0000221^2+UR-0000222^2) ^2+ ( UR-0031221UR-0031222 ) ^2+UR-0031222^2",
                    ecVariables) == ResultCodeType.FORMULA_OK.value()) {
                logger.debug(s + " " + ResultCodeType.FORMULA_OK + "\n");
            }
        } catch (EquationException ee) {
            logger.error(s + " Error desc = " + ee.getErrorDescription() + " Error status = " + ee.getErrorStatus() + " Error pos = " + ee.getPosition() + "\n");
        }
        s = "sin (( #UR-0000211# + #UR-0000212# ) ^2 /)";
        try {
            if (EquationChecker.checkEquation(s, "sin (( #UR-0000211# + #UR-0000212# ) ^2 /)", ecVariables) == ResultCodeType.FORMULA_OK.value()) {
                logger.debug(s + " " + ResultCodeType.FORMULA_OK + "\n");
            }
        } catch (EquationException ee) {
            logger.error(s + " Error desc = " + ee.getErrorDescription() + " Error status = " + ee.getErrorStatus() + " Error pos = " + ee.getPosition() + "\n");
        }

        // Check VCOPLMPSA-26940 : Problem with Decimal Separator
        s = "min ( max( 4,5*#UR-0000211# + #UR-0000212#, #UR-0000213# ))";
        try {
            if (EquationChecker.checkEquation(s, s, ecVariables) == ResultCodeType.FORMULA_OK.value()) {
                logger.debug(s + " " + ResultCodeType.FORMULA_OK + "\n");
            }
        } catch (EquationException ee) {
            logger.error(s + " Error desc = " + ee.getErrorDescription() + " Error status = " + ee.getErrorStatus() + " Error pos = " + ee.getPosition() + "\n");
        }

    }
}
