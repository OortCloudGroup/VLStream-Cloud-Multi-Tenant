# AGENTS.md
本文件用于指导 Codex 在此代码仓库中工作时的行为规范。

> 本规范适用于 本项目的所有开发任务，为强制性条款。除非用户显式豁免，任何条目都不得忽视或删减。
>
> 作为 AI 助手参与本项目开发时，你必须：
> - 每次输出前深度理解 本项目架构、档案业务逻辑和 Spring Boot 技术栈特征
> - 当回答依赖外部知识时，先查询 Spring Boot 3.x、MyBatis-Plus等官方文档
> - 引用外部资料须注明来源（链接或版本号）
> - 若需求含糊，先复述已知信息并列出关键澄清问题
> - 同一次回复中的术语、变量名、逻辑描述保持一致
> - 仅回答并处理与问题直接相关的内容，避免冗余
> - 面对复杂需求，先拆分为可管理的子任务
>
> 所有开发内容必须建立在深度思考过的基础之上，禁止机械生成与错误填充。
> 如果你已了解所有规范，请在用户第一次对话时说明："我已充分了解 本项目开发规范。"

## 1. 开发规范

### 1.1 项目架构
```
apaas-vls-server/
|- src/
|  |- main/
|  |  |- java/org/springblade/{vlstream,common,job,modules/}
|  |  |- resources/{application-*.yml,mapper,log,static,i18n}
|  |- test/{java/org/springblade/test,resources}
|- doc/{script/{docker,fatjar},sql/{ai,...}}
|- Dockerfile

```

### 1.2 当你需要了解架构时
1. 熟悉项目整体架构:
    - vlstream：业务领域 API 实现；
    - common：Blade 启动及通用支持模块；
    - job：定时任务与调度；
    - modules：功能模块，如 auth、database、develop、system；
    - doc/：用于打包与数据库架构（schema）管理的脚本与资源。
2. 理解 档案业务模型:
  - 项目详细设计方案
   
### 1.3 当你需要了解技术栈时
1. Java 版本：17
2. Spring Boot：3.2.10
3. 数据库访问：MyBatis-Plus: 3.5.8
4. 构建工具：Maven 3.11.0

### 1.4 当你尝试编写一个新功能、添加新类或书写 Javadoc 时
1. 你需要先查看其他类的实现，尤其是相同模块中的类
2. 主动学习、模仿现有代码的结构与风格，包括:
    - 缩进与换行格式
    - 命名习惯
    - Javadoc 注释风格

### 1.5 你必须制定清晰的 ToDo 任务清单，并在开发完成后遵循以下规则
1. 使用 Maven 的 `mvn clean compile -DskipTests` 命令 尝试打包
2. 若出现编译错误，必须修复后重新编译，直至编译通过
3. 若需要为当前模块引入其他模块作为依赖:
    - 先检查是否会造成循环依赖
    - 若有循环依赖，则采用更优的方案避免
4. 编译全部通过后，将完整测试流程交由用户执行，由用户反馈日志进行进一步调整，你不能自己执行任何的测试
5. 除非用户有明确说明，否则你不应撰写任何示例（example）或额外文档

## 2. 编码规范

### 2.1 命名规范
1. 包名规范：
    - 统一前缀：`org.springblade.vlstream`
2. 类名规范：
    - Entity 类：`XxxEntity`（如 `DeviceEntity`）
    - DTO 类：`XxxDTO`（如 `DeviceDTO`）
    - VO 类：`XxxVO`（如 `DeviceVO`）
    - Service 接口：`IXxxService`（如 `IDeviceService`）
    - Service 实现：`XxxServiceImpl`（如 `DeviceServiceImpl`）
    - Controller：`XxxController`（如 `DeviceController`）

### 2.2 命名规范详细要求
1. 变量和方法命名必须具有明确语义：
    - ✅ 正确：`Exception exception`、`DeviceEntity device`、`List<DeviceEntity> deviceList`
    - ❌ 错误：`Exception e`、`DeviceEntity d`、`List<DeviceEntity> list`
2. 出现命名冲突时，提升语义层级：
    - ✅ 正确：`Cache cache; Cache redisCache`
    - ❌ 错误：`Cache cache1; Cache cache2`

