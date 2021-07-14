--테이블 삭제
drop table users;

--시퀀스 삭제
drop sequence seq_user_no;

--테이블 생성
create table users (
    no number,
    id VARCHAR2(20) UNIQUE not null,
    password VARCHAR2(20) not null,
    name VARCHAR2(20),
    gender VARCHAR2(10),
    PRIMARY KEY (no)
);

--시퀀스 생성
create SEQUENCE seq_user_no
INCREMENT BY 1 
START WITH 1
NOCACHE;

--Insert 문
INSERT INTO users
VALUES(seq_user_no.nextval,'danny', '1234', '조대근', 'male');

--Select 문
select *
from users;

rollback;