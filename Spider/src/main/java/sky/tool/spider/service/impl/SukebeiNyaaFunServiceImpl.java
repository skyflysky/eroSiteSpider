package sky.tool.spider.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sky.tool.spider.dao.SukebeiDao;
import sky.tool.spider.entity.Sukebei;
import sky.tool.spider.service.SukebeiNyaaFunService;

@Service
public class SukebeiNyaaFunServiceImpl implements SukebeiNyaaFunService
{
	@Autowired SukebeiDao dao;

	@Override
	public boolean checkExistence(Integer webId)
	{
		Sukebei s = dao.findByWebId(webId);
		return s != null;
	}

	@Override
	public Sukebei save(Sukebei sukebei)
	{
		return dao.save(sukebei);
	}
	
}
