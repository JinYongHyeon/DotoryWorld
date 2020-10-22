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

select * from tab
select * from member
delete from member where password='a';
drop table hobbyboard;


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

select * from HOBBYBOARD;

CREATE SEQUENCE hobbyboard_no_seq;
drop sequence hobbyboard_no_seq;
delete from hobbyboard;

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

select count(*) from hobby_post where hobby_title LIKE '%메%' and id like '%u%'

SELECT * FROM hobby_post

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
	
	--20년10월20일[id 까지 PK 복합키로 설정]
CREATE TABLE dotorylist(
	dotory_id VARCHAR2(300),
	id VARCHAR2(300),
	CONSTRAINT PK_dotorylist PRIMARY KEY (dotory_id,id),
	CONSTRAINT FK_dotorylist_id FOREIGN KEY (id) REFERENCES member (id)
	ON DELETE CASCADE
)

DROP TABLE dotorylist

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

/* 신고게시판 게시물 테이블 */
CREATE TABLE report_post(
	reportpost_no NUMBER PRIMARY KEY,
	report_title VARCHAR2(300) NOT NULL,
	report_content CLOB NOT NULL, 
	reportpost_date DATE NOT NULL,
	category_no NUMBER NOT NULL,
	id VARCHAR2(300) NOT NULL,
	CONSTRAINT FK_report_post_id FOREIGN KEY (id) REFERENCES member (id),
	CONSTRAINT FK_report_post_category_no FOREIGN KEY (category_no) REFERENCES category (category_no)
	ON DELETE CASCADE
)
select * from CATEGORY;
delete * from CATEGORY;
CREATE SEQUENCE reportpost_no_seq NOCACHE;
SELECT * FROM report_post;

/* 공지게시판 게시물 테이블 */
CREATE TABLE notice_post(
	noticepost_no NUMBER PRIMARY KEY,
	notice_title VARCHAR2(300) NOT NULL,
	notice_content CLOB NOT NULL, 
	noticepost_date DATE NOT NULL,
	notice_like NUMBER NOT NULL,
	category_no NUMBER NOT NULL,
	id VARCHAR2(300) NOT NULL,
	CONSTRAINT FK_notice_post_id FOREIGN KEY (id) REFERENCES member (id),
	CONSTRAINT FK_notice_post_category_no FOREIGN KEY (category_no) REFERENCES category (category_no)
	ON DELETE CASCADE
)
CREATE SEQUENCE noticepost_no_seq NOCACHE;
ALTER TABLE notice_post MODIFY(notice_like DEFAULT 0);
--게시물 좋아요 테이블
CREATE TABLE hobbypostlike(
	ID VARCHAR2(400),
	HOBBYPOST_NO NUMBER,
	CONSTRAINT pk_hobbypostlike PRIMARY KEY(id,hobbypost_no),
	CONSTRAINT fk_hobbypostlike_id FOREIGN KEY(id) REFERENCES member(id) ON DELETE CASCADE,
	CONSTRAINT fk_hobbypostlike_no FOREIGN KEY(hobbypost_no) REFERENCES hobby_post(hobbypost_no) ON DELETE CASCADE
)




/* 시퀀스 검색*/
SELECT * FROM USER_SEQUENCES;
SELECT * FROM TAB;
SELECT * FROM CATEGORY
SELECT * FROM REPORT_POST
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
DROP TABLE report_post

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
select * from tab;
--카테고리 소개글 생김
ALTER TABLE category ADD(category_content CLOB NOT NULL); 

--게시판에 좋아요 기본 0 설정, 게시판 소개글 삭제
ALTER TABLE hobbyboard MODIFY(hobbyboard_like DEFAULT 0);
ALTER TABLE hobbyboard DROP COLUMN hobbyboard_content;
--게시판 좋아요 삭제
ALTER TABLE hobbyboard DROP COLUMN hobbyboard_like;

--게시글 좋아요 기본 0 설정, 게시글 조회수 기본 0 설정
ALTER TABLE hobby_post MODIFY(hobby_like DEFAULT 0);
ALTER TABLE hobby_post MODIFY(hobbypost_viewcount DEFAULT 0);

