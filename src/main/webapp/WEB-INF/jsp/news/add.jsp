<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="发布新闻"/>
</jsp:include>

<c:set var="isAdminOrManager" value="${sessionScope.roleCode eq 'DSSCM_ADMIN' or sessionScope.roleCode eq 'DSSCM_MANAGER'}"/>

<div class="card form-card">
    <div class="card-header"><h2>发布新闻</h2></div>
    <form id="addForm" action="${pageContext.request.contextPath}/news/save" method="post">
        <input type="hidden" name="approvalStatus" id="approvalStatus" value="0">
        <div class="form-group"><label>标题<span class="required">*</span></label><input type="text" name="title" size="40" required placeholder="输入新闻标题"></div>
        <div class="form-group"><label>内容<span class="required">*</span></label><textarea name="content" rows="6" placeholder="请输入新闻内容..."></textarea></div>
        <div class="form-actions">
            <c:if test="${isAdminOrManager}">
                <button type="submit" class="btn btn-success" onclick="document.getElementById('approvalStatus').value='2'">保存</button>
            </c:if>
            <c:if test="${not isAdminOrManager}">
                <button type="button" class="btn btn-primary" onclick="submitEditApply('news','add',null)">提交审批新增</button>
            </c:if>
            <a href="${pageContext.request.contextPath}/news/list" class="btn btn-outline">取消</a>
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
            window.location.href = '${pageContext.request.contextPath}/news/list';
        } else {
            alert('提交失败：' + d);
        }
    });
}
</script>
<jsp:include page="../footer.jsp"/>
