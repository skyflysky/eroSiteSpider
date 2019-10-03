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

@Entity
@Table(name = "pic_url")
public class PicUrl
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH}, optional = false , fetch = FetchType.LAZY)
	@JoinColumn(name = "page_id" , nullable = false)
	private PicPage page;
	
	@Column(nullable = false , columnDefinition = "text")
	private String url;
	
	@Column(nullable = false)
	private Boolean dawnload;
	
	@Column(nullable = true , columnDefinition = "text")
	private String localPath;

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
	}
}
