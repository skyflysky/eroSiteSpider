package sky.tool.spider.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import sky.tool.spider.entity.PicUrl;

@Repository
public interface PicUrlDao extends JpaRepository<PicUrl, Long> , JpaSpecificationExecutor<PicUrl>
{
	/**
	 * 根据图片是否已经被下载来查询图片详情
	 * @param dawnload
	 * @return
	 */
	List<PicUrl> findByDawnload(Boolean dawnload);
	
}
