create table if not exists users (
  id uuid primary key,
  email varchar(255) not null unique,
  password_hash varchar(255) not null,
  role varchar(32) not null,
  created_at timestamptz not null
);

create table if not exists products (
  id uuid primary key,
  sku varchar(64) not null unique,
  name varchar(255) not null,
  description text,
  price_yen bigint not null,
  active boolean not null,
  created_at timestamptz not null
);

create table if not exists carts (
  id uuid primary key,
  user_id uuid not null unique,
  created_at timestamptz not null,
  updated_at timestamptz not null
);

create table if not exists cart_items (
  id uuid primary key,
  cart_id uuid not null references carts(id) on delete cascade,
  product_id uuid not null references products(id),
  quantity int not null,
  unit_price_yen bigint not null,
  created_at timestamptz not null,
  updated_at timestamptz not null,
  unique (cart_id, product_id)
);

create table if not exists orders (
  id uuid primary key,
  user_id uuid not null,
  status varchar(32) not null,
  total_yen bigint not null,
  created_at timestamptz not null
);

create table if not exists order_items (
  id uuid primary key,
  order_id uuid not null references orders(id) on delete cascade,
  product_id uuid not null,
  product_name varchar(255) not null,
  unit_price_yen bigint not null,
  quantity int not null
);

create index if not exists idx_orders_user_id_created_at on orders(user_id, created_at desc);

