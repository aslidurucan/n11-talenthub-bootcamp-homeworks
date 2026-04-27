import { create } from 'zustand'
import { persist } from 'zustand/middleware'

interface AuthState {
  accessToken: string | null
  refreshToken: string | null
  username: string | null
  isAdmin: boolean
  setAuth: (accessToken: string, refreshToken: string, username: string, isAdmin?: boolean) => void
  setAccessToken: (token: string) => void
  logout: () => void
}

export const useAuthStore = create<AuthState>()(
  persist(
    (set) => ({
      accessToken: null,
      refreshToken: null,
      username: null,
      isAdmin: false,
      setAuth: (accessToken, refreshToken, username, isAdmin = false) =>
        set({ accessToken, refreshToken, username, isAdmin }),
      setAccessToken: (accessToken) => set({ accessToken }),
      logout: () => set({ accessToken: null, refreshToken: null, username: null, isAdmin: false }),
    }),
    { name: 'auth' }
  )
)
