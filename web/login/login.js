function login() {
	var userName = document.getElementById('userName').value;

	if (userName == "") {
		alert("Username is empty");
		return ;
	}

	var passWord = document.getElementById('passWord').value;

	if (passWord == "") {
		alert("Password is empty");
		return ;
	}
	
	var str = 'userName=' + encodeURIComponent(userName) + '&passWord=' + encodeURIComponent(passWord);
	ajax('POST', '/login', str);
	return ;
}