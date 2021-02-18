package com.exam.batch;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;


public class Ex3 {

	public static void main(String[] args) {
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		
		try {
			Scheduler scheduler = schedulerFactory.getScheduler();
			
			JobDetail jobDetail = JobBuilder.newJob(TestJob.class)
					.withIdentity("jobName", Scheduler.DEFAULT_GROUP)
					.build();
			
			Trigger trigger = TriggerBuilder.newTrigger()
					.withIdentity("triggerName", Scheduler.DEFAULT_GROUP)
					.withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
					.build();
			
			scheduler.scheduleJob(jobDetail, trigger);
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	} // main

}
