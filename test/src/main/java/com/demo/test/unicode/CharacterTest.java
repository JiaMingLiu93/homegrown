package com.demo.test.unicode;

/**
 * @author youyu
 * @date 2020/4/26 6:02 PM
 */
public class CharacterTest {
    public static void main(String[] args) {
        //BMP,U+0000~U+FFFF,code point
        System.out.println("code point of \\uD840 is "+Integer.valueOf(String.valueOf('\uD840').codePointAt(0)));
        System.out.println("value of 0xD840 is "+ 0xD840);
        System.out.println(Character.isLetter('\uD840'));

        System.out.println(Character.isLetter('\uD700'));

        System.out.println("Unicode code point 0x10437 is letter: "+Character.isLetter(0x10437));
        System.out.println("name of Unicode code point 0x10437 is: "+Character.getName(0x10437));
        System.out.println("type of Unicode code point 0x10437 is: "+Character.getType(0x10437));
        System.out.println("chars of Unicode code point 0x10437 is: first->"+ (int) Character.toChars(0x10437)[0] +",second->"+(int) Character.toChars(0x10437)[1]);

        System.out.println("surrogate of Unicode code point 0x10437 is: low->"+Integer.toHexString((int) Character.lowSurrogate(0x10437))+",high->"+Integer.toHexString((int)Character.highSurrogate(0x10437)));

        System.out.println(String.valueOf('\u2f81'));

        System.out.println(String.valueOf('\u8BA1').getBytes().length);

        System.out.println(String.valueOf('\u8BA1'));
        System.out.println(String.valueOf('\u0061').codePointAt(0));

        System.out.println(String.valueOf(0x0061));

        Integer codePoint = Character.codePointAt(new char[]{'\u8BA1'}, 0);
        System.out.println(Integer.toHexString(codePoint));



        System.out.println('\u263a');
        System.out.println('\uFFFF');

        System.out.println(0x1 << 0);
    }
}
