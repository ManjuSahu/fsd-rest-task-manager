CREATE DATABASE task_manager;

CREATE TABLE task_manager.users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    employee_id INT,
    project_id INT,
    task_id INT
);

CREATE TABLE task_manager.projects (
    project_id INT AUTO_INCREMENT PRIMARY KEY,
    project VARCHAR(50) NOT NULL,
    start_date date,
    end_date date,
    priority INT,
    status VARCHAR(50),
    manager_id INT,
    CONSTRAINT fk_manager_id FOREIGN KEY (manager_id) REFERENCES task_manager.users(user_id)
);

CREATE TABLE task_manager.parent_tasks (
      parent_id INT AUTO_INCREMENT PRIMARY KEY,
      parent_task VARCHAR(500) NOT NULL,
      status VARCHAR(50),
      project_id INT,
      CONSTRAINT fk_project_id FOREIGN KEY (project_id) REFERENCES task_manager.projects(project_id)
);

CREATE TABLE task_manager.tasks (
      task_id INT AUTO_INCREMENT PRIMARY KEY,
      task VARCHAR(500) NOT NULL,
      start_date date,
      end_date date,
      priority INT,
      status VARCHAR(50),
      parent_id INT,
      project_id INT,
      task_owner_id INT,
      CONSTRAINT fk_parent_id FOREIGN KEY (parent_id) REFERENCES task_manager.parent_tasks(parent_id),
      CONSTRAINT fk_task_project_id FOREIGN KEY (project_id) REFERENCES task_manager.projects(project_id),
      CONSTRAINT fk_task_owner_id FOREIGN KEY (task_owner_id) REFERENCES task_manager.users(user_id)
);