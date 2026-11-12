<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="添加供应商"/>
</jsp:include>

<div class="card form-card">
    <div class="card-header"><h2>添加供应商</h2></div>
    <form id="addForm" action="${pageContext.request.contextPath}/provider/save" method="post">
        <input type="hidden" name="approvalStatus" id="approvalStatus" value="0">
        <div class="form-group"><label>供应商编码<span class="required">*</span></label><input type="text" name="proCode" required placeholder="输入编码"></div>
        <div class="form-group"><label>供应商名称<span class="required">*</span></label><input type="text" name="proName" required placeholder="输入名称"></div>
        <div class="form-group"><label>详细描述</label><textarea name="proDesc" rows="2" placeholder="可选描述"></textarea></div>
        <div class="form-group"><label>联系人</label><input type="text" name="proContact" placeholder="联系人姓名"></div>
        <div class="form-group"><label>联系电话</label><input type="text" name="proPhone" placeholder="手机号码"></div>
        <div class="form-group"><label>地址</label><input type="text" name="proAddress" placeholder="联系地址"></div>
        <div class="form-group"><label>传真</label><input type="text" name="proFax" placeholder="传真号码"></div>
        <div class="form-actions">
            <c:if test="${sessionScope.roleCode eq 'DSSCM_ADMIN' or sessionScope.roleCode eq 'DSSCM_MANAGER'}">
                <button type="submit" class="btn btn-success" onclick="document.getElementById('approvalStatus').value='2'">保存</button>
            </c:if>
            <c:if test="${sessionScope.roleCode ne 'DSSCM_ADMIN' and sessionScope.roleCode ne 'DSSCM_MANAGER'}">
                <button type="button" class="btn btn-primary" onclick="submitEditApply('provider','add',null)">提交审批新增</button>
            </c:if>
            <a href="${pageContext.request.contextPath}/provider/list" class="btn btn-outline">取消</a>
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
            window.location.href = '${pageContext.request.contextPath}/provider/list';
        } else {
            alert('提交失败：' + d);
        }
    });
}
</script>
<jsp:include page="../footer.jsp"/>
