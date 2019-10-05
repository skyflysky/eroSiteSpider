package sky.tool.spider.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import sky.tool.spider.entity.NovelPage;

public interface NovelPageDao extends JpaRepository<NovelPage, Long>  , JpaSpecificationExecutor<NovelPage>
{

}
