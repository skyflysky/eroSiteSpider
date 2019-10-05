package sky.tool.spider.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import sky.tool.spider.entity.NovelPage;
import sky.tool.spider.entity.NovelUrl;

public interface NovelUrlDao extends JpaRepository<NovelUrl, Long> , JpaSpecificationExecutor<NovelUrl>
{

	NovelUrl findByPage(NovelPage novelPage);

}
