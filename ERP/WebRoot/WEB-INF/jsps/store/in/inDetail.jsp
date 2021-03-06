<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<link href="css/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<script type="text/javascript">
	
/*
	$(function(){
		//缓存机制
		var uuidArr = new Array();
		var nameArr = new Array();
		var i = 0;
		<s:iterator value="storeList">
			uuidArr[i] = ${uuid};
			nameArr[i] = "${name}";
			i++;
		</s:iterator>
		
		//入库按钮
		$(".oper").click(function(){
			$(".in").remove();
			//动态生成行，行中有仓库信息与剩余未入库数量
			$nowTr = $(this).parent().parent();
			var odmUuid = $nowTr.attr("odm");
			//根据订单明细uuid获取当前未入库的商品剩余数量
			$.post("orderDetail_ajaxGetSurplusByUuid.action",{"odmUuid":odmUuid},function(data){
				//data中封装的就是surplus数据变量   data.surplus
				var surplus = data.surplus;
				
				$tr = $('<tr class="in"></tr>');
				
				$td1 = $('<td align="right">仓库：</td>');
				$tr.append($td1);
				
				$td2 = $('<td height="30"></td>');
				$select = $('<select id="store" style="width:200px"></select>');
				for(var i = 0;i<uuidArr.length;i++){
					$op = $('<option value="'+uuidArr[i]+'">'+nameArr[i]+'</option>');
					$select.append($op);
				}
				$td2.append($select);
				$tr.append($td2);
				
				$td3 = $('<td align="right">入库量：</td>');
				$tr.append($td3);
				
				$td4 = $('<td><input type="text" value="'+surplus+'" id="inNum"></td>');
				$tr.append($td4);
				
				$td5 = $('<td align="center"><a class="ajaxIn xiu" href="javascript:void(0)"><img src="images/icon_3.gif">确定</a></td>');
				$tr.append($td5);
				
				//添加位置
				$nowTr.after($tr);
			});
		});
		
		//确定
		$(".ajaxIn").live("click",function(){
			//需求：入库操作
			//将什么货物多少个放置到什么地方，谁操作的
			//参数：哪个货物(货物uuid),入库到哪个仓库(仓库uuid),入库人(session数据),数量(num)
			//		货物ID对业务无意义的，应该传明细id
			var $nowTr = $(this).parent().parent();
			var $prevTr = $nowTr.prev();
			
			var jsonParam = {};
			jsonParam["num"] = $("#inNum").val();
			jsonParam["storeUuid"] = $("#store").val();
			jsonParam["odmUuid"] = $(this).parent().parent().prev().attr("odm");
			$.post("order_ajaxInGoods.action",jsonParam,function(data){
				//data中包含有两个数据 data.num  data.surplus
				var num = data.num;
				var surplus = data.surplus;
				
				//如果全部订单项都入库完毕，隐藏数据区，显示返回区
				//if(所有订单明细全部入库完毕){
				//if(入库明细数据行只有1行，并且本次操作返回剩余数量为0){
				if($(".inTr").length == 1 && surplus == 0){
					//显示2个allInTitle return
					$("#allInTitle").show();
					$("#return").show();
					//隐藏2个inOrderTitle inOrder
					$("#inOrderTitle").hide();
					$("#inOrder").hide();
					return ;
				}
				
				//如果当前明细已经入库完毕，直接删除对应的行
				if(surplus == 0){
					$nowTr.remove();
					$prevTr.remove();
					return;
				}
				
				//修改三个地方
				$prevTr.children("td:eq(2)").html(num - surplus);
				$prevTr.children("td:eq(3)").html(surplus);
				$("#inNum").val(surplus);
			});
			
		});
		
	});
	*/
	$(function(){
		$(".oper").click(function(){
			//alert('oper');
			//首先移除 动态生成的行
			$(".in").remove();
			var $obj = $(this);
			var url = "store_list.action";
			var params = {};
			var callback = function(data){
					
				//alert(data[0].name);
				var $tr = $('<tr class="in"></tr>');
				
				var $td0 = $('<td align="right">仓库：</td>');
				$tr.append($td0);
				var $td1 = $('<td height="30"></td>');
					//需要显示仓库(ajax)
					var $select = $('<select name="smUuid" style="width:200px"></select>');
					for(var i=0;i<data.length;i++){
						//alert(data[i].uuid);						
						var $option = $('<option value="'+data[i].uuid+'">'+data[i].name+'</option>')
						$select.append($option);
					}
					$td1.append($select);	
				$tr.append($td1);
				var $td2 = $('<td align="right">入库量：</td>');
				$tr.append($td2);
				var $td3 = $('<td><input id="inNum" value="80" type="text"></td>');
				$tr.append($td3);
				var $td4 = $('<td align="center"><a href="javascript:void(0)" goodsUuid="'+$obj.attr("goodsUuid")+'" orderDetailUuid="'+$obj.attr("orderDetailUuid")+'" class="ajaxInStore xiu" ><img src="images/icon_3.gif">确定</a></td>');
				$tr.append($td4);
				
				//加行
				$obj.parent().parent().after($tr);
			};
			$.post(url,params,callback);
		});	
		//入库
		//未来事件
		$(".ajaxInStore").live("click",function(){
			//获取入库量
			//alert($("#inNum").val());
			//获取商品
			//alert($(this).attr("goods"));
			//alert($("select[name='smUuid']").val());
			var url = "storeOper_ajax_inStore.action";
			var $obj = $(this);
			var params = {};
			params["orderDetailUuid"] = $obj.attr("orderDetailUuid");
			params["goodsUuid"] = $obj.attr("goodsUuid");
			params["storeUuid"] = $("select[name='smUuid']").val();
			params["inStoreNum"] = $("#inNum").val();
			
			$.post(url,params,function(data){
				
				var tmp = $obj.attr("orderDetailUuid");
				var $shengyu = $("#"+tmp+"");
				$shengyu.html(data.suplus);
				
				//总共
				var $prev2 = $shengyu.prev().prev().html()*1;
				$shengyu.prev().html($prev2-data.suplus);
			});
				
			
			
		});
	})
