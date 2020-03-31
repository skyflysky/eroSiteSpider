package sky.tool.spider.tool;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sky.tool.spider.entity.DownloadEntity;
import sky.tool.spider.service.PictureService;
import sky.tool.spider.task.QueueExecutor;

@Component
public class PicDownloadTool extends QueueExecutor<DownloadEntity>
{
	private static final long serialVersionUID = 4452284700284789399L;

	Logger logger = Logger.getLogger(PicDownloadTool.class);

	@Autowired
	PictureService pictureService;

	public void download(String targetUrl, File inFile, Long id)
	{
		logger.info("开始下载id:'" + id + "'");
		try
		{
			pictureService.tryDownloadPicUrl(id);
			ImageDownloader imageDownloader = new ImageDownloader();
			imageDownloader.initApacheHttpClient();
			imageDownloader.fetchContent(targetUrl, inFile);
		} 
		catch (java.lang.IllegalArgumentException e) 
		{
			logger.error("参数错误，下载" + id + "失败");
		}
		catch (java.net.SocketTimeoutException e) 
		{
			logger.error("超时，下载" + id + "失败");
			inFile.delete();
		}
		catch (javax.net.ssl.SSLHandshakeException e) 
		{
			logger.error("远端服务器关闭连接， 下载" + id + "失败");
			inFile.delete();
		}
		catch (javax.net.ssl.SSLProtocolException e) 
		{
			logger.error("ssl原因，下载" + id + "失败");
			inFile.delete();
		}
		catch (java.net.SocketException e) 
		{
			logger.error("socket问题，下载" + id + "失败");
			inFile.delete();
		} 
		catch (ClientProtocolException e)
		{
			logger.error("Client问题，下载" + id + "失败");
			inFile.delete();
		} 
		catch (IOException e)
		{
			inFile.delete();
			if(e.getLocalizedMessage().contains("error code = 404"))
			{
				logger.error("404问题，下载" + id + "失败");
			}
			else
			{
				e.printStackTrace();
			}
		}
		catch (java.lang.IllegalStateException e) 
		{
			logger.error("ConnetionPool原因，下载" + id + "失败");
			inFile.delete();
		}
		catch (Exception e) 
		{
			logger.error("未知原因，下载" + id + "失败" , e);
			inFile.delete();
		}
		finally 
		{
			if(inFile.exists() && inFile.length() > 1024)
			{
				if(pictureService.markPicUrlDownloaded(id, inFile.getAbsolutePath()))
				{
					logger.info("下载" + id + "成功");
				}
				else
				{
					inFile.delete();
					logger.info(id + "失败");
				}
			}
			else
			{
				inFile.delete();
			}
		}
	}

	@Override
	public void runMission(Optional<DownloadEntity> opt) throws Exception
	{
		download(opt.get().getUrl(), opt.get().getFile(), opt.get().getId());
	}
	
	public PicDownloadTool(int thredCount , long timeOut , int queueSizeTimes)
	{
		super(thredCount, timeOut, queueSizeTimes);
	}
	
	public PicDownloadTool()
	{
		super();
	}
}
