package sky.tool.spider.dto;

public class PicDto
{
	public Long id;
	
	public String url;
	
	public Long pid;

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

	public Long getPid()
	{
		return pid;
	}

	public void setPid(Long pid)
	{
		this.pid = pid;
	}

	public PicDto(Long id, String url, Long pid)
	{
		super();
		this.id = id;
		this.url = url;
		this.pid = pid;
	}
}
