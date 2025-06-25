function ajax(req, link, str) {
	var xhr = new XMLHttpRequest(); //第一步  
	//新建一个FormData对象  
	// var formData = new FormData(); //++++++++++  
	// //追加文件数据  
	// formData.append('text', txt.value);
	// formData.append('password', $("#password").val());
	//post方式  
	
	xhr.open(req, link); //第二步骤  

	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
	
	xhr.send(str);
	//ajax返回  
	xhr.onreadystatechange = function() { //第四步
		if (xhr.readyState == 4 && xhr.status == 200) {
			if (xhr.responseText == "Success")
				window.location.href="../index.html";
			else if (xhr.responseText == "Database")
				alert("Connect database error");
				// document.getElementByID("databaseError").style.display="";
			else if (xhr.responseText == "Username")
				alert("Username is wrong");
				// document.getElementByID("userNameError").style.display="";
			else if (xhr.responseText == "Password")
				alert("Password is wrong");
				// document.getElementByID("passWordError").style.display="";
			else if (xhr.responseText == "Logined") {
				alert("You logined");
				window.location.href="../index.html";
			}
		}
	} ;

	return ;
}