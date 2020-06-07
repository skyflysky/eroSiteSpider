package sky.tool.spider.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@ConditionalOnProperty(prefix="work" , name = "mode" ,havingValue = "grab")
@Component
public class AutoGrabTask extends AbstractTask
{
	@Autowired
	VideoPageTask vpt;
	
	@Autowired
	VideoUrlTask vut;
	
	@Autowired
	PicPageTask ppt;
	
	@Autowired
	PicUrlTask put;
	
	@Autowired
	NovelPageTask npt;
	
	@Autowired
	NovelUrlTask nut;
	
	@Autowired
	SukebeiNyaaFunGrabTask st;

	@Override
	void doWork()
	{
		logger.info("auto grab start");
		st.doWork();
		vpt.doWork();
		vut.doWork();
		ppt.doWork();
		put.doWork();
		npt.doWork();
		nut.doWork();
		logger.info("auto grab end");
	}

	@Override
	boolean isWork()
	{
		return "auto".equals(grabMode);
	}

}
