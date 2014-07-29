-- used to time inserts --- while running continous inserts into the webui_usage table, run this once, then plug in the dt and inserted into the query. 
-- Then every run will shown the insert rate as long as the insert code is running
Select inserted/secs as insert_rate, *
FROM
--(Select ttlrecs,inserted, EXTRACT(EPOCH FROM AGE(dt, '"2014-05-23 21:26:56.30344+00"')) as secs,dt FROM (SELECT Count(*) as ttlrecs, (Count(*)-3026230) as inserted, now() as dt FROM webui_usage) as a) as b;

(Select ttlrecs,inserted, EXTRACT(EPOCH FROM AGE(dt, '"2014-05-25 20:48:58.106353+00"	')) as secs,dt FROM (SELECT Count(*) as ttlrecs, (Count(*)-7930709) as inserted, now() as dt FROM webui_usage) as a) as b;

--(Select ttlrecs,inserted, EXTRACT(EPOCH FROM AGE(dt, '"2014-05-24 00:57:50.806993+00"')) as secs,dt FROM (SELECT Count(*) as ttlrecs, (Count(*)-3714169) as inserted, now() as dt FROM webui_usage) as a) as b;