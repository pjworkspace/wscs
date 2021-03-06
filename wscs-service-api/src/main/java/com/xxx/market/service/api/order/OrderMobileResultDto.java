package com.xxx.market.service.api.order;

import java.util.Date;
import java.util.List;

import com.xxx.market.service.common.AbstractResultDto;

@SuppressWarnings("serial")
public class OrderMobileResultDto extends AbstractResultDto{

	private Long orderId;
	private String sn;
	private String totalPrice;
	private String status;
	private Date created;
	
	private List<OrderItemResultDto> orderItems; 
	
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public List<OrderItemResultDto> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItemResultDto> orderItems) {
		this.orderItems = orderItems;
	}
	
}
