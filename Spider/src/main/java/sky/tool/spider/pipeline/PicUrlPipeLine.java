package sky.tool.spider.pipeline;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sky.tool.spider.entity.PicPage;
import sky.tool.spider.entity.PicUrl;
import sky.tool.spider.service.PictureService;
import sky.tool.spider.utils.SpringUtil;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@Transactional
@Component
public class PicUrlPipeLine implements Pipeline
{
	Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	PictureService pictureService;
	
	@Transactional
	@Override
	public void process(ResultItems resultItems, Task task)
	{
		logger.info("正在处理");
		
		List<String> imgs = resultItems.get("imgs");
		
		PicPage picPage = pictureService.getPicPageByWebId(SpringUtil.getWebIdFromUrl(resultItems.get("url"), PicPage.ngateMark));
		int picCount = 0;
		for(String img : imgs)
		{
			String src = SpringUtil.attGetter(img, "data-original");
			
			pictureService.insertPicUrl(new PicUrl(picPage, src));
			picCount ++;
		}
		boolean pageStatus = pictureService.setPicPageOpenAble(true, picPage);
		pictureService.setPicPageCount(picCount, picPage);
		if(pageStatus != true)
		{
			logger.error("更改页面打开状态失败, url:'" + resultItems.get("url") + "'");
			return;
		}
		
		logger.info("处理完成");
	}
}
