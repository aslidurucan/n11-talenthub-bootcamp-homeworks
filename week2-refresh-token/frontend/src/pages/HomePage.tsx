import { useQuery } from '@tanstack/react-query'
import { useSearchParams } from 'react-router-dom'
import { getProducts, searchProducts } from '../api/products'
import ProductCard from '../components/ProductCard'

export default function HomePage() {
  const [params] = useSearchParams()
  const search = params.get('search') || ''

  const { data: products, isLoading, isError } = useQuery({
    queryKey: ['products', search],
    queryFn: () => search ? searchProducts(search) : getProducts(),
  })

  return (
    <div className="max-w-7xl mx-auto px-4 py-6">
      {/* Hero Banner */}
      {!search && (
        <div className="bg-gradient-to-r from-[#ff6000] to-[#ff8533] rounded-2xl p-8 mb-8 text-white flex items-center justify-between overflow-hidden relative">
          <div>
            <p className="text-sm font-medium opacity-80 mb-1">Özel Fırsatlar</p>
            <h1 className="text-3xl font-bold mb-2">Kampanyalı Ürünler</h1>
            <p className="text-sm opacity-90">En iyi fiyatlarla alışverişin keyfini çıkar</p>
          </div>
          <div className="text-8xl opacity-20 absolute right-8">🛍️</div>
        </div>
      )}

      {search && (
        <div className="mb-6">
          <h2 className="text-xl font-semibold text-gray-800">
            "<span className="text-[#ff6000]">{search}</span>" için {products?.length ?? 0} sonuç
          </h2>
        </div>
      )}

      {isLoading && (
        <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-5 gap-4">
          {Array.from({ length: 10 }).map((_, i) => (
            <div key={i} className="bg-white rounded-xl overflow-hidden shadow-sm animate-pulse">
              <div className="aspect-square bg-gray-200" />
              <div className="p-3 space-y-2">
                <div className="h-3 bg-gray-200 rounded w-1/2" />
                <div className="h-4 bg-gray-200 rounded" />
                <div className="h-4 bg-gray-200 rounded w-3/4" />
              </div>
            </div>
          ))}
        </div>
      )}

      {isError && (
        <div className="text-center py-16 text-gray-500">
          <p className="text-4xl mb-4">😕</p>
          <p className="font-medium">Ürünler yüklenemedi. Backend çalışıyor mu?</p>
        </div>
      )}

      {!isLoading && !isError && products?.length === 0 && (
        <div className="text-center py-16 text-gray-500">
          <p className="text-4xl mb-4">🔍</p>
          <p className="font-medium">Sonuç bulunamadı</p>
        </div>
      )}

      {!isLoading && !isError && products && products.length > 0 && (
        <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-5 gap-4">
          {products.map((p) => <ProductCard key={p.id} product={p} />)}
        </div>
      )}
    </div>
  )
}
