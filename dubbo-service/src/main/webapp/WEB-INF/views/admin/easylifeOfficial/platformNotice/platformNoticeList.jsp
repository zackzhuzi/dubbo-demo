<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<script type="text/javascript">
	var platformNoticeDataGrid;
	$(function() {
		platformNoticeDataGrid = $('#platformNoticeDataGrid')
				.datagrid(
						{
							url : '${path}/easylifeOfficial/platformNotice/dataGrid',
							queryParams : {
								noticeStatusSelected : $('#noticeStatusSelected').val(),
								noticeDateBegin : $('#noticeDateBegin').val(),
								noticeDateEnd : $('#noticeDateEnd').val()
							},
							striped : true,
							rownumbers : true,
							pagination : true,
							singleSelect : true,
							idField : 'noticeIdd',
							//sortName : 'id',
							//sortOrder : 'asc',
							pageSize : 20,
							pageList : [ 10, 20, 30, 40, 50, 100, 200, 300,
									400, 500 ],
							frozenColumns : [ [
									{
										width : '300',
										title : '标题',
										field : 'noticeTitle',
										sortable : false
									},
									{
										width : '200',
										title : '公告日期',
										field : 'noticeDate',
										sortable : true
									},
									{
										field : 'action',
										title : '操作',
										width : 200,
										formatter : function(value, row, index) {
											var str = '';
											<shiro:hasPermission name="/easylifeOfficial/platformNotice/edit">
											str += $
													.formatString(
															'<a href="javascript:void(0)" class="platformNotice-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="platformNoticeEditFun(\'{0}\');" >编辑</a>',
															row.noticeId);
											</shiro:hasPermission>
											return str;
										}
									} ] ],
							onLoadSuccess : function(data) {
								$('.platformNotice-easyui-linkbutton-edit')
										.linkbutton({
											text : '编辑'
										});
							},
							toolbar : '#platformNoticeToolbar'
						});
	});

	/**
	 * 添加框
	 * @param url
	 */
	function platformNoticeAddFun() {
		parent.$
				.modalDialog({
					title : '添加',
					width : 1000,
					height : 800,
					resizable : true,
					collapsible : true,
					maximizable : true,
					href : '${path}/easylifeOfficial/platformNotice/addPage',
					buttons : [ {
						text : '确定',
						handler : function() {
							parent.$.modalDialog.openner_dataGrid = platformNoticeDataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
							var f = parent.$.modalDialog.handler
									.find('#platformNoticeAddForm');
							f.submit();
						}
					} ]
				});
	}

	/**
	 * 编辑
	 */
	function platformNoticeEditFun(id) {
		if (id == undefined) {
			var rows = platformNoticeDataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			platformNoticeDataGrid.datagrid('unselectAll').datagrid(
					'uncheckAll');
		}
		parent.$
				.modalDialog({
					title : '编辑',
					width : 1000,
					height : 800,
					resizable : true,
					collapsible : true,
					maximizable : true,
					href : '${path}/easylifeOfficial/platformNotice/editPage?id='
							+ id,
					buttons : [ {
						text : '确定',
						handler : function() {
							parent.$.modalDialog.openner_dataGrid = platformNoticeDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
							var f = parent.$.modalDialog.handler
									.find('#platformNoticeEditForm');
							f.submit();
						}
					} ]
				});
	}

	/**
	 * 清除
	 */
	function platformNoticeCleanFun() {
		$('#noticeDateBegin').val('');
		$('#noticeDateEnd').val('');
		$('#noticeStatusSelected').combobox('select', '0');
		platformNoticeDataGrid.datagrid('load', $
				.serializeObject($('#platformNoticeSearchForm')));
	}
	/**
	 * 搜索
	 */
	function platformNoticeSearchFun() {
		platformNoticeDataGrid.datagrid('load', $
				.serializeObject($('#platformNoticeSearchForm')));
	}
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false"
		style="height: 30px; overflow: hidden; background-color: #fff">
		<form id="platformNoticeSearchForm">
			<table>
				<tr>
					<th>公告状态</th>
					<td><select id="noticeStatusSelected"
						name="noticeStatusSelected" class="easyui-combobox"
						data-options="width:140,height:25,editable:false,panelHeight:'auto'">
							<option value="0" selected>有效</option>
							<option value="1">无效</option>
					</select></td>
					<th>开始日期：</th>
					<td><input id="noticeDateBegin" name="noticeDateBegin"
						placeholder="开始日期"
						onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"
						readonly="readonly" /></td>
					<th>结束日期：</th>
					<td><input name="noticeDateEnd" name="noticeDateEnd"
						placeholder="结束日期"
						onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"
						readonly="readonly" /></td>
					<td><a href="javascript:void(0);" class="easyui-linkbutton"
						data-options="iconCls:'fi-magnifying-glass',plain:true"
						onclick="platformNoticeSearchFun();">查询</a> <a
						href="javascript:void(0);" class="easyui-linkbutton"
						data-options="iconCls:'fi-x-circle',plain:true"
						onclick="platformNoticeCleanFun();">清空</a></td>
				</tr>
			</table>
		</form>
	</div>

	<div data-options="region:'center',border:false">
		<table id="platformNoticeDataGrid"
			data-options="fit:true,border:false"></table>
	</div>
</div>
<div id="platformNoticeToolbar" style="display: none;">
	<shiro:hasPermission name="/easylifeOfficial/platformNotice/add">
		<a onclick="platformNoticeAddFun();" href="javascript:void(0);"
			class="easyui-linkbutton"
			data-options="plain:true,iconCls:'fi-page-add'">添加</a>
	</shiro:hasPermission>
</div>