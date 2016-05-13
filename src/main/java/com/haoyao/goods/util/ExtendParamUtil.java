package com.haoyao.goods.util;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLDecoder;
import java.util.Properties;

public class ExtendParamUtil {

	/**
	 * 服务地址前缀
	 */
	public static String ACTIONPREFIX;
	/**
	 * 财付通商户身份标�?
	 */
	public static String PARTNERID;
	/**
	 * 财付通商户权限密钥Key
	 */
	public static String PATERNERKEY;
	/**
	 * 公众号支付请求中用于加密的密钥Key�?
	 * 可验证商户唯�?��份，PaySignKey 对应于支付场景中的appKey 值�?
	 */
	public static String APPKEY;
	/**
	 * access_token接口地址前缀
	 */
	public static String ACCESSTOKENADDRPREFIX;

	/**
	 * 获取access_token必填
	 */
	public static String GRANTTYPE;

	/**
	 * 第三方用户唯�?���?
	 */
	public static String APPID;

	/**
	 * 第三方用户唯�?��证密钥，既appsecret
	 */
	public static String SECRET;
	/**
	 * 创建公众号自定义菜单api前缀
	 */
	public static String CREATEMENUADDRPREFIX;
	/**
	 * 创建二维码ticket的api前缀
	 */
	public static String CREATEQRCODETICKETADDRPREFIX;
	/**
	 * 通过ticket换取二维码的api前缀
	 */
	public static String CREATEQRCODEADDRPREFIX;
	
	/**
	 * 公众号发送消息接口前�?
	 */
	public static String SENDMESSAGEPREFIX;
	
//	/**
//	 * �?我的订单
//	 */
//	public static String MYORDER;
//	
//	/**
//	 * 服务�?维权
//	 */
//	public static String SRLTS;
//	
//	/**
//	 * 服务�?使用帮助
//	 */
//	public static String USEHELP;
//	
//	
//	/**
//	 * 值得�?本周推荐
//	 */
//	public static String RECOMMEND;
//	
//	/**
//	 * 值得�?精�?商品
//	 */
//	public static String CHOICENESS;
	
	/**
	 * 图面地址
	 */
	public static String IMAGERESOURCE;
	/**
	 * 药师�?药师关�?
	 */
	public static String PHASTSHOW;
	
	/**
	 * 	#药师�?健康导航
	 */
	public static String HEALTHNAVIG;
	
	/**
	 * 药师�?时令养生
	 */
	public static String PRESERVE;
	
	/**
	 * 药师�?药师观察
	 */
	public static String PHASTSURVEY;
	
	/**
	 * 药师�?限时优惠
	 */
	public static String TIMEFAVORABLE;
	
	
	/**
	 * �?账号绑定
	 */
	public static String MYACCOUNT;
	/**
	 * 药品属�?分隔�?
	 */
	public static String DRUGPROPERTYSPLIT;
	/**
	 * 药品分隔�?
	 */
	public static String DRUGSPLIT;
	
	/**
	 * 发货通知接口
	 * */
	public static String DLIVERNOTIFYPREFIX;
	/**
	 * 订单查询接口
	 * */
	public static String ORDERQUERYPREFIX;
	/**
	 * 微信用户查询接口
	 * */
	public static String USERINFOPREFIX;
	/**
	 * �?服务查询
	 */
	public static String MYSERVEINQ;
	
	/**
	 * �?售后维权
	 */
	public static String MYAFTERSAFEGUARD;
	
	/**
	 * �?订单服务
	 */
	public static String MYORDERSERVE;
	
	/**
	 * �?订单查询
	 */
	public static String MYORDER;
	
	/**
	 * �?服药助手
	 */
	public static String MYTAKEHELPER;
	
	/**
	 * �?云药�?
	 */
	public static String MYCLOUDMEDICAL;
	

	/**
	 * 精挑细�?-精品购物
	 */
	public static String QUALITYSHOP;
	/**
	 * 投诉用户接口前缀
	 */
	public static String COMPLAINT;
	public static String JSAPIADDRESS; 
	/**
	 * 微信支付成功模板消息模板id
	 */
	public static String PAYTEMPLATEID;
	/**
	 * 微信商品已发出模板消息模板id
	 */
	public static String GOODISSEND;
	
