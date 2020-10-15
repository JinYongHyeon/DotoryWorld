CREATE TABLE member(
	id VARCHAR2(300) PRIMARY KEY,
	password VARCHAR2(300) NOT NULL,
	name VARCHAR2(300) NOT NULL,
	address VARCHAR2(300) NOT NULL,
	email VARCHAR2(300) NOT NULL,
	nickname VARCHAR2(300) NOT NULL,
	profile_photo VARCHAR2(300), /* NULL 설정 */
	profile_content VARCHAR2(300) NOT NULL,
	grade VARCHAR2(300)	NOT NULL
)

SELECT * FROM member;


CREATE TABLE category(
	category_no NUMBER PRIMARY KEY,
	category_name VARCHAR2(300) NOT NULL
)


CREATE SEQUENCE category_no_seq;

CREATE TABLE hobbyboard(
	hobbyboard_no NUMBER PRIMARY KEY,
	hobbyboard_title VARCHAR2(300) NOT NULL,
	hobbyboard_content CLOB NOT NULL,
	hobbyboard_like NUMBER NOT NULL,
	category_no NUMBER NOT NULL,
	CONSTRAINT FK_hobbyboard_category_no FOREIGN KEY (category_no) REFERENCES category (category_no)
	ON DELETE CASCADE
)

CREATE SEQUENCE hobbyboard_no_seq;

CREATE TABLE hobby_post(
	hobbypost_no NUMBER PRIMARY KEY,
	hobby_title VARCHAR2(300) NOT NULL,
	hobby_content CLOB NOT NULL, 
	hobbypost_date DATE NOT NULL,
	hobby_like NUMBER NOT NULL,
	hobbypost_viewcount NUMBER NOT NULL,
	hobbyboard_no NUMBER NOT NULL,
	id VARCHAR2(300) NOT NULL,
	CONSTRAINT FK_hobby_post_id FOREIGN KEY (id) REFERENCES member (id) 	ON DELETE CASCADE,
	CONSTRAINT FK_hobby_post_hobbyboard_no FOREIGN KEY (hobbyboard_no) REFERENCES hobbyboard (hobbyboard_no)
	ON DELETE CASCADE
)

CREATE SEQUENCE hobbypost_no_seq;

CREATE TABLE bookmark(
	bookmark_no NUMBER PRIMARY KEY,
	link VARCHAR2(2000) NOT NULL,
	bookmark_divide VARCHAR2(300) NOT NULL,
	id VARCHAR2(300) NOT NULL,
	CONSTRAINT FK_bookmark_id FOREIGN KEY (id) REFERENCES member (id)
	ON DELETE CASCADE
)

CREATE SEQUENCE bookmark_no_seq;

CREATE TABLE dotorylist(
	dotory_id VARCHAR2(300) PRIMARY KEY,
	id VARCHAR2(300) NOT NULL,
	CONSTRAINT FK_dotorylist_id FOREIGN KEY (id) REFERENCES member (id)
	ON DELETE CASCADE
)

/*DROP TABLE dotorylist*/

CREATE TABLE photobook(
	photobook_no NUMBER PRIMARY KEY,
	photobook_url VARCHAR2(2000),
	id VARCHAR2(300) NOT NULL,
	CONSTRAINT FK_photobook_id FOREIGN KEY (id) REFERENCES member (id)
	ON DELETE CASCADE
)

CREATE SEQUENCE photobook_no_seq;


CREATE TABLE toryhome_board(
	toryhome_no NUMBER PRIMARY KEY,
	toryhome_title VARCHAR2(300) NOT NULL,
	toryhome_content CLOB NOT NULL,
	toryhome_date DATE NOT NULL,
	id_writer VARCHAR2(300) NOT NULL,
	id VARCHAR2(300) NOT NULL,
	CONSTRAINT FK_toryhome_board_id FOREIGN KEY (id) REFERENCES member (id)
	ON DELETE CASCADE
)

CREATE SEQUENCE toryhome_no_seq
/* 시퀀스 검색*/
SELECT * FROM USER_SEQUENCES;
SELECT * FROM TAB;
/* ON DELETE CASCADE */ 

