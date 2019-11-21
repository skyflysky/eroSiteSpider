package sky.tool.spider.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import sky.tool.spider.entity.PicPage;

@Repository
public interface PicPageDao extends JpaRepository<PicPage, Long> , JpaSpecificationExecutor<PicPage>
{
	/**
	 * 根据WebId来查列表页
	 * @param WebId
	 * @return
	 */
	PicPage findOneByWebId(Integer WebId);
}
