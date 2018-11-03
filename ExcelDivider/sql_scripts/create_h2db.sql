CREATE TABLE version (
  id    INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  param varchar(30) unique             NOT NULL
);

CREATE TABLE issueType (
  id    INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  param varchar(30) unique             NOT NULL
);

CREATE TABLE status (
  id    INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  param varchar(30) unique             NOT NULL
);

CREATE TABLE priority (
  id    INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  param varchar(30) unique             NOT NULL
);

CREATE TABLE resolution (
  id    INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  param varchar(40) unique             NOT NULL
);

CREATE TABLE employee (
  id         INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  firstname  varchar(30)                    NOT NULL,
  secondname varchar(30)                    NOT NULL,
  unique (firstname, secondname)
);

CREATE TABLE epicColor (
  id    INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  param varchar(30) unique             NOT NULL
);

CREATE TABLE sprint (
  id    INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  param varchar(60) unique             NOT NULL
);

CREATE TABLE keyword (
  id    INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  param varchar(60) unique             NOT NULL
);

create table workers (
  id       INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  assignee int references employee (id),
  reporter int references employee (id),
  creater  int references employee (id),
);

create table epics (
  id          INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  epic_name   varchar(150),
  epic_status int references status (id),
  epic_color   int references epicColor (id),
  epic_link   varchar(100)
);

create table statistics (
  id                     INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  progress               int,
  time_spent             int,
  original_estimate      int,
  remaining_estimate     int,
  work_ration            int,

  sum_progress           int,
  sum_time_spant         int,
  sum_remaining_estimate int,
  sum_original_estimate  int
);

create table dates (
  id          INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  updated     timestamp,
  resolved    timestamp,
  due_date    timestamp,
  last_viewed timestamp
);

create table consumables (
  id                INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  status            int references status (id),
  priority          int references priority (id),
  resolution        int references resolution (id),
  description       varchar(32500),
  sprint            int references sprint (id),
  order_number      varchar(100),
  delivered_version varchar(100),
  drc_number        varchar(20),
  keyword           int references keyword (id),
  fix_priority      int references priority (id),
  workers           int references workers (id),
  epics             int references epics (id),
  statistics        int references statistics (id),
  dates             int references dates (id)
);

CREATE TABLE task (
  id          INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  keys        varchar(9)                     NOT NULL UNIQUE,
  summary     varchar(300)                   NOT NULL,
  issue_type  int references issueType (id),
  created     timestamp,
  consumables int references consumables (id)
);

CREATE TABLE sub_task (
  task_id    int REFERENCES task (id)
  ON UPDATE CASCADE ON DELETE CASCADE,
  subtask_id int REFERENCES task (id)
  ON UPDATE CASCADE ON DELETE CASCADE,
  primary key (task_id, subtask_id),
  UNIQUE (task_id, subtask_id)
);

CREATE TABLE relation_task (
  task_id          int REFERENCES task (id)
  ON UPDATE CASCADE ON DELETE CASCADE,
  relation_task_id int REFERENCES task (id)
  ON UPDATE CASCADE ON DELETE CASCADE,
  primary key (task_id, relation_task_id),
  UNIQUE (task_id, relation_task_id)
);

CREATE TABLE duplicate_task (
  task_id           int REFERENCES task (id)
  ON UPDATE CASCADE ON DELETE CASCADE,
  duplicate_task_id int REFERENCES task (id)
  ON UPDATE CASCADE ON DELETE CASCADE,
  primary key (task_id, duplicate_task_id),
  UNIQUE (task_id, duplicate_task_id)
);

CREATE TABLE comment (
  id           INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  task_id      int references task (id),
  comment_date timestamp,
  author       int references employee (id),
  comment      varchar(34000)
);

CREATE TABLE sub_affects_version (
  consumables_id int REFERENCES consumables (id)
  ON UPDATE CASCADE ON DELETE CASCADE,
  version_id     int REFERENCES version (id)
  ON UPDATE CASCADE ON DELETE CASCADE,
  primary key (consumables_id, version_id),
  UNIQUE (consumables_id, version_id)
);

CREATE TABLE task_fix_version (
  consumables_id int REFERENCES consumables (id)
  ON UPDATE CASCADE ON DELETE CASCADE,
  version_id     int REFERENCES version (id)
  ON UPDATE CASCADE ON DELETE CASCADE,
  primary key (consumables_id, version_id),
  UNIQUE (consumables_id, version_id)
);

CREATE TABLE component (
  id    INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  param varchar(30) unique             NOT NULL
);

CREATE TABLE task_component (
  consumables_id int REFERENCES consumables (id)
  ON UPDATE CASCADE ON DELETE CASCADE,
  component_id   int REFERENCES component (id)
  ON UPDATE CASCADE ON DELETE CASCADE,
  primary key (consumables_id, component_id),
  UNIQUE (consumables_id, component_id)
);

CREATE TABLE label (
  id    INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  param varchar(50) unique             NOT NULL
);

CREATE TABLE task_label (
  consumables_id int REFERENCES consumables (id)
  ON UPDATE CASCADE ON DELETE CASCADE,
  label_id       int REFERENCES label (id)
  ON UPDATE CASCADE ON DELETE CASCADE,
  primary key (consumables_id, label_id),
  UNIQUE (consumables_id, label_id)
);

CREATE TABLE team (
  id    INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  param varchar(30) unique             NOT NULL
);

CREATE TABLE task_team (
  consumables_id int REFERENCES consumables (id)
  ON UPDATE CASCADE ON DELETE CASCADE,
  team_id        int REFERENCES team (id)
  ON UPDATE CASCADE ON DELETE CASCADE,
  primary key (consumables_id, team_id),
  UNIQUE (consumables_id, team_id)
);