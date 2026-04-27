import { useParams, useNavigate } from 'react-router-dom'
import { useQuery, useQueryClient } from '@tanstack/react-query'
import { getProduct } from '../api/products'
import { addToCart } from '../api/cart'
import { useAuthStore } from '../store/authStore'
import { useState } from 'react'

export default function ProductDetailPage() {
  const { id } = useParams<{ id: string }>()
  const navigate = useNavigate()
  const { accessToken } = useAuthStore()
  const queryClient = useQueryClient()
  const [quantity, setQuantity] = useState(1)
  const [adding, setAdding] = useState(false)
  const [added, setAdded] = useState(false)

  const { data: product, isLoading, isError } = useQuery({
    queryKey: ['product', id],
    queryFn: () => getProduct(Number(id)),
    enabled: !!id,
  })

  const handleAddToCart = async () => {
    if (!accessToken) { navigate('/login'); return }
    setAdding(true)
    try {
      await addToCart({ productId: Number(id), quantity })
      queryClient.invalidateQueries({ queryKey: ['cart'] })
      setAdded(true)
      setTimeout(() => setAdded(false), 2000)
    } finally {
      setAdding(false)
    }
  }

  if (isLoading) return (
    <div className="max-w-5xl mx-auto px-4 py-8 animate-pulse">
      <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
        <div className="aspect-square bg-gray-200 rounded-2xl" />
        <div className="space-y-4">
          <div className="h-6 bg-gray-200 rounded w-3/4" />
          <div className="h-10 bg-gray-200 rounded w-1/3" />
          <div className="h-4 bg-gray-200 rounded" />
          <div className="h-4 bg-gray-200 rounded w-5/6" />
        </div>
      </div>
    </div>
  )

  if (isError || !product) return (
    <div className="text-center py-16 text-gray-500">
      <p className="text-4xl mb-4">😕</p>
      <p>Ürün bulunamadı</p>
    </div>
  )

  const image = product.imageUrl || `https://placehold.co/500x500?text=${encodeURIComponent(product.name)}`

  return (
    <div className="max-w-5xl mx-auto px-4 py-8">
      <button onClick={() => navigate(-1)} className="text-sm text-gray-500 hover:text-[#ff6000] mb-6 flex items-center gap-1">
        ← Geri
      </button>

      <div className="bg-white rounded-2xl shadow-sm overflow-hidden">
        <div className="grid grid-cols-1 md:grid-cols-2 gap-0">
          <div className="bg-gray-50 p-8 flex items-center justify-center">
            <img
              src={image}
              alt={product.name}
              className="max-h-80 object-contain"
              onError={(e) => { (e.target as HTMLImageElement).src = `https://placehold.co/400x400?text=Ürün` }}
            />
          </div>

          <div className="p-8 flex flex-col">
            <span className="text-sm text-[#ff6000] font-medium mb-2">{product.categoryName}</span>
            <h1 className="text-2xl font-bold text-gray-900 mb-4">{product.name}</h1>

            {product.description && (
              <p className="text-gray-600 text-sm mb-6 leading-relaxed">{product.description}</p>
            )}

            <div className="text-3xl font-bold text-[#ff6000] mb-6">
              {product.price.toLocaleString('tr-TR', { style: 'currency', currency: 'TRY' })}
            </div>

            <div className="flex items-center gap-2 mb-2">
              <div className={`w-2 h-2 rounded-full ${product.stock > 0 ? 'bg-green-500' : 'bg-red-500'}`} />
              <span className={`text-sm font-medium ${product.stock > 0 ? 'text-green-600' : 'text-red-600'}`}>
                {product.stock > 0 ? `Stokta (${product.stock} adet)` : 'Stokta Yok'}
              </span>
            </div>

            {product.stock > 0 && (
              <div className="flex items-center gap-3 mb-6 mt-4">
                <span className="text-sm text-gray-600">Adet:</span>
                <div className="flex items-center border border-gray-200 rounded-lg overflow-hidden">
                  <button onClick={() => setQuantity(Math.max(1, quantity - 1))}
                    className="px-3 py-2 hover:bg-gray-100 text-gray-600 font-medium transition-colors">−</button>
                  <span className="px-4 py-2 text-sm font-semibold border-x border-gray-200">{quantity}</span>
                  <button onClick={() => setQuantity(Math.min(product.stock, quantity + 1))}
                    className="px-3 py-2 hover:bg-gray-100 text-gray-600 font-medium transition-colors">+</button>
                </div>
              </div>
            )}

            <button
              onClick={handleAddToCart}
              disabled={adding || product.stock === 0}
              className={`mt-auto py-3 rounded-xl font-semibold text-white transition-all ${
                added ? 'bg-green-500' :
                product.stock === 0 ? 'bg-gray-300 cursor-not-allowed' :
                'bg-[#ff6000] hover:bg-[#e05500] active:scale-95'
              }`}
            >
              {added ? '✓ Sepete Eklendi' : adding ? 'Ekleniyor...' : 'Sepete Ekle'}
            </button>
          </div>
        </div>
      </div>
    </div>
  )
}
