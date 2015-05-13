<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td width="51%">&nbsp;</td>
		<td width="13%">共${dataTotal}条记录
		<td width="6%">
			<a id="fir" class="sye">首&nbsp;&nbsp;页</a>
		</td>
		<td width="6%">
			<a id="pre" class="sye">上一页</a>
		</td>
		<td width="6%">
			<a id="next" class="sye">下一页</a>
		</td>
		<td width="6%">
			<a id="last" class="sye">末&nbsp;&nbsp;页</a>
		</td>
		<td width="12%">当前第<span style="color:red;">${pageNum}</span>/${maxPageNum}页</td>
	</tr>
</table>
<s:hidden name="pageNum"/>					
<script type="text/javascript">
	$(function(){
		var pageNum = ${pageNum};
		var maxPageNum = ${maxPageNum};
		
		//控制显示与不显示
		//只有一页，都不显示
		//第一页，显示后2
		//最后页，显示前2
		//其他,都显示
		if(maxPageNum == 1){
			$("#fir").css("display","none");
			$("#pre").css("display","none");
			$("#next").css("display","none");
			$("#last").css("display","none");
		}else if(pageNum == 1){
			$("#fir").css("display","none");
			$("#pre").css("display","none");
			$("#next").css("display","inline");
			$("#last").css("display","inline");
		}else if(pageNum == maxPageNum){
			$("#fir").css("display","inline");
			$("#pre").css("display","inline");
			$("#next").css("display","none");
			$("#last").css("display","none");
		}else {
			$("#fir").css("display","inline");
			$("#pre").css("display","inline");
			$("#next").css("display","inline");
			$("#last").css("display","inline");
		}
		
		$("#fir").click(function(){
			$("[name=pageNum]").val(1);
			$("form:first").submit();
		});
		$("#pre").click(function(){
			$("[name=pageNum]").val($("[name=pageNum]").val()-1);
			$("form:first").submit();
		});
		//next
		$("#next").click(function(){
			//将页码值改变后，提交form
			//parseInt,快捷
			$("[name=pageNum]").val($("[name=pageNum]").val()*1+1);
			$("form:first").submit();
		});
		$("#last").click(function(){
			//$("[name=pageNum]").val(${maxPageNum});
			//$("[name=pageNum]").val(<s:property value="pageNum"/>);
			$("[name=pageNum]").val(maxPageNum);
			$("form:first").submit();
		});
		
	});
</script>