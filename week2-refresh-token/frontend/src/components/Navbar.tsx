import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { useAuthStore } from '../store/authStore'
import { logout as logoutApi } from '../api/auth'
import { useQuery } from '@tanstack/react-query'
import { getCart } from '../api/cart'

export default function Navbar() {
  const { username, isAdmin, accessToken, refreshToken, logout } = useAuthStore()
  const navigate = useNavigate()
  const [search, setSearch] = useState('')

  const { data: cart } = useQuery({
    queryKey: ['cart'],
    queryFn: getCart,
    enabled: !!accessToken,
  })

  const totalItems = cart?.items?.reduce((s: number, i) => s + i.quantity, 0) ?? 0

  const handleSearch = (e: React.FormEvent) => {
    e.preventDefault()
    if (search.trim()) navigate(`/?search=${encodeURIComponent(search.trim())}`)
  }

  const handleLogout = async () => {
    if (refreshToken) await logoutApi(refreshToken).catch(() => {})
    logout()
    navigate('/')
  }

  return (
    <header className="bg-[#ff6000] shadow-md sticky top-0 z-50">
      <div className="max-w-7xl mx-auto px-4">
        <div className="flex items-center gap-4 h-16">
          <Link to="/" className="text-white font-bold text-2xl tracking-tight shrink-0">
            Shopra
          </Link>

          <form onSubmit={handleSearch} className="flex-1 max-w-2xl">
            <div className="flex">
              <input
                value={search}
                onChange={(e) => setSearch(e.target.value)}
                placeholder="Ürün, kategori veya marka ara..."
                className="w-full px-4 py-2 text-sm rounded-l-md outline-none border-0"
              />
              <button
                type="submit"
                className="bg-[#e05500] hover:bg-[#cc4d00] text-white px-4 py-2 rounded-r-md text-sm font-medium transition-colors"
              >
                Ara
              </button>
            </div>
          </form>

          <div className="flex items-center gap-3 shrink-0">
            {accessToken ? (
              <>
                <span className="text-white text-sm hidden sm:block">
                  Merhaba, <strong>{username}</strong>
                </span>
                {isAdmin && (
                  <Link
                    to="/admin"
                    className="text-white text-sm hover:underline hidden sm:block"
                  >
                    Admin
                  </Link>
                )}
                <button
                  onClick={handleLogout}
                  className="text-white text-sm hover:underline"
                >
                  Çıkış
                </button>
              </>
            ) : (
              <>
                <Link to="/login" className="text-white text-sm hover:underline">
                  Giriş Yap
                </Link>
                <Link
                  to="/register"
                  className="bg-white text-[#ff6000] text-sm font-semibold px-3 py-1.5 rounded-md hover:bg-orange-50 transition-colors"
                >
                  Üye Ol
                </Link>
              </>
            )}

            <Link to="/cart" className="relative text-white">
              <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2}
                  d="M3 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-2.293 2.293c-.63.63-.184 1.707.707 1.707H17m0 0a2 2 0 100 4 2 2 0 000-4zm-8 2a2 2 0 11-4 0 2 2 0 014 0z" />
              </svg>
              {totalItems > 0 && (
                <span className="absolute -top-2 -right-2 bg-white text-[#ff6000] text-xs font-bold rounded-full w-5 h-5 flex items-center justify-center">
                  {totalItems}
                </span>
              )}
            </Link>
          </div>
        </div>
      </div>
    </header>
  )
}
