import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { ApiError } from "../api/http";
import { useAuth } from "../state/auth";
import { loginSchema, PASSWORD_MAX_LENGTH, PASSWORD_MIN_LENGTH } from "../validation/schemas";

export default function LoginPage() {
  const auth = useAuth();
  const nav = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState<string | null>(null);
  const [fieldErrors, setFieldErrors] = useState<{ email?: string; password?: string }>({});
  const [submitting, setSubmitting] = useState(false);

  async function onSubmit(e: React.FormEvent) {
    e.preventDefault();
    setSubmitting(true);
    setError(null);
    setFieldErrors({});
    try {
      const parsed = loginSchema.safeParse({ email, password });
      if (!parsed.success) {
        const flat = parsed.error.flatten().fieldErrors;
        setFieldErrors({
          email: flat.email?.[0],
          password: flat.password?.[0],
        });
        return;
      }
      await auth.login(email, password);
      nav("/products");
    } catch (e) {
      setError(toMessage(e));
    } finally {
      setSubmitting(false);
    }
  }

  return (
    <div className="panel">
      <h2 style={{ marginTop: 0 }}>Login</h2>
      <p className="muted">ログインしてカート・注文機能を利用します。</p>
      <form onSubmit={onSubmit}>
        <div className="row" style={{ alignItems: "flex-start" }}>
          <div style={{ flex: 1, minWidth: 240 }}>
            <div className="muted">Email</div>
            <input value={email} onChange={(e) => setEmail(e.target.value)} placeholder="you@example.com" />
            {fieldErrors.email && <div className="error">{fieldErrors.email}</div>}
          </div>
          <div style={{ flex: 1, minWidth: 240 }}>
            <div className="muted">Password</div>
            <input
              value={password}
              onChange={(e) => setPassword(e.target.value.slice(0, PASSWORD_MAX_LENGTH))}
              placeholder="********"
              type="password"
              autoComplete="current-password"
              maxLength={PASSWORD_MAX_LENGTH}
            />
            <div className="muted" style={{ fontSize: 12, marginTop: 4 }}>
              {PASSWORD_MIN_LENGTH}〜{PASSWORD_MAX_LENGTH}文字（{password.length}/{PASSWORD_MAX_LENGTH}）
            </div>
            {fieldErrors.password && <div className="error">{fieldErrors.password}</div>}
          </div>
        </div>
        <div className="space" />
        {error && <div className="error">{error}</div>}
        <div className="space" />
        <div className="row">
          <button className="btn" disabled={submitting}>
            Login
          </button>
          <Link to="/register" className="muted">
            アカウントを作成する
          </Link>
        </div>
      </form>
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
