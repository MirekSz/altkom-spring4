<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ attribute name="value" required="true" type="java.lang.Object" %>
<s:eval expression="cs.convert(value,T(java.lang.String))"></s:eval>
