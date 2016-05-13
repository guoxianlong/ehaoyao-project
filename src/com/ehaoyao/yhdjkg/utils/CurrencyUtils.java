package com.ehaoyao.yhdjkg.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.ehaoyao.yhdjkg.model.order_center.OrderDetail;

public class CurrencyUtils {

	/**
	 * 计算优惠后的价格
	 * 
	 * @param discount
	 *            优惠的金额
	 * @param detailList
	 *            明细集合
	 * @return 计算之后的价格
	 * 
	 *         算法： 明细1优惠了的金额：(明细1的金额/总金额)×优惠的金额) 明细2优惠了的金额：(明细2的金额/总金额)×优惠的金额)
	 *         明细3优惠了的金额：优惠的金额－明细1优惠了的金额－明细2优惠了的金额
	 */
	public static void getDiscountMoney(Double discount,
			List<OrderDetail> detailList) {
		if (detailList.size() > 1) {
			double sum = 0.0;// 存储订单总价格
			// 算出订单总价格
			for (int i = 0; i < detailList.size(); i++) {
				sum += detailList.get(i).getTotalPrice();
			}
			Double disAll = 0.0;// 存储所有的优惠了的价格
			for (int j = 0; j < detailList.size() - 1; j++) {
				Double weight = detailList.get(j).getTotalPrice() / sum;// 所占权重
				Double weightPrice = discount * weight;// 按权重所占的优惠了的价格
				disAll += weightPrice;// 把所有优惠的价格存起来；
				Double lastTotalPrice = (detailList.get(j).getTotalPrice() - weightPrice);// 减去优惠了的价格之后的明细的总价格
				detailList.get(j).setDisTotalPrice(lastTotalPrice);// 设置减去优惠价格后的总价格
				// Double
				// lastPrice=(detailList.get(j).getTotalPrice()-weightPrice)/detailList.get(j).getCount();//减去优惠了的价格之后的明细的单价
				// detailList.get(j).setPriceDiscounted(lastPrice);
			}
			// 处理最后一个明细优惠后的价格
			Double lastDisMoney = discount - disAll;// 存储最后一个明细优惠的价格
			Double lastTotal = detailList.get(detailList.size() - 1)
					.getTotalPrice() - lastDisMoney;// 最后一个明细优惠后的总价格
			detailList.get(detailList.size() - 1).setDisTotalPrice(lastTotal);// 设置减去优惠价格后的总价格
			// Double
			// lastPrice=(detailList.get(detailList.size()-1).getTotalPrice()-lastDisMoney)/detailList.get(detailList.size()-1).getCount();//减去优惠了的价格之后的明细的单价
			// detailList.get(detailList.size()-1).setPriceDiscounted(lastPrice);
		} else if (detailList.size() == 1) {
			Double lastTotalPrice = detailList.get(0).getTotalPrice()
					- discount;// 减去优惠了的价格之后明细的总价格
			detailList.get(0).setDisTotalPrice(lastTotalPrice);// 设置减去优惠价格后的总价格
			// Double lastPrice=(detailList.get(0).getTotalPrice()-
			// discount)/detailList.get(0).getCount();//减去优惠了的价格之后的明细的单价
			// detailList.get(0).setPriceDiscounted(lastPrice);

		}
	}

