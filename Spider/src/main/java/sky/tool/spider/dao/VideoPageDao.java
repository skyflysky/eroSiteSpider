package sky.tool.spider.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import sky.tool.spider.entity.VideoPage;

@Repository
public interface VideoPageDao extends JpaRepository<VideoPage, Long> , JpaSpecificationExecutor<VideoPage>
{
	/**
	 * 根据webId来查列表页
	 * @param webid
	 * @return
	 */
	VideoPage findOneByWebId(Integer webid);
	
	/**
	 * 根据webid 看有没有对应的实体类
	 * @param webid
	 * @return
	 */
	int countByWebId(Integer webid);
}
