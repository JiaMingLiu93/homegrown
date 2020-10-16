<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="com.homegrown.tools.code.generator.struct.processor.demo.model.GeneratedType" -->
<#if hasPackageName()>
package ${packageName};
</#if>

<#list importTypeNames as importedType>
import ${importedType};
</#list>

<#if annotations??>
<#list annotations as annotation>
<#nt><@includeModel object=annotation/>
</#list>
</#if>
<#lt>public ${type} ${name}<#if superClassName??> extends ${superClassName}</#if><#if interfaceName??> implements ${interfaceName}</#if> {

<#if fields??>
<#list fields as field><#if field.used><#nt>    <@includeModel object=field/>
</#if></#list>
</#if>

<#if methods??>
<#list methods as method>
<#nt>    <@includeModel object=method/>
</#list>
</#if>
}
