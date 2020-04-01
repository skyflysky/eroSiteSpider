package sky.tool.spider.service;

import java.util.List;

import sky.tool.spider.entity.Sukebei;

public interface SukebeiNyaaFunService
{
	boolean checkExistence(Integer webId);
	
	Sukebei save(Sukebei sukebei);
	
	List<Sukebei> getSukebeiBySql(String sql);
	
	Sukebei setDownloaded(String torrentPath , Sukebei sukebei); 
}
