$(document).ready(function (){
    $("#member_join_action").submit(function() {
        pwCheck();
    });
});
/**
 * 아이디 체크
 */
function idOverlap() {
    var id = $("#member_join_id").val();
    var sendData ={"id":id};

    //아이디 유효성 체크
    if(!idValueCheck(id)){
        alert("아이디를 영문자로 시작하는 영문자 또는 숫자 6~20자로 입력하세요.");
        $("#member_join_id").css('border', '2px solid red');
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
                $("#member_join_id").css('border', '1px solid rgb(118, 118, 118)');
            }else{
                $("#member_join_id").css('border', '2px solid red');
                alert("이미 사용중인 아이디 입니다");
            }
        },
        error : function(XMLHttpRequest, textStatus, errorThrown){
            alert("통신 실패.");
        }
    });
}
function idValueCheck(id) {
    var regExp = /^[a-z]+[a-z0-9]{5,19}$/g;
    return regExp.test(id);
}

/**
 * 비밀번호 체크
 */
function pwCheck(){
    var pw = $("#member_join_pw").val();
    var pwCheck = $("#member_join_pw_check").val();

    //비밀번호 유효성 체크
    if(!pwValueCheck(pw)){
        //alert("6~20 영문 대소문자, 최소 1개의 숫자 혹은 특수문자를 포함해주세요.");
        alert("특수문자 / 문자 / 숫자 포함 형태의 8~15자리 이내의 암호");
        $("#member_join_pw").css('border', '2px solid red');
        return ;
    }
    //비밀번호와 비밀번호확인란이 null이거나 값이다를 경우
    if(pw != pwCheck && pw == null){
        alert("비밀번호확인란 체크.");
    }

}
function pwValueCheck(pw) {
    var regExp = /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/;
    return regExp.test(pw);
}


