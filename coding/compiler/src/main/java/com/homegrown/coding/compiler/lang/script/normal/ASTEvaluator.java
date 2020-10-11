package com.homegrown.coding.compiler.lang.script.normal;

import com.homegrown.coding.compiler.lang.script.craft.antlrtest.grammar.PlayScriptParser;
import com.homegrown.coding.compiler.lang.script.normal.scope.*;

import java.util.Stack;

/**
 * @author youyu
 */
public class ASTEvaluator extends JamScriptBaseVisitor<Object>{
    // 之前的编译结果
    private AnnotatedTree at;

    protected boolean traceFunctionCall = false;

    // 栈帧管理
    private Stack<StackFrame> stack = new Stack<>();

    public ASTEvaluator(AnnotatedTree at) {
        this.at = at;
    }

    /**
     * 栈帧入栈
     * 其中最重要的任务，是要保证栈帧的parentFrame设置正确。否则，
     * (1)随着栈的变深，查找变量的性能会降低
     * (2)甚至有可能找错栈桢，比如在递归(直接或间接)的场景下
     * @param frame
     */
    private void pushStack(StackFrame frame) {
        if (stack.size()>0){
            //从栈顶到栈底依次查找
            for (int i = stack.size()-1; i>0; i--){
                StackFrame f = stack.get(i);
                /*
                如果新加入的栈桢，跟某个已有的栈桢的enclosingScope是一样的，那么这俩的parentFrame也一样。
                因为它们原本就是同一级的嘛。
                比如：
                void foo(){};
                void bar(foo());

                或者：
                void foo();
                if (...){
                    foo();
                }
                */
                if (f.getScope().getEnclosingScope() == frame.getScope().getEnclosingScope()){
                    frame.setParentFrame(f.getParentFrame());
                    break;
                }
                /*
                如果新加入的栈桢，是某个已有的栈桢的下一级，那么就把把这个父子关系建立起来。比如：
                void foo(){
                    if (...){  //把这个块往栈桢里加的时候，就符合这个条件。
                    }
                }
                todo 在我理解，只有在函数调用时才会出现入栈操作，这个if语句块也要入栈？

                再比如,下面的例子:
                class MyClass{
                    void foo();
                }
                MyClass c = MyClass();  //先加Class的栈桢，里面有类的属性，包括父类的
                c.foo();                //再加foo()的栈桢


                 */
                else if (f.getScope() == frame.getScope().getEnclosingScope()){
                    frame.setParentFrame(f);
                    break;
                }
                /*
                这是针对函数可能是一等公民的情况。这个时候，函数运行时的作用域，与声明时的作用域会不一致。
                我在这里设计了一个“receiver”的机制，意思是这个函数是被哪个变量接收了。要按照这个receiver的作用域来判断。
                 */
                else if (frame.getObject() instanceof FunctionObject){
                    FunctionObject functionObject = (FunctionObject)frame.getObject();
                    if (functionObject.getReceiver() != null && functionObject.getReceiver().getEnclosingScope() == f.getScope()) {
                        frame.setParentFrame(f);
                        break;
                    }
                }
            }

            if (frame.getParentFrame() == null){
                frame.setParentFrame(stack.peek());
            }
        }
        stack.push(frame);

        dumpStackFrame();
    }

    private void popStack(){
        stack.pop();
    }

    private void dumpStackFrame(){
        System.out.println("\nStack Frames ----------------");
        for (StackFrame frame : stack){
            System.out.println(frame);
        }
        System.out.println("-----------------------------\n");
    }

    /**
     * 访问的逻辑可以对照JamScript.g4这个语法规则文件，每个节点应该关注什么都能找到
     * @param ctx
     * @return
     */
    @Override
    public Object visitProg(JamScriptParser.ProgContext ctx) {
        pushStack(new StackFrame(at.getScopeWith(ctx)));
        //访问各种块语句节点，比如表达式语句、赋值语句、for语句、if语句等等
        Object result = visitBlockStatements(ctx.blockStatements());
        popStack();
        return result;
    }

