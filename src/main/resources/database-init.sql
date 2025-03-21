DROP TABLE IF EXISTS favorite_records;
DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS items;
DROP TABLE IF EXISTS users;

-- VARCHAR is variable length string with max len of 100 chars
CREATE TABLE users(
    -- PRIMARY KEY ensures id is NOT NULL && UNIQUE
        -- PRIMARY KEY preferred choice for main identifier (better performance)
        -- NOT NULL UNIQUE for additional unique constraint
    id INT PRIMARY KEY      AUTO_INCREMENT,
    username VARCHAR(50)    NOT NULL UNIQUE,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    password VARCHAR(100)   NOT NULL,
    -- indicate whether user account is enabled/disabled
    -- TINYINT is 1 byte int
    enabled TINYINT         NOT NULL DEFAULT 1
);

CREATE TABLE authorities(
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50)        NOT NULL,
    authority VARCHAR(50)       NOT NULL,
    -- FOREIGN KEY is a col in one table that reference a col in another table
    -- CASCADE goes one-way, when username in users table is deleted/updated
    -- the username in authorities table will be deleted/updated
    FOREIGN KEY (username) REFERENCES users(username) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE items(
    id INT PRIMARY KEY AUTO_INCREMENT,
    twitch_id VARCHAR(255) UNIQUE NOT NULL,
    -- TEXT can store much larger amounts of texts compared to VARCHAR(255)
    -- we use TEXT due to some streamers use very long titles
    title TEXT,
    url VARCHAR(255),
    thumbnail_url VARCHAR(255),
    broadcaster_name VARCHAR(255),
    game_id VARCHAR(255),
    type VARCHAR(255)
);

CREATE TABLE favorite_records(
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    item_id INT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (item_id) REFERENCES items(id) ON DELETE CASCADE,
    -- unlike PRIMARY KEY, a table can have multiple UNIQUE KEY constraint
    -- no 2 rows have the same item_id && user_id
        -- meaning no user can like the same item more than once
        -- user 1 add item 101 (valid)
        -- user 1 add item 102 (valid)
        -- user 1 add item 101 (invalid)
    UNIQUE KEY unique_item_and_user_combo(item_id, user_id)
);