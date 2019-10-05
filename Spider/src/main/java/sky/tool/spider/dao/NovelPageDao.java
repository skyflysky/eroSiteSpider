package sky.tool.spider.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import sky.tool.spider.entity.NovelPage;

public interface NovelPageDao extends JpaRepository<NovelPage, Long>  , JpaSpecificationExecutor<NovelPage>
{
	List<NovelPage> findByOpenAble(Boolean openAble);
	
	NovelPage findByWebId(Integer webId);
}
