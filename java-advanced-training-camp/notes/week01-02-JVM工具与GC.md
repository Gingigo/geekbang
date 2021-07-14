# 02、JVM 核心技术--工具与GC

## 一、内置命令行工具

- 应用相关的命令

  - `java` : 应用的启动程序
  - `javac` ： JDK 内置编译工具
  - `javap` ： 反编译 class文件的工具
  - `javadoc` ： 根据 java 代码和标准注释，自动生成相关的 API 说明文档

- JVM 相关

  - `jps、jinfo` ： 查看 java 进程

    - `jps -lmvV` : 查看启动的参数

  - `jstat` ：查看 JVM 内部 GC 相关信息

    ![image-20210529113925856](images/week01/image-20210529113925856.png)

    - 例子 ： `jstat -gcutil -t -h 10 pid 10s 20`

      ![image-20210529114941184](images/week01/image-20210529114941184.png)

    - 例子：`jstat -gc pid 1000 1000`

      ![image-20210529115313469](images/week01/image-20210529115313469.png)

  - `jmap` ： 查看 heap 或者类占用空间统计

    - `jmap -heap pid` :  打印堆内存的配置信息和试用信息
    - `jmap -histo pid` ：看哪些类占用的空间最多
    - `jmap -dump:format=b,file=pid.hprof pid` : 到处堆内存数据

  - `jstack`： 查看线程信息

    - `jstack -l pid` ： （正常连接模式）长列表模式. 将线程相关的 locks 信息一起输出，比如持有的锁，等待的锁
    - `jstack -F pid`：（强制连接模式）在线程被 hung 住的时候 可以进行强制连接
    - `jstack -m pid`: （混合模式）将 Java 帧和 native 帧一起输出

  - `jcmd`：执行 JVM 相关分析命令（整合命令）

    - `jcmd pid VM.version` :  打印应用的虚拟机版本
    - `jcmd pid VM.flags`：打印 JVM 的配置参数
    - `jcmd  pid VM.command_line`：启动的命令行
    - `jcmd pid VM.system_properties` : 打印系统参数
    - `jcmd pid Thread.print`： 打印线程信息
    - `jcmd pid GC.class_histogram`：看哪些类占用的空间最多
    - `jcmd pid GC.heap_info`：打印堆信息

  - `jrunscript/jjs` ：执行 js 命令

## 二、图形化工具

- jconsole

  - 启动命令 ： `jconsole`

    ![image-20210529141246774](images/week01/image-20210529141246774.png)

    ![image-20210529141357936](images/week01/image-20210529141357936.png)

  ![image-20210529141704679](images/week01/image-20210529141704679.png)

  ![](images/week01/image-20210529141733944.png)

  ![image-20210529141805726](images/week01/image-20210529141805726.png)

- jvisualvm

  - 启动命令：`jvisualvm`

  ![image-20210529145724048](images/week01/image-20210529145724048.png)

  ![image-20210529151856609](images/week01/image-20210529151856609.png)

  ![image-20210529151932893](images/week01/image-20210529151932893.png)

  ​				![image-20210529151945508](images/week01/image-20210529151945508.png)

- jmc

  ​	![image-20210529152626236](images/week01/image-20210529152626236.png)

![image-20210529152635481](images/week01/image-20210529152635481.png)

![image-20210529152646633](images/week01/image-20210529152646633.png)

![image-20210529152657113](images/week01/image-20210529152657113.png)

![image-20210529152705523](images/week01/image-20210529152705523.png)

![image-20210529152713578](images/week01/image-20210529152713578.png)

![image-20210529152723077](images/week01/image-20210529152723077.png)

## 三、GC 的背景与一般原理

### 为什么会有GC？

本质上是内存资源的有限性，因此需要大家共享使用，手工申请，手动释放。

### GC 原理

#### 引用计数

![image-20210529161623180](images/week01/image-20210529161623180.png)

#### 标记清除算法（Mark and Sweep）

