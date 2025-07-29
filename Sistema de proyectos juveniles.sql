CREATE TYPE "categoria_enum" AS ENUM (
  'Tecnología',
  'Educación',
  'Salud',
  'Alimentos',
  'Moda',
  'Turismo',
  'Finanzas'
);

CREATE TYPE "estado_enum" AS ENUM (
  'pendiente',
  'aprobado',
  'rechazado',
  'en_proceso',
  'finalizado'
);

CREATE TYPE "fase_enum" AS ENUM (
  'Ideación',
  'Planificación',
  'Desarrollo',
  'Validación',
  'Lanzamiento',
  'Seguimiento'
);

CREATE TYPE "tipo_usuario_enum" AS ENUM (
  'emprendedor',
  'administrador'
);

CREATE TABLE "avance_fases" (
  "id" integer PRIMARY KEY NOT NULL,
  "idea_id" integer NOT NULL,
  "fase_id" integer NOT NULL,
  "porcentaje_avance" numeric(5,2) NOT NULL,
  "fecha_avance" date NOT NULL DEFAULT (CURRENT_TIMESTAMP)
);

CREATE TABLE "categorias" (
  "id" integer PRIMARY KEY NOT NULL,
  "nombre" categoria_enum UNIQUE NOT NULL,
  "descripcion" text
);

CREATE TABLE "estadisticas_idea" (
  "id" integer PRIMARY KEY NOT NULL,
  "avance_fase_id" integer NOT NULL,
  "mentoria_id" integer
);

CREATE TABLE "fases_proyecto" (
  "id" integer PRIMARY KEY NOT NULL,
  "fase" fase_enum UNIQUE NOT NULL,
  "descripcion" text
);

CREATE TABLE "ideas_negocio" (
  "id" integer PRIMARY KEY NOT NULL,
  "usuario_id" integer NOT NULL,
  "categoria_id" integer NOT NULL,
  "titulo" "character varying(200)" NOT NULL,
  "descripcion" text,
  "estado" integer DEFAULT 1
);

CREATE TABLE "logs_sistema" (
  "id" integer PRIMARY KEY NOT NULL,
  "usuario_id" integer NOT NULL,
  "accion" text,
  "fecha" timestamp DEFAULT (CURRENT_TIMESTAMP)
);

CREATE TABLE "mentores" (
  "id" integer PRIMARY KEY NOT NULL,
  "persona_id" integer NOT NULL,
  "especialidad" "character varying(100)"
);

CREATE TABLE "mentorias" (
  "id" integer PRIMARY KEY NOT NULL,
  "idea_id" integer NOT NULL,
  "mentor_id" integer NOT NULL,
  "fecha" date DEFAULT (CURRENT_TIMESTAMP),
  "estado" integer
);

CREATE TABLE "observaciones" (
  "id" integer PRIMARY KEY NOT NULL,
  "mentoria_id" integer NOT NULL,
  "comentario" text NOT NULL,
  "fecha_observacion" timestamp DEFAULT (CURRENT_TIMESTAMP)
);

CREATE TABLE "personas" (
  "id" integer PRIMARY KEY NOT NULL,
  "nombres" "character varying(100)" NOT NULL,
  "apellidos" "character varying(100)" NOT NULL,
  "correo" "character varying(100)" UNIQUE NOT NULL,
  "telefono" "character varying(20)",
  "direccion" text
);

CREATE TABLE "reportes" (
  "id" integer PRIMARY KEY NOT NULL,
  "fecha" timestamp DEFAULT (CURRENT_TIMESTAMP),
  "resultado_id" integer NOT NULL
);

CREATE TABLE "resultados" (
  "id" integer PRIMARY KEY NOT NULL,
  "usuario_id" integer NOT NULL,
  "estadisticas_id" integer NOT NULL
);

CREATE TABLE "tipo_estados" (
  "id" integer PRIMARY KEY NOT NULL,
  "tipo" "character varying(50)"
);

CREATE TABLE "usuarios" (
  "id" integer PRIMARY KEY NOT NULL,
  "persona_id" integer NOT NULL,
  "contraseña" "character varying(100)" NOT NULL,
  "tipo_usuario" tipo_usuario_enum NOT NULL
);

ALTER TABLE "avance_fases" ADD CONSTRAINT "fk_avance_fase" FOREIGN KEY ("fase_id") REFERENCES "fases_proyecto" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "estadisticas_idea" ADD CONSTRAINT "fk_avance_fase" FOREIGN KEY ("avance_fase_id") REFERENCES "avance_fases" ("id");

ALTER TABLE "avance_fases" ADD CONSTRAINT "fk_avance_idea" FOREIGN KEY ("idea_id") REFERENCES "ideas_negocio" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "resultados" ADD CONSTRAINT "fk_estadisticas_id" FOREIGN KEY ("estadisticas_id") REFERENCES "estadisticas_idea" ("id") ON DELETE CASCADE;

ALTER TABLE "mentorias" ADD CONSTRAINT "fk_estado" FOREIGN KEY ("estado") REFERENCES "tipo_estados" ("id");

ALTER TABLE "ideas_negocio" ADD CONSTRAINT "fk_idea_categoria" FOREIGN KEY ("categoria_id") REFERENCES "categorias" ("id") ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE "ideas_negocio" ADD CONSTRAINT "fk_idea_usuario" FOREIGN KEY ("usuario_id") REFERENCES "usuarios" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "logs_sistema" ADD CONSTRAINT "fk_log_usuario" FOREIGN KEY ("usuario_id") REFERENCES "usuarios" ("id") ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE "mentores" ADD CONSTRAINT "fk_mentor_persona" FOREIGN KEY ("persona_id") REFERENCES "personas" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "estadisticas_idea" ADD CONSTRAINT "fk_mentoria_id" FOREIGN KEY ("mentoria_id") REFERENCES "mentorias" ("id");

ALTER TABLE "mentorias" ADD CONSTRAINT "fk_mentoria_idea" FOREIGN KEY ("idea_id") REFERENCES "ideas_negocio" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "mentorias" ADD CONSTRAINT "fk_mentoria_mentor" FOREIGN KEY ("mentor_id") REFERENCES "mentores" ("id");

ALTER TABLE "observaciones" ADD CONSTRAINT "fk_observacion_mentoria" FOREIGN KEY ("mentoria_id") REFERENCES "mentorias" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "reportes" ADD CONSTRAINT "fk_resultado_id" FOREIGN KEY ("resultado_id") REFERENCES "resultados" ("id") ON DELETE CASCADE;

ALTER TABLE "resultados" ADD CONSTRAINT "fk_usuario_id" FOREIGN KEY ("usuario_id") REFERENCES "usuarios" ("id") ON DELETE CASCADE;

ALTER TABLE "usuarios" ADD CONSTRAINT "fk_usuario_persona" FOREIGN KEY ("persona_id") REFERENCES "personas" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "ideas_negocio" ADD CONSTRAINT "ideas_negocio_estado_fkey" FOREIGN KEY ("estado") REFERENCES "tipo_estados" ("id");
