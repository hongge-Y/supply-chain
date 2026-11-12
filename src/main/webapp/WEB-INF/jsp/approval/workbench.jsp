<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="../header.jsp">
  <jsp:param name="title" value="审批工作台"/>
</jsp:include>

<div class="card">
  <div class="card-header">
    <h2>审批工作台</h2>
    <span class="badge">待审批：${pendingApprovalCount}</span>
  </div>

  <!-- Tab 导航 -->
  <div class="tab-nav">
    <a href="javascript:void(0);" class="tab-link active" data-tab="tab-all">
      全部 <c:if test="${pendingApprovalCount > 0}"><span class="badge-pending-count">${pendingApprovalCount}</span></c:if>
    </a>
    <a href="javascript:void(0);" class="tab-link" data-tab="tab-providers">
      供应商 <c:if test="${fn:length(appProviders) > 0}"><span class="badge-pending-count">${fn:length(appProviders)}</span></c:if>
    </a>
    <a href="javascript:void(0);" class="tab-link" data-tab="tab-bills">
      采购订单 <c:if test="${fn:length(appBills) > 0}"><span class="badge-pending-count">${fn:length(appBills)}</span></c:if>
    </a>
    <a href="javascript:void(0);" class="tab-link" data-tab="tab-products">
      商品 <c:if test="${fn:length(appProducts) > 0}"><span class="badge-pending-count">${fn:length(appProducts)}</span></c:if>
    </a>
    <a href="javascript:void(0);" class="tab-link" data-tab="tab-orders">
      销售订单 <c:if test="${fn:length(appOrders) > 0}"><span class="badge-pending-count">${fn:length(appOrders)}</span></c:if>
    </a>
    <a href="javascript:void(0);" class="tab-link" data-tab="tab-users">
      用户 <c:if test="${fn:length(appUsers) > 0}"><span class="badge-pending-count">${fn:length(appUsers)}</span></c:if>
    </a>
    <a href="javascript:void(0);" class="tab-link" data-tab="tab-news">
      新闻 <c:if test="${fn:length(appNews) > 0}"><span class="badge-pending-count">${fn:length(appNews)}</span></c:if>
    </a>
  </div>

  <!-- Tab：全部 -->
  <div class="tab-content active" id="tab-all">
    <c:choose>
      <c:when test="${empty pendingAll}">
        <div class="empty-state"><p>暂无待审批的单据</p></div>
      </c:when>
      <c:otherwise>
        <div class="table-responsive">
          <table>
            <tr><th>类型</th><th>申请人</th><th>操作</th><th>申请理由</th><th>操作</th></tr>
            <c:forEach items="${pendingAll}" var="item">
              <tr>
                <td><span class="status-badge info">${item.moduleName}</span></td>
                <td>${item.code}</td>
                <td>${item.name}</td>
                <td class="text-muted">${item.desc}</td>
                <td>
                  <div class="action-group">
                    <c:if test="${not empty item.detailJson}">
                      <button class="btn btn-sm btn-primary" onclick="viewDetail(this)" data-detail='${fn:escapeXml(item.detailJson)}'>详情</button>
                    </c:if>
                    <button class="btn btn-sm btn-success" onclick="doApprove('${item.module}',${item.id},'apply')">通过</button>
                    <button class="btn btn-sm btn-danger" onclick="doReject('${item.module}',${item.id},'apply')">驳回</button>
                  </div>
                </td>
              </tr>
            </c:forEach>
          </table>
        </div>
      </c:otherwise>
    </c:choose>
  </div>

  <!-- Tab：供应商 -->
  <div class="tab-content" id="tab-providers">
    <c:choose>
      <c:when test="${empty appProviders}"><div class="empty-state"><p>暂无待审批的供应商</p></div></c:when>
      <c:otherwise>
        <div class="table-responsive">
          <table>
            <tr><th>申请人</th><th>操作</th><th>理由</th><th>操作</th></tr>
            <c:forEach items="${appProviders}" var="a">
              <tr>
                <td>${a.applicantName}</td>
                <td>${a.actionName}</td>
                <td>${a.applyReason}</td>
                <td><div class="action-group">
                  <c:if test="${not empty a.detailJson}">
                    <button class="btn btn-sm btn-primary" onclick="viewDetail(this)" data-detail='${fn:escapeXml(a.detailJson)}'>详情</button>
                  </c:if>
                  <button class="btn btn-sm btn-success" onclick="doApprove('${a.module}',${a.id},'apply')">通过</button>
                  <button class="btn btn-sm btn-danger" onclick="doReject('${a.module}',${a.id},'apply')">驳回</button>
                </div></td>
              </tr>
            </c:forEach>
          </table>
        </div>
      </c:otherwise>
    </c:choose>
  </div>

  <!-- Tab：采购订单 -->
  <div class="tab-content" id="tab-bills">
    <c:choose>
      <c:when test="${empty appBills}"><div class="empty-state"><p>暂无待审批的采购订单</p></div></c:when>
      <c:otherwise>
        <div class="table-responsive">
          <table>
            <tr><th>申请人</th><th>操作</th><th>理由</th><th>操作</th></tr>
            <c:forEach items="${appBills}" var="a">
              <tr>
                <td>${a.applicantName}</td>
                <td>${a.actionName}</td>
                <td>${a.applyReason}</td>
                <td><div class="action-group">
                  <c:if test="${not empty a.detailJson}">
                    <button class="btn btn-sm btn-primary" onclick="viewDetail(this)" data-detail='${fn:escapeXml(a.detailJson)}'>详情</button>
                  </c:if>
                  <button class="btn btn-sm btn-success" onclick="doApprove('${a.module}',${a.id},'apply')">通过</button>
                  <button class="btn btn-sm btn-danger" onclick="doReject('${a.module}',${a.id},'apply')">驳回</button>
                </div></td>
              </tr>
            </c:forEach>
          </table>
        </div>
      </c:otherwise>
    </c:choose>
  </div>

  <!-- Tab：商品 -->
  <div class="tab-content" id="tab-products">
    <c:choose>
      <c:when test="${empty appProducts}"><div class="empty-state"><p>暂无待审批的商品</p></div></c:when>
      <c:otherwise>
        <div class="table-responsive">
          <table>
            <tr><th>申请人</th><th>操作</th><th>理由</th><th>操作</th></tr>
            <c:forEach items="${appProducts}" var="a">
              <tr>
                <td>${a.applicantName}</td>
                <td>${a.actionName}</td>
                <td>${a.applyReason}</td>
                <td><div class="action-group">
                  <c:if test="${not empty a.detailJson}">
                    <button class="btn btn-sm btn-primary" onclick="viewDetail(this)" data-detail='${fn:escapeXml(a.detailJson)}'>详情</button>
                  </c:if>
                  <button class="btn btn-sm btn-success" onclick="doApprove('${a.module}',${a.id},'apply')">通过</button>
                  <button class="btn btn-sm btn-danger" onclick="doReject('${a.module}',${a.id},'apply')">驳回</button>
                </div></td>
              </tr>
            </c:forEach>
          </table>
        </div>
      </c:otherwise>
    </c:choose>
  </div>

  <!-- Tab：销售订单 -->
  <div class="tab-content" id="tab-orders">
    <c:choose>
      <c:when test="${empty appOrders}"><div class="empty-state"><p>暂无待审批的销售订单</p></div></c:when>
      <c:otherwise>
        <div class="table-responsive">
          <table>
            <tr><th>申请人</th><th>操作</th><th>理由</th><th>操作</th></tr>
            <c:forEach items="${appOrders}" var="a">
              <tr>
                <td>${a.applicantName}</td>
                <td>${a.actionName}</td>
                <td>${a.applyReason}</td>
                <td><div class="action-group">
                  <c:if test="${not empty a.detailJson}">
                    <button class="btn btn-sm btn-primary" onclick="viewDetail(this)" data-detail='${fn:escapeXml(a.detailJson)}'>详情</button>
                  </c:if>
                  <button class="btn btn-sm btn-success" onclick="doApprove('${a.module}',${a.id},'apply')">通过</button>
                  <button class="btn btn-sm btn-danger" onclick="doReject('${a.module}',${a.id},'apply')">驳回</button>
                </div></td>
              </tr>
            </c:forEach>
          </table>
        </div>
      </c:otherwise>
    </c:choose>
  </div>

  <!-- Tab：用户 -->
  <div class="tab-content" id="tab-users">
    <c:choose>
      <c:when test="${empty appUsers}"><div class="empty-state"><p>暂无待审批的用户</p></div></c:when>
      <c:otherwise>
        <div class="table-responsive">
          <table>
            <tr><th>申请人</th><th>操作</th><th>理由</th><th>操作</th></tr>
            <c:forEach items="${appUsers}" var="a">
              <tr>
                <td>${a.applicantName}</td>
                <td>${a.actionName}</td>
                <td>${a.applyReason}</td>
                <td><div class="action-group">
                  <c:if test="${not empty a.detailJson}">
                    <button class="btn btn-sm btn-primary" onclick="viewDetail(this)" data-detail='${fn:escapeXml(a.detailJson)}'>详情</button>
                  </c:if>
                  <button class="btn btn-sm btn-success" onclick="doApprove('${a.module}',${a.id},'apply')">通过</button>
                  <button class="btn btn-sm btn-danger" onclick="doReject('${a.module}',${a.id},'apply')">驳回</button>
                </div></td>
              </tr>
            </c:forEach>
          </table>
        </div>
      </c:otherwise>
    </c:choose>
  </div>

  <!-- Tab：新闻 -->
  <div class="tab-content" id="tab-news">
    <c:choose>
      <c:when test="${empty appNews}"><div class="empty-state"><p>暂无待审批的新闻</p></div></c:when>
      <c:otherwise>
        <div class="table-responsive">
          <table>
            <tr><th>申请人</th><th>操作</th><th>理由</th><th>操作</th></tr>
            <c:forEach items="${appNews}" var="a">
              <tr>
                <td>${a.applicantName}</td>
                <td>${a.actionName}</td>
                <td>${a.applyReason}</td>
                <td><div class="action-group">
                  <c:if test="${not empty a.detailJson}">
                    <button class="btn btn-sm btn-primary" onclick="viewDetail(this)" data-detail='${fn:escapeXml(a.detailJson)}'>详情</button>
                  </c:if>
                  <button class="btn btn-sm btn-success" onclick="doApprove('${a.module}',${a.id},'apply')">通过</button>
                  <button class="btn btn-sm btn-danger" onclick="doReject('${a.module}',${a.id},'apply')">驳回</button>
                </div></td>
              </tr>
            </c:forEach>
          </table>
        </div>
      </c:otherwise>
    </c:choose>
  </div>

