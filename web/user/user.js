function logout() {
	ajax("POST", "/logout", "");
	return ;
}

function setUser() {
	var passWord = document.getElementById("passWord").value;
	var newPassWord = document.getElementById("newPassWord").value;
	var newPassWordAgain = document.getElementById("newPassWordAgain").value;

	if (passWord == "") {
		alert("Now Password is empty");
		return ;
	}

	if (newPassWord != newPassWordAgain) {
		alert("New Passwords are diffirent");
		return ;
	}

	ajax("POST", "/user", "passWord=" + encodeURIComponent(passWord) + "&newUserName=" + encodeURIComponent(document.getElementById("newUserName").value) + "&newPassWord=" + encodeURIComponent(newPassWord));
	return ;
}

function deluser() {
	ajax("POST", "/deluser", "");
}