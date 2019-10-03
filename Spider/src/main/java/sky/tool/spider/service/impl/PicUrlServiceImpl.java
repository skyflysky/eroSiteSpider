package sky.tool.spider.service.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.swing.text.html.HTMLDocument.HTMLReader.SpecialAction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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

	@Override
	public List<PicUrl> getUnLoad()
	{
		return puDao.findByDawnload(false);
	}

	@Override
	public boolean downloadMark(Long id , String path)
	{
		PicUrl picUrl = puDao.getOne(id);
		picUrl.setLocalPath(path);
		picUrl.setDawnload(true);
		return puDao.save(picUrl).getDawnload();
	}

	
}
