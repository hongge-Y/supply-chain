<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="添加用户"/>
</jsp:include>

<div class="card form-card">
    <div class="card-header"><h2>添加新用户</h2></div>
    <form id="addForm" action="${pageContext.request.contextPath}/user/save" method="post">
        <div class="form-group"><label>用户编码<span class="required">*</span></label><input type="text" name="userCode" required placeholder="请输入编码"></div>
        <div class="form-group"><label>真实姓名<span class="required">*</span></label><input type="text" name="userName" required placeholder="请输入姓名"></div>
        <div class="form-group"><label>密码<span class="required">*</span></label><input type="password" name="userPassword" required placeholder="至少6位"></div>

        <div class="form-group">
            <label>性别</label>
            <div class="radio-group">
                <label><input type="radio" name="gender" value="2" checked> 男</label>
                <label><input type="radio" name="gender" value="1"> 女</label>
            </div>
        </div>

        <div class="form-group"><label>出生日期</label><input type="date" name="birthday"></div>
        <div class="form-group"><label>邮箱</label><input type="email" name="email" placeholder="example@domain.com"></div>
        <div class="form-group"><label>电话</label><input type="text" name="phone" placeholder="手机号码"></div>
        <div class="form-group"><label>地址</label><input type="text" name="address" placeholder="联系地址"></div>
        <div class="form-group"><label>简介</label><textarea name="userDesc" rows="3" placeholder="简要描述..."></textarea></div>
        <div class="form-group">
            <label>角色<span class="required">*</span></label>
            <select name="userRole">
                <option value="">请选择角色</option>
                <c:forEach items="${roles}" var="role">
                    <option value="${role.id}">${role.roleName}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-actions">
            <c:if test="${sessionScope.roleCode eq 'DSSCM_ADMIN' or sessionScope.roleCode eq 'DSSCM_MANAGER'}">
                <button type="submit" class="btn btn-success">保存</button>
            </c:if>
            <c:if test="${sessionScope.roleCode ne 'DSSCM_ADMIN' and sessionScope.roleCode ne 'DSSCM_MANAGER'}">
                <button type="button" class="btn btn-primary" onclick="submitEditApply('user','add',null)">提交审批新增</button>
            </c:if>
            <a href="${pageContext.request.contextPath}/user/list" class="btn btn-outline">取消</a>
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
            window.location.href = '${pageContext.request.contextPath}/user/list';
        } else {
            alert('提交失败：' + d);
        }
    });
}
</script>
<jsp:include page="../footer.jsp"/>
