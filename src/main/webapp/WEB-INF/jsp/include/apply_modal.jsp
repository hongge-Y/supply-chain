<%--
  审批申请模态框公共片段
  在每个列表 JSP 中通过以下方式引入：
    <jsp:include page="../include/apply_modal.jsp"/>
  不需要额外参数，自动使用 pageContext.request.contextPath
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 审批申请模态框 -->
<div id="applyModal" style="display:none;">
    <div class="modal-content">
        <h3>申请操作</h3>
        <div class="info-row">
            <span class="info-label">模块：</span>
            <span class="info-value" id="applyModule"></span>
        </div>
        <div class="info-row">
            <span class="info-label">操作：</span>
            <span class="info-value" id="applyAction"></span>
        </div>
        <label for="applyReason">申请理由：</label>
        <textarea id="applyReason" rows="3" placeholder="请详细描述申请理由..."></textarea>
        <div class="modal-footer-btns">
            <button onclick="closeApplyModal()" class="btn btn-outline">取消</button>
            <button onclick="submitApply()" class="btn btn-primary">提交申请</button>
        </div>
    </div>
</div>

<script>
    var currentApply = {module:'', action:'', recordId:null};

    function openApplyModal(module, action, recordId) {
        currentApply.module = module;
        currentApply.action = action;
        currentApply.recordId = recordId || null;
        document.getElementById('applyModule').innerText = module;
        document.getElementById('applyAction').innerText = action;
        document.getElementById('applyReason').value = '';
        document.getElementById('applyModal').style.display = 'flex';
    }
    function closeApplyModal() {
        document.getElementById('applyModal').style.display = 'none';
    }
    function submitApply() {
        var reason = document.getElementById('applyReason').value.trim();
        if (!reason) { alert('请输入申请理由'); return; }
        var params = 'module=' + currentApply.module + '&action=' + currentApply.action;
        if (currentApply.recordId) params += '&recordId=' + currentApply.recordId;
        params += '&reason=' + encodeURIComponent(reason);
        fetch('${pageContext.request.contextPath}/approval/apply', {
            method: 'POST',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            body: params
        }).then(res => res.text()).then(data => {
            if (data === 'success') {
                alert('申请已提交，请等待审批');
                closeApplyModal();
                location.reload();
            } else { alert('提交失败'); }
        });
    }
</script>
