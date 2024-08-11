# Spring demo

* 框架
1. spring-boot
2. baomidou
3. knife4j

* 配置
1. HttpMessageConverterConfig：输入 StrimTrim，输出 ClobToString、……，日期格式转换。
2. SwaggerConfig：配置 knife4j
3. MybatisPlusInterceptor：为 mybatis 添加 baomidou 拦截器
4. MybatisPlusConfig：“@RequestBody @Validated vo”、“@Validated vo”处理参数校验异常

* 事务
1. transactional service：专门提出写事务的方法
