import React, { createContext, useContext, useEffect, useState } from "react";
import { getCurrentUser, login as apiLogin, logout as apiLogout, register as apiRegister } from "../services/auth";

export interface UserInfo {
  username: string;
  nickname: string;
  identity: number;
  workId?: string;
  gender?: number;
}

interface AuthContextType {
  user: UserInfo | null;
  token: string | null;
  login: (username: string, password: string) => Promise<void>;
  register: (payload: {
    username: string;
    password: string;
    nickname: string;
    identity: number;
  }) => Promise<void>;
  logout: () => Promise<void>;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

const TOKEN_KEY = "campus-ai-token";
const USER_KEY = "campus-ai-user";

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [user, setUser] = useState<UserInfo | null>(null);
  const [token, setToken] = useState<string | null>(null);

  useEffect(() => {
    const storedToken = localStorage.getItem(TOKEN_KEY);
    const storedUser = localStorage.getItem(USER_KEY);
    if (storedToken) {
      setToken(storedToken);
    }
    if (storedUser) {
      setUser(JSON.parse(storedUser));
    }
  }, []);

  const login = async (username: string, password: string) => {
    const { user, token } = await apiLogin({ username, password });
    setUser(user);
    setToken(token);
    localStorage.setItem(TOKEN_KEY, token);
    localStorage.setItem(USER_KEY, JSON.stringify(user));
  };

  const register = async (payload: {
    username: string;
    password: string;
    nickname: string;
    identity: number;
  }) => {
    await apiRegister(payload);
    // 注册后仍需登录，这里不自动登录
  };

  const logout = async () => {
    await apiLogout();
    setUser(null);
    setToken(null);
    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem(USER_KEY);
  };

  useEffect(() => {
    // 尝试从后端获取当前用户信息（如果你实现了这个接口的话）
    if (!user && token) {
      getCurrentUser()
        .then((info) => {
          setUser(info);
          localStorage.setItem(USER_KEY, JSON.stringify(info));
        })
        .catch(() => {
          // ignore
        });
    }
  }, [token, user]);

  return (
    <AuthContext.Provider value={{ user, token, login, register, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const ctx = useContext(AuthContext);
  if (!ctx) {
    throw new Error("useAuth must be used within AuthProvider");
  }
  return ctx;
};


