// CodeMirror 초기화
function initCodeMirror() {
    const editor = CodeMirror.fromTextArea(document.getElementById("code"), {
        lineNumbers: true,
        mode: "python",
        theme: "default",
    });

    // 초기화 버튼 클릭
    document.getElementById("reset-button").addEventListener("click", () => {
        editor.setValue(" 답안을 작성하세요. ");
    });
}

// 페이지 로드 시 CodeMirror 초기화 함수 호출
window.addEventListener("load", initCodeMirror);

// textarea 입력 이벤트 핸들러
function updateCodeMirror(value) {
    const editor = document.querySelector(".CodeMirror").CodeMirror;
    editor.setValue(value);
}