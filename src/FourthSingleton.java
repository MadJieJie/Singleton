/**
 * @author Created by MadJieJie on 2017/1/24-13:00.
 * @brief
 * @attention
 */
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