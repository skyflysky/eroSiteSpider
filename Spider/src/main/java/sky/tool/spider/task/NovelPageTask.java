package sky.tool.spider.task;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@ConditionalOnProperty(prefix="work" , name = "mode" ,havingValue = "grab")
@Component

public class NovelPageTask
{

}
