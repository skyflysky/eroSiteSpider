package sky.tool.spider.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import sky.tool.spider.dao.PicPageDao;
import sky.tool.spider.dao.PicUrlDao;
import sky.tool.spider.dto.PicDto;
import sky.tool.spider.entity.PicPage;
import sky.tool.spider.entity.PicUrl;
import sky.tool.spider.service.PictureService;
import sky.tool.spider.utils.SpringUtil;

@Service
public class PictureServiceImpl implements PictureService
{
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	PicUrlDao puDao;
	
	@Autowired
	PicPageDao ppDao;
	
	@SuppressWarnings("restriction")
	@Override
	public PicPage insertPicPage(PicPage picPage) throws com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException , NumberFormatException
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
	public boolean setPicPageOpenAble(boolean openAble, PicPage picPage)
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
	public int setPicPageCount(int picCount, PicPage picPage)
	{
		picPage.setPicCount(picCount);
		PicPage pp = ppDao.save(picPage);
		return pp.getPicCount();
	}
	
	@Override
	public PicUrl insertPicUrl(PicUrl picUrl)
	{
		return puDao.save(picUrl);
	}

	@Override
	public List<PicUrl> getUnLoadPicUrl()
	{
		Specification<PicUrl> spec = new Specification<PicUrl>()
		{
			private static final long serialVersionUID = 492698231191958811L;

			@Override
			public Predicate toPredicate(Root<PicUrl> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder)
			{
				List<Predicate> pList = new ArrayList<>();
				
				pList.add(criteriaBuilder.equal(root.get("dawnload").as(Boolean.class), false));
				pList.add(criteriaBuilder.lessThanOrEqualTo(root.get("reTryCount").as(Integer.class), new Integer(6)));
				
				Predicate[] pArray = new Predicate[pList.size()];
				pList.toArray(pArray);
				return criteriaBuilder.and(pArray);
			}
		};
		return puDao.findAll(spec);
	}

	@Override
	public boolean markPicUrlDownloaded(Long id , String path)
	{
		PicUrl picUrl = null;
		try
		{
			picUrl = puDao.getOne(id);
			picUrl.setLocalPath(path);
			picUrl.setDawnload(true);
			picUrl.setSideways(SpringUtil.isPictureSideways(new File(path)));
		} catch (Exception e)
		{
			logger.error("更新图片页时抛出异常" , e);
		}
		return puDao.save(picUrl).getDawnload();
	}

	@Override
	public List<PicDto> getLoadedPicDto(Long id)
	{
		return getPicUrlList(ppDao.getOne(id));
	}

	private List<PicDto> getPicUrlList(PicPage page)
	{
		page.setReadCount(page.getReadCount() + 1);
		ppDao.save(page);
		List<PicDto> stringList = new ArrayList<>();
		for(PicUrl pu : page.getPics())
		{
			if(pu.getDawnload()) 
			{
				stringList.add(new PicDto(pu.getId(), pu.getUrl().replace("https://", "http://127.0.0.1:43960/"), pu.getPage().getId()));
			}
		}
		stringList.sort(getUrlPageSort());
		return stringList;
	}

	private Comparator<? super PicDto> getUrlPageSort()
	{
		return new Comparator<PicDto>()
		{
			@Override
			public int compare(PicDto o1, PicDto o2)
			{
				try
				{
					String[] a1 = o1.getUrl().split("/");
					String[] a2 = o2.getUrl().split("/");
					String[] a11 = a1[a1.length - 1].split(".");
					String[] a22 = a2[a2.length - 1].split(".");
					Integer i1 = Integer.valueOf(a11[1]);
					Integer i2 = Integer.valueOf(a22[1]);
					return i1 - i2;
				} 
				catch (Exception e)
				{
					return 0;
				}
			}
		};
	}

	@Override
	public List<PicDto> getNextPicDto(Long id)
	{
		PicPage pp = getNextPage(ppDao.getOne(id));
		return getPicUrlList(pp);
	}

	private PicPage getNextPage(PicPage pp)
	{
		Specification<PicPage> spec = new Specification<PicPage>()
		{
			private static final long serialVersionUID = -6790829260448678665L;

			@Override
			public Predicate toPredicate(Root<PicPage> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder)
			{
				List<Predicate> pList = new ArrayList<>();
				pList.add(criteriaBuilder.equal(root.get("type").as(String.class), pp.getType()));
				pList.add(criteriaBuilder.greaterThan(root.get("id").as(Long.class),pp.getId()));
				Predicate[] pArray = new Predicate[pList.size()];
				pList.toArray(pArray);
				return criteriaBuilder.and(pArray);
			}
		};
		
		Pageable page = PageRequest.of(0, 1);
		List<PicPage> picPageList = ppDao.findAll(spec ,page).getContent();
		return picPageList.get(0);
	}

	@Override
	public void delePhoto(Long id)
	{
		try
		{
			PicUrl pu = puDao.getOne(id);
			File pic = new File(pu.getLocalPath());
			pic.delete();
			pu.setDawnload(false);
			pu.setLocalPath(null);
			puDao.save(pu);
		} catch (Exception e)
		{
			logger.error("删除图片错误");
			e.printStackTrace();
		}
	}

	@Override
	public void tryDownloadPicUrl(Long id)
	{
		PicUrl pu = puDao.getOne(id);
		pu.setReTryCount(pu.getReTryCount() + 1);
		puDao.save(pu);
	}
}
