package sky.tool.spider.tool;

import java.io.File;
import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import sky.tool.spider.service.PicUrlService;

@Component
@EnableAsync
public class PicDownloadTool
{
	Logger logger = Logger.getLogger(getClass());

	@Autowired
	PicUrlService puService;

	@Async
	public void download(String targetUrl, File inFile, Long id)
	{
		logger.debug("线程:'" + Thread.currentThread().getName() + "'开始下载id:'" + id + "'");
		try
		{
			ImageDownloader imageDownloader = new ImageDownloader();
			imageDownloader.initApacheHttpClient();
			imageDownloader.fetchContent(targetUrl, inFile);
		} 
		catch (java.net.SocketTimeoutException e) 
		{
			logger.error("超时，下载" + id + "失败");
		}
		catch (javax.net.ssl.SSLHandshakeException e) 
		{
			logger.error("远端服务器关闭连接， 下载" + id + "失败");
		}
		catch (javax.net.ssl.SSLProtocolException e) 
		{
			logger.error("ssl原因，下载" + id + "失败");
		}
		catch (java.net.SocketException e) 
		{
			logger.error("socket问题，下载" + id + "失败");
		} 
		catch (ClientProtocolException e)
		{
			logger.error("Client问题，下载" + id + "失败");
		} catch (IOException e)
		{
			logger.error("IO问题，下载" + id + "失败");
			e.printStackTrace();
		}
		catch (java.lang.IllegalStateException e) 
		{
			logger.error("ConnetionPool原因，下载" + id + "失败");
		}
		catch (Exception e) 
		{
			logger.error("未知原因，下载" + id + "失败" , e);
		}
		finally 
		{
			if(inFile.length() > 1024)
			{
				puService.downloadMark(id, inFile.getAbsolutePath());
				logger.info("下载" + id + "成功");
			}
			else
			{
				inFile.delete();
			}
		}
	}
}
