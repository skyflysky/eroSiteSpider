package sky.tool.spider.utils;

import java.text.SimpleDateFormat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringUtil implements ApplicationContextAware
{
	private static ApplicationContext applicationContext = null;

	/**
	 * 获取applicationContext
	 */
	public static ApplicationContext getApplicationContext()
	{
		return applicationContext;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
	{
		if (SpringUtil.applicationContext == null)
		{
			SpringUtil.applicationContext = applicationContext;
		}
	}

	/**
	 * 通过name获取 Bean
	 */
	public static Object getBean(String name)
	{
		return getApplicationContext().getBean(name);
	}
	
	public static Integer getWebIdFromUrl(String url , String ngateMark) throws NumberFormatException
	{
		Integer webid = Integer.valueOf(url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf(".")));
		if(!url.contains(ngateMark))
		{
			webid = Math.negateExact(webid);
		}
		return webid;
	}
	
	public static String attrGetter(String HTMLElement , String attrName)
	{
		String elemtentsString = HTMLElement.substring(HTMLElement.indexOf("<") + 1 , HTMLElement.indexOf(">"));
		String[] elementsArray = elemtentsString.split(" ");
		assert elementsArray.length >= 2;
		for(int i = elementsArray.length - 1 ; i >= 1 ; i --)
		{
			String element = elementsArray[i];
			String attr = element.substring(0 , element.indexOf("="));
			if(attr.equals(attrName))
			{
				return element.substring(element.indexOf("=") + 2 , element.length() - 1);
			}
		}
		return "";
	}
	
	public static String attGetter(String HtmlElement , String attrName)
	{
		Document document = Jsoup.parse(HtmlElement);
		Elements elements = document.getAllElements();
		return elements.get(elements.size() - 1).attr(attrName);
	}
	
	public static String innerGetter(String HTMLElement)
	{
		return HTMLElement.substring(HTMLElement.indexOf(">") + 1, HTMLElement.lastIndexOf("<"));
	}
	
	public static SimpleDateFormat ymdFomat()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf;
	}
}
