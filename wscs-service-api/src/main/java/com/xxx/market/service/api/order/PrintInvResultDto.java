package com.xxx.market.service.api.order;

import java.util.List;

import com.xxx.market.model.InvoiceTemplate;
import com.xxx.market.service.common.AbstractResultDto;

@SuppressWarnings("serial")
public class PrintInvResultDto extends AbstractResultDto{

	List<PrintInvItemResultDto> printData;	//打印数据
	InvoiceTemplate invoiceTemplate;			//发货模板数据
	
	public InvoiceTemplate getInvoiceTemplate() {
		return invoiceTemplate;
	}

	public void setInvoiceTemplate(InvoiceTemplate invoiceTemplate) {
		this.invoiceTemplate = invoiceTemplate;
	}

	public List<PrintInvItemResultDto> getPrintData() {
		return printData;
	}

	public void setPrintData(List<PrintInvItemResultDto> printData) {
		this.printData = printData;
	}

}
