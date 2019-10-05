package sky.tool.spider.service;

import java.io.File;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import sky.tool.spider.entity.NovelPage;
import sky.tool.spider.entity.NovelUrl;

@SuppressWarnings("restriction")
public interface NovelService
{

	NovelPage insertNovelPage(NovelPage novelPage) throws MySQLIntegrityConstraintViolationException;

	List<NovelPage> findUnloadPage();

	NovelPage getNovelPageByWebId(Integer webId);
	
	NovelUrl getNovelUrlByPage(NovelPage novelPage);

	NovelUrl novelUrlDownloed(NovelPage novelPage, File targetFile);
}
