<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="修改采购订单"/>
</jsp:include>

<div class="card form-card">
    <div class="card-header"><h2>修改采购订单</h2>
        <c:if test="${bill.approvalStatus eq 3}">
            <span class="status-badge danger">已驳回，请修改后重新提交</span>
        </c:if>
    </div>
    <form id="editForm" action="${pageContext.request.contextPath}/bill/update" method="post" onsubmit="return validateNumericFields(this)">
        <input type="hidden" name="id" value="${bill.id}">
        <input type="hidden" name="approvalStatus" id="approvalStatus" value="${bill.approvalStatus eq 3 ? 0 : bill.approvalStatus}">
        <div class="form-group"><label>账单编码<span class="required">*</span></label><input type="text" name="billCode" value="${bill.billCode}" required></div>
        <div class="form-group"><label>商品名称<span class="required">*</span></label><input type="text" name="productName" value="${bill.productName}" required></div>
        <div class="form-group"><label>商品描述</label><textarea name="productDesc" rows="2">${bill.productDesc}</textarea></div>
        <div class="form-group"><label>商品单位</label><input type="text" name="productUnit" value="${bill.productUnit}"></div>
        <div class="form-group"><label>商品数量<span class="required">*</span></label>
            <input type="text" name="productCount" value="${bill.productCount}" data-type="number" data-label="商品数量" required>
        </div>
        <div class="form-group"><label>总价<span class="required">*</span></label>
            <input type="text" name="totalPrice" value="${bill.totalPrice}" data-type="number" data-label="总价" required>
        </div>
        <div class="form-group"><label>是否支付</label>
            <select name="isPayment">
                <option value="1" ${bill.isPayment eq 1 ? 'selected' : ''}>未支付</option>
                <option value="2" ${bill.isPayment eq 2 ? 'selected' : ''}>已支付</option>
            </select>
        </div>
        <div class="form-group"><label>供应商<span class="required">*</span></label>
            <select name="providerId" required>
                <option value="">请选择供应商</option>
                <c:forEach items="${providers}" var="p">
                    <option value="${p.id}" ${p.id eq bill.providerId ? 'selected' : ''}>${p.proName}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-actions">
            <c:if test="${sessionScope.roleCode eq 'DSSCM_ADMIN' or sessionScope.roleCode eq 'DSSCM_MANAGER'}">
                <button type="submit" class="btn btn-success" onclick="document.getElementById('approvalStatus').value='2'">保存</button>
            </c:if>
            <c:if test="${sessionScope.roleCode ne 'DSSCM_ADMIN' and sessionScope.roleCode ne 'DSSCM_MANAGER'}">
                <button type="button" class="btn btn-primary" onclick="submitEditApply('bill','update',${bill.id})">提交审批修改</button>
            </c:if>
            <a href="${pageContext.request.contextPath}/bill/list" class="btn btn-outline">取消</a>
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
            window.location.href = '${pageContext.request.contextPath}/bill/list';
        } else {
            alert('提交失败：' + d);
        }
    });
}
</script>
<jsp:include page="../footer.jsp"/>
