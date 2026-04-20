<img width="937" height="922" alt="image" src="https://github.com/user-attachments/assets/ae22b1e7-7f12-44a1-b360-3f34ce463273" />

# Ödeme Sistemi

Modern bir ödeme sistemi uygulaması. SOLID prensipleri, Annotation, Reflection, AOP, Spring Boot ve React kullanılarak geliştirilmiştir.


├── payment-system-spring/  # Spring Boot - AOP + REST API + PostgreSQL

└── payment-ui/             # React Frontend (ayrı proje)

## Geliştirme Süreci

Bu proje bootcamp sürecinde öğrenilen konular aşama aşama uygulanarak geliştirilmiştir.

- `payment-system-spring/` → 3-6. aşama: Spring Boot ile AOP, REST API, PostgreSQL entegrasyonu geliştirildi.
- `payment-ui/` → React ile ayrı bir frontend projesi olarak geliştirildi.

## Öğrenilen Konular ve Uygulamalar

### 1. SOLID Prensipleri

- **Single Responsibility:** Her sınıf tek bir sorumluluk taşır. `PaymentController` sadece HTTP isteklerini karşılar, `PaymentServiceImpl` sadece iş mantığını yönetir, `PaymentTransactionMapper` sadece dönüşüm yapar.
- **Open/Closed:** Yeni ödeme yöntemi eklemek için mevcut sınıflara dokunulmaz. Sadece `IPayment`'ı implemente eden yeni bir sınıf yazılır.
- **Liskov Substitution:** `CreditCardPayment`, `ApplePayPayment`, `N11PayPayment` gibi tüm sınıflar `IPayment` yerine geçebilir.
- **Interface Segregation:** `IPayment` sadece `pay()` ve `getPaymentName()` metodlarını içerir.
- **Dependency Inversion:** `PaymentProcessor` somut sınıfa değil `IPayment` interface'ine bağlıdır.

### 2. Custom Annotation + Reflection

Her ödeme sınıfına `@PaymentMethod` annotation'ı eklenerek isim bilgisi taşınmaktadır. `PaymentConfig` bu annotation'ları reflection ile okuyarak Spring bean'lerini dinamik olarak Map'e dönüştürür.

```java
@PaymentMethod(name = "N11Pay", order = 4)
public class N11PayPayment implements IPayment { ... }
```

### 3. AOP (Aspect Oriented Programming)

`PaymentLoggingAspect` sınıfı, `PaymentServiceImpl.processPayment()` metoduna dokunmadan loglama ve süre ölçme işlemlerini gerçekleştirir.

[LOG] Ödeme işlemi başlıyor...

[LOG] Metod: processPayment

250.0 USD paid with Credit Card.

[LOG] Ödeme işlemi tamamlandı.

[LOG] Süre: 3ms

### 4. Spring Boot + Katmanlı Mimari

Controller → Service (Interface + Impl) → Repository → Entity

- **DTO:** Controller ile Service arasında veri taşıma
- **Mapper:** Entity → DTO dönüşümü
- **Config:** CORS ve Bean konfigürasyonu

### 5. PostgreSQL + JPA

`PaymentTransaction` entity'si veritabanında `payment_transactions` tablosuna eşleştirilmiştir. `JpaRepository` ile SQL yazmadan CRUD işlemleri gerçekleştirilmektedir.

### 6. React Frontend

Kullanıcı arayüzü `payment-ui/` dizininde ayrı bir React projesi olarak geliştirilmiştir. Ödeme yöntemi seçimi, tutar girişi ve işlem geçmişi görüntüleme özellikleri bulunmaktadır.

## Kurulum

### Gereksinimler

- Java 21
- Maven
- PostgreSQL
- Node.js

> **Not:** `./mvnw` komutu çalışmazsa `JAVA_HOME` environment variable'ının
> Java 21'i gösterdiğinden emin ol. Alternatif olarak IntelliJ IDEA kullanarak
> `PaymentSystemSpringApplication.java` dosyasını doğrudan çalıştırabilirsin.


### PostgreSQL Kurulumu
1. PostgreSQL kur
2. `n11bootcamp` adında bir veritabanı oluştur:
```sql
CREATE DATABASE n11bootcamp;
```

### Backend

#### IntelliJ IDEA ile (Önerilen)

1. IntelliJ'de projeyi aç
2. Sol panelde `payment-system-spring/pom.xml` dosyasına sağ tıkla
3. **"Add as Maven Project"** seç
4. `PaymentSystemSpringApplication.java` dosyasını aç
5. Yeşil ▶ butonuna bas

**Mac/Linux:**
```bash
cd payment-system-spring
./mvnw spring-boot:run
```

**Windows:**
```bash
cd payment-system-spring
mvnw.cmd spring-boot:run
```

`application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/n11bootcamp
spring.datasource.username=postgres
spring.datasource.password=your_password
```

### Frontend

```bash
cd payment-ui
npm install
npm start
```

## API Endpointleri

| Method | URL | Açıklama |
|--------|-----|----------|
| GET | /api/payments/methods | Ödeme yöntemlerini listeler |
| POST | /api/payments/pay | Ödeme işlemi gerçekleştirir |
| GET | /api/payments/transactions | Tüm işlemleri listeler |
