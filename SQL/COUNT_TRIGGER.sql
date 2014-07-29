	CREATE OR REPLACE FUNCTION count_rows() RETURNS TRIGGER AS
	'
	   BEGIN
	      IF TG_OP = ''INSERT'' THEN
	         UPDATE rowcount
	            SET total_rows = total_rows + 1
	            WHERE table_name = TG_RELNAME;
	      ELSIF TG_OP = ''DELETE'' THEN
	         UPDATE rowcount
	            SET total_rows = total_rows - 1
	            WHERE table_name = TG_RELNAME;
	      END IF;
	      RETURN NULL;
	   END;
	' LANGUAGE plpgsql;
	
	DROP TABLE rowcount;

	CREATE TABLE rowcount
	(
	table_name character varying(255) NOT NULL,
	total_rows numeric,
	CONSTRAINT row_counts_pkey PRIMARY KEY (table_name)
	);

	BEGIN;
	   -- Make sure no rows can be added to webui_usage until we have finished
	   LOCK TABLE webui_usage IN SHARE ROW EXCLUSIVE MODE;
	
	   create TRIGGER countrows
	      AFTER INSERT OR DELETE on webui_usage
	      FOR EACH ROW EXECUTE PROCEDURE count_rows();

	   -- Initialise the row count record
	   DELETE FROM rowcount WHERE table_name = 'webui_usage';

	   INSERT INTO rowcount (table_name, total_rows)
	   VALUES  ('webui_usage',  (SELECT COUNT(*) FROM webui_usage));

	COMMIT;

