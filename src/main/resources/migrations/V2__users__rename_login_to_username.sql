-- Миграция для изменения имени поля login на username

-- Удаляем уникальный индекс на поле login (если есть)
DROP INDEX IF EXISTS idx_users_login;

-- Изменяем имя поля login на username
ALTER TABLE public.users RENAME COLUMN login TO username;

-- Создаем уникальный индекс на поле username
CREATE UNIQUE INDEX idx_users_username ON public.users (username);