### 2.3 Lombok 规范
1. 禁止生成大量的 getter/setter 方法
2. 使用 Lombok 注解简化代码
3. 常用注解：
    - `@Data`：自动生成 getter、setter、toString、equals、hashCode 方法
    - `@AllArgsConstructor`：生成全参构造函数
    - `@NoArgsConstructor`：生成无参构造函数
    - `@RequiredArgsConstructor`：生成必需参数构造函数

### 2.4 MyBatis-Plus 使用规范
```java
// Entity 示例
@Data
@TableName("iot_device")
@Schema(description = "设备表")
@EqualsAndHashCode(callSuper = true)
public class DeviceEntity extends BaseEntity {

    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @Schema(description = "租户ID")
    private String tenantId;
    
    @TableField("product_key")
    @Schema(description = "产品id")
    private String productKey;
    
    @TableField("device_name")
    @Schema(description = "设备名称")
    private String deviceName;
    
}

// Mapper 示例
@Mapper
public interface DeviceMapper extends BaseMapper<DeviceEntity> {
    // 复杂查询写在 XML 中
    List<DeviceVO> selectDeviceList(@Param("query") DeviceQuery query);
}

// Service 示例
@Service
@RequiredArgsConstructor
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, DeviceEntity> implements IDeviceService {
    
    private final DeviceMapper deviceMapper;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean createDevice(DeviceDTO dto) {
        // 参数校验
        Assert.notNull(dto.getProductKey(), "产品标识不能为空");
        
        // 业务逻辑
        DeviceEntity entity = BeanUtil.copy(dto, DeviceEntity.class);
        
        return save(entity);
    }
}
```

### 2.5 API 接口规范
```java
@RestController
@RequiredArgsConstructor
@RequestMapping(IotConstant.APP_KEY + "/device")
@Tag(name = "设备管理", description = "设备管理接口")
public class DeviceController extends BladeController {
    
    private final IDeviceService deviceService;
    
    @PostMapping("/save")
    @ApiOperationSupport(order = 1)
    @Operation(summary = "新增设备", description = "传入device")
    public R<Boolean> save(@Valid @RequestBody DeviceEntity device) {
        return R.status(deviceService.save(device));
    }
    
    @GetMapping("/detail")
    @ApiOperationSupport(order = 2)
    @Operation(summary = "设备详情", description = "传入device")
    public R<DeviceVO> detail(DeviceEntity device) {
        DeviceEntity detail = deviceService.getOne(Condition.getQueryWrapper(device));
        return R.data(DeviceWrapper.build().entityVO(detail));
    }
    
    @GetMapping("/page")
    @ApiOperationSupport(order = 3)
    @Operation(summary = "分页查询", description = "传入groupId")
    public R<IPage<DeviceVO>> page(Long groupId, Query query) {
        IPage<DeviceVO> pages = deviceService.selectDevicePage(Condition.getPage(query), groupId);
        return R.data(pages);
    }
}
```

### 2.6. Java 版本与特性使用规范
1. 在编写代码之前，必须了解当前项目所使用的 Java 版本，确保所使用的语法和特性与该版本兼容
    - 例如：若当前项目基于 Java 17，则可以使用增强 switch、Text Blocks 等特性
2. 除非另有说明，否则禁止使用 `var`、`val` 等类型推断关键字，即使当前 Java 版本支持也不允许
    - 所有变量必须显式声明类型，以提高代码可读性和明确性
3. 建议在 Java 版本允许的前提下，合理使用语法糖以提升代码简洁度和表达力，例如：
    - 增强 switch 表达式（Java 12+）：
      `
      String result = switch (status) {
          case 1 -> "STARTED";
          case 2 -> "STOPPED";
          default -> "UNKNOWN";
      };
      `

    - Text Blocks（Java 13+）：
      `
      String json = """
          {
              "name": "test",
              "enabled": true
          }
          """;
      `

    - Pattern Matching for instanceof（Java 16+）：
      `
      if (obj instanceof String str) {
          Log.info("Length: %d", str.length());
      }
      `
