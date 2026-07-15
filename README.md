# VLStream Cloud Multi-Tenant Backend Service

[中文版](README-cn.md)

## Introduction

VLStream Cloud Multi-Tenant is an intelligent video stream management system built with Spring Boot 3 + MyBatis-Plus + MySQL, supporting multi-tenant field isolation mode. This project is open-source, with some core services depending on the [OORT.sh](https://oort.sh) platform for deployment.

> 📌 **Single-Tenant Version**: For a fully open-source single-tenant version, visit [VLStream-Cloud](https://github.com/oortcloud/VLStream-Cloud)

## Tech Stack

- **Framework**: Spring Boot 3.2.10
- **Base Framework**: BladeX 4.8.1
- **ORM**: MyBatis-Plus 3.5.8
- **Database**: MySQL 8.0+
- **Cache**: Redis 6.0+
- **API Documentation**: Knife4j (OpenAPI 3)
- **Multi-Tenant**: blade-starter-tenant (Field Isolation Mode)
- **Utilities**: Hutool 5.8.35
- **Java Version**: JDK 17

## Project Structure

```
VLStream-Cloud-Multi-Tenant/
├── src/
│   ├── main/
│   │   ├── java/org/springblade/
│   │   │   ├── vlstream/           # Business domain implementation
│   │   │   │   ├── controller/     # Controller layer
│   │   │   │   ├── service/        # Business logic layer
│   │   │   │   ├── mapper/         # Data access layer
│   │   │   │   ├── pojo/           # Entity/DTO/VO
│   │   │   │   ├── config/         # Business configuration
│   │   │   │   ├── detection/      # Detection algorithms
│   │   │   │   ├── protocol/       # Protocol handling
│   │   │   │   └── wrapper/        # Wrappers
│   │   │   ├── common/             # Common modules
│   │   │   ├── job/                # Scheduled tasks
│   │   │   └── modules/            # Functional modules
│   │   └── resources/
│   │       ├── application.yml     # Main configuration
│   │       ├── application-dev.yml # Development environment config
│   │       └── mapper/             # MyBatis XML mapping files
│   └── test/                       # Test code
├── doc/
│   ├── sql/                        # Database scripts
│   └── script/                     # Deployment scripts
├── pom.xml                         # Maven configuration
└── README.md                       # Project documentation
```

## Core Function Modules

### 1. Device Management
- CRUD operations for device information
- Device status monitoring
- Device grouping and tag management
- Device connection testing

### 2. Algorithm Management
- Algorithm repository management
- Algorithm model management
- Algorithm training tasks
- Algorithm orchestration

### 3. Intelligent Analysis
- Analysis request management
- Analysis result queries
- Real-time analysis monitoring

### 4. Monitoring & Alerting
- Device alert management
- Alert rule configuration
- Alert processing workflow

### 5. Scene Governance
- Scene governance configuration
- Mobile scene governance
- Event strategy management

### 6. Multi-Tenant Support
- Multi-tenant mode based on field isolation
- Automatic tenant data filtering
- Tenant-level permission control

## Quick Start

### Prerequisites

- JDK 17+
- Maven 3.6+
- MySQL 8.0+
- Redis 6.0+

### Installation Steps

1. **Create Database**
```sql
CREATE DATABASE vls_stream CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. **Execute SQL Scripts**
```bash
mysql -u root -p vls_stream < doc/sql/bladex/*.sql
```

3. **Modify Configuration**
Edit `src/main/resources/application-dev.yml` to update database and Redis connection information

4. **Compile Project**
```bash
mvn clean compile -DskipTests
```

5. **Start Application**
```bash
mvn spring-boot:run
```

6. **Access API Documentation**
After successful startup, visit: http://localhost:8080/doc.html

## API Endpoints

### Device Management API

| Method | Path | Description |
|--------|------|-------------|
| GET | /api/vls/device/page | Paginate device list |
| GET | /api/vls/device/{id} | Get device by ID |
| POST | /api/vls/device | Create new device |
| PUT | /api/vls/device | Update device |
| DELETE | /api/vls/device/{id} | Delete device |

### Response Format

All API responses follow a unified format:

```json
{
  "code": 200,
  "msg": "Operation successful",
  "data": {},
  "success": true
}
```

## Deployment

### Open Source Deployment

This project is fully open-source and can be compiled and deployed independently. The following dependent services are required:
- MySQL Database
- Redis Cache
- MQTT Message Queue (Optional)

### OORT.sh Platform Deployment

Some core services (such as AI inference, video stream processing) depend on OORT.sh platform capabilities. It is recommended to use OORT.sh for production environment deployment.

Visit [OORT.sh](https://oort.sh) for more information.

## Comparison with Single-Tenant Version

| Feature | Single-Tenant Version | Multi-Tenant Version |
|---------|----------------------|---------------------|
| Multi-Tenant Support | ❌ | ✅ Field Isolation |
| Java Version | JDK 8+ | JDK 17 |
| Spring Boot | 2.7.18 | 3.2.10 |
| Base Framework | Standalone Spring Boot | BladeX |
| Open Source | Fully Open Source | Open Source + OORT.sh Dependencies |

## License

This project is licensed under the [MIT License](LICENSE).

## Contact

- **Project Homepage**: https://vls.oortcloudsmart.com
- **Technical Support**: zhangxuelian@oortcloudsmart.com
- **OORT Platform**: https://sh.oortcloudsmart.com/zh/
