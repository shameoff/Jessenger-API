CREATE DATABASE users_db;
CREATE DATABASE friends_db;
CREATE DATABASE chats_db;
CREATE DATABASE notifications_db;
CREATE DATABASE fileserver_db;

CREATE USER users_user WITH PASSWORD 'users_password';
CREATE USER friends_user WITH PASSWORD 'friends_password';
CREATE USER chats_user WITH PASSWORD 'chats_password';
CREATE USER notifications_user WITH PASSWORD 'notifications_password';
CREATE USER fileserver_user WITH PASSWORD 'fileserver_password';

GRANT ALL PRIVILEGES ON DATABASE users_db TO users_user;
GRANT ALL PRIVILEGES ON DATABASE friends_db TO friends_user;
GRANT ALL PRIVILEGES ON DATABASE chats_db TO chats_user;
GRANT ALL PRIVILEGES ON DATABASE notifications_db TO notifications_user;
GRANT ALL PRIVILEGES ON DATABASE fileserver_db TO fileserver_user;