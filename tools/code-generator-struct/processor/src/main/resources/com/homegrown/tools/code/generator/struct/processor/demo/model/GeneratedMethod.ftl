<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="com.homegrown.tools.code.generator.struct.processor.demo.model.GeneratedMethod" -->
<#if overridden??>
<#if overridden>@Override</#if>
</#if>
<#lt>${accessibility.keyword} <@includeModel object=returnType/> ${name}(<#list parameters as param><@includeModel object=param/><#if param_has_next>, </#if></#list>)<#if thrownTypes??><@throws/></#if>