--작은항목 이미지 URL 주소 컬럼 추가
ALTER TABLE HOBBYBOARD ADD(hobbyboard_imgName VARCHAR2(500))

--북마크 제목 추가
ALTER TABLE bookmark ADD(bookmark_title VARCHAR2(300)NOT NULL);

select * from category

--북마크 상품번호 FK  설정
ALTER TABLE BOOKMARK

ALTER TABLE '테이블명' ADD CONSTRAINT '제약조건명' FOREIGN KEY('외래키 칼럼명') 
REFERENCES '참조테이블'('참조테이블_PK') [ON DELET 옵션] [ON UPDATE 옵션]



--샘플 데이터
INSERT INTO member(id,password,name,address,email,nickname,profile_content,grade) VALUES('admin','1234','관리자','판교','admin@gmail.com','다람쥐','관리자입니다','다람쥐');
INSERT INTO member(id,password,name,address,email,nickname,profile_content,grade) VALUES('user1','1234','사용자1','판교','user1@gmail.com','도토리1','도토리1입니다','도토리');
INSERT INTO member(id,password,name,address,email,nickname,profile_content,grade) VALUES('user2','1234','사용자2','판교','user2@gmail.com','도토리2','도토리2입니다','도토리');
INSERT INTO member(id,password,name,address,email,nickname,profile_content,grade) VALUES('user3','1234','사용자3','판교','user3@gmail.com','도토리3','도토리3입니다','도토리');
INSERT INTO member(id,password,name,address,email,nickname,profile_content,grade) VALUES('user4','1234','사용자4','판교','user4@gmail.com','도토리4','도토리4입니다','도토리');
INSERT INTO member(id,password,name,address,email,nickname,profile_content,grade) VALUES('user5','1234','꽈꽈','북극','user5@gmail.com','도토리5','도토리5입니다','도토리');
INSERT INTO member(id,password,name,address,email,nickname,profile_content,grade) VALUES('user6','1234','꾸꾸','남극','user6@gmail.com','도토리6','도토리6입니다','도토리');

INSERT INTO category(category_no,category_name,category_content) VALUES(category_no_seq.nextval,'운동','운동설명');
INSERT INTO category(category_no,category_name,category_content) VALUES(category_no_seq.nextval,'요리','요리설명');
INSERT INTO category(category_no,category_name,category_content) VALUES(category_no_seq.nextval,'영화','요리설명');
INSERT INTO category(category_no,category_name,category_content) VALUES(category_no_seq.nextval,'음악','음악설명');
INSERT INTO category(category_no,category_name,category_content) VALUES(category_no_seq.nextval,'공지/신고','공지/신고사항');

select * from CATEGORY;

INSERT INTO hobbyboard(hobbyboard_no,hobbyboard_title,category_no) VALUES(hobbyboard_no_seq.nextval,'축구',1);
INSERT INTO hobbyboard(hobbyboard_no,hobbyboard_title,category_no) VALUES(hobbyboard_no_seq.nextval,'배드민턴',1);
INSERT INTO hobbyboard(hobbyboard_no,hobbyboard_title,category_no) VALUES(hobbyboard_no_seq.nextval,'농구',1);
INSERT INTO hobbyboard(hobbyboard_no,hobbyboard_title,category_no) VALUES(hobbyboard_no_seq.nextval,'야구',1);

INSERT INTO hobbyboard(hobbyboard_no,hobbyboard_title,category_no) VALUES(hobbyboard_no_seq.nextval,'일식',2);
INSERT INTO hobbyboard(hobbyboard_no,hobbyboard_title,category_no) VALUES(hobbyboard_no_seq.nextval,'양식',2);
INSERT INTO hobbyboard(hobbyboard_no,hobbyboard_title,category_no) VALUES(hobbyboard_no_seq.nextval,'중식',2);
INSERT INTO hobbyboard(hobbyboard_no,hobbyboard_title,category_no) VALUES(hobbyboard_no_seq.nextval,'한식',2);

