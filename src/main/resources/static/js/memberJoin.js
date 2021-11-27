$(document).ready(function (){
    $("#member_join_btn").click(function() {
        alert("버튼1을 누르셨습니다.");
        console.log("dddd");
        //pwCheck();
    });
});
function idOverlap() {
    var id = $("#member_join_id").val();
    var sendData ={"id":id};
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
function pwValueCheck(pw) {
    var regExp = "^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$";
    return regExp.test(pw);
}
function pwCheck(){
    var pw = $("#member_join_pw").val();
    var pwCheck = $("#member_join_pw_check").val();

    if(!pwValueCheck(pw)){
        alert("최소 8 자, 하나 이상의 문자와 하나의 숫자로 입력하세요.");
        $("#member_join_pw").css('border', '2px solid red');
        return ;
    }
    if(pw != pwCheck){
        alert("비밀번호확인란 체크.");
    }
}



