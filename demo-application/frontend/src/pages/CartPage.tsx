import { useCallback, useEffect, useMemo, useState } from "react";
import { Link } from "react-router-dom";
import { ApiError, api, formatYen } from "../api/http";
import type { Cart } from "../api/types";
import { useAuth } from "../state/auth";
import { quantitySchema } from "../validation/schemas";

export default function CartPage() {
  const auth = useAuth();
  const [cart, setCart] = useState<Cart | null>(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [busy, setBusy] = useState<Record<string, boolean>>({});

  const token = auth.token;
  const canUse = useMemo(() => !!token, [token]);

  const load = useCallback(async () => {
    if (!token) return;
    setLoading(true);
    setError(null);
    try {
      const c = await api<Cart>("/api/cart", { token });
      setCart(c);
    } catch (e) {
      setError(toMessage(e));
    } finally {
      setLoading(false);
    }
  }, [token]);

  useEffect(() => {
    void load();
  }, [load]);

  async function changeQty(itemId: string, quantity: number) {
    if (!token) return;
    setBusy((m) => ({ ...m, [itemId]: true }));
    setError(null);
    try {
      const qty = quantitySchema.parse(quantity);
      const c = await api<Cart>(`/api/cart/items/${itemId}`, { method: "PUT", token, body: { quantity: qty } });
      setCart(c);
    } catch (e) {
      setError(toMessage(e));
    } finally {
      setBusy((m) => ({ ...m, [itemId]: false }));
    }
  }

  async function remove(itemId: string) {
    if (!token) return;
    setBusy((m) => ({ ...m, [itemId]: true }));
    setError(null);
    try {
      const c = await api<Cart>(`/api/cart/items/${itemId}`, { method: "DELETE", token });
      setCart(c);
    } catch (e) {
      setError(toMessage(e));
    } finally {
      setBusy((m) => ({ ...m, [itemId]: false }));
    }
  }

  async function checkout() {
    if (!token) return;
    setBusy((m) => ({ ...m, checkout: true }));
    setError(null);
    try {
      await api("/api/orders/checkout", { method: "POST", token });
      await load();
    } catch (e) {
      setError(toMessage(e));
    } finally {
      setBusy((m) => ({ ...m, checkout: false }));
    }
  }

  if (!canUse) {
    return (
      <div className="panel">
        <h2 style={{ marginTop: 0 }}>Cart</h2>
        <div className="muted">
          カートを見るにはログインが必要です。<Link to="/login">Login</Link>
        </div>
      </div>
    );
  }

  return (
    <div className="panel">
      <div className="row" style={{ justifyContent: "space-between" }}>
        <h2 style={{ margin: 0 }}>Cart</h2>
        <button className="btn btnOk" disabled={!cart?.items?.length || busy.checkout} onClick={checkout}>
          Checkout
        </button>
      </div>

      <div className="space" />
      {loading ? (
        <div className="muted">Loading...</div>
      ) : error ? (
        <div className="error">{error}</div>
      ) : !cart || cart.items.length === 0 ? (
        <div className="muted">
          Cart is empty. <Link to="/products">Browse products</Link>
        </div>
      ) : (
        <>
          <table>
            <thead>
              <tr>
                <th>Product</th>
                <th style={{ width: "18%" }}>Unit</th>
                <th style={{ width: "22%" }}>Qty</th>
                <th style={{ width: "18%" }}>Subtotal</th>
                <th style={{ width: "14%" }} />
              </tr>
            </thead>
            <tbody>
              {cart.items.map((i) => (
                <tr key={i.itemId}>
                  <td>{i.productName ?? i.productId}</td>
                  <td>{formatYen(i.unitPriceYen)}</td>
                  <td>
                    <div className="row">
                      <button className="btn" disabled={busy[i.itemId] || i.quantity <= 1} onClick={() => changeQty(i.itemId, i.quantity - 1)}>
                        -
                      </button>
                      <span>{i.quantity}</span>
                      <button className="btn" disabled={busy[i.itemId]} onClick={() => changeQty(i.itemId, i.quantity + 1)}>
                        +
                      </button>
                    </div>
                  </td>
                  <td>{formatYen(i.subtotalYen)}</td>
                  <td style={{ textAlign: "right" }}>
                    <button className="btn btnDanger" disabled={busy[i.itemId]} onClick={() => remove(i.itemId)}>
                      Remove
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
          <div className="space" />
          <div className="row" style={{ justifyContent: "flex-end" }}>
            <div className="muted">Total</div>
            <div style={{ fontSize: 20, fontWeight: 700 }}>{formatYen(cart.totalYen)}</div>
          </div>
        </>
      )}
    </div>
  );
}

function toMessage(e: unknown): string {
  if (e instanceof ApiError) {
    const payload = e.payload as any;
    return payload?.message ?? `HTTP ${e.status}`;
  }
  if (e instanceof Error) return e.message;
  return "unknown error";
}
