let idBtn = false;
let oldValue;
function formCheck(){
    let pwBoolean = pwCheck();
    let nickNameBoolean= nickNameCheck();
    let genderBoolean = genderCheck();
    let idBoolean = idCheck();
    if(pwBoolean && nickNameBoolean && genderBoolean &&idBoolean) {
        return true;
    }
    return false;
}
/**
 * 아이디 중복 체크
 */
function idOverlap() {
    let id = $("#member_join_id").val();
    let sendData ={"id":id};

    //아이디 유효성 체크
    if(!idValueCheck(id)){
        alert("아이디를 영문자로 시작하는 영문자 또는 숫자 6~20자로 입력하세요.");
        $("#member_join_id").css('border', '2px solid #FF0000');
        idBtn = false;
        return ;
    }
    $.ajax({
        url: "/idOverlap",
        type: "post",
        data: JSON.stringify(sendData),
        dataType:'json',
        contentType: "application/json",
        async: false,
        success: function(data){
            //아이디 중복체크
            if(data == true){
                alert("사용 가능한 아이디 입니다.");
                $("#member_join_id").css('border', '1px solid #767676');
                idBtn = true;
                oldValue = id;
            }else{
                $("#member_join_id").css('border', '2px solid #FF0000');
                alert("이미 사용중인 아이디 입니다");
                idBtn = false;
            }
        },
        error : function(XMLHttpRequest, textStatus, errorThrown){
            alert("통신 실패.");
        }
    });
}
function idValueCheck(id) {
    let regExp = /^[a-z]+[a-z0-9]{5,19}$/g;
    return regExp.test(id);
}

/**
 * 아이디 체크
 */
function idCheck() {
    let currentValue = $("#member_join_id").val();
    if(currentValue === oldValue && idBtn) {
        return true;
    }
    alert("id를 중복체크 해주세요.");
    return false;
}


/**
 * 닉네임 체크
 */
function nickNameCheck() {
    let nickName = $("#member_join_nickname").val();
    let sendData = {"nickName":nickName};
    //닉네임 정규식 검사
    if(!nickNameValueCheck(nickName)){
        $("#member_join_nickname").css('border', '2px solid #FF0000');
        $("#member_join_nickname_error").css('display', 'block');
        return false;
    }else  {
        //닉네임 중복 체크
        $.ajax({
            url: "/nickNameOverlap",
            type: "post",
            data: JSON.stringify(sendData),
            dataType:'json',
            contentType: "application/json",
            async: false,
            success: function(data){
                if(data == true){
                    $("#member_join_nickname_error").css('display', 'none');
                    $("#member_join_nickname").css('border', '1px solid #767676');
                    return true;
                }else{
                    $("#member_join_nickname").css('border', '2px solid #FF0000');
                    $("#member_join_nickname_error").css('display', 'block');
                    $("#member_join_nickname").text("이미 사용 중인 닉네임입니다.");
                    return false;
                }
            },
            error : function(XMLHttpRequest, textStatus, errorThrown){
                alert("통신 실패.");
            }
        });
    }
}
//2~6자 닉네임 체크
function nickNameValueCheck(nickName) {
    let regExp = /^([a-zA-Z0-9ㄱ-ㅎ|ㅏ-ㅣ|가-힣]).{1,5}$/
    return regExp.test(nickName);
}
/**
 * 비밀번호 체크
 */
function pwValueCheck(pw) {
    let regExp = "^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$";
    return regExp.test(pw);
}
function pwCheck(){
    let pw = $("#member_join_pw").val();
    let pwCheck = $("#member_join_pw_check").val();

    //비밀번호 유효성 체크
    if(!pwValueCheck(pw)){
        $("#member_join_pw").css('border', '2px solid #FF0000');
        $("#member_join_pw_error").text("최소 8 자, 하나 이상의 문자와 하나의 숫자로 입력하세요.");
        $("#member_join_pw_error").css('display', 'block');
        return false;
    }else {
        $("#member_join_pw").css('border', '1px solid #767676');
        $("#member_join_pw_error").css('display', 'none');
    }
    //비밀번호와 비밀번호확인 값이다를 경우
    if(pw != pwCheck){
        $("#member_join_pw_check").css('border', '2px solid #FF0000');
        $('#member_join_pw_check_error').text("비밀번호 확인란을 다시 입력해주세요.");
        $("#member_join_pw_check_error").css('display', 'block');
        return false;
    }else {
        $("#member_join_pw_check_error").css('display', 'none');
        $("#member_join_pw_check").css('border', '1px solid #767676');
    }
    return true;
}
function pwValueCheck(pw) {
    let regExp = /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/;
    return regExp.test(pw);
}

/**
 * 성별 체크
 */
function genderCheck() {
    if(!$("input[name=gender]").is(":checked")) {
        $("#gender_error").css('display', 'block');
    }else {
        $("#gender_error").css('display', 'none');
    }
}

/**
 * 핸드폰 인증번호
 */
function sendPhone() {
    const phoneNumber = $("#member_join_phone").val();
    const ncpAccessKey = "AkHun7IKEQmKYKTYXnBa";
    const ncpSecretKey = "sdsrwMTdaWwrRlQLLRoyPNjiqHscB8ynnGEde2Rq";
    const ncpServiceID = "ncp:sms:kr:275984439775:toyproject";
    const myPhoneNumber = "01099498902";
    let timestamp = new Date().getTime();

    let url = `https://sens.apigw.ntruss.com/sms/v2/services/${ncpServiceID}/messages`;
    let sendData = {
        "type" : "SMS",
        "contentType":"COMM",
        "from":myPhoneNumber,
        "messages":[
            {
                "to":phoneNumber,
                "content":"hi"
            },
        ]
    }
    $.ajax({
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Content-type","application/json; charset=utf-8");
            xhr.setRequestHeader("x-ncp-apigw-timestamp","JWT " + timestamp);
            xhr.setRequestHeader("x-ncp-iam-access-key",ncpAccessKey);
            xhr.setRequestHeader("x-ncp-apigw-signature-v2", ncpSecretKey);
            xhr.setRequestHeader("Access-Control-Allow-Origin:*")
            xhr.setRequestHeader("Access-Control-Allow-Methods:GET,POST,PUT,DELETE,OPTIONS");
            xhr.setRequestHeader("Access-Control-Max-Age:3600");
            xhr.setRequestHeader("Access-Control-Allow-Headers: Origin,Accept,X-Requested-With,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization");
        },
        url: url,
        type: "post",
        data: sendData,
        dataType: "json",
        success: function () {
            alert("성공");
        },
        error: function (requestId, requestTime, statusCode, statusName) {
            alert(requestId);
        }

    })

}



