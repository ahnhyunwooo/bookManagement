$(document).ready(function() {
    /**
     * 엔터키 입력으로 로그인하기
     */
    $("#pw_box").keydown(function(key) {
        //13번은 엔터키
        if (key.keyCode == 13) {
            document.getElementById('form').submit();
        }
    });
    $("#phone_search_error").css("display","none");
});

/**
 * 아이디 찾기 팝업 (새창)
 */
function searchId() {
    // 만들 팝업창 width 크기의 1/2 만큼 보정값으로 빼주었음
    let popupX = (window.screen.width / 2) - (400 / 2);

    // 만들 팝업창 height 크기의 1/2 만큼 보정값으로 빼주었음
    let popupY= (window.screen.height / 2) - (270 / 2);
    window.open("login/idSearch","아이디찾기",'status=no, height=' + 270  + ', width=' + 400  + ', left='+ popupX + ', top='+ popupY);
}

/**
 * 비밀번호 찾기 팝업 (새창)
 */
function searchPw() {
    // 만들 팝업창 width 크기의 1/2 만큼 보정값으로 빼주었음
    let popupX = (window.screen.width / 2) - (400 / 2);

    // 만들 팝업창 height 크기의 1/2 만큼 보정값으로 빼주었음
    let popupY= (window.screen.height / 2) - (270 / 2);
    window.open("login/pwSearch","비밀번호찾기",'status=no, height=' + 270  + ', width=' + 400  + ', left='+ popupX + ', top='+ popupY);
}

/**
 * 아이디 찾기 - 연락처로 찾기
 */
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

/**
 * 아이디 찾기 - Email로 찾기
 */
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

/**
 * 비밀번호 찾기 - 연락처로 찾기
 */
let phoneRealCertification;

function searchPwByPhone() {
    let id = $("#pw_search_id").val();
    let phone =$("#pw_search_phone").val();
    let phoneCheck = $("#pw_search_check").val();

    console.log("id : "+id);
    console.log("phone : "+phone);
    console.log("phoneCheck : "+phoneCheck);
    console.log("phoneRealCertification : "+phoneRealCertification);

    let sendData = {"id":id, "phone":phone};
    if(name == "" || phone== "") {
        alert("정보를 입력해주세요.");
        return ;
    }
    if(phoneCheck == "" || phoneCheck != phoneRealCertification) {
        alert("핸드폰 인증을 진행해주세요.");
        return;
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
            $("#phone_search_error").css("display","none");
            location.href = "/login/pwSearch/newPw/"+id;
        },
        error: function () {
            $("#phone_search_error").css("display","block");
        }
    });
}

/**
 * 비밀번호 찾기 - 연락처로 찾기 - 핸드폰 인증하기
 */
function sendPhone() {
    const phoneNumber = $("#pw_search_phone").val();
    let sendData ={"phoneNumber":phoneNumber};

    //핸드폰번호 유효성 체크
    if(!phoneNumberCheck(phoneNumber)){
        alert('휴대폰번호를 정확히 입력해주세요.');
        return;
    }

    $.ajax({
        url: "/login/pwSearch/phone/phoneMessage",
        type: "post",
        data: JSON.stringify(sendData),
        dataType:'json',
        contentType: "application/json",
        async: false,
        success : function(data)
        {
            alert("인증번호가 전송되었습니다.");
            phoneRealCertification = data;
        },
        error: function () {
            alert("실패");
        }
    });
}

/**
 * 비밀번호 찾기 - 연락처로 찾기 - 핸드폰 인증번호 확인
 */
function phoneCheck(){
    let phoneCertification = $("#pw_search_check").val();

    if(phoneCertification == phoneRealCertification) {
        alert("핸드폰 인증이 되었습니다");
        phoneCheckBtn = true;
        $("#pw_search_phone").attr("readonly",true);
        $("#pw_search_check").attr("readonly", true);
    }else {
        alert("다시 확인 부탁드립니다.");
        phoneCheckBtn = false;
    }

}


/**
 * 비밀번호 찾기 - Email로 찾기
 */
function searchPwByEmail() {
    let id = $("#pw_search_id").val();
    let email = $("#pw_search_phone").val();
    let emailCheck = $("#pw_search_check").val();

    let sendData = {"id":id, "email":email};
    if(id == "" || email== "") {
        alert("정보를 입력해주세요.");
        return ;
    }
    $.ajax({
        url: "/login/pwSearch/email",
        type: "post",
        data: JSON.stringify(sendData),
        dataType:'json',
        contentType: "application/json",
        async: false,
        success: function(data){
            location.href = "/login/pwSearch/newPw";
            //window.location.href = "newPw.html";
        },
        error : function(XMLHttpRequest, textStatus, errorThrown){

        }
    });
}

/**
 * 비밀번호 찾기 - Email로 찾기 - Email 인증하기
 */
let emailRealString;
function sendEmail() {
    let emailAddress = $("#pw_search_phone").val();
    if( emailAddress == null ) {
        alert("이메일을 입력해주세요.");
        return ;
    }
    $.ajax({
        url: "/login/pwSearch/email/sendEmailCertification",
        type: "post",
        data: JSON.stringify(emailAddress),
        dataType:'text',
        contentType: "application/json",
        async: false,
        success : function(data) {
            alert("인증번호 발송했습니다.");
            emailRealString = data;
        },
        error: function () {
            alert("실패");
        }
    })
}

/**
 * 취소
 */
function cancel() {
    window.close();
}

/**
 * 새 비밀번호 입력 form 체크
 */
function formCheck() {
    console.log('d');
    let check1 = ($("#new_pw").val() === $("#new_pw_check").val());
    let check2 = pwValueCheck($("#new_pw_check").val());
    console.log('ddd');
    console.log('ddddd',check2);
    if (!check2) {
        alert("최소 8 자, 하나 이상의 문자와 하나의 숫자로 입력하세요.");
        return false;
    } else if (!check1) {
        alert("비밀번호를 다시 확인해주세요!!");
        return false;
    } else {
        return true;
    };
}

/**
 * 비밀번호 유효성 체크
 */
function pwValueCheck(pw) {
    let regExp = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;
    return regExp.test(pw);
}

/**
 * 휴대폰 전화번호 유효성 체크
 */
function phoneNumberCheck(phoneNumber) {
    let regPhone = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;
    return regPhone.test(phoneNumber);
}



