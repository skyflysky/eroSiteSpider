package sky.tool.spider.controller;

import java.io.File;
import java.net.URLEncoder;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("video")
public class VideoController
{
	private Logger logger = Logger.getLogger(VideoController.class);
	
	@RequestMapping("vi")
	public String getVideo(Model model, @RequestParam(required = true) String name)
	{
		model.addAttribute("curr", name);
		File f = new File("K:\\food");
		for(String s : f.list())
		{
			while (name.length() < 4)
			{
				name = "0" + name;
			}
			if(s.startsWith(name))
			{
				File n = new File(f, s);
				model.addAttribute("realPath", "http://192.168.147.101:43960/vi/" + n.getName());
				if(n.getName().toLowerCase().endsWith(new String("mp4")))
				{
					model.addAttribute("name", n.getName());
					model.addAttribute("netPath", "http://192.168.147.101:43960/vi/" + n.getName());
					return "videoMp4";
				}
			}
		}
		model.addAttribute("name", "默认播放MP4");
		model.addAttribute("netPath", "http://192.168.147.101:43960/vi/0780.mp4");
		return "videoMp4";
	}
	
	@RequestMapping("video")
	public String getVideoo(Model model, @RequestParam(required = true) Integer name)
	{
		try
		{
			model.addAttribute("curr", name);
			File f = new File("K:\\food\\aaddd");
			File n = new File(f, f.list()[name]);
			model.addAttribute("realPath", "http://192.168.147.101:43960/vi/aaddd/" + URLEncoder.encode(n.getName(), "UTF-8"));
			if (n.getName().toLowerCase().endsWith(new String("mp4")))
			{
				model.addAttribute("name", n.getName());
				model.addAttribute("netPath", "http://192.168.147.101:43960/vi/aaddd/" + n.getName());
				return "videoA";
			} 
		} 
		catch (Exception e)
		{
			model.addAttribute("name", "默认播放MP4");
			model.addAttribute("netPath", "http://192.168.147.101:43960/vi/0780.mp4");
			logger.error(e);
			return "videoA";
		}
		model.addAttribute("name", "默认播放MP4");
		model.addAttribute("netPath", "http://192.168.147.101:43960/vi/0780.mp4");
		return "videoA";
	}
}
