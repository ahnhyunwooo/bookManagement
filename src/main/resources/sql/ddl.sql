CREATE TABLE member
(
    member_index VARCHAR2(10) PRIMARY KEY,
    member_name VARCHAR2(20),
    member_id   VARCHAR2(20),
    member_pw   VARCHAR2(20),
    member_nickname VARCHAR2(50),
    member_gender   VARCHAR2(5),
    member_phonenumber    VARCHAR2(20),
    member_email          VARCHAR2(50),
    member_joindate       DATE,
    member_book_rental_count  NUMBER,
    member_book_rental_total  NUMBER,
    member_waring_count   NUMBER,
    member_update         DATE
);
CREATE TABLE admin
(
    admin_index VARCHAR2(20) PRIMARY KEY,
    admin_name  VARCHAR2(20),
    admin_id    VARCHAR2(20),
    admin_pw    VARCHAR2(20),
    adimin_nickname VARCHAR2(50)
);
CREATE TABLE book
(
    book_index  VARCHAR2(10) PRIMARY KEY,
    book_name   VARCHAR2(100),
    book_publisher  VARCHAR2(100),
    book_author VARCHAR2(20),
    book_genre  VARCHAR2(20),
    book_register_date DATE,
    book_count  NUMBER,
    book_yn VARCHAR2(5),
    book_retal_count    NUMBER,
    admin_index VARCHAR2(10),
    CONSTRAINT admin_book_fk FOREIGN KEY(admin_index)
    REFERENCES admin(admin_index)
);
CREATE TABLE book_management
(
    retal_index VARCHAR2(10) PRIMARY KEY,
    rental_date DATE,
    deadline_date DATE,
    book_index VARCHAR2(10),
    member_index VARCHAR2(10),
    CONSTRAINT book_book_management_fk FOREIGN KEY( book_index) REFERENCES book(book_index),
    CONSTRAINT member_book_management_fk FOREIGN KEY(member_index) REFERENCES member(member_index)
);
CREATE TABLE category
(
    category_index VARCHAR2(10) PRIMARY KEY,
    category_name VARCHAR2(10)
);
CREATE TABLE board
(
    board_index VARCHAR2(10) PRIMARY KEY,
    board_title VARCHAR2(10),
    board_content VARCHAR2(1000),
    board_views NUMBER,
    board_insdate DATE,
    board_upd_date DATE,
    board_state VARCHAR2(20),
    board_state_memo VARCHAR2(1000),
    member_index VARCHAR2(20),
    category_index VARCHAR2(20),
    CONSTRAINT member_board_fk FOREIGN KEY(member_index) REFERENCES member(member_index),
    CONSTRAINT category_board_fk FOREIGN KEY(category_index) REFERENCES category(category_index)
);
CREATE TABLE board_comment
(
    comment_index VARCHAR2(10) PRIMARY KEY,
    comment_content VARCHAR2(100),
    comment_insdate DATE,
    comment_udp_date DATE,
    board_index VARCHAR2(20),
    member_index VARCHAR2(20),
    CONSTRAINT board_board_comment_fk FOREIGN KEY(board_index) REFERENCES board(board_index),
    CONSTRAINT member_board_comment_fk FOREIGN KEY(member_index) REFERENCES member(member_index)
);
CREATE TABLE book_reservation
(
    reservation_index VARCHAR2(20) PRIMARY KEY,
    member_index VARCHAR2(20),
    book_index VARCHAR2(20),
    reservation_date DATE,
    CONSTRAINT member_book_reservation_fk FOREIGN KEY(member_index) REFERENCES member(member_index),
    CONSTRAINT book_book_reservation_fk FOREIGN KEY(book_index) REFERENCES book(book_index)
);
