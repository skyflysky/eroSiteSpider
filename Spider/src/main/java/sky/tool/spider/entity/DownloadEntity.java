package sky.tool.spider.entity;

import java.io.File;

public class DownloadEntity
{

	private String url;
	
	private File file;
	
	private Long id;
	
	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public File getFile()
	{
		return file;
	}

	public void setFile(File file)
	{
		this.file = file;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}
	
	public DownloadEntity(String url , File file , Long id)
	{
		super();
		this.url = url;
		this.file = file;
		this.id = id;
	}

	
}
