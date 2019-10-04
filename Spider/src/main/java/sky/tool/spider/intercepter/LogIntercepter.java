package sky.tool.spider.intercepter;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

public class LogIntercepter	implements HandlerInterceptor
{
	private Logger logger = Logger.getLogger(getClass());
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception
	{
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception
	{
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse arg1, Object arg2) throws Exception
	{
		logRequestLog(request);
		return true;
	}
	
	private void logRequestLog(HttpServletRequest request)
	{
		Map<String, String[]> origeinalMap = request.getParameterMap();
		Map<String, Object> targetMap = new LinkedHashMap<>();
		for (String key : origeinalMap.keySet())
		{
			String[] array = origeinalMap.get(key);
			if(array.length > 1)
				targetMap.put(key, origeinalMap.get(key));
			else
				targetMap.put(key, array[0]);
		}
		JSONObject ob = new JSONObject(targetMap);
		ob.put("QueryString", request.getQueryString());
		ob.put("RemoteHost", getIpAddr(request));
		logger.info("控制层请求日志 请求接口:'" + request.getServletPath() + "' 内容:'" + ob.toJSONString() +"'");
	}
	
	private String getIpAddr(HttpServletRequest request)
	{
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("CLIENTIP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getRemoteAddr();
		}
		return ip;
	}

}
