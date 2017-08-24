<fieldset class="solid">
	<legend>创建${table.remark}</legend>
	<#list listColumn as column>
	<div>
		<#if column.autoIncrement == false>
		<label>${column.remark}</label>
		<input type="text" name="${table.modelName?uncap_first}.${column.fieldName}" value="#(${table.modelName?uncap_first}.${column.fieldName}??)" />#(${column.fieldName}Msg)
		</#if>
	</div>
	</#list>
	<div>
		<label>&nbsp;</label>
		<input value="提交" type="submit">
	</div>
</fieldset>