INSERT INTO hobbyboard(hobbyboard_no,hobbyboard_title,category_no) VALUES(hobbyboard_no_seq.nextval,'로맨스',3);
INSERT INTO hobbyboard(hobbyboard_no,hobbyboard_title,category_no) VALUES(hobbyboard_no_seq.nextval,'스릴러',3);
INSERT INTO hobbyboard(hobbyboard_no,hobbyboard_title,category_no) VALUES(hobbyboard_no_seq.nextval,'공포',3);
INSERT INTO hobbyboard(hobbyboard_no,hobbyboard_title,category_no) VALUES(hobbyboard_no_seq.nextval,'액션',3);

INSERT INTO hobbyboard(hobbyboard_no,hobbyboard_title,category_no) VALUES(hobbyboard_no_seq.nextval,'댄스',4);
INSERT INTO hobbyboard(hobbyboard_no,hobbyboard_title,category_no) VALUES(hobbyboard_no_seq.nextval,'클래식',4);
INSERT INTO hobbyboard(hobbyboard_no,hobbyboard_title,category_no) VALUES(hobbyboard_no_seq.nextval,'발라드',4);
INSERT INTO hobbyboard(hobbyboard_no,hobbyboard_title,category_no) VALUES(hobbyboard_no_seq.nextval,'팝',4);

INSERT INTO hobbyboard(hobbyboard_no,hobbyboard_title,category_no) VALUES(hobbyboard_no_seq.nextval,'공지',5);
INSERT INTO hobbyboard(hobbyboard_no,hobbyboard_title,category_no) VALUES(hobbyboard_no_seq.nextval,'신고',5);

select * from HOBBYBOARD;

-- 취미게시판 샘플데이터
INSERT INTO hobby_post(hobbypost_no,hobby_title,hobby_content,hobbypost_date,hobbyboard_no,id)
VALUES(hobbypost_no_seq.NEXTVAL,'메시는 메시다..','메시~~',TO_DATE(SYSDATE,'YYYY-MM-DD HH24:MI:SS'),1,'user1');
INSERT INTO hobby_post(hobbypost_no,hobby_title,hobby_content,hobbypost_date,hobbyboard_no,id)
VALUES(hobbypost_no_seq.NEXTVAL,'네이마르는 네이마르다..','네이마르~~',TO_DATE(SYSDATE,'YYYY-MM-DD HH24:MI:SS'),1,'user3');

INSERT INTO hobby_post(hobbypost_no,hobby_title,hobby_content,hobbypost_date,hobbyboard_no,id)
VALUES(hobbypost_no_seq.NEXTVAL,'고구마는 고구마..','고구마',TO_DATE(SYSDATE,'YYYY-MM-DD HH24:MI:SS'),1,'user3');
INSERT INTO hobby_post(hobbypost_no,hobby_title,hobby_content,hobbypost_date,hobbyboard_no,id)
VALUES(hobbypost_no_seq.NEXTVAL,'고구마는 고구마..','고구마',TO_DATE(SYSDATE,'YYYY-MM-DD HH24:MI:SS'),1,'user3');
INSERT INTO hobby_post(hobbypost_no,hobby_title,hobby_content,hobbypost_date,hobbyboard_no,id)
VALUES(hobbypost_no_seq.NEXTVAL,'고구마는 고구마..','고구마',TO_DATE(SYSDATE,'YYYY-MM-DD HH24:MI:SS'),1,'user3');
INSERT INTO hobby_post(hobbypost_no,hobby_title,hobby_content,hobbypost_date,hobbyboard_no,id)
VALUES(hobbypost_no_seq.NEXTVAL,'고구마는 고구마..','고구마',TO_DATE(SYSDATE,'YYYY-MM-DD HH24:MI:SS'),1,'user3');
INSERT INTO hobby_post(hobbypost_no,hobby_title,hobby_content,hobbypost_date,hobbyboard_no,id)
VALUES(hobbypost_no_seq.NEXTVAL,'고구마는 고구마..','고구마',TO_DATE(SYSDATE,'YYYY-MM-DD HH24:MI:SS'),1,'user3');