    @Override
    public Object visitBlockStatements(JamScriptParser.BlockStatementsContext ctx) {
        Object rtn = null;
        for (JamScriptParser.BlockStatementContext child : ctx.blockStatement()) {
            rtn = visitBlockStatement(child);

            //如果返回的是break，那么不执行下面的语句
            if (rtn instanceof BreakObject){
                break;
            }

            //碰到Return, 退出函数
            // TODO 要能层层退出一个个block，弹出一个栈桢
            else if (rtn instanceof ReturnObject){
                break;
            }
        }
        return rtn;
    }

    @Override
    public Object visitBlockStatement(JamScriptParser.BlockStatementContext ctx) {
        Object rtn = null;
        if (ctx.variableDeclarators() != null) {
            rtn = visitVariableDeclarators(ctx.variableDeclarators());
        } else if (ctx.statement() != null) {
            rtn = visitStatement(ctx.statement());
        }
        return rtn;
    }

    @Override
    public Object visitVariableDeclarators(JamScriptParser.VariableDeclaratorsContext ctx) {
        Object rtn = null;
        // Integer typeType = (Integer)visitTypeType(ctx.typeType()); //后面要利用这个类型信息
        for (JamScriptParser.VariableDeclaratorContext child : ctx.variableDeclarator()) {
            rtn = visitVariableDeclarator(child);
        }
        return rtn;
    }

    @Override
    public Object visitVariableDeclarator(JamScriptParser.VariableDeclaratorContext ctx) {
        Object rtn = null;
        LValue lValue = (LValue) visitVariableDeclaratorId(ctx.variableDeclaratorId());
        if (ctx.variableInitializer() != null) {
            rtn = visitVariableInitializer(ctx.variableInitializer());
            if (rtn instanceof LValue) {
                rtn = ((LValue) rtn).getValue();
            }
            lValue.setValue(rtn);
        }
        return rtn;
    }

    @Override
    public Object visitVariableDeclaratorId(JamScriptParser.VariableDeclaratorIdContext ctx) {
        Object rtn = null;
        Symbol symbol = at.getSymbolWith(ctx);
        rtn = getLValue((Variable) symbol);
        return rtn;
    }

    @Override
    public Object visitStatement(JamScriptParser.StatementContext ctx) {
        Object rtn = null;
        if (ctx.statementExpression != null) {
            rtn = visitExpression(ctx.statementExpression);
        }else if (ctx.IF() != null) {
            Boolean condition = (Boolean) visitParExpression(ctx.parExpression());
            if (Boolean.TRUE == condition) {
                rtn = visitStatement(ctx.statement(0));
            } else if (ctx.ELSE() != null) {
                rtn = visitStatement(ctx.statement(1));
            }
        }
        //while循环
        else if (ctx.WHILE() != null) {
            if (ctx.parExpression().expression() != null && ctx.statement(0) != null) {
                while (true) {
                    //每次循环都要计算一下循环条件
                    Boolean condition = true;
                    Object value = visitExpression(ctx.parExpression().expression());
                    if (value instanceof LValue) {
                        condition = (Boolean) ((LValue) value).getValue();
                    } else {
                        condition = (Boolean) value;
                    }

                    if (condition) {
                        //执行while后面的语句
                        rtn = visitStatement(ctx.statement(0));

                        //break
                        if (rtn instanceof BreakObject){
                            rtn = null;  //清除BreakObject，也就是只跳出一层循环
                            break;
                        }
                        //return
                        else if (rtn instanceof ReturnObject){
                            break;
                        }
                    }else {
                        break;
                    }
                }
            }
        }
        //for循环
        else if (ctx.FOR() != null) {
            // 添加StackFrame
            // todo 这里入栈了，是因为for语句有自己的作用域，就是括号括起来的地方
            BlockScope scope = (BlockScope) at.getScopeWith(ctx);
            StackFrame frame = new StackFrame(scope);
            // frame.parentFrame = stack.peek();
            pushStack(frame);

            JamScriptParser.ForControlContext forControl = ctx.forControl();
            if (forControl.enhancedForControl() != null) {

            }else {
                // 初始化部分执行一次
                if (forControl.forInit() != null) {
                    rtn = visitForInit(forControl.forInit());
                }

                while (true) {
                    Boolean condition = true; // 如果没有条件判断部分，意味着一直循环
                    if (forControl.expression() != null) {
                        Object value = visitExpression(forControl.expression());
                        if (value instanceof LValue) {
                            condition = (Boolean) ((LValue) value).getValue();
                        } else {
                            condition = (Boolean) value;
                        }
                    }

                    if (condition) {
                        // 执行for的语句体
                        rtn = visitStatement(ctx.statement(0));

                        //处理break
                        if (rtn instanceof BreakObject){
                            rtn = null;
                            break;
                        }
                        //return
                        else if (rtn instanceof ReturnObject){
                            break;
                        }

                        // 执行forUpdate，通常是“i++”这样的语句。这个执行顺序不能出错。
                        if (forControl.forUpdate != null) {
                            visitExpressionList(forControl.forUpdate);
                        }
                    }else {
                        break;
                    }

                }
            }
            // 去掉StackFrame
            popStack();
        }
        //block
        else if (ctx.blockLabel != null) {
            rtn = visitBlock(ctx.blockLabel);
        }
        //break语句
        else if (ctx.BREAK() != null) {
            rtn = BreakObject.instance();
        }
        //return语句
        else if (ctx.RETURN() != null) {
            if (ctx.expression() != null) {
                rtn = visitExpression(ctx.expression());

                //return语句应该不需要左值   //TODO 取左值的场景需要优化，目前都是取左值。
                if (rtn instanceof LValue){
                    rtn = ((LValue)rtn).getValue();
                }

                // 把闭包涉及的环境变量都打包带走
                if (rtn instanceof FunctionObject) {
                    FunctionObject functionObject = (FunctionObject) rtn;
                    getClosureValues(functionObject.getFunction(), functionObject);
                }
                //如果返回的是一个对象，那么检查它的所有属性里有没有是闭包的。//TODO 如果属性仍然是一个对象，可能就要向下递归查找了。
                else if (rtn instanceof ClassObject){
                    ClassObject classObject = (ClassObject)rtn;
                    getClosureValues(classObject);
                }
            }
            //把真实的返回值封装在一个ReturnObject对象里，告诉visitBlockStatements停止执行下面的语句
            rtn = new ReturnObject(rtn);
        }

        return rtn;
    }

