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
 * 文章详情页的实体类
 * @author skygd
 *
 */
@Entity
@Table(name = "novel_url")
public class NovelUrl
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 当前小说详情页归属于哪个小说列表页
	 */
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH}, optional = false , fetch = FetchType.LAZY)
	@JoinColumn(nullable = false , name ="page_id")
	private NovelPage page;
	
	/**
	 * 小说本体是否已经被下载
	 */
	@Column(nullable = false)
	private Boolean dawnload;
	
	/**
	 * 小说本体的本地绝对路径
	 */
	@Column(nullable = false , columnDefinition = "text")
	private String localPath;
	
	/**
	 * 小说本地的大小，过小的小说将不会被保存
	 */
	@Column(nullable = false)
	private Long size;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public NovelPage getPage()
	{
		return page;
	}

	public void setPage(NovelPage page)
	{
		this.page = page;
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

	public Long getSize()
	{
		return size;
	}

	public void setSize(Long size)
	{
		this.size = size;
	}

	public NovelUrl()
	{
		super();
	}

	public NovelUrl(NovelPage page, String localPath, Long size)
	{
		super();
		this.page = page;
		this.dawnload = true;
		this.localPath = localPath;
		this.size = size;
	}
	
	
}
