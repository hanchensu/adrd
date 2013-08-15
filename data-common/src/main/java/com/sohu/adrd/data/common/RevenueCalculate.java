package com.sohu.adrd.data.common;



/**
 * @author huazhongwang@sohu-inc.com
 * @date 2013-8-13 下午2:53:20
 * 
 */
public class RevenueCalculate {

	/**
	 * @param bidType
	 * @param bidType2
	 * @param bidPrice
	 * @param bidPrice2
	 * @param ecpm
	 * @param ecpm2
	 * @param recordType 主要包括广告展示日志("addisplay")和广告点击日志("adclick")
	 * @param adtype
	 * @param priceType 按第一高价("price1")或者第二高价计费("price2")
	 * @return revenue
	 */
	public static double calculateRevenue(int bidType, int bidType2,
			double bidPrice, double bidPrice2, double ecpm, double ecpm2,
			String recordType, String adtype, String priceType) {
		double revenue = 0.0;
		//RTB广告类型，只能按照第一高价计费
		if ("5".equals(adtype) && "price2".equals(priceType)) {
			revenue = calculateRevenue(String.valueOf(bidType), String.valueOf(bidType2), bidPrice, bidPrice2,
					ecpm, ecpm2, recordType, "price1");

		} else {
			revenue = calculateRevenue(String.valueOf(bidType), String.valueOf(bidType2), bidPrice, bidPrice2,
					ecpm, ecpm2, recordType, priceType);
		}
		return revenue;
	}

	private static double calculateRevenue(String bidType, String bidType2,
			double bidPrice, double bidPrice2, double ecpm, double ecpm2,
			String recordType, String priceType) {
		double revenue = 0.0;
		// 展示(display)日志
		if (ConstData.CG_ADDISPLAY.equals(recordType)) {
			// 按第一高价(price1)计费
			if ("price1".equals(priceType)) {
				// 第一高价是cpm
				if ("1".equals(bidType)) {
					revenue = bidPrice / 100000.0;
				}
			}
			// 按第二高价(price2)计费
			if ("price2".equals(priceType)) {
				// 两个价格的计费方式相同,并且都是cpm
				if ("1".equals(bidType) && "1".equals(bidType2)) {
					revenue = bidPrice2 / 100000.0;
				}
				// 最高出价是cpm,第二高出价是cpc
				if ("1".equals(bidType) && "2".equals(bidType2)) {
					revenue = ecpm2 / 100000.0;
				}
				// 最高出价是cpc,第二高出价是cpm
				// 两个出价都是cpc
				// 这两种方式的产生的费用都在点击日志(click)里计算
			}
		}
		// 点击(click)日志
		if (ConstData.CG_ADCLICK.equals(recordType)) {
			// 按第一高价计费(price1)
			if ("price1".equals(priceType)) {
				// 第一高价为cpc
				if ("2".equals(bidType)) {
					revenue = bidPrice / 100.0;
				}
			}
			// 按第二高价计费(price2)
			if ("price2".equals(priceType)) {
				// 两个价格计费方式相同，并且都是cpc
				if ("2".equals(bidType) && "2".equals(bidType2)) {
					revenue = bidPrice2 / 100.0;
				}
				// 第一高价为cpc,第二高价为cpm
				if ("2".equals(bidType) && "1".equals(bidType2)) {
					revenue = (ecpm2 * bidPrice * 1.0) / (ecpm * 100.0);
				}
				// 第一高价为cpm,第二高价为cpc
				// 两个价格都为cpm
				// 这两种情况都在展示日志里计算
			}
		}
		return revenue;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Price:" + calculateRevenue(2, 2, 70, 80, 
				93.0609, 87.9363, "adclick","1", "price2"));
	}

}
