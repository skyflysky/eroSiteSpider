package sky.tool.spider.config;

import java.net.SocketTimeoutException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.HttpClientDownloader;

@Component
public class SkyDownloader extends HttpClientDownloader
{
	private Logger logger = Logger.getLogger(SkyDownloader.class);
	
	public static Site sukeibeiSite = Site.me()
			.setRetryTimes(3)
			.setRetrySleepTime(1500)
			.setCycleRetryTimes(10)
			.setSleepTime(500)
			.addCookie(".nyaa.fun", "__cfduid", "d319e4f93d4e6ed741321ad1763f7537d1586251669")
			.addCookie(".nyaa.fun", "_ga", "GA1.2.1213555172.1586251672")
			.addCookie(".nyaa.fun", "_gat_gtag_UA_144337329_2", "1")
			.addCookie(".nyaa.fun", "_gid", "GA1.2.1901076260.1588587312")
			.addCookie(".nyaa.fun","iads", "alive")
			.addCookie("nyaa.fun", "splash_i", "false")
			.setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
	
	public static Site site = Site.me()
			.setRetryTimes(3)
			.setRetrySleepTime(1500)
			.setCycleRetryTimes(10)
			.setSleepTime(500)
			.setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
	
	@Override
	public Page download(Request request, Task task)
	{
		Page p = null;
		try
		{
			p = dwonload(request, task);
		}
		catch (SocketTimeoutException e)
		{
			logger.info("读取超时:" + request.getUrl());
			p = new Page();
			p.setDownloadSuccess(false);
		}
		catch (Exception e) 
		{
			logger.error("下载出错:" + request.getUrl(),e);
			p = new Page();
			p.setDownloadSuccess(false);
		}
		return p;
	}
	
	private Page dwonload(Request request , Task task) throws java.net.SocketTimeoutException
	{
		return super.download(request, task);
	}
	
	/*
	 java.net.SocketTimeoutException: Read timed out
	at java.net.SocketInputStream.socketRead0(Native Method) ~[na:1.8.0_161]
	at java.net.SocketInputStream.socketRead(SocketInputStream.java:116) ~[na:1.8.0_161]
	at java.net.SocketInputStream.read(SocketInputStream.java:171) ~[na:1.8.0_161]
	at java.net.SocketInputStream.read(SocketInputStream.java:141) ~[na:1.8.0_161]
	at sun.security.ssl.InputRecord.readFully(InputRecord.java:465) ~[na:1.8.0_161]
	at sun.security.ssl.InputRecord.read(InputRecord.java:503) ~[na:1.8.0_161]
	at sun.security.ssl.SSLSocketImpl.readRecord(SSLSocketImpl.java:983) ~[na:1.8.0_161]
	at sun.security.ssl.SSLSocketImpl.readDataRecord(SSLSocketImpl.java:940) ~[na:1.8.0_161]
	at sun.security.ssl.AppInputStream.read(AppInputStream.java:105) ~[na:1.8.0_161]
	at org.apache.http.impl.io.SessionInputBufferImpl.streamRead(SessionInputBufferImpl.java:137) ~[httpcore-4.4.12.jar:4.4.12]
	at org.apache.http.impl.io.SessionInputBufferImpl.fillBuffer(SessionInputBufferImpl.java:153) ~[httpcore-4.4.12.jar:4.4.12]
	at org.apache.http.impl.io.SessionInputBufferImpl.readLine(SessionInputBufferImpl.java:280) ~[httpcore-4.4.12.jar:4.4.12]
	at org.apache.http.impl.conn.DefaultHttpResponseParser.parseHead(DefaultHttpResponseParser.java:138) ~[httpclient-4.5.9.jar:4.5.9]
	at org.apache.http.impl.conn.DefaultHttpResponseParser.parseHead(DefaultHttpResponseParser.java:56) ~[httpclient-4.5.9.jar:4.5.9]
	at org.apache.http.impl.io.AbstractMessageParser.parse(AbstractMessageParser.java:259) ~[httpcore-4.4.12.jar:4.4.12]
	at org.apache.http.impl.DefaultBHttpClientConnection.receiveResponseHeader(DefaultBHttpClientConnection.java:163) ~[httpcore-4.4.12.jar:4.4.12]
	at org.apache.http.impl.conn.CPoolProxy.receiveResponseHeader(CPoolProxy.java:157) ~[httpclient-4.5.9.jar:4.5.9]
	at org.apache.http.protocol.HttpRequestExecutor.doReceiveResponse(HttpRequestExecutor.java:273) ~[httpcore-4.4.12.jar:4.4.12]
	at org.apache.http.protocol.HttpRequestExecutor.execute(HttpRequestExecutor.java:125) ~[httpcore-4.4.12.jar:4.4.12]
	at org.apache.http.impl.execchain.MainClientExec.execute(MainClientExec.java:272) ~[httpclient-4.5.9.jar:4.5.9]
	at org.apache.http.impl.execchain.ProtocolExec.execute(ProtocolExec.java:186) ~[httpclient-4.5.9.jar:4.5.9]
	at org.apache.http.impl.execchain.RetryExec.execute(RetryExec.java:89) ~[httpclient-4.5.9.jar:4.5.9]
	at org.apache.http.impl.execchain.RedirectExec.execute(RedirectExec.java:110) ~[httpclient-4.5.9.jar:4.5.9]
	at org.apache.http.impl.client.InternalHttpClient.doExecute(InternalHttpClient.java:185) ~[httpclient-4.5.9.jar:4.5.9]
	at org.apache.http.impl.client.CloseableHttpClient.execute(CloseableHttpClient.java:83) ~[httpclient-4.5.9.jar:4.5.9]
	at us.codecraft.webmagic.downloader.HttpClientDownloader.download(HttpClientDownloader.java:85) ~[webmagic-core-0.7.3.jar:na]
	at sky.tool.spider.config.SkyDownloader.dwonload(SkyDownloader.java:39) [classes/:na]
	at sky.tool.spider.config.SkyDownloader.download(SkyDownloader.java:22) [classes/:na]
	at us.codecraft.webmagic.Spider.processRequest(Spider.java:404) [webmagic-core-0.7.3.jar:na]
	at us.codecraft.webmagic.Spider.access$000(Spider.java:61) [webmagic-core-0.7.3.jar:na]
	at us.codecraft.webmagic.Spider$1.run(Spider.java:320) [webmagic-core-0.7.3.jar:na]
	at us.codecraft.webmagic.thread.CountableThreadPool$1.run(CountableThreadPool.java:74) [webmagic-core-0.7.3.jar:na]
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149) [na:1.8.0_161]
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624) [na:1.8.0_161]
	at java.lang.Thread.run(Thread.java:748) [na:1.8.0_161]
	 */
}
