package sky.tool.spider.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
	
	@Transactional
	@Modifying
	@Query(nativeQuery=true , value = "UPDATE pic_url SET dawnload=0,local_path=NULL,re_try_count=0,rate=1 WHERE id IN (:ids);")
	int redownload(@Param("ids")List<Long> idList);
}