	public static void getDiscountMoneyBigDe(Double discount,
			List<OrderDetail> detailList) {
		if (detailList.size() > 1) {
			double sum = 0.0;// 存储订单总价格
			// 算出订单总价格
			for (int i = 0; i < detailList.size(); i++) {
				sum += detailList.get(i).getTotalPrice();
			}
			BigDecimal disAll = new BigDecimal(0.0000);
			for (int j = 0; j < detailList.size() - 1; j++) {
				Double weight = detailList.get(j).getTotalPrice() / sum;// 所占权重
				BigDecimal weightPrice = BigDecimal
						.valueOf((discount * weight));// 按权重所占的优惠了的价格
				disAll = disAll.add(weightPrice);// 把所有优惠的价格存起来；
				BigDecimal sourceTotalPrice = new BigDecimal(detailList.get(j)
						.getTotalPrice());// 转换明细的价格为BigDecimal
				BigDecimal lastTotalPrice = sourceTotalPrice
						.subtract(weightPrice);// 减去优惠了的价格之后的明细的总价格
				BigDecimal end = lastTotalPrice.setScale(4,
						RoundingMode.HALF_DOWN);// 保留4位小数
				detailList.get(j).setDisTotalPrice(
						Double.parseDouble(end.toString()));// 设置减去优惠价格后的总价格

			}
			// 处理最后一个明细优惠后的价格
			BigDecimal discountBig = new BigDecimal(discount);// 把优惠的价格转换成BigDecimal
			BigDecimal lastDisMoney = discountBig.subtract(disAll);// 最后一个明细优惠的价格
			BigDecimal sourceTotalPriceLast = new BigDecimal(detailList.get(
					detailList.size() - 1).getTotalPrice());// 把最后一个明细的价格转换成BigDecimal
			BigDecimal lastTotal = sourceTotalPriceLast.subtract(lastDisMoney);// 最后一个明细优惠后的总价格
			BigDecimal end1 = lastTotal.setScale(4, RoundingMode.HALF_DOWN);// 保留4位小数
			detailList.get(detailList.size() - 1).setDisTotalPrice(
					Double.parseDouble(end1.toString()));// 设置减去优惠价格后的总价格

		} else if (detailList.size() == 1) {
			BigDecimal sourceOnlyPrice = new BigDecimal(detailList.get(0)
					.getTotalPrice());// 把明细的价格转换成BigDecimal
			BigDecimal disBig = new BigDecimal(discount);// 把优惠的价格转换成BigDecimal
			BigDecimal lastTotalPrice = sourceOnlyPrice.subtract(disBig);// 减去优惠了的价格之后明细的总价格
			BigDecimal end2 = lastTotalPrice
					.setScale(4, RoundingMode.HALF_DOWN);// 保留4位小数
			detailList.get(0).setDisTotalPrice(
					Double.parseDouble(end2.toString()));// 设置减去优惠价格后的总价格
		}
	}
	
	
	public static void getDiscountMoneyBigDe1(Double discount,
			List<com.ehaoyao.yhdjkg.domain.OrderDetail> detailList) {
		if (detailList.size() > 1) {
			double sum = 0.0;// 存储订单总价格
			// 算出订单总价格
			for (int i = 0; i < detailList.size(); i++) {
				sum += detailList.get(i).getTotalPrice().doubleValue();
			}
			BigDecimal disAll = new BigDecimal(0.0000);
			for (int j = 0; j < detailList.size() - 1; j++) {
				Double weight = detailList.get(j).getTotalPrice().doubleValue() / sum;// 所占权重
				BigDecimal weightPrice = BigDecimal
						.valueOf((discount * weight));// 按权重所占的优惠了的价格
				disAll = disAll.add(weightPrice);// 把所有优惠的价格存起来；
				BigDecimal sourceTotalPrice = detailList.get(j).getTotalPrice();// 转换明细的价格为BigDecimal
				BigDecimal lastTotalPrice = sourceTotalPrice
						.subtract(weightPrice);// 减去优惠了的价格之后的明细的总价格
				BigDecimal end = lastTotalPrice.setScale(4,
						RoundingMode.HALF_DOWN);// 保留4位小数
				detailList.get(j).setDiscountAmount(end);// 设置减去优惠价格后的总价格

			}
			// 处理最后一个明细优惠后的价格
			BigDecimal discountBig = new BigDecimal(discount);// 把优惠的价格转换成BigDecimal
			BigDecimal lastDisMoney = discountBig.subtract(disAll);// 最后一个明细优惠的价格
			BigDecimal sourceTotalPriceLast = detailList.get(detailList.size() - 1).getTotalPrice();// 把最后一个明细的价格转换成BigDecimal
			BigDecimal lastTotal = sourceTotalPriceLast.subtract(lastDisMoney);// 最后一个明细优惠后的总价格
			BigDecimal end1 = lastTotal.setScale(4, RoundingMode.HALF_DOWN);// 保留4位小数
			detailList.get(detailList.size() - 1).setDiscountAmount(end1);// 设置减去优惠价格后的总价格

		} else if (detailList.size() == 1) {
			BigDecimal sourceOnlyPrice =detailList.get(0).getTotalPrice();// 把明细的价格转换成BigDecimal
			BigDecimal disBig = new BigDecimal(discount);// 把优惠的价格转换成BigDecimal
			BigDecimal lastTotalPrice = sourceOnlyPrice.subtract(disBig);// 减去优惠了的价格之后明细的总价格
			BigDecimal end2 = lastTotalPrice
					.setScale(4, RoundingMode.HALF_DOWN);// 保留4位小数
			detailList.get(0).setDiscountAmount(end2);// 设置减去优惠价格后的总价格
		}
	}
}
