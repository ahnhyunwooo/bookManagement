let fileIndex = 0;
<<<<<<< HEAD
/**
 *
 * @param file
 * 텍스트 박스에 파일 이름 넣기
 */
=======
>>>>>>> parent of 6145199 (파일 추가2)
function fileNameChange(file) {
    let splitFile = file.value.split('\\');
    $("#book_register_text0").val(splitFile[splitFile.length-1]);
}

/**
 * 파일 행 추가
 */
function fileRowAdd() {
    ++fileIndex;
    const fileHtml ='<div class="book_register_form_container">'+
        '&nbsp;<input type="text" id="book_register_text'+fileIndex+'"class="book_register_file_text plus_file" readonly>'+
        ' <label for="file_'+fileIndex+'" class="book_register_file_btn">파일추가</label>'+
        ' <input type="file" id="file_'+fileIndex+'" name="file" class="book_register_file" onchange="fileNameChange(this)">'+
        ' <button type="button" class="book_register_file_add_btn" onclick="fileRowAdd()">+</button>'+
        ' <button type="button" class="book_register_file_add_btn" onclick="fileRowMinus(this)">-</button>';
    $("#book_register_form").append(fileHtml);
}
/**
 * 파일 행 제거
 */
function fileRowMinus(btn) {
    $(btn).parent().remove();
    console.log($(btn).parent());
}

