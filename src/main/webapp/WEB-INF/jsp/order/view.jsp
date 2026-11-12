<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="销售订单详情"/>
</jsp:include>

<div class="card form-card detail-view">
    <div class="card-header"><h2>💰 订单基本信息</h2></div>
    <table class="detail-table">
        <tr><td class="detail-label">订单号</td><td class="detail-value">${order.serialNumber}</td></tr>
        <tr><td class="detail-label">顾客姓名</td><td class="detail-value">${order.userName}</td></tr>
        <tr><td class="detail-label">电话</td><td class="detail-value">${order.customerPhone}</td></tr>
        <tr><td class="detail-label">地址</td><td class="detail-value">${order.userAddress}</td></tr>
        <tr><td class="detail-label">商品总数</td><td class="detail-value">${order.proCount}</td></tr>
        <tr><td class="detail-label">总金额</td><td class="detail-value">${order.cost}</td></tr>
        <tr>
            <td class="detail-label">状态</td>
            <td class="detail-value">
                <c:choose>
                    <c:when test="${order.status eq 1}"><span class="status-badge warning">待审核</span></c:when>
                    <c:when test="${order.status eq 2}"><span class="status-badge info">审核通过</span></c:when>
                    <c:when test="${order.status eq 3}"><span class="status-badge purple">配货</span></c:when>
                    <c:when test="${order.status eq 4}"><span class="status-badge success">卖家已发货</span></c:when>
                    <c:when test="${order.status eq 5}"><span class="status-badge success">已收货</span></c:when>
                    <c:otherwise><span class="status-badge">未知</span></c:otherwise>
                </c:choose>
            </td>
        </tr>
        <tr><td class="detail-label">付款方式</td><td class="detail-value">${order.payType eq 1 ? '在线支付' : '货到付款'}</td></tr>
    </table>

    <h3 style="margin-top:30px; font-size:1.1rem; font-weight:600; color:var(--primary);">📋 订单明细</h3>
    <div class="table-responsive" style="margin-top:12px;">
        <table>
            <tr><th>商品ID</th><th>数量</th><th>消费</th></tr>
            <c:forEach items="${details}" var="d">
                <tr>
                    <td>${d.productId}</td>
                    <td>${d.quantity}</td>
                    <td>${d.cost}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div class="form-actions">
        <a href="${pageContext.request.contextPath}/order/list" class="btn btn-primary">← 返回列表</a>
    </div>
</div>
<jsp:include page="../footer.jsp"/>
