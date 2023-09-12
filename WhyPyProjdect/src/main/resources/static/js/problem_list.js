// 숫자로 되어있는 카테고리 한글로 바꾸는 함수
document.addEventListener("DOMContentLoaded", function (){SwitchCategoryName();});

function SwitchCategoryName()
{
    const categoryCells = document.querySelectorAll(".q_category");
    categoryCells.forEach(cell => {
        const categoryText = cell.textContent;
        if (categoryText == "1") {
            cell.textContent = "연습문제";
        } else if (categoryText == "2") {
            cell.textContent = "직무문제";
        }
    });
}

// 맨 처음에는 모든 체크박스 선택 해제
document.addEventListener('DOMContentLoaded', function() {
    var statusCheckboxes = document.querySelectorAll('#statusChecklist input[type="checkbox"]');
    var allUnchecked = true;

    statusCheckboxes.forEach(function(checkbox) {
        if (checkbox.checked) {
            allUnchecked = false;
        }
    });

    if (allUnchecked) {
        statusCheckboxes.forEach(function(checkbox) {
            checkbox.checked = true;
        });
    }

    // 이전 선택한 필터 체크 유지
    var urlParams = new URLSearchParams(window.location.search);
    urlParams.forEach(function(value, key) {
        var inputElement = document.querySelector(`[name="${key}"][value="${value}"]`);
        if (inputElement) {
            inputElement.checked = true;
        }
    });
});


document.addEventListener('DOMContentLoaded', function() {
    // 이전 선택한 필터를 복원하기 위한 URL의 쿼리 매개변수
    var urlParams = new URLSearchParams(window.location.search);

    // 초기에는 상태 체크박스 선택 해제
    var solvedCheckboxes = document.querySelectorAll('input[name="status1"]');
    solvedCheckboxes.forEach(function(checkbox) {
        checkbox.checked = false;
    });

    // 체크박스 상태 복원
    urlParams.forEach(function(value, key) {
        if (key === "status1") {
            solvedCheckboxes.forEach(function(checkbox) {
                if (checkbox.value === value) {
                    checkbox.checked = true;
                }
            });
        }
    });
});



function applyFilters() {
    var selectedType = [];
    var selectedStatus = [];
    var selectedLevel = [];

    // 문제 종류 필터 확인
    var typeCheckboxes = document.querySelectorAll('input[name="type1"]');
    typeCheckboxes.forEach(function(checkbox) {
        if (checkbox.checked) {
            selectedType.push(checkbox.value);
        }
    });

    // member 정보 가져오기
    var memberInput = document.getElementById("member");
    var member = memberInput.value;
    if (member != null) {
        var statusCheckboxes = document.querySelectorAll('input[name="status1"]');
        statusCheckboxes.forEach(function(checkbox) {
            if (checkbox.checked) {
                selectedStatus.push(checkbox.value);
            }
        });
    }

    // 레벨 필터 확인
    var levelCheckboxes = document.querySelectorAll('input[name="level1"]');
    levelCheckboxes.forEach(function(checkbox) {
        if (checkbox.checked) {
            selectedLevel.push(checkbox.value);
        }
    });

    // 선택된 필터를 사용하여 적절한 작업 수행
    // 예: 선택된 필터를 URL 매개변수로 추가하고 페이지 다시로드
    var url = "/questions";
    var params = [];

    if (selectedType.length > 0) {
        params.push("type1=" + selectedType.join(","));
    }

    if (selectedStatus.length > 0) {
        params.push("status1=" + selectedStatus.join(","));
    }

    if (selectedLevel.length > 0) {
        params.push("level1=" + selectedLevel.join(","));
    }

    if (params.length > 0) {
        url += "?" + params.join("&");
    }

    window.location.href = url;
}

function opensetting1() {
    if (document.getElementById('check-list').style.display === 'block') {
        document.getElementById('check-list').style.display = 'none';
    } else {
        document.getElementById('check-list').style.display = 'block';
    }
}
function opensetting2() {
    if (document.getElementById('status-list').style.display === 'block') {
        document.getElementById('status-list').style.display = 'none';
    } else {
        document.getElementById('status-list').style.display = 'block';
    }
}
function opensetting3() {
    if (document.getElementById('level-list').style.display === 'block') {
        document.getElementById('level-list').style.display = 'none';
    } else {
        document.getElementById('level-list').style.display = 'block';
    }
}