--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: additive_constraint; Type: TABLE; Schema: public; Owner: abahn; Tablespace: 
--

CREATE TABLE additive_constraint (
    con_id bigint NOT NULL,
    con_value double precision
);


--
-- Name: admin_domain; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE admin_domain (
    domain_id character varying(255) NOT NULL,
    asid character varying(255),
    ad_name character varying(255),
    client_domain boolean,
    idcp_server character varying(255)
);


--
-- Name: bod_user; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE bod_user (
    name character varying(255) NOT NULL,
    email character varying(255),
    homedomain character varying(255) NOT NULL
);


--
-- Name: boolean_constraint; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE boolean_constraint (
    con_id bigint NOT NULL,
    con_value boolean,
    logic character varying(255)
);


--
-- Name: domain_constraints; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE domain_constraints (
    dcon_id bigint NOT NULL,
    gcon_id bigint,
    domain_order integer
);


--
-- Name: domain_ids; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE domain_ids (
    gcon_id bigint NOT NULL,
    domain_id character varying(255),
    domain_order integer NOT NULL
);


--
-- Name: eth_link; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE eth_link (
    link_id bigint NOT NULL,
    discovery_protocol character varying(255),
    is_trunk boolean,
    is_l2_bndry boolean,
    native_vlan bigint
);


--
-- Name: eth_logical_port; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE eth_logical_port (
    interface_id bigint NOT NULL
);


--
-- Name: eth_physical_port; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE eth_physical_port (
    interface_id bigint NOT NULL,
    interface_name character varying(255),
    mac_address character varying(255),
    duplex character varying(255),
    medium_dependent_interface character varying(255),
    is_tagged boolean
);


--
-- Name: generic_connection; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE generic_connection (
    generic_connection_id bigint NOT NULL,
    version_id bigint,
    path_id bigint,
    link_id bigint,
    direction character varying(255),
    connection_type character varying(255),
    bandwidth double precision
);


--
-- Name: generic_interface; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE generic_interface (
    interface_id bigint NOT NULL,
    version_id bigint,
    interface_type_id bigint,
    parent_interface_id bigint,
    node_id bigint,
    name character varying(255),
    description character varying(255),
    bandwidth bigint,
    status character varying(255),
    mtu integer NOT NULL,
    domain_id character varying(255),
    client_port boolean
);


--
-- Name: generic_link; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE generic_link (
    link_id bigint NOT NULL,
    version_id bigint,
    start_interface_id bigint,
    end_interface_id bigint,
    direction character varying(255),
    protection boolean,
    prop_delay double precision
);


--
-- Name: glink_to_intrapath; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE glink_to_intrapath (
    path_id bigint NOT NULL,
    glink_id bigint NOT NULL,
    link_order integer NOT NULL
);


--
-- Name: global_constraints; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE global_constraints (
    gcon_id bigint NOT NULL
);


--
-- Name: has_role; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE has_role (
    node_id character varying(255) NOT NULL,
    link_id character varying(255) NOT NULL,
    demarc boolean
);


--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- Name: ho_vc_group; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE ho_vc_group (
    ho_vc_group_id bigint NOT NULL,
    vlan_tag_id bigint,
    group_name character varying(255)
);


--
-- Name: ho_vc_link; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE ho_vc_link (
    ho_vc_link_id bigint NOT NULL,
    ho_vc_group_id bigint,
    stm_link_id bigint,
    ho_vc_type_id bigint,
    time_slot bigint,
    date_modified date,
    group_sequence bigint,
    status character varying(255)
);


--
-- Name: ho_vc_type; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE ho_vc_type (
    ho_vc_type_id bigint NOT NULL,
    type_name character varying(255),
    bandwidth bigint,
    payload bigint
);


--
-- Name: interdomain_node; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE interdomain_node (
    node_id character varying(255) NOT NULL,
    type character varying(255),
    address character varying(255),
    name character varying(255),
    country character varying(255),
    city character varying(255),
    institution character varying(255),
    longitude character varying(255),
    latitude character varying(255),
    prov_domain character varying(255) NOT NULL
);


--
-- Name: interdomain_path; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE interdomain_path (
    path_id bigint NOT NULL,
    monetary_cost double precision,
    manual_cost double precision
);


--
-- Name: interface_type; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE interface_type (
    interface_type_id bigint NOT NULL,
    switching_type character varying(255),
    data_encoding_type character varying(255)
);


--
-- Name: intradomain_path; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE intradomain_path (
    path_id bigint NOT NULL,
    capacity bigint
);


--
-- Name: intradomain_reservation; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE intradomain_reservation (
    res_id character varying(255) NOT NULL,
    created boolean,
    active boolean,
    path_id bigint,
    params_id bigint
);


--
-- Name: link; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE link (
    link_id character varying(255) NOT NULL,
    kind integer,
    start_port character varying(255) NOT NULL,
    end_port character varying(255) NOT NULL,
    bidirectional boolean,
    delay integer,
    manual_cost double precision,
    monetary_cost double precision,
    granularity bigint,
    min_res_capacity bigint,
    max_res_capacity bigint,
    capacity bigint,
    resilience character varying(255),
    state_oper integer,
    state_admin integer,
    link_type integer,
    local_name character varying(255),
    l_time timestamp without time zone
);


