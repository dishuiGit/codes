<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<link href="css/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="js/Calendar.js"></script>
<script type="text/javascript">
	/*
	$(function() {
		$("#all").click(function() {
			$("[name=resources]:checkbox").attr("checked",$("#all").attr("checked")=="checked");
		});
		$("#reverse").click(function() {
			$("[name=resources]:checkbox").each(function () {
                $(this).attr("checked", !$(this).attr("checked"));
            });

		});
	});
	*/
	$(function(){
		//全选
		$("#all").click(function(){
			//将下面所有name="resUuids"的组件全部设置为选中状态:错误
			//将下面所有name="resUuids"的组件全部设置为全选按钮的状态:正确
			//alert($(this).attr("checked"));
			//js:语法 false ,False,0表示false,其他全为true
			$("[name=resUuids]").attr("checked",$(this).attr("checked") == "checked");
		});
		//反选
		$("#reverse").click(function(){
			//将name="resUuids"的组件的状态设置为相反的状态
			//alert($("[name=resUuids]").attr("checked")=="checked");
			//$("[name=resUuids]")如果取出的是单个对象，值就是对应的数据
			//$("[name=resUuids]")如果取出的是多个对象，值是数组中第一个组件对应的数据
			//$("[name=resUuids]").attr("checked",!($("[name=resUuids]").attr("checked")=="checked"));
			
			//每一个组件切换成自己的状态的反状态
			$("[name=resUuids]").each(function(){
				//alert($(this).attr("checked"));
				$(this).attr("checked",!($(this).attr("checked") == "checked"));
			});
			checkAll2();
		});
		
		//切换选中
		$("[name=resUuids]").click(function(){
			//修改全选按钮的状态为？
			//如果下面的全部选中，全选为选中，否则为不选中
			//js:语法 &表示位运算与运算
			checkAll2();
		});
		
		function checkAll2(){
			var allFlag = true;
			$("[name=resUuids]").each(function(){
				var flag = $(this).attr("checked") == "checked";				
				allFlag = allFlag && flag;
			});
			$("#all").attr("checked",allFlag);
		}
		
	});
	
	
	
</script>
<div class="content-right">
	<div class="content-r-pic_w">
		<div style="margin:8px auto auto 12px;margin-top:6px">
			<span class="page_title">角色管理</span>
		</div>
	</div>
	<div class="content-text">
		<div class="square-order">
			<s:form action="role_save" method="post">
			<s:hidden name="rm.uuid"/>
  			<div style="border:1px solid #cecece;">
				<table width="100%"  border="0" cellpadding="0" cellspacing="0">
				  <tr bgcolor="#FFFFFF">
				    <td>&nbsp;</td>
				  </tr>
				</table>
				<table width="100%"  border="0" cellpadding="0" cellspacing="0">
				    <tr  bgcolor="#FFFFFF">
				      <td width="18%" height="30" align="center">角色名称</td>
				      <td width="32%">
				      	<s:textfield name="rm.name" size="25"/>
				      </td>
				      <td width="18%" align="center">角色编码</td>
				      <td width="32%">
				      	<s:textfield name="rm.code" size="25"/>
				      </td>
				    </tr>
				    <tr  bgcolor="#FFFFFF">
				      <td colspan="4">&nbsp;</td>
				    </tr>
				    <tr  bgcolor="#FFFFFF">
				      <td width="18%" height="30" align="center">资源名称</td>
				      <td width="82%" colspan="3">
				      	<input type="checkbox" id="all">全选&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				      	<input type="checkbox" id="reverse">反选
				      </td>
				    </tr>
				    <tr  bgcolor="#FFFFFF">
				      <td width="18%" height="30" align="center">&nbsp;</td>
				      <td width="82%" colspan="3">
				      	<s:checkboxlist name="resUuids" list="resList" listKey="uuid" listValue="name"></s:checkboxlist>
				      </td>
				    </tr>
				     <tr  bgcolor="#FFFFFF">
				      <td width="18%" height="30" align="center">菜单名称</td>
				      <td width="82%" colspan="3">
				      	<input type="checkbox" id="all2">全选&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				      	<input type="checkbox" id="reverse2">反选
				      </td>
				    </tr>
				    <tr  bgcolor="#FFFFFF">
				      <td width="18%" height="30" align="center">&nbsp;</td>
				      <td width="82%" colspan="3">
				      	<s:checkboxlist name="menuUuids" list="menuList" listKey="uuid" listValue="name"></s:checkboxlist>
				      </td>
				    </tr>
				    <tr  bgcolor="#FFFFFF">
				      <td colspan="4">&nbsp;</td>
				    </tr>
				</table>
			</div>
			<div class="order-botton">
				<div style="margin:1px auto auto 1px;">
					<table width="100%"  border="0" cellpadding="0" cellspacing="0">
					  <tr>
					    <td>
					    	<a href="javascript:document.forms[0].submit()"><img src="images/order_tuo.gif" border="0" /></a>
					    </td>
					    <td>&nbsp;</td>
					    <td><a href="#"><img src="images/order_tuo.gif" border="0" /></a></td>
					    <td>&nbsp;</td>
					    <td><a href="#"><img src="images/order_tuo.gif" border="0" /></a></td>
					  </tr>
					</table>
				</div>
			</div>
			</s:form>
		</div><!--"square-order"end-->
	</div><!--"content-text"end-->
	<div class="content-bbg"><img src="images/content_bbg.jpg" /></div>
</div>
