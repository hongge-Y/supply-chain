<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="添加销售订单"/>
</jsp:include>

<div class="card form-card">
    <div class="card-header"><h2>添加销售订单</h2></div>
    <form id="addForm" action="${pageContext.request.contextPath}/order/save" method="post">
        <input type="hidden" name="approvalStatus" id="approvalStatus" value="0">
        <div class="form-group"><label>顾客姓名<span class="required">*</span></label><input type="text" name="userName" required placeholder="输入顾客姓名"></div>
        <div class="form-group"><label>联系电话</label><input type="text" name="customerPhone" placeholder="手机号码"></div>
        <div class="form-group"><label>地址</label><input type="text" name="userAddress" placeholder="收货地址"></div>
        <div class="form-group"><label>订单号<span class="required">*</span></label><input type="text" name="serialNumber" required placeholder="输入订单号"></div>
        <div class="form-group"><label>商品数量<span class="required">*</span></label>
            <input type="text" name="proCount" data-type="number" data-label="商品数量" required placeholder="请输入数字">
        </div>
        <div class="form-group"><label>总消费<span class="required">*</span></label>
            <input type="text" name="cost" data-type="number" data-label="总消费" required placeholder="请输入数字">
        </div>
        <div class="form-group"><label>付款方式</label>
            <select name="payType">
                <option value="1">在线支付</option>
                <option value="2">货到付款</option>
            </select>
        </div>
        <div class="form-group"><label>订单状态</label>
            <select name="status">
                <option value="1">待审核</option>
                <option value="2">审核通过</option>
                <option value="3">配货</option>
                <option value="4">卖家已发货</option>
                <option value="5">已收货</option>
            </select>
        </div>
        <div class="form-actions">
            <c:if test="${sessionScope.roleCode eq 'DSSCM_ADMIN' or sessionScope.roleCode eq 'DSSCM_MANAGER'}">
                <button type="submit" class="btn btn-success" onclick="document.getElementById('approvalStatus').value='2'">保存</button>
            </c:if>
            <c:if test="${sessionScope.roleCode ne 'DSSCM_ADMIN' and sessionScope.roleCode ne 'DSSCM_MANAGER'}">
                <button type="button" class="btn btn-primary" onclick="submitEditApply('order','add',null)">提交审批新增</button>
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
