package sky.tool.spider.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import sky.tool.spider.constant.SukebeiType;
import sky.tool.spider.entity.Sukebei;

@Repository
public interface SukebeiDao extends JpaRepository<Sukebei, Long> , JpaSpecificationExecutor<Sukebei>
{
	List<Sukebei> findByDownloaded(Boolean Downloaded);
	
	List<Sukebei> findByDownloadedAndType(Boolean Downloaded , SukebeiType sukebeiType);
	
	Sukebei findByWebId(Integer webId);
}