--
-- Name: link_to_path; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE link_to_path (
    path_id bigint NOT NULL,
    link_id character varying(255) NOT NULL,
    link_order integer NOT NULL
);


--
-- Name: link_type; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE link_type (
    id integer NOT NULL,
    description character varying(255)
);


--
-- Name: location; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE location (
    location_id bigint NOT NULL,
    name character varying(255),
    description character varying(255),
    country character varying(255),
    institution character varying(255),
    street character varying(255),
    floor integer,
    room_suite integer,
    row_ integer,
    cabinet integer,
    zip_code character varying(255),
    phone_number character varying(255),
    e_mail_address character varying(255),
    geo_latitude double precision,
    geo_longitude double precision,
    type character varying(255),
    zone character varying(255),
    altitude double precision
);


--
-- Name: minval_constraint; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE minval_constraint (
    con_id bigint NOT NULL,
    con_value double precision
);


--
-- Name: mpls_link; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE mpls_link (
    link_id bigint NOT NULL
);


--
-- Name: network_constraint; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE network_constraint (
    con_id bigint NOT NULL
);


--
-- Name: node; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE node (
    node_id bigint NOT NULL,
    version_id bigint,
    location_id bigint,
    name character varying(255),
    description character varying(255),
    status character varying(255),
    vendor character varying(255),
    model character varying(255),
    os_name character varying(255),
    os_version character varying(255),
    ip_address character varying(255),
    vlan_translation boolean
);


--
-- Name: och; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE och (
    och_id bigint NOT NULL,
    payload character varying(255),
    status character varying(255)
);


--
-- Name: och_link; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE och_link (
    och_link_id bigint NOT NULL,
    ops_link_id bigint,
    och_id bigint,
    frequency double precision,
    status character varying(255)
);


--
-- Name: ops_link; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE ops_link (
    ops_link_id bigint NOT NULL,
    max_no_lambdas bigint,
    bitrate bigint,
    status character varying(255)
);


--
-- Name: path; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE path (
    path_id bigint NOT NULL,
    version_id bigint,
    name character varying(255),
    status character varying(255)
);


--
-- Name: path_constraint; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE path_constraint (
    pcon_id bigint NOT NULL,
    dcon_id bigint,
    path_order integer
);


--
-- Name: pcon_add_constraints; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE pcon_add_constraints (
    pcon_id bigint NOT NULL,
    constraint_id bigint NOT NULL,
    constraint_order integer NOT NULL
);


--
-- Name: pcon_add_names; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE pcon_add_names (
    pcon_id bigint NOT NULL,
    name smallint,
    constraint_order integer NOT NULL
);


--
-- Name: pcon_bool_constraints; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE pcon_bool_constraints (
    pcon_id bigint NOT NULL,
    constraint_id bigint NOT NULL,
    constraint_order integer NOT NULL
);


--
-- Name: pcon_bool_names; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE pcon_bool_names (
    pcon_id bigint NOT NULL,
    name smallint,
    constraint_order integer NOT NULL
);


--
-- Name: pcon_minval_constraints; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE pcon_minval_constraints (
    pcon_id bigint NOT NULL,
    constraint_id bigint NOT NULL,
    constraint_order integer NOT NULL
);


--
-- Name: pcon_minval_names; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE pcon_minval_names (
    pcon_id bigint NOT NULL,
    name smallint,
    constraint_order integer NOT NULL
);


--
-- Name: pcon_range_constraints; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE pcon_range_constraints (
    pcon_id bigint NOT NULL,
    constraint_id bigint NOT NULL,
    constraint_order integer NOT NULL
);


--
-- Name: pcon_range_names; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE pcon_range_names (
    pcon_id bigint NOT NULL,
    name smallint,
    constraint_order integer NOT NULL
);


--
-- Name: pcons_to_intrapath; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE pcons_to_intrapath (
    path_id bigint NOT NULL,
    pcon_id bigint NOT NULL,
    glink_map_id bigint NOT NULL
);


--
-- Name: port; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE port (
    port_id character varying(255) NOT NULL,
    description character varying(255),
    technology character varying(255),
    bundled boolean,
    node character varying(255) NOT NULL
);


--
-- Name: provisioning_domain; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE provisioning_domain (
    provdomain_id character varying(255) NOT NULL,
    prov_type character varying(255),
    admin_domain character varying(255) NOT NULL
);



--
-- Name: range; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE range (
    range_id bigint NOT NULL,
    min integer,
    max integer,
    con_id bigint NOT NULL,
    link_order integer
);



--
-- Name: range_constraint; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE range_constraint (
    con_id bigint NOT NULL
);



--
-- Name: reservation; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE reservation (
    res_id character varying(255) NOT NULL,
    res_type character varying(255) NOT NULL,
    state integer,
    startport character varying(255) NOT NULL,
    endport character varying(255) NOT NULL,
    starttime timestamp without time zone,
    endtime timestamp without time zone,
    priority integer,
    capacity bigint,
    description character varying(255),
    maxdelay integer,
    resiliency character varying(255),
    bidirectional boolean,
    mtu integer,
    state_oper_enum integer,
    globalconstraints bigint,
    path bigint,
    authparameters bigint,
    srv_id character varying(255),
    res_index integer
);



