import { Link, Outlet, useLocation } from "react-router-dom";
import { useAuth } from "../state/auth";

export default function Layout() {
  const auth = useAuth();
  const loc = useLocation();

  return (
    <div className="container">
      <div className="panel">
        <div className="row" style={{ justifyContent: "space-between" }}>
          <div className="row">
            <Link to="/products" className={isActive(loc.pathname, "/products") ? "" : "muted"}>
              Products
            </Link>
            <Link to="/cart" className={isActive(loc.pathname, "/cart") ? "" : "muted"}>
              Cart
            </Link>
            <Link to="/orders" className={isActive(loc.pathname, "/orders") ? "" : "muted"}>
              Orders
            </Link>
          </div>
          <div className="row">
            {auth.loading ? (
              <span className="muted">Loading...</span>
            ) : auth.me ? (
              <>
                <span className="muted">{auth.me.email}</span>
                <button className="btn btnDanger" onClick={auth.logout}>
                  Logout
                </button>
              </>
            ) : (
              <>
                <Link to="/login" className={isActive(loc.pathname, "/login") ? "" : "muted"}>
                  Login
                </Link>
                <Link to="/register" className={isActive(loc.pathname, "/register") ? "" : "muted"}>
                  Register
                </Link>
              </>
            )}
          </div>
        </div>
      </div>

      <div className="space" />
      <Outlet />
    </div>
  );
}

function isActive(pathname: string, prefix: string) {
  return pathname === prefix || pathname.startsWith(prefix + "/");
}

