export type UUID = string;

export type AuthResult = {
  accessToken: string;
  userId: UUID;
  email: string;
  role: string;
};

export type AuthPrincipal = {
  userId: UUID;
  email: string;
  role: string;
};

export type Product = {
  id: UUID;
  sku: string;
  name: string;
  description?: string | null;
  priceYen: number;
};

export type CartItem = {
  itemId: UUID;
  productId: UUID;
  productName?: string | null;
  unitPriceYen: number;
  quantity: number;
  subtotalYen: number;
};

export type Cart = {
  cartId: UUID;
  userId: UUID;
  items: CartItem[];
  totalYen: number;
};

export type OrderItem = {
  itemId: UUID;
  productId: UUID;
  productName: string;
  unitPriceYen: number;
  quantity: number;
  subtotalYen: number;
};

export type Order = {
  orderId: UUID;
  status: string;
  totalYen: number;
  createdAt: string;
  items: OrderItem[];
};

