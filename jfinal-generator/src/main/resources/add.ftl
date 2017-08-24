#@layout()
#define main()
<h1>${table.remark}管理 ---&gt; 创建${table.remark}
</h1>
<div class="form_box">
	<form action="/${table.modelName?lower_case}/save" method="post">
		#include("_form.html")
	</form>
</div>
#end