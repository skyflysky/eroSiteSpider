package sky.tool.spider.service;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import sky.tool.spider.entity.NovelPage;

@SuppressWarnings("restriction")
public interface NovelService
{

	NovelPage insertNovelPage(NovelPage novelPage) throws MySQLIntegrityConstraintViolationException;

}
