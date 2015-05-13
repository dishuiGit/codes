<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<link href="css/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="js/Calendar.js"></script>
<script type="text/javascript">
	/* $(function() {
		$("#all").click(function() {
			$("[name=roles]:checkbox").attr("checked",$("#all").attr("checked")=="checked");
		});
		$("#reverse").click(function() {
			$("[name=roles]:checkbox").each(function () {
                $(this).attr("checked", !$(this).attr("checked"));
            });
		});
		$("#supplier").change(function(){
			$.post("goodsTypeAction_getAll.action",{"gm.supplier.uuid":$(this).val()},function(data){
				$("#goodsType").empty();
				for(var i = 0;i<data.gtList.length;i++){
					var goodsType = data.gtList[i];
					var $option = $("<option value='"+goodsType.uuid+"'>"+goodsType.goodsTypeName+"</option>");	//创建option对象(jQuery格式)
					$("#goodsType").append($option);				//将option对象添加到select组件中
				}
			});
		});
	}); */
	$(function(){
		$("#supplierId").change(function(){
			var url = "goodsType_getAllGT.action";
			var params = {"supplierUuid":$(this).val()};
			var callback = function(data){
				//清空option
				$("#goodsType").empty();
				for(var i = 0;i<data.gtList.length;i++){
					var $goodsType = data.gtList[i];
					var $option = $("<option value='"+$goodsType.uuid+"'>"+$goodsType.name+"</option>");
					$("#goodsType").append($option);
				}
			};
			$.post(url,params,callback);
		}); 
		
	})
</script>
<div class="content-right">
	<div class="content-r-pic_w">
		<div style="margin:8px auto auto 12px;margin-top:6px">
			<span class="page_title">商品管理</span>
		</div>
	</div>
	<div class="content-text">
		<div class="square-order">
			<s:form action="goods_save" method="post">
			<s:hidden name="gm.uuid" />
  			<div style="border:1px solid #cecece;">
				<table width="100%"  border="0" cellpadding="0" cellspacing="0">
				  <tr bgcolor="#FFFFFF">
				    <td>&nbsp;</td>
				  </tr>
				</table>
				<table width="100%"  border="0" cellpadding="0" cellspacing="0">
				    <tr  bgcolor="#FFFFFF">
				      <td width="18%" height="30" align="center">供&nbsp;应&nbsp;商</td>
				      <td width="32%">
				      	<s:select id="supplierId" name="gm.gtm.sm.uuid" list="supplierList" listKey="uuid" listValue="name"></s:select>
				      </td>
				      <td width="18%"align="center">商品类别</td>
				      <td width="32%">
				      	<s:select id="goodsType" name="gm.gtm.uuid" list="gtmList" listKey="uuid" listValue="name"></s:select>
					  </td>
				    </tr>
				    <tr bgcolor="#FFFFFF">
					  <td colspan="4">&nbsp;</td>
					</tr>
				    <tr  bgcolor="#FFFFFF">
				      <td align="center">商品名称</td>
				      <td>
				      	<s:textfield size="25" name="gm.name" />
				      </td>
				      <td  align="center">产&nbsp;&nbsp;&nbsp;&nbsp;地</td>
				      <td >
				      	<s:textfield size="25" name="gm.origin" />
				      </td>
				    </tr>
				     <tr bgcolor="#FFFFFF">
					  <td colspan="4">&nbsp;</td>
					</tr>
				    <tr  bgcolor="#FFFFFF">
				      <td height="30" align="center">生产厂家</td>
				      <td>
				      	<s:textfield size="25" name="gm.producer" />
				      <td align="center">单&nbsp;&nbsp;&nbsp;&nbsp;位</td>
				      <td>
				      	<s:textfield size="25" name="gm.unit" />
					  </td>
				     </tr>
				    <tr bgcolor="#FFFFFF">
					  <td colspan="4">&nbsp;</td>
					</tr>
				    <tr  bgcolor="#FFFFFF">
				      <td height="30" align="center">进货单价</td>
				      <td>
				      	<s:textfield size="25" name="gm.inPrice" />
					  </td>
				      <td align="center">销售单价</td>
				      <td>
				      	<s:textfield size="25" name="gm.outPrice" />
					  </td>
				    </tr>
				    <tr bgcolor="#FFFFFF">
					  <td colspan="4">&nbsp;</td>
					</tr>
				    <tr  bgcolor="#FFFFFF">
				      <td height="30" align="center">体&nbsp;&nbsp;&nbsp;&nbsp;积</td>
				      <td>
				      	<s:textfield size="25" />
					  </td>
				      <td align="center">&nbsp;</td>
				      <td>&nbsp;</td>
				    </tr>
				     <tr bgcolor="#FFFFFF">
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
