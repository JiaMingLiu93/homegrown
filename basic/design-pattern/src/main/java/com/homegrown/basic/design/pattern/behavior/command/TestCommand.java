package com.homegrown.basic.design.pattern.behavior.command;

/**
 * 命令模式
 * 优势：
 * 1. 解耦请求者与接收者，在本例中，Switch是请求者，有两个请求：打开和关闭；Fan、Light是接收者，代表具体的物体、业务，可变化的。
 *    1.1 为什么要解耦呢？
 *        1.1.1 如果不解耦，会出现什么情况。显然，如果要通过Switch来打开和关闭Fan、Light，Switch必须依赖这两个类，要知道，这两个是具体的类，就产生了
 *              高度耦合的情况，如果想增加一个电器，同时又想使用Switch来控制打开和关闭，不解耦的话，只能在Switch中的打开和关闭方法中增加对应的逻辑代码，
 *              这样就不符合开闭原则了，也不好扩展
 *        1.1.2 ok，怎么解决这个问题呢，自然是解耦了，该怎么解呢？
 *              首先想到的，应该就是不让Switch直接依赖具体类，让它依赖一个接口（Command），然后在Switch的两个方法中写一些对这个接口调用的代码，此时，已经达到了不依赖
 *              具体类的目的了。
 *              然后，Fan、Light这些具体类怎么受Switch控制呢，因为Switch依赖一个接口（Command），那就创建一个实现该接口的类，比如FanOnCommand，扔给Switch，调用Switch
 *              的打开和关闭方法就可以触发实现了Command接口的FanOnCommand，而之前的业务就可以写在FanOnCommand实现的接口方法中，这样的话，如果想打开一个Light，
 *              提供一个LightOnCommand扔给Switch，Switch也可以控制Light的开启了，换句话说，Switch可以控制任何实现了Command接口的类了。
 * 缺点：
 * 1. 只是每个具体类，如果想被Switch控制，就需要提供与Switch方法相同个数的类，代码量就上去了
 * 场景：
 * 1. Switch好像是一个遥控器，每点一个按钮，就发送一个命令出去，至于谁会拿这个命令干点啥，它是不关心的；照这么看来，它也属于生产者/消费者模式，生产者发送一个消息
 *    ，就不管了，对这个消息感兴趣的消费者会作出相应处理。
 *    生产者/消费者模式的另一种实现是消息队列，它和命令模式就很像了
 *
 * @author youyu
 * @date 2020/1/17 2:06 PM
 */
public class TestCommand {
    public static void main(String[] args) {
        Light  testLight = new Light( );
        LightOnCommand testLOC = new LightOnCommand(testLight);
        LightOffCommand testLFC = new LightOffCommand(testLight);
        Switch testSwitch = new Switch( testLOC,testLFC);
        testSwitch.flipUp( );
        testSwitch.flipDown( );
        Fan testFan = new Fan( );
        FanOnCommand foc = new FanOnCommand(testFan);
        FanOffCommand ffc = new FanOffCommand(testFan);
        Switch ts = new Switch( foc,ffc);
        ts.flipUp( );
        ts.flipDown( );
    }
}

class Fan {
    public void startRotate() {
        System.out.println("Fan is rotating");
    }
    public void stopRotate() {
        System.out.println("Fan is not rotating");
    }
}

class Light {
    public void turnOn( ) {
        System.out.println("Light is on ");
    }
    public void turnOff( ) {
        System.out.println("Light is off");
    }
}
interface Command {
    public abstract void execute ( );
}

class Switch {
    private Command UpCommand, DownCommand;
    public Switch( Command Up, Command Down) {
        UpCommand = Up; // concrete Command registers itself with the invoker
        DownCommand = Down;
    }
    void flipUp( ) { // invoker calls back concrete Command, which executes the Command on the receiver
        UpCommand . execute ( ) ;
    }
    void flipDown( ) {
        DownCommand . execute ( );
    }
}

class LightOnCommand implements Command {
    private Light myLight;
    public LightOnCommand ( Light L) {
        myLight  =  L;
    }
    public void execute( ) {
        myLight . turnOn( );
    }
}
class LightOffCommand implements Command {
    private Light myLight;
    public LightOffCommand ( Light L) {
        myLight  =  L;
    }
    public void execute( ) {
        myLight . turnOff( );
    }
}
class FanOnCommand implements Command {
    private Fan myFan;
    public FanOnCommand ( Fan F) {
        myFan  =  F;
    }
    public void execute( ) {
        myFan . startRotate( );
    }
}
class FanOffCommand implements Command {
    private Fan myFan;
    public FanOffCommand ( Fan F) {
        myFan  =  F;
    }
    public void execute( ) {
        myFan . stopRotate( );
    }
}