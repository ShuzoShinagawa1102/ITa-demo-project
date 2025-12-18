import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { ApiError } from "../api/http";
import { useAuth } from "../state/auth";
import { registerSchema } from "../validation/schemas";

export default function RegisterPage() {
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
      const parsed = registerSchema.safeParse({ email, password });
      if (!parsed.success) {
        const flat = parsed.error.flatten().fieldErrors;
        setFieldErrors({
          email: flat.email?.[0],
          password: flat.password?.[0],
        });
        return;
      }
      await auth.register(email, password);
      nav("/products");
    } catch (e) {
      setError(toMessage(e));
    } finally {
      setSubmitting(false);
    }
  }

  return (
    <div className="panel">
      <h2 style={{ marginTop: 0 }}>Register</h2>
      <p className="muted">メールアドレスとパスワードでアカウントを作成します（パスワードは8文字以上）。</p>
      <form onSubmit={onSubmit}>
        <div className="row">
          <div style={{ flex: 1, minWidth: 240 }}>
            <div className="muted">Email</div>
            <input value={email} onChange={(e) => setEmail(e.target.value)} placeholder="you@example.com" />
            {fieldErrors.email && <div className="error">{fieldErrors.email}</div>}
          </div>
          <div style={{ flex: 1, minWidth: 240 }}>
            <div className="muted">Password</div>
            <input
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="********"
              type="password"
              autoComplete="new-password"
            />
            {fieldErrors.password && <div className="error">{fieldErrors.password}</div>}
          </div>
        </div>
        <div className="space" />
        {error && <div className="error">{error}</div>}
        <div className="space" />
        <div className="row">
          <button className="btn" disabled={submitting}>
            Register
          </button>
          <Link to="/login" className="muted">
            すでにアカウントがある
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
