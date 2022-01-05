const navBtn = document.querySelector('.nav_btn');
navBtn.addEventListener('mouseover', () => {
    navBtn.classList.add('active');

})
navBtn.addEventListener('mouseleave', () => {
    navBtn.classList.remove('active');
})