--
-- Name: reservation_params; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE reservation_params (
    id bigint NOT NULL,
    capacity bigint,
    maxdelay integer,
    resiliency character varying(255),
    bidirectional boolean,
    starttime timestamp without time zone,
    endtime timestamp without time zone,
    mtu integer,
    pathconstraintsingress bigint NOT NULL,
    pathconstraintsegress bigint NOT NULL,
    authparameters bigint
);



--
-- Name: sdh_device; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE sdh_device (
    node_id bigint NOT NULL,
    sdh_domain_id bigint,
    name character varying(255),
    nsap bigint
);



--
-- Name: sdh_domain; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE sdh_domain (
    sdh_domain_id bigint NOT NULL,
    domain_name character varying(255),
    prov_method character varying(255),
    equipment_provider character varying(255),
    date_modified date
);



--
-- Name: sdh_port; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE sdh_port (
    port_id bigint NOT NULL,
    address character varying(255),
    phy_port_type character varying(255)
);



--
-- Name: service; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE service (
    srv_id character varying(255) NOT NULL,
    justification character varying(255),
    priority integer,
    bod_user character varying(255) NOT NULL
);



--
-- Name: spanning_tree; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE spanning_tree (
    link_id bigint NOT NULL,
    vlan_id bigint NOT NULL,
    state character varying(255),
    cost bigint
);



--
-- Name: state_admin; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE state_admin (
    id integer NOT NULL,
    description character varying(255)
);



--
-- Name: state_oper; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE state_oper (
    id integer NOT NULL,
    description character varying(255)
);



--
-- Name: statistics; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE statistics (
    id integer NOT NULL,
    reservation_id character varying(255),
    intradomain boolean,
    setup_time bigint
);



--
-- Name: statistics_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE statistics_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



--
-- Name: stm_link; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE stm_link (
    stm_link_id bigint NOT NULL,
    och_id bigint,
    stm_type_id bigint,
    status character varying(255)
);



--
-- Name: stm_type; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE stm_type (
    stm_type_id bigint NOT NULL,
    type_name character varying(255),
    bandwidth bigint
);



--
-- Name: user_auth_params; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE user_auth_params (
    id bigint NOT NULL,
    organization character varying(255),
    projectmembership character varying(255),
    projectrole character varying(255),
    email character varying(255),
    identifier character varying(255)
);



--
-- Name: version_info; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE version_info (
    version_id bigint NOT NULL,
    start_date date,
    end_date date,
    created_by character varying(255),
    modified_by character varying(255),
    date_created date,
    date_modified date
);



--
-- Name: vlan; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE vlan (
    vlan_id bigint NOT NULL,
    vtp_domain_id bigint,
    name character varying(255),
    low_number bigint,
    high_number bigint
);



--
-- Name: vlan_port; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE vlan_port (
    interface_id bigint NOT NULL,
    vlan_id bigint
);



--
-- Name: vlan_tag; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE vlan_tag (
    vlan_tag_id bigint NOT NULL,
    payload character varying(255),
    date_modified date
);



--
-- Name: vtp_domain; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE vtp_domain (
    vtp_domain_id bigint NOT NULL,
    name character varying(255),
    srv_ipv4_addr character varying(255)
);



--
-- Name: additive_constraint_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY additive_constraint
    ADD CONSTRAINT additive_constraint_pkey PRIMARY KEY (con_id);


--
-- Name: admin_domain_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY admin_domain
    ADD CONSTRAINT admin_domain_pkey PRIMARY KEY (domain_id);


--
-- Name: bod_user_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY bod_user
    ADD CONSTRAINT bod_user_pkey PRIMARY KEY (name);


--
-- Name: boolean_constraint_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY boolean_constraint
    ADD CONSTRAINT boolean_constraint_pkey PRIMARY KEY (con_id);


--
-- Name: domain_constraints_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY domain_constraints
    ADD CONSTRAINT domain_constraints_pkey PRIMARY KEY (dcon_id);


--
-- Name: domain_ids_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY domain_ids
    ADD CONSTRAINT domain_ids_pkey PRIMARY KEY (gcon_id, domain_order);


--
-- Name: eth_link_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY eth_link
    ADD CONSTRAINT eth_link_pkey PRIMARY KEY (link_id);


--
-- Name: eth_logical_port_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY eth_logical_port
    ADD CONSTRAINT eth_logical_port_pkey PRIMARY KEY (interface_id);


--
-- Name: eth_physical_port_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY eth_physical_port
    ADD CONSTRAINT eth_physical_port_pkey PRIMARY KEY (interface_id);


--
-- Name: generic_connection_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY generic_connection
    ADD CONSTRAINT generic_connection_pkey PRIMARY KEY (generic_connection_id);


--
-- Name: generic_interface_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY generic_interface
    ADD CONSTRAINT generic_interface_pkey PRIMARY KEY (interface_id);


--
-- Name: generic_link_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY generic_link
    ADD CONSTRAINT generic_link_pkey PRIMARY KEY (link_id);


--
-- Name: glink_to_intrapath_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY glink_to_intrapath
    ADD CONSTRAINT glink_to_intrapath_pkey PRIMARY KEY (path_id, link_order);


--
-- Name: global_constraints_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY global_constraints
    ADD CONSTRAINT global_constraints_pkey PRIMARY KEY (gcon_id);


--
-- Name: has_role_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY has_role
    ADD CONSTRAINT has_role_pkey PRIMARY KEY (node_id, link_id);


