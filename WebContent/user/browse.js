function check() {
	if(document.getElementById('buildingList').value != "")
		document.getElementById('floorList').disabled = false;
	else
		document.getElementById('floorList').disabled = true;
}
/* @author Brian Olaogun   Used to show each drop down after the previous is selected */
