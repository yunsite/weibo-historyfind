package com.taobao.zhangjun;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import weibo4j.Paging;
import weibo4j.Status;
import weibo4j.Weibo;
import weibo4j.WeiboException;
import weibo4j.http.AccessToken;

public class HistoryFind {
	
	public static final long CST_GMT_INTERVAL_TIME = 8L*60L*60L*1000L;
	public static final long DAY_TIME = 24L * 60L * 60L * 1000L;
	AccessToken access = null;
	private static final Log logger = LogFactory.getLog(HistoryFind.class);
	
	
	public HistoryFind(AccessToken access){
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
    	this.access = access;
	}
	
	private List<Status> getIntervalsMsgsByDays(int intervalDays) throws WeiboException, InterruptedException{
		List<Status> targetStatuses = new LinkedList<Status>();
		Weibo weibo = new Weibo();
		weibo.setToken(access.getToken(),access.getTokenSecret());
		Paging pag = new Paging();
		int pageIndex = 1;
		int pageOffset = 1;
		List<Status> statuses = null;
		boolean finish = false;
		do {
			pag.setPage(pageIndex);
			//pag.setCount(perPageCount);
			statuses = weibo.getUserTimeline(pag);
			for (Status status : statuses) {
				int chargeStatus = isTargetStatus(status,intervalDays);
				if( chargeStatus == 0 ){
					targetStatuses.add(status);
				}else if( chargeStatus < 0 ){ 
					finish = true;
					break;
				}
			}
			pageIndex = pageIndex + pageOffset;
			Thread.sleep(3000);
		} while (statuses.size() != 0 && finish == false );
		return targetStatuses;
	}
	
	/**
	 * 判断消息发送时间是否为目标日期
	 * @param status
	 * @param intervalDays
	 * @return
	 * -1：在目标日期之前
	 *  0：目标日期
	 *  1：在目标日期之后
	 */
	private int isTargetStatus(Status status, int intervalDays) {
		// TODO Auto-generated method stub
		Date statusDate = status.getCreatedAt();
		Date currentDate = new Date();
		Date statusSDate = DateUtil.getDateBeginTime(statusDate);
		Date targetSDate = DateUtil.getDateBeginTime(currentDate, -intervalDays);
		
		long statusBeginTime = statusSDate.getTime();
		long targetBeginTime = targetSDate.getTime();
		if( statusBeginTime < targetBeginTime ){
			return -1;
		}else if( statusBeginTime > targetBeginTime ){
			return 1;
		}else{
			return 0;
		}
	}

	private void repostMsg(Status status,int intervalDays) throws WeiboException{
		Weibo weibo = new Weibo();
		weibo.setToken(access.getToken(),access.getTokenSecret());
    	//args[2]：添加转发的信息
		String message = "原来"+intervalDays+"天前我说了这些... ...[囧]//@"+status.getUser().getName()+"："+status.getText();
		if( message.length() > 140 ){
			message = message.substring(0,140);
		}
    	weibo.repost(status.getId()+"", message);
	}
	
	public int findAndPublish(int intervalDays) throws WeiboException, InterruptedException {

		List<Status> intervalMsgs = null;
		intervalMsgs = getIntervalsMsgsByDays(intervalDays);
		
		for(Status status : intervalMsgs){
			repostMsg(status, intervalDays);
			Thread.sleep(1000);
		}
		int intervalMsgsCount = intervalMsgs.size();
		logger.info("intervalMsgsCount = " + intervalMsgsCount +",intervalDays = " + intervalDays);
		return intervalMsgsCount;
	}
}
