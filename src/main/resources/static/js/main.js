const label = document.querySelector('.nav_label');
mouseOver(label);
mouseLeave(label);

const btn = document.querySelector('.nav_btn');
mouseOver(btn);
mouseLeave(btn);

const login = document.querySelector('.login_link');
mouseOver(login);
mouseLeave(login);

const join = document.querySelector('.join_link');
mouseOver(join);
mouseLeave(join);

//토글 버튼 클릭 시 나타나게
const toggleBtn = document.querySelector('.toggle-btn');
const menu = document.querySelector('.nav_container');

toggleBtn.addEventListener('click', () => {
    menu.classList.toggle('open');
});



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
