/* Token de acesso */
CREATE TABLE oauth_access_token (
    authentication_id CHARACTER VARYING(255) NOT NULL,
    token_id CHARACTER VARYING(255),
    token BYTEA,
    user_name CHARACTER VARYING(255),
    client_id CHARACTER VARYING(255),
    authentication BYTEA,
    refresh_token CHARACTER VARYING(255),
    CONSTRAINT pk_oauth_access_token PRIMARY KEY(authentication_id)
);

COMMENT ON TABLE oauth_access_token IS 'Tabela contendo as informacoes de token do Spring';

/* Refresh do token de acesso */
CREATE TABLE oauth_refresh_token (
    token_id CHARACTER VARYING(255),
    token BYTEA,
    authentication BYTEA
);

COMMENT ON TABLE oauth_refresh_token IS 'Tabela contendo as informacoes de token (refresh) do Spring';