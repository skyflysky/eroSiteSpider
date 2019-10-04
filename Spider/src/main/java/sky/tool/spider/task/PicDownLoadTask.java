package sky.tool.spider.task;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import sky.tool.spider.entity.PicUrl;
import sky.tool.spider.service.PicUrlService;
import sky.tool.spider.tool.PicDownloadTool;

@Component
public class PicDownLoadTask implements ApplicationRunner
{
	private Logger logger = Logger.getLogger(getClass());
	
	@Value("${pic.storage}")
	private String storage;
	
	@Value("${work.mode}")
	private String workMode;
	
	@Value("${download.mode}")
	private String downloadMode;
	
	@Autowired
	PicDownloadTool tool;
	
	@Autowired
	PicUrlService puService;
	
	@Override
	public void run(ApplicationArguments args) throws Exception
	{
		if(workMode.equals("download") && downloadMode.equals("pic"))
		{
			doDownload();
		}
	}

	private void doDownload()
	{
		logger.info("开始下载");
		List<PicUrl> unloadList = puService.getUnLoad();
		int count = unloadList.size();
		for(int i = 0 ; i < count ; i ++)
		{
			try
			{
				//Thread.sleep(1000);
				
				String url = unloadList.get(i).getUrl();
				String[] storageTree = url.split("/");
				
				File targetFile = makeDir(new File(storage) , storageTree , 2);
				logger.info("开始下载" + unloadList.get(i).getId() + "，它是第" + (i + 1)  + "/" + count);
				tool.download(url,targetFile , unloadList.get(i).getId());
			} 
			catch (Exception e) 
			{
				logger.error("未知错误" , e);
				continue;
			}
		}
		logger.info("下载完成");
	}
	
	private File makeDir(File fatherFile, String[] storageTree, int i)
	{
		File node = new File(fatherFile , storageTree[i]);
		if(i == storageTree.length - 1)
		{
			if(!node.exists())
			{
				try
				{
					node.createNewFile();
				} 
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			return node;
		}
		else
		{
			if(!node.exists())
			{
				node.mkdir();
			}
			return makeDir(node, storageTree, i + 1);
		}
	}

	
	
}