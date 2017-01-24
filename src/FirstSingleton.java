/**
 * @author Created by MadJieJie on 2017/1/24-13:00.
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
