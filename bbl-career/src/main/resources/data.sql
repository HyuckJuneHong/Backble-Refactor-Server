INSERT INTO tbl_career(member_identity,name,context,period,history_id) VALUES('member1','kim','안녕하세요 저는 네이버에서 3년을 근무한 인재입니다' ,'3years','11');
INSERT INTO tbl_career(member_identity,name,context,period,history_id) VALUES('member2','hong','안녕하세요 저는 카카오에서 3년을 근무한 인재입니다.' ,'3years','22');
INSERT INTO tbl_career(member_identity,name,context,period,history_id) VALUES('member3','park','안녕하세요 저는 토스에서 3년을 근무한 인재입니다','3years','33');

INSERT INTO tbl_grade(career_id,average,grade,total_score,total_member) VALUES('1',23,'GOLD',23,1);
INSERT INTO tbl_grade(career_id,average,grade,total_score,total_member) VALUES('2',17,'SILVER',17,1);
INSERT INTO tbl_grade(career_id,average,grade,total_score,total_member) VALUES('3',5,'BLACK',5,1);


INSERT INTO tbl_score(career_id, evaluator_identity, attendance,sincerity,positiveness, technical,cooperative) VALUES('3','evaluator1',5,5,5,5,5);
INSERT INTO tbl_score(career_id, evaluator_identity, attendance,sincerity,positiveness, technical,cooperative) VALUES('2','evaluator2',5,5,5,5,5);
INSERT INTO tbl_score(career_id, evaluator_identity, attendance,sincerity,positiveness, technical,cooperative) VALUES('1','evaluator3',5,5,5,5,5);
