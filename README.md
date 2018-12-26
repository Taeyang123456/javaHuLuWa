# 葫芦娃大战妖精

#### 操作说明
非战斗状态下，摁下Q、W、E键调整敌人阵型，L键打开记录文件并回放，空格键开始新战斗；

战斗状态下，摁下R键让葫芦娃进入暴走模式

### **葫芦大会（故事从这里开始)**
> 自从葫芦娃们把妖精们镇压于七彩山峰之下，妖精们就想方设法逃走。终于有一天，葫芦娃们都去上Java课了，妖精们趁机溜了出来。
+ 蝎子精：咋办，跑了迟早要被抓回去啊，咱打不过这七个啊！
+ 蛇精：岸上打不过，我们就逃到海里，我就不信这七个小屁孩还会游泳！
+ 蝎子精：夫人说得对！
+ 二娃(顺风耳)：.....当然会游泳啊。
> 二娃把妖精们的计划告诉了其他兄弟，兄弟们一惊！咋办？没在海里打过架啊？
+ 七娃： 慌什么，写个Java程序模拟一下不就知道怎么打了吗。
> 妙啊！现学现用！

![HuLus vs HuaiDans](/cartoon.gif "HuLuBattle")

> 于是葫芦娃们开始写Java了
-----
##### 生物类
葫芦娃和妖精们都是生物，都有生物的特性，比如攻击力和生命值。
那么就有了Crearute这个类。
```
public class Creature{
    protected int blood;
    protected int total_blood;
    protected int attack;
    protected Location location;
    ......
}
```
但是，光有属性肯定是不行的，还得有些接口，比如他们会打架？
```
public void tryAttack();
// 会打架也得会挨揍
public void sufferAttack();
```
在有了这些基本的生物所有的性质以及行为之后，葫芦娃们和妖精们就可以extends这个类了
```
public class Brother extends Creature {
    public Brother() { super(); }
    ......
}
```
妖精们也继承自这个Creature，当然他们也会有和葫芦娃不一样的行为以及属性。

##### 战场类
有了战争的参与者，当然要有个地盘给他们打架啊
```
public class BattleField {
    static final int width = 16;
    static final int height = 14;
    private static Creature[][] battlefield = new Creature[width][height];
}
```
BattleField类中存有battleFiled二维数组，每个数组上可以放一个生物，这就是最基本的战场，葫芦娃和妖精们在这个战场上进行战斗。

对```<T extends Creature>```的对象，可以将他们放在战场上，而其他的类型：

不可以

##### 合成/聚合
七个葫芦娃，一堆妖精，一个个管理起来有点麻烦，不如把他们划成两堆：
好人堆、坏人堆。

要用到葫芦娃或者妖精的时候，从堆里面找好了；要排阵型的时候，以堆为单位多好。

Hold your Object： ArrayList的使用
```
 List<Brother> brothers = new ArrayList<>();
```


初具雏形

------

### **葫芦大会 Part 2**
> 葫芦娃们写出了人物和战场，但是他们发现这些Creature不会动...
+ 大娃：敲键盘让他们动不就完事了，键盘上不是有四个方向键吗？
+ 三娃：那多捞啊，为什么不让他们自己抖动起来呢？
+ 大娃：还有这种操作？？？
> 七娃掏出了Thinking in Java.....
-------

##### 让葫芦娃们动起来，引入线程

为了让葫芦娃们能自己行动，Java设置了线程这个机制。

两种途径：
1. implements Runnable
2. extends Thread

这里我们使用第一种，让Class Brother implements Runnable这个借口，类定义处加上：
```
public class Brother extends Creature implements Runnable {...}
```
在Brother内，完成Runnable中的run函数，即指定好让葫芦娃们动起来的方法：
```
public void run() {
        while(true) {
            fieldMove(); // 葫芦娃的行走
            tryAttack(); // 打架
            try {
                sleep(sleepTime); // 走累了歇歇
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
}
```
葫芦娃们动起来之后就可以自由行走了，同样的妖精们也implements了这个借口，并实现了妖精们的行走。

但是！ 发现了一个问题，

葫芦娃和妖精们怎么和战场联系起来呢？在类中定义一个对战场的引用，引用自同一块战场。

看起来他们好像站在了同一个战场上。

但是问题又来了，要是两个葫芦娃要站在同一个位置怎么办？挤一起吗？
两个娃还好处理，要是三个娃呢？再加一个妖精？你让他们挤一起？

这样当然不好。

##### 线程对共享资源的同步问题
在Java中有多种方法对共享资源的访问进行控制，葫芦娃们选用了synchronized这个方法。
1. 每个生物想要进行移动时，都需要对战场进行勘察，看看自己能不能走这块。
2. 访问时对战场加锁，以防止选好地盘之后被别人抢了先的情况。
3. 当该生物移动到他想要走的地盘之后，再交出战场的访问所，给下一个需要的生物。

注意到，synchronized某一对象之后，只可以允许当前加锁的进程访问该对象，其余想要访问的进程需要等待。

改一下代码：

```
public void run() {
        while(true) {
            // 访问时，对战场加上一把锁；
            // 如果有别的线程在使用，等待至其用完
            synchronized (battleField) {
                fieldMove();
                tryAttack();
            }
            // 用完啦，还锁，给别人用
            try {
                sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
```
在战斗开始时，使用：
```
new Thread(.....).start()；
```
这样的语句让每个进程开始他们的生命周期，然后一切就开始自己run起来了

