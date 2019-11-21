package sky.tool.spider.pipeline;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import sky.tool.spider.entity.PicPage;
import sky.tool.spider.service.PictureService;
import sky.tool.spider.utils.SpringUtil;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@SuppressWarnings("restriction")
@Component
public class PicPagePipeline implements Pipeline
{
	Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	PictureService pictureService;
	
	@Override
	public void process(ResultItems resultItems, Task task)
	{
		List<String> infos = resultItems.get("infos");
		String type = resultItems.get("type");//获取图片类型
		for(String onePage : infos)//循环所有解析到的页面
		{
			try
			{
				Document document = Jsoup.parse(onePage);//Jsoup解析页面 dom4j遇到<img>标签会懵逼 非要个</img> ε=(´ο｀*)))唉
				String urls = document.getElementsByTag("a").get(0).attr("href");//图片详情页面的相对url
				String picUrls = document.getElementsByTag("img").get(0).attr("data-original");//图片的封面图
				String title = document.getElementsByTag("h3").get(0).html();//图片的标题
				String uploadDate = document.getElementsByTag("span").get(0).html();//上传的日期
				Calendar uploadDay = Calendar.getInstance();
				uploadDay.setTime(SpringUtil.ymdFomat().parse(uploadDate));
				
				PicPage p = pictureService.insertPicPage(new PicPage(picUrls, urls, title, uploadDay , SpringUtil.innerGetter(type).trim()));
				if(p != null && p.getId() != null)
				{
					logger.info("页面" + urls + "抓取成功");
				}
				else
				{
					logger.error("页面" + urls + "抓取失败");
				}
			} 
			//webId 是唯一约束
			catch (MySQLIntegrityConstraintViolationException | org.hibernate.exception.ConstraintViolationException |org.springframework.dao.DataIntegrityViolationException  e)
			{
				logger.info("已经被记录过了");
				continue;
			} 
			catch (NumberFormatException e) 
			{
				logger.error("出现非法的webId");
				logger.error(onePage);
				break;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				break;
			}
			
		}
	}
}