</script>
<div class="content-right">
	<div class="content-r-pic_w">
		<div style="margin:8px auto auto 12px;margin-top:6px">
			<span class="page_title">入库</span>
		</div>
	</div>
	<div class="content-text">
			<div class="square-o-top">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					style="font-size:14px; font-weight:bold; font-family:"黑体";">
					<tr>
						<td>订 单 号:</td>
						<td class="order_show_msg">${om.orderNum }</td>
						<td>商品总量:</td>
						<td class="order_show_msg">${om.totalNum }</td>
					</tr>
				</table>
			</div>
			<!--"square-o-top"end-->
			<div class="square-order">
				<center id="inOrderTitle" style="text-decoration:underline;font-size:16px; font-weight:bold; font-family:"黑体";">&nbsp;&nbsp;&nbsp;&nbsp;单&nbsp;&nbsp;据&nbsp;&nbsp;明&nbsp;&nbsp;细&nbsp;&nbsp;&nbsp;&nbsp;</center>
				<br/>
				<table id="inOrder" width="100%" border="1" cellpadding="0" cellspacing="0">
					<tr align="center"
						style="background:url(images/table_bg.gif) repeat-x;">
						<td width="20%" height="30">商品名称</td>
						<td width="30%">总数量</td>
						<td width="10%">已入库数量</td>
						<td width="30%">剩余数量</td>
						<td width="10%">入库</td>
					</tr>
					<s:iterator value="#om.odms">
							<tr class="inTr" align="center" bgcolor="#FFFFFF">
								<td height="30">${gm.name }</td>
								<td>${num }</td>
								<td >${num-suplus}</td>
								<td id="${uuid}">${suplus }</td>
								<td><a orderDetailUuid="${uuid}" goodsUuid="${gm.uuid }" href="javascript:void(0)" class="oper xiu"><img src="images/icon_3.gif" />入库</a></td>
							</tr>
					</s:iterator>
				</table>
				<center id="allInTitle" style="display:none;font-size:16px; font-weight:bold; font-family:"黑体";">&nbsp;&nbsp;&nbsp;&nbsp;全&nbsp;&nbsp;部&nbsp;&nbsp;入&nbsp;&nbsp;库&nbsp;&nbsp;&nbsp;&nbsp;</center>
				<table id="return" style="display:none" >
					<tr>
						<td>&nbsp;</td>
						<td width="100%" align="center">
							<a href="order_inStoreList.action" style="color:#f00;font-size:20px;padding-top:2px;font-weight:bold;text-decoration:none;width:82px;height:28px;display:block;background:url(images/btn_bg.jpg)">
								返回
							</a>
						</td>
						<td>&nbsp;</td>
					</tr>
				</table>
			</div>
	</div>
	<div class="content-bbg"></div>
</div>
