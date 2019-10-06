package sky.tool.spider.task;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import sky.tool.spider.entity.VideoUrl;
import sky.tool.spider.service.VideoService;
import sky.tool.spider.utils.SpringUtil;

@ConditionalOnProperty(prefix="work" , name = "mode" ,havingValue = "download")
@Component
public class VideoDownLoadTask extends AbstractTask
{
	@Value("${lastdate}")
	String lastdate;
	
	@Autowired
	VideoService videoService;
	
	@Override
	void doWork()
	{
		logger.info("开始准备下载视频");
		if(!StringUtils.isEmpty(lastdate))
		{
			Calendar lastTime = Calendar.getInstance();
			try
			{
				lastTime.setTime(SpringUtil.ymdFomat().parse(lastdate));
				lastTime.add(Calendar.MINUTE, -1);
			} 
			catch (ParseException e)
			{
				logger.error("上次下载日期输入有误！");
				return;
			}
			List<VideoUrl> urlList = videoService.unloadVideo(lastTime);
			Scanner scanner = new Scanner(System.in);
			System.setProperty("java.awt.headless", "false");
			for(VideoUrl vu : urlList)
			{
				toPast(vu.getMagnet());
				logger.info("已复制:'" + vu.getName() + "'，按任意键继续");
				scanner.next();
				videoService.downloadUrl(vu);
			}
			scanner.close();
			logger.info("库存已下载完毕");
		}
		else
		{
			logger.warn("没有获取到上次下载日期！lastdate");
		}
	}

	private void toPast(String magnet)
	{
		Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable tText = new StringSelection(magnet);
        clip.setContents(tText, null);
	}

	@Override
	boolean isWork()
	{
		return "video".equals(downloadMode);
	}

}
