CREATE TABLE singles_match (
    id UUID PRIMARY KEY,
    player1Score VARCHAR(5) NOT NULL,
    player2Score VARCHAR(5) NOT NULL,
    created_at TIMESTAMP NOT NULL
);
