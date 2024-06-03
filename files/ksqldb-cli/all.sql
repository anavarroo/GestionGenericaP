CREATE SOURCE CONNECTOR mysql_source_connector
WITH (
  'connector.class' = 'io.confluent.connect.jdbc.JdbcSourceConnector',
  'connection.url' = 'jdbc:mysql://generics-db:3306/generics',
  'connection.user' = 'root',
  'connection.password' = 'root',
  'table.whitelist' = 'usuarios',
  'mode' = 'incrementing',
  'incrementing.column.name' = 'id',
  'topic.prefix' = '',
  'key'='id'
);

CREATE STREAM usuarios_raw WITH (
    KAFKA_TOPIC='usuarios',
    VALUE_FORMAT='AVRO'
);

CREATE STREAM usuarios_cleaned WITH (
    KAFKA_TOPIC='usuarios_cleaned',
    VALUE_FORMAT='AVRO'
) AS
SELECT
    id,
    apellidos,
    REPLACE(contrasena, '\x00', '') AS contrasena,
    correo,
    create_date,
    direccion,
    edad,
    edition_date,
    estado,
    mfa_enabled,
    nombre,
    role,
    fakey,
    telefono
FROM usuarios_raw;


CREATE SINK CONNECTOR redis_sink WITH (
{
  "name": "redis_sink",
  "config": {
    "connector.class": "com.github.jcustenborder.kafka.connect.redis.RedisSinkConnector",
    "tasks.max": "1",
    "topics": "usuarios_cleaned",
    "redis.hosts": "redis:6379",
    "key.converter": "org.apache.kafka.connect.converters.ByteArrayConverter",
    "value.converter": "org.apache.kafka.connect.converters.ByteArrayConverter"
  }
});


-- CREATE SINK CONNECTOR redis_sink WITH (
--  'connector.class'='com.github.jcustenborder.kafka.connect.redis.RedisSinkConnector',
--  'tasks.max'='1',
--  'topics'='usuarios',
--  'redis.hosts'='redis:6379',
--  'key.converter'='org.apache.kafka.connect.converters.ByteArrayConverter',
--  'value.converter'='org.apache.kafka.connect.converters.ByteArrayConverter'
--);

