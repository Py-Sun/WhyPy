// CodeMirror 초기화
function initCodeMirror() {
    const editor = CodeMirror.fromTextArea(document.getElementById("code"), {
        lineNumbers: true, // 라인 번호 표시 여부
        mode: "python",
        theme: "default",
    });

    // 초기화 버튼 클릭
    document.getElementById("reset-button").addEventListener("click", () => {
        editor.setValue("코드를 입력해주세요. ");
    });
    
    // 제출 버튼 클릭
    document.getElementById("submitAnswer").addEventListener("click", () =>{
        var userAnswer = editor.getValue();
        var questionId = $("#questionId").val();

        if (userAnswer === "코드를 입력해주세요. ") {
            userAnswer = ""; // 코드가 초기 값이면 공백으로 설정
        }

        // AJAX 요청 보내기
        $.ajax({
            url: "/questions/" + questionId,
            method: "POST",
            data: {
                userAnswer: userAnswer
            },
            success: function(data) {
                // AJAX 요청이 성공적으로 완료되었을 때 실행되는 콜백 함수
                window.location.href = data; // 리다이렉트 수행
            }
        });
    });
}

// 페이지 로드 시 CodeMirror 초기화 함수 호출
window.addEventListener("load", initCodeMirror);

// textarea 입력 이벤트 핸들러
function updateCodeMirror(value) {
    const editor = document.querySelector(".CodeMirror").CodeMirror;
    editor.setValue(value);
}
