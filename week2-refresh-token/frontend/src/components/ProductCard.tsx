import { useState } from 'react'
import { Link } from 'react-router-dom'
import type { ProductResponse } from '../types'
import { useAuthStore } from '../store/authStore'
import { addToCart } from '../api/cart'
import { useQueryClient } from '@tanstack/react-query'

interface Props {
  product: ProductResponse
}

export default function ProductCard({ product }: Props) {
  const { accessToken } = useAuthStore()
  const queryClient = useQueryClient()
  const [adding, setAdding] = useState(false)

  const handleAddToCart = async (e: React.MouseEvent) => {
    e.preventDefault()
    if (!accessToken) { window.location.href = '/login'; return }
    setAdding(true)
    try {
      await addToCart({ productId: product.id, quantity: 1 })
      queryClient.invalidateQueries({ queryKey: ['cart'] })
    } finally {
      setAdding(false)
    }
  }

  const image = product.imageUrl || `https://placehold.co/300x300?text=${encodeURIComponent(product.name)}`

  return (
    <Link to={`/product/${product.id}`} className="group bg-white rounded-xl overflow-hidden shadow-sm hover:shadow-md transition-all duration-200 flex flex-col border border-gray-100">
      <div className="relative aspect-square overflow-hidden bg-gray-50">
        <img
          src={image}
          alt={product.name}
          className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-300"
          onError={(e) => { (e.target as HTMLImageElement).src = `https://placehold.co/300x300?text=Urun` }}
        />
        {product.stock === 0 && (
          <div className="absolute inset-0 bg-black/50 flex items-center justify-center">
            <span className="text-white font-semibold text-sm">Stokta Yok</span>
          </div>
        )}
      </div>

      <div className="p-3 flex flex-col flex-1">
        <span className="text-xs text-[#ff6000] font-medium mb-1">{product.categoryName}</span>
        <h3 className="text-sm text-gray-800 font-medium line-clamp-2 flex-1 mb-2">{product.name}</h3>
        <div className="flex items-center justify-between mt-auto">
          <span className="text-lg font-bold text-[#ff6000]">
            {product.price.toLocaleString('tr-TR', { style: 'currency', currency: 'TRY' })}
          </span>
          <button
            onClick={handleAddToCart}
            disabled={adding || product.stock === 0}
            className="bg-[#ff6000] hover:bg-[#e05500] disabled:bg-gray-300 text-white text-xs font-medium px-3 py-1.5 rounded-lg transition-colors"
          >
            {adding ? '...' : 'Sepete Ekle'}
          </button>
        </div>
      </div>
    </Link>
  )
}
