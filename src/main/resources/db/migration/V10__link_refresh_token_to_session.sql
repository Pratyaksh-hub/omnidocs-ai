ALTER TABLE refresh_tokens
ADD COLUMN session_id BIGINT;

UPDATE refresh_tokens rt
SET session_id = (
    SELECT us.id
    FROM user_sessions us
    WHERE us.user_id = rt.user_id
    LIMIT 1
);

ALTER TABLE refresh_tokens
ALTER COLUMN session_id SET NOT NULL;

ALTER TABLE refresh_tokens
ADD CONSTRAINT fk_refresh_token_session
FOREIGN KEY(session_id)
REFERENCES user_sessions(id);

ALTER TABLE refresh_tokens
DROP CONSTRAINT fk_refresh_token_user;

ALTER TABLE refresh_tokens
DROP COLUMN user_id;

CREATE INDEX idx_refresh_token_session
ON refresh_tokens(session_id);