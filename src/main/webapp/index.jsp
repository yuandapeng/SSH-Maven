<jsp:directive.page language="java" pageEncoding="UTF-8"/>
<jsp:scriptlet>
String path = request.getContextPath();
response.sendRedirect(path + "/index.action");
</jsp:scriptlet>