--
-- Name: ho_vc_group_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY ho_vc_group
    ADD CONSTRAINT ho_vc_group_pkey PRIMARY KEY (ho_vc_group_id);


--
-- Name: ho_vc_link_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY ho_vc_link
    ADD CONSTRAINT ho_vc_link_pkey PRIMARY KEY (ho_vc_link_id);


--
-- Name: ho_vc_type_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY ho_vc_type
    ADD CONSTRAINT ho_vc_type_pkey PRIMARY KEY (ho_vc_type_id);


--
-- Name: interdomain_node_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY interdomain_node
    ADD CONSTRAINT interdomain_node_pkey PRIMARY KEY (node_id);


--
-- Name: interdomain_path_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY interdomain_path
    ADD CONSTRAINT interdomain_path_pkey PRIMARY KEY (path_id);


--
-- Name: interface_type_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY interface_type
    ADD CONSTRAINT interface_type_pkey PRIMARY KEY (interface_type_id);


--
-- Name: intradomain_path_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY intradomain_path
    ADD CONSTRAINT intradomain_path_pkey PRIMARY KEY (path_id);


--
-- Name: intradomain_reservation_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY intradomain_reservation
    ADD CONSTRAINT intradomain_reservation_pkey PRIMARY KEY (res_id);


--
-- Name: link_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY link
    ADD CONSTRAINT link_pkey PRIMARY KEY (link_id);


--
-- Name: link_to_path_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY link_to_path
    ADD CONSTRAINT link_to_path_pkey PRIMARY KEY (path_id, link_order);


--
-- Name: link_type_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY link_type
    ADD CONSTRAINT link_type_pkey PRIMARY KEY (id);


--
-- Name: location_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY location
    ADD CONSTRAINT location_pkey PRIMARY KEY (location_id);


--
-- Name: minval_constraint_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY minval_constraint
    ADD CONSTRAINT minval_constraint_pkey PRIMARY KEY (con_id);


--
-- Name: mpls_link_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY mpls_link
    ADD CONSTRAINT mpls_link_pkey PRIMARY KEY (link_id);


--
-- Name: network_constraint_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY network_constraint
    ADD CONSTRAINT network_constraint_pkey PRIMARY KEY (con_id);


--
-- Name: node_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY node
    ADD CONSTRAINT node_pkey PRIMARY KEY (node_id);


--
-- Name: och_link_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY och_link
    ADD CONSTRAINT och_link_pkey PRIMARY KEY (och_link_id);


--
-- Name: och_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY och
    ADD CONSTRAINT och_pkey PRIMARY KEY (och_id);


--
-- Name: ops_link_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY ops_link
    ADD CONSTRAINT ops_link_pkey PRIMARY KEY (ops_link_id);


--
-- Name: path_constraint_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY path_constraint
    ADD CONSTRAINT path_constraint_pkey PRIMARY KEY (pcon_id);


--
-- Name: path_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY path
    ADD CONSTRAINT path_pkey PRIMARY KEY (path_id);


--
-- Name: pcon_add_constraints_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY pcon_add_constraints
    ADD CONSTRAINT pcon_add_constraints_pkey PRIMARY KEY (pcon_id, constraint_order);


--
-- Name: pcon_add_names_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY pcon_add_names
    ADD CONSTRAINT pcon_add_names_pkey PRIMARY KEY (pcon_id, constraint_order);


--
-- Name: pcon_bool_constraints_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY pcon_bool_constraints
    ADD CONSTRAINT pcon_bool_constraints_pkey PRIMARY KEY (pcon_id, constraint_order);


--
-- Name: pcon_bool_names_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY pcon_bool_names
    ADD CONSTRAINT pcon_bool_names_pkey PRIMARY KEY (pcon_id, constraint_order);


--
-- Name: pcon_minval_constraints_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY pcon_minval_constraints
    ADD CONSTRAINT pcon_minval_constraints_pkey PRIMARY KEY (pcon_id, constraint_order);


--
-- Name: pcon_minval_names_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY pcon_minval_names
    ADD CONSTRAINT pcon_minval_names_pkey PRIMARY KEY (pcon_id, constraint_order);


--
-- Name: pcon_range_constraints_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY pcon_range_constraints
    ADD CONSTRAINT pcon_range_constraints_pkey PRIMARY KEY (pcon_id, constraint_order);


--
-- Name: pcon_range_names_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY pcon_range_names
    ADD CONSTRAINT pcon_range_names_pkey PRIMARY KEY (pcon_id, constraint_order);


--
-- Name: pcons_to_intrapath_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY pcons_to_intrapath
    ADD CONSTRAINT pcons_to_intrapath_pkey PRIMARY KEY (path_id, glink_map_id);


--
-- Name: port_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY port
    ADD CONSTRAINT port_pkey PRIMARY KEY (port_id);


--
-- Name: provisioning_domain_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY provisioning_domain
    ADD CONSTRAINT provisioning_domain_pkey PRIMARY KEY (provdomain_id);


--
-- Name: range_constraint_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY range_constraint
    ADD CONSTRAINT range_constraint_pkey PRIMARY KEY (con_id);


--
-- Name: range_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY range
    ADD CONSTRAINT range_pkey PRIMARY KEY (range_id);


