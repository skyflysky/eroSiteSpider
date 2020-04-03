package sky.tool.spider.service;

import java.util.List;

import sky.tool.spider.entity.Sukebei;
import sky.tool.spider.entity.Sukebei404;
import sky.tool.spider.entity.SukebeiPage;

public interface SukebeiNyaaFunService
{
	boolean checkSukebeiExistence(Integer webId);
	
	boolean checkSukebeiPageExistence(Integer webId);
	
	Sukebei save(Sukebei sukebei);
	
	SukebeiPage save(SukebeiPage sukebeiPage);
	
	List<Sukebei> getSukebeiBySql(String sql);
	
	Sukebei setDownloaded(String torrentPath , Sukebei sukebei); 
	
	List<Integer> getUndownloadedWebId(int max , int min);
	
	Sukebei404 save(Sukebei404 sukebei404);
}