    @Override
    public Object visitExpression(JamScriptParser.ExpressionContext ctx) {
        Object rtn = null;
        if (ctx.bop != null && ctx.expression().size() >= 2) {
            Object left = visitExpression(ctx.expression(0));
            Object right = visitExpression(ctx.expression(1));
            Object leftObject = left;
            Object rightObject = right;

            //如果是对栈中值的引用，需要拿到真实的值
            if (left instanceof LValue) {
                leftObject = ((LValue) left).getValue();
            }
            //如果是对栈中值的引用，需要拿到真实的值
            if (right instanceof LValue) {
                rightObject = ((LValue) right).getValue();
            }

            //本节点期待的数据类型
            Type type = at.getTypeWith(ctx);

            //左右两个子节点的类型
            Type type1 = at.getTypeWith(ctx.expression(0));
            Type type2 = at.getTypeWith(ctx.expression(1));

            switch (ctx.bop.getType()) {
                case PlayScriptParser.ADD:
                    rtn = add(leftObject, rightObject, type);
                    break;
                case PlayScriptParser.SUB:
                    rtn = minus(leftObject, rightObject, type);
                    break;
                case PlayScriptParser.MUL:
                    rtn = mul(leftObject, rightObject, type);
                    break;
                case PlayScriptParser.DIV:
                    rtn = div(leftObject, rightObject, type);
                    break;
                case PlayScriptParser.EQUAL:
                    rtn = EQ(leftObject, rightObject, PrimitiveType.getUpperType(type1, type2));
                    break;
                case PlayScriptParser.NOTEQUAL:
                    rtn = !EQ(leftObject, rightObject, PrimitiveType.getUpperType(type1, type2));
                    break;
                case PlayScriptParser.LE:
                    rtn = LE(leftObject, rightObject, PrimitiveType.getUpperType(type1, type2));
                    break;
                case PlayScriptParser.LT:
                    rtn = LT(leftObject, rightObject, PrimitiveType.getUpperType(type1, type2));
                    break;
                case PlayScriptParser.GE:
                    rtn = GE(leftObject, rightObject, PrimitiveType.getUpperType(type1, type2));
                    break;
                case PlayScriptParser.GT:
                    rtn = GT(leftObject, rightObject, PrimitiveType.getUpperType(type1, type2));
                    break;
                case PlayScriptParser.AND:
                    rtn = (Boolean) leftObject && (Boolean) rightObject;
                    break;
                case PlayScriptParser.OR:
                    rtn = (Boolean) leftObject || (Boolean) rightObject;
                    break;
                case PlayScriptParser.ASSIGN:
                    if (left instanceof LValue) {
                        //((LValue) left).setValue(right);
                        ((LValue) left).setValue(rightObject);
                        rtn = right;
                    }else {
                        System.out.println("Unsupported feature during assignment");
                    }
                    break;
                default:
                    break;
            }
        } else if (ctx.bop != null && ctx.bop.getType() == PlayScriptParser.DOT) {
            // 此语法是左递归的，算法体现这一点
            Object leftObject = visitExpression(ctx.expression(0));
            if (leftObject instanceof LValue) {
                Object value = ((LValue) leftObject).getValue();
                if (value instanceof ClassObject) {
                    ClassObject valueContainer = (ClassObject) value;
                    Variable leftVar = (Variable)at.getSymbolWith(ctx.expression(0));
                    // 获得field或调用方法
                    if (ctx.IDENTIFIER() != null) {
                        Variable variable = (Variable) at.getSymbolWith(ctx);

                        //对于this和super引用的属性，不用考虑重载，因为它们的解析是准确的
                        if (!(leftVar instanceof This || leftVar instanceof Super)) {
                            //类的成员可能需要重载
                            variable = at.lookupVariable(valueContainer.getType(), variable.getName());
                        }
                        LValue lValue = new MyLValue(valueContainer, variable);
                        rtn = lValue;
                    }else if (ctx.functionCall() != null) {
                        //要先计算方法的参数，才能加对象的StackFrame.
                        if (traceFunctionCall){
                            System.out.println("\n>>MethodCall : " + ctx.getText());
                        }
                        rtn = methodCall(valueContainer, ctx.functionCall(), (leftVar instanceof Super));
                    }
                }
            }else {
                System.out.println("Expecting an Object Reference");
            }
        }else if (ctx.primary() != null) {
            rtn = visitPrimary(ctx.primary());
        }
        // 后缀运算，例如：i++ 或 i--
        else if (ctx.postfix != null) {
            Object value = visitExpression(ctx.expression(0));
            LValue lValue = null;
            Type type = at.getTypeWith(ctx.expression(0));
            if (value instanceof LValue) {
                lValue = (LValue) value;
                value = lValue.getValue();
            }
            switch (ctx.postfix.getType()) {
                case PlayScriptParser.INC:
                    if (type == PrimitiveType.Integer) {
                        lValue.setValue((Integer) value + 1);
                    } else {
                        lValue.setValue((Long) value + 1);
                    }
                    rtn = value;
                    break;
                case PlayScriptParser.DEC:
                    if (type == PrimitiveType.Integer) {
                        lValue.setValue((Integer) value - 1);
                    } else {
                        lValue.setValue((long) value - 1);
                    }
                    rtn = value;
                    break;
                default:
                    break;
            }
        }
        //前缀操作，例如：++i 或 --i
        else if (ctx.prefix != null) {
            Object value = visitExpression(ctx.expression(0));
            LValue lValue = null;
            Type type = at.getTypeWith(ctx.expression(0));
            if (value instanceof LValue) {
                lValue = (LValue) value;
                value = lValue.getValue();
            }else {
                //todo 如果不是局部变量，该怎么办？
                return null;
            }

            switch (ctx.prefix.getType()) {
                case PlayScriptParser.INC:
                    if (type == PrimitiveType.Integer) {
                        rtn = (Integer) value + 1;
                    } else {
                        rtn = (Long) value + 1;
                    }
                    lValue.setValue(rtn);
                    break;
                case PlayScriptParser.DEC:
                    if (type == PrimitiveType.Integer) {
                        rtn = (Integer) value - 1;
                    } else {
                        rtn = (Long) value - 1;
                    }
                    lValue.setValue(rtn);
                    break;
                //!符号，逻辑非运算
                case PlayScriptParser.BANG:
                    rtn = !((Boolean) value);
                    break;
                default:
                    break;
            }
        }else if (ctx.functionCall() != null) {// functionCall
            rtn = visitFunctionCall(ctx.functionCall());
        }
        return rtn;
    }

