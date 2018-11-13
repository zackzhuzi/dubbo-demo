<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<script type="text/javascript">
	$(function() {
		$('#departmentEditPid').combotree({
			url : '${path }/department/tree?flag=false',
			parentField : 'pid',
			lines : true,
			panelHeight : 'auto',
			value : '${department.pid}'
		});

		$('#departmentEditForm').form({
			url : '${path }/department/edit',
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
					parent.$.modalDialog.openner_treeGrid.treegrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_treeGrid这个对象，是因为department.jsp页面预定义好了
					parent.$.modalDialog.handler.dialog('close');
				} else {
					var form = $('#departmentEditForm');
					parent.$.messager.alert('提示', eval(result.msg), 'warning');
				}
			}
		});

	});
</script>
<div style="padding: 3px;">
	<form id="departmentEditForm" method="post">
		<table class="grid">
			<tr>
				<td>资源名称</td>
				<td><input name="id" type="hidden" value="${department.id}"><input
					name="name" type="text" value="${department.name}"
					placeholder="请输入部门名称" class="easyui-validatebox"
					data-options="required:true"></td>
				<td>菜单图标</td>
				<td><input name="icon" value="${department.icon}" /></td>
			</tr>
			<tr>
				<td>地址</td>
				<td colspan="3"><input name="address" style="width: 300px;"
					value="${department.address}" /></td>
			</tr>
			<tr>
				<td>简介</td>
				<td colspan="5"><input name="description" style="width: 300px;"
					value="${department.description}" /></td>
			</tr>
			<tr>
				<td>上级资源</td>
				<td colspan="3"><select id="departmentEditPid" name="pid"
					style="width: 200px; height: 29px;"></select> <a
					class="easyui-linkbutton" href="javascript:void(0)"
					onclick="$('#departmentEditPid').combotree('clear');">清空</a></td>
			</tr>
		</table>
	</form>
</div>
