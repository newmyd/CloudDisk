var page = 1;
var siz;
var obj;
var len;
var sot = "time DESC";
var file = document.getElementById("file");
var schInput = document.getElementById("searchInput");
var sch = "";
var maxn = 100000;
var maxlen = 100;
var f = new Array(maxlen);
var mp = new Array(maxn);
var ifr = [];
var ifrlen;

ajax("/viewshare", "sort=" + sot + "&page=1&name=", false);

function ajax(url, str, opt) {
	var xhr = new XMLHttpRequest();
	xhr.open("POST", url);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
	xhr.send(str);
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			if (opt) {
				ajax("/viewshare",  "sort=" + sot + "&page=" + page + "&name=" + encodeURIComponent(sch), false);
			} else if (xhr.responseText != "") {
				show(xhr.responseText);
			}
		}
	} ;
}

function update() {
	document.getElementById("nu").src="../main/img/u.svg";
	document.getElementById("tu").src="../main/img/u.svg";
	document.getElementById("uu").src="../main/img/u.svg";
	document.getElementById("su").src="../main/img/u.svg";
	document.getElementById("nd").src="../main/img/d.svg";
	document.getElementById("td").src="../main/img/d.svg";
	document.getElementById("ud").src="../main/img/d.svg";
	document.getElementById("sd").src="../main/img/d.svg";
}

function st(str, id, dir) {
	sot = str;
	page = 1;
	update();
	document.getElementById(id).src="../main/img/" + dir + ".svg";
	ajax("/viewshare",  "sort=" + sot + "&page=1&name=" + encodeURIComponent(sch), false);
}

function searchName() {
	page = 1;
	sch = schInput.value;
	ajax("/viewshare",  "sort=" + sot + "&page=1&name=" + encodeURIComponent(sch), false);
	return false;
}

function chck(s) {
	mp[obj[s].id] = f[s].checked;
}

function cln() {
	mp = new Array(maxn);
	for (var i = 1; i < len; ++i)
		f[i].checked = false;
}

function show(json) {
	var sz, dw;
	obj = JSON.parse(json);
	siz = obj[0];
	if (siz == 0)
		document.getElementById("nowPage").innerHTML = "0 / 0";
	else
		document.getElementById("nowPage").innerHTML = page + " / " + siz;
	len = 0;
	for (i in obj)
		++len;
	file.innerHTML = "";
	for (i = 1; i < len; ++i) {
		
		sz = obj[i].siz;
		dw = "B";
		
		if (sz >= 1024) {
			sz /= 1024;
			dw = "K";
		}
		
		if (sz >= 1024) {
			sz /= 1024;
			dw = "M";
		}
		
		if (sz >= 1024) {
			sz /= 1024;
			dw = "G";
		}
		
		if (sz >= 1024) {
			sz /= 1024;
			dw = "T";
		}
		
		if (sz >= 1024) {
			sz /= 1024;
			dw = "P";
		}
		
		file.innerHTML = file.innerHTML + "<tr><td><input id='file" + i + "' type='checkbox' class='checkbox' onclick='chck(" + i + ");' /></td><td>" + obj[i].name + "</td><td>" + sz.toFixed(3) + dw + "</td><td>" + obj[i].src + "</td><td>" + obj[i].time + "</td></tr>";
	}
	for (i = 1; i < len; ++i) {
		f[i] = document.getElementById("file" + i);
		f[i].checked = mp[obj[i].id];
	}
}

function allch() {
	for (var i = 1; i < len; ++i) {
		f[i].checked = true;
		mp[obj[i].id] = true;
	}
}

function can() {
	for (var i = 1; i < len; ++i) {
		f[i].checked = false;
		mp[obj[i].id] = false;
	}
}

function rev() {
	for (var i = 1; i < len; ++i) {
		f[i].checked ^= true;
		mp[obj[i].id] = f[i].checked;
	}
}

function jsn() {
	
	var json = "[";
	for (var i = 1; i <= maxn; ++i) {
		if (mp[i]) {
			json = json + i + ",";
		}
	}
	json = json.substr(0, json.length - 1) + "]";
	cln();
	return json;
}

function ac() {
	ajax("/acshare", "json=" + jsn(), true);
}

function del() {
	ajax("/delshare", "json=" + jsn(), true);
}

function rename() {
	if (document.getElementById("nameInput").value == "")
		return ;
	ajax("/renameshare", "name=" + encodeURIComponent(document.getElementById("nameInput").value) + "&json=" + jsn(), true);
	document.getElementById("nameInput").value = "";
	return ;
}

function share() {
	if (document.getElementById("userInput").value == "")
		return ;
	ajax("/shareshare", "user=" + encodeURIComponent(document.getElementById("userInput").value) + "&json=" + jsn(), true);
	document.getElementById("userInput").value = "";
	return ;
}

function clr() {
	for (var i = 1; i <= ifrlen; ++i)
		ifr[i].remove();
	return ;
}

function down() {
	ifr = new Array(maxlen);
	ifrlen = 0;
	for (var i = 1; i <= maxn; ++i) {
		if (mp[i] == true) {
			if (!!window.ActiveXObject || 'ActiveXObject' in window) {
				window.open("/downloadshare?id=" + i, "Download");
			} else {
				ifr[++ifrlen] = document.createElement('iframe');
				ifr[ifrlen].style.display = 'none';
				ifr[ifrlen].style.height = 0;
				ifr[ifrlen].src = "/downloadshare?id=" + i;
				document.body.appendChild(ifr[ifrlen]);
			}
		}
	}
	setTimeout("clr()", 1000);
	cln();
	return ;
}

function fst() {
	page = 1;
	ajax("/viewshare",  "sort=" + sot + "&page=1&name=" + encodeURIComponent(sch), false);
}

function lst() {
	page = siz;
	ajax("/viewshare",  "sort=" + sot + "&page=" + page + "&name=" + encodeURIComponent(sch), false);
}

function pre() {
	if (page == 1)
		return ;
	page = page - 1;
	ajax("/viewshare",  "sort=" + sot + "&page=" + page + "&name=" + encodeURIComponent(sch), false);
}

function nxt() {
	if (page == siz)
		return ;
	page = page + 1;
	ajax("/viewshare",  "sort=" + sot + "&page=" + page + "&name=" + encodeURIComponent(sch), false);
}

function go() {
	tmp = document.getElementById("pag").value;
	document.getElementById("pag").value = "";
	if (1 <= tmp && tmp <= siz) {
		page = tmp;
		ajax("/viewshare",  "sort=" + sot + "&page=" + page + "&name=" + encodeURIComponent(sch), false);
	}
}