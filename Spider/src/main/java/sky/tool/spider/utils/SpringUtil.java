package sky.tool.spider.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import sky.tool.spider.constant.SukebeiType;

@Component
public class SpringUtil implements ApplicationContextAware
{
	private static ApplicationContext applicationContext = null;

	private static Logger logger = Logger.getLogger(SpringUtil.class);
	
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

	public static Integer getWebIdFromUrl(String url, String ngateMark) throws NumberFormatException
	{
		Integer webid = Integer.valueOf(url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf(".")));
		if (!url.contains(ngateMark))
		{
			webid = Math.negateExact(webid);
		}
		return webid;
	}

	public static String attrGetter(String HTMLElement, String attrName)
	{
		String elemtentsString = HTMLElement.substring(HTMLElement.indexOf("<") + 1, HTMLElement.indexOf(">"));
		String[] elementsArray = elemtentsString.split(" ");
		assert elementsArray.length >= 2;
		for (int i = elementsArray.length - 1; i >= 1; i--)
		{
			String element = elementsArray[i];
			String attr = element.substring(0, element.indexOf("="));
			if (attr.equals(attrName))
			{
				return element.substring(element.indexOf("=") + 2, element.length() - 1);
			}
		}
		return "";
	}

	/**
	 * jsoup获取html元素中指定的属性值
	 * 
	 * @param HtmlElement
	 *            html元素的字符串
	 * @param attrName
	 *            属性名
	 * @return
	 */
	public static String attGetter(String HtmlElement, String attrName)
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

	public static boolean isPictureSideways(File file) throws IOException
	{
		FileInputStream fis = new FileInputStream(file);
		BufferedImage bi = ImageIO.read(fis);
		boolean result = bi.getWidth() > bi.getHeight();
		fis.close();
		return result;
	}

	public static Double getPictureRate(File file) throws IOException
	{
		FileInputStream fis = new FileInputStream(file);
		BufferedImage bi = ImageIO.read(fis);
		BigDecimal result = new BigDecimal(bi.getWidth()).divide(new BigDecimal(bi.getHeight()), 5,
				BigDecimal.ROUND_HALF_UP);
		fis.close();
		return result.doubleValue();
	}

	public static byte[] hexTobytes(String hex) throws StringIndexOutOfBoundsException , NumberFormatException
	{
		if (hex == null || hex.length() < 1)
		{
			return null;
		}
		else
		{
			byte[] result = new byte[hex.length() / 2];
			int j = 0;
			for (int i = 0; i < hex.length(); i += 2)
			{
				result[j++] = (byte) Integer.parseInt(hex.substring(i, i + 2), 16);
			}
			return result;
		}
	}
	
	public static SukebeiType getSukebeiType(String url)
	{
		switch (url)
		{
			case "/search/c_1_1_k_0":
				return SukebeiType.ANIME;
			case "/search/c_1_2_k_0":
				return SukebeiType.DOUJINSHI;
			case "/search/c_1_3_k_0":
				return SukebeiType.GAMES;
			case "/search/c_1_4_k_0":
				return SukebeiType.MANGA;
			case "/search/c_1_5_k_0":
				return SukebeiType.PICTURES;
			case "/search/c_2_6_k_0":
				return SukebeiType.PHOTO;
			case "/search/c_2_7_k_0":
				return SukebeiType.VIDEO;
		}
		return null;
	}
	
	public static String getFileName(String magnet)
	{
		String fileName = null;
		int indexdn = magnet.indexOf("dn=");
		if(indexdn > -1)
		{
			try
			{
				magnet = magnet.substring(indexdn + 3);
				int indexand = magnet.indexOf("&");
				if(indexand > -1)
				{
					fileName = URLDecoder.decode(magnet.substring(0 ,indexand) , "utf-8");
				}
				else
				{
					fileName = URLDecoder.decode(magnet , "utf-8");
				}
			}
			catch (UnsupportedEncodingException e)
			{
				logger.info("error" , e);
			}
		}
		return fileName;
	}
}