INSERT INTO hobby_post(hobbypost_no,hobby_title,hobby_content,hobbypost_date,hobbyboard_no,id)
VALUES(hobbypost_no_seq.NEXTVAL,'고구마는 고구마..','고구마',TO_DATE(SYSDATE,'YYYY-MM-DD HH24:MI:SS'),1,'user1');
INSERT INTO hobby_post(hobbypost_no,hobby_title,hobby_content,hobbypost_date,hobbyboard_no,id)
VALUES(hobbypost_no_seq.NEXTVAL,'고구마는 고구마..','고구마',TO_DATE(SYSDATE,'YYYY-MM-DD HH24:MI:SS'),1,'user1');
INSERT INTO hobby_post(hobbypost_no,hobby_title,hobby_content,hobbypost_date,hobbyboard_no,id)
VALUES(hobbypost_no_seq.NEXTVAL,'고구마는 고구마..','고구마',TO_DATE(SYSDATE,'YYYY-MM-DD HH24:MI:SS'),1,'user1');

/* 신고게시판 샘플 데이터 */
INSERT INTO hobby_post(hobbypost_no,hobby_title,hobby_content,hobbypost_date,hobbyboard_no,id)
VALUES(hobbypost_no_seq.NEXTVAL,'신고신고신고','신고',TO_DATE(SYSDATE,'YYYY-MM-DD HH24:MI:SS'),18,'user3');

-- 공지게시판 샘플 데이터
INSERT INTO hobby_post(hobbypost_no,hobby_title,hobby_content,hobbypost_date,hobbyboard_no,id)
VALUES(hobbypost_no_seq.NEXTVAL,'공지공지공지','공지',TO_DATE(SYSDATE,'YYYY-MM-DD HH24:MI:SS'),17,'user3');

-- 내 도토리 목록 데이터
INSERT INTO dotorylist VALUES('user2','user1');
INSERT INTO dotorylist VALUES('user3','user1');
INSERT INTO dotorylist VALUES('user4','user1');
INSERT INTO dotorylist VALUES('user5','user1');
INSERT INTO dotorylist VALUES('user6','user1');

SELECT ROW_NUMBER() OVER(ORDER BY hobbyboard_no ASC),hobbyboard_title from HOBBYBOARD WHERE category_no = ?;

UPDATE hobbyboard SET hobbyboard_imgName = 'soccer.jpg' WHERE hobbyboard_no='1';
UPDATE hobbyboard SET hobbyboard_imgName = 'badminton.jpg' WHERE hobbyboard_no='2';
UPDATE hobbyboard SET hobbyboard_imgName = 'basketball.jpg' WHERE hobbyboard_no='5';
UPDATE hobbyboard SET hobbyboard_imgName = 'baseball.jpg' WHERE hobbyboard_no='6';

UPDATE hobbyboard SET hobbyboard_imgName = 'japan_food.jpg' WHERE hobbyboard_no='3';
UPDATE hobbyboard SET hobbyboard_imgName = 'italian_food.jpg' WHERE hobbyboard_no='4';
UPDATE hobbyboard SET hobbyboard_imgName = 'chinese_food.jpg' WHERE hobbyboard_no='7';
UPDATE hobbyboard SET hobbyboard_imgName = 'korea_food.jpg' WHERE hobbyboard_no='8';

UPDATE hobbyboard SET hobbyboard_imgName = 'romance.jpg' WHERE hobbyboard_no='9';
UPDATE hobbyboard SET hobbyboard_imgName = 'thriller.jpg' WHERE hobbyboard_no='10';
UPDATE hobbyboard SET hobbyboard_imgName = 'horror.jpg' WHERE hobbyboard_no='11';
UPDATE hobbyboard SET hobbyboard_imgName = 'action.jpg' WHERE hobbyboard_no='12';

UPDATE hobbyboard SET hobbyboard_imgName = 'dance.jpg' WHERE hobbyboard_no='13';
UPDATE hobbyboard SET hobbyboard_imgName = 'classic.jpg' WHERE hobbyboard_no='14';
UPDATE hobbyboard SET hobbyboard_imgName = 'ballad.png' WHERE hobbyboard_no='15';
UPDATE hobbyboard SET hobbyboard_imgName = 'pop.jpg' WHERE hobbyboard_no='16';

