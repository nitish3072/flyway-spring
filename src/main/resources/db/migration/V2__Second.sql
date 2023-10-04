--
-- PostgreSQL database dump started
--
CREATE TABLE public.org (
    id uuid NOT NULL primary key,
    created bigint,
    name character varying(255),
    email character varying(255),
    ph character varying(255),
    status character varying(255)
);

ALTER TABLE public.org OWNER TO flyway;

--
-- PostgreSQL database dump complete
--

