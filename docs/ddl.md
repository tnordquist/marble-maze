---
title: Database definition language
description: Sql statements to create tables & indices.
menu: DDL
order: 50
---

```sqlite
CREATE TABLE IF NOT EXISTS `game`
(
    `game_id`   INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `created`   INTEGER                           NOT NULL,
    `size`      INTEGER                           NOT NULL,
    `speed`     INTEGER                           NOT NULL,
    `time`      INTEGER                           NOT NULL,
    `completed` INTEGER                           NOT NULL
);

CREATE INDEX IF NOT EXISTS `index_game_created` ON `game` (`created`);

CREATE INDEX IF NOT EXISTS `index_game_size` ON `game` (`size`);
```

[`ddl.sql`](sql/ddl.sql)