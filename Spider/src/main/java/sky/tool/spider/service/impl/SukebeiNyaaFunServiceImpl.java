package sky.tool.spider.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Service;

import sky.tool.spider.dao.Sukebei404Dao;
import sky.tool.spider.dao.Sukebei50Dao;
import sky.tool.spider.dao.SukebeiDao;
import sky.tool.spider.dao.SukebeiPageDao;
import sky.tool.spider.entity.Sukebei;
import sky.tool.spider.entity.Sukebei404;
import sky.tool.spider.entity.Sukebei500502;
import sky.tool.spider.entity.SukebeiPage;
import sky.tool.spider.service.SukebeiNyaaFunService;

@Service
public class SukebeiNyaaFunServiceImpl implements SukebeiNyaaFunService
{
	private Logger logger = Logger.getLogger(SukebeiNyaaFunServiceImpl.class);
	
	@Autowired 
	SukebeiDao dao;
	
	@Autowired
	SukebeiPageDao pageDao;
	
	@Autowired
	Sukebei404Dao fourDao;
	
	@Autowired
	Sukebei50Dao fiveDao;
	
	@Value("${sukebeipage.download50}")
	Boolean download50;
	
	@Value("${sukebei.auto.limit}")
	Integer limit;
	
	@Autowired
	LocalContainerEntityManagerFactoryBean entityManagerFactory;

	@Override
	public boolean checkSukebeiExistence(Integer webId)
	{
		Sukebei s = dao.findByWebId(webId);
		return s != null;
	}

	@Override
	public Sukebei save(Sukebei sukebei)
	{
		return dao.save(sukebei);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Sukebei> getSukebeiBySql(String sql , Integer downloadSize)
	{
		logger.info(sql);
		EntityManager em = entityManagerFactory.getNativeEntityManagerFactory().createEntityManager();
		em.getTransaction().begin();
		Query query = em.createNativeQuery(sql,Sukebei.class);
		List<Sukebei> resultList = new ArrayList<>(query.getResultList());
		if(downloadSize > 0)
		{
			List<Sukebei> tempList = new ArrayList<>(downloadSize);
			for(int i = 0  ; i < downloadSize ; i++)
			{
				tempList.add(resultList.get(i));
			}
			return tempList;
		}
		return resultList;
	}

	@Override
	public Sukebei setDownloaded(String torrentPath, Sukebei sukebei)
	{
		sukebei.setDownloaded(true);
		sukebei.setTorrentPath(torrentPath);
		return dao.save(sukebei);
	}

	@Override
	public boolean checkSukebeiPageExistence(Integer webId)
	{
		SukebeiPage sp = pageDao.findByWebId(webId);
		return sp != null;
	}

	@Override
	public SukebeiPage save(SukebeiPage sukebeiPage)
	{
		return pageDao.save(sukebeiPage);
	}

	@Override
	public List<Integer> getUndownloadedWebId(int max , int min)
	{
		Specification<SukebeiPage> specp = new Specification<SukebeiPage>()
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 4525607177507733764L;

			@Override
			public Predicate toPredicate(Root<SukebeiPage> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder)
			{
				Predicate[] pArray = new Predicate[2];
				pArray[0] = criteriaBuilder.greaterThanOrEqualTo(root.get("webId").as(Integer.class), Integer.valueOf(min));
				pArray[1] = criteriaBuilder.lessThanOrEqualTo(root.get("webId").as(Integer.class), Integer.valueOf(max));
				
				return criteriaBuilder.and(pArray);
			}
		};
		Set<Integer> downloaded = new HashSet<>();
		for(SukebeiPage sp : pageDao.findAll(specp))
		{
			downloaded.add(sp.getWebId());
		}
		
		Specification<Sukebei404> spec4 = new Specification<Sukebei404>()
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 578949990697462538L;

			@Override
			public Predicate toPredicate(Root<Sukebei404> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder)
			{
				Predicate[] pArray = new Predicate[2];
				pArray[0] = criteriaBuilder.greaterThanOrEqualTo(root.get("id").as(Integer.class), Integer.valueOf(min));
				pArray[1] = criteriaBuilder.lessThanOrEqualTo(root.get("id").as(Integer.class), Integer.valueOf(max));
				
				return criteriaBuilder.and(pArray);
			}
		};
		for(Sukebei404 s4 : fourDao.findAll(spec4))
		{
			downloaded.add(s4.getId());
		}
		
		if(!download50)
		{
			Specification<Sukebei500502> spec5 = new Specification<Sukebei500502>()
			{
				private static final long serialVersionUID = 7064308541115846435L;

				@Override
				public Predicate toPredicate(Root<Sukebei500502> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder)
				{
					Predicate[] pArray = new Predicate[2];
					pArray[0] = criteriaBuilder.greaterThanOrEqualTo(root.get("id").as(Integer.class), Integer.valueOf(min));
					pArray[1] = criteriaBuilder.lessThanOrEqualTo(root.get("id").as(Integer.class), Integer.valueOf(max));
					
					return criteriaBuilder.and(pArray);
				}
			};
			
			for(Sukebei500502 s5 : fiveDao.findAll(spec5))
			{
				downloaded.add(s5.getId());
			}
		}
		
		List<Integer> result = new ArrayList<>();
		for(int i = min ; i <= max ; i++)
		{
			if(!downloaded.contains(Integer.valueOf(i)))
			{
				result.add(i);
			}
		}
		
		return result;
	}

	@Override
	public Sukebei404 save(Sukebei404 sukebei404)
	{
		return fourDao.save(sukebei404);
	}

	@Override
	public Sukebei500502 save(Sukebei500502 sukebei500502)
	{
		return fiveDao.save(sukebei500502);
	}

	@Override
	public long autoLastGrab()
	{
		Pageable page = PageRequest.of(0, limit, Sort.by("publishDate").descending());
		List<Sukebei> sukebeiList = new ArrayList<>(dao.findAll(page).getContent());
		
		Long max = Long.MIN_VALUE;
		Integer index = 0;
		for(int i = 0 ; i < sukebeiList.size() - 1 ; i ++)
		{
			Long subtract = sukebeiList.get(i).getPublishDate().getTimeInMillis() - sukebeiList.get(i+1).getPublishDate().getTimeInMillis();
			if(subtract >= max)
			{
				max = subtract;
				index = i + 1;
			}
		}
		
		return sukebeiList.get(index).getPublishDate().getTimeInMillis();
	}
}