    @Override
    public Object visitParExpression(JamScriptParser.ParExpressionContext ctx) {
        return visitExpression(ctx.expression());
    }

    @Override
    public Object visitForInit(JamScriptParser.ForInitContext ctx) {
        Object rtn = null;
        if (ctx.variableDeclarators() != null) {
            rtn = visitVariableDeclarators(ctx.variableDeclarators());
        } else if (ctx.expressionList() != null) {
            rtn = visitExpressionList(ctx.expressionList());
        }
        return rtn;
    }

    @Override
    public Object visitExpressionList(JamScriptParser.ExpressionListContext ctx) {
        Object rtn = null;
        for (JamScriptParser.ExpressionContext child : ctx.expression()) {
            rtn = visitExpression(child);
        }
        return rtn;
    }

    @Override
    public Object visitBlock(JamScriptParser.BlockContext ctx) {
        BlockScope scope = (BlockScope) at.getScopeWith(ctx);

        if (scope != null){  //有些block是不对应scope的，比如函数底下的block.
            StackFrame frame = new StackFrame(scope);
            // frame.parentFrame = stack.peek();
            pushStack(frame);
        }

        Object rtn = visitBlockStatements(ctx.blockStatements());

        if (scope !=null) {
            popStack();
        }

        return rtn;
    }

