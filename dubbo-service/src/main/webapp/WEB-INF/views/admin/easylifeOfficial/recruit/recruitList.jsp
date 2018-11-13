<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<script type="text/javascript">
	var recruitDataGrid;
	$(function() {
		recruitDataGrid = $('#recruitDataGrid')
				.datagrid(
						{
							url : '${path}/easylifeOfficial/recruit/dataGrid',
							queryParams : {
								recruitTypeSelectd : $('#recruitTypeSelectd')
										.val(),
								jobStatusSelectd : $('#jobStatusSelectd').val(),
								releaseTimeBegin : $('#releaseTimeBegin').val(),
								releaseTimeEnd : $('#releaseTimeEnd').val()
							},
							striped : true,
							rownumbers : true,
							pagination : true,
							singleSelect : true,
							idField : 'recruitId',
							//sortName : 'id',
							//sortOrder : 'asc',
							pageSize : 20,
							pageList : [ 10, 20, 30, 40, 50, 100, 200, 300,
									400, 500 ],
							frozenColumns : [ [
									{
										width : '150',
										title : '职位名称',
										field : 'jobTitle',
										sortable : false
									},
									{
										width : '150',
										title : '招聘类型',
										field : 'recruitType',
										sortable : false,
										formatter : function(value, row, index) {
											switch (value) {
											case 1:
												return '校园招聘';
											case 2:
												return '社会招聘';
											case 3:
												return '实习生招聘';
											}
										}
									},
									{
										width : '150',
										title : '职位类别',
										field : 'jobType',
										sortable : false
									},
									{
										width : '150',
										title : '工作地点',
										field : 'workplace',
										sortable : false
									},
									{
										width : '200',
										title : '发布时间',
										field : 'releaseTime',
										sortable : true
									},
									{
										field : 'action',
										title : '操作',
										width : 200,
										formatter : function(value, row, index) {
											var str = '';
											<shiro:hasPermission name="/easylifeOfficial/recruit/edit">
											str += $
													.formatString(
															'<a href="javascript:void(0)" class="recruit-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="recruitEditFun(\'{0}\');" >编辑</a>',
															row.recruitId);
											</shiro:hasPermission>
											return str;
										}
									} ] ],
							onLoadSuccess : function(data) {
								$('.recruit-easyui-linkbutton-edit')
										.linkbutton({
											text : '编辑'
										});
							},
							toolbar : '#recruitToolbar'
						});
	});

	/**
	 * 添加框
	 * @param url
	 */
	function recruitAddFun() {
		parent.$.modalDialog({
			title : '添加',
			width : 1000,
			height : 800,
			resizable : true,
			collapsible : true,
			maximizable : true,
			href : '${path}/easylifeOfficial/recruit/addPage',
			buttons : [ {
				text : '确定',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = recruitDataGrid; //因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
					var f = parent.$.modalDialog.handler
							.find('#recruitAddForm');
					f.submit();
				}
			} ]
		});
	}

	/**
	 * 编辑
	 */
	function recruitEditFun(id) {
		if (id == undefined) {
			var rows = recruitDataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			recruitDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}

		parent.$.modalDialog({
			title : '编辑',
			width : 1000,
			height : 800,
			resizable : true,
			collapsible : true,
			maximizable : true,
			href : '${path}/easylifeOfficial/recruit/editPage?id=' + id,
			buttons : [ {
				text : '确定',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = recruitDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler
							.find('#recruitEditForm');
					f.submit();
				}
			} ]
		});
	}

	/**
	 * 清除
	 */
	function recruitCleanFun() {
		$('#releaseTimeBegin').val('');
		$('#releaseTimeEnd').val('');
		$('#recruitTypeSelectd').combobox('select', '-1');
		$('#jobStatusSelectd').combobox('select', '0');
		recruitDataGrid.datagrid('load', $
				.serializeObject($('#recruitSearchForm')));
	}
	/**
	 * 搜索
	 */
	function recruitSearchFun() {
		recruitDataGrid.datagrid('load', $
				.serializeObject($('#recruitSearchForm')));
	}
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false"
		style="height: 30px; overflow: hidden; background-color: #fff">
		<form id="recruitSearchForm">
			<table>
				<tr>
					<th>招聘类型</th>
					<td><select id="recruitTypeSelectd" name="recruitTypeSelectd"
						class="easyui-combobox"
						data-options="width:140,height:25,editable:false,panelHeight:'auto'">
							<option value="-1" selected>全部</option>
							<option value="1">校园招聘</option>
							<option value="2">社会招聘</option>
							<option value="3">实习生招聘</option>
					</select></td>
					<th>岗位状态</th>
					<td><select id="jobStatusSelectd" name="jobStatusSelectd"
						class="easyui-combobox"
						data-options="width:140,height:25,editable:false,panelHeight:'auto'">
							<option value="0" selected>有效</option>
							<option value="1">无效</option>
					</select></td>
					<th>开始日期：</th>
					<td><input id="releaseTimeBegin" name="releaseTimeBegin"
						placeholder="开始日期"
						onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"
						readonly="readonly" /></td>
					<th>结束日期：</th>
					<td><input id="releaseTimeEnd" name="releaseTimeEnd"
						placeholder="结束日期"
						onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"
						readonly="readonly" /></td>
					<td><a href="javascript:void(0);" class="easyui-linkbutton"
						data-options="iconCls:'fi-magnifying-glass',plain:true"
						onclick="recruitSearchFun();">查询</a> <a href="javascript:void(0);"
						class="easyui-linkbutton"
						data-options="iconCls:'fi-x-circle',plain:true"
						onclick="recruitCleanFun();">清空</a></td>
				</tr>
			</table>
		</form>
	</div>

	<div data-options="region:'center',border:false">
		<table id="recruitDataGrid" data-options="fit:true,border:false"></table>
	</div>
</div>
<div id="recruitToolbar" style="display: none;">
	<shiro:hasPermission name="/easylifeOfficial/recruit/add">
		<a onclick="recruitAddFun();" href="javascript:void(0);"
			class="easyui-linkbutton"
			data-options="plain:true,iconCls:'fi-page-add'">添加</a>
	</shiro:hasPermission>
</div>
