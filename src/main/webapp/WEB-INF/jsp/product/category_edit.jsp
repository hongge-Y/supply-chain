<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="修改商品分类"/>
</jsp:include>

<div class="card form-card">
    <div class="card-header"><h2>修改分类</h2></div>
    <form id="categoryForm" action="${pageContext.request.contextPath}/product/category/update" method="post" onsubmit="return validateForm()">
        <input type="hidden" name="id" value="${category.id}">
        <input type="hidden" name="parentId" value="${category.parentId}">
        <div class="form-group"><label>分类名称<span class="required">*</span></label><input type="text" name="name" value="${category.name}" required></div>
        <div class="form-group"><label>分类级别</label>
            <select name="type">
                <option value="1" ${category.type eq 1 ? 'selected' : ''}>一级</option>
                <option value="2" ${category.type eq 2 ? 'selected' : ''}>二级</option>
                <option value="3" ${category.type eq 3 ? 'selected' : ''}>三级</option>
            </select>
        </div>
        <div class="form-actions">
            <c:if test="${sessionScope.roleCode eq 'DSSCM_ADMIN' or sessionScope.roleCode eq 'DSSCM_MANAGER'}">
                <button type="submit" class="btn btn-success">保存</button>
            </c:if>
            <c:if test="${sessionScope.roleCode ne 'DSSCM_ADMIN' and sessionScope.roleCode ne 'DSSCM_MANAGER'}">
                <button type="submit" class="btn btn-primary">保存并提交审批</button>
            </c:if>
            <a href="${pageContext.request.contextPath}/product/category/list?parentId=${category.parentId}" class="btn btn-outline">取消</a>
        </div>
    </form>
</div>
<script>
    function validateForm() { return true; }
</script>
<jsp:include page="../footer.jsp"/>
