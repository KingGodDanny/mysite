--테이블 삭제
drop table board;

--시퀀스 삭제
drop SEQUENCE seq_board_no;

--Board 테이블 생성
create table board (
    no number,
    title VARCHAR2(500) not null,
    content VARCHAR2(4000),
    hit NUMBER default 0 ,
    reg_date date not null,
    user_no number not null,
    PRIMARY KEY (no),
    CONSTRAINT board_fk FOREIGN KEY(user_no)
    REFERENCES users(no)
);

--시퀀스 생성
create SEQUENCE seq_board_no
INCREMENT by 1
START WITH 1
nocache;


--테스트라인****************************

--삽입 테스트
INSERT INTO board VALUES (seq_board_no.nextval, '하하하', 'ㅋㅋㅋ', '0', sysdate, 1 );

--Board출력
select no,
       title,
       content,
       hit,
       to_char(reg_date, 'YYYY-MM-DD HH24-MI'),
       user_no
from board;

--Users X Board List.jsp 출력용
select bo.no,
       bo.title,
       us.name,
       bo.hit,
       to_char(bo.reg_date, 'YYYY-MM-DD') dateD ,
       bo.user_no
from users us,
     board bo
where us.no = bo.user_no
order by bo.reg_date;

select bo.no,
       us.name,
       bo.hit,
       to_char(bo.reg_date, 'YYYY-MM-DD') dateD ,
       bo.title,
       bo.content,
       bo.user_no
from users us,
     board bo
where us.no = bo.user_no
and bo.no = 1;

--인서트 삽입
INSERT INTO board 
VALUES (seq_board_no.nextval, '제목입니다', '콘텐츠', 0 , sysdate, 1);


--조회수 증가
UPDATE board
set hit = hit + 1
where no = 1;

--전체출력
select *
from board;

--삭제하기
Delete from board
where no = 1;

--수정하기
UPDATE board 
SET title = 'ggg',
    content = 'zzz'
where no = 4;

select bo.no,
       bo.title,
       us.name,
       bo.hit,
       to_char(bo.reg_date, 'YYYY-MM-DD HH24:MI') dateD,
       bo.user_no
from users us,
     board bo
where us.no = bo.user_no
and us.name || bo.title like '% + keyword + %';

rollback;

commit;