Perfect！

------
### **葫芦大会 Part 3**
> 葫芦娃们终于让自己的模型动起来了，但是问题来了，
怎么知道现在的线程运行地怎么样了呢？
+ 大娃：System.out 输出一下啊
+ 二娃：....
+ 四娃：我来写个图形界面吧
> 七娃又掏出了Thinking in Java...
+ 四娃：你们每人发一个自拍给我，等下显示的时候要用
+ 五娃：我们长得不都差不多吗....
---------
##### 图形界面
在javafx的强大GUI功能支持下，我们可以将葫芦娃的实时战况通过图像显示到屏幕上

在javafx的大框架下，从.fxml文件中读取各个图形化组件的属性设置，并将其显示出来。
```
<AnchorPane fx:id="root" ....>
   <children>
      <Canvas fx:id="canvas"...>
      ....
   </children>
</AnchorPane>
```


实验中用的主要是javafx下的canvas画布组件，来显示战场。

通过canvas的接口
```
getGraphicsContext2D()
```
获得可以进行canvas绘制的GraphicsContext对象，并在其上进行一系列的操作
```
context.drawImage(....);
context.clearRect(....);
context.fillRect(....);
```
canvas通过对battleFiled这一共享资源的互斥访问，获得战场的时时情况，
并将其绘制到屏幕上（获取对应生物的图片，并将绘制图片）。

战场瞬息万变，怎么样让显示出来的图像是当前的战场状况呢？

不停地刷！这是葫芦娃们能想到的办法。

于是就有了这个类的诞生：
```
public class Renovate implements Runnable {
    Canvas canvas;
    BattleField battleField;
    List<Image> imageList = new ArrayList<>();
    ...
    public Renovate(Canvas canvas, BattleField battleField) {
       this.canvas = canvas;
       this.battleField = battleField;
       ....
    }
    ...
}
```
这个类实现的run方法中，每隔特定的时间就访问战场，并将当前战场的情况刷新到屏幕上，
其中的run方法：
```
public void run() {
        while(true) {
            synchronized (canvas) {
                synchronized (battleField) {
                    if ( battlefiled怎么怎么样 ) {
                        // 画这些东西
                    } else {
                        // 画别的东西
                    }
                }
            }
            try {
                sleep(certainTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}
```
该对象的线程在程序运行的初期就被创建，并一直持续到程序的结束。
run方法中不停地重复刷新战场，或者显示别的信息(例如，打印出战争胜利方等)。

那葫芦娃们怎么操纵它呢？

##### EventHandler的使用
在main初始化时，增加对键盘输入的监听，并自定义监听事件的处理，
```
public class KeyEventHandle implements  EventHandler<KeyEvent> {
        public KeyEventHandle() { super(); }
        @Override
        public void handle(KeyEvent event) {         
            if(event.getCode() == KeyCode.SPACE) {
                // .... do something
            } else if(event.getCode() == KeyCode.Q) {
                // .... do something
            } else if(event.getCode() == KeyCode.W) {
                // .... do something
            } else if(event.getCode() == KeyCode.E) {
                // .... do something
            } 
            .........
        }
    }
```
在初始化场景时将KsyEventHandle这一监听者加入到场景中，就可以对用户的输入产生反应了。

挺好~

-----
### **葫芦大会 Part 4**
> 有了图形化界面，葫芦娃们终于写出来一个图形化战斗模拟游戏了
+ 三娃：看见没，像刚才那样子打，我们就能赢得海底大战
+ 七娃：我没看见，能不能再放一遍
+ 其他娃：.....
> 七娃沉迷于Thinking in Java，居然错过了战术分析，怎么办，写个回放？
-----
##### 回放，I/O

在每次刷新界面的时候，战场上有生物的地方记录下来，并用特定的记录格式将关键信息写入文件中；
回放的时候根据这些关键信息，得到具体的战斗情况，逐帧播放，这样就可以实现战况回放啦

```
InputStreamReader read = new InputStreamReader(new FileInputStream(file));
BufferedReader bufferedReader = new BufferedReader(read);
```
装饰器设计模式，将inputStreamReader这一对象用BufferedReader装饰起来，就可以做到有缓存的输入和输出啦！

调整刷新器Renovate的状态，进入回放状态：
```
public void run() {
        while(true) {
            synchronized (canvas) {
                if( 处于回放状态 ) {   
                    replayDrawing();
                }
                else {
                .......
```
-----

### **葫芦大会 Part 5**
> 葫芦娃们终于研究好了战术，制定了周密的计划，大家好像都胸有成语
+ 六娃：要是出现了意想不到的情况，打不过怎么办
+ 五娃：哈哈，这你不懂了吧，~~开鬼刀啊~~
> 原来葫芦娃们还留了一手！
> 战斗中摁下R键，触发葫芦娃们的技能 “鬼刀一开” ，提高葫芦娃们的移动速度，并增加攻击力。

### 结局
> 葫芦娃们把坏蛋们抓回了山下，维护了世界和平
+ 大娃：多亏了Java，让我们有了对战斗的提前准备
+ 妖精：什么？什么是Java？我们也要学！

### GAME OVER

致谢：感谢曹老师、余老师以及助教们一学期的辛勤付出！！！