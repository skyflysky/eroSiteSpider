package sky.tool.spider.task;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import sky.tool.spider.entity.Sukebei;
import sky.tool.spider.service.SukebeiNyaaFunService;

@ConditionalOnProperty(prefix="work" , name = "mode" ,havingValue = "download")
@Component
public class SukebeiNyaaFunDownloadTask extends AbstractTask
{
	@Value("${sukebei.sql}")
	String sukebeiSql;
	
	@Value("${sukebei.thunderProgramPath}")
	String thunderProgramPath;
	
	@Value("${sukebei.thunderTorrentsPath}")
	String thunderTorrentsPath;
	
	@Autowired
	SukebeiNyaaFunService service;

	@Override
	void doWork()
	{
		logger.info("sukebei download start");
		logger.info(sukebeiSql);
		String sql = sukebeiSql.replaceAll("@!@", " ");
		try
		{
			Scanner scanner = new Scanner(System.in);
			logger.info("开始唤起迅雷，迅雷唤起后，输入任意字符以继续");
			Runtime.getRuntime().exec(thunderProgramPath);
			List<Sukebei> sukebeiList = service.getSukebeiBySql(sql);
			scanner.next();
			File torrentFatherFile = new File(thunderTorrentsPath);
			File torrentTargetFile = new File(storage , "torrent");
			if(!torrentTargetFile.exists())
			{
				torrentTargetFile.mkdir();
			}
			for(Sukebei sukebei : sukebeiList)
			{
				logger.info("开始下载:" + sukebei.getWebName());
				String magnet = sukebei.getMagnet();
				String torrentHashName = getTorrHash(magnet).toUpperCase() + ".torrent";
				Runtime.getRuntime().exec(thunderProgramPath + " " + magnet);
				scanner.next();
				File torrentFile = new File(torrentFatherFile, torrentHashName);
				if(!torrentFatherFile.exists())
				{
					logger.error("未找到'" + sukebei.getWebName() + "'的种子文件");
					break;
				}
				else
				{
					File targetTorrentFile = new File(torrentTargetFile , windowsFileFilter(sukebei.getWebName()) + torrentHashName);
					targetTorrentFile.createNewFile();
					FileUtils.copyFile(torrentFile, targetTorrentFile);
					service.setDownloaded(targetTorrentFile.getAbsolutePath(), sukebei);
					logger.info(sukebei.getWebName() + "已经添加到迅雷");
				}
			}
			scanner.close();
			logger.info("所有都下载了");
		}
		catch (IOException e)
		{
			logger.error(e);
			return;
		}
		logger.info("sukebei download finished");
	}

	private String getTorrHash(String magnet)
	{
		magnet = magnet.substring(magnet.indexOf("btih:"));
		int andIndex = magnet .indexOf("&");
		if(andIndex > 0)
		{
			return magnet.substring(5, andIndex);
		}
		else
		{
			return magnet.substring(5);
		}
	}
	
	private String windowsFileFilter(String org)
	{
		org.replace('\\', ' ');
		org.replace('/', ' ');
		org.replace(':', ' ');
		org.replace('*', ' ');
		org.replace('?', ' ');
		org.replace('"', ' ');
		org.replace('<', ' ');
		org.replace('>', ' ');
		org.replace('|', ' ');
		if(org.length() > 80)
		{
			org = org.substring(0, 80);
		}
		return org;
	}

	@Override
	boolean isWork()
	{
		return "sukebei".equals(downloadMode);
	}

}
