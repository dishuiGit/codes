package app.${table}.bean;

import java.io.Serializable;

public class ${table?cap_first}Bean implements Serializable {
<#list attr_list as attr>
	<#if attr?ends_with("_")>
	<#-- 变量赋值 -->
	<#assign newAttr=attr?substring(0,attr?length-1)/>
	private Integer ${newAttr};
	public Integer get${newAttr?cap_first}() {
		return ${newAttr};
	}
	public void set${newAttr?cap_first}(Integer ${newAttr}) {
		this.${newAttr} = ${newAttr};
	}
	<#elseif attr?ends_with("_d")>
	<#-- 变量赋值 -->
	<#assign newAttr1=attr?substring(0,attr?length-2)/>
	private Double ${newAttr1};
	public Double get${newAttr1?cap_first}() {
		return ${newAttr1};
	}
	public void set${newAttr1?cap_first}(Double ${newAttr1}) {
		this.${newAttr1} = ${newAttr1};
	}
	<#else>
	private String ${attr};
	public String get${attr?cap_first}() {
		return ${attr};
	}
	public void set${attr?cap_first}(String ${attr}) {
		this.${attr} = ${attr};
	}
	</#if>
</#list>
}