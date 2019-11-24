package sky.tool.spider.controller;

import java.io.File;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("video")
public class VideoController
{
	@RequestMapping("vi")
	public String getVideo(Model model, @RequestParam(required = true) String name)
	{
		File f = new File("K:\\food");
		for(String s : f.list())
		{
			if(s.startsWith(name))
			{
				File n = new File(f, s);
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
}
