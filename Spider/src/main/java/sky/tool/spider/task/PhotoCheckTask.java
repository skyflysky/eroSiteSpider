package sky.tool.spider.task;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import sky.tool.spider.entity.PicUrl;
import sky.tool.spider.service.PictureService;

@ConditionalOnProperty(prefix="work" , name = "mode" ,havingValue = "check")
@Component
public class PhotoCheckTask extends AbstractTask
{
	@Value("${check.mode}")
	String checkMode;
	
	@Autowired
	PictureService service;
	
	@Value("${check.threshold.line}")
	private Integer lineThreshold;
	
	@Value("${check.threshold.photo}")
	private Double phtotThreshold;
	
	@Value("${check.page.index}")
	private Integer pageIndex;
	
	@Override
	void doWork()
	{
		logger.info("图片检查");
		List<PicUrl> list = new ArrayList<>();
		int count = 0;
		for(PicUrl p : service.checkPic(pageIndex))
		{
			File f = new File(p.getLocalPath());
			logger.info((count++) + "\t" + f.getAbsolutePath());
			if(! checkPic(f))
			{
				logger.info("added");
				list.add(p);
			}
		}
		
		service.deletePicLog(list);
		
		logger.info("finish");
	}
	
	private boolean checkPic(File picFile)
	{
		try
		{
			//logger.info(picFile.getAbsoluteFile());
			FileInputStream fis = new FileInputStream(picFile);
			BufferedImage bi = ImageIO.read(fis);
			int width = bi.getWidth() , height = bi.getHeight();
			List<Map<Color, Integer>> fullColor = new ArrayList<>(bi.getHeight());
			for(int i = 0 ; i < height ; i ++)
			{
				try
				{
					Map<Color, Integer> lineColor = new HashMap<Color,Integer>(width);
					for(int j = 0 ; j < width ; j ++)
					{
						try
						{
							Color c = new Color(bi.getRGB(j, i));
							Integer count = lineColor.get(c);
							if(count != null)
							{
								lineColor.put(c, count + 1);
							}
							else
							{
								lineColor.put(c, 1);
							}
						}
						catch (Exception e)
						{
							logger.error("error" , e);
							continue;
						}
					}
					fullColor.add(lineColor);
				}
				catch (Exception e)
				{
					logger.error("error" , e);
					continue;
				}
			}
			fis.close();
			
			int value = 0; 
			
			for(int i = 0 ; i < fullColor.size() ; i ++)
			{
				Map<Color, Integer> lineColor = fullColor.get(i);
				if(lineColor.keySet().size() < lineThreshold)
				{
					value ++;
				}
				else
				{
					if(value > 0)
					{
						value --;
					}
				}
			}
			
			return value < (height * phtotThreshold);
		}
		catch (IOException e)
		{
			logger.error("error" , e);
			return false;
		}
	}

	@Override
	boolean isWork()
	{
		return "photo".equals(checkMode);
	}
	
	

}
