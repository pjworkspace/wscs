package com.xxx.market.service.api.prize;

import com.xxx.market.model.BuyerUser;
import com.xxx.market.model.SellerUser;
import com.xxx.market.service.common.AbstractParamDto;

/**
 * wjun_java@163.com
 * 2016年7月20日
 */
@SuppressWarnings("serial")
public class PrizeSendParamDto extends AbstractParamDto{

	private Long prizeId;
	private Long activityId;
	private Integer activityType;
	private String reason;
	private SellerUser seller;
	private BuyerUser buyer;
	
	public SellerUser getSeller() {
		return seller;
	}
	public void setSeller(SellerUser seller) {
		this.seller = seller;
	}
	public BuyerUser getBuyer() {
		return buyer;
	}
	public void setBuyer(BuyerUser buyer) {
		this.buyer = buyer;
	}
	public Long getActivityId() {
		return activityId;
	}
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	public Integer getActivityType() {
		return activityType;
	}
	public void setActivityType(Integer activityType) {
		this.activityType = activityType;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Long getPrizeId() {
		return prizeId;
	}
	public void setPrizeId(Long prizeId) {
		this.prizeId = prizeId;
	}
	
}
