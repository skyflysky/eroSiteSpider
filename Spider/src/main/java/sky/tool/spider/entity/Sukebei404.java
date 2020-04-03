package sky.tool.spider.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Sukebei404
{
	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	@Id
	private Integer id;

	public Sukebei404(Integer id)
	{
		this.id = id;
	}

	public Sukebei404()
	{
		super();
	}
}
