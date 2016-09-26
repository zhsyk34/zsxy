CREATE TABLE IF NOT EXISTS role (
  roleId     BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  name       VARCHAR(30)     NOT NULL,
  createTime TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updateTime TIMESTAMP,
  PRIMARY KEY (roleId)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = UTF8;

