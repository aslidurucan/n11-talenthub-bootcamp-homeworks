export interface AuthResponse {
  accessToken: string
  refreshToken: string
}

export interface RefreshTokenResponse {
  accessToken: string
}

export interface User {
  username: string
  roles: string[]
}

export interface ProductResponse {
  id: number
  name: string
  description: string
  price: number
  stock: number
  imageUrl: string
  categoryName: string
  categoryId: number
}

export interface CategoryResponse {
  id: number
  name: string
}

export interface CartItemResponse {
  id: number
  productId: number
  productName: string
  productPrice: number
  quantity: number
  totalPrice: number
}

export interface CartResponse {
  id: number
  items: CartItemResponse[]
  grandTotal: number
}

export interface RegisterRequest {
  username: string
  email: string
  password: string
}

export interface LoginRequest {
  username: string
  password: string
}

export interface CreateProductRequest {
  name: string
  description: string
  price: number
  stock: number
  imageUrl: string
  categoryId: number
}

export interface CreateCategoryRequest {
  name: string
}

export interface AddToCartRequest {
  productId: number
  quantity: number
}

export interface UpdateCartItemRequest {
  quantity: number
}

export interface ErrorResponse {
  status: number
  message: string
  timestamp: string
}
