function idOverlap(){
    var id = $(".member_join").val();
    var sendData ={"id":id};
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
            }else{
                alert("이미 사용중인 아이디 입니다");
            }
        },
        error : function(XMLHttpRequest, textStatus, errorThrown){
            alert("통신 실패.")
        }
    });
}