package com.xxx.market.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseAgent<M extends BaseAgent<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Long id) {
		set("id", id);
	}

	public java.lang.Long getId() {
		return get("id");
	}

	public void setSellerId(java.lang.Long sellerId) {
		set("seller_id", sellerId);
	}

	public java.lang.Long getSellerId() {
		return get("seller_id");
	}

	public void setBuyerId(java.lang.Long buyerId) {
		set("buyer_id", buyerId);
	}

	public java.lang.Long getBuyerId() {
		return get("buyer_id");
	}

	public void setParentId(java.lang.Long parentId) {
		set("parent_id", parentId);
	}

	public java.lang.Long getParentId() {
		return get("parent_id");
	}

	public void setAgentName(java.lang.String agentName) {
		set("agent_name", agentName);
	}

	public java.lang.String getAgentName() {
		return get("agent_name");
	}

	public void setAgentPhone(java.lang.String agentPhone) {
		set("agent_phone", agentPhone);
	}

	public java.lang.String getAgentPhone() {
		return get("agent_phone");
	}

	public void setAreaId(java.lang.Long areaId) {
		set("area_id", areaId);
	}

	public java.lang.Long getAreaId() {
		return get("area_id");
	}

	public void setAreaTreePath(java.lang.String areaTreePath) {
		set("area_tree_path", areaTreePath);
	}

	public java.lang.String getAreaTreePath() {
		return get("area_tree_path");
	}

	public void setAgentAddr(java.lang.String agentAddr) {
		set("agent_addr", agentAddr);
	}

	public java.lang.String getAgentAddr() {
		return get("agent_addr");
	}

	public void setExpireDate(java.util.Date expireDate) {
		set("expire_date", expireDate);
	}

	public java.util.Date getExpireDate() {
		return get("expire_date");
	}

	public void setAuditDate(java.util.Date auditDate) {
		set("audit_date", auditDate);
	}

	public java.util.Date getAuditDate() {
		return get("audit_date");
	}

	public void setStatus(java.lang.Integer status) {
		set("status", status);
	}

	public java.lang.Integer getStatus() {
		return get("status");
	}

	public void setActive(java.lang.Boolean active) {
		set("active", active);
	}

	public java.lang.Boolean getActive() {
		return get("active");
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
