// 通用删除确认提示
function confirmDelete(url) {
    if (confirm('确定要删除该记录吗？')) {
        window.location.href = url;
    }
}

// 表单提交前确认
function confirmSubmit(form, msg) {
    if (confirm(msg || '确定提交吗？')) {
        form.submit();
    }
}