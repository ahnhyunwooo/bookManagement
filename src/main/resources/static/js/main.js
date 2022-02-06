// 변수 지정
const label = document.querySelectorAll('.nav_label');
const book1 = document.querySelector('.book_info_items1');
const book2 = document.querySelector('.book_info_items2');
const book3 = document.querySelector('.book_info_items3');
const book4 = document.querySelector('.book_info_items4');

//마우스 over, 카테고리 나타남
label.forEach((btn) => {
    btn.addEventListener('mouseover', (e) => {
        const filter = btn.dataset.filter;
        if (filter === book1.dataset.type || filter === book2.dataset.type
            || filter === book3.dataset.type || filter === book4.dataset.type) {
            btn.classList.add('selected');
            mouseLeave(btn);
        }
    });
});

//로그인, 마우스 오버/아웃
const login = document.querySelector('.login_link');
mouseOver(login);
mouseLeave(login);

//회원가입, 마우스 오버/아웃
const join = document.querySelector('.join_link');
mouseOver(join);
mouseLeave(join);

//토글 버튼 클릭, 메뉴 나타남
const toggleBtn = document.querySelector('.toggle-btn');
const menu = document.querySelector('.nav_container');

toggleBtn.addEventListener('click', () => {
    menu.classList.toggle('open');
});

//공통 함수
function mouseOver(selector) {
    selector.addEventListener('mouseover', () => {
        selector.classList.add('selected');
    })
};

function mouseLeave(selector) {
    selector.addEventListener('mouseleave', () => {
        selector.classList.remove('selected');
    })
};
