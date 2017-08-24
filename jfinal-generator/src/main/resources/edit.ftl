#@layout()
#define main()
<h1>${table.remark}管理 ---&gt; 修改${table.remark}
</h1>
<div class="form_box">
	<form action="/${table.modelName?lower_case}/update" method="post">
		#include("_form.html")
	</form>
</div>
#end