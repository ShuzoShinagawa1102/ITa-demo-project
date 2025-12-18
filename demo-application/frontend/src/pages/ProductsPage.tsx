import { useEffect, useMemo, useState } from "react";
import { api, formatYen } from "../api/http";
import type { Product } from "../api/types";
import { useAuth } from "../state/auth";
import { quantitySchema } from "../validation/schemas";

export default function ProductsPage() {
  const auth = useAuth();
  const [products, setProducts] = useState<Product[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [adding, setAdding] = useState<Record<string, boolean>>({});

  useEffect(() => {
    (async () => {
      setLoading(true);
      setError(null);
      try {
        const list = await api<Product[]>("/api/products");
        setProducts(list);
      } catch (e) {
        setError(e instanceof Error ? e.message : "failed to load");
      } finally {
        setLoading(false);
      }
    })();
  }, []);

  const canAdd = useMemo(() => !!auth.token, [auth.token]);

  async function addToCart(productId: string) {
    if (!auth.token) return;
    setAdding((m) => ({ ...m, [productId]: true }));
    try {
      const qty = quantitySchema.parse(1);
      await api("/api/cart/items", { method: "POST", token: auth.token, body: { productId, quantity: qty } });
    } finally {
      setAdding((m) => ({ ...m, [productId]: false }));
    }
  }

  return (
    <div className="panel">
      <div className="row" style={{ justifyContent: "space-between" }}>
        <h2 style={{ margin: 0 }}>Products</h2>
        {!canAdd && <span className="muted">カートに追加するにはログインが必要です</span>}
      </div>

      <div className="space" />
      {loading ? (
        <div className="muted">Loading...</div>
      ) : error ? (
        <div className="error">{error}</div>
      ) : (
        <table>
          <thead>
            <tr>
              <th style={{ width: "14%" }}>SKU</th>
              <th>Name</th>
              <th style={{ width: "18%" }}>Price</th>
              <th style={{ width: "18%" }} />
            </tr>
          </thead>
          <tbody>
            {products.map((p) => (
              <tr key={p.id}>
                <td className="muted">{p.sku}</td>
                <td>
                  <div>{p.name}</div>
                  {p.description && <div className="muted">{p.description}</div>}
                </td>
                <td>{formatYen(p.priceYen)}</td>
                <td style={{ textAlign: "right" }}>
                  <button className="btn" disabled={!canAdd || adding[p.id]} onClick={() => addToCart(p.id)}>
                    Add (1)
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}
