boldCnt = 0;
codeCnt = 0;

document.addEventListener("DOMContentLoaded", function() {
    const contentsElement = document.getElementById("contents");
    contentsElement.addEventListener("input", function() {
        const boldText = this.value.replace(/\*\*(.*?)\*\*/g, '<span style="font-weight: bold;">$1</span>');
        this.innerHTML = boldText;
        //console.log(contentsElement.innerHTML);
    });
});

function bold() {
    boldCnt++;
    let button = document.getElementById('boldBtn');
    let contentElement = document.getElementById('contents');
    let contents = document.getElementById('contents').value;
    // if(boldCnt % 2 == 1) {
    //     button.style.backgroundColor = "blue";
    //     //element.style.fontWeight = "bold";
    // }
    // else {
    //     button.style.backgroundColor = "lightgrey";
    //     //element.style.fontWeight = "normal";
    // }
    document.getElementById('contents').value = contents + "**";
    let pos = contentElement.value.length;
    contentElement.selectionStart = pos;
    contentElement.selectionEnd = pos;
    contentElement.focus();
}

function code() {
    codeCnt++;
    let button2 = document.getElementById('codeBtn');
    let contentElement = document.getElementById('contents');
    let contents = document.getElementById('contents').value;
    // if(codeCnt % 2 == 1) {
    //     button2.style.backgroundColor = "blue";
    //     //element.style.fontWeight = "bold";
    // }
    // else {
    //     button2.style.backgroundColor = "lightgrey";
    //     //element.style.fontWeight = "normal";
    // }
    document.getElementById('contents').value = contents + "'''";
    let pos = contentElement.value.length;
    contentElement.selectionStart = pos;
    contentElement.selectionEnd = pos;
    contentElement.focus();
}