<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="修改销售订单"/>
</jsp:include>

<div class="card form-card">
    <div class="card-header"><h2>修改销售订单</h2></div>

    <%-- 驳回提示 --%>
    <c:if test="${order.approvalStatus eq 3}">
        <div class="alert alert-warning">⛔ 该单据已被驳回，请修改后重新提交审批</div>
    </c:if>

    <form id="editForm" action="${pageContext.request.contextPath}/order/update" method="post">
        <input type="hidden" name="id" value="${order.id}">
        <input type="hidden" name="approvalStatus" id="approvalStatus" value="0">
        <div class="form-group"><label>顾客姓名<span class="required">*</span></label>
            <input type="text" name="userName" value="${order.userName}" required></div>
        <div class="form-group"><label>联系电话</label>
            <input type="text" name="customerPhone" value="${order.customerPhone}"></div>
        <div class="form-group"><label>地址</label>
            <input type="text" name="userAddress" value="${order.userAddress}"></div>
        <div class="form-group"><label>订单号<span class="required">*</span></label>
            <input type="text" name="serialNumber" value="${order.serialNumber}" required></div>
        <div class="form-group"><label>商品数量<span class="required">*</span></label>
            <input type="text" name="proCount" value="${order.proCount}" data-type="number" required></div>
        <div class="form-group"><label>总消费<span class="required">*</span></label>
            <input type="text" name="cost" value="${order.cost}" data-type="number" required></div>
        <div class="form-group"><label>付款方式</label>
            <select name="payType">
                <option value="1" ${order.payType eq 1 ? 'selected' : ''}>在线支付</option>
                <option value="2" ${order.payType eq 2 ? 'selected' : ''}>货到付款</option>
            </select>
        </div>
        <div class="form-group"><label>订单状态</label>
            <select name="status">
                <option value="1" ${order.status eq 1 ? 'selected' : ''}>待审核</option>
                <option value="2" ${order.status eq 2 ? 'selected' : ''}>审核通过</option>
                <option value="3" ${order.status eq 3 ? 'selected' : ''}>配货</option>
                <option value="4" ${order.status eq 4 ? 'selected' : ''}>卖家已发货</option>
                <option value="5" ${order.status eq 5 ? 'selected' : ''}>已收货</option>
            </select>
        </div>
        <div class="form-actions">
            <c:if test="${sessionScope.roleCode eq 'DSSCM_ADMIN' or sessionScope.roleCode eq 'DSSCM_MANAGER'}">
                <button type="submit" class="btn btn-success" onclick="document.getElementById('approvalStatus').value='2'">保存</button>
            </c:if>
            <c:if test="${sessionScope.roleCode ne 'DSSCM_ADMIN' and sessionScope.roleCode ne 'DSSCM_MANAGER'}">
                <button type="button" class="btn btn-primary" onclick="submitEditApply('order','update',${order.id})">提交审批修改</button>
            </c:if>
            <a href="${pageContext.request.contextPath}/order/list" class="btn btn-outline">取消</a>
        </div>
    </form>
</div>
<jsp:include page="../include/apply_modal.jsp"/>
<script>
function submitEditApply(module, action, recordId) {
    var form = document.getElementById('editForm') || document.getElementById('addForm');
    var data = {};
    for (var i = 0; i < form.elements.length; i++) {
        var el = form.elements[i];
        if (el.name && el.name !== 'approvalStatus' && el.type !== 'submit' && el.type !== 'button') {
            if (el.type === 'radio') { if (el.checked) data[el.name] = el.value; }
            else { data[el.name] = el.value; }
        }
    }
    var reason = prompt('请填写申请理由：');
    if (reason === null) return;
    var params = 'module=' + module + '&action=' + action;
    if (recordId) params += '&recordId=' + recordId;
    params += '&reason=' + encodeURIComponent(reason);
    params += '&detailJson=' + encodeURIComponent(JSON.stringify(data));
    fetch('${pageContext.request.contextPath}/approval/apply', {
        method: 'POST',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body: params
    }).then(function(r){ return r.text(); }).then(function(d){
        if (d === 'success') {
            alert('申请已提交，请等待审批');
            window.location.href = '${pageContext.request.contextPath}/order/list';
        } else {
            alert('提交失败：' + d);
        }
    });
}
</script>
<jsp:include page="../footer.jsp"/>
