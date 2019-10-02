package sky.tool.spider.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sky.tool.spider.dao.VideoUrlDao;
import sky.tool.spider.entity.VideoUrl;
import sky.tool.spider.service.VideoUrlServcie;

@Service
public class VideoUrlServiceImpl implements VideoUrlServcie
{
	@Autowired
	private VideoUrlDao vudao;

	@Override
	public VideoUrl insert(VideoUrl videoUrl)
	{
		return vudao.save(videoUrl);
	}
	
	
}
