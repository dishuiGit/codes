<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<link href="css/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<script type="text/javascript">
	$(function() {
		$("#query").click(function() {
			$("[name='pageNum']").val(1);
			$("form:first").submit();
		});
	});
</script>
<div class="content-right">
	<div class="content-r-pic_w">
		<div style="margin:8px auto auto 12px;margin-top:6px">
			<span class="page_title">任务查询</span>
		</div>
	</div>
	<div class="content-text">
		<form action="tasks.jsp" method="post"> 
			<div class="square-o-top">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					style="font-size:14px; font-weight:bold; font-family:"黑体";">
					<tr>
						<td width="8%">供&nbsp;应&nbsp;商:</td>
						<td width="29%">
						<s:select name="" list="supplierList" listKey="uuid" listValue="name" headerKey="-1" headerValue="----请-选-择----" cssStyle="width:137px"></s:select>
						</td>
						<td width="8%">发货方式:</td>
						<td width="45%">
						<s:select name="" list="@cn.itcast.erp.invoice.supplier.vo.SupplierModel@needsViewMap" headerKey="-1" headerValue="----请-选-择----" cssStyle="width:137px"></s:select>
						</td>
						<td width=""><a id="query"> 
							<img src="images/can_b_01.gif" border="0" /> </a>
						</td>
					</tr>
				</table>
			</div>
			<!--"square-o-top"end-->
			<div class="square-order">
				<table width="100%" border="1" cellpadding="0" cellspacing="0">
					<tr align="center"
						style="background:url(images/table_bg.gif) repeat-x;">
						<td width="8%" height="30">订单类别</td>
						<td width="11%">供应商</td>
						<td width="7%">发货方式</td>
						<td width="6%">联系人</td>
						<td width="12%">联系方式</td>
						<td width="30%">地址</td>
						<td width="15%">订单状态</td>
						<td width="6%">详情</td>
					</tr>
					<s:iterator value="orderList">
						<tr align="center" bgcolor="#FFFFFF">
							<td height="30">${typeView}</td>
							<td>${supplier.name}</td>
							<td>${supplier.needsView}</td>
							<td>${supplier.contact}</td>
							<td>${supplier.tele}</td>
							<td align="left">&nbsp;${supplier.address}</td>
							<td>${orderStateView }</td>
							<td>
								<a href="transport_taskDetail.action?om.uuid=${uuid}">
									<img src="images/icon_3.gif" />详情
								</a>
							</td>
						</tr>
					</s:iterator>
				</table>
			</div>
		</form>
	</div>
	<div class="content-bbg"></div>
</div>
