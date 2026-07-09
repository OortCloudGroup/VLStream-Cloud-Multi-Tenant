# VLStream Cloud 多租户版后端服务

## 项目简介

VLStream Cloud 多租户版是基于 Spring Boot 3 + MyBatis-Plus + MySQL 技术栈开发的智能视频流管理系统，支持多租户字段隔离模式。本项目为开源项目，部分核心服务依赖 [OORT.sh](https://oort.sh) 平台进行部署。

> 📌 **单租户版本**：如需完全开源的单租户版本，请访问 [VLStream-Cloud](https://github.com/oortcloud/VLStream-Cloud)

## 技术栈

- **框架**: Spring Boot 3.2.10
- **底层框架**: BladeX 4.8.1
- **ORM**: MyBatis-Plus 3.5.8
- **数据库**: MySQL 8.0+
- **缓存**: Redis 6.0+
- **API 文档**: Knife4j (OpenAPI 3)
- **多租户**: blade-starter-tenant (字段隔离模式)
- **工具库**: Hutool 5.8.35
- **Java 版本**: JDK 17

## 项目结构

```
VLStream-Cloud-Multi-Tenant/
├── src/
│   ├── main/
│   │   ├── java/org/springblade/
│   │   │   ├── vlstream/           # 业务领域实现
│   │   │   │   ├── controller/     # 控制器层
│   │   │   │   ├── service/        # 业务逻辑层
│   │   │   │   ├── mapper/         # 数据访问层
│   │   │   │   ├── pojo/           # 实体/DTO/VO
│   │   │   │   ├── config/         # 业务配置
│   │   │   │   ├── detection/      # 检测算法相关
│   │   │   │   ├── protocol/       # 协议处理
│   │   │   │   └── wrapper/        # 包装器
│   │   │   ├── common/             # 通用模块
│   │   │   ├── job/                # 定时任务
│   │   │   └── modules/            # 功能模块
│   │   └── resources/
│   │       ├── application.yml     # 主配置
│   │       ├── application-dev.yml # 开发环境配置
│   │       └── mapper/             # MyBatis XML 映射文件
│   └── test/                       # 测试代码
├── doc/
│   ├── sql/                        # 数据库脚本
│   └── script/                     # 部署脚本
├── pom.xml                         # Maven 配置
└── README.md                       # 项目说明
```

## 核心功能模块

### 1. 设备管理
- 设备信息增删改查
- 设备状态监控
- 设备分组与标签管理
- 设备连接测试

### 2. 算法管理
- 算法仓库管理
- 算法模型管理
- 算法训练任务
- 算法编排

### 3. 智能分析
- 分析请求管理
- 分析结果查询
- 实时分析监控

### 4. 监控告警
- 设备告警管理
- 告警规则配置
- 告警处理流程

### 5. 场景治理
- 场景治理配置
- 移动端场景治理
- 事件策略管理

### 6. 多租户支持
- 基于字段隔离的多租户模式
- 租户数据自动过滤
- 租户级权限控制

## 快速开始

### 环境要求

- JDK 17+
- Maven 3.6+
- MySQL 8.0+
- Redis 6.0+

### 安装步骤

1. **创建数据库**
```sql
CREATE DATABASE vls_stream CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. **执行 SQL 脚本**
```bash
mysql -u root -p vls_stream < doc/sql/bladex/*.sql
```

3. **修改配置**
编辑 `src/main/resources/application-dev.yml` 修改数据库和 Redis 连接信息

4. **编译项目**
```bash
mvn clean compile -DskipTests
```

5. **启动应用**
```bash
mvn spring-boot:run
```

6. **访问 API 文档**
启动成功后访问：http://localhost:8080/doc.html

## API 接口

### 设备管理 API

| 方法 | 路径 | 描述 |
|------|------|------|
| GET | /api/vls/device/page | 分页查询设备信息 |
| GET | /api/vls/device/{id} | 根据 ID 查询设备 |
| POST | /api/vls/device | 新增设备 |
| PUT | /api/vls/device | 更新设备 |
| DELETE | /api/vls/device/{id} | 删除设备 |

### 响应格式

所有 API 响应遵循统一格式：

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {},
  "success": true
}
```

## 部署说明

### 开源部署

本项目代码完全开源，可自行编译部署。需要配置以下依赖服务：
- MySQL 数据库
- Redis 缓存
- MQTT 消息队列（可选）

### OORT.sh 平台部署

部分核心服务（如 AI 推理、视频流处理）依赖 OORT.sh 平台能力，推荐使用 OORT.sh 进行生产环境部署。

访问 [OORT.sh](https://oort.sh) 了解更多信息。

## 与单租户版本的区别

| 特性 | 单租户版本 | 多租户版本 |
|------|-----------|-----------|
| 多租户支持 | ❌ | ✅ 字段隔离 |
| Java 版本 | JDK 8+ | JDK 17 |
| Spring Boot | 2.7.18 | 3.2.10 |
| 底层框架 | 独立 Spring Boot | BladeX |
| 开源程度 | 完全开源 | 开源 + OORT.sh 依赖 |

## 许可证

本项目采用 [MIT License](LICENSE) 开源协议。

## 联系方式

- **项目主页**: https://vls.oortcloudsmart.com
- **技术支持**: zhangxuelian@oortcloudsmart.com
- **OORT 平台**: https://oort.sh