--삭제 테이블
DROP TABLE member
DROP TABLE category
DROP TABLE hobbyboard
DROP TABLE hobby_post
DROP TABLE bookmark
DROP TABLE dotorylist
DROP TABLE photobook
DROP TABLE toryhome_board

--삭제 시퀀스
DROP SEQUENCE bookmark_no_seq
DROP SEQUENCE category_no_seq 
DROP SEQUENCE hobbyboard_no_seq
DROP SEQUENCE hobbypost_no_seq
DROP SEQUENCE photobook_no_seq
DROP SEQUENCE toryhome_no_seq


--캐쉬 기능 비활성화(비정상 종료 방지)
ALTER SEQUENCE bookmark_no_seq NOCACHE;
ALTER SEQUENCE category_no_seq NOCACHE;
ALTER SEQUENCE hobbyboard_no_seq NOCACHE;
ALTER SEQUENCE hobbypost_no_seq NOCACHE;
ALTER SEQUENCE photobook_no_seq NOCACHE;
ALTER SEQUENCE toryhome_no_seq NOCACHE;

--테이블 변경사항

--카테고리 소개글 생김
ALTER TABLE category ADD(category_content CLOB NOT NULL); 

--게시판에 좋아요 기본 0 설정, 게시판 소개글 삭제
ALTER TABLE hobbyboard MODIFY(hobbyboard_like DEFAULT 0);
ALTER TABLE hobbyboard DROP COLUMN hobbyboard_content;

--게시글 좋아요 기본 0 설정, 게시글 조회수 기본 0 설정
ALTER TABLE hobby_post MODIFY(hobby_like DEFAULT 0);
ALTER TABLE hobby_post MODIFY(hobbypost_viewcount DEFAULT 0);



--샘플 데이터
INSERT INTO member(id,password,name,address,email,nickname,profile_content,grade) VALUES('admin','1234','관리자','판교','admin@gmail.com','다람쥐','관리자입니다','다람쥐');
INSERT INTO member(id,password,name,address,email,nickname,profile_content,grade) VALUES('user1','1234','사용자1','판교','user1@gmail.com','도토리1','도토리1입니다','도토리');
INSERT INTO member(id,password,name,address,email,nickname,profile_content,grade) VALUES('user2','1234','사용자2','판교','user2@gmail.com','도토리2','도토리2입니다','도토리');
INSERT INTO member(id,password,name,address,email,nickname,profile_content,grade) VALUES('user3','1234','사용자3','판교','user3@gmail.com','도토리3','도토리3입니다','도토리');
INSERT INTO member(id,password,name,address,email,nickname,profile_content,grade) VALUES('user4','1234','사용자4','판교','user4@gmail.com','도토리4','도토리4입니다','도토리');

INSERT INTO category(category_no,category_name,category_content) VALUES(category_no_seq.nextval,'운동','운동설명');
INSERT INTO category(category_no,category_name,category_content) VALUES(category_no_seq.nextval,'요리','요리설명');

INSERT INTO hobbyboard(hobbyboard_no,hobbyboard_title,category_no) VALUES(hobbyboard_no_seq.nextval,'축구',1);
INSERT INTO hobbyboard(hobbyboard_no,hobbyboard_title,category_no) VALUES(hobbyboard_no_seq.nextval,'배드민턴',1);

INSERT INTO hobbyboard(hobbyboard_no,hobbyboard_title,category_no) VALUES(hobbyboard_no_seq.nextval,'일식',2);
INSERT INTO hobbyboard(hobbyboard_no,hobbyboard_title,category_no) VALUES(hobbyboard_no_seq.nextval,'양식',2);

INSERT INTO hobby_post(hobbypost_no,hobby_title,hobby_content,hobbypost_date,hobbyboard_no,id)
VALUES(hobbypost_no_seq.NEXTVAL,'메시는 메시다..','메시~~',TO_DATE(SYSDATE,'YYYY-MM-DD HH24:MI:SS'),1,'user4');
INSERT INTO hobby_post(hobbypost_no,hobby_title,hobby_content,hobbypost_date,hobbyboard_no,id)
VALUES(hobbypost_no_seq.NEXTVAL,'네이마르는 네이마르다..','네이마르~~',TO_DATE(SYSDATE,'YYYY-MM-DD HH24:MI:SS'),1,'user3');







,