![image-20210529161758900](images/week01/image-20210529161758900.png)

#### 整理算法

比标记算法多了一步，需要进行数据整理压缩

#### 堆分代的思想

![image-20210529162049028](images/week01/image-20210529162049028.png)

![image-20210529162235705](images/week01/image-20210529162235705.png)

为什么老年代是通过移动的方式（类似剪切），而新生代是复制呢？

- 移动是有两个动作，复制+删除原有的。所以老年代在GC中的时候是移动，这样保证了移动后原有的位置不需要再次清除
- 而年轻代进行 YGC 的时候，Eden 区 和 存活区 复制到另个存活区，然后把之前的两个区全部清空，没必要用移动这种方式

#### 那些对象可以作为 GC Roots？

1. 当前正在执行的方法里的局部变量和输入参数
2. 活动线程（Active threads）
3. 所有类的静态字段（static field）
4. JNI 引用

进行 GC 的时候会 STW，堆中的数据全部暂停，提供给 GC 做标记。此阶段暂停的时间，与堆内存大小,对象的总数没有直接关系，而是由存活对象（alive objects）的数量来决定。所以增加堆内存的大小并不会直接影响标记阶段占用的时间。

## 四、串行 GC/并行 GC

### 串行GC （Serial GC）/ ParNewGC

串行：不能同一时刻同时做多件事情，而且一件事情必须从头做到底，才能做其他事情

并发：不能同一时刻同时做多件事情，但是多件事情可以拆分成多个环境，交替执行

并行：能够同一时刻处理多件事情

![image-20210529164411527](images/week01/image-20210529164411527.png)

Serial GC：串行

ParNewGC：并发，Serial GC 的多线程版

### 并行 GC（Parallel GC）

![image-20210529170216397](images/week01/image-20210529170216397.png)

##  五、CMS GC/G1 GC

### CMS GC（Mostly Concurrent Mark and Sweep Garbage Collector）

![image-20210529170745836](images/week01/image-20210529170745836.png)

#### CMS GC 六个阶段

![image-20210529171026452](images/week01/image-20210529171026452.png)

![image-20210529171100820](images/week01/image-20210529171100820.png)

![image-20210529171501488](images/week01/image-20210529171501488.png)

![image-20210529171549123](images/week01/image-20210529171549123.png)

![image-20210529171628971](images/week01/image-20210529171628971.png)

![image-20210529171746467](images/week01/image-20210529171746467.png)

### G1 GC

![image-20210529172318355](images/week01/image-20210529172318355.png)

![image-20210529172410779](images/week01/image-20210529172410779.png)

#### G1 GC--配置参数

![image-20210529172735591](images/week01/image-20210529172735591.png)

![image-20210529173027555](images/week01/image-20210529173027555.png)

#### G1 GC 的处理步骤 

![image-20210529173120311](images/week01/image-20210529173120311.png)

![image-20210529173215484](images/week01/image-20210529173215484.png)

![image-20210529173611890](images/week01/image-20210529173611890.png)

![image-20210529174403763](images/week01/image-20210529174403763.png)

#### G1 GC 的注意事项

![image-20210529174733758](images/week01/image-20210529174733758.png)

### 各个GC 的对比

![image-20210529174937717](images/week01/image-20210529174937717.png)

### 常用的 GC 组合

![image-20210529175012094](images/week01/image-20210529175012094.png)

### GC 如何选择

![image-20210529175716786](images/week01/image-20210529175716786.png)

## ZGC/Shenandoah GC

### ZGC

![image-20210529175803323](images/week01/image-20210529175803323.png)

### ShennandoahGC

![image-20210529175938058](images/week01/image-20210529175938058.png)

### ShennandoahGC 与其他 GC 的 STW 比较

![image-20210529180023612](images/week01/image-20210529180023612.png)

## 总结

![image-20210529180540669](images/week01/image-20210529180540669.png)

![image-20210529180711906](images/week01/image-20210529180711906.png)