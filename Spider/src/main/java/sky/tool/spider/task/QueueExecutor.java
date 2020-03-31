
package sky.tool.spider.task;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

@SuppressWarnings("serial")
/**
 *	一个多线程处理泛型的工具类
 *	子类继承本类并实现runMission方法，即可对泛型类进行多线程处理
 * 	正确调用顺序：实例化子类 -> add() -> start() -> add() -> isFinish() -> stop() -> isStop()
 */

public abstract class QueueExecutor<T> implements Serializable
{
	protected Logger logger = Logger.getLogger(QueueExecutor.class);

	/**
	 * 存储泛型的阻塞队列 如果队列满了 add()方法会停下。
	 */
	private LinkedBlockingQueue<T> queue;

	/**
	 * 多线程的列表
	 */
	private List<Runnable> runList;

	/**
	 * 实现本方法，对泛型类进行想要的处理。
	 * @throws Exception
	 */
	public abstract void runMission(Optional<T> opt) throws Exception;

	/**
	 * 运行标志
	 */
	private boolean runFlag = false;
	
	/**
	 * 每次尝试从队列中取出泛型对象的延时。
	 */
	private long timeout;

	/**
	 * 线程池
	 */
	private ExecutorService pool;

	/**
	 * 多线程实例
	 */
	private class subRunner implements Runnable
	{
		public void run()
		{
			//只有当运行标志true时 才会运行。
			while (runFlag)
			{
				try
				{
					//尝试从队列中取对象，同时进行延时处理。
					T t = queue.poll(timeout, TimeUnit.MILLISECONDS);
					//延时到了但队列中仍空，则会返回null。
					if (t != null)
					{
						runMission(Optional.of(t));
					}
				}
				catch (InterruptedException e)
				{
					logger.warn(e);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			return;
		}
	}

	/**
	 * 线程工厂，为子线程命名。格式：'泛型类名-线程池编号-queue-线程编号'
	 *
	 */
	public class NamedThreadFactory implements ThreadFactory
	{
		private final AtomicInteger poolNumber = new AtomicInteger(1);

		private final ThreadGroup threadGroup;

		private final AtomicInteger threadNumber = new AtomicInteger(1);

		public final String namePrefix;

		NamedThreadFactory(String name)
		{
			SecurityManager s = System.getSecurityManager();
			threadGroup = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
			if (null == name || "".equals(name.trim()))
			{
				name = "pool";
			}
			namePrefix = name + "-" + poolNumber.getAndIncrement() + "-queue-";
		}

		public Thread newThread(Runnable r)
		{
			Thread t = new Thread(threadGroup, r, namePrefix + threadNumber.getAndIncrement(), 0);
			if (t.isDaemon())
			{
				t.setDaemon(false);
			}
			if (t.getPriority() != Thread.NORM_PRIORITY)
			{
				t.setPriority(Thread.NORM_PRIORITY);
			}
			return t;
		}
	}
	public QueueExecutor()
	{
		this(25 , 10000l , 100);
	}
	
	public QueueExecutor(int thredCount , long timeOut , int queueSizeTimes)
	{
		this(thredCount , timeOut , queueSizeTimes , null);
	}
	
	public QueueExecutor(int thredCount , long timeOut , Collection<T> collection)
	{
		this(thredCount ,timeOut , null , collection);
	}

	/**
	 * 基本构造方法 queueSizeTimes 和 collection 同时应该只有一个生效。
	 * @param thredCount 共开启多少个子线程。 默认开启25个线程，最大不能超过100个线程
	 * @param timeOut 每个线程在尝试从队列中获取泛型对象时的等待时间，单位：毫秒。 默认等待10秒钟，最小不能小于100毫秒，最大不能超过1分钟。
	 * @param queueSizeTimes 队列倍增系数，用于没有初始元素的泛型队列初始化。每个线程可以在队列中扩展多少个。默认一百倍，最大不超过一万倍
	 * @param collection 泛型队列的初始元素集合。
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private QueueExecutor(Integer thredCount , Long timeOut , Integer queueSizeTimes , Collection<T> collection)
	{
		//参数效验
		if(thredCount < 0 || thredCount > 100)
		{
			thredCount = 25;
		}
		if(timeOut < 100 || timeOut > 60000)
		{
			timeOut = 10000l;
		}
		if(queueSizeTimes == null || queueSizeTimes < 0 || queueSizeTimes > 10000)
		{
			queueSizeTimes = 100;
		}
		//超时赋值
		this.timeout = timeOut;
		//获取泛型的类名
		Class t = (Class<T>) (((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
		String parameterizedTypeName = t.getSimpleName();
		//泛型队列初始化
		if(collection != null && !collection.isEmpty())
		{
			this.queue = new LinkedBlockingQueue<T>(collection);
		}
		else
		{
			this.queue = new LinkedBlockingQueue<T>(queueSizeTimes * thredCount);
		}
		//线程列表初始化
		runList = new ArrayList<Runnable>(thredCount);
		//新建线程池，由于本线程池完全不对外开放，所以采用这种模式新建线程池。
		this.pool = Executors.newCachedThreadPool(new NamedThreadFactory(parameterizedTypeName));
		for (int i = 0; i < thredCount; i++)
		{
			runList.add(new subRunner());
		}
	}

	/**
	 * 向泛型队列中添加泛型元素，如果队列已满会阻塞在这个方法内。
	 * @param t
	 */
	public final void add(Optional<T> t)
	{
		try
		{
			queue.put(t.get());
		}
		catch (InterruptedException e)
		{
			logger.error(e);
		}
	}
	
	/**
	 * 将多线程提交到线程池 ，开始处理，可以多次反复调用。
	 */
	public synchronized final void start()
	{
		if(!this.runFlag)
		{
			this.runFlag = true;
			for (Runnable r : runList)
			{
				this.pool.execute(r);
			}
		}
	}

	/**
	 * @return 是否所有多线程都闲下来了
	 */
	public final boolean isFinish()
	{
		return this.queue.isEmpty();
	}

	/**
	 * 立即停止所有多线程的工作，应该先确认所有多线程都已经闲下来了再停止。
	 */
	public final void stop()
	{
		this.pool.shutdown();
		this.runFlag = false;
	}

	/**
	 * 查看整个线程池所有线程是否都停止。
	 * @return
	 */
	public final boolean isStop()
	{
		return this.pool.isShutdown() && this.pool.isTerminated();
	}
	
	/**
	 * 查看当前队列长度
	 * @return 队列长度
	 */
	public final int getQueueLength()
	{
		return this.queue.size();
	}
}
