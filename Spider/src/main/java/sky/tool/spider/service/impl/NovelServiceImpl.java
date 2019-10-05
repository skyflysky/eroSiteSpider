package sky.tool.spider.service.impl;

import java.io.File;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
		return npDao.save(novelPage);
	}

	@Override
	public List<NovelPage> findUnloadPage()
	{
		return npDao.findByOpenAble(false);
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
