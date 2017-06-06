package com.xxx.market.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseLotteryRecord<M extends BaseLotteryRecord<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Long id) {
		set("id", id);
	}

	public java.lang.Long getId() {
		return get("id");
	}

	public void setBuyerId(java.lang.Long buyerId) {
		set("buyer_id", buyerId);
	}

	public java.lang.Long getBuyerId() {
		return get("buyer_id");
	}

	public void setLotteryId(java.lang.Long lotteryId) {
		set("lottery_id", lotteryId);
	}

	public java.lang.Long getLotteryId() {
		return get("lottery_id");
	}

	public void setLotteryTime(java.util.Date lotteryTime) {
		set("lottery_time", lotteryTime);
	}

	public java.util.Date getLotteryTime() {
		return get("lottery_time");
	}

	public void setLotteryMonth(java.lang.String lotteryMonth) {
		set("lottery_month", lotteryMonth);
	}

	public java.lang.String getLotteryMonth() {
		return get("lottery_month");
	}

	public void setCreated(java.util.Date created) {
		set("created", created);
	}

	public java.util.Date getCreated() {
		return get("created");
	}

	public void setUpdated(java.util.Date updated) {
		set("updated", updated);
	}

	public java.util.Date getUpdated() {
		return get("updated");
	}

}