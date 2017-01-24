/**
 * @author Created by MadJieJie on 2017/1/24-13:00.
 * @brief
 * @attention
 */
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