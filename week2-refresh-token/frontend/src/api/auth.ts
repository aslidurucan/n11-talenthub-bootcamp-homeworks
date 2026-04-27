import api from './axios'
import type { AuthResponse, LoginRequest, RegisterRequest } from '../types'

export const register = (data: RegisterRequest) =>
  api.post<AuthResponse>('/auth/register', data).then((r) => r.data)

export const login = (data: LoginRequest) =>
  api.post<AuthResponse>('/auth/login', data).then((r) => r.data)

export const logout = (refreshToken: string) =>
  api.post('/auth/logout', { refreshToken })
