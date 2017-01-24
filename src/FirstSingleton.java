/**
 * @author Created by MadJieJie on 2017/1/22-11:39.
 * @brief
 * @attention
 */
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
