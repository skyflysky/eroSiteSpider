package sky.tool.spider.entity;

import java.io.IOException;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import sun.misc.BASE64Decoder;

@Entity
@Table(name = "video_url" , indexes = @Index(columnList = "fileName"))
public class VideoUrl
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 对应哪个页面
	 */
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="page_Id")
	private VideoPage videoPage;
	
	/**
	 * 视频详情的链接页面
	 */
	@Column(columnDefinition="text",nullable=false)
	private String magnet;
	
	/**
	 * 视频文件的文件名，经解码后得到
	 */
	@Column(nullable = true)
	private String fileName;
	
	/**
	 * 视频的上传日期
	 */
	@Column(nullable = false)
	private Calendar uploadDate;
	
	/**
	 * 更新的时间
	 */
	@Column(nullable = false)
	private Calendar updateTime;
	
	/**
	 * 视频是否已经添加到下载
	 */
	@Column(nullable = false)
	private Boolean downloaded;
	
	/**
	 * 视频的类型
	 */
	@Column(nullable = false)
	private String type;
	
	/**
	 * 视频的标题
	 */
	@Column(columnDefinition = "text" ,nullable = false)
	private String name;
	
	/**
	 * 视频的封面图
	 */
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

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
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
		if(magnet.startsWith("thunder://"))
		{
			try
			{
				String n = new String(new BASE64Decoder().decodeBuffer(magnet.substring(10)));
				this.fileName = n.substring(n.lastIndexOf('/') + 1 ,n.length() - 2);
			} catch (IOException e)
			{
				e.printStackTrace();
				this.fileName = null;
			}
		}
		else if (magnet.startsWith("http"))
		{
			this.fileName = magnet.substring(magnet.lastIndexOf('/') + 1);
		}
		else
		{
			this.fileName = null;
		}
	}

	public VideoUrl()
	{
		super();
	}
}
