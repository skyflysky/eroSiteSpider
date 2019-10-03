package sky.tool.spider.service;

import java.util.List;

import sky.tool.spider.entity.PicUrl;

public interface PicUrlService
{
	PicUrl insert(PicUrl picUrl);
	
	List<PicUrl> getUnLoad();
	
	boolean downloadMark(Long id, String path);
}
