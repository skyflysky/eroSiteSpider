package sky.tool.spider.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import sky.tool.spider.entity.Sukebei404;

public interface Sukebei404Dao extends JpaRepository<Sukebei404, Integer>, JpaSpecificationExecutor<Sukebei404>
{

}
