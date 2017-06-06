package com.xxx.market.service.api.order;

import java.util.List;
import java.util.Map;

import com.xxx.market.model.InvoiceTemplate;

/**
 * 发货模板接口
 * @author wangjun
 *
 */
public interface InvTemplateService {
	InvoiceTemplate save(InvTplSaveParamDto tplParamDto) throws OrderException;
	InvoiceTemplate getUserTpl(Long sellerId) throws OrderException;
	Map<String, List<PalletElementResultDto>> initPalletElement (Long sellerId) throws OrderException;
	Map<String, List<PalletElementResultDto>> initTableColumnElement (Long sellerId) throws OrderException;
}
