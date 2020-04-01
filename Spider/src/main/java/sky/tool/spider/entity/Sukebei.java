package sky.tool.spider.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import sky.tool.spider.constant.SukebeiType;

@Entity
@Table(name = "sukebei" , uniqueConstraints = {@UniqueConstraint(columnNames = "webId")})
public class Sukebei
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * webId 唯一约束 截取自网页的命名 是网站给的ID
	 */
	@Column(nullable = false)
	private Integer webId;
	
	/**
	 * 资源类型
	 */
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private SukebeiType type;
	
	/**
	 * 网页的名字
	 */
	@Column(nullable = false)
	private String webName;
	
	/**
	 * 下一级网页的URL
	 */
	@Column(nullable = false  , columnDefinition="text")
	private String link;
	
	/**
	 * 磁链的内容
	 */
	@Column(nullable = false , columnDefinition="text")
	private String magnet;
	
	/**
	 * 文本格式的资源大小
	 */
	@Column(nullable = false)
	private String size;
	
	/**
	 * 资源发布时日期
	 */
	@Column(nullable = false)
	private Calendar publishDate;
	
	/**
	 * 资源抓取日期
	 */
	@Column(nullable = false)
	private Calendar grabDate;
	
	/**
	 * 是否被下载
	 */
	@Column(nullable = false)
	private Boolean downloaded;

	/**
	 * 通过磁链dn获取到的文件名
	 */
	@Column(nullable = true)
	private String fileName;
	
	/**
	 * 下载后另存的种子路径
	 */
	@Column(nullable = true)
	private String torrentPath;
	
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

	public SukebeiType getType()
	{
		return type;
	}

	public void setType(SukebeiType type)
	{
		this.type = type;
	}

	public String getWebName()
	{
		return webName;
	}

	public void setWebName(String webName)
	{
		this.webName = webName;
	}

	public String getLink()
	{
		return link;
	}

	public void setLink(String link)
	{
		this.link = link;
	}

	public String getMagnet()
	{
		return magnet;
	}

	public void setMagnet(String magnet)
	{
		this.magnet = magnet;
	}

	public String getSize()
	{
		return size;
	}

	public void setSize(String size)
	{
		this.size = size;
	}

	public Calendar getPublishDate()
	{
		return publishDate;
	}

	public void setPublishDate(Calendar publishDate)
	{
		this.publishDate = publishDate;
	}

	public Calendar getGrabDate()
	{
		return grabDate;
	}

	public void setGrabDate(Calendar grabDate)
	{
		this.grabDate = grabDate;
	}

	public Boolean getDownloaded()
	{
		return downloaded;
	}

	public void setDownloaded(Boolean downloaded)
	{
		this.downloaded = downloaded;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public String getTorrentPath()
	{
		return torrentPath;
	}

	public void setTorrentPath(String torrentPath)
	{
		this.torrentPath = torrentPath;
	}

	public Sukebei()
	{
		super();
	}

	public Sukebei(Integer webId , SukebeiType type , String webName , String link , String magnet ,
			String size , Calendar publishDate ,String fileName)
	{
		super();
		this.webId = webId;
		this.type = type;
		this.webName = webName;
		this.link = link;
		this.magnet = magnet;
		this.size = size;
		this.publishDate = publishDate;
		this.grabDate = Calendar.getInstance();
		this.downloaded = false;
		this.fileName = fileName;
	}

	
	
}