--
-- Name: reservation_params_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY reservation_params
    ADD CONSTRAINT reservation_params_pkey PRIMARY KEY (id);


--
-- Name: reservation_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY reservation
    ADD CONSTRAINT reservation_pkey PRIMARY KEY (res_id);


--
-- Name: sdh_device_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY sdh_device
    ADD CONSTRAINT sdh_device_pkey PRIMARY KEY (node_id);


--
-- Name: sdh_domain_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY sdh_domain
    ADD CONSTRAINT sdh_domain_pkey PRIMARY KEY (sdh_domain_id);


--
-- Name: sdh_port_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY sdh_port
    ADD CONSTRAINT sdh_port_pkey PRIMARY KEY (port_id);


--
-- Name: service_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY service
    ADD CONSTRAINT service_pkey PRIMARY KEY (srv_id);


--
-- Name: spanning_tree_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY spanning_tree
    ADD CONSTRAINT spanning_tree_pkey PRIMARY KEY (link_id, vlan_id);


--
-- Name: state_admin_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY state_admin
    ADD CONSTRAINT state_admin_pkey PRIMARY KEY (id);


--
-- Name: state_oper_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY state_oper
    ADD CONSTRAINT state_oper_pkey PRIMARY KEY (id);


--
-- Name: statistics_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY statistics
    ADD CONSTRAINT statistics_pkey PRIMARY KEY (id);


--
-- Name: stm_link_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY stm_link
    ADD CONSTRAINT stm_link_pkey PRIMARY KEY (stm_link_id);


--
-- Name: stm_type_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY stm_type
    ADD CONSTRAINT stm_type_pkey PRIMARY KEY (stm_type_id);


--
-- Name: user_auth_params_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY user_auth_params
    ADD CONSTRAINT user_auth_params_pkey PRIMARY KEY (id);


--
-- Name: version_info_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY version_info
    ADD CONSTRAINT version_info_pkey PRIMARY KEY (version_id);


--
-- Name: vlan_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY vlan
    ADD CONSTRAINT vlan_pkey PRIMARY KEY (vlan_id);


--
-- Name: vlan_port_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY vlan_port
    ADD CONSTRAINT vlan_port_pkey PRIMARY KEY (interface_id);


--
-- Name: vlan_tag_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY vlan_tag
    ADD CONSTRAINT vlan_tag_pkey PRIMARY KEY (vlan_tag_id);


--
-- Name: vtp_domain_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY vtp_domain
    ADD CONSTRAINT vtp_domain_pkey PRIMARY KEY (vtp_domain_id);


--
-- Name: fk10ab1424b06469e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY link_to_path
    ADD CONSTRAINT fk10ab1424b06469e FOREIGN KEY (link_id) REFERENCES link(link_id);


--
-- Name: fk10ab1424d14d52be; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY link_to_path
    ADD CONSTRAINT fk10ab1424d14d52be FOREIGN KEY (path_id) REFERENCES interdomain_path(path_id);


--
-- Name: fk17d3c8d5988bff1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY minval_constraint
    ADD CONSTRAINT fk17d3c8d5988bff1 FOREIGN KEY (con_id) REFERENCES network_constraint(con_id);


--
-- Name: fk18bbf6611367648f; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY interdomain_node
    ADD CONSTRAINT fk18bbf6611367648f FOREIGN KEY (prov_domain) REFERENCES provisioning_domain(provdomain_id);


--
-- Name: fk2328d7ac819c8388; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY reservation
    ADD CONSTRAINT fk2328d7ac819c8388 FOREIGN KEY (startport) REFERENCES port(port_id);


--
-- Name: fk2328d7ac8262ff12; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY reservation
    ADD CONSTRAINT fk2328d7ac8262ff12 FOREIGN KEY (srv_id) REFERENCES service(srv_id);


--
-- Name: fk2328d7ac839bdbff; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY reservation
    ADD CONSTRAINT fk2328d7ac839bdbff FOREIGN KEY (state_oper_enum) REFERENCES state_oper(id);


--
-- Name: fk2328d7ac900b4caa; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY reservation
    ADD CONSTRAINT fk2328d7ac900b4caa FOREIGN KEY (globalconstraints) REFERENCES global_constraints(gcon_id);


--
-- Name: fk2328d7aca0ba6381; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY reservation
    ADD CONSTRAINT fk2328d7aca0ba6381 FOREIGN KEY (endport) REFERENCES port(port_id);


--
-- Name: fk2328d7acae576e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY reservation
    ADD CONSTRAINT fk2328d7acae576e FOREIGN KEY (path) REFERENCES interdomain_path(path_id);


--
-- Name: fk2328d7acfa2cb018; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY reservation
    ADD CONSTRAINT fk2328d7acfa2cb018 FOREIGN KEY (authparameters) REFERENCES user_auth_params(id);


--
-- Name: fk23a7fa1b614317; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY link
    ADD CONSTRAINT fk23a7fa1b614317 FOREIGN KEY (state_oper) REFERENCES state_oper(id);


--
-- Name: fk23a7fa1d3f07d7; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY link
    ADD CONSTRAINT fk23a7fa1d3f07d7 FOREIGN KEY (link_type) REFERENCES link_type(id);


