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

<#list annotations as annotation>
<#nt><@includeModel object=annotation/>
</#list>
<#lt>public ${type} ${name}<#if superClassName??> extends ${superClassName}</#if><#if interfaceName??> implements ${interfaceName}</#if> {
}
