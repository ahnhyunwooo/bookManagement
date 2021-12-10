//아이디 찾기
function searchId() {
    // 만들 팝업창 width 크기의 1/2 만큼 보정값으로 빼주었음
    let popupX = (window.screen.width / 2) - (400 / 2);

    // 만들 팝업창 height 크기의 1/2 만큼 보정값으로 빼주었음
    let popupY= (window.screen.height / 2) - (300 / 2);
    window.open("login/idSearch","아이디찾기",'status=no, height=' + 270  + ', width=' + 400  + ', left='+ popupX + ', top='+ popupY);
}
//핸드폰으로 아이디 찾기
function searchIdByPhone() {
    let name = $("#id_search_name").val();
    let phone =$("#id_search_phone").val();
    let sendData = {"name":name, "phone":phone};
    if(name == "" || phone== "") {
        alert("정보를 입력해주세요.");
        return ;
    }
    $.ajax({
        url: "/login/idSearch/phone",
        type: "post",
        data: JSON.stringify(sendData),
        dataType:'json',
        contentType: "application/json",
        async: false,
        success: function(data){
            alert("문자로 아이디 전송했습니다.");
            $("#id_search_error").css("display","none");
            window.close();
        },
        error : function(XMLHttpRequest, textStatus, errorThrown){
            $("#id_search_error").css("display","block");
        }
    });
}
//email로 아이디 찾기
function searchIdByPhone() {
    let name = $("#id_search_name").val();
    let email =$("#id_search_email").val();
    let sendData = {"name":name, "email":email};
    if(name == "" || email== "") {
        alert("정보를 입력해주세요.");
        return ;
    }
    $.ajax({
        url: "/login/idSearch/email",
        type: "post",
        data: JSON.stringify(sendData),
        dataType:'json',
        contentType: "application/json",
        async: false,
        success: function(data){
            alert("이메일로 아이디 전송했습니다.");
            $("#id_search_error").css("display","none");
            window.close();
        },
        error : function(XMLHttpRequest, textStatus, errorThrown){
            $("#id_search_error").css("display","block");
        }
    });
}