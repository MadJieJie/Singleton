/**
 * @author Created by MadJieJie on 2017/1/22-11:53.
 * @brief
 * @attention
 */
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
