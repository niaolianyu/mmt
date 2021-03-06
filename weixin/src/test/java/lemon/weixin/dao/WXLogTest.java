package lemon.weixin.dao;

import java.security.SecureRandom;
import java.util.Date;

import lemon.shared.entity.ServiceType;
import lemon.shared.log.bean.SiteAccess;
import lemon.shared.log.mapper.MMTLogManager;
import lemon.weixin.log.bean.MsgLog;
import lemon.weixin.log.bean.SubscribeLog;
import lemon.weixin.log.bean.UnSubscribeLog;
import lemon.weixin.log.mapper.WXLogManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(JUnit4.class)
public class WXLogTest {
	private static Log logger = LogFactory.getLog(WXLogTest.class);
	private WXLogManager wXLogManager;
	private MMTLogManager mmtLogManager;
	private ApplicationContext acx;

	@Before
	public void init() {
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		acx = new ClassPathXmlApplicationContext(resource);
		wXLogManager = acx.getBean(WXLogManager.class);
		mmtLogManager = acx.getBean(MMTLogManager.class);
		assertNotNull(wXLogManager);
		assertNotNull(mmtLogManager);
	}

	@Test
	public void siteAccessLog() {
		logger.info("begin...");
		for (int i = 0; i < 10; i++) {
			SecureRandom rnd = new SecureRandom();
			SiteAccess log = new SiteAccess();
			log.setSignature("signature123213123123123");
			log.setTimestamp_api(new Date().getTime() + "");
			log.setNonce(rnd.nextInt(10000) + "");
			log.setEchostr("echosresdadakdov");
			log.setCust_id(123);
			log.setToken("token");
			log.setService_type(ServiceType.WEIXIN);
			mmtLogManager.saveSiteAccessLog(log);
			logger.info("ID=" + log.getId());
			assertNotEquals(0, log.getId());
		}
		logger.info("end...");
	}
	@Test
	public void msgLog(){
		logger.info("begin...");
		for (int i = 0; i < 10; i++) {
			MsgLog receive = MsgLog.createReciveLog(123,"<xml><ToUserName><![CDATA[gh_de370ad657cf]]></ToUserName><FromUserName><![CDATA[ot9x4jpm4x_rBrqacQ8hzikL9D-M]]></FromUserName><CreateTime>1378001728</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[你好]]></Content><MsgId>5918472355591487529</MsgId></xml>");
			wXLogManager.saveMessageLog(receive);
			logger.info("ID=" + receive.getId());
			assertNotEquals(0, receive.getId());
			
			MsgLog send = MsgLog.createReciveLog(123,"<xml><ToUserName><![CDATA[gh_de370ad657cf]]></ToUserName><FromUserName><![CDATA[ot9x4jpm4x_rBrqacQ8hzikL9D-M]]></FromUserName><CreateTime>1378001728</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[你好]]></Content><MsgId>5918472355591487529</MsgId></xml>");
			wXLogManager.saveMessageLog(send);
			logger.info("ID=" + send.getId());
			assertNotEquals(0, send.getId());
		}
		logger.info("end...");
	}
	
	@Test
	public void subscribeLog(){
		SubscribeLog log = new SubscribeLog();
		log.setCust_id(100);
		log.setWxid("ot9x4jpm4x_rBrqacQ8hzikL9D-M");
		wXLogManager.saveSubscribeLog(log);
	}
	
	@Test
	public void unSubscribeLog(){
		UnSubscribeLog log = new UnSubscribeLog();
		log.setCust_id(100);
		log.setWxid("ot9x4jpm4x_rBrqacQ8hzikL9D-M");
		wXLogManager.saveUnSubscribeLog(log);
	}
}
