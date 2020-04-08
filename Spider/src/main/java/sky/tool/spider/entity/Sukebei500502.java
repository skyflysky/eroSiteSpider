package sky.tool.spider.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Sukebei500502
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

	public Sukebei500502(Integer id)
	{
		this.id = id;
	}

	public Sukebei500502()
	{
		super();
	}
}
