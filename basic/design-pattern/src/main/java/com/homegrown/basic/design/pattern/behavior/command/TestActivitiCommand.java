package com.homegrown.basic.design.pattern.behavior.command;

/**
 * activiti框架中使用了命令设计模式，比TestCommand里的要复杂：
 * 1. 配合拦截器模式，让命令执行更具扩展性：可以在执行命令之前做一些共性操作，比如初始化命令上下文等等，CommandInvoker实现了CommandInterceptor
 * 另外，该框架中的命令调用者，也就是CommandInvoker，只执行一个命令，不像com.homegrown.basic.design.pattern.behavior.command.Switch，关联了
 * 两个命令，而且，这里是关联，activiti框架中的CommandInvoker只是对Command的依赖，对CommandInterceptor的关联
 *
 * activiti框架命令模式设计思路大概是：
 * 抽掉CommandInterceptor，CommandInvoker仅仅是一个命令调用者而已，只是触发了一次请求罢了，具体的业务逻辑还是得看具体的Command，为了实现这个目的，
 * activiti框架设计Command的方法签名时引入了CommandContext，然后为了初始化CommandContext，又引入了CommandInterceptor
 * @author youyu
 * @date 2020/1/20 11:31 AM
 */
public class TestActivitiCommand {
    public static void main(String[] args) {
        CommandContextInterceptor commandContextInterceptor = new CommandContextInterceptor();
        CommandInvoker commandInvoker = new CommandInvoker();
        commandContextInterceptor.setNext(commandInvoker);

        CommandExecutor commandExecutor = new CommandExecutorImpl(new CommandConfig(),commandContextInterceptor);

        NamePrinter namePrinter = new NamePrinter();
        commandExecutor.execute(namePrinter);
    }
}

interface CommandExecutor {
    CommandConfig getDefaultConfig();
    <T> T execute(CommandConfig config, ActivitiCommand<T> command);
    <T> T execute(ActivitiCommand<T> command);
}

class CommandExecutorImpl implements CommandExecutor {

    private final CommandConfig defaultConfig;
    private final CommandInterceptor first;

    public CommandExecutorImpl(CommandConfig defaultConfig, CommandInterceptor first) {
        this.defaultConfig = defaultConfig;
        this.first = first;
    }

    public CommandInterceptor getFirst() {
        return first;
    }

    @Override
    public CommandConfig getDefaultConfig() {
        return defaultConfig;
    }

    @Override
    public <T> T execute(ActivitiCommand<T> command) {
        return execute(defaultConfig, command);
    }

    @Override
    public <T> T execute(CommandConfig config, ActivitiCommand<T> command) {
        return first.execute(config, command);
    }

}

class CommandConfig{}

interface CommandInterceptor {
    <T> T execute(CommandConfig config, ActivitiCommand<T> command);
    CommandInterceptor getNext();
    void setNext(CommandInterceptor next);
}

abstract class AbstractCommandInterceptor implements CommandInterceptor {

    protected CommandInterceptor next;

    @Override
    public CommandInterceptor getNext() {
        return next;
    }

    @Override
    public void setNext(CommandInterceptor next) {
        this.next = next;
    }
}

class CommandContextInterceptor extends AbstractCommandInterceptor {
    public <T> T execute(CommandConfig config, ActivitiCommand<T> command) {
        System.out.println("init commandContext");
        Context.setCommandContext();
        return next.execute(config, command);
    }
}

class CommandInvoker extends AbstractCommandInterceptor {

    @Override
    public <T> T execute(CommandConfig config, ActivitiCommand<T> command) {
        return command.execute(Context.getCommandContext());
    }

    @Override
    public CommandInterceptor getNext() {
        return null;
    }

    @Override
    public void setNext(CommandInterceptor next) {
        throw new UnsupportedOperationException("CommandInvoker must be the last interceptor in the chain");
    }

}

interface ActivitiCommand <T> {
    T execute(CommandContext commandContext);
}

class NamePrinter implements ActivitiCommand<Void>{

    @Override
    public Void execute(CommandContext commandContext) {
        System.out.println(commandContext.getName());
        return null;
    }
}

class CommandContext {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class Context {
    private static CommandContext commandContext;

    public static CommandContext getCommandContext() {
        return commandContext;
    }

    public static void setCommandContext(){
        commandContext = new CommandContext();
        commandContext.setName("jam");
    }
}
