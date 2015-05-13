<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td width="51%">&nbsp;</td>
		<td width="13%">共${totalPageCount}条记录
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
		<td width="12%">当前第<span style="color:red;">${ pageNum}</span>/${totalPage }页</td>
	</tr>
</table>

<script type="text/javascript">
	/* 上一页/下一页 */
	$("#fir").click(function(){
		$("#pageNum").val(1);
		$("form:first").submit();
	});
	$("#pre").click(function(){
		$("#pageNum").val($("#pageNum").val()*1-1);
		$("form:first").submit();
	});
	$("#next").click(function(){
		$("#pageNum").val($("#pageNum").val()*1+1);
		$("form:first").submit();
	});
	$("#last").click(function(){
		$("#pageNum").val(${totalPage});
		$("form:first").submit();
	});
	//---------控制显示----------
	if(${ pageNum}==1 && ${ totalPage}>1){
		$("#fir").css("display","none");
		$("#pre").css("display","none");
		$("#next").css("display","block");
		$("#last").css("display","block");
	}
	if(${ totalPage}==${pageNum} && ${ totalPage}>1){
		$("#fir").css("display","block");
		$("#pre").css("display","inline");
		$("#next").css("display","none");
		$("#last").css("display","none");
	}
	if(${ totalPage}==${pageNum} && ${ totalPage}==1){
		$("#fir").css("display","none");
		$("#pre").css("display","none");
		$("#next").css("display","none");
		$("#last").css("display","none");
	}
</script>