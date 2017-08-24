#@layout()
#define main()
<h1>${table.remark}管理&nbsp;&nbsp;
<a href="/${table.modelName?lower_case}/add">创建${table.remark}</a>
</h1>
<div class="table_box">
	<table class="list">
		<tbody>
			<tr>
				<#list listColumn as column>
				<th width="">${column.remark}</th>
				</#list>
			</tr>
			
			#for(x : ${table.modelName}Page.getList())
			<tr>
				<#list listColumn as column>
				<td style="text-align:left;">#(x.${column.fieldName})</td>
				</#list>
				<td style="text-align:left;">
					&nbsp;&nbsp;<a href="/${table.modelName?lower_case}/delete/#(x.id)">删除</a>
					&nbsp;&nbsp;<a href="/${table.modelName?lower_case}/edit/#(x.id)">修改</a>
				</td>
			</tr>
			#end
		</tbody>
	</table>
	#@paginate(${table.modelName}Page.pageNumber, ${table.modelName}Page.totalPage, "/${table.modelName?lower_case}/")
</div>
#end