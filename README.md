#什么是单例模式？
##定义
####单例（singleton）：指仅被实例化一次的类，即在JVM中只存在一个实例，常被用于代表那些本质上唯一存在的系统组件，如窗口管理器或文件系统 。


----------


#适用情况&益处

####1.当某些类创建十分频繁时，对于大型的实例（对象），节省了很大一笔资源开销；
####2.省去了new操作符，减少了垃圾收回器的资源消耗；
####3.有些核心的操作类不能创建第二个，防止出现安全和混乱问题。


----------


#程序解析
##饿汉式
####饿汉式是较为简单的方式，将构造器保持为私有，并导出私有成员。
###利弊
####在应用启动时就将单例进行实例化。适用于实例较小的情况，如果实例较大（占用内存较多），则会对启动速度有影响。

> 实现的思路

####1.将该类的引用类型变量添加private、static和final修饰符，便于静态方法调用和防止改变，并实例化；
####2.将构造器声明私有；
####3.通过工厂方法导出成员;
####4.添加readResolve()方法构成可序列化类。

```
public class FirstSingleton
{
	private static final FirstSingleton INSTANCE = new FirstSingleton();
	
	private FirstSingleton()
	{
	}
	
	public static FirstSingleton getInstance()
	{
		return INSTANCE;
	}
	
	public Object readResolve()
	{
		return INSTANCE;
	}
}

```

##懒汉式
####懒汉式与饿汉式最大的区别在于实例化操作，前者是当调用时才进行实例化操作，后者是启动程序便开始实例化操作。
###利弊
####懒汉式实现了使用静态方法时才实例化，如果单例使用的次数不多且提供的功能复杂，避免了JVM启动消耗大量的资源，但静态方法存在多线程安全问题。
> 实现的思路

####1.将该类的引用类型变量添加private、static修饰符；
####2.工厂方法添加逻辑判断引用变量是否为NULL，是则实例化变量，反之不执行。

```
public class SecondSingleton
{
	private static SecondSingleton INSTANCE = null;
	
	private SecondSingleton()
	{
	}
	
	public static SecondSingleton getInstance()
	{
		if(INSTANCE ==null)
			INSTANCE = new SecondSingleton();
		return INSTANCE;
	}
	
	public Object readResolve()
	{
		return getInstance();
	}
}
```


----------


##懒汉式同步锁
> 实现的思路
####1.将该类的引用类型变量添加private、static和volatile修饰符；
####2.将构造器声明私有；
####3.工厂方法添加逻辑判断引用变量是否为NULL，是则实例化变量，反之不执行。

```
public class ThirdSingleton
{
	private static volatile ThirdSingleton INSTANCE = null;
	
	private static ThirdSingleton getInstance()
	{
		if(INSTANCE == null)
		{
			synchronized( INSTANCE )
			{
				if(INSTANCE == null)
					INSTANCE = new ThirdSingleton();
			}
		}
		return INSTANCE;
	}
	
	public Object readResolve()
	{
		return getInstance();
	}
	
}
```
####注意：volatile关键字有两层语义，第一层可见性，指的是在一个线程中对该变量的修改会马上由工作内存（Work Memory）写回主内存（Main Memory），所以会马上反应在其它线程的读取操作中。顺便一提，工作内存和主内存可以近似理解为实际电脑中的高速缓存和主存，工作内存是线程独享的，主存是线程共享的；volatile的第二层语义是禁止指令重排序优化，大家知道我们写的代码（尤其是多线程代码），由于编译器优化，在实际执行的时候可能与我们编写的顺序不同。编译器只保证程序执行结果与源代码相同，却不保证实际指令的顺序与源代码相同。这在单线程看起来没什么问题，然而一旦引入多线程，这种乱序就可能导致严重问题。第二条语义禁止指令重排优化直到jdk1.5以后才支持。

----------

#静态内部类
####以上两种方式还是挺麻烦的， 我们可以利用JVM的类加载机制去实现。在很多情况下JVM已经为我们提供了同步控制，比如：  

 - 在static{}区块中初始化的数据
 - 访问final字段时

####因为在**JVM进行类加载**的时候他会保证数据是同步的，我们可以这样实现：

####采用内部类，在这个内部类里面去创建对象实例。这样的话，只要应用中不使用内部类则 JVM 就不会去加载这个单例类，也就不会创建单例对象，从而实现懒汉式的延迟加载和线程安全。
> 实现的思路

####1.创建静态内部类
####2.创建静态方法返回静态内部类的单例。

```
public class FourthSingleton
{
	private static class SingletonHolder
	{
		public static FourthSingleton INSTANCE = new FourthSingleton();
	}
	
	private FourthSingleton ()
	{
	}
	
	public static FourthSingleton newInstance ()
	{
		return SingletonHolder.INSTANCE;
	}
	
	public Object readResolve ()
	{
		return SingletonHolder.INSTANCE;
	}
}
```
#上述四种方法存在共同问题
####1.都需额外的工作(Serializable、transient、readResolve())来实现序列化，否则每次反序列化一个序列化的对象实例时都会创建一个新的实例。
####2.都存在反射攻击的安全问题，享有特权的客户端可以借AccessibleObject.setAccessible方法通过反射机制调用私有构造使得创建多个实例，必须修改构造器，让其在创建第二个实例的实例的时候抛出异常。

#单元素枚举
####创建单个元素的枚举类实现单例。
###利弊
####简洁和无偿地实现线程安全、序列化机制和多次实例化安全，单元素枚举已经成为实现单例的最佳方法。
####但是在Android中却不推荐这种用法，在Android官网Managing Your App’s Memory中有这样一段话：

####Enums often require more than twice as much memory as static constants. You should strictly avoid using enums on Android.

####意思大致：枚举类型内存占用上是静态变量的两倍以上，应该尽可能的避免枚举类型在Android中。

####个人认为，如果不大量采用枚举类型或枚举单例过于庞大，性能的影响是很微小的。

```
public enum FifthSingleton
{
	INSTANCE;
	public static FifthSingleton getInstance()
	{
		return INSTANCE;
	}
}

```