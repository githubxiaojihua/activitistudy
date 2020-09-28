insert into act_id_group(id_,name_) values('admin','管理员');
insert into act_id_group(id_,name_) values('deptLeader','部门经理');
insert into act_id_group(id_,name_) values('generalManager','总经理');
insert into act_id_group(id_,name_) values('hr','人事经理');
--新增角色
insert into act_id_group(id_,name_) values('treasurer','财务人员');
insert into act_id_group(id_,name_) values('cashier','出纳员');
insert into act_id_group(id_,name_) values('supportCrew','后勤人员');


insert into act_id_user(id_,email_,first_,last_,pwd_) values('henry','admin@localhost','Henry','Yan','000000');
insert into act_id_user(id_,email_,first_,last_,pwd_) values('bill','bill@localhost','Bill','Zheng','000000');
insert into act_id_user(id_,email_,first_,last_,pwd_) values('jenny','hr@localhost','Jenny','Luo','000000');

insert into act_id_user(id_,email_,first_,last_,pwd_) values('eric','eric@localhost','Eric','Li','000000');
insert into act_id_user(id_,email_,first_,last_,pwd_) values('tom','tom@localhost','tom','Wang','000000');
insert into act_id_user(id_,email_,first_,last_,pwd_) values('kermit','kermit@localhost','kermit','Miao','000000');
insert into act_id_user(id_,email_,first_,last_,pwd_) values('amy','amy@localhost','Amy','Zhang','000000');
insert into act_id_user(id_,email_,first_,last_,pwd_) values('andy','andy@localhost','andy','Zhao','000000');
-- 新增用户
insert into act_id_user(id_,email_,first_,last_,pwd_) values('tony','tony@localhost','Tony','Zhang','000000');
insert into act_id_user(id_,email_,first_,last_,pwd_) values('thomas','thomas@localhost','Thomas','Zhang','000000');




insert into ACT_ID_MEMBERSHIP (user_id_,group_id_) values('henry','admin');
insert into ACT_ID_MEMBERSHIP (user_id_,group_id_) values('bill','generalManager');
insert into ACT_ID_MEMBERSHIP (user_id_,group_id_) values('jenny','hr');
insert into ACT_ID_MEMBERSHIP (user_id_,group_id_) values('kermit','deptLeader');
insert into ACT_ID_MEMBERSHIP (user_id_,group_id_) values('andy','deptLeader');
--新增关系
insert into ACT_ID_MEMBERSHIP (user_id_,group_id_) values('tony','treasurer');
insert into ACT_ID_MEMBERSHIP (user_id_,group_id_) values('tony','cashier');
insert into ACT_ID_MEMBERSHIP (user_id_,group_id_) values('thomas','supportCrew');
