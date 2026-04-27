import api from './axios'
import type { ProductResponse, CategoryResponse, CreateProductRequest, CreateCategoryRequest } from '../types'

export const createProduct = (data: CreateProductRequest) =>
  api.post<ProductResponse>('/admin/products', data).then((r) => r.data)

export const updateProduct = (id: number, data: CreateProductRequest) =>
  api.put<ProductResponse>(`/admin/products/${id}`, data).then((r) => r.data)

export const deleteProduct = (id: number) =>
  api.delete(`/admin/products/${id}`)

export const createCategory = (data: CreateCategoryRequest) =>
  api.post<CategoryResponse>('/admin/categories', data).then((r) => r.data)
