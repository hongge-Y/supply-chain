<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="商品详情"/>
</jsp:include>

<div class="card form-card detail-view">
    <div class="card-header"><h2>📦 商品详情</h2></div>
    <table class="detail-table">
        <tr><td class="detail-label">名称</td><td class="detail-value">${product.name}</td></tr>
        <tr><td class="detail-label">描述</td><td class="detail-value">${product.description}</td></tr>
        <tr><td class="detail-label">价格</td><td class="detail-value">${product.price}</td></tr>
        <tr><td class="detail-label">位置</td><td class="detail-value">${product.placement}</td></tr>
        <tr><td class="detail-label">库存</td><td class="detail-value">${product.stock}</td></tr>
        <tr><td class="detail-label">一级分类</td><td class="detail-value">${categoryName1}</td></tr>
        <tr><td class="detail-label">二级分类</td><td class="detail-value">${categoryName2}</td></tr>
        <tr><td class="detail-label">三级分类</td><td class="detail-value">${categoryName3}</td></tr>
        <tr><td class="detail-label">审批状态</td><td class="detail-value">
            <c:choose>
                <c:when test="${product.approvalStatus eq 0}"><span class="status-badge secondary">草稿</span></c:when>
                <c:when test="${product.approvalStatus eq 1}"><span class="status-badge warning">待审批</span></c:when>
                <c:when test="${product.approvalStatus eq 2}"><span class="status-badge success">已通过</span></c:when>
                <c:when test="${product.approvalStatus eq 3}"><span class="status-badge danger">已驳回</span></c:when>
                <c:otherwise>未知</c:otherwise>
            </c:choose>
        </td></tr>
    </table>
    <div class="form-actions">
        <a href="${pageContext.request.contextPath}/product/list" class="btn btn-primary">← 返回列表</a>
    </div>
</div>
<jsp:include page="../footer.jsp"/>
