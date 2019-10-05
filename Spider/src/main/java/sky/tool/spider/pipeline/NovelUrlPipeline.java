package sky.tool.spider.pipeline;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import sky.tool.spider.entity.NovelPage;
import sky.tool.spider.service.NovelService;
import sky.tool.spider.utils.SpringUtil;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@Component
public class NovelUrlPipeline implements Pipeline
{
	private Logger logger = Logger.getLogger(getClass());
	
	@Value("${pic.storage}")
	String storage;
	
	@Autowired
	NovelService novelService;
	
	@Override
	public void process(ResultItems resultItems, Task task)
	{
		logger.info("开始解析");
		try
		{
			NovelPage np = novelService.getNovelPageByWebId(SpringUtil.getWebIdFromUrl(resultItems.get("url"), NovelPage.ngateMark));
			File targetFile = getFile(np);
			writeTxt(resultItems.get("body") , targetFile);
			if(targetFile.length() < 50)
			{
				targetFile.delete();
				logger.error(np.getId() + "文件过小忽略不计");
			}
			else
			{
				novelService.novelUrlDownloed(np , targetFile);
				logger.info("文章" + np.getTitle() + "下载完毕");
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		} 
		logger.info("解析完毕");
		
	}
	
	private void writeTxt(String body, File file) throws IOException
	{
		Document document = Jsoup.parse(body);
		List<Element> ps = document.getElementsByTag("p");
		BufferedWriter bw = new BufferedWriter(new FileWriter(file)); 
		for(Element p : ps)
		{
			List<Element> spans = p.getElementsByTag("span");
			if(spans.size() > 0)
				bw.write(spans.get(0).text().trim());
			else
				bw.write(p.text().trim());
			bw.newLine();
		}
		bw.flush();
		bw.close();
	}

	private File getFile(NovelPage novelPage) throws IOException
	{
		File parFile = new File(storage);
		if(!parFile.exists())
			parFile.mkdir();
		File novelFile = new File(parFile, "novel");
		if(!novelFile.exists())
			novelFile.mkdir();
		File typeFile = new File(novelFile, novelPage.getType());
		if(!typeFile.exists())
			typeFile.mkdir();
		File tarFile = new File(typeFile , novelPage.getTitle() + novelPage.getWebId() + ".txt");
		tarFile.createNewFile();
		return tarFile;
	}

}