4. 数据处理优先使用 `Stream API`：
    - 避免使用传统 `for` 循环完成可以用 `stream` 完成的链式操作
    - 确保代码简洁、函数式、可读性强
    - Lambda 表达式需保持简洁：
        - ✅ 正确：`() -> "something"`、`list.forEach(CustomExecutor::shutdown)`
        - ❌ 错误：`(x) -> { doSomething(x); }`、`() -> { return "something"; }`
5. 禁止使用项目未明确支持的 Java 语法与特性，避免在构建、部署等阶段产生兼容性问题
    - 若不确定某语法是否可用，请先 web 搜索，查阅 Java 官方版本说明，或询问维护者确认项目支持范围

## 3. 数据库规范

### 3.1 表设计规范
1. 表名前缀：
    - IoT 业务表：`biz_`（如 `biz_device`、`biz_product`）
    - 系统表：`blade_`（如 `blade_user`、`blade_role`）
2. 字段规范(业务表必须包含)：
    - 主键：`id` (BIGINT)
    - 租户ID：`tenant_id` (VARCHAR(64))
    - 创建人：`create_user` (BIGINT)
    - 创建部门：`create_dept` (VARCHAR(64))
    - 创建时间：`create_time` (DATETIME)
    - 更新人：`update_user` (BIGINT)
    - 更新时间：`update_time` (DATETIME)
    - 逻辑删除：`is_deleted` (INT)
3. 索引规范：
    - 唯一索引：`uk_` 前缀（如 `uk_device_name`）
    - 普通索引：`idx_` 前缀（如 `idx_product_key`）
    - 联合索引按查询频率排序

### 3.2 MyBatis-Plus 查询规范，禁止使用JDBC直接查询
```java
LambdaQueryWrapper<DeviceEntity> wrapper = Wrappers.<DeviceEntity>lambdaQuery()
    .eq(DeviceEntity::getProductKey, productKey)                    // 等于
    .ne(DeviceEntity::getStatus, DeviceStatus.DELETED)              // 不等于
    .gt(DeviceEntity::getCreateTime, startTime)                     // 大于
    .ge(DeviceEntity::getUpdateTime, updateTime)                    // 大于等于
    .lt(DeviceEntity::getId, maxId)                                 // 小于
    .le(DeviceEntity::getVersion, currentVersion)                   // 小于等于
    .like(DeviceEntity::getDeviceName, "sensor")                    // 模糊查询
    .likeLeft(DeviceEntity::getDeviceCode, "x")                     // 左模糊
    .likeRight(DeviceEntity::getDeviceModel, "v1")                 // 右模糊
    .in(DeviceEntity::getStatus, Arrays.asList(ONLINE, OFFLINE))    // IN查询
    .notIn(DeviceEntity::getType, Arrays.asList("TEST", "DEMO"))   // NOT IN
    .isNull(DeviceEntity::getDeleteTime)                            // 为空
    .isNotNull(DeviceEntity::getActiveTime)                         // 不为空
    .between(DeviceEntity::getCreateTime, startDate, endDate)       // 区间查询
    .orderByDesc(DeviceEntity::getCreateTime)                       // 降序排序
    .orderByAsc(DeviceEntity::getDeviceName);                        // 升序排序
```

## 4. 单元测试规范
```java
@ExtendWith(BladeSpringExtension.class)
@BladeBootTest(appName = "blade-runner", enableLoader = true)
public class BladeTest {

    @Autowired
    private IDeviceService deviceService;

    @Test
    public void contextLoads() {
        List<DeviceEntity> list = CacheUtil.get(IOT_CACHE, DEVICE_ID, "0", () -> deviceService.list());
        assert list != null;
        list.forEach(deviceEntity -> {
            System.out.println("===============");
            System.out.println(deviceEntity.getId());
            System.out.println(deviceEntity.getDeviceName());
            System.out.println("===============");
        });
    }

}
```

## 5. 日志规范

### 5.1 日志输出要求
1. 包含关键业务标识（如 categoryName、categoryId、tableId 等）
2. 避免打印敏感信息（如 密码、密钥 等）
3. 使用占位符而非字符串拼接
4. 异常日志必须包含堆栈信息

