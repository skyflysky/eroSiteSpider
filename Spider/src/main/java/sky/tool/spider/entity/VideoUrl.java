package sky.tool.spider.entity;

import java.util.Calendar;

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
@Table(name = "video_url")
public class VideoUrl
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="page_Id")
	private VideoPage videoPage;
	
	@Column(columnDefinition="text",nullable=false)
	private String magnet;
	
	@Column(nullable = false)
	private Calendar uploadDate;
	
	@Column(nullable = false)
	private Calendar updateTime;
	
	@Column(nullable = false)
	private Boolean downloaded;
	
	@Column(nullable = false)
	private String type;
	
	@Column(columnDefinition = "text" ,nullable = false)
	private String name;
	
	@Column(columnDefinition = "text" ,nullable = false)
	private String titlePhoto;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public VideoPage getVideoPage()
	{
		return videoPage;
	}

	public void setVideoPage(VideoPage videoPage)
	{
		this.videoPage = videoPage;
	}

	public String getMagnet()
	{
		return magnet;
	}

	public void setMagnet(String magnet)
	{
		this.magnet = magnet;
	}

	public Calendar getUploadDate()
	{
		return uploadDate;
	}

	public void setUploadDate(Calendar uploadDate)
	{
		this.uploadDate = uploadDate;
	}

	public Calendar getUpdateTime()
	{
		return updateTime;
	}

	public void setUpdateTime(Calendar updateTime)
	{
		this.updateTime = updateTime;
	}

	public Boolean getDownloaded()
	{
		return downloaded;
	}

	public void setDownloaded(Boolean downloaded)
	{
		this.downloaded = downloaded;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getTitlePhoto()
	{
		return titlePhoto;
	}

	public void setTitlePhoto(String titlePhoto)
	{
		this.titlePhoto = titlePhoto;
	}

	public VideoUrl(VideoPage vp, String magnet, Calendar uploadDate , String type , String picUrl , String name)
	{
		super();
		this.videoPage = vp;
		this.magnet = magnet;
		this.uploadDate = uploadDate;
		this.titlePhoto = picUrl;
		this.downloaded = false;
		this.name = name;
		this.type = type;
		this.updateTime = Calendar.getInstance();
	}
}
