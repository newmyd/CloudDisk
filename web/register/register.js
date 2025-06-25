function register() {
	var userName = document.getElementById('userName').value;
	var passWord = document.getElementById('passWord').value;
	var passWordAgain = document.getElementById('passWordAgain').value;

	if (userName == "") {
		alert("Username is empty");
		return ;
	}


	if (passWord == "") {
		alert("Password is empty");
		return ;
	}
	
	if (passWord != passWordAgain) {
		alert("PassWords are different");
		return ;
	}
	
	var str = 'userName=' + encodeURIComponent(userName) + '&passWord=' + encodeURIComponent(passWord);
	ajax('POST', '/register', str);
	return ;
}