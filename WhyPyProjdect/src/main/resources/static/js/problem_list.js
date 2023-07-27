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

