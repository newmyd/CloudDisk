var xhr = new XMLHttpRequest();
xhr.open("POST", "/check");
xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
xhr.send("");
xhr.onreadystatechange = function() {
	if (xhr.readyState == 4 && xhr.status == 200) {
		if (xhr.responseText != "") {
			document.getElementById("user").innerHTML = xhr.responseText;
			document.getElementById("user").href = "../user/user.html";
		} else {
			window.location.href = "../login/login.html";
		}
	}
} ;