    /**
     * 对象方法调用
     * 要先计算完参数的值，然后再添加对象的StackFrame，然后再调用方法。
     * @param classObject 实际调用时的对象。通过这个对象可以获得真实的类，支持多态
     * @param ctx
     * @param isSuper
     * @return
     */
    private Object methodCall(ClassObject classObject, JamScriptParser.FunctionCallContext ctx, boolean isSuper) {
        Object rtn = null;

        //查找函数，并根据需要创建FunctionObject
        //如果查找到的是类的属性，FunctionType型的，需要在对象的栈桢里查
        StackFrame classFrame = new StackFrame(classObject);
        pushStack(classFrame);


        return null;
    }

    /**
     * 为闭包获取环境变量的值
     * @param function 闭包所关联的函数。这个函数会访问一些环境变量。
     * @param valueContainer 存放环境变量的值的容器
     */
    private void getClosureValues(Function function, JamObject valueContainer){
        if (function.getClosureVariables() != null) {
            for (Variable var : function.getClosureVariables()) {
                LValue lValue = getLValue(var); // 现在还可以从栈里取，退出函数以后就不行了
                Object value = lValue.getValue();
                valueContainer.setValue(var, value);
            }
        }
    }

    /**
     * 为从函数中返回的对象设置闭包值。因为多个函数型属性可能共享值，所以要打包到ClassObject中，而不是functionObject中
     * @param classObject
     */
    private void getClosureValues(ClassObject classObject){
        //先放在一个临时对象里，避免对classObject即读又写
        JamObject tempObject = new JamObject();

        for ( Variable v : classObject.getFields().keySet()) {
            if (v.getType() instanceof FunctionType) {
                Object object = classObject.getFields().get(v);
                if (object != null) {
                    FunctionObject functionObject = (FunctionObject) object;
                    getClosureValues(functionObject.getFunction(), tempObject);
                }
            }
        }

        classObject.getFields().putAll(tempObject.getFields());
    }

    private LValue getLValue(Variable variable) {
        StackFrame f = stack.peek();

        JamObject valueContainer = null;
        while (f != null) {
            if (f.getScope().containsSymbol(variable)) { //对于对象来说，会查找所有父类的属性
                valueContainer = f.getObject();
                break;
            }
            f = f.getParentFrame();
        }

        //通过正常的作用域找不到，就从闭包里找
        //原理：PlayObject中可能有一些变量，其作用域跟StackFrame.scope是不同的。
        if (valueContainer == null){
            f = stack.peek();
            while (f != null) {
                if (f.contains(variable)) {
                    valueContainer = f.getObject();
                    break;
                }
                f = f.getParentFrame();
            }
        }

        return new MyLValue(valueContainer, variable);
    }

