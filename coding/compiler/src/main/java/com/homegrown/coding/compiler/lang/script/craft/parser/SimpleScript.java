package com.homegrown.coding.compiler.lang.script.craft.parser;

import com.homegrown.coding.compiler.lang.script.craft.calculator.ASTNode;
import com.homegrown.coding.compiler.lang.script.craft.calculator.ASTNodeType;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * 一个简单的脚本解释器
 * 所支持的语法，请参见{@link SimpleParser}
 *
 * 运行脚本：
 * 在命令行下，键入：java SimpleScript
 * 则进入一个REPL界面。你可以依次敲入命令。比如：
 * > 2+3;
 * > int age = 10;
 * > int b;
 * > b = 10*2;
 * > age = age + b;
 * > exit();  //退出REPL界面
 *
 * 你还可以使用一个参数 -v，让每次执行脚本的时候，都输出AST和整个计算过程
 *
 * @author youyu
 */
public class SimpleScript {
    private static boolean verbose = false;
    private HashMap<String, Integer> variables = new HashMap<>();
    /**
     * 实现一个简单的REPL
     * @param args
     */
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("-v")) {
            verbose = true;
            System.out.println("verbose mode");
        }

        System.out.println("Simple script language!");

        SimpleParser parser = new SimpleParser();
        SimpleScript script = new SimpleScript();

        System.out.print("\n>");
        String scriptText = "";

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            try {
                String line = reader.readLine().trim();
                if (line.equals("exit();")) {
                    System.out.println("good bye!");
                    break;
                }

                scriptText += line + "\n";
                if (line.endsWith(";")){
                    ASTNode tree = parser.parse(scriptText);
                    if (verbose){
                        parser.dumpAST(tree,"");
                    }
                    script.evaluate(tree,"");

                    System.out.print("\n>");   //提示符
                    scriptText = "";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 遍历AST，计算值
     * @param node
     * @param indent
     */
    private Integer evaluate(ASTNode node, String indent) throws Exception{
        Integer result = null;
        if (verbose) {
            System.out.println(indent + "Calculating: " + node.getType());
        }
        ASTNode left;
        ASTNode right;
        int leftValue;
        int rightValue;

        switch (node.getType()){
            case Program:
                for (ASTNode child:node.getChildren()){
                    result = evaluate(child,indent);
                }
                break;
            case Additive:
                left = node.getChildren().get(0);
                leftValue = evaluate(left, indent+"\t");
                right = node.getChildren().get(1);
                rightValue = evaluate(right, indent+"\t");
                if (node.getText().equals("+")){
                    result = leftValue + rightValue;
                }else {
                    result = leftValue - rightValue;
                }
                break;
            case Multiplicative:
                left = node.getChildren().get(0);
                leftValue = evaluate(left, indent+"\t");
                right = node.getChildren().get(1);
                rightValue = evaluate(right, indent+"\t");
                if (node.getText().equals("*")){
                    result = leftValue * rightValue;
                }else {
                    result = leftValue / rightValue;
                }
                break;
            case IntLiteral:
                result = Integer.parseInt(node.getText());
                break;
            case Identifier:
                String varName = node.getText();
                if (variables.containsKey(varName)){
                    Integer value = variables.get(varName);
                    if (value != null){
                        result = value;
                    }else
                        throw new RuntimeException("variable " + varName + " has not been set any value");
                }else {
                    throw new RuntimeException("unknown variable: " + varName);
                }
                break;
            case AssignmentStmt:
                varName = node.getText();
                if (!variables.containsKey(varName)){
                    throw new RuntimeException("unknown variable: " + varName);
                }
            case IntDeclaration:
                //var name
                varName = node.getText();
                if (node.getChildren().size()>0){
                    ASTNode child = node.getChildren().get(0);
                    result = evaluate(child, indent + "\t");
                    variables.put(varName,result);
                }
                break;
            default:
        }
        if (verbose) {
            System.out.println(indent + "Result: " + result);
        } else if (indent.equals("")) { // 顶层的语句
            if (node.getType() == ASTNodeType.IntDeclaration || node.getType() == ASTNodeType.AssignmentStmt) {
                System.out.println(node.getText() + ": " + result);
            }else if (node.getType() != ASTNodeType.Program){
                System.out.println(result);
            }
        }

        return result;
    }
}
