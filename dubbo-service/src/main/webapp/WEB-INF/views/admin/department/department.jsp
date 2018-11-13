<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<script type="text/javascript">
	var TreeGrid;
	$(function() {
		departmentTreeGrid = $('#departmentTreeGrid')
				.treegrid(
						{
							url : '${path }/department/treeGrid',
							idField : 'id',
							treeField : 'name',
							parentField : 'pid',
							fit : true,
							fitColumns : false,
							border : false,
							frozenColumns : [ [ {
								title : 'id',
								field : 'id',
								width : 40,
								hidden : true
							} ] ],
							columns : [ [
									{
										field : 'name',
										title : '部门名称',
										width : 180
									},
									{
										field : 'iconCls',
										title : '图标',
										width : 120
									},
									{
										width : '130',
										title : '创建时间',
										field : 'createTime'
									},
									{
										field : 'pid',
										title : '上级资源ID',
										width : 150,
										hidden : true
									},
									{
										field : 'address',
										title : '地址',
										width : 120
									},
									{
										field : 'description',
										title : '简介',
										width : 250
									},
									{
										field : 'action',
										title : '操作',
										width : 130,
										formatter : function(value, row, index) {
											var str = '';
											<shiro:hasPermission name="/department/edit">
											str += $
													.formatString(
															'<a href="javascript:void(0)" class="department-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editdepartmentFun(\'{0}\');" >编辑</a>',
															row.id);
											</shiro:hasPermission>
											<shiro:hasPermission name="/department/delete">
											str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
											str += $
													.formatString(
															'<a href="javascript:void(0)" class="department-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="deletedepartmentFun(\'{0}\');" >删除</a>',
															row.id);
											</shiro:hasPermission>
											return str;
										}
									} ] ],
							onLoadSuccess : function(data) {
								$('.department-easyui-linkbutton-edit')
										.linkbutton({
											text : '编辑'
										});
								$('.department-easyui-linkbutton-del')
										.linkbutton({
											text : '删除'
										});
							},
							toolbar : '#orgToolbar'
						});
	});

	function editdepartmentFun(id) {
		if (id != undefined) {
			departmentTreeGrid.treegrid('select', id);
		}
		var node = departmentTreeGrid.treegrid('getSelected');
		if (node) {
			parent.$
					.modalDialog({
						title : '编辑',
						width : 500,
						height : 300,
						href : '${path }/department/editPage?id=' + node.id,
						buttons : [ {
							text : '编辑',
							handler : function() {
								parent.$.modalDialog.openner_treeGrid = departmentTreeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
								var f = parent.$.modalDialog.handler
										.find('#departmentEditForm');
								f.submit();
							}
						} ]
					});
		}
	}

	function deletedepartmentFun(id) {
		if (id != undefined) {
			departmentTreeGrid.treegrid('select', id);
		}
		var node = departmentTreeGrid.treegrid('getSelected');
		if (node) {
			parent.$.messager.confirm('询问', '您是否要删除当前资源？删除当前资源会连同子资源一起删除!',
					function(b) {
						if (b) {
							progressLoad();
							$.post('${path }/department/delete', {
								id : node.id
							}, function(result) {
								if (result.success) {
									parent.$.messager.alert('提示', result.msg,
											'info');
									departmentTreeGrid.treegrid('reload');
								} else {
									parent.$.messager.alert('提示', result.msg,
											'info');
								}
								progressClose();
							}, 'JSON');
						}
					});
		}
	}

	function adddepartmentFun() {
		parent.$.modalDialog({
			title : '添加',
			width : 500,
			height : 300,
			href : '${path }/department/addPage',
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_treeGrid = departmentTreeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
					var f = parent.$.modalDialog.handler
							.find('#departmentAddForm');
					f.submit();
				}
			} ]
		});
	}
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false"
		style="overflow: hidden;">
		<table id="departmentTreeGrid"></table>
	</div>
	<div id="orgToolbar" style="display: none;">
		<shiro:hasPermission name="/department/add">
			<a onclick="adddepartmentFun();" href="javascript:void(0);"
				class="easyui-linkbutton"
				data-options="plain:true,iconCls:'fi-plus icon-green'">添加</a>
		</shiro:hasPermission>
	</div>
</div>