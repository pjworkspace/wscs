package com.xxx.market.service.provider;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.ActiveRecordException;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.weixin.sdk.api.ApiResult;
import com.weixin.sdk.api.TemplateData;
import com.weixin.sdk.api.TemplateMsgApi;
import com.xxx.market.model.*;
import com.xxx.market.service.api.authuser.AuthUserService;
import com.xxx.market.service.api.order.*;
import com.xxx.market.service.api.product.ProductException;
import com.xxx.market.service.api.serinum.SerinumService;
import com.xxx.market.service.api.sys.RoleResultDto;
import com.xxx.market.service.api.sys.RoleService;
import com.xxx.market.service.api.sys.RoleSubmitParamDto;
import com.xxx.market.service.base.AbstractServiceImpl;
import com.xxx.market.service.enmu.OrderStatus;
import com.xxx.market.service.enmu.PaymentStatus;
import com.xxx.market.service.enmu.ShippingStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("roleService")
public class RoleServiceImpl extends AbstractServiceImpl implements RoleService {


	@Override
	public void save(RoleSubmitParamDto roleSubmitParamDto) throws ProductException {

	}

	@Override
	public void update(RoleSubmitParamDto roleSubmitParamDto, Long productId) throws ProductException {

	}

	@Override
	public Page<RoleResultDto> list(RoleSubmitParamDto roleSubmitParamDto) {
		return null;
	}
}
