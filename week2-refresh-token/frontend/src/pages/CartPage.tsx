import { useQuery, useQueryClient } from '@tanstack/react-query'
import { getCart, removeFromCart, updateCartItem } from '../api/cart'
import { useAuthStore } from '../store/authStore'
import { Link } from 'react-router-dom'

export default function CartPage() {
  const { accessToken } = useAuthStore()
  const queryClient = useQueryClient()

  const { data: cart, isLoading, isError } = useQuery({
    queryKey: ['cart'],
    queryFn: getCart,
    enabled: !!accessToken,
  })

  const handleQuantityChange = async (cartItemId: number, quantity: number) => {
    await updateCartItem(cartItemId, { quantity })
    queryClient.invalidateQueries({ queryKey: ['cart'] })
  }

  const handleRemove = async (cartItemId: number) => {
    await removeFromCart(cartItemId)
    queryClient.invalidateQueries({ queryKey: ['cart'] })
  }

  if (!accessToken) return (
    <div className="max-w-3xl mx-auto px-4 py-16 text-center">
      <p className="text-4xl mb-4">🛒</p>
      <h2 className="text-xl font-semibold text-gray-800 mb-2">Sepetinizi görüntülemek için giriş yapın</h2>
      <Link to="/login" className="inline-block mt-4 bg-[#ff6000] text-white px-6 py-2.5 rounded-lg font-medium hover:bg-[#e05500] transition-colors">
        Giriş Yap
      </Link>
    </div>
  )

  if (isLoading) return (
    <div className="max-w-3xl mx-auto px-4 py-8 space-y-4">
      {[1, 2, 3].map(i => (
        <div key={i} className="bg-white rounded-xl p-4 animate-pulse flex gap-4">
          <div className="w-20 h-20 bg-gray-200 rounded-lg" />
          <div className="flex-1 space-y-2">
            <div className="h-4 bg-gray-200 rounded w-3/4" />
            <div className="h-4 bg-gray-200 rounded w-1/4" />
          </div>
        </div>
      ))}
    </div>
  )

  if (isError) return (
    <div className="max-w-3xl mx-auto px-4 py-16 text-center text-gray-500">
      <p className="text-4xl mb-4">😕</p>
      <p className="font-medium">Sepet yüklenemedi. Backend çalışıyor mu?</p>
    </div>
  )

  const isEmpty = !cart?.items || cart.items.length === 0

  return (
    <div className="max-w-3xl mx-auto px-4 py-8">
      <h1 className="text-2xl font-bold text-gray-900 mb-6">Sepetim</h1>

      {isEmpty ? (
        <div className="bg-white rounded-2xl p-12 text-center shadow-sm">
          <p className="text-5xl mb-4">🛒</p>
          <h2 className="text-xl font-semibold text-gray-700 mb-2">Sepetiniz boş</h2>
          <p className="text-gray-500 text-sm mb-6">Alışverişe devam edin</p>
          <Link to="/" className="bg-[#ff6000] text-white px-6 py-2.5 rounded-lg font-medium hover:bg-[#e05500] transition-colors inline-block">
            Alışverişe Başla
          </Link>
        </div>
      ) : (
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
          <div className="lg:col-span-2 space-y-3">
            {cart.items.map((item) => (
              <div key={item.id} className="bg-white rounded-xl p-4 shadow-sm flex gap-4 items-center">
                <div className="w-20 h-20 bg-gray-100 rounded-lg flex items-center justify-center text-gray-400 text-xs shrink-0">
                  📦
                </div>
                <div className="flex-1 min-w-0">
                  <p className="text-sm font-medium text-gray-800 line-clamp-2">{item.productName}</p>
                  <p className="text-[#ff6000] font-bold mt-1">
                    {Number(item.productPrice).toLocaleString('tr-TR', { style: 'currency', currency: 'TRY' })}
                  </p>
                </div>
                <div className="flex flex-col items-end gap-2">
                  <div className="flex items-center border border-gray-200 rounded-lg overflow-hidden">
                    <button
                      onClick={() => item.quantity > 1 ? handleQuantityChange(item.id, item.quantity - 1) : handleRemove(item.id)}
                      className="px-2.5 py-1.5 hover:bg-gray-100 text-gray-600 font-medium text-sm transition-colors"
                    >−</button>
                    <span className="px-3 py-1.5 text-sm font-semibold border-x border-gray-200">{item.quantity}</span>
                    <button
                      onClick={() => handleQuantityChange(item.id, item.quantity + 1)}
                      className="px-2.5 py-1.5 hover:bg-gray-100 text-gray-600 font-medium text-sm transition-colors"
                    >+</button>
                  </div>
                  <button onClick={() => handleRemove(item.id)} className="text-xs text-red-400 hover:text-red-600 transition-colors">
                    Kaldır
                  </button>
                </div>
              </div>
            ))}
          </div>

          <div className="lg:col-span-1">
            <div className="bg-white rounded-xl p-5 shadow-sm sticky top-24">
              <h3 className="font-semibold text-gray-800 mb-4">Sipariş Özeti</h3>
              <div className="space-y-2 text-sm text-gray-600 mb-4">
                {cart.items.map((item) => (
                  <div key={item.id} className="flex justify-between">
                    <span className="line-clamp-1 max-w-[60%]">{item.productName} x{item.quantity}</span>
                    <span>{Number(item.totalPrice).toLocaleString('tr-TR', { style: 'currency', currency: 'TRY' })}</span>
                  </div>
                ))}
              </div>
              <div className="border-t border-gray-100 pt-3 flex justify-between font-bold text-gray-900">
                <span>Toplam</span>
                <span className="text-[#ff6000]">
                  {Number(cart.grandTotal).toLocaleString('tr-TR', { style: 'currency', currency: 'TRY' })}
                </span>
              </div>
              <button className="w-full mt-4 bg-[#ff6000] hover:bg-[#e05500] text-white font-semibold py-3 rounded-xl transition-colors">
                Siparişi Tamamla
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  )
}
