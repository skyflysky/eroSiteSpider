package sky.tool.spider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import sky.tool.spider.task.VideoPageTask;

@Controller
public class VpController
{
	@Autowired
	VideoPageTask videoPageTask;
	
	//@RequestMapping("videoPage")
	@ResponseBody
	public String videoPage()
	{
		videoPageTask.doSpider();
		return "go";
	}
}
