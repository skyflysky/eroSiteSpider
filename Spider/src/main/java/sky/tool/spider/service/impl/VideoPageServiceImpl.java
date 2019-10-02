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

import sky.tool.spider.dao.VideoPageDao;
import sky.tool.spider.entity.VideoPage;
import sky.tool.spider.service.VideoPageService;

@Service
public class VideoPageServiceImpl implements VideoPageService
{
	@Autowired
	VideoPageDao vpDao;
	
	private Logger logger = Logger.getLogger(getClass());

	@SuppressWarnings("restriction")
	@Override
	public VideoPage insertVideoPage(String url) throws NumberFormatException, com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException
	{
		VideoPage videoPage = new VideoPage(url);
		VideoPage vp = vpDao.save(videoPage);
		logger.info("新增网页地址:'" + url + "'");
		return vp;
	}
	
	@Override
	public boolean setOpenAble(boolean openable , VideoPage videoPage)
	{
		videoPage.setOpenable(openable);
		VideoPage vp = vpDao.save(videoPage);
		return vp.getOpenable();
	}
	
	@Override
	public VideoPage getVideoPageByWebId(Integer webId)
	{
		VideoPage vp = vpDao.findOneByWebId(webId);
		vp.setRetryCount(vp.getRetryCount() + 1);
		return vpDao.save(vp);
	}

	@Override
	public List<VideoPage> findAblePage()
	{
		Specification<VideoPage> spec = new Specification<VideoPage>()
		{
			private static final long serialVersionUID = -2470255032930275358L;

			@Override
			public Predicate toPredicate(Root<VideoPage> root, CriteriaQuery<?> cq, CriteriaBuilder cb)
			{
				List<Predicate> pList = new ArrayList<Predicate>();
				
				pList.add(cb.equal(root.get("openable").as(Boolean.class), false));
				pList.add(cb.lessThanOrEqualTo(root.get("retryCount").as(Integer.class), 2));
				
				Predicate[] pArray = new Predicate[pList.size()];
				pList.toArray(pArray);
				return cb.and(pArray);
			}
		};
		
		return vpDao.findAll(spec);
	}
}
