CREATE DATABASE task_manager;

CREATE TABLE task_manager.users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    employee_id INT,
    project_id INT,
    task_id INT
);