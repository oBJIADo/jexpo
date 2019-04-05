select distinct *
from task as tsk
where tsk.KEYS like '%ad-999%'
   or tsk.summary like '%ad-999%'
   or tsk.CREATED like '%ad-999%'
   --
   or tsk.ISSUE_TYPE in (select id
                         from FEATURE as f
                         where f.TITLE like '%ad-999%'
                           and f.NATURE = (select n.id from NATURE as n where n.TITLE = 'issueType'))
   --
   or tsk.id in (select distinct(com.TASK_ID)
                 from COMMENT as com
                 where com.COMMENT_DATE like '%ad-999%'
                    or lower(com.COMMENT) like '%ad-999%'
                    or com.AUTHOR in (select id from employee as emplc where emplc.FIRSTNAME like '%ad-999%'))
   --
   or tsk.id in (select distinct(stask.TASK_ID)
                 from SUB_TASK as stask
                 where stask.SUBTASK_ID in (select id from TASK as tbl where lower(tbl.KEYS) like '%ad-999%'))
   --
   or tsk.id in (select distinct(rtask.TASK_ID)
                 from RELATION_TASK as rtask
                 where rtask.RELATION_TASK_ID in (select id from TASK as tbl where lower(tbl.KEYS) like '%ad-999%'))
   --
   or tsk.id in (select distinct(dtask.TASK_ID)
                 from DUPLICATE_TASK as dtask
                 where dtask.DUPLICATE_TASK_ID in (select id from TASK as tbl where lower(tbl.KEYS) like '%ad-999%'))
   --
   or tsk.CONSUMABLES in (select cons.id
                          from CONSUMABLES as cons
                          where cons.DESCRIPTION like '%ad-999%'
                             or cons.ORDER_NUMBER like '%ad-999%'
                             or cons.DELIVERED_VERSION like '%ad-999%'
                             or cons.DRC_NUMBER like '%ad-999%'
                             or cons.STATUS in (select f.id
                                                from FEATURE as f
                                                where f.TITLE like '%ad-999%'
                                                  and f.NATURE = (select n.id from NATURE as n where n.TITLE = 'status'))
                             --
                             or cons.PRIORITY in (select f.id
                                                  from FEATURE as f
                                                  where f.TITLE like '%ad-999%'
                                                    and f.NATURE = (select n.id from NATURE as n where n.TITLE = 'priority'))
                             --
                             or cons.RESOLUTION in (select f.id
                                                    from FEATURE as f
                                                    where f.TITLE like '%ad-999%'
                                                      and f.NATURE = (select n.id from NATURE as n where n.TITLE = 'resolution'))
                             --
                             or cons.SPRINT in (select f.id
                                                from FEATURE as f
                                                where f.TITLE like '%ad-999%'
                                                  and f.NATURE = (select n.id from NATURE as n where n.TITLE = 'sprint'))
                             --
                             or cons.KEYWORD in (select f.id
                                                 from FEATURE as f
                                                 where f.TITLE like '%ad-999%'
                                                   and f.NATURE = (select n.id from NATURE as n where n.TITLE = 'sprint'))
                             --
                             or cons.FIX_PRIORITY in (select f.id
                                                      from FEATURE as f
                                                      where f.TITLE like '%ad-999%'
                                                        and f.NATURE = (select n.id from NATURE as n where n.TITLE = 'sprint'))
                             --
                             or cons.DATES in (
                              select dat.id
                              from DATES as dat
                              where dat.last_viewed like '%ad-999%'
                                 or dat.updated like '%ad-999%'
                                 or dat.resolved like '%ad-999%'
                                 or dat.due_date like '%ad-999%'
                              )
                             --
                             or cons.EPICS in (
                              select epi.id
                              from EPICS as epi
                              where epi.EPIC_NAME like '%ad-999%'
                                 or epi.EPIC_LINK like '%ad-999%'
                                 or epi.EPIC_COLOR in (select f.id
                                                       from FEATURE as f
                                                       where f.TITLE like '%ad-999%'
                                                         and f.NATURE = (select n.id from NATURE as n where n.TITLE = 'epicColor'))
                                 or epi.EPIC_STATUS in (select f.id
                                                        from FEATURE as f
                                                        where f.TITLE like '%ad-999%'
                                                          and f.NATURE = (select n.id from NATURE as n where n.TITLE = 'epicStatus'))
                              )
                             --
                             or cons.WORKERS in (select wks.id
                                                 from WORKERS as wks
                                                 where wks.assignee in (select id
                                                                        from employee as empl
                                                                        where empl.FIRSTNAME like '%ad-999%'
                                                                           or empl.SECONDNAME like '%ad-999%')
                                                    or wks.reporter in (select id
                                                                        from employee as empl
                                                                        where empl.FIRSTNAME like '%ad-999%'
                                                                           or empl.SECONDNAME like '%ad-999%')
                                                    or wks.creater in (select id
                                                                       from employee as empl
                                                                       where empl.FIRSTNAME like '%ad-999%'
                                                                          or empl.SECONDNAME like '%ad-999%'))
                             --
                             or cons.STATISTICS in (select stat.id
                                                    from STATISTICS as stat
                                                    where stat.original_estimate like '%ad-999%'
                                                       or stat.remaining_estimate like '%ad-999%'
                                                       or stat.time_spent like '%ad-999%'
                                                       or stat.work_ration like '%ad-999%'
                                                       or stat.progress like '%ad-999%'
                                                       or stat.sum_progress like '%ad-999%'
                                                       or stat.sum_time_spant like '%ad-999%'
                                                       or stat.sum_remaining_estimate like '%ad-999%'
                                                       or stat.sum_original_estimate like '%ad-999%'
                              )
                             --
                             or cons.id in (select distinct(afvers.consumables_id)
                                            from sub_affects_version as afvers
                                            where afvers.VERSION_ID in
                                                  (select f.id
                                                   from FEATURE as f
                                                   where f.TITLE like '%ad-999%'
                                                     and f.NATURE = (select n.id from NATURE as n where n.TITLE = 'status')))
                             or cons.id in (select distinct(fxvers.consumables_id)
                                            from task_fix_version as fxvers
                                            where fxvers.VERSION_ID in
                                                  (select f.id
                                                   from FEATURE as f
                                                   where f.TITLE like '%ad-999%'
                                                     and f.NATURE = (select n.id from NATURE as n where n.TITLE = 'status')))
                             or cons.id in (select distinct(tskcomp.consumables_id)
                                            from task_component as tskcomp
                                            where tskcomp.COMPONENT_ID in
                                                  (select f.id
                                                   from FEATURE as f
                                                   where f.TITLE like '%ad-999%'
                                                     and f.NATURE = (select n.id from NATURE as n where n.TITLE = 'status')))
                             or cons.id in (select distinct(tsklbl.consumables_id)
                                            from task_label as tsklbl
                                            where tsklbl.LABEL_ID in
                                                  (select f.id
                                                   from FEATURE as f
                                                   where f.TITLE like '%ad-999%'
                                                     and f.NATURE = (select n.id from NATURE as n where n.TITLE = 'status')))
                             or cons.id in (select distinct(tsktm.consumables_id)
                                            from task_team as tsktm
                                            where tsktm.TEAM_ID in
                                                  (select f.id
                                                   from FEATURE as f
                                                   where f.TITLE like '%ad-999%'
                                                     and f.NATURE = (select n.id from NATURE as n where n.TITLE = 'status'))))
order by tsk.ID;
-- END ---------------------------------------------------------------------------------------------------------------------------------------------------