--
-- Name: fk23a7fa4f31df83; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY link
    ADD CONSTRAINT fk23a7fa4f31df83 FOREIGN KEY (state_admin) REFERENCES state_admin(id);


--
-- Name: fk23a7fa6752410a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY link
    ADD CONSTRAINT fk23a7fa6752410a FOREIGN KEY (end_port) REFERENCES port(port_id);


--
-- Name: fk23a7faa2b421e3; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY link
    ADD CONSTRAINT fk23a7faa2b421e3 FOREIGN KEY (start_port) REFERENCES port(port_id);


--
-- Name: fk24a6028835554d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY node
    ADD CONSTRAINT fk24a6028835554d FOREIGN KEY (location_id) REFERENCES location(location_id);


--
-- Name: fk24a6028ee852f5; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY node
    ADD CONSTRAINT fk24a6028ee852f5 FOREIGN KEY (version_id) REFERENCES version_info(version_id);


--
-- Name: fk255c258ee852f5; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY path
    ADD CONSTRAINT fk255c258ee852f5 FOREIGN KEY (version_id) REFERENCES version_info(version_id);


--
-- Name: fk259081aceb28; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY port
    ADD CONSTRAINT fk259081aceb28 FOREIGN KEY (node) REFERENCES interdomain_node(node_id);


--
-- Name: fk283d6398f12658; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY vlan
    ADD CONSTRAINT fk283d6398f12658 FOREIGN KEY (vtp_domain_id) REFERENCES vtp_domain(vtp_domain_id);


--
-- Name: fk296ff987707e78d1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY ops_link
    ADD CONSTRAINT fk296ff987707e78d1 FOREIGN KEY (ops_link_id) REFERENCES generic_link(link_id);


--
-- Name: fk2d4562374019ac92; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY path_constraint
    ADD CONSTRAINT fk2d4562374019ac92 FOREIGN KEY (dcon_id) REFERENCES domain_constraints(dcon_id);


--
-- Name: fk38cd171b7e81299e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY has_role
    ADD CONSTRAINT fk38cd171b7e81299e FOREIGN KEY (node_id) REFERENCES interdomain_node(node_id);


--
-- Name: fk38cd171bb06469e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY has_role
    ADD CONSTRAINT fk38cd171bb06469e FOREIGN KEY (link_id) REFERENCES link(link_id);


--
-- Name: fk3a5c01ce71c78646; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY pcon_bool_constraints
    ADD CONSTRAINT fk3a5c01ce71c78646 FOREIGN KEY (constraint_id) REFERENCES boolean_constraint(con_id);


--
-- Name: fk3a5c01ce8a96b69d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY pcon_bool_constraints
    ADD CONSTRAINT fk3a5c01ce8a96b69d FOREIGN KEY (pcon_id) REFERENCES path_constraint(pcon_id);


--
-- Name: fk3b667fa9833fbb6e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sdh_port
    ADD CONSTRAINT fk3b667fa9833fbb6e FOREIGN KEY (port_id) REFERENCES generic_interface(interface_id);


--
-- Name: fk3de1469d6257222c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY pcons_to_intrapath
    ADD CONSTRAINT fk3de1469d6257222c FOREIGN KEY (path_id) REFERENCES intradomain_path(path_id);


--
-- Name: fk3de1469d6bed35da; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY pcons_to_intrapath
    ADD CONSTRAINT fk3de1469d6bed35da FOREIGN KEY (glink_map_id) REFERENCES generic_link(link_id);


--
-- Name: fk3de1469d8a96b69d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY pcons_to_intrapath
    ADD CONSTRAINT fk3de1469d8a96b69d FOREIGN KEY (pcon_id) REFERENCES path_constraint(pcon_id);


--
-- Name: fk3f60b3143af08f99; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY ho_vc_link
    ADD CONSTRAINT fk3f60b3143af08f99 FOREIGN KEY (ho_vc_group_id) REFERENCES ho_vc_group(ho_vc_group_id);


--
-- Name: fk3f60b31462bf895b; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY ho_vc_link
    ADD CONSTRAINT fk3f60b31462bf895b FOREIGN KEY (ho_vc_type_id) REFERENCES ho_vc_type(ho_vc_type_id);


--
-- Name: fk3f60b314a93a78cc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY ho_vc_link
    ADD CONSTRAINT fk3f60b314a93a78cc FOREIGN KEY (stm_link_id) REFERENCES stm_link(stm_link_id);


--
-- Name: fk41f156562113f503; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY provisioning_domain
    ADD CONSTRAINT fk41f156562113f503 FOREIGN KEY (admin_domain) REFERENCES admin_domain(domain_id);


--
-- Name: fk4a2411da0ddfc56; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY range
    ADD CONSTRAINT fk4a2411da0ddfc56 FOREIGN KEY (con_id) REFERENCES range_constraint(con_id);


--
-- Name: fk4dfb302b8a96b69d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY pcon_add_constraints
    ADD CONSTRAINT fk4dfb302b8a96b69d FOREIGN KEY (pcon_id) REFERENCES path_constraint(pcon_id);


--
-- Name: fk4dfb302be2a576a2; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY pcon_add_constraints
    ADD CONSTRAINT fk4dfb302be2a576a2 FOREIGN KEY (constraint_id) REFERENCES additive_constraint(con_id);


