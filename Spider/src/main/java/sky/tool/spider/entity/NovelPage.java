package sky.tool.spider.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "novel_page")
public class NovelPage
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Integer webId;
	
	@Column(columnDefinition = "text" , nullable = false)
	private String pageUrl;
	
	@Column(columnDefinition = "text" , nullable = false)
	private String title;
	
	@Column(nullable = false)
	private Calendar uploadDate;
	
	@Column(nullable = false)
	private Calendar grabTime;
	
	@Column(nullable = false)
	private Boolean openAble;
	
	@Column(nullable = false)
	private Integer retryCount;
	
	@Column(nullable = false)
	private String type;
	
}