    /// 各种运算
    private Object add(Object obj1, Object obj2, Type targetType) {
        Object rtn = null;
        if (targetType == PrimitiveType.String) {
            rtn = obj1 + String.valueOf(obj2);
        } else if (targetType == PrimitiveType.Integer) {
            rtn = ((Number) obj1).intValue() + ((Number) obj2).intValue();
        } else if (targetType == PrimitiveType.Float) {
            rtn = ((Number) obj1).floatValue() + ((Number) obj2).floatValue();
        } else if (targetType == PrimitiveType.Long) {
            rtn = ((Number) obj1).longValue() + ((Number) obj2).longValue();
        } else if (targetType == PrimitiveType.Double) {
            rtn = ((Number) obj1).doubleValue() + ((Number) obj2).doubleValue();
        } else if (targetType == PrimitiveType.Short) {
            rtn = ((Number) obj1).shortValue() + ((Number) obj2).shortValue();
        }
        else {
            System.out.println("unsupported add operation");
        }

        return rtn;
    }

    private Object minus(Object obj1, Object obj2, Type targetType) {
        Object rtn = null;
        if (targetType == PrimitiveType.Integer) {
            rtn = ((Number) obj1).intValue() - ((Number) obj2).intValue();
        } else if (targetType == PrimitiveType.Float) {
            rtn = ((Number) obj1).floatValue() - ((Number) obj2).floatValue();
        } else if (targetType == PrimitiveType.Long) {
            rtn = ((Number) obj1).longValue() - ((Number) obj2).longValue();
        } else if (targetType == PrimitiveType.Double) {
            rtn = ((Number) obj1).doubleValue() - ((Number) obj2).doubleValue();
        } else if (targetType == PrimitiveType.Short) {
            rtn = ((Number) obj1).shortValue() - ((Number) obj2).shortValue();
        }

        return rtn;
    }

    private Object mul(Object obj1, Object obj2, Type targetType) {
        Object rtn = null;
        if (targetType == PrimitiveType.Integer) {
            rtn = ((Number) obj1).intValue() * ((Number) obj2).intValue();
        } else if (targetType == PrimitiveType.Float) {
            rtn = ((Number) obj1).floatValue() * ((Number) obj2).floatValue();
        } else if (targetType == PrimitiveType.Long) {
            rtn = ((Number) obj1).longValue() * ((Number) obj2).longValue();
        } else if (targetType == PrimitiveType.Double) {
            rtn = ((Number) obj1).doubleValue() * ((Number) obj2).doubleValue();
        } else if (targetType == PrimitiveType.Short) {
            rtn = ((Number) obj1).shortValue() * ((Number) obj2).shortValue();
        }

        return rtn;
    }

    private Object div(Object obj1, Object obj2, Type targetType) {
        Object rtn = null;
        if (targetType == PrimitiveType.Integer) {
            rtn = ((Number) obj1).intValue() / ((Number) obj2).intValue();
        } else if (targetType == PrimitiveType.Float) {
            rtn = ((Number) obj1).floatValue() / ((Number) obj2).floatValue();
        } else if (targetType == PrimitiveType.Long) {
            rtn = ((Number) obj1).longValue() / ((Number) obj2).longValue();
        } else if (targetType == PrimitiveType.Double) {
            rtn = ((Number) obj1).doubleValue() / ((Number) obj2).doubleValue();
        } else if (targetType == PrimitiveType.Short) {
            rtn = ((Number) obj1).shortValue() / ((Number) obj2).shortValue();
        }

        return rtn;
    }

    private Boolean EQ(Object obj1, Object obj2, Type targetType) {
        Boolean rtn = null;
        if (targetType == PrimitiveType.Integer) {
            rtn = ((Number) obj1).intValue() == ((Number) obj2).intValue();
        } else if (targetType == PrimitiveType.Float) {
            rtn = ((Number) obj1).floatValue() == ((Number) obj2).floatValue();
        } else if (targetType == PrimitiveType.Long) {
            rtn = ((Number) obj1).longValue() == ((Number) obj2).longValue();
        } else if (targetType == PrimitiveType.Double) {
            rtn = ((Number) obj1).doubleValue() == ((Number) obj2).doubleValue();
        } else if (targetType == PrimitiveType.Short) {
            rtn = ((Number) obj1).shortValue() == ((Number) obj2).shortValue();
        }
        //对于对象实例、函数，直接比较对象引用
        else {
            rtn = obj1 == obj2;
        }

        return rtn;
    }

