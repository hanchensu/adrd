namespace java sessionlog.op
struct AdInfoOperation {
	1: optional string adId,
	2: optional string adpId,
	3: optional string adPos,
	4: optional i32 adpX,
	5: optional i32 adpY,
	6: optional string adType,
	7: optional string browser,
	8: optional i32 clickX,
	9: optional i32 clickY,
	10: optional string contentUrl,
	11: optional string ext,
	12: optional string freq,
	13: optional string getUrl,
	14: optional string impressionId,
	15: optional i64 latency,
	16: optional string monitorKey,
	17: optional string os,
	18: optional string refer,
	19: optional string region,
	20: optional string reqType,
	21: optional string resolution,
	22: optional string suv,
	23: optional i64 timestamp,
	24: optional string turn,
	25: optional string userAgent,
	26: optional string userIp,
	27: optional string yyId,
	28: optional i32 repeat,
	29: optional string supportFlash,
	30: optional double e,
	31: optional double c,
	32: optional string pageId,
	33: optional i32 statusCode,
	34: optional string advertiserId,
	35: optional double bidPrice,
	36: optional string bidType,
	37: optional double bidPrice2,
	38: optional string bidType2,
	39: optional string jsVersion,
	40: optional double ctr,
	41: optional double ctr2,
	42: optional double eCPM,
	43: optional double eCPM2,
	44: optional string adgroupMK,
	45: optional string advertiserIdMK,
	46: optional string adScore,
	47: optional string campaignIdMK,
	48: optional string edContent,
	49: optional string edStatus,
	50: optional string lineIdMK,
	51: optional string materialMK,
}

struct PvOperation {
	1: optional string yyid
	2: optional string sohupass,
	3: optional string suv,
	4: optional string ip,
	5: optional string useragent,
	6: optional string region,
	7: optional i64 timestamp,
	8: optional string url,
	9: optional byte flag,
	10: optional string refUrl,
	11: optional i32 statusCode,
}

struct SearchOperation {
	1: optional string yyid
	2: optional string sohupass,
	3: optional string suv,
	4: optional string ip,
	5: optional string useragent,
	6: optional string region,
	7: optional i64 timestamp,
	8: optional string domain,
	9: optional string keywords,
	10: optional byte flag,
	11: optional i32 statusCode,
}

