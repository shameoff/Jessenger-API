-- Миграция для изменения имени поля login на username

-- Удаляем уникальный индекс на поле first_middle_last_name (если есть)
DROP INDEX IF EXISTS idx_users_first_middle_last_name;

-- Изменяем имя поля first_middle_last_name на fullName
ALTER TABLE public.users RENAME COLUMN first_middle_last_name TO full_name;
