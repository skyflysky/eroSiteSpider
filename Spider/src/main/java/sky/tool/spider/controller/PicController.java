package sky.tool.spider.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sky.tool.spider.dto.PicDto;
import sky.tool.spider.service.PictureService;
@ConditionalOnProperty(prefix="work" , name = "mode" ,havingValue = "enjoy")
@Controller
public class PicController
{
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	PictureService pictureService;

	@RequestMapping(value = "showPic")
	public String getPic(Model model, @RequestParam(required = true) Long id)
	{
		List<PicDto> stringList = pictureService.getLoadedPicDto(id);
		model.addAttribute("manList", stringList);
		model.addAttribute("id", stringList.get(0).getPid());
		return "picUrl";
	}

	@RequestMapping(value = "next")
	public String getNext(Model model, @RequestParam(required = true) Long id)
	{
		List<PicDto> stringList = pictureService.getNextPicDto(id);
		model.addAttribute("id", stringList.get(0).getPid());
		model.addAttribute("manList", stringList);
		return "picUrl";
	}
	
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(Long id)
	{
		pictureService.delePhoto(id);
		return "";
	}

	@PostConstruct
	public void testPicService()
	{
		try
		{
			URL url = new URL("http://127.0.0.1:43960/ok.txt");
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			InputStream input = http.getInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while((len = input.read(buffer)) != -1)
			{
				baos.write(buffer , 0 ,len);
			}
			String str = baos.toString();
			input.close();
			baos.close();
			if(str.equals("go"))
			{
				logger.info("图片服务开启正常");
			}
		} 
		catch (java.net.ConnectException e) 
		{
			for(int i = 0 ;  i < 30 ; i ++)
			{
				logger.warn("图片服务未开启！！！");
			}
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
}
