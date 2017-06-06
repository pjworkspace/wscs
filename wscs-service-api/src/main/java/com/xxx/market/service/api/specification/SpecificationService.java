package com.xxx.market.service.api.specification;

import java.util.List;

import com.xxx.market.model.SellerUser;
import com.xxx.market.model.Specification;

public interface SpecificationService {
      public List<SpecificationResultDto> findAll(SpecificationParamDto specificationParamDto);
      
      public Specification find(Long specificationIds);
      
      public Specification doSave(Specification specification,String items, SellerUser sellerUser);
      
      public Specification doUpdate(Specification specification,String items, SellerUser sellerUser);

}
