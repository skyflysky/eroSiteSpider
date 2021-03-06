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
/**
 * 文章列表页实体
 * @author skygd
 *
 */
@Entity
@Table(name = "novel_page" , uniqueConstraints = {@UniqueConstraint(columnNames = "webId")})
public class NovelPage
{
	public static final String ngateMark = "xiaoshuo";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * webId 唯一约束 截取自网页的命名 是网站给的ID
	 */
	@Column(nullable = false)
	private Integer webId;
	
	/**
	 * 所指向的小说详情页的相对路径URL
	 */
	@Column(columnDefinition = "text" , nullable = false)
	private String pageUrl;
	
	/**
	 * 小说的标题
	 */
	@Column(columnDefinition = "text" , nullable = false)
	private String title;
	
	/**
	 * 小说的上传日期
	 */
	@Column(nullable = false)
	private Calendar uploadDate;
	
	/**
	 * 小说的抓取日期
	 */
	@Column(nullable = false)
	private Calendar grabTime;
	
	/**
	 * 所指向的小说详情页是否能打开
	 */
	@Column(nullable = false)
	private Boolean openAble;
	
	/**
	 * 尝试打开小说详情页的次数
	 */
	@Column(nullable = false)
	private Integer retryCount;
	
	/**
	 * 小说的类型
	 */
	@Column(nullable = false)
	private String type;

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

	public NovelPage()
	{
		super();
	}

	public NovelPage(String pageUrl, String title, Calendar uploadDate, String type)
	{
		super();
		this.webId = SpringUtil.getWebIdFromUrl(pageUrl, ngateMark);
		this.pageUrl = pageUrl;
		this.title = title;
		this.uploadDate = uploadDate;
		this.grabTime = Calendar.getInstance();
		this.openAble = false;
		this.retryCount = 0;
		this.type = type;
	}
	
	
	
}
