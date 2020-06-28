package sky.tool.spider.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import sky.tool.spider.dao.NovelPageDao;
import sky.tool.spider.dao.NovelUrlDao;
import sky.tool.spider.entity.NovelPage;
import sky.tool.spider.entity.NovelUrl;
import sky.tool.spider.service.NovelService;

@SuppressWarnings("restriction")
@Service
public class NovelServiceImpl implements NovelService
{
	Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	NovelPageDao npDao;
	
	@Autowired
	NovelUrlDao nuDao;
	
	@Override
	public NovelPage insertNovelPage(NovelPage novelPage) throws MySQLIntegrityConstraintViolationException
	{
		if(npDao.countByWebId(novelPage.getWebId()) == 0)
		{
			return npDao.save(novelPage);
		}
		else
		{
			logger.info("数据已存在");
			return null;
		}
	}

	@Override
	public List<NovelPage> findUnloadPage()
	{
		Specification<NovelPage> spec = new Specification<NovelPage>()
		{
			private static final long serialVersionUID = -6578547871899978997L;

			@Override
			public Predicate toPredicate(Root<NovelPage> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder)
			{
				List<Predicate> pList = new ArrayList<Predicate>();
				pList.add(criteriaBuilder.equal(root.get("openAble").as(Boolean.class), false));
				pList.add(criteriaBuilder.lessThanOrEqualTo(root.get("retryCount").as(Integer.class), new Integer(4)));
				Predicate[] pArray = new Predicate[pList.size()];
				pList.toArray(pArray);
				return criteriaBuilder.and(pArray);
			}
		};
		return npDao.findAll(spec);
	}

	@Override
	public NovelPage getNovelPageByWebId(Integer webId)
	{
		NovelPage np = npDao.findByWebId(webId);
		np.setRetryCount(np.getRetryCount() + 1);
		return npDao.save(np);
	}

	@Override
	public NovelUrl getNovelUrlByPage(NovelPage novelPage)
	{
		return nuDao.findByPage(novelPage);
	}

	@Transactional
	@Override
	public NovelUrl novelUrlDownloed(NovelPage novelPage, File targetFile)
	{
		NovelUrl nu = new NovelUrl(novelPage, targetFile.getAbsolutePath(),targetFile.length());
		novelPage.setOpenAble(true);
		npDao.save(novelPage);
		return nuDao.save(nu);
		
		
	}

}
