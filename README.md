# Real Estate Listing App - Backend

## Tech Stack
- Java 17 + Spring Boot 3.2
- Spring Security + JWT
- MySQL + Spring Data JPA
- Lombok

## Setup Instructions

### 1. Create MySQL Database
```sql
CREATE DATABASE real_estate_db;
```

### 2. Update application.properties
```
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
jwt.secret=YOUR_SECRET_KEY_MIN_32_CHARS
```

### 3. Run the App
```bash
mvn spring-boot:run
```

## API Endpoints

### Auth
| Method | URL | Description |
|--------|-----|-------------|
| POST | /api/auth/register | Register (roles: BUYER, SELLER, AGENT, ADMIN) |
| POST | /api/auth/login | Login → returns JWT token |

### Properties
| Method | URL | Auth | Description |
|--------|-----|------|-------------|
| GET | /api/properties | No | Get all properties |
| GET | /api/properties/{id} | No | Get property by ID |
| GET | /api/properties/featured | No | Get featured properties |
| GET | /api/properties/search?city=&type=&minPrice=&maxPrice= | No | Search properties |
| POST | /api/properties | Yes | Add property |
| DELETE | /api/properties/{id} | Yes | Delete property |

### Inquiries (Buyer Interest System)
| Method | URL | Role | Description |
|--------|-----|------|-------------|
| POST | /api/inquiries | BUYER | Submit interest in a property |
| GET | /api/inquiries/buyer | BUYER | View my inquiries |
| GET | /api/inquiries/seller | SELLER/AGENT | View inquiries on my properties |
| PATCH | /api/inquiries/{id}/status?status=CONTACTED | SELLER/AGENT | Update inquiry status |

## Inquiry Status Flow
PENDING → CONTACTED → (done)
PENDING → REJECTED

## Sample Register Request
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123",
  "role": "BUYER"
}
```

## Sample Property Request
```json
{
  "title": "2BHK Apartment in Chennai",
  "description": "Beautiful apartment near beach",
  "price": 5000000,
  "location": "Anna Nagar",
  "city": "Chennai",
  "state": "Tamil Nadu",
  "area": 1200,
  "bedrooms": 2,
  "bathrooms": 2,
  "type": "APARTMENT",
  "listingType": "SELL",
  "imageUrl": "https://example.com/image.jpg",
  "featured": true
}
```

## Sample Inquiry Request
```json
{
  "propertyId": 1,
  "message": "I am interested in this property. Please contact me."
}
```
