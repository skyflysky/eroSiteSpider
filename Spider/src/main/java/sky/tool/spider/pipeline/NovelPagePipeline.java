package sky.tool.spider.pipeline;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import sky.tool.spider.entity.NovelPage;
import sky.tool.spider.service.NovelService;
import sky.tool.spider.utils.SpringUtil;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@SuppressWarnings("restriction")
@Component
public class NovelPagePipeline implements Pipeline
{
	Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	NovelService novelService;
	
	@Override
	public void process(ResultItems resultItems, Task task)
	{
		logger.info("开始解析" + resultItems.get("url"));
		
		String type = SpringUtil.innerGetter(resultItems.get("type"));//文章的类型
		
		List<String> strList = resultItems.get("links");//有哪些文章
		for(String s : strList)
		{
			try
			{
				Document document = DocumentHelper.parseText(s);
				Element rootElement = document.getRootElement();
				String href = rootElement.attributeValue("href");//文章指向的页面的相对链接
				String title = rootElement.attributeValue("title");//文章的标题
				String DateString = rootElement.elements().get(0).getText();//文章的上传日期字符串
				Calendar uploadDate = Calendar.getInstance();
				uploadDate.setTime(SpringUtil.ymdFomat().parse(DateString));
				NovelPage np = novelService.insertNovelPage(new NovelPage(href, title,uploadDate , type));
				if(np != null && np.getId() != null)
				{
					logger.info("新增页面:'" + href + "'成功");
				}
			} 
			//webId是唯一约束 如果抓到唯一约束 则这个页面之前解析过，不再解析
			catch (MySQLIntegrityConstraintViolationException | org.hibernate.exception.ConstraintViolationException |org.springframework.dao.DataIntegrityViolationException e)
			{
				logger.info("页面 " + resultItems.get("url") + "已存在");
			}
			catch (DocumentException e)
			{
				e.printStackTrace();
			} 
			catch (ParseException e)
			{
				e.printStackTrace();
			} 
		}
		logger.info("解析" + resultItems.get("url") + "完成");
	}

}
