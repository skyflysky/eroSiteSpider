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

@Entity
@Table(name = "pic_page" , uniqueConstraints = {@UniqueConstraint(columnNames = "webId")})

public class PicPage
{
	public static final String ngateMark = "tupian";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Integer webId;
	
	@Column(columnDefinition = "text" , nullable = false)
	private String picUrl;
	
	@Column(columnDefinition = "text" , nullable = false)
	private String pageUrl;
	
	@Column(columnDefinition = "text" , nullable = false)
	private String title;
	
	@Column(nullable = false)
	private Calendar uploadDate;
	
	@Column(nullable = false)
	private Calendar grabTime;
	
	@Column(nullable = false)
	private Boolean openAble;
	
	@Column(nullable = false)
	private Integer retryCount;
	
	@Column(nullable = false)
	private String type;
	
	@Column(nullable = true)
	private Integer picCount;
	
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
	}

	
}
