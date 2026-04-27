# Shopra — Mini E-Commerce

N11 TalentHub Backend Bootcamp kapsamında geliştirilen, JWT + Refresh Token mimarisi kullanan mini e-ticaret projesi.

## Teknolojiler

**Backend**
- Java 21 · Spring Boot 3.5.13 · Spring Security
- JWT (jjwt 0.11.5) · Spring Data JPA · PostgreSQL · Docker

**Frontend**
- React 18 · TypeScript · Vite · Tailwind CSS
- React Router v6 · Axios · TanStack Query · Zustand

## Hızlı Başlangıç

### Gereksinimler
- Java 21
- Node.js 18+
- Docker Desktop

### 1. Projeyi klonla

```bash
git clone <repo-url>
cd <proje-klasörü>
```

### 2. Veritabanını başlat

```bash
docker-compose up -d
```

### 3. Backend'i başlat

```bash
./mvnw spring-boot:run
```

→ `http://localhost:8080`

### 4. Frontend'i başlat

```bash
cd frontend
npm install
npm run dev
```

→ `http://localhost:5173`

> `application.properties` dosyasında varsayılan değerler tanımlıdır, `.env` oluşturmana gerek yok. Üretim ortamı için `.env.example` dosyasına bak.

---

## Proje Yapısı

```
├── src/main/java/com/n11bootcamp/refreshtoken/
│   ├── api/          → Controller interface'leri
│   ├── auth/         → JWT filter, token yönetimi
│   ├── config/       → Spring Security konfigürasyonu
│   ├── controller/   → HTTP endpoint'leri
│   ├── converter/    → Entity ↔ DTO dönüşümleri
│   ├── dto/          → Request / Response modelleri
│   ├── exception/    → Custom exception'lar + GlobalExceptionHandler
│   ├── model/        → JPA entity'leri
│   ├── repository/   → Veritabanı arayüzleri
│   └── service/      → İş mantığı
│
├── frontend/
│   └── src/
│       ├── api/      → Axios istekleri
│       ├── components/ → Navbar, ProductCard
│       ├── pages/    → Home, Cart, Login, Register, Admin
│       ├── store/    → Zustand auth store
│       └── types/    → TypeScript tipleri
│
└── docker-compose.yml
```

---

## API Endpoints

### Auth
| Method | URL | Açıklama | Auth |
|--------|-----|----------|------|
| POST | `/api/auth/register` | Kayıt ol | — |
| POST | `/api/auth/login` | Giriş yap | — |
| POST | `/api/auth/refresh` | Token yenile | — |
| POST | `/api/auth/logout` | Çıkış yap | Bearer |

### Ürünler
| Method | URL | Açıklama | Auth |
|--------|-----|----------|------|
| GET | `/api/products` | Tüm ürünler | — |
| GET | `/api/products/{id}` | Ürün detay | — |
| GET | `/api/products/search?name=` | Ürün ara | — |
| GET | `/api/products/category/{id}` | Kategoriye göre | — |

### Sepet
| Method | URL | Açıklama | Auth |
|--------|-----|----------|------|
| GET | `/api/cart` | Sepeti getir | Bearer |
| POST | `/api/cart/items` | Sepete ekle | Bearer |
| PUT | `/api/cart/items/{id}` | Miktar güncelle | Bearer |
| DELETE | `/api/cart/items/{id}` | Sepetten çıkar | Bearer |

### Admin
| Method | URL | Açıklama | Auth |
|--------|-----|----------|------|
| POST | `/api/admin/products` | Ürün ekle | ADMIN |
| PUT | `/api/admin/products/{id}` | Ürün güncelle | ADMIN |
| DELETE | `/api/admin/products/{id}` | Ürün sil | ADMIN |
| POST | `/api/admin/categories` | Kategori ekle | ADMIN |

---

## Refresh Token Mimarisi

```
Login
  └─→ Access Token (60sn*)  +  Refresh Token (7 gün)  →  DB'ye kaydedilir

Her istekte:
  └─→ Authorization: Bearer <accessToken>

Access Token süresi doldu:
  └─→ 401 Unauthorized
        └─→ POST /api/auth/refresh  { refreshToken }
              ├─→ Geçerliyse: Yeni Access Token
              └─→ Geçersiz / iptal edilmişse: 401 → Tekrar login

Logout:
  └─→ Kullanıcının tüm refresh token'ları DB'de revoked = true yapılır
```

> *Access token süresi demo amaçlı 60 saniyedir. Üretimde `JWT_ACCESS_TOKEN_VALIDITY=900000` (15 dk) kullanın.

---

## Hata Yanıtları

Tüm hatalar standart formatta döner:

```json
{
  "status": 404,
  "message": "Ürün bulunamadı",
  "timestamp": "2026-04-24T10:00:00"
}
```

| Durum | HTTP Kodu |
|-------|-----------|
| Kaynak bulunamadı | 404 |
| İş kuralı ihlali | 400 |
| Token hatası | 401 |
| Yetersiz yetki | 403 |
| Sunucu hatası | 500 |