--
-- Name: fk564d9cafd9e5f55e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY mpls_link
    ADD CONSTRAINT fk564d9cafd9e5f55e FOREIGN KEY (link_id) REFERENCES generic_link(link_id);


--
-- Name: fk573948fd8a96b69d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY pcon_add_names
    ADD CONSTRAINT fk573948fd8a96b69d FOREIGN KEY (pcon_id) REFERENCES path_constraint(pcon_id);


--
-- Name: fk5b676bb38a96b69d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY pcon_minval_constraints
    ADD CONSTRAINT fk5b676bb38a96b69d FOREIGN KEY (pcon_id) REFERENCES path_constraint(pcon_id);


--
-- Name: fk5b676bb3d198a555; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY pcon_minval_constraints
    ADD CONSTRAINT fk5b676bb3d198a555 FOREIGN KEY (constraint_id) REFERENCES minval_constraint(con_id);


--
-- Name: fk6130883f21cdd1a1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY spanning_tree
    ADD CONSTRAINT fk6130883f21cdd1a1 FOREIGN KEY (vlan_id) REFERENCES vlan(vlan_id);


--
-- Name: fk6130883f7d93788c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY spanning_tree
    ADD CONSTRAINT fk6130883f7d93788c FOREIGN KEY (link_id) REFERENCES eth_link(link_id);


--
-- Name: fk66f9a865239669d3; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY och_link
    ADD CONSTRAINT fk66f9a865239669d3 FOREIGN KEY (och_id) REFERENCES och(och_id);


--
-- Name: fk66f9a8652404c0d8; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY och_link
    ADD CONSTRAINT fk66f9a8652404c0d8 FOREIGN KEY (ops_link_id) REFERENCES ops_link(ops_link_id);


--
-- Name: fk67d894913d1567ad; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY generic_interface
    ADD CONSTRAINT fk67d894913d1567ad FOREIGN KEY (node_id) REFERENCES node(node_id);


--
-- Name: fk67d89491576c337a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY generic_interface
    ADD CONSTRAINT fk67d89491576c337a FOREIGN KEY (interface_type_id) REFERENCES interface_type(interface_type_id);


--
-- Name: fk67d894918ee852f5; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY generic_interface
    ADD CONSTRAINT fk67d894918ee852f5 FOREIGN KEY (version_id) REFERENCES version_info(version_id);


--
-- Name: fk67d89491c59b512b; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY generic_interface
    ADD CONSTRAINT fk67d89491c59b512b FOREIGN KEY (parent_interface_id) REFERENCES generic_interface(interface_id);


--
-- Name: fk6eaa323f5988bff1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY range_constraint
    ADD CONSTRAINT fk6eaa323f5988bff1 FOREIGN KEY (con_id) REFERENCES network_constraint(con_id);


--
-- Name: fk7c35c5821fcc6073; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY generic_link
    ADD CONSTRAINT fk7c35c5821fcc6073 FOREIGN KEY (start_interface_id) REFERENCES generic_interface(interface_id);


--
-- Name: fk7c35c5824648689a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY generic_link
    ADD CONSTRAINT fk7c35c5824648689a FOREIGN KEY (end_interface_id) REFERENCES generic_interface(interface_id);


--
-- Name: fk7c35c5828ee852f5; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY generic_link
    ADD CONSTRAINT fk7c35c5828ee852f5 FOREIGN KEY (version_id) REFERENCES version_info(version_id);


--
-- Name: fk882e936f61e80e60; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY intradomain_reservation
    ADD CONSTRAINT fk882e936f61e80e60 FOREIGN KEY (params_id) REFERENCES reservation_params(id);


--
-- Name: fk882e936f6257222c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY intradomain_reservation
    ADD CONSTRAINT fk882e936f6257222c FOREIGN KEY (path_id) REFERENCES intradomain_path(path_id);


--
-- Name: fk8a74d0e08a96b69d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY pcon_bool_names
    ADD CONSTRAINT fk8a74d0e08a96b69d FOREIGN KEY (pcon_id) REFERENCES path_constraint(pcon_id);


--
-- Name: fk9250864d1b9c6e4b; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stm_link
    ADD CONSTRAINT fk9250864d1b9c6e4b FOREIGN KEY (stm_link_id) REFERENCES generic_link(link_id);


--
-- Name: fk9250864d239669d3; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stm_link
    ADD CONSTRAINT fk9250864d239669d3 FOREIGN KEY (och_id) REFERENCES och(och_id);


--
-- Name: fk9250864d6bd730cc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stm_link
    ADD CONSTRAINT fk9250864d6bd730cc FOREIGN KEY (stm_type_id) REFERENCES stm_type(stm_type_id);


--
-- Name: fk94860173971e13b2; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bod_user
    ADD CONSTRAINT fk94860173971e13b2 FOREIGN KEY (homedomain) REFERENCES admin_domain(domain_id);


--
-- Name: fk957f5979778518db; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY reservation_params
    ADD CONSTRAINT fk957f5979778518db FOREIGN KEY (pathconstraintsegress) REFERENCES path_constraint(pcon_id);


--
-- Name: fk957f5979f1a229d9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY reservation_params
    ADD CONSTRAINT fk957f5979f1a229d9 FOREIGN KEY (pathconstraintsingress) REFERENCES path_constraint(pcon_id);


