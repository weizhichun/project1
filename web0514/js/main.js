// 获取用户信息
function getUserInfo() {
    fetch('http://localhost:8080/user/info')
        .then(response => response.json())
        .then(data => {
            document.getElementById('result').innerText = data.msg + "：" + data.data;
        })
        .catch(error => {
            document.getElementById('result').innerText = "请求失败：" + error;
        });
}