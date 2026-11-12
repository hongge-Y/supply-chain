<%@ page contentType="text/html;charset=UTF-8" %>
</main>
<script>
    function validateNumericFields(form){
        var f=form.querySelectorAll('[data-type="number"]');
        for(var i=0;i<f.length;i++){var v=f[i].value.trim();if(v!==''&&isNaN(parseFloat(v))){alert('请输入数字：'+(f[i].getAttribute('data-label')||f[i].name));f[i].focus();return false}}
        return true
    }
</script>
</body>
</html>
