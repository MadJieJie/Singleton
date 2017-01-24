/**
 * @author Created by MadJieJie on 2017/1/23-12:41.
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
			synchronized( ThirdSingleton.class )
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
