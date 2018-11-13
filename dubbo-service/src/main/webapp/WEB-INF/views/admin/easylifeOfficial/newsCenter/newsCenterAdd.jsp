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
/* 	var newsDigestUe = UE.getEditor('newsDigest', {
		autoHeightEnabled : true,
		autoFloatEnabled : true
	}); */
	var newsContentUe = UE.getEditor('newsContent', {
		autoHeightEnabled : true,
		autoFloatEnabled : true,
		initialFrameHeight : 300,
	});
	var uploadImageUe = UE.getEditor("uploadImage", {
		initialFrameWidth : 800,
		initialFrameHeight : 300,
	});

	uploadImageUe.ready(function() {
		//设置编辑器不可用(事实上不可以设置不可用...所以注释掉,以观后效)
		//_editor.setDisabled();

		//隐藏编辑器,因为只使用上传功能
		uploadImageUe.hide();

		//侦听图片上传
		uploadImageUe.addListener('beforeInsertImage', function(t, arg) {
			//将图片地址赋给input
			$("#pictureHref").attr("value", arg[0].src);
			//将图片地址赋给img的src,实现预览
			$("#showImage").attr("src", arg[0].src);
		});
	});
	//上传dialog
	function upImage() {
		var myImage = uploadImageUe.getDialog("insertimage");
		myImage.open();
	}
</script>
<script type="text/javascript">
	$(function() {
		$('#newsCenterAddForm').form({
			url : '${path}/easylifeOfficial/newsCenter/add',
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
					//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
					parent.$.modalDialog.openner_dataGrid.datagrid('reload');
					parent.$.modalDialog.handler.dialog('close');
				} else {
					var form = $('#newsCenterAddForm');
					parent.$.messager.alert('错误', eval(result.msg), 'error');
				}
			}
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false"
		style="overflow: scroll; overflow-y: scroll; padding: 3px;">
		<form id="newsCenterAddForm" method="post">
			<table class="grid">
				<tr>
					<td>标题</td>
					<td><input name="newsTitle" type="text" placeholder="请输入标题"
						class="easyui-validatebox span2" style="width: 100%;"
						data-options="required:true" value=""></td>
					<td>发布时间</td>
					<td><input name="newsDate" placeholder="请选择发布时间"
						onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"
						readonly="readonly" data-options="required:true" value="" /></td>
					<td>新闻状态</td>
					<td><select name="newsStatus" class="easyui-combobox"
						data-options="width:140,height:25,editable:false,panelHeight:'auto'">
							<option value="0" selected>有效</option>
							<option value="1">无效</option>
					</select></td>
				</tr>
				<tr>
					<td>新闻类别</td>
					<td><select name="newsType" class="easyui-combobox"
						data-options="width:140,height:25,editable:false,panelHeight:'auto'">
							<option value="1" selected>小贷新闻</option>
							<option value="2">外部新闻</option>
					</select></td>
					<td>外部新闻来源</td>
					<td><input name="newsSource" type="text" placeholder="请输入外部新闻来源"
						class="easyui-validatebox span2" value=""></td>
					<td>外部新闻链接</td>
					<td><input name="newsHref" type="text" placeholder="请输入外部新闻链接"
						class="easyui-validatebox span2" style="width: 100%;" value=""></td>
				</tr>
				<tr>
					<td>新闻配图</td>
					<td><input type="text" id="pictureHref" name="pictureHref"
						style="width: 100%;" value=""></td>
					<td><a href="javascript:void(0)" onclick="upImage()">上传图片</a></td>
					<td><img id="showImage" src="static/style/images/noimg.png"
						style="width: 120px; height: 100px;"> <!-- 定义一个新编辑器,但是会隐藏它,因为只会用到图片上传 -->
						<textarea id="uploadImage"></textarea></td>
				</tr>
			</table>
			<table class="grid">
				<tr>
					<td>新闻摘要</td>
					<td>
						<textArea id="newsDigest" name="newsDigest"
							style="width: 100%; height: 100%;"></textArea>
					</td>
				</tr>
				<tr>
					<td>新闻内容</td>
					<td>
						<!-- 加载编辑器的容器 --> <textArea id="newsContent" name='newsContent'
							style="width: 100%; height: 100%;"></textArea>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>