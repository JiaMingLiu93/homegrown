package com.demo.test;

/**
 * @author jam
 * @description
 * @create 2018-05-14
 **/
public class TestThreadLocal {
    TestThreadLocalMap map;
    TestThreadLocalMapNotStatic mapNotStatic;
    public TestThreadLocal(){
        map = new TestThreadLocalMap();
        mapNotStatic = new TestThreadLocalMapNotStatic();
    }
    public void test(){
        System.out.println(map.getName());
        System.out.println(mapNotStatic.getName());

    }
    static class TestThreadLocalMap{
        private String name;
        private String getName(){
            return "name";
        }
    }

    class TestThreadLocalMapNotStatic{
        private String getName(){
            return "not static";
        }
    }

    public static void main(String[] args) {
        new TestThreadLocal().test();
        TestThreadLocalMap testThreadLocalMap = new TestThreadLocalMap();
        testThreadLocalMap.getName();
    }
}
