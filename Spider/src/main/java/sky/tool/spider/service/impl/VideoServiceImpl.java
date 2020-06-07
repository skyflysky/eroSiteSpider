package sky.tool.spider.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import sky.tool.spider.dao.VideoPageDao;
import sky.tool.spider.dao.VideoUrlDao;
import sky.tool.spider.entity.VideoPage;
import sky.tool.spider.entity.VideoUrl;
import sky.tool.spider.service.VideoService;
import sky.tool.spider.utils.SpringUtil;

@Service
public class VideoServiceImpl implements VideoService
{
	@Autowired
	private VideoPageDao vpDao;
	
	@Autowired
	private VideoUrlDao vuDao;
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Override
	public VideoUrl insert(VideoUrl videoUrl)
	{
		return vuDao.save(videoUrl);
	}
	
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
	public boolean setVideoOpenAble(boolean openable , VideoPage videoPage)
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
				pList.add(cb.lessThanOrEqualTo(root.get("retryCount").as(Integer.class), 3));
				
				Predicate[] pArray = new Predicate[pList.size()];
				pList.toArray(pArray);
				return cb.and(pArray);
			}
		};
		
		return vpDao.findAll(spec);
	}

	@Override
	public List<VideoUrl> unloadVideo(Calendar lastTime)
	{
		logger.info("开始查询自" + SpringUtil.ymdFomat().format(lastTime.getTime()) + "至今的数据");
		Specification<VideoUrl> spec = new Specification<VideoUrl>()
		{
			private static final long serialVersionUID = 8721612486207011572L;

			@Override
			public Predicate toPredicate(Root<VideoUrl> root, CriteriaQuery<?> query, CriteriaBuilder cb)
			{
				List<Predicate> pList = new ArrayList<>();
				
				pList.add(cb.greaterThanOrEqualTo(root.get("uploadDate").as(Calendar.class), lastTime));
				pList.add(cb.equal(root.get("downloaded").as(Boolean.class), false));
				
				Predicate[] pArray = new Predicate[pList.size()];
				pList.toArray(pArray);
				return cb.and(pArray);
			}
		};
		return vuDao.findAll(spec , PageRequest.of(0, 20, Sort.by(Direction.DESC, "id"))).getContent();
	}

	@Override
	public boolean downloadUrl(VideoUrl vu)
	{
		vu.setDownloaded(true);
		VideoUrl videoUrl = vuDao.save(vu);
		return videoUrl.getDownloaded();
	}

	@Override
	public VideoUrl getByFileName(String fileName)
	{
		List<VideoUrl> list = vuDao.findByFileName(fileName);
		if(list.size() == 1)
		{
			return list.get(0);
		}
		else if(list.size() > 1)
		{
			logger.error("++++++++++++++++++++++++++++++++++++++++");
			logger.error("========================================");
			logger.error("----------------------------------------");
			logger.error("查询文件:'" + fileName + "'出现重复");
			for(VideoUrl vu : list)
			{
				logger.error(vu.getId() + "出现重复");
			}
			logger.error("现在返回的是" + list.get(0).getId() + "的文件名"); 
			logger.error("----------------------------------------");
			logger.error("========================================");
			logger.error("++++++++++++++++++++++++++++++++++++++++");
			return list.get(0);
		}
		else
		{
			return null;
		}
	}
}
