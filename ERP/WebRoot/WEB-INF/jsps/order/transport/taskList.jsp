<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<link href="css/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="js/Calendar.js"></script>
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
			<span class="page_title">商品运输管理</span>
		</div>
	</div>
	<div class="content-text">
		<form action="list.jsp" method="post"> 
			<div class="square-o-top">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					style="font-size:14px; font-weight:bold; font-family:"黑体";">
					<tr>
						<td>下单时间:</td>
						<td>
							<input type="text" size="10" onfocus="c.showMoreDay=false;c.show(this);" readonly="true"/>
						</td>
						<td>到&nbsp;</td>
						<td>
							<input type="text" size="10" onfocus="c.showMoreDay=false;c.show(this);" readonly="true"/>
						</td>
						<td>供&nbsp;应&nbsp;商:</td>
						<td>
							<s:select list="supplierList" listKey="uuid" listValue="name" headerKey="-1" headerValue="----请-选-择----"></s:select>
						</td>
						<td>下单人:</td>
						<td><input type="text" size="10" /></td>
						<td>&nbsp;</td>
						<td><a id="query"> 
							<img src="images/can_b_01.gif" border="0" /> </a>
						</td>
					</tr>
					<tr>
						<td>审核时间:</td>
						<td>
							<input type="text" size="10" onfocus="c.showMoreDay=false;c.show(this);" readonly="true"/>
						</td>
						<td>到&nbsp;</td>
						<td>
							<input type="text" size="10" onfocus="c.showMoreDay=false;c.show(this);" readonly="true"/>
						</td>
						<td>发货方式:</td>
						<td>
							<s:select list="@cn.itcast.erp.invoice.supplier.vo.SupplierModel@needsViewMap" headerKey="-1" headerValue="----请-选-择----"></s:select>
						</td>
						<td>审核人:</td>
						<td><input type="text" size="10" /></td>
						<td>跟单人:</td>
						<td><input type="text" size="10" /></td>
					</tr>
				</table>
			</div>
			<!--"square-o-top"end-->
			<div class="square-order">
				<table width="100%" border="1" cellpadding="0" cellspacing="0">
					<tr align="center"
						style="background:url(images/table_bg.gif) repeat-x;">
						<td width="10%" height="30">订单类别</td>
						<td width="13%">下单时间</td>
						<td width="13%">制单人</td>
						<td width="13%">审核时间</td>
						<td width="13%">审核人</td>
						<td width="15%">供应商</td>
						<td width="13%">发货方式</td>
						<td width="10%">跟单人</td>
					</tr>
					<s:iterator value="orderList">
						<tr align="center" bgcolor="#FFFFFF">
							<td height="30">${typeView}</td>
							<td>${createTimeView}</td>
							<td>${creater.name}</td>
							<td>${checkTimeView }</td>
							<td>${checker.name}</td>
							<td>${supplier.name}</td>
							<td>${supplier.needsView}</td>
							<td>
								<s:if test="completer == null">
									<img src="images/icon_3.gif" /> 
									<span style="line-height:12px; text-align:center;"> 
										<a href="transport_assignTask.action?om.uuid=${uuid}" class="xiu">任务指派
										</a> 
									</span>
								</s:if>
								<s:else>
									${completer.name}
								</s:else>
							</td>
						</tr>
					</s:iterator>
				</table>
			</div>
		</form>
	</div>
	<div class="content-bbg"></div>
</div>
