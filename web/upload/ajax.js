function ajaxUpload(dir, id, file) {
	var xhr = new XMLHttpRequest(); //第一步  
	//新建一个FormData对象  
	var formData = new FormData(); //++++++++++  
	//追加文件数据  
	formData.append('dir', dir);
	formData.append('idx', id);
	formData.append('file', file);
	//post方式  
	xhr.open('POST', '/upload'); //第二步骤  
	//发送请求
	//ajax返回

	xhr.send(formData); //第三步骤
	
	xhr.onreadystatechange = function() { //第四步
		if (xhr.readyState == 4 && xhr.status == 200) {
			if (xhr.responseText == "Success") {
				++idx[now];
				uploadFile();
			} else {
				alert(xhr.responseText + " Error");
			}
		}
	};
	
	xhr.onerror = function() {
		sc[now] = true;
		getEleId("sta" + now).innerHTML = "Stop";
		uploadId();
	} ;

}




function ajax(url, str, opt) {
	var xhr = new XMLHttpRequest();
	xhr.open("POST", url);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
	xhr.send(str);
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			if (!opt) {
				if (xhr.responseText == "Database")
					alert("Database Error");
				else {
					dir[now] = xhr.responseText;
					uploadFile();
				}
			} else {
				if (xhr.responseText == "Success") {
					idx[now] = len[now] + 1;
					getEleId("sta" + now).innerHTML = "Finish";
					uploadId();
				}
			}
		}
	} ;
	
	xhr.onerror = function() {
		sc[now] = true;
		getEleId("sta" + now).innerHTML = "Stop";
		uploadId();
	} ;
}

function uploadFile() {
	if (len[now] == -1 || idx[now] > len[now] || sc[now]) {
		uploadId();
		return ;
	}
	if (idx[now] == len[now]) {
		merge();
		return ;
	}
	ajaxUpload(dir[now], idx[now] + 1, res[now].slice(idx[now] * sz, Math.min(idx[now] * sz + sz, res[now].size)));
	return ;
}

function merge() {
	if (len[now] == -1 || idx[now] > len[now] || sc[now]) {
		uploadId();
		return ;
	}
	res[now]
	ajax("/merge", "dir=" + dir[now] + "&idx=" + len[now] + "&name=" + encodeURIComponent(res[now].name), true);
	return ;
}

function uploadId() {
	flag = false;
	var flg = true;
	for (now = 0; now < len.length; ++now) {
		if (len[now] != -1 && idx[now] <= len[now]) {
			flg = false;
			if (!sc[now]) {
				break;
			}
		}
	}

	if (flg) {

		clearInterval(siv);
		st = false;
		flag = false;
		tb.innerHTML = "";
		res = [];
		idx = [];
		len = [];
		sc = [];
		dir = [];
		return;
	}

	if (now >= len.length) {
		flag = true;
		return ;
	}

	if (dir[now] > 0)
		uploadFile();
	else
		ajax("/uploadid", "", false);
	return ;
}