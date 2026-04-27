import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { register } from '../api/auth'
import { useAuthStore } from '../store/authStore'

export default function RegisterPage() {
  const navigate = useNavigate()
  const { setAuth } = useAuthStore()
  const [form, setForm] = useState({ username: '', email: '', password: '' })
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setError('')
    setLoading(true)
    try {
      const data = await register(form)
      setAuth(data.accessToken, data.refreshToken, form.username)
      navigate('/')
    } catch (err: unknown) {
      const msg = (err as { response?: { data?: { message?: string } } })?.response?.data?.message
      setError(msg || 'Kayıt başarısız')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="min-h-[80vh] flex items-center justify-center px-4">
      <div className="bg-white rounded-2xl shadow-sm p-8 w-full max-w-md">
        <Link to="/" className="flex items-center justify-center mb-6">
          <span className="text-3xl font-bold text-[#ff6000]">Shopra</span>
        </Link>
        <h1 className="text-xl font-bold text-gray-900 text-center mb-6">Üye Ol</h1>

        {error && (
          <div className="bg-red-50 border border-red-200 text-red-600 text-sm px-4 py-3 rounded-lg mb-4">
            {error}
          </div>
        )}

        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Kullanıcı Adı</label>
            <input
              type="text"
              required
              minLength={3}
              value={form.username}
              onChange={(e) => setForm({ ...form, username: e.target.value })}
              className="w-full px-4 py-2.5 border border-gray-200 rounded-lg text-sm outline-none focus:border-[#ff6000] focus:ring-1 focus:ring-[#ff6000] transition"
              placeholder="kullanici_adi"
            />
          </div>
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">E-posta</label>
            <input
              type="email"
              required
              value={form.email}
              onChange={(e) => setForm({ ...form, email: e.target.value })}
              className="w-full px-4 py-2.5 border border-gray-200 rounded-lg text-sm outline-none focus:border-[#ff6000] focus:ring-1 focus:ring-[#ff6000] transition"
              placeholder="ornek@mail.com"
            />
          </div>
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Şifre</label>
            <input
              type="password"
              required
              minLength={6}
              value={form.password}
              onChange={(e) => setForm({ ...form, password: e.target.value })}
              className="w-full px-4 py-2.5 border border-gray-200 rounded-lg text-sm outline-none focus:border-[#ff6000] focus:ring-1 focus:ring-[#ff6000] transition"
              placeholder="En az 6 karakter"
            />
          </div>
          <button
            type="submit"
            disabled={loading}
            className="w-full bg-[#ff6000] hover:bg-[#e05500] disabled:bg-gray-300 text-white font-semibold py-3 rounded-xl transition-colors mt-2"
          >
            {loading ? 'Kayıt yapılıyor...' : 'Üye Ol'}
          </button>
        </form>

        <p className="text-center text-sm text-gray-500 mt-6">
          Zaten üye misin?{' '}
          <Link to="/login" className="text-[#ff6000] font-medium hover:underline">
            Giriş Yap
          </Link>
        </p>
      </div>
    </div>
  )
}
