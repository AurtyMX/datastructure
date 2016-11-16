package datastructure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Created by minxue on 16/11/15.
 */
public class Expression {
    public static void main(String[] args) {
        System.out.println(value("((1+2+(7-4)*2)*3*4+100)/2+10"));
    }

    public static List<String> suffix(String expr) {
        if (!isValidExpr(expr)) {
            System.out.println("表达式格式错误");
            return Collections.emptyList();
        }
        Stack<Character> stack = new Stack<>();
        List<String> list = new ArrayList<>();
        int isBrackets = 0;
        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);
            if (Character.isDigit(c)) {
                String num = readNum(expr, i);
                i += num.length() - 1;
                list.add(num);
                continue;
            }
            if (c == '(') {
                isBrackets = +10;
                stack.push(c);
                continue;
            }

            if (isOperator(String.valueOf(c))) {
                while (!stack.isEmpty() && compareOperator(c, stack.peek(), isBrackets) < 0) {
                    list.add(String.valueOf(stack.pop()));
                }
                stack.push(c);
            }
            if (c == ')') {
                isBrackets = -10;
                while (!stack.isEmpty() && stack.peek() != '(') {
                    list.add(String.valueOf(stack.pop()));
                }
                stack.pop();
            }
        }
        while (!stack.isEmpty()) {
            list.add(String.valueOf(stack.pop()));
        }
        return list;
    }

    private static String readNum(String expr, int index) {
        StringBuilder sb = new StringBuilder();
        for (int i = index; i < expr.length(); i++) {
            char c = expr.charAt(i);
            if (Character.isDigit(c)) {
                sb.append(String.valueOf(c));
            } else {
                break;
            }
        }
        return sb.toString();
    }

    public static int value(String expr) {
        Stack<String> stack = new Stack<>();
        List<String> suffix = suffix(expr);
        for (String str : suffix) {
            if (str.matches("[0-9]+")) {
                stack.push(str);
                continue;
            }
            if (isOperator(str)) {
                String s1 = stack.pop();
                String s2 = stack.pop();
                stack.push(String.valueOf(calc(str.toCharArray()[0], s2, s1)));
            }
        }
        return Integer.parseInt(stack.pop());
    }


    private static boolean isValidExpr(String expr) {
        return expr.matches("[\\d\\+\\-\\*/\\(\\)]+");

    }

    private static boolean isOperator(String str) {
        return str.matches("\\+|\\-|\\*|/");
    }

    private static int operatorValue(char c, int isBrackets) {
        int value = 0;
        switch (c) {
            case '+':
            case '-':
                value = 1;
                break;
            case '*':
            case '/':
                value = 2;
                break;
            default:
                break;
        }
        if (isBrackets > 0) return value * isBrackets;
        return value;
    }

    private static int compareOperator(char c1, char c2, int isBrackets) {
        return operatorValue(c1, isBrackets) - operatorValue(c2, isBrackets);
    }

    private static int calc(char operate, String s1, String s2) {
        int value = 0;
        switch (operate) {
            case '+':
                value = Integer.parseInt(s1) + Integer.parseInt(s2);
                break;
            case '-':
                value = Integer.parseInt(s1) - Integer.parseInt(s2);
                break;
            case '*':
                value = Integer.parseInt(s1) * Integer.parseInt(s2);
                break;
            case '/':
                value = Integer.parseInt(s1) / Integer.parseInt(s2);
                break;
            default:
                break;
        }
        return value;
    }

}
