package com.gingo.training.camp.week01.work;

/**
 * 第一周 作业 1
 * 自己写一个简单的 Hello.java，里面需要涉及基本类型，四则运行，if 和 for，然后自己分析一下对应的字节码
 */
public class Hello {
    public static void main(String[] args) {
        int a = 1;
        int b = 5;
        String result = "hello world:";
        if (a + b > 2) {
            for (int i = a; i < b; i++) {
                result += i;
            }
        }
        System.out.println(result);
    }
}

/*
$ javap -c -verbose Hello.class
Classfile /E:/DevelopProject/Gin/geekbang/java-advanced-training-camp/target/classes/com/gingo/training/camp/week01/Hello.class
  Last modified 2021-5-22; size 929 bytes
  MD5 checksum f43c965966eb3966cb467c49e46f7e97
  Compiled from "Hello.java"
public class com.gingo.training.camp.week01.Hello
  minor version: 0
  major version: 49
  flags: ACC_PUBLIC, ACC_SUPER
Constant pool:
   #1 = Methodref          #11.#31        // java/lang/Object."<init>":()V
   #2 = String             #32            // hello world:
   #3 = Class              #33            // java/lang/StringBuilder
   #4 = Methodref          #3.#31         // java/lang/StringBuilder."<init>":()V
   #5 = Methodref          #3.#34         // java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
   #6 = Methodref          #3.#35         // java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
   #7 = Methodref          #3.#36         // java/lang/StringBuilder.toString:()Ljava/lang/String;
   #8 = Fieldref           #37.#38        // java/lang/System.out:Ljava/io/PrintStream;
   #9 = Methodref          #39.#40        // java/io/PrintStream.println:(Ljava/lang/String;)V
  #10 = Class              #41            // com/gingo/training/camp/week01/Hello
  #11 = Class              #42            // java/lang/Object
  #12 = Utf8               <init>
  #13 = Utf8               ()V
  #14 = Utf8               Code
  #15 = Utf8               LineNumberTable
  #16 = Utf8               LocalVariableTable
  #17 = Utf8               this
  #18 = Utf8               Lcom/gingo/training/camp/week01/Hello;
  #19 = Utf8               main
  #20 = Utf8               ([Ljava/lang/String;)V
  #21 = Utf8               i
  #22 = Utf8               I
  #23 = Utf8               args
  #24 = Utf8               [Ljava/lang/String;
  #25 = Utf8               a
  #26 = Utf8               b
  #27 = Utf8               result
  #28 = Utf8               Ljava/lang/String;
  #29 = Utf8               SourceFile
  #30 = Utf8               Hello.java
  #31 = NameAndType        #12:#13        // "<init>":()V
  #32 = Utf8               hello world:
  #33 = Utf8               java/lang/StringBuilder
  #34 = NameAndType        #43:#44        // append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
  #35 = NameAndType        #43:#45        // append:(I)Ljava/lang/StringBuilder;
  #36 = NameAndType        #46:#47        // toString:()Ljava/lang/String;
  #37 = Class              #48            // java/lang/System
  #38 = NameAndType        #49:#50        // out:Ljava/io/PrintStream;
  #39 = Class              #51            // java/io/PrintStream
  #40 = NameAndType        #52:#53        // println:(Ljava/lang/String;)V
  #41 = Utf8               com/gingo/training/camp/week01/Hello
  #42 = Utf8               java/lang/Object
  #43 = Utf8               append
  #44 = Utf8               (Ljava/lang/String;)Ljava/lang/StringBuilder;
  #45 = Utf8               (I)Ljava/lang/StringBuilder;
  #46 = Utf8               toString
  #47 = Utf8               ()Ljava/lang/String;
  #48 = Utf8               java/lang/System
  #49 = Utf8               out
  #50 = Utf8               Ljava/io/PrintStream;
  #51 = Utf8               java/io/PrintStream
  #52 = Utf8               println
  #53 = Utf8               (Ljava/lang/String;)V
{
  public com.gingo.training.camp.week01.Hello();
    descriptor: ()V
    flags: ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: return
      LineNumberTable:
        line 7: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       5     0  this   Lcom/gingo/training/camp/week01/Hello;

  public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=5, args_size=1
         0: iconst_1
         1: istore_1
         2: iconst_5
         3: istore_2
         4: ldc           #2                  // String hello world:
         6: astore_3
         7: iload_1
         8: iload_2
         9: iadd
        10: iconst_2
        11: if_icmple     49
        14: iload_1
        15: istore        4
        17: iload         4
        19: iload_2
        20: if_icmpge     49
        23: new           #3                  // class java/lang/StringBuilder
        26: dup
        27: invokespecial #4                  // Method java/lang/StringBuilder."<init>":()V
        30: aload_3
        31: invokevirtual #5                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        34: iload         4
        36: invokevirtual #6                  // Method java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        39: invokevirtual #7                  // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
        42: astore_3
        43: iinc          4, 1
        46: goto          17
        49: getstatic     #8                  // Field java/lang/System.out:Ljava/io/PrintStream;
        52: aload_3
        53: invokevirtual #9                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
        56: return
      LineNumberTable:
        line 9: 0
        line 10: 2
        line 11: 4
        line 12: 7
        line 13: 14
        line 14: 23
        line 13: 43
        line 17: 49
        line 18: 56
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
           17      32     4     i   I
            0      57     0  args   [Ljava/lang/String;
            2      55     1     a   I
            4      53     2     b   I
            7      50     3 result   Ljava/lang/String;
}
SourceFile: "Hello.java"
 */
