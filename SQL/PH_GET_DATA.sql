

--select array_to_json(array_agg(row_to_json(t))) from (
      select 
      case when queensysname like '%0001%' then cid
	when queensysname like '%0003%' then cid+20
	when queensysname like '%0005%' then cid+40
	when queensysname like '%0006%' then cid+60
	when queensysname like '%0007%' then cid+80
	when queensysname like '%0008%' then cid+100
	when queensysname like '%0009%' then cid+120
	when queensysname like '%0010%' then cid+140
	when queensysname like '%0011%' then cid+160
	when queensysname like '%0012%' then cid+180
	when queensysname like '%0013%' then cid+200
	when queensysname like '%0014%' then cid+220
	when queensysname like '%0015%' then cid+240
	when queensysname like '%0016%' then cid+260
	when queensysname like '%0017%' then cid+280
	when queensysname like '%0018%' then cid+300
	when queensysname like '%0019%' then cid+320
      end as circuit_id,
      case when queensysname like '%0001%' then 1 
	when queensysname like '%0003%' then 2
	when queensysname like '%0005%' then 3
	when queensysname like '%0006%' then 4
	when queensysname like '%0007%' then 5
	when queensysname like '%0008%' then 6
	when queensysname like '%0009%' then 7
	when queensysname like '%0010%' then 8
	when queensysname like '%0011%' then 9
	when queensysname like '%0012%' then 10
	when queensysname like '%0013%' then 11
	when queensysname like '%0014%' then 12
	when queensysname like '%0015%' then 13
	when queensysname like '%0016%' then 14
	when queensysname like '%0017%' then 15
	when queensysname like '%0018%' then 16
	when queensysname like '%0019%' then 17
      end as queen_id,

	ttldwh as "dayWhLifetime",
	ttlnwh as "nightWhLifetime",
	dwatts - nwatts as "dayWhToday",
	nwatts as "nightWhToday",
	dwatts as "totalWhToday",
	cr as "creditBalance",
        syncdate as "dataDateTime"
      from queen_usage_log
--    ) t
--where "dataDateTime"::date = '2014-06-01'
where syncdate::date = '2014-05-27'

  