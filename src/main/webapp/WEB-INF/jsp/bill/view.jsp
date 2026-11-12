<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="采购订单详情"/>
</jsp:include>

<div class="card form-card detail-view">
    <div class="card-header"><h2>📋 采购订单详情</h2></div>
    <table class="detail-table">
        <tr><td class="detail-label">账单编码</td><td class="detail-value">${bill.billCode}</td></tr>
        <tr><td class="detail-label">商品名称</td><td class="detail-value">${bill.productName}</td></tr>
        <tr><td class="detail-label">描述</td><td class="detail-value">${bill.productDesc}</td></tr>
        <tr><td class="detail-label">单位</td><td class="detail-value">${bill.productUnit}</td></tr>
        <tr><td class="detail-label">数量</td><td class="detail-value">${bill.productCount}</td></tr>
        <tr><td class="detail-label">总价</td><td class="detail-value">${bill.totalPrice}</td></tr>
        <tr>
            <td class="detail-label">支付状态</td>
            <td class="detail-value">
                <span class="status-badge ${bill.isPayment eq 2 ? 'success' : 'warning'}">
                    ${bill.isPayment eq 1 ? '未支付' : '已支付'}
                </span>
            </td>
        </tr>
        <tr><td class="detail-label">创建时间</td><td class="detail-value">${bill.creationDate}</td></tr>
    </table>
    <div class="form-actions">
        <a href="${pageContext.request.contextPath}/bill/list" class="btn btn-primary">← 返回列表</a>
    </div>
</div>
<jsp:include page="../footer.jsp"/>
