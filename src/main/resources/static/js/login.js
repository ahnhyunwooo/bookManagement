//아이디 팝업
function searchId() {
    // 만들 팝업창 width 크기의 1/2 만큼 보정값으로 빼주었음
    let popupX = (window.screen.width / 2) - (400 / 2);

    // 만들 팝업창 height 크기의 1/2 만큼 보정값으로 빼주었음
    let popupY= (window.screen.height / 2) - (270 / 2);
    window.open("login/idSearch","아이디찾기",'status=no, height=' + 270  + ', width=' + 400  + ', left='+ popupX + ', top='+ popupY);
}
//비밀번호 팝업
function searchPw() {
    // 만들 팝업창 width 크기의 1/2 만큼 보정값으로 빼주었음
    let popupX = (window.screen.width / 2) - (400 / 2);

    // 만들 팝업창 height 크기의 1/2 만큼 보정값으로 빼주었음
    let popupY= (window.screen.height / 2) - (270 / 2);
    window.open("login/pwSearch","비밀번호찾기",'status=no, height=' + 270  + ', width=' + 400  + ', left='+ popupX + ', top='+ popupY);
}
//아이디 찾기 - 연락처로 찾기
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
//아이디 찾기 - Email로 찾기
function searchIdByEmail() {
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

////////////////////////지영 개발중////////////////////
/**
 * 비밀번호 찾기 - 연락처로 찾기
 */
function searchPwByPhone() {
    let name = $("#pw_search_id").val();
    let phone =$("#pw_search_phone").val();
    let sendData = {"name":name, "phone":phone};
    if(name == "" || phone== "") {
        alert("정보를 입력해주세요.");
        return ;
    }
    $.ajax({
        url: "/login/pwSearch/phone",
        type: "post",
        data: JSON.stringify(sendData),
        dataType:'json',
        contentType: "application/json",
        async: false,
        success : function(data)
        {
            alert("인증번호가 전송되었습니다.");
        },
        error: function () {
            alert("실패");
        }
    });
}

function searchPwByPhoneCheck(){
    let phoneCertification = $("#phone_check").val();
    if(phoneCertification == phoneRealCertification) {
        alert("핸드폰 인증이 되었습니다");
        phoneCheckBtn = true;
        $("#member_join_phone").attr("readonly",true);
        $("#phone_check").attr("readonly", true);
    }else {
        alert("다시 확인 부탁드립니다.");
        phoneCheckBtn = false;
    }
}
//////////////////////////////////////////////////