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
			//通过Url  获取webId 再获取所属的列表页实体
			NovelPage np = novelService.getNovelPageByWebId(SpringUtil.getWebIdFromUrl(resultItems.get("url"), NovelPage.ngateMark));
			if (np.getRetryCount() < 4)
			{
				File targetFile = getFile(np);//生成小说实体对应的文件
				writeTxt(resultItems.get("body"), targetFile);//将html文字写入文件
				if (targetFile.length() < 50)//如果写入完文件过小 则过程中有问题 删掉
				{
					targetFile.delete();
					logger.error(np.getId() + "文件过小忽略不计");
				} else
				{
					novelService.novelUrlDownloed(np, targetFile);//写入完成后保存数据库
					logger.info("文章" + np.getTitle() + "下载完毕");
				} 
			}
			else
			{
				logger.info("文章'" + np.getTitle() + "'已经重试过" + np.getRetryCount() + "次，不再尝试");
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
		Document document = Jsoup.parse(body);//用jsoup解析 dom4j遇到<br>标签会懵逼 提示必须要有</br> ε=(´ο｀*)))唉
		List<Element> ps = document.getElementsByTag("p");//提取出所有<p>子元素
		BufferedWriter bw = new BufferedWriter(new FileWriter(file)); //做好写文件的准备
		for(Element p : ps)
		{
			List<Element> spans = p.getElementsByTag("span");//看看<p>是否有<span>子元素，如果有 那也只会有一个 取<span>里面的内容
			if(spans.size() > 0)
				bw.write(spans.get(0).text().trim());
			else
				bw.write(p.text().trim());//没有则取<p>里面的内容
			bw.newLine();//一个<p>换一次行
		}
		bw.flush();//写文件
		bw.close();//关闭流
	}

	private File getFile(NovelPage novelPage) throws IOException
	{
		File parFile = new File(storage);
		if(!parFile.exists())
			parFile.mkdir();
		File novelFile = new File(parFile, "novel");//在图片下载目录下 创建novel文件夹
		if(!novelFile.exists())
			novelFile.mkdir();
		File typeFile = new File(novelFile, novelPage.getType());//根据文章类型再建一个文件夹
		if(!typeFile.exists())
			typeFile.mkdir();
		//文件命名规则 文章名 + webId
		File tarFile = new File(typeFile , novelPage.getTitle() + novelPage.getWebId() + ".txt");
		tarFile.createNewFile();
		return tarFile;
	}

}
