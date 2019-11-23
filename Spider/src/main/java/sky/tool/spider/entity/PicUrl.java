package sky.tool.spider.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 图片详情页
 * @author skygd
 *
 */
@Entity
@Table(name = "pic_url")
public class PicUrl
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 对应的列表页 这里是多对一 其他都是一对一！！
	 */
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH}, optional = false , fetch = FetchType.LAZY)
	@JoinColumn(name = "page_id" , nullable = false)
	private PicPage page;
	
	/**
	 * 图片的全网URL
	 */
	@Column(nullable = false , columnDefinition = "text")
	private String url;
	
	/**
	 * 图片是否已经被下载
	 */
	@Column(nullable = false)
	private Boolean dawnload;
	
	/**
	 * 本地储存的路径
	 */
	@Column(nullable = true , columnDefinition = "text")
	private String localPath;
	
	/**
	 * 图片的宽度/高度值
	 */
	@Column(nullable = true)
	private Double rate;
	
	/**
	 * 这张图片尝试下载的重试次数
	 */
	@Column(nullable = true)
	private Integer reTryCount;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public PicPage getPage()
	{
		return page;
	}

	public void setPage(PicPage page)
	{
		this.page = page;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public Boolean getDawnload()
	{
		return dawnload;
	}

	public void setDawnload(Boolean dawnload)
	{
		this.dawnload = dawnload;
	}

	public String getLocalPath()
	{
		return localPath;
	}

	public void setLocalPath(String localPath)
	{
		this.localPath = localPath;
	}

	public Double getRate()
	{
		return rate;
	}

	public void setRate(Double rate)
	{
		this.rate = rate;
	}
	
	public Integer getReTryCount()
	{
		return reTryCount;
	}

	public void setReTryCount(Integer reTryCount)
	{
		this.reTryCount = reTryCount;
	}

	public PicUrl()
	{
		super();
	}

	public PicUrl(PicPage page, String url)
	{
		super();
		this.page = page;
		this.url = url;
		this.dawnload = false;
		this.localPath = null;
		this.rate = new Double("1");
		this.reTryCount = 0;
	}
}
