package sky.tool.spider.service;

import sky.tool.spider.entity.Sukebei;

public interface SukebeiNyaaFunService
{
	boolean checkExistence(Integer webId);
	
	Sukebei save(Sukebei sukebei);
}
