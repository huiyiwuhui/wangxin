<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%-- <%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager"%> --%>
<c:if test="${ctx == null}">
	<c:set var="ctx" value="${pageContext.request.contextPath}" scope="application" />
</c:if>
<link href="${ctx}/static/yu/js_css/yu.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/static/yu/js_css/yu.js"></script>
<script type="text/javascript">
F.st();
</script> 