	public static String SAFESTOCK;
	/**
	 * 获取OauthAccessToken失效次数
	 */
	public static int OAUTHACCESSTOKENFAILNUM; 
	/**
	 * 订单会员接口url
	 */
	public static String MEMBERURL;
	/**
	 * 获取订单会员接口定时器启动间隔
	 */
	public static String TIMENUM;
	/**
	 * 获取订单会员接口秘钥
	 */
	public static String MEMBERKEY;
	/**
	 * 获取订单会员接口分页每页条数
	 */
	public static String MEMPAGESIZE;
	static {
		try {
			String classPath = URLDecoder.decode(ExtendParamUtil.class
					.getResource("/").getFile(), "utf-8");
			Properties props = new Properties();
			File file = new File(classPath + "extend.properties");
			props.load(new FileInputStream(file));
			/**订单会员获取所需参数*/
			MEMBERURL = props.getProperty("memberUrl");
			TIMENUM = props.getProperty("timeNum");
			MEMBERKEY = props.getProperty("key");
			MEMPAGESIZE = props.getProperty("memPageSize");
			
			ACTIONPREFIX = props.getProperty("actionPrefix");
			PARTNERID=props.getProperty("partnerId");
			PATERNERKEY = props.getProperty("paternerKey");
			APPKEY = props.getProperty("appKey");
			ACCESSTOKENADDRPREFIX = props.getProperty("accessTokenAddrPrefix");
			GRANTTYPE = props.getProperty("grant_type");
			APPID = props.getProperty("appid");
			SECRET = props.getProperty("secret");
			CREATEMENUADDRPREFIX = props.getProperty("createMenuAddrPrefix");
			CREATEQRCODETICKETADDRPREFIX=props.getProperty("createQRCodeTicketAddrPrefix");
			CREATEQRCODEADDRPREFIX=props.getProperty("createQRCodeAddrPrefix");
			SENDMESSAGEPREFIX = props.getProperty("sendMessagePrefix");
			PHASTSHOW=props.getProperty("phastshow");
			HEALTHNAVIG=props.getProperty("healthnavig");
			PRESERVE=props.getProperty("preserve");
			PHASTSURVEY=props.getProperty("phastsurvey");
			TIMEFAVORABLE=props.getProperty("timefavorable");
			MYACCOUNT=props.getProperty("myaccount");
			MYSERVEINQ=props.getProperty("myserveinq");
			MYAFTERSAFEGUARD=props.getProperty("myaftersafeguard");
			MYORDERSERVE=props.getProperty("myorderserve");
			MYTAKEHELPER=props.getProperty("mytakehelper");
			QUALITYSHOP=props.getProperty("qualityshop");
			IMAGERESOURCE=props.getProperty("ImageResource");
			DRUGPROPERTYSPLIT=props.getProperty("drugPropertySplit");
			DRUGSPLIT=props.getProperty("drugSplit");
			DLIVERNOTIFYPREFIX=props.getProperty("deliverNotifyPrefix");
			ORDERQUERYPREFIX=props.getProperty("orderQueryPrefix");
			USERINFOPREFIX=props.getProperty("userInfoPrefix");
			JSAPIADDRESS=props.getProperty("jsapi_address");
			COMPLAINT=props.getProperty("complaint");
			PAYTEMPLATEID=props.getProperty("payTemplate_id");
			GOODISSEND=props.getProperty("goodIsSend_id");
			JSAPIADDRESS=props.getProperty("jsapi_address");
			SAFESTOCK=props.getProperty("safe_stock");
			if(props.getProperty("oauthAccessTokenFailNum") != null && !"".equals(props.getProperty("oauthAccessTokenFailNum"))){
				OAUTHACCESSTOKENFAILNUM=new Integer(props.getProperty("oauthAccessTokenFailNum")).intValue();
			}
			
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
