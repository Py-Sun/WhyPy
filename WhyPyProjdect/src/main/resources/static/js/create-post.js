boldCnt = 0;

function a() {
    boldCnt++;
    console.log(boldCnt);
}

function bold() {
    boldCnt++;
    let button = document.getElementById('boldBtn');
    let contents=document.getElementById('contents').value;
    if(boldCnt % 2 == 1) {
        button.style.backgroundColor = "blue"
        document.getElementById('contents').value = contents + "<b>";
    }
    else {
        button.style.backgroundColor = "lightgrey"
        document.getElementById('contents').value = contents + "</b>";
    }
}