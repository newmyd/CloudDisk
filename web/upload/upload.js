var fileInput;
var fileBtn;
var box;
var res = [];
var tb;
var sz = 5 * 1024 * 1024;
var now;
var idx = [];
var len = [];
var sc = [];
var dir = [];
var siv;
var st;
var flag = false;


window.onload = function() {
	fileInput = document.getElementById("file");
	fileBtn = document.getElementById('fileBtn');
	box = document.getElementById('fileBtn');
	tb = document.getElementById("tb");
	disDrag();
	drag();
	return;
}

function siz(sz) {
	
	var dw = "B";
		
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
	
	return sz.toFixed(3) + dw;
}

function disDrag() {
	document.addEventListener("drop", function(e) { //拖离
		e.preventDefault();
	})

	document.addEventListener("dragleave", function(e) { //拖后放   
		e.preventDefault();
	})

	document.addEventListener("dragenter", function(e) { //拖进  
		e.preventDefault();
	})

	document.addEventListener("dragover", function(e) { //拖来拖去    
		e.preventDefault();
	})

	return;
}



function drag() {
	box.addEventListener("drop", function(e) {
		var fileList = e.dataTransfer.files; //获取文件对象    
		if (fileList.length == 0) {
			return false;
		}
		fileInput.value = '';
		var i;
		for (i = 0; i < fileList.length; ++i) {
			res.push(fileList[i]);
			showlis();
		}
	}, false);
	return;
}



function chooseFile() {
	fileInput.click();
	return;
}


function showFile() {
	if (fileInput.value != "") {
		for (i = 0; i < $("#file")[0].files.length; ++i) {
			res.push($("#file")[0].files[i]);
			showlis();
		}
		fileInput.value = "";
	}
	return;
}




function getEleId(str) {
	return document.getElementById(str);
}

function show() {
	for (var i = 0; i < res.length; ++i) {
		if (len[i] == -1)
			continue;
		if (len[i] == 0) {
			getEleId("ud" + i).innerHTML = "0B";
			getEleId("pe" + i).innerHTML = "0.00%";
		} else if (idx[i] > len[i]) {
			getEleId("ud" + i).innerHTML = siz(res[i].size);
			getEleId("pe" + i).innerHTML = "100.00%";
		} else {
			getEleId("ud" + i).innerHTML = siz(Math.min(idx[i] * sz, res[i].size));
			getEleId("pe" + i).innerHTML = (idx[i] * 98 / len[i]).toFixed(2) + "%";
		}
	}
}


function showlis() {
	idx.push(0);
	dir.push(0);
	sc.push(false);
	var j = idx.length - 1;
	len.push(Math.ceil(res[j].size / sz));
	tb.innerHTML = tb.innerHTML
		+ "<tr id='tr" + j + "'><td><input id='ck" + j + "' type='checkbox' class='checkbox' /></td><td>"
		+ res[j].name + "</td><td id='ud" + j + "'>0B</td><td>" + siz(res[j].size) + "</td><td id='pe" + j
		+ "'>0.00%</td><td id='sta" + j + "'>Upload</td></tr>";
}

function getCk(id) {
	return document.getElementById("ck" + id).checked;
}

function setCk(id, val) {
	return document.getElementById("ck" + id).checked = val;
}

function start() {
	if (st)
		return false;
	st = true;
	for (var i = 0; i < len.length; ++i)
		if (len[i] != -1 && !sc[i])
			getEleId("sta" + i).innerHTML = "Upload";
	siv = setInterval("show();", 100);
	uploadId();
	return false;
}



function allch() {
	for (var i = 0; i < len.length; ++i)
		setCk(i, true);
}

function can() {
	for (var i = 0; i < len.length; ++i)
		setCk(i, false);
}

function rev() {
	for (var i = 0; i < len.length; ++i)
		setCk(i, getCk(i) ^ true);
}

function del() {
	for (var i = 0; i < len.length; ++i) {
		if (len[i] != -1 && getCk(i)) {
			len[i] = -1;
			$("#tr" + i).remove();
		}
	}
}

function stop() {
	
	for (var i = 0; i < len.length; ++i) {
		if (len[i] != -1 && idx[i] <= len[i] && getCk(i)) {
			sc[i] = true;
			getEleId("sta" + i).innerHTML = "Stop";
			setCk(i, false);
		}
	}
}

function contin() {
	
	for (var i = 0; i < len.length; ++i) {
		if (len[i] != -1 && idx[i] <= len[i] && getCk(i)) {
			sc[i] = false;
			getEleId("sta" + i).innerHTML = "Upload";
			setCk(i, false);
		}
	}
	if (flag)
		uploadId();
}