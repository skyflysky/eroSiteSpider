package sky.tool.spider.entity;

import java.util.Calendar;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import sky.tool.spider.utils.SpringUtil;
/**
 * 图片列表页实体
 * @author skygd
 *
 */
@Entity
@Table(name = "pic_page" , uniqueConstraints = {@UniqueConstraint(columnNames = "webId")})

public class PicPage
{
	public static final String ngateMark = "tupian";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 列表页对应的webId 由被抓取的页面得到
	 */
	@Column(nullable = false)
	private Integer webId;
	
	/**
	 * 封面图对应的Url
	 */
	@Column(columnDefinition = "text" , nullable = false)
	private String picUrl;
	
	/**
	 * 指向的详情页的相对路径Url
	 */
	@Column(columnDefinition = "text" , nullable = false)
	private String pageUrl;
	
	/**
	 * 封面标题
	 */
	@Column(columnDefinition = "text" , nullable = false)
	private String title;
	
	/**
	 * 上传的日期
	 */
	@Column(nullable = false)
	private Calendar uploadDate;
	
	/**
	 * 抓取的时间
	 */
	@Column(nullable = false)
	private Calendar grabTime;
	
	/**
	 * 对应的图片详情页 是否能打开
	 */
	@Column(nullable = false)
	private Boolean openAble;
	
	/**
	 * 尝试打开对应图片详情页的次数
	 */
	@Column(nullable = false)
	private Integer retryCount;
	
	/**
	 * 图片的风格
	 */
	@Column(nullable = false)
	private String type;
	
	/**
	 * 这个页面对应页面有多少张图片
	 */
	@Column(nullable = true)
	private Integer picCount;
	
	/**
	 * 这个页面被阅读了几次
	 */
	@Column(nullable = true)
	private Integer readCount;
	
	@OneToMany(mappedBy = "page" , cascade = { CascadeType.REFRESH, CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REMOVE },fetch = FetchType.LAZY )
	private Set<PicUrl> pics;
	
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Integer getWebId()
	{
		return webId;
	}

	public void setWebId(Integer webId)
	{
		this.webId = webId;
	}

	public String getPicUrl()
	{
		return picUrl;
	}

	public void setPicUrl(String picUrl)
	{
		this.picUrl = picUrl;
	}

	public String getPageUrl()
	{
		return pageUrl;
	}

	public void setPageUrl(String pageUrl)
	{
		this.pageUrl = pageUrl;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public Calendar getUploadDate()
	{
		return uploadDate;
	}

	public void setUploadDate(Calendar uploadDate)
	{
		this.uploadDate = uploadDate;
	}

	public Calendar getGrabTime()
	{
		return grabTime;
	}

	public void setGrabTime(Calendar grabTime)
	{
		this.grabTime = grabTime;
	}

	public Boolean getOpenAble()
	{
		return openAble;
	}

	public void setOpenAble(Boolean openAble)
	{
		this.openAble = openAble;
	}

	public Integer getRetryCount()
	{
		return retryCount;
	}

	public void setRetryCount(Integer retryCount)
	{
		this.retryCount = retryCount;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}
	
	public Integer getPicCount()
	{
		return picCount;
	}

	public void setPicCount(Integer picCount)
	{
		this.picCount = picCount;
	}

	public Integer getReadCount()
	{
		return readCount;
	}

	public void setReadCount(Integer readCount)
	{
		this.readCount = readCount;
	}

	public Set<PicUrl> getPics()
	{
		return pics;
	}

	public void setPics(Set<PicUrl> pics)
	{
		this.pics = pics;
	}

	public PicPage()
	{
		super();
	}

	public PicPage(String picUrl, String pageUrl, String title, Calendar uploadDate , String type) throws NumberFormatException
	{
		super();
		this.webId = SpringUtil.getWebIdFromUrl(pageUrl, ngateMark);
		this.picUrl = picUrl;
		this.pageUrl = pageUrl;
		this.title = title;
		this.uploadDate = uploadDate;
		this.type = type;
		this.grabTime = Calendar.getInstance();
		this.openAble = false;
		this.retryCount = 0;
		this.picCount = -1;
		this.readCount = 0;
	}

	
}
