select distinct *
  from task as tsk
    where
-- Varchar fields
          lower(tsk.KEYS) like '%ad-999%' or
          lower(tsk.summary) like '%ad-999%' or
          lower(tsk.description) like '%ad-999%' or
          lower(tsk.EPIC_NAME) like '%ad-999%' or
          lower(tsk.EPIC_LINK) like '%ad-999%' or
          lower(tsk.ORDER_NUMBER) like '%ad-999%' or
          lower(tsk.DELIVERED_VERSION) like '%ad-999%' or
          lower(tsk.DRC_NUMBER) like '%ad-999%' or
-- Num fields
          tsk.CREATED like '%ad-999%' or
          tsk.last_viewed like '%ad-999%' or
          tsk.updated like '%ad-999%' or
          tsk.resolved like '%ad-999%' or
          tsk.due_date like '%ad-999%' or
          tsk.original_estimate like '%ad-999%' or
          tsk.remaining_estimate like '%ad-999%' or
          tsk.time_spent like '%ad-999%' or
          tsk.work_ration like '%ad-999%' or
          tsk.progress like '%ad-999%' or
          tsk.sum_progress like '%ad-999%' or
          tsk.sum_time_spant like '%ad-999%' or
          tsk.sum_remaining_estimate like '%ad-999%' or
          tsk.sum_original_estimate like '%ad-999%' or
-- Id's of another one-field tables
          tsk.ISSUE_TYPE in (select id from issueType as isty where isty.PARAM like '%ad-999%') or
          tsk.STATUS in (select id from status as stat where stat.PARAM like '%ad-999%') or
          tsk.PRIORITY in (select id from priority as prio where prio.PARAM like '%ad-999%') or
          tsk.RESOLUTION in (select id from resolution as reso where reso.PARAM like '%ad-999%') or
          tsk.EPICCOLOR in (select id from epicColor as epcol where epcol.PARAM like '%ad-999%') or
          tsk.SPRINT in (select id from sprint as spri where spri.PARAM like '%ad-999%') or
          tsk.KEYWORD in (select id from keyword as keyw where keyw.PARAM like '%ad-999%') or
          tsk.EPIC_STATUS in (select id from STATUS as stat where stat.PARAM like '%ad-999%') or
          tsk.FIX_PRIORITY in (select id from PRIORITY as prior where prior.PARAM like '%ad-999%') or
-- Employees
          tsk.assignee in (select id from employee as empl where empl.FIRSTNAME like '%ad-999%' or
                                                                empl.SECONDNAME like '%ad-999%') or
          tsk.reporter in (select id from employee as empl where empl.FIRSTNAME like '%ad-999%' or
                                                                empl.SECONDNAME like '%ad-999%') or
          tsk.creater in (select id from employee as empl where empl.FIRSTNAME like '%ad-999%' or
                                                                empl.SECONDNAME like '%ad-999%') or
-- Comments
          tsk.id in (select distinct(com.TASK_ID)
                      from COMMENT as com
                        where
                              com.COMMENT_DATE like '%ad-999%' or
                              lower(com.COMMENT) like '%ad-999%' or
                              com.AUTHOR in (select id from employee as emplc where emplc.FIRSTNAME like '%ad-999%')) or
-- Many-to-many links
          tsk.id in (select distinct(afvers.TASK_ID)
                      from sub_affects_version as afvers
                        where afvers.VERSION_ID in (select id from VERSION as vrs where lower(vrs.PARAM) like '%ad-999%')) or
          tsk.id in (select distinct(fxvers.TASK_ID)
                      from task_fix_version as fxvers
                        where fxvers.VERSION_ID in (select id from VERSION as vrs where lower(vrs.PARAM) like '%ad-999%')) or
          tsk.id in (select distinct(tskcomp.TASK_ID)
                      from task_component as tskcomp
                        where tskcomp.COMPONENT_ID in (select id from COMPONENT as comp where lower(comp.PARAM) like '%ad-999%')) or
          tsk.id in (select distinct(tsklbl.TASK_ID)
                      from task_label as tsklbl
                        where tsklbl.LABEL_ID in (select id from LABEL as lbl where lower(lbl.PARAM) like '%ad-999%')) or
          tsk.id in (select distinct(tsktm.TASK_ID)
                      from task_team as tsktm
                        where tsktm.TEAM_ID in (select id from TEAM as tm where lower(tm.PARAM) like '%ad-999%')) or
          tsk.id in (select distinct(subtask.TASK_ID)
                      from SUB_TASK as subtask
                        where subtask.SUBTASK_ID in (select id from TASK as tbl where lower(tbl.KEYS) like '%ad-999%'))
order by tsk.ID;
-- END ---------------------------------------------------------------------------------------------------------------------------------------------------

SELECT * FROM TASK AS TSK
WHERE

TSK.ID IN (SELECT DISTINCT(MTM.TASK_ID)
FROM NULL[*] AS MTM
WHERE MTM.KEYS_ID IN (SELECT ID FROM TASK AS TBL WHERE LOWER(TBL.KEYS) LIKE ?)) OR
TSK.ID IN (SELECT DISTINCT(MTM.TASK_ID)
FROM NULL AS MTM
WHERE MTM.SUMMARY_ID IN (SELECT ID FROM TASK AS TBL WHERE LOWER(TBL.KEYS) LIKE ?))