UPDATE category SET category_content = '<h3><b>SPORTS</b></h3><BR> 
<b>You have to exercise, or at some point you will just break down.</b> <BR>
<b>Reading is to the mind what exercise is to the body. </b><BR>
<b>The reason I exercise is for the quality of life I enjoy. </b>' WHERE category_no='1';
UPDATE category SET category_content = '<h3><b>COOKING</b></h3><BR> 
<b>Good food ends with good talk.</b><BR>
<b>There aint no such thing as wrong food.</b><BR>
<b>Food is the most primitive form of comfort.</b> <BR>' WHERE category_no='2';
UPDATE category SET category_content = '<h3><b>MOVIE</b></h3><BR> 
<b>You can never replace anyone, because everyone is made up of such beautiful specific details.</b><BR>
<b>You either die a hero or you live long enough to see yourself become the villain.</b><BR>
<b>Life is like a box of chocolates, You never know what you are gonna get.</b><BR>' WHERE category_no='3';
UPDATE category SET category_content = '<h3><b>MUSIC</b></h3><BR>
<b>Dont need make-up to cover up. Being the way that you are is enough.</b><BR>
<b>Dont hide yourself in regret. Just love yourself and you are set.</b><BR>
<b>A heart thats broke is a heart thats been loved.</b> <BR>' WHERE category_no='4';

update category set category_name='공지/신고' where category_no='5'
select * from category

INSERT INTO category(category_no,category_name,category_content) VALUES(category_no_seq.nextval,'요리','요리설명');


select * from category;
select * from HOBBYBOARD;


delete from member where password='123'
select * from dotorylist;

delete from member where id='user5'
select * from member;

select * from hobby_post;
select count(*) from hobby_post;

SELECT * FROM hobbypostlike WHERE id='user1' AND hobbypost_no=55

select hobbypost_no,hobby_title,id,hobbypost_date,hobbypost_viewcount from(
select row_number() over(order by hobbypost_no asc) as rnum, hobbypost_no,hobby_title,id,hobbypost_date,hobbypost_viewcount from hobby_post )
where rnum between 1 and 2 ;

select id from DOTORYLIST d;


--친구목록 리스트
SELECT d.dotory_id,m.name FROM dotorylist d, member m WHERE m.id = d.dotory_id AND d.id= 'user1';




-- 방명록 테스트
INSERT INTO toryhome_board(toryhome_no, toryhome_title, toryhome_content, toryhome_date, id_writer, id)
VALUES(toryhome_no_seq.nextval,'방명록','테스트 중입니다.', SYSDATE, 'user1','user4');
INSERT INTO toryhome_board(toryhome_no, toryhome_title, toryhome_content, toryhome_date, id_writer, id)
VALUES(toryhome_no_seq.nextval,'방명록','테스트 중입니다.user2', SYSDATE, 'user2','user4');
INSERT INTO toryhome_board(toryhome_no, toryhome_title, toryhome_content, toryhome_date, id_writer, id)
VALUES(toryhome_no_seq.nextval,'방명록','테스트 중user2', SYSDATE, 'user2','user3');
INSERT INTO toryhome_board(toryhome_no, toryhome_title, toryhome_content, toryhome_date, id_writer, id)
VALUES(toryhome_no_seq.nextval,'방명록','테스트 중입니다.user1', SYSDATE, 'user1','user4');
INSERT INTO toryhome_board(toryhome_no, toryhome_title, toryhome_content, toryhome_date, id_writer, id)
VALUES(toryhome_no_seq.nextval,'방명록','테스트 중user4', SYSDATE, 'user4','user3');
INSERT INTO toryhome_board(toryhome_no, toryhome_title, toryhome_content, toryhome_date, id_writer, id)
VALUES(toryhome_no_seq.nextval,'방명록','테스트 중user5', SYSDATE, 'user5','user3');
INSERT INTO toryhome_board(toryhome_no, toryhome_title, toryhome_content, toryhome_date, id_writer, id)
VALUES(toryhome_no_seq.nextval,'방명록','테스트 중user5', SYSDATE, 'user5','user4');

