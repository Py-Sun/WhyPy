function redirectToLectures() {
    window.location.href = "/lectures";
}

function redirectToQuestions() {
    window.location.href = "/questions";
}

// 컨트롤러에서 넘겨준 리스트
const unsolvedQuestions = [
    "질문 1",
    "질문 2",
    "질문 3",
    // ... 더 많은 질문들 ...
];

const unsolvedQuestionsInput = document.getElementById('unsolvedQuestions');
const unsolvedQuestions2 = JSON.parse(unsolvedQuestionsInput.value);
const questionContainer = document.getElementById('question-container');
let currentIndex = 0;

function showNextQuestion() {
    if (currentIndex < unsolvedQuestions2.length) {
        questionContainer.textContent = unsolvedQuestions2[currentIndex];
        currentIndex++;
    } else {
        questionContainer.textContent = "더 이상 질문이 없습니다.";
    }
}

// 페이지 로드 시 첫 번째 질문 보여주기
showNextQuestion();

// 다음 질문 클릭 시 다음 질문 보여주기