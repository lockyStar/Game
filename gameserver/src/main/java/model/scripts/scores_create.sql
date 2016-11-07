-- DROP TABLE scores;

  CREATE TABLE IF NOT EXISTS scores
  (
      id        SERIAL PRIMARY KEY NOT NULL,
      score     INTEGER            NOT NULL,
      userName  VARCHAR            NOT NULL
  );
