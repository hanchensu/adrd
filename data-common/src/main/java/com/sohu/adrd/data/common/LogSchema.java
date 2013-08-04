package com.sohu.adrd.data.common;

public class LogSchema {

	public static final String[] COUNTINFO_SCHEMA = { "LogTime", "LogLevel",
			"ADID", "ADPID", "ADPOS", "ADP_X", "ADP_Y", "ADTYPE", "BROWSER",
			"CLICK_X", "CLICK_Y", "CONTENT_URL", "EXT", "FREQ", "GETURL",
			"IMPRESSIONID", "LATENCY", "MONITORKEY", "OS", "REFFER", "REGION",
			"REQTYPE", "RESOLUTION", "SUPPORT_FLASH", "SUV", "TIME", "TURN",
			"USERAGENT", "USERIP", "YYID", "e", "c", "PAGEID", "ADVERTISERID",
			"JS_VERSION", "BidPrice", "BidPrice2", "BidType", "BidType2",
			"CTR", "CTR2", "eCPM", "eCPM2", "ADGROUPID_MK", "ADVERTISERID_MK",
			"AdScore", "CAMPAIGNID_MK", "ED_CONTENT", "ED_STATUS", "LINEID_MK",
			"MATERIAL_MK" };

	public static final String[] GTR_SCHEMA = { "LogTime", "LogLevel", "AD_ID",
			"AD_SERVER_VERSION", "AdScore", "BROWSER", "BUCKET", "BidPrice",
			"BidPrice2", "BidType", "BidType2", "COLOR_BG", "COLOR_BORDER",
			"COLOR_FG_LINK", "COLOR_FG_TEXT", "COLOR_FG_TITLE",
			"CONTENT_LINKURL", "CREATIVE_URL", "CTR", "CTR2", "DEVICEID",
			"FONT_SIZE", "FROME_TYPE", "FreqCtl", "FreqNow", "GET_URL",
			"HEIGHT", "IMPRESSION_ID", "ITEM_SPACE_ID", "LATENCY", "LOCAL_IP",
			"LOG_TYPE", "MONITORKEY", "OS", "PageID", "Priority", "REF_URL",
			"REQ_TYPE", "SUV", "SUV_MUST", "TEXT_LEN", "TEXT_NUM",
			"TIME_STAMP", "TMODE", "TMP_HEIGHT", "TMP_WIDTH", "TURN",
			"USER_AGENT", "USER_IP", "USER_REGION", "WIDTH", "YYID", "eCPM",
			"eCPM2" };
	
	public static final String[] CM_SCHEMA = { "LogTime", "LogLevel", "EXTDATA","EXUID","MID","SUV","TAGS","Ver","YYID"};

	public static final String[] EX_SCHEMA = { "LogTime", "LogLevel", "DSPID",
			"BIDID", "IMPID", "MONITORKEY", "SUV", "ADID", "ADPID", "ADSIZE",
			"ADURL", "BIDFLOOR", "BIDPRICE", "SECONDPRICE", "STATUS",
			"LATENCY", "LOGTYPE" };

	public static final String[] CI_SEND_SCHEMA = { "id", "apid", "aid",
			"impid", "at", "type", "status" };

	public static final String[] GTR_RETURN_SCHEMA = { "id", "adid",
			"itempspaceid", "impression_id" };

	public static final String[] GTR_SEND_SCHEMA = { "id", "itemspaceid",
			"SUV", "status" };

	public static void main(String args[]) {
		for (String attr : COUNTINFO_SCHEMA) {
			System.out.println(attr.toLowerCase() + "\t\tSTRING,");
		}
	}

}
