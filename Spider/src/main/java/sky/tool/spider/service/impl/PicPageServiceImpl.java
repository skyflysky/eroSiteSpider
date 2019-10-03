package sky.tool.spider.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import sky.tool.spider.dao.PicPageDao;
import sky.tool.spider.entity.PicPage;
import sky.tool.spider.service.PicPageService;

@Service
public class PicPageServiceImpl implements PicPageService
{

	Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	PicPageDao ppDao;
	
	@SuppressWarnings("restriction")
	@Override
	public PicPage insert(PicPage picPage) throws com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException , NumberFormatException
	{
		return ppDao.save(picPage);
	}

	@Override
	public List<PicPage> findAblePage()
	{
		Specification<PicPage> spec = new Specification<PicPage>()
		{
			private static final long serialVersionUID = -8965663983816413434L;

			@Override
			public Predicate toPredicate(Root<PicPage> root, CriteriaQuery<?> cq, CriteriaBuilder cb)
			{
				List<Predicate> pList = new ArrayList<Predicate>();
				
				pList.add(cb.equal(root.get("openAble").as(Boolean.class), false));
				pList.add(cb.lessThanOrEqualTo(root.get("retryCount").as(Integer.class), 3));
				
				Predicate[] pArray = new Predicate[pList.size()];
				pList.toArray(pArray);
				return cb.and(pArray);
			}
		};
		return ppDao.findAll(spec);
	}

	@Override
	public boolean setOpenAble(boolean openAble, PicPage picPage)
	{
		picPage.setOpenAble(openAble);
		PicPage pp = ppDao.save(picPage);
		return pp.getOpenAble();
	}

	@Override
	public PicPage getPicPageByWebId(Integer webId)
	{
		PicPage pp = ppDao.findOneByWebId(webId);
		pp.setRetryCount(pp.getRetryCount() + 1);
		return ppDao.save(pp);
	}

	@Override
	public int setPicCount(int picCount, PicPage picPage)
	{
		picPage.setPicCount(picCount);
		PicPage pp = ppDao.save(picPage);
		return pp.getPicCount();
	}
	
}