    private Object LE(Object obj1, Object obj2, Type targetType) {
        Object rtn = null;
        if (targetType == PrimitiveType.Integer) {
            rtn = ((Number) obj1).intValue() <= ((Number) obj2).intValue();
        } else if (targetType == PrimitiveType.Float) {
            rtn = ((Number) obj1).floatValue() <= ((Number) obj2).floatValue();
        } else if (targetType == PrimitiveType.Long) {
            rtn = ((Number) obj1).longValue() <= ((Number) obj2).longValue();
        } else if (targetType == PrimitiveType.Double) {
            rtn = ((Number) obj1).doubleValue() <= ((Number) obj2).doubleValue();
        } else if (targetType == PrimitiveType.Short) {
            rtn = ((Number) obj1).shortValue() <= ((Number) obj2).shortValue();
        }

        return rtn;
    }

    private Object LT(Object obj1, Object obj2, Type targetType) {
        Object rtn = null;
        if (targetType == PrimitiveType.Integer) {
            rtn = ((Number) obj1).intValue() < ((Number) obj2).intValue();
        } else if (targetType == PrimitiveType.Float) {
            rtn = ((Number) obj1).floatValue() < ((Number) obj2).floatValue();
        } else if (targetType == PrimitiveType.Long) {
            rtn = ((Number) obj1).longValue() < ((Number) obj2).longValue();
        } else if (targetType == PrimitiveType.Double) {
            rtn = ((Number) obj1).doubleValue() < ((Number) obj2).doubleValue();
        } else if (targetType == PrimitiveType.Short) {
            rtn = ((Number) obj1).shortValue() < ((Number) obj2).shortValue();
        }

        return rtn;
    }

    private Object GE(Object obj1, Object obj2, Type targetType) {
        Object rtn = null;
        if (targetType == PrimitiveType.Integer) {
            rtn = ((Number) obj1).intValue() >= ((Number) obj2).intValue();
        } else if (targetType == PrimitiveType.Float) {
            rtn = ((Number) obj1).floatValue() >= ((Number) obj2).floatValue();
        } else if (targetType == PrimitiveType.Long) {
            rtn = ((Number) obj1).longValue() >= ((Number) obj2).longValue();
        } else if (targetType == PrimitiveType.Double) {
            rtn = ((Number) obj1).doubleValue() >= ((Number) obj2).doubleValue();
        } else if (targetType == PrimitiveType.Short) {
            rtn = ((Number) obj1).shortValue() >= ((Number) obj2).shortValue();
        }

        return rtn;
    }

    private Object GT(Object obj1, Object obj2, Type targetType) {
        Object rtn = null;
        if (targetType == PrimitiveType.Integer) {
            rtn = ((Number) obj1).intValue() > ((Number) obj2).intValue();
        } else if (targetType == PrimitiveType.Float) {
            rtn = ((Number) obj1).floatValue() > ((Number) obj2).floatValue();
        } else if (targetType == PrimitiveType.Long) {
            rtn = ((Number) obj1).longValue() > ((Number) obj2).longValue();
        } else if (targetType == PrimitiveType.Double) {
            rtn = ((Number) obj1).doubleValue() > ((Number) obj2).doubleValue();
        } else if (targetType == PrimitiveType.Short) {
            rtn = ((Number) obj1).shortValue() > ((Number) obj2).shortValue();
        }

        return rtn;
    }

    /**
     * 自己实现的左值对象
     */
    private final class MyLValue implements LValue {
        private Variable variable;
        private JamObject valueContainer;

        public MyLValue(JamObject valueContainer, Variable variable) {
            this.valueContainer = valueContainer;
            this.variable = variable;
        }

        @Override
        public Object getValue() {
            //对于this或super关键字，直接返回这个对象，应该是ClassObject
            if (variable instanceof This || variable instanceof Super){
                return valueContainer;
            }

            return valueContainer.getValue(variable);
        }

        @Override
        public void setValue(Object value) {
            valueContainer.setValue(variable, value);

            //如果variable是函数型变量，那改变functionObject.receiver
            if (value instanceof FunctionObject){
                ((FunctionObject) value).setReceiver(variable);
            }
        }

        @Override
        public Variable getVariable() {
            return variable;
        }

        @Override
        public String toString() {
            return "LValue of " + variable.getName() + " : " + getValue();
        }

        @Override
        public JamObject getValueContainer() {
            return valueContainer;
        }
    }
}