</div>

<!-- 详情弹窗 -->
<div id="detailModal" style="display:none;position:fixed;top:0;left:0;right:0;bottom:0;background:rgba(0,0,0,0.3);z-index:2000;align-items:center;justify-content:center;">
  <div style="background:#fff;border-radius:14px;padding:28px;width:600px;max-width:90vw;max-height:80vh;overflow-y:auto;box-shadow:0 10px 40px rgba(0,0,0,0.15);">
    <h3 style="font-size:16px;margin-bottom:16px;color:#0f172a;">申请详情</h3>
    <div id="detailContent" style="font-size:13px;color:#334155;line-height:1.8;white-space:pre-wrap;word-break:break-all;"></div>
    <div style="margin-top:16px;text-align:right;">
      <button onclick="document.getElementById('detailModal').style.display='none'" class="btn btn-outline">关闭</button>
    </div>
  </div>
</div>

<script>
  // Tab切换
  document.querySelectorAll('.tab-link').forEach(function(link) {
    link.addEventListener('click', function() {
      document.querySelectorAll('.tab-link, .tab-content').forEach(function(el) { el.classList.remove('active'); });
      this.classList.add('active');
      document.getElementById(this.getAttribute('data-tab')).classList.add('active');
    });
  });

  function doApprove(module, id, type) {
    var comment = prompt("审批意见（可选）：");
    if (comment !== null) {
      fetch('${pageContext.request.contextPath}/approval/workbench/approve', {
        method:'POST', headers:{'Content-Type':'application/x-www-form-urlencoded'},
        body:'module='+module+'&id='+id+'&type='+(type||'apply')+'&comment='+encodeURIComponent(comment)
      }).then(function(r){return r.text();}).then(function(d){
        if(d==='ok'){location.reload();}else{alert('操作失败：'+d);}
      });
    }
  }
  function doReject(module, id, type) {
    var comment = prompt("驳回原因：");
    if (comment !== null) {
      fetch('${pageContext.request.contextPath}/approval/workbench/reject', {
        method:'POST', headers:{'Content-Type':'application/x-www-form-urlencoded'},
        body:'module='+module+'&id='+id+'&type='+(type||'apply')+'&comment='+encodeURIComponent(comment)
      }).then(function(r){return r.text();}).then(function(d){
        if(d==='ok'){location.reload();}else{alert('操作失败：'+d);}
      });
    }
  }
  function viewDetail(btn) {
    var json = btn.getAttribute('data-detail');
    try {
      var data = JSON.parse(json);
      var html = '';
      for (var key in data) {
        html += '<b>' + key + '</b>: ' + (data[key] || '') + '<br>';
      }
      document.getElementById('detailContent').innerHTML = html;
    } catch(e) {
      document.getElementById('detailContent').innerText = json;
    }
    document.getElementById('detailModal').style.display = 'flex';
  }
</script>

<jsp:include page="../footer.jsp"/>
