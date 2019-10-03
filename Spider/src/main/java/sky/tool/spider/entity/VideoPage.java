package sky.tool.spider.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import sky.tool.spider.utils.SpringUtil;

@Entity
@Table(name = "video_page" , uniqueConstraints = {@UniqueConstraint(columnNames = "webId")})

public class VideoPage
{
	public static final String ngateMark = "xiazai";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(columnDefinition = "text" , nullable = false)
	private String url;
	
	@Column(nullable = false)
	private Integer webId;
	
	@Column(nullable = false)
	private Calendar addTime;
	
	@Column(nullable = false)
	private Boolean openable;
	
	@Column(nullable = false)
	private Integer retryCount;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public Calendar getAddTime()
	{
		return addTime;
	}

	public void setAddTime(Calendar addTime)
	{
		this.addTime = addTime;
	}

	public Boolean getOpenable()
	{
		return openable;
	}

	public void setOpenable(Boolean openable)
	{
		this.openable = openable;
	}

	public Integer getRetryCount()
	{
		return retryCount;
	}

	public void setRetryCount(Integer retryCount)
	{
		this.retryCount = retryCount;
	}
	
	public Integer getWebId()
	{
		return webId;
	}

	public void setWebId(Integer webId)
	{
		this.webId = webId;
	}

	public VideoPage(String url) throws NumberFormatException
	{
		super();
		this.webId = SpringUtil.getWebIdFromUrl(url , ngateMark);
		this.addTime = Calendar.getInstance();
		this.openable = false;
		this.retryCount = 0;
		this.url = url;
	}

	public VideoPage()
	{
		super();
	}
}
