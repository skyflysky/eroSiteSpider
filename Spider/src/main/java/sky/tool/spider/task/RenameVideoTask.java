package sky.tool.spider.task;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import sky.tool.spider.entity.VideoUrl;
import sky.tool.spider.service.VideoService;

@Component
@ConditionalOnProperty(prefix="work" , name = "mode" ,havingValue = "download")
public class RenameVideoTask extends AbstractTask
{

	@Value("${video.storage}")
	String videoPath;
	
	@Autowired
	VideoService videoService;
	
	@Override
	void doWork()
	{
		logger.info("开始重命名");
		File files = new File(videoPath);
		for(String childFileName : files.list())
		{
			VideoUrl vu = videoService.getByFileName(childFileName);
			if(vu != null)
			{
				File childFile = new File(files, childFileName);
				StringBuilder sb = new StringBuilder(childFileName.substring(0, childFileName.lastIndexOf(".")));
				sb.append("__");
				sb.append(vu.getName());
				sb.append(childFileName.substring(childFileName.lastIndexOf(".")));
				File newChildFile = new File(files , sb.toString().replace(" ", ""));
				childFile.renameTo(newChildFile);
				logger.info("重命名得到文件:" + sb.toString());
			}
			else if(!childFileName.contains("__"))
			{
				logger.error("++++++++++++++++++++++++++++++++++++++++");
				logger.error("========================================");
				logger.error("----------------------------------------");
				logger.error(childFileName + "无对应记录");
				logger.error("----------------------------------------");
				logger.error("========================================");
				logger.error("++++++++++++++++++++++++++++++++++++++++");
			}
		}
		logger.info("重命名结束");
	}

	@Override
	boolean isWork()
	{
		return "rename".equals(downloadMode);
	}

}
