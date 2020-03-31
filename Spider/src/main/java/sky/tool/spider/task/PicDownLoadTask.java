package sky.tool.spider.task;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import sky.tool.spider.entity.DownloadEntity;
import sky.tool.spider.entity.PicUrl;
import sky.tool.spider.service.PictureService;
import sky.tool.spider.tool.PicDownloadTool;
@ConditionalOnProperty(prefix="work" , name = "mode" ,havingValue = "download")
@Component
public class PicDownLoadTask extends AbstractTask
{
	@Resource(name="de")
	PicDownloadTool tool;
	
	@Autowired
	PictureService pictureService;
	
	@Value("${de.timeout}")
	Long timeout;
	
	boolean isWork()
	{
		return "pic".equals(downloadMode);
	}

	void doWork()
	{
		logger.info("开始下载");
		List<PicUrl> unloadList = pictureService.getUnLoadPicUrl();
		Set<Long> errorId = new HashSet<>();
		int count = unloadList.size();
		tool.start();
		for(int i = 0 ; i < count ; i ++)
		{
			try
			{
				//Thread.sleep(1000);
				
				String url = unloadList.get(i).getUrl().replaceAll("\n", "");
				String[] storageTree = url.split("/");
				
				File targetFile = makeDir(new File(storage) , storageTree , 2);
				logger.info("将" + unloadList.get(i).getId() + "添加到队列，它是第" + (i + 1)  + "/" + count + "\t它是第" + unloadList.get(i).getReTryCount() + "次尝试下载");
				//tool.download(url,targetFile , unloadList.get(i).getId());
				tool.add(Optional.of(new DownloadEntity(url, targetFile, unloadList.get(i).getId())));
			} 
			catch (java.lang.ArrayIndexOutOfBoundsException e) 
			{
				logger.error("数组下标越界",e);
				errorId.add(unloadList.get(i).getId());
				continue;
			}
			catch (IOException e) 
			{
				if(!e.getLocalizedMessage().contains("文件名、目录名"))
				{
					logger.error("未知IO错误" ,e);
				}
				else
				{
					logger.error("尝试创建本地文件id:" + unloadList.get(i).getId() +"时出错");
					errorId.add(unloadList.get(i).getId());
				}
			}
			catch (Exception e) 
			{
				logger.error("未知错误" , e);
				continue;
			}
		}
		try
		{
			while(!tool.isFinish())
			{
				logger.info("当前队列长度:" + tool.getQueueLength());
				Thread.sleep(timeout);
			}
			logger.info("队列已经清空");
			tool.stop();
			while(!tool.isStop())
			{
				Thread.sleep(timeout);
			}
			logger.info("下载完成");
			logger.info("以下id有错误");
			for(Long id : errorId)
			{
				logger.error(id);
			}
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	private File makeDir(File fatherFile, String[] storageTree, int i) throws IOException
	{
		File node = new File(fatherFile , storageTree[i]);
		if(i == storageTree.length - 1)
		{
			if(node.exists())
			{
				node.delete();
			}
			node.createNewFile();
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
