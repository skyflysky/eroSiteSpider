package sky.tool.spider.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import sky.tool.spider.entity.SukebeiPage;

@Repository
public interface SukebeiPageDao extends JpaRepository<SukebeiPage, Long> , JpaSpecificationExecutor<SukebeiPage>
{
	SukebeiPage findByWebId(Integer webId);
}
