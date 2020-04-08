package sky.tool.spider.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import sky.tool.spider.entity.Sukebei500502;
@Repository
public interface Sukebei50Dao extends JpaRepository<Sukebei500502, Integer> , JpaSpecificationExecutor<Sukebei500502>
{

}