### 5.2 日志使用规范
```java
@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceDataProcessService {
    
    public void handleDeviceStatusChange(String productKey, String deviceName, DeviceStatus newStatus) {
        DeviceStatus oldStatus = getDeviceCurrentStatus(productKey, deviceName);
        
        // ✅ 正确用法：日志中包含关键业务信息
        log.info("设备状态变更: productKey={}, deviceName={}, 旧状态={}, 新状态={}", 
                productKey, deviceName, oldStatus, newStatus);
        
        // ❌ 错误用法：字符串拼接
        // log.info("设备状态变更: productKey=" + productKey + ", deviceName=" + deviceName);
        
        // ❌ 错误用法：String.format
        // log.info(String.format("设备状态变更: productKey=%s, deviceName=%s", productKey, deviceName));
        
        // ❌ 错误用法：变量名过于简单
        // Exception e = new Exception("测试");
        // log.error("处理失败", e);
        
        // ✅ 正确用法：变量名具有明确语义
        try {
            updateDeviceStatus(productKey, deviceName, newStatus);
        } catch (Exception statusUpdateException) {
            log.error("更新设备状态失败: productKey={}, deviceName={}, 目标状态={}", productKey, deviceName, newStatus, statusUpdateException);
        }
    }
}
```

## 7. 自主学习与风格一致性要求
1. 你必须具备 "自我学习、自我适配" 的意识，凡遇风格不确定、规则模糊的场景，须优先：
    - 主动查阅现有代码
    - 模仿当前模块的实现方式
    - 避免引入破坏风格一致性的代码
2. 遇到新模块、空白模块时：
    - 可以参考 `org.springblade.vlstream.*` 包中的类为基本风格模板
    - 所有新的命名风格、设计结构，必须与项目现有命名方式协调一致
    - 若存在不确定性，且确认已经进行学习后仍然不清楚，可暂时标记 `// TODO: 确认命名规范` 后告诉开发组，待开发组确认后再定稿
3. 在大型重构或多人协作中，应保持沟通、拉取最新代码，确保提交不会引起大面积冲突或风格割裂
4. 若现有模块已满足需求，禁止自写替代实现；如确认存在缺口，需在 Pull Request 描述和 commit 信息中同时说明：
    - 已检索过的相关类或方法
    - 为什么现有实现不足
    - 新实现的范围与改进点
5. 对任何新功能，先阅读相关模块的 README / Javadoc / 示例，确保理解其使用方式与边界，再决定是否扩展
6. 只有当现有实现无法满足需求或存在严重缺陷时，才可自行重构或实现，并必须在 commit 信息与 PR 描述里写明评估过程与理由，避免后续重复开发

## 8. Git 提交规范（Commit Message）
1. 提交信息必须简洁明确，并符合项目现有格式风格。你需要：
    - 查看近期的 Git 历史记录（`git log --oneline` 或 `git log -n 10`）
    - 学习当前项目所使用的 commit 语言风格、结构模板等
    - 避免使用口语、不相关内容或个人语气
    - 若项目采用语义化提交规范（Conventional Commits），你必须严格遵守其格式
2. 通用规范（若无特殊格式要求）：
    - ✅ 正确示例：
        - `Add: 实现实时设备状态监控仪表板`
        - `Fix: 修复设备管理器中的设备连接超时问题`
        - `Refactor: 从数据采集器中提取传感器数据处理方法`
        - `Docs: 更新设备管理接口API文档`
    - ❌ 错误示例：
        - `修复一下问题`
        - `update`
        - `提交代码`
3. 推荐格式（若尚无约定）：
    ```
    <类型>: <简要说明（默认中文，如果要求英文则首字母大写）>
   
    <可选说明：描述变更原因、影响范围、解决的问题等>
    ```
   常见类型包括：
    - `Add:` 新增功能、类、方法
    - `Fix:` 修复 bug
    - `Refactor:` 重构（不涉及功能行为改变）
    - `Docs:` 修改文档或注释
    - `Test:` 添加或修复测试
    - `Chore:` 杂项、配置项更新等
4. 合并（merge）请求前，务必 squash commit 或整理历史，保持提交记录清晰有序


## 9. 回答语言规范（即你与用户交流时）
1. 你在与用户交互时必须全程使用中文
2. 若希望临时切换语言，可以明确告知，否则始终保持中文为默认沟通语言