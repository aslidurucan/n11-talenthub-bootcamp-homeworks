import api from './axios'
import type { ProductResponse } from '../types'

export const getProducts = () =>
  api.get<ProductResponse[]>('/products').then((r) => r.data)

export const getProduct = (id: number) =>
  api.get<ProductResponse>(`/products/${id}`).then((r) => r.data)

export const searchProducts = (name: string) =>
  api.get<ProductResponse[]>('/products/search', { params: { name } }).then((r) => r.data)

export const getProductsByCategory = (categoryId: number) =>
  api.get<ProductResponse[]>(`/products/category/${categoryId}`).then((r) => r.data)
