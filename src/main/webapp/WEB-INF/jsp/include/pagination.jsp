<%--
  分页公共片段
  依赖的 request attribute:
    - page: PageUtil 对象（含 totalCount, pageIndex, totalPages）
    - searchParams: 搜索参数字符串（如 "userName=${userName}&roleId=${roleId}"），每页用 <c:set> 定义
  用法（在每个列表 JSP 的分页位置）:
    <c:set var="searchParams" value="userName=${userName}&amp;roleId=${roleId}"/>
    <jsp:include page="../include/pagination.jsp"/>
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="pagination">
    <span class="page-info">共 ${page.totalCount} 条，第 ${page.pageIndex}/${page.totalPages} 页</span>
    <div class="page-links">
        <c:if test="${page.pageIndex > 1}">
            <a href="list?pageIndex=1&${searchParams}">« 首页</a>
            <a href="list?pageIndex=${page.pageIndex-1}&${searchParams}">‹ 上页</a>
        </c:if>
        <c:if test="${page.pageIndex < page.totalPages}">
            <a href="list?pageIndex=${page.pageIndex+1}&${searchParams}">下页 ›</a>
            <a href="list?pageIndex=${page.totalPages}&${searchParams}">末页 »</a>
        </c:if>
    </div>
</div>
