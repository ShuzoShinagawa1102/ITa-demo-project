import { useEffect, useMemo, useState } from "react";
import { Link } from "react-router-dom";
import { ApiError, api, formatYen } from "../api/http";
import type { Order } from "../api/types";
import { useAuth } from "../state/auth";

export default function OrdersPage() {
  const auth = useAuth();
  const token = auth.token;
  const canUse = useMemo(() => !!token, [token]);
  const [orders, setOrders] = useState<Order[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (!token) return;
    (async () => {
      setLoading(true);
      setError(null);
      try {
        const list = await api<Order[]>("/api/orders", { token });
        setOrders(list);
      } catch (e) {
        setError(toMessage(e));
      } finally {
        setLoading(false);
      }
    })();
  }, [token]);

  if (!canUse) {
    return (
      <div className="panel">
        <h2 style={{ marginTop: 0 }}>Orders</h2>
        <div className="muted">
          注文履歴を見るにはログインが必要です。<Link to="/login">Login</Link>
        </div>
      </div>
    );
  }

  return (
    <div className="panel">
      <h2 style={{ marginTop: 0 }}>Orders</h2>

      {loading ? (
        <div className="muted">Loading...</div>
      ) : error ? (
        <div className="error">{error}</div>
      ) : orders.length === 0 ? (
        <div className="muted">
          No orders. <Link to="/products">Browse products</Link>
        </div>
      ) : (
        <div className="row" style={{ flexDirection: "column", alignItems: "stretch" }}>
          {orders.map((o) => (
            <div key={o.orderId} className="panel" style={{ background: "rgba(0,0,0,0.15)" }}>
              <div className="row" style={{ justifyContent: "space-between" }}>
                <div>
                  <div style={{ fontWeight: 700 }}>Order {o.orderId}</div>
                  <div className="muted">
                    {new Date(o.createdAt).toLocaleString("ja-JP")} / {o.status}
                  </div>
                </div>
                <div style={{ fontSize: 18, fontWeight: 800 }}>{formatYen(o.totalYen)}</div>
              </div>
              <div className="space" />
              <table>
                <thead>
                  <tr>
                    <th>Item</th>
                    <th style={{ width: "18%" }}>Unit</th>
                    <th style={{ width: "16%" }}>Qty</th>
                    <th style={{ width: "18%" }}>Subtotal</th>
                  </tr>
                </thead>
                <tbody>
                  {o.items.map((i) => (
                    <tr key={i.itemId}>
                      <td>{i.productName}</td>
                      <td>{formatYen(i.unitPriceYen)}</td>
                      <td>{i.quantity}</td>
                      <td>{formatYen(i.subtotalYen)}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          ))}
        </div>
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

