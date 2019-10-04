package sky.tool.spider.pipeline;

import java.text.ParseException;
import java.util.Calendar;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sky.tool.spider.entity.VideoPage;
import sky.tool.spider.entity.VideoUrl;
import sky.tool.spider.service.VideoService;
import sky.tool.spider.utils.SpringUtil;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@Transactional
@Component
public class VideoUrlPipeline implements Pipeline
{
	Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	VideoService videoService;
	
	@Transactional
	@Override
	public void process(ResultItems resultItems, Task task)
	{
		logger.info("正在处理");
		String rowType = (String) resultItems.get("type");
		String rowUploadDate = (String) resultItems.get("uploadDate");
		String rowMagnate = (String) resultItems.get("magnate");
		String rowTitlePic = (String) resultItems.get("titlePic");
		String rowTitle = (String) resultItems.get("title");
		String rowUrl = (String) resultItems.get("url");
		
		try
		{
			VideoPage videoPage = videoService.getVideoPageByWebId(SpringUtil.getWebIdFromUrl(rowUrl , VideoPage.ngateMark));
			
			
			String type = rowType.substring(rowType.lastIndexOf("：") + 1, rowType.lastIndexOf("<"));
			
			String uploadDate = rowUploadDate.substring(rowUploadDate.lastIndexOf("：") + 1, rowUploadDate.lastIndexOf("<"));
			Calendar uploadCalendar = Calendar.getInstance();
			uploadCalendar.setTime(SpringUtil.ymdFomat().parse(uploadDate));
			
			String magnate = SpringUtil.attGetter(rowMagnate, "value");
			
			String titlePic = SpringUtil.attGetter(rowTitlePic, "data-original");
			
			String title = SpringUtil.innerGetter(rowTitle);
			
			videoService.insert( new VideoUrl(videoPage, magnate, uploadCalendar, type, titlePic, title));
			
			boolean pageStatus = videoService.setVideoOpenAble(true, videoPage);
			if(pageStatus != true)
			{
				logger.error("更改页面打开状态失败, url:'" + rowUrl + "'");
				return;
			}
			logger.info("新增可下载视频:'" + magnate +"'");
			
		} 
		catch (NumberFormatException e)
		{
			e.printStackTrace();
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		catch (IndexOutOfBoundsException e) 
		{
			e.printStackTrace();
			logger.error("页面" + rowUrl + "出现无法正常解析");
		}
		
		logger.info("处理完成");
	}
}
