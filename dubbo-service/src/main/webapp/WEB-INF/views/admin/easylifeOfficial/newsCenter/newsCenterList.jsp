<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<script type="text/javascript">
	var newsCenterDataGrid;
	$(function() {
		newsCenterDataGrid = $('#newsCenterDataGrid')
				.datagrid(
						{
							url : '${path}/easylifeOfficial/newsCenter/dataGrid',
							queryParams : {
								newsTypeSelected : $('#newsTypeSelected').val(),
								newsStatusSelected : $('#newsStatusSelected')
										.val(),
								newsDateBegin : $('#newsDateBegin').val(),
								newsDateEnd : $('#newsDateEnd').val()
							},
							striped : true,
							rownumbers : true,
							pagination : true,
							singleSelect : true,
							idField : 'newsId',
							//sortName : 'id',
							//sortOrder : 'asc',
							pageSize : 20,
							pageList : [ 10, 20, 30, 40, 50, 100, 200, 300,
									400, 500 ],
							frozenColumns : [ [
									{
										width : '500',
										title : '标题',
										field : 'newsTitle',
										sortable : false
									},
									{
										width : '150',
										title : '新闻类别',
										field : 'newsType',
										sortable : false,
										formatter : function(value, row, index) {
											switch (value) {
											case 1:
												return '小贷新闻';
											case 2:
												return '外部新闻';
											}
										}
									},
									{
										width : '100',
										title : '新闻来源',
										field : 'newsSource',
										sortable : false,
									},
									{
										width : '140',
										title : '发布日期',
										field : 'newsDate',
										sortable : true
									},
									{
										field : 'action',
										title : '操作',
										width : 200,
										formatter : function(value, row, index) {
											var str = '';
											<shiro:hasPermission name="/easylifeOfficial/newsCenter/edit">
											str += $
													.formatString(
															'<a href="javascript:void(0)" class="newsCenter-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="newsCenterEditFun(\'{0}\');" >编辑</a>',
															row.newsId);
											</shiro:hasPermission>
											return str;
										}
									} ] ],
							onLoadSuccess : function(data) {
								$('.newsCenter-easyui-linkbutton-edit')
										.linkbutton({
											text : '编辑'
										});
							},
							toolbar : '#newsCenterToolbar'
						});
	});

	/**
	 * 添加框
	 * @param url
	 */
	function newsCenterAddFun() {
		parent.$.modalDialog({
			title : '添加',
			width : 1000,
			height : 800,
			resizable : true,
			collapsible : true,
			maximizable : true,
			href : '${path}/easylifeOfficial/newsCenter/addPage',
			buttons : [ {
				text : '确定',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = newsCenterDataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
					var f = parent.$.modalDialog.handler
							.find('#newsCenterAddForm');
					f.submit();
				}
			} ]
		});
	}

	/**
	 * 编辑
	 */
	function newsCenterEditFun(id) {
		if (id == undefined) {
			var rows = newsCenterDataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			newsCenterDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.modalDialog({
			title : '编辑',
			width : 1000,
			height : 800,
			resizable : true,
			collapsible : true,
			maximizable : true,
			href : '${path}/easylifeOfficial/newsCenter/editPage?id=' + id,
			buttons : [ {
				text : '确定',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = newsCenterDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler
							.find('#newsCenterEditForm');
					f.submit();
				}
			} ]
		});
	}

	/**
	 * 清除
	 */
	function newsCenterCleanFun() {
		$('#newsDateBegin').val('');
		$('#newsDateEnd').val('');
		$('#newsTypeSelected').combobox('select', '-1');
		$('#newsStatusSelected').combobox('select', '0');
		newsCenterDataGrid.datagrid('load', $
				.serializeObject($('#newsCenterSearchForm')));
	}
	/**
	 * 搜索
	 */
	function newsCenterSearchFun() {
		newsCenterDataGrid.datagrid('load', $
				.serializeObject($('#newsCenterSearchForm')));
	}
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false"
		style="height: 30px; overflow: hidden; background-color: #fff">
		<form id="newsCenterSearchForm">
			<table>
				<tr>
					<th>新闻类别</th>
					<td><select id="newsTypeSelected" name="newsTypeSelected"
						class="easyui-combobox"
						data-options="width:140,height:25,editable:false,panelHeight:'auto'">
							<option value="-1" selected>全部</option>
							<option value="1">小贷新闻</option>
							<option value="2">外部新闻</option>
					</select></td>
					<th>新闻状态</th>
					<td><select id="newsStatusSelected" name="newsStatusSelected"
						class="easyui-combobox"
						data-options="width:140,height:25,editable:false,panelHeight:'auto'">
							<option value="0" selected>有效</option>
							<option value="1">无效</option>
					</select></td>
					<th>开始日期：</th>
					<td><input id="newsDateBegin" name="newsDateBegin"
						placeholder="开始日期"
						onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"
						readonly="readonly" /></td>
					<th>结束日期：</th>
					<td><input id="newsDateEnd" name="newsDateEnd"
						placeholder="结束日期"
						onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"
						readonly="readonly" /></td>
					<td><a href="javascript:void(0);" class="easyui-linkbutton"
						data-options="iconCls:'fi-magnifying-glass',plain:true"
						onclick="newsCenterSearchFun();">查询</a> <a
						href="javascript:void(0);" class="easyui-linkbutton"
						data-options="iconCls:'fi-x-circle',plain:true"
						onclick="newsCenterCleanFun();">清空</a></td>
				</tr>
			</table>
		</form>
	</div>

	<div data-options="region:'center',border:false">
		<table id="newsCenterDataGrid" data-options="fit:true,border:false"></table>
	</div>
</div>
<div id="newsCenterToolbar" style="display: none;">
	<shiro:hasPermission name="/easylifeOfficial/newsCenter/add">
		<a onclick="newsCenterAddFun();" href="javascript:void(0);"
			class="easyui-linkbutton"
			data-options="plain:true,iconCls:'fi-page-add'">添加</a>
	</shiro:hasPermission>
</div>