function check() {
	if(document.getElementById('buildingList').value != "")
		document.getElementById('floorList').disabled = false;
	else
		document.getElementById('floorList').disabled = true;
}

