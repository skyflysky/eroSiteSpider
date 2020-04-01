package sky.tool.spider.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sky.tool.spider.dao.SukebeiDao;
import sky.tool.spider.entity.Sukebei;
import sky.tool.spider.service.SukebeiNyaaFunService;

@Service
public class SukebeiNyaaFunServiceImpl implements SukebeiNyaaFunService
{
	private Logger logger = Logger.getLogger(SukebeiNyaaFunServiceImpl.class);
	
	@Autowired 
	SukebeiDao dao;
	
	@Autowired
	LocalContainerEntityManagerFactoryBean entityManagerFactory;

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

	@SuppressWarnings("unchecked")
	@Override
	public List<Sukebei> getSukebeiBySql(String sql)
	{
		logger.info(sql);
		EntityManager em = entityManagerFactory.getNativeEntityManagerFactory().createEntityManager();
		em.getTransaction().begin();
		Query query = em.createNativeQuery(sql,Sukebei.class);
		return query.getResultList();
	}

	@Override
	public Sukebei setDownloaded(String torrentPath, Sukebei sukebei)
	{
		sukebei.setDownloaded(true);
		sukebei.setTorrentPath(torrentPath);
		return dao.save(sukebei);
	}

	
}
