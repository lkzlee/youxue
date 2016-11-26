package com.youxue.core.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolUtil
{
	private final static ExecutorService exec = new ThreadPoolExecutor(15, 15, 60, TimeUnit.SECONDS,
			new LinkedBlockingQueue<Runnable>());

	public static void exec(Runnable r)
	{
		exec.execute(r);
	}
}
