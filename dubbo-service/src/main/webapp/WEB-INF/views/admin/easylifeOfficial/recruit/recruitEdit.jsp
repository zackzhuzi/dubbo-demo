<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!-- 配置文件 -->
<script type="text/javascript"
	src="${staticPath }/static/ueditor/ueditor.config.js"></script>
<!-- 编辑器源码文件 -->
<script type="text/javascript"
	src="${staticPath }/static/ueditor/ueditor.all.js"></script>
<!-- 实例化编辑器 -->
<script type="text/javascript">
	var jobRequirementUe = UE.getEditor('jobRequirement', {
		autoHeightEnabled : true,
		autoFloatEnabled : true,
		initialFrameHeight : 300,
	});
	var jobResponsibilityUe = UE.getEditor('jobResponsibility', {
		autoHeightEnabled : true,
		autoFloatEnabled : true,
		initialFrameHeight : 300,
	});
</script>
<script type="text/javascript">
	$(function() {
		$('#recruitEditForm').form({
			url : '${path}/easylifeOfficial/recruit/edit',
			onSubmit : function() {
				progressLoad();
				var isValid = $(this).form('validate');
				if (!isValid) {
					progressClose();
				}
				return isValid;
			},
			success : function(result) {
				progressClose();
				result = $.parseJSON(result);
				if (result.success) {
					parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
					parent.$.modalDialog.handler.dialog('close');
				} else {
					var form = $('#recruitEditForm');
					parent.$.messager.alert('错误', eval(result.msg), 'error');
				}
			}
		});

		/* 		$('#recruitType').combobox('select', '${recruit.recruitType}');
		 $('#jobStatus').combobox('select', '${recruit.jobStatus}');
		 */
		$('#recruitType').val('${recruit.recruitType}');
		$('#jobStatus').val('${recruit.jobStatus}');
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title=""
		style="overflow: scroll; overflow-y: scroll; padding: 3px;">
		<form id="recruitEditForm" method="post">
			<input name="recruitId" type="hidden" value="${recruit.recruitId}">
			<table class="grid">
				<tr>
					<td>岗位名称</td>
					<td><input name="jobTitle" type="text" placeholder="请输入名称"
						class="easyui-validatebox span2" data-options="required:true"
						value="${recruit.jobTitle}"></td>
					<td>岗位类别</td>
					<td><input name="jobType" type="text" placeholder="请输入岗位类别"
						class="easyui-validatebox span2" data-options="required:true"
						value="${recruit.jobType}"></td>
					<td>招聘类型</td>
					<td><select id="recruitType" name="recruitType"
						class="easyui-combobox"
						data-options="width:140,height:25,editable:false,panelHeight:'auto'">
							<option value="1">校园招聘</option>
							<option value="2">社会招聘</option>
							<option value="3">实习生招聘</option>
					</select></td>
				</tr>
				<tr>
					<td>岗位状态</td>
					<td><select id="jobStatus" name="jobStatus"
						class="easyui-combobox"
						data-options="width:140,height:25,editable:false,panelHeight:'auto'">
							<option value="0">有效</option>
							<option value="1">无效</option>
					</select></td>
					<td>工作地点</td>
					<td><input name="workplace" type="text" placeholder="请输入工作地点"
						class="easyui-validatebox span2" data-options="required:true"
						value="${recruit.workplace}"></td>
					<td>招聘人数</td>
					<td><input name="recruitNumber" type="text"
						placeholder="请输入招聘人数" class="easyui-validatebox span2"
						data-options="required:true" value="${recruit.recruitNumber}"></td>
				</tr>
				<tr>
					<td>发布时间</td>
					<td><input name="releaseTime" placeholder="请选择发布时间"
						onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"
						readonly="readonly" data-options="required:true"
						value="${recruit.releaseTime}" /></td>
					<td>投递邮箱</td>
					<td><input name="resumeEmail" type="text"
						placeholder="请输入投递邮箱" class="easyui-validatebox span2"
						data-options="required:true" value="${recruit.resumeEmail}"></td>
				</tr>
			</table>
			<table class="grid">
				<tr>
					<td>工作职责</td>
					<td>
						<!-- 加载编辑器的容器 --> <textArea id="jobResponsibility"
							name="jobResponsibility" style="width: 100%; height: 100%;">${recruit.jobResponsibility}</textArea>
					</td>
				</tr>
				<tr>
					<td>职位要求</td>
					<td>
						<!-- 加载编辑器的容器 --> <textArea id="jobRequirement"
							name='jobRequirement' style="width: 100%; height: 100%;">${recruit.jobRequirement}</textArea>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>