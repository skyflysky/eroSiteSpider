package sky.tool.spider.entity;

import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import sky.tool.spider.constant.SukebeiType;
import sky.tool.spider.utils.SpringUtil;

@Entity
@Table(name = "sukebei_Page" , uniqueConstraints = {@UniqueConstraint(columnNames = "webId")})
public class SukebeiPage
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
	 * 磁链内的hash数据
	 */
	@Lob 
	@Basic(fetch = FetchType.LAZY) 
	@Column(columnDefinition="MEDIUMBLOB", nullable=true) 
	private byte[] hashData;
	
	/**
	 * 磁链内的hash值
	 */
	@Column(nullable = true)
	private String hash;
	
	/**
	 * 磁链的内容
	 */
	@Column(nullable = false , columnDefinition="text")
	private String magnet;
	
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
	@Column(nullable = true, columnDefinition="text")
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

	public byte[] getHashData()
	{
		return hashData;
	}

	public void setHashData(byte[] hashData)
	{
		this.hashData = hashData;
	}

	public String getHash()
	{
		return hash;
	}

	public void setHash(String hash)
	{
		this.hash = hash;
	}

	public String getMagnet()
	{
		return magnet;
	}

	public void setMagnet(String magnet)
	{
		this.magnet = magnet;
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

	public SukebeiPage()
	{
		super();
	}

	public SukebeiPage(Integer webId , SukebeiType type , String webName , String hash ,
			String magnet , Calendar publishDate ,String fileName)
	{
		super();
		this.webId = webId;
		this.type = type;
		this.webName = webName;
		try
		{
			this.hashData = SpringUtil.hexTobytes(hash);
		}
		catch (StringIndexOutOfBoundsException | NumberFormatException e)
		{
			this.hashData = null;
		}
		this.hash = hash;
		this.magnet = magnet;
		this.publishDate = publishDate;
		this.grabDate = Calendar.getInstance();
		this.downloaded = false;
		this.fileName = fileName;
	}
}
