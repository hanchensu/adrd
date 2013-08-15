package com.sohu.adrd.data.common;



/**
 * @author huazhongwang@sohu-inc.com
 * @date 2013-8-13 ����2:53:20
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
	 * @param recordType ��Ҫ�������չʾ��־("addisplay")�͹������־("adclick")
	 * @param adtype
	 * @param priceType ����һ�߼�("price1")���ߵڶ��߼ۼƷ�("price2")
	 * @return revenue
	 */
	public static double calculateRevenue(int bidType, int bidType2,
			double bidPrice, double bidPrice2, double ecpm, double ecpm2,
			String recordType, String adtype, String priceType) {
		double revenue = 0.0;
		//RTB������ͣ�ֻ�ܰ��յ�һ�߼ۼƷ�
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
		// չʾ(display)��־
		if (ConstData.CG_ADDISPLAY.equals(recordType)) {
			// ����һ�߼�(price1)�Ʒ�
			if ("price1".equals(priceType)) {
				// ��һ�߼���cpm
				if ("1".equals(bidType)) {
					revenue = bidPrice / 100000.0;
				}
			}
			// ���ڶ��߼�(price2)�Ʒ�
			if ("price2".equals(priceType)) {
				// �����۸�ļƷѷ�ʽ��ͬ,���Ҷ���cpm
				if ("1".equals(bidType) && "1".equals(bidType2)) {
					revenue = bidPrice2 / 100000.0;
				}
				// ��߳�����cpm,�ڶ��߳�����cpc
				if ("1".equals(bidType) && "2".equals(bidType2)) {
					revenue = ecpm2 / 100000.0;
				}
				// ��߳�����cpc,�ڶ��߳�����cpm
				// �������۶���cpc
				// �����ַ�ʽ�Ĳ����ķ��ö��ڵ����־(click)�����
			}
		}
		// ���(click)��־
		if (ConstData.CG_ADCLICK.equals(recordType)) {
			// ����һ�߼ۼƷ�(price1)
			if ("price1".equals(priceType)) {
				// ��һ�߼�Ϊcpc
				if ("2".equals(bidType)) {
					revenue = bidPrice / 100.0;
				}
			}
			// ���ڶ��߼ۼƷ�(price2)
			if ("price2".equals(priceType)) {
				// �����۸�Ʒѷ�ʽ��ͬ�����Ҷ���cpc
				if ("2".equals(bidType) && "2".equals(bidType2)) {
					revenue = bidPrice2 / 100.0;
				}
				// ��һ�߼�Ϊcpc,�ڶ��߼�Ϊcpm
				if ("2".equals(bidType) && "1".equals(bidType2)) {
					revenue = (ecpm2 * bidPrice * 1.0) / (ecpm * 100.0);
				}
				// ��һ�߼�Ϊcpm,�ڶ��߼�Ϊcpc
				// �����۸�Ϊcpm
				// �������������չʾ��־�����
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