SELECT toryhome_title, toryhome_content, to_char(toryhome_date, 'YYYY-MM-DD HH24:MI:SS'), id_writer, id
FROM toryhome_board;

SELECT ROW_NUMBER() OVER(ORDER BY t.toryhome_no DESC) as rnum, t.toryhome_no, t.toryhome_title, t.toryhome_content, t.toryhome_date, t.id_writer, m.profile_photo
FROM TORYHOME_BOARD t, member m
where t.id_writer = m.id and t.id = 'user4' and t.toryhome_title='방명록'

SELECT tory.toryhome_no, tory.toryhome_title, tory.toryhome_content, TO_CHAR(tory.toryhome_date, 'YYYY-MM-DD HH24:MI:SS'), tory.id_writer, tory.profile_photo
FROM (SELECT ROW_NUMBER() OVER(ORDER BY t.toryhome_no DESC) as rnum, t.toryhome_no, t.toryhome_title, t.toryhome_content, t.toryhome_date, t.id_writer, m.profile_photo
FROM TORYHOME_BOARD t, member m
WHERE t.id_writer = m.id and t.id = 'user4' and t.toryhome_title='방명록') tory
WHERE tory.rnum BETWEEN 4 AND 4;

delete from TORYHOME_BOARD
where id_writer='user2' and id='user4' and TO_CHAR(toryhome_date, 'YYYY-MM-DD HH24:MI:SS')='2020-10-21 10:45:51';

SELECT COUNT(*) FROM TORYHOME_BOARD WHERE id='user4';

--북마크 페이징 쿼리문
SELECT no,b.link,h.hobby_title,h.id,ho.hobbyboard_title FROM(
SELECT ROW_NUMBER() OVER(ORDER BY bookmark_no desc) as no,link FROM bookmark
WHERE id= 'user1' AND bookmark_divide = '북마크')b,hobby_post h,hobbyboard ho
WHERE b.link =h.hobbypost_no AND h.hobbyboard_no = ho.hobbyboard_no AND no BETWEEN 1 AND 20
ORDER BY no ASC


SELECT * FROM HOBBY_POST
SELECT * FROM BOOKMARK

--핫 리스트 쿼리문
SELECT H.RNUM,H.HOBBYBOARD_NO,hb.hobbyboard_imgName,hb.hobbyboard_title FROM(
	SELECT ROW_NUMBER() OVER(ORDER BY HOBBY_LIKE DESC) AS rnum, HOBBYBOARD_NO  FROM (
	SELECT HOBBYBOARD_NO,SUM(HOBBY_LIKE) AS HOBBY_LIKE,SUM(hobbypost_viewcount) FROM HOBBY_POST GROUP BY HOBBYBOARD_NO
))h,HOBBYBOARD hb WHERE h.HOBBYBOARD_NO = hb.HOBBYBOARD_NO AND RNUM<=2

--베스트 쿼리문
SELECT H.RNUM,H.HOBBYBOARD_NO,hb.hobbyboard_imgName,hb.hobbyboard_title FROM(
	SELECT ROW_NUMBER() OVER(ORDER BY hobbypost_viewcount DESC) AS rnum, HOBBYBOARD_NO  FROM (
	SELECT HOBBYBOARD_NO,SUM(HOBBY_LIKE) AS HOBBY_LIKE,SUM(hobbypost_viewcount) AS hobbypost_viewcount FROM HOBBY_POST GROUP BY HOBBYBOARD_NO
))h,HOBBYBOARD hb WHERE h.HOBBYBOARD_NO = hb.HOBBYBOARD_NO AND RNUM<=2




SELECT b.rnum, b.LINK,b.ID,h.hobbyboard_title,h.hobbyboard_imgName 
FROM(SELECT ROW_NUMBER() OVER(ORDER BY bookmark_no ASC) AS rnum,link,id FROM bookmark 
WHERE bookmark_divide='즐겨찾기' AND id='user1')b,hobbyboard h 
WHERE h.hobbyboard_no = b.link AND b.rnum BETWEEN 2 AND 3;

SELECT COUNT(*) FROM BOOKMARK WHERE ID ='user1' AND link = 54;


