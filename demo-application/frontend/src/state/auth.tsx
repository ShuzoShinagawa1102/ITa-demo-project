import React, { createContext, useCallback, useContext, useEffect, useMemo, useState } from "react";
import { api } from "../api/http";
import type { AuthPrincipal, AuthResult } from "../api/types";

type AuthState = {
  token: string | null;
  me: AuthPrincipal | null;
  loading: boolean;
  login: (email: string, password: string) => Promise<void>;
  register: (email: string, password: string) => Promise<void>;
  logout: () => void;
  refreshMe: () => Promise<void>;
};

const AuthCtx = createContext<AuthState | null>(null);

const TOKEN_KEY = "demo_shop_token";

export function AuthProvider(props: { children: React.ReactNode }) {
  const [token, setToken] = useState<string | null>(() => localStorage.getItem(TOKEN_KEY));
  const [me, setMe] = useState<AuthPrincipal | null>(null);
  const [loading, setLoading] = useState<boolean>(true);

  const refreshMe = useCallback(async () => {
    if (!token) {
      setMe(null);
      return;
    }
    try {
      const principal = await api<AuthPrincipal>("/api/auth/me", { token });
      setMe(principal);
    } catch {
      setMe(null);
      setToken(null);
      localStorage.removeItem(TOKEN_KEY);
    }
  }, [token]);

  async function login(email: string, password: string) {
    const result = await api<AuthResult>("/api/auth/login", { method: "POST", body: { email, password } });
    setToken(result.accessToken);
    localStorage.setItem(TOKEN_KEY, result.accessToken);
    await refreshMe();
  }

  async function register(email: string, password: string) {
    const result = await api<AuthResult>("/api/auth/register", { method: "POST", body: { email, password } });
    setToken(result.accessToken);
    localStorage.setItem(TOKEN_KEY, result.accessToken);
    await refreshMe();
  }

  function logout() {
    setMe(null);
    setToken(null);
    localStorage.removeItem(TOKEN_KEY);
  }

  useEffect(() => {
    (async () => {
      setLoading(true);
      await refreshMe();
      setLoading(false);
    })();
  }, [refreshMe]);

  const value = useMemo<AuthState>(
    () => ({ token, me, loading, login, register, logout, refreshMe }),
    [token, me, loading, refreshMe],
  );

  return <AuthCtx.Provider value={value}>{props.children}</AuthCtx.Provider>;
}

export function useAuth(): AuthState {
  const ctx = useContext(AuthCtx);
  if (!ctx) throw new Error("useAuth must be used within AuthProvider");
  return ctx;
}
