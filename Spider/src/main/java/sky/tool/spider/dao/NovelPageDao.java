package sky.tool.spider.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import sky.tool.spider.entity.NovelPage;

public interface NovelPageDao extends JpaRepository<NovelPage, Long>  , JpaSpecificationExecutor<NovelPage>
{
	/**
	 * 根据页面是否可以打开查询页面
	 * @param openAble 
	 * @return
	 */
	List<NovelPage> findByOpenAble(Boolean openAble);
	
	/**
	 * 根据webId 查询页面
	 * @param webId
	 * @return
	 */
	NovelPage findByWebId(Integer webId);
}