--
-- Name: fk957f5979fa2cb018; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY reservation_params
    ADD CONSTRAINT fk957f5979fa2cb018 FOREIGN KEY (authparameters) REFERENCES user_auth_params(id);


--
-- Name: fk984c94305988bff1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY additive_constraint
    ADD CONSTRAINT fk984c94305988bff1 FOREIGN KEY (con_id) REFERENCES network_constraint(con_id);


--
-- Name: fk9ec5f1dd21cdd1a1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY vlan_port
    ADD CONSTRAINT fk9ec5f1dd21cdd1a1 FOREIGN KEY (vlan_id) REFERENCES vlan(vlan_id);


--
-- Name: fk9ec5f1ddee320bb6; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY vlan_port
    ADD CONSTRAINT fk9ec5f1ddee320bb6 FOREIGN KEY (interface_id) REFERENCES generic_interface(interface_id);


--
-- Name: fka10f52952a6bc364; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY service
    ADD CONSTRAINT fka10f52952a6bc364 FOREIGN KEY (bod_user) REFERENCES bod_user(name);


--
-- Name: fkac7356852f8fa6b7; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY ho_vc_group
    ADD CONSTRAINT fkac7356852f8fa6b7 FOREIGN KEY (ho_vc_group_id) REFERENCES generic_connection(generic_connection_id);


--
-- Name: fkac735685db66b58c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY ho_vc_group
    ADD CONSTRAINT fkac735685db66b58c FOREIGN KEY (vlan_tag_id) REFERENCES vlan_tag(vlan_tag_id);


--
-- Name: fkb3aca858a96b69d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY pcon_minval_names
    ADD CONSTRAINT fkb3aca858a96b69d FOREIGN KEY (pcon_id) REFERENCES path_constraint(pcon_id);


--
-- Name: fkb9f6741dee320bb6; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY eth_logical_port
    ADD CONSTRAINT fkb9f6741dee320bb6 FOREIGN KEY (interface_id) REFERENCES generic_interface(interface_id);


--
-- Name: fkc44642668ee852f5; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY generic_connection
    ADD CONSTRAINT fkc44642668ee852f5 FOREIGN KEY (version_id) REFERENCES version_info(version_id);


--
-- Name: fkc44642668fe190cd; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY generic_connection
    ADD CONSTRAINT fkc44642668fe190cd FOREIGN KEY (path_id) REFERENCES path(path_id);


--
-- Name: fkc4464266d9e5f55e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY generic_connection
    ADD CONSTRAINT fkc4464266d9e5f55e FOREIGN KEY (link_id) REFERENCES generic_link(link_id);


--
-- Name: fkd25fa15d6257222c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY glink_to_intrapath
    ADD CONSTRAINT fkd25fa15d6257222c FOREIGN KEY (path_id) REFERENCES intradomain_path(path_id);


--
-- Name: fkd25fa15da57f0317; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY glink_to_intrapath
    ADD CONSTRAINT fkd25fa15da57f0317 FOREIGN KEY (glink_id) REFERENCES generic_link(link_id);


--
-- Name: fkd2d9109d3e2d7e16; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY domain_ids
    ADD CONSTRAINT fkd2d9109d3e2d7e16 FOREIGN KEY (gcon_id) REFERENCES global_constraints(gcon_id);


--
-- Name: fkd36c82077189461b; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY pcon_range_constraints
    ADD CONSTRAINT fkd36c82077189461b FOREIGN KEY (constraint_id) REFERENCES range_constraint(con_id);


--
-- Name: fkd36c82078a96b69d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY pcon_range_constraints
    ADD CONSTRAINT fkd36c82078a96b69d FOREIGN KEY (pcon_id) REFERENCES path_constraint(pcon_id);


--
-- Name: fkd4675b1b3e2d7e16; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY domain_constraints
    ADD CONSTRAINT fkd4675b1b3e2d7e16 FOREIGN KEY (gcon_id) REFERENCES global_constraints(gcon_id);


--
-- Name: fkd9142fd98a96b69d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY pcon_range_names
    ADD CONSTRAINT fkd9142fd98a96b69d FOREIGN KEY (pcon_id) REFERENCES path_constraint(pcon_id);


--
-- Name: fkdb77d700d9e5f55e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY eth_link
    ADD CONSTRAINT fkdb77d700d9e5f55e FOREIGN KEY (link_id) REFERENCES generic_link(link_id);


--
-- Name: fke6bfd77e3d1567ad; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sdh_device
    ADD CONSTRAINT fke6bfd77e3d1567ad FOREIGN KEY (node_id) REFERENCES node(node_id);


--
-- Name: fke6bfd77ed4c4f622; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sdh_device
    ADD CONSTRAINT fke6bfd77ed4c4f622 FOREIGN KEY (sdh_domain_id) REFERENCES sdh_domain(sdh_domain_id);


--
-- Name: fkefdbbea3ee320bb6; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY eth_physical_port
    ADD CONSTRAINT fkefdbbea3ee320bb6 FOREIGN KEY (interface_id) REFERENCES generic_interface(interface_id);


--
-- Name: fkf5ca8a745988bff1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY boolean_constraint
    ADD CONSTRAINT fkf5ca8a745988bff1 FOREIGN KEY (con_id) REFERENCES network_constraint(con_id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

