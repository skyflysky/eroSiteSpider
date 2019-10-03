package sky.tool.spider.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import sky.tool.spider.entity.VideoPage;

@Repository
public interface VideoPageDao extends JpaRepository<VideoPage, Long> , JpaSpecificationExecutor<VideoPage>
{
	VideoPage findOneByWebId(Integer webid);
}
