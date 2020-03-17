function getRangeDate() {
	var $ = layui.jquery;
	// 如果已有选择的日期
	var date = $("#dateInput").val();
	if (date) {
		return date;
	}

	var date = new Date();
	var nowDate = formatDate1(date);
	var nextOneMonth = $("#nextOneMonth").val();

	return nowDate + " - " + nextOneMonth
}

function getStartTime() {
	var time = getRangeDate();
	var startTime = time.substr(0, 10) + " 00:00:00";

	return startTime;
}

function getEndTime() {
	var time = getRangeDate();
	var endTime = time.substr(13) + " 23:59:59";

	return endTime;
}

function geCarMsg(row) {
	var type = row.carType;
	var name = type == 0 ? row.ownerName : row.custName;
	name = name?name: "";
	var phone = type == 0 ? row.ownerCellphone : row.cellphone;
	phone = phone ? phone :"";
	
	return name + " " + phone;
}