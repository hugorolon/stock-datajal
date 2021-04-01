
CREATE TABLE public.usuario_roles
(
  id serial NOT NULL,
  id_usuario integer NOT NULL,
  id_rol integer NOT NULL,
  CONSTRAINT usuarios_rolespkey PRIMARY KEY (id),
  CONSTRAINT fk4a087507o9j9ksh1tuw7ed4up FOREIGN KEY (id_usuario)
      REFERENCES public.usuarios (id) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT fkroles FOREIGN KEY (id_rol)
      REFERENCES public.roles (id) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.usuario_roles
  OWNER TO postgres;

ALTER TABLE public.venta_detalles
  ADD COLUMN id serial NOT NULL;
ALTER TABLE public.venta_detalles
  ADD CONSTRAINT ventadetallepkey PRIMARY KEY (id);

INSERT INTO public.roles(
             activo, nombre)
    VALUES ( 1,"VENTAS CON DESC. ITEM");
INSERT INTO public.roles(
             activo, nombre)
    VALUES ( 1,"VENTAS CON DESCUENTO TOTAL");
ALTER TABLE public.clientes
    ALTER COLUMN nombre TYPE character varying (125) COLLATE pg_catalog."default";    
ALTER TABLE public.clientes
    ALTER COLUMN razon_social TYPE character varying (125) COLLATE pg_catalog."default";    