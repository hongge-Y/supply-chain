<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="添加商品"/>
</jsp:include>

<div class="card form-card">
    <div class="card-header"><h2>添加商品</h2></div>
    <form id="addForm" action="${pageContext.request.contextPath}/product/save" method="post">
        <input type="hidden" name="approvalStatus" id="approvalStatus" value="0">
        <div class="form-group"><label>名称<span class="required">*</span></label><input type="text" name="name" required placeholder="输入商品名称"></div>
        <div class="form-group"><label>描述</label><textarea name="description" rows="2" placeholder="可选描述"></textarea></div>
        <div class="form-group"><label>价格<span class="required">*</span></label>
            <input type="text" name="price" data-type="number" data-label="价格" required placeholder="请输入数字">
        </div>
        <div class="form-group"><label>摆放位置</label><input type="text" name="placement" placeholder="仓库位置"></div>
        <div class="form-group"><label>库存数量<span class="required">*</span></label>
            <input type="text" name="stock" data-type="number" data-label="库存数量" required placeholder="请输入数字">
        </div>
        <div class="form-group"><label>一级分类</label>
            <select name="categoryLevel1Id">
                <option value="">请选择</option>
                <c:forEach items="${level1List}" var="c1">
                    <option value="${c1.id}">${c1.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-actions">
            <c:if test="${sessionScope.roleCode eq 'DSSCM_ADMIN' or sessionScope.roleCode eq 'DSSCM_MANAGER'}">
                <button type="submit" class="btn btn-success" onclick="document.getElementById('approvalStatus').value='2'">保存</button>
            </c:if>
            <c:if test="${sessionScope.roleCode ne 'DSSCM_ADMIN' and sessionScope.roleCode ne 'DSSCM_MANAGER'}">
                <button type="button" class="btn btn-primary" onclick="submitEditApply('product','add',null)">提交审批新增</button>
            </c:if>
            <a href="${pageContext.request.contextPath}/product/list" class="btn btn-outline">取消</a>
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
            window.location.href = '${pageContext.request.contextPath}/product/list';
        } else {
            alert('提交失败：' + d);
        }
    });
}
</script>
<jsp:include page="../footer.jsp"/>
