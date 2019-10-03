package sky.tool.spider.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sky.tool.spider.dao.PicUrlDao;
import sky.tool.spider.entity.PicUrl;
import sky.tool.spider.service.PicUrlService;

@Service
public class PicUrlServiceImpl implements PicUrlService
{

	@Autowired
	PicUrlDao puDao;
	
	@Override
	public PicUrl insert(PicUrl picUrl)
	{
		return puDao.save(picUrl);
	}
	
}
