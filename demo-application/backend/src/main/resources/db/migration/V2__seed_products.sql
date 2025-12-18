insert into products (id, sku, name, description, price_yen, active, created_at)
values
  ('00000000-0000-0000-0000-000000000101', 'SKU-001', 'Coffee Beans 200g', 'Medium roast. Good for espresso.', 980, true, now()),
  ('00000000-0000-0000-0000-000000000102', 'SKU-002', 'Drip Bag Set (10)', 'Easy drip bags for travel.', 1200, true, now()),
  ('00000000-0000-0000-0000-000000000103', 'SKU-003', 'Mug Cup', 'Ceramic mug cup.', 1500, true, now())
on conflict (sku) do nothing;

