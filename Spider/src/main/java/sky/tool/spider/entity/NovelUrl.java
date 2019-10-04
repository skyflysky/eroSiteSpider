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
@Table(name = "novel_url")
public class NovelUrl
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH}, optional = false , fetch = FetchType.LAZY)
	@JoinColumn(nullable = false , name ="page_id")
	private NovelPage page;
	
	@Column(nullable = false)
	private Boolean dawnload;
	
	@Column(nullable = true , columnDefinition = "text")
	private String localPath;
}
