package sky.tool.spider.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import sky.tool.spider.entity.VideoUrl;

@Repository
public interface VideoUrlDao extends JpaRepository<VideoUrl, Long> , JpaSpecificationExecutor<VideoUrl>
{

}
