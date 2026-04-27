import api from './axios'
import type { CartResponse, AddToCartRequest, UpdateCartItemRequest } from '../types'

export const getCart = () =>
  api.get<CartResponse>('/cart').then((r) => r.data)

export const addToCart = (data: AddToCartRequest) =>
  api.post<CartResponse>('/cart/items', data).then((r) => r.data)

export const updateCartItem = (cartItemId: number, data: UpdateCartItemRequest) =>
  api.put<CartResponse>(`/cart/items/${cartItemId}`, data).then((r) => r.data)

export const removeFromCart = (cartItemId: number) =>
  api.delete(`/cart/items/${cartItemId}`)
