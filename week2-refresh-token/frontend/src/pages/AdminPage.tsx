import { useState } from 'react'
import { useQuery, useQueryClient } from '@tanstack/react-query'
import { getProducts } from '../api/products'
import { createProduct, updateProduct, deleteProduct, createCategory } from '../api/admin'
import { useAuthStore } from '../store/authStore'
import { Navigate } from 'react-router-dom'
import type { ProductResponse } from '../types'

export default function AdminPage() {
  const { isAdmin, accessToken } = useAuthStore()
  const queryClient = useQueryClient()
  const [tab, setTab] = useState<'products' | 'categories'>('products')
  const [editProduct, setEditProduct] = useState<ProductResponse | null>(null)
  const [showForm, setShowForm] = useState(false)
  const [error, setError] = useState('')
  const [success, setSuccess] = useState('')

  const [productForm, setProductForm] = useState({
    name: '', description: '', price: '', stock: '', imageUrl: '', categoryId: '',
  })
  const [categoryForm, setCategoryForm] = useState({ name: '' })

  const { data: products, isLoading } = useQuery({
    queryKey: ['products'],
    queryFn: getProducts,
    enabled: !!accessToken,
  })

  if (!accessToken) return <Navigate to="/login" />
  if (!isAdmin) return (
    <div className="text-center py-16 text-gray-500">
      <p className="text-4xl mb-4">🚫</p>
      <p className="font-medium">Bu sayfaya erişim yetkiniz yok</p>
    </div>
  )

  const notify = (msg: string, isError = false) => {
    isError ? setError(msg) : setSuccess(msg)
    setTimeout(() => isError ? setError('') : setSuccess(''), 3000)
  }

  const openAdd = () => {
    setEditProduct(null)
    setProductForm({ name: '', description: '', price: '', stock: '', imageUrl: '', categoryId: '' })
    setShowForm(true)
  }

  const openEdit = (p: ProductResponse) => {
    setEditProduct(p)
    setProductForm({
      name: p.name, description: p.description || '', price: String(p.price),
      stock: String(p.stock), imageUrl: p.imageUrl || '', categoryId: String(p.categoryId),
    })
    setShowForm(true)
  }

  const handleProductSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    const payload = {
      name: productForm.name,
      description: productForm.description,
      price: Number(productForm.price),
      stock: Number(productForm.stock),
      imageUrl: productForm.imageUrl,
      categoryId: Number(productForm.categoryId),
    }
    try {
      if (editProduct) await updateProduct(editProduct.id, payload)
      else await createProduct(payload)
      queryClient.invalidateQueries({ queryKey: ['products'] })
      setShowForm(false)
      notify(editProduct ? 'Ürün güncellendi' : 'Ürün oluşturuldu')
    } catch (err: unknown) {
      const msg = (err as { response?: { data?: { message?: string } } })?.response?.data?.message
      notify(msg || 'Bir hata oluştu', true)
    }
  }

  const handleDelete = async (id: number) => {
    if (!confirm('Bu ürünü silmek istiyor musunuz?')) return
    try {
      await deleteProduct(id)
      queryClient.invalidateQueries({ queryKey: ['products'] })
      notify('Ürün silindi')
    } catch {
      notify('Silinemedi', true)
    }
  }

  const handleCategorySubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    try {
      await createCategory(categoryForm)
      setCategoryForm({ name: '' })
      notify('Kategori oluşturuldu')
    } catch (err: unknown) {
      const msg = (err as { response?: { data?: { message?: string } } })?.response?.data?.message
      notify(msg || 'Bir hata oluştu', true)
    }
  }

  return (
    <div className="max-w-6xl mx-auto px-4 py-8">
      <h1 className="text-2xl font-bold text-gray-900 mb-6">Admin Paneli</h1>

      {error && <div className="bg-red-50 border border-red-200 text-red-600 text-sm px-4 py-3 rounded-lg mb-4">{error}</div>}
      {success && <div className="bg-green-50 border border-green-200 text-green-600 text-sm px-4 py-3 rounded-lg mb-4">{success}</div>}

      <div className="flex gap-2 mb-6">
        {(['products', 'categories'] as const).map((t) => (
          <button key={t} onClick={() => setTab(t)}
            className={`px-4 py-2 rounded-lg text-sm font-medium transition-colors ${tab === t ? 'bg-[#ff6000] text-white' : 'bg-white text-gray-600 hover:bg-gray-50 border border-gray-200'}`}>
            {t === 'products' ? 'Ürünler' : 'Kategoriler'}
          </button>
        ))}
      </div>

      {tab === 'products' && (
        <div>
          <div className="flex justify-between items-center mb-4">
            <h2 className="text-lg font-semibold text-gray-800">Ürün Yönetimi</h2>
            <button onClick={openAdd} className="bg-[#ff6000] text-white text-sm font-medium px-4 py-2 rounded-lg hover:bg-[#e05500] transition-colors">
              + Yeni Ürün
            </button>
          </div>

          {showForm && (
            <div className="bg-white rounded-xl border border-gray-200 p-6 mb-6 shadow-sm">
              <h3 className="font-semibold text-gray-800 mb-4">{editProduct ? 'Ürün Düzenle' : 'Yeni Ürün'}</h3>
              <form onSubmit={handleProductSubmit} className="grid grid-cols-1 sm:grid-cols-2 gap-4">
                {[
                  { label: 'Ürün Adı', key: 'name', type: 'text', required: true },
                  { label: 'Açıklama', key: 'description', type: 'text', required: false },
                  { label: 'Fiyat (₺)', key: 'price', type: 'number', required: true },
                  { label: 'Stok', key: 'stock', type: 'number', required: true },
                  { label: 'Görsel URL', key: 'imageUrl', type: 'text', required: false },
                  { label: 'Kategori ID', key: 'categoryId', type: 'number', required: true },
                ].map(({ label, key, type, required }) => (
                  <div key={key}>
                    <label className="block text-xs font-medium text-gray-600 mb-1">{label}</label>
                    <input
                      type={type}
                      required={required}
                      value={productForm[key as keyof typeof productForm]}
                      onChange={(e) => setProductForm({ ...productForm, [key]: e.target.value })}
                      className="w-full px-3 py-2 border border-gray-200 rounded-lg text-sm outline-none focus:border-[#ff6000] focus:ring-1 focus:ring-[#ff6000]"
                    />
                  </div>
                ))}
                <div className="sm:col-span-2 flex gap-3 justify-end">
                  <button type="button" onClick={() => setShowForm(false)} className="px-4 py-2 text-sm text-gray-600 border border-gray-200 rounded-lg hover:bg-gray-50">İptal</button>
                  <button type="submit" className="px-4 py-2 text-sm bg-[#ff6000] text-white rounded-lg hover:bg-[#e05500]">
                    {editProduct ? 'Güncelle' : 'Oluştur'}
                  </button>
                </div>
              </form>
            </div>
          )}

          {isLoading ? (
            <div className="text-center py-8 text-gray-400">Yükleniyor...</div>
          ) : (
            <div className="bg-white rounded-xl border border-gray-200 overflow-hidden shadow-sm">
              <table className="w-full text-sm">
                <thead className="bg-gray-50 border-b border-gray-200">
                  <tr>
                    {['ID', 'Ürün', 'Kategori', 'Fiyat', 'Stok', 'İşlemler'].map(h => (
                      <th key={h} className="text-left px-4 py-3 text-xs font-semibold text-gray-600 uppercase tracking-wide">{h}</th>
                    ))}
                  </tr>
                </thead>
                <tbody className="divide-y divide-gray-100">
                  {products?.map((p) => (
                    <tr key={p.id} className="hover:bg-gray-50 transition-colors">
                      <td className="px-4 py-3 text-gray-500">#{p.id}</td>
                      <td className="px-4 py-3 font-medium text-gray-800 max-w-[200px] truncate">{p.name}</td>
                      <td className="px-4 py-3 text-gray-600">{p.categoryName}</td>
                      <td className="px-4 py-3 text-[#ff6000] font-semibold">
                        {p.price.toLocaleString('tr-TR', { style: 'currency', currency: 'TRY' })}
                      </td>
                      <td className="px-4 py-3">
                        <span className={`px-2 py-0.5 rounded-full text-xs font-medium ${p.stock > 0 ? 'bg-green-100 text-green-700' : 'bg-red-100 text-red-600'}`}>
                          {p.stock}
                        </span>
                      </td>
                      <td className="px-4 py-3">
                        <div className="flex gap-2">
                          <button onClick={() => openEdit(p)} className="text-xs text-blue-600 hover:underline">Düzenle</button>
                          <button onClick={() => handleDelete(p.id)} className="text-xs text-red-500 hover:underline">Sil</button>
                        </div>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
              {products?.length === 0 && (
                <div className="text-center py-8 text-gray-400 text-sm">Ürün yok</div>
              )}
            </div>
          )}
        </div>
      )}

      {tab === 'categories' && (
        <div className="max-w-md">
          <h2 className="text-lg font-semibold text-gray-800 mb-4">Kategori Oluştur</h2>
          <div className="bg-white rounded-xl border border-gray-200 p-6 shadow-sm">
            <form onSubmit={handleCategorySubmit} className="flex gap-3">
              <input
                type="text"
                required
                value={categoryForm.name}
                onChange={(e) => setCategoryForm({ name: e.target.value })}
                placeholder="Kategori adı"
                className="flex-1 px-3 py-2 border border-gray-200 rounded-lg text-sm outline-none focus:border-[#ff6000] focus:ring-1 focus:ring-[#ff6000]"
              />
              <button type="submit" className="bg-[#ff6000] text-white text-sm font-medium px-4 py-2 rounded-lg hover:bg-[#e05500] transition-colors whitespace-nowrap">
                Oluştur
              </button>
            </form>
          </div>
        </div>
      )}
    </div>
  )
}
