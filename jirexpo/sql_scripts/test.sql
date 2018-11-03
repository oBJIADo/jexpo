-- ******* SCRIPT_1 ******** --
select count(distinct tsk.id)
from task as tsk
where
-- Varchar fields
lower(tsk.keys) like '%ad-999%' or
lower(tsk.summary) like '%ad-999%' or
lower(tsk.description) like '%ad-999%' 
-- Num fields
or
tsk.created like '%ad-999%' or
tsk.last_viewed like '%ad-999%' or
tsk.updated like '%ad-999%' 
-- Id's of another one-field tables
or
tsk.issue_type in (select id from issueType as tbl where tbl.PARAM like '%ad-999%') 
-- Employees
or
tsk.assignee in (select id from employee as empl where empl.FIRSTNAME like '%ad-999%' or
empl.SECONDNAME like '%ad-999%') or
tsk.reporter in (select id from employee as empl where empl.FIRSTNAME like '%ad-999%' or
empl.SECONDNAME like '%ad-999%') or
tsk.creater in (select id from employee as empl where empl.FIRSTNAME like '%ad-999%' or
empl.SECONDNAME like '%ad-999%') 
-- Comments
or
tsk.id in (select distinct(com.TASK_ID)
from COMMENT as com
where
com.COMMENT_DATE like '%ad-999%' or
lower(com.COMMENT) like '%ad-999%' or
com.AUTHOR in (select id from employee as emplc where emplc.FIRSTNAME like '%ad-999%'))
-- Many-to-many links
or
tsk.id in (select distinct(mtm.TASK_ID)
from sub_affects_version as mtm
where mtm.VERSION_ID in (select id from VERSION as tm where lower(tm.PARAM) like '%ad-999%')) or
tsk.id in (select distinct(mtm.TASK_ID)
from task_fix_version as mtm
where mtm.VERSION_ID in (select id from VERSION as tm where lower(tm.PARAM) like '%ad-999%')) or
tsk.id in (select distinct(mtm.TASK_ID)
from SUB_TASK as mtm
where mtm.SUBTASK_ID in (select id from TASK as tbl where lower(tbl.KEYS) like '%ad-999%'));
-- ******* SCRIPT_1 ******** --

-- ******* SCRIPT_2 ******** --
select count(distinct tsk.id)
from task as tsk
where
-- Varchar fields
lower(tsk.keys) like '%ad-999%';
-- ******* SCRIPT_2 ******** --