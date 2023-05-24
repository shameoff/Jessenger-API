CREATE DATABASE user_db;
CREATE DATABASE friends_db;
CREATE DATABASE chat_db;
CREATE DATABASE file_storage_db;
CREATE DATABASE notifications_db;
CREATE DATABASE eureka_db;
CREATE DATABASE files_db;

CREATE USER user_user WITH PASSWORD 'user_password';
CREATE USER friends_user WITH PASSWORD 'friends_password';
CREATE USER chat_user WITH PASSWORD 'chat_password';
CREATE USER file_storage_user WITH PASSWORD 'file_storage_password';
CREATE USER notifications_user WITH PASSWORD 'notifications_password';
CREATE USER eureka_user WITH PASSWORD 'eureka_password';
CREATE USER files_user WITH PASSWORD 'files_password';

GRANT ALL PRIVILEGES ON DATABASE user_db TO user_user;
GRANT ALL PRIVILEGES ON DATABASE friends_db TO friends_user;
GRANT ALL PRIVILEGES ON DATABASE chat_db TO chat_user;
GRANT ALL PRIVILEGES ON DATABASE file_storage_db TO file_storage_user;
GRANT ALL PRIVILEGES ON DATABASE notifications_db TO notifications_user;
GRANT ALL PRIVILEGES ON DATABASE eureka_db TO eureka_user;
GRANT ALL PRIVILEGES ON DATABASE files_db TO files_user;