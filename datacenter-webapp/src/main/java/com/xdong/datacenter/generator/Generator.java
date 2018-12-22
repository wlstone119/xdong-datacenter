package com.xdong.datacenter.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.enums.FieldFill;
import com.weidai.mp.generator.AutoGenerator;
import com.weidai.mp.generator.InjectionConfig;
import com.weidai.mp.generator.config.DataSourceConfig;
import com.weidai.mp.generator.config.GlobalConfig;
import com.weidai.mp.generator.config.PackageConfig;
import com.weidai.mp.generator.config.StrategyConfig;
import com.weidai.mp.generator.config.TemplateConfig;
import com.weidai.mp.generator.config.converts.MySqlTypeConvert;
import com.weidai.mp.generator.config.po.TableFill;
import com.weidai.mp.generator.config.rules.DbColumnType;
import com.weidai.mp.generator.config.rules.DbType;
import com.weidai.mp.generator.config.rules.NamingStrategy;

/**
 * 
 * 类Generator.java的实现描述：mp代码生成器
 * @author wanglei 2018年4月19日 下午3:25:27
 */
public class Generator {

    /**
     * <p>
     * MySQL 生成演示
     * </p>
     */
    public static void main(String[] args) {
        
        String[] bannerTables = { "banner" };
        String[] inviteTables = { "invite_token" };
        String[] insuranceTables = {"insurance_info"};
        String[] dirTables = { "dir_config" };
        String[] discoverTables = { "discover_element", "discover_model" };
        String[] userTables = { "credit_user_extend", "linkman_user","user_car_info","user_crypt","user_huanxin","cancel_account_record" };
        String[] cacheTables = { "cache_redis" };
        String[] mqTables = { "mq_msg" };
        String[] creditTables = { "credit_tag", "credit_tag_history","credit_white_user", "credit_white_user_operation",
                                  "product_credit_tag","user_credit_tag", "user_creditcard_info"};
        String[] drivingTables = { "driving_licence_comparison","driving_licence_ocr_data","driving_license"};
        String[] regionTables = { "user_region","region_group_config" };
        String[] ruleTables = { "rule_audit_log","rule_audit_log_history","rule_invoke" };
        String[] ivrTables = { "ivr_outbound_record", "ivr_outbound_task" };
        String[] ocrTables = { "face_business","face_recognition","idcard_ocr" };
        String[] logTables = { "log_operat" };
        String[] saleTables = { "sale_clue_info" };
        String[] syncTables = { "sync_fail_loan_record" };
        String[] channelTables = { "sms_code_white_channel" };
        String[] ticketTables = { "t_ticket","t_ticket_order","t_user_ticket" };
        String[] feedTables = { "user_feedback_info" };
       // String[] tables = { "image"};
        //String[] tables  = {"rp_songs","rp_crawler_url","rp_songs_sheet"};
        String[] tables  = {"xd_idol","xd_idol_home","xd_idol_home_view","xd_idol_home_view_detail"};
        
        // 自定义需要填充的字段
        List<TableFill> tableFillList = new ArrayList<>();
        tableFillList.add(new TableFill("ASDD_SS", FieldFill.INSERT_UPDATE));

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator()
                .setGlobalConfig(
                        // 全局配置
                        new GlobalConfig().setOutputDir("/Users/stone/Downloads/mp")// 输出目录
                                .setFileOverride(true)// 是否覆盖文件
                                .setActiveRecord(false)// 开启 activeRecord 模式
                                .setEnableCache(false)// XML 二级缓存
                                .setBaseResultMap(true)// XML ResultMap
                                .setBaseColumnList(true)// XML columList
                                // .setKotlin(true) 是否生成 kotlin 代码
                                .setAuthor("wanglei")
                                // 自定义文件命名，注意 %s 会自动填充表实体属性！
                                .setMapperName("%sMapper").setXmlName("%sMapper").setServiceName("I%sService").setServiceImplName("%sServiceImpl")
                                .setControllerName("%sController").setFacadeName("%sFacade").setFacadeImplName("%sFacadeImpl"))
                .setDataSource(
                        // 数据源配置
                        new DataSourceConfig().setDbType(DbType.MYSQL)// 数据库类型
                                .setTypeConvert(new MySqlTypeConvert() {
                                    // 自定义数据库表字段类型转换【可选】
                                    @Override
                                    public DbColumnType processTypeConvert(String fieldType) {
                                        System.out.println("转换类型：" + fieldType);
                                        // if ( fieldType.toLowerCase().contains( "tinyint" ) ) {
                                        // return DbColumnType.BOOLEAN;
                                        // }
                                        return super.processTypeConvert(fieldType);
                                    }
                                }).setDriverName("com.mysql.jdbc.Driver")
                                //.setUsername("uatclmg").setPassword("UATclmg1234!").setUrl("jdbc:mysql://192.168.16.250:6606/clmg?useUnicode=true&characterEncoding=utf-8"))
                                .setUsername("wanglei").setPassword("wanglei")
                              .setUrl("jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=utf-8"))
                .setStrategy(
                        // 策略配置
                        new StrategyConfig()
                                // .setCapitalMode(true)// 全局大写命名
                                // .setDbColumnUnderline(true)//全局下划线命名
                                //.setTablePrefix(new String[] { "mp_" })// 此处可以修改为您的表前缀
                                .setNaming(NamingStrategy.underline_to_camel)// 表名生成策略
                                .setInclude(tables) // 需要生成的表
                                // .setExclude(new String[]{"test"}) // 排除生成的表
                                // 自定义实体父类
                                // .setSuperEntityClass("com.xdong.ripple.CommonEntity")
                                // 自定义实体，公共字段
                                // .setSuperEntityColumns(new String[]{"test_id"})
                                //.setTableFillList(tableFillList)
                                // 自定义 mapper 父类
                                //.setSuperMapperClass("com.baomidou.mybatisplus.mapper.BaseMapper")
                                // 自定义 service 父类
                                //.setSuperServiceClass("com.weidai.mp.support.service.IMPService")
                                // 自定义 service 实现类父类
                                //.setSuperServiceImplClass("com.weidai.mp.support.service.impl.MPServiceImpl")
                                .setSuperFacadeClass("com.weidai.creditcenter.facade.api.base.BaseFacade")
                                .setSuperFacadeImplClass("com.weidai.creditcenter.provider.base.BaseFacadeImpl")
                                // 自定义 controller 父类
                                // .setSuperControllerClass("com.weidai.demo.TestController")
                                // 【实体】是否生成字段常量（默认 false）
                                // public static final String ID = "test_id";
                                // .setEntityColumnConstant(true)
                                // 【实体】是否为构建者模型（默认 false）
                                // public User setName(String name) {this.name = name; return this;}
                                // .setEntityBuilderModel(true)
                                // 【实体】是否为lombok模型（默认 false）<a href="https://projectlombok.org/">document</a>
                                //.setEntityLombokModel(true)
                                // Boolean类型字段是否移除is前缀处理
                                // .setEntityBooleanColumnRemoveIsPrefix(true)
                                // .setRestControllerStyle(true)
                                // .setControllerMappingHyphenStyle(true)
                                // .entityTableFieldAnnotationEnable(true)
                                //.setLogicDeleteFieldName("is_delete")
                                )
                .setPackageInfo(
                        // 包配置
                        new PackageConfig().setModuleName("")
                        .setEntity("model.entity.image")
                        .setMapper("dal.mapper.image")
                        .setXml("dal.mapper.image.xml")
                        .setService("spi.image")
                        .setServiceImpl("service.image")
                        .setFacacde("facade.api.inner.image")
                        .setFacacdeImpl("provider.inner.image")
                        .setParent("com.xdong.datacenter")// 自定义包路径
                               // .setController("controller")// 这里是控制器包名，默认 web
                ).setCfg(
                        // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
                        new InjectionConfig() {
                            @Override
                            public void initMap() {
                                Map<String, Object> map = new HashMap<>();
                                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                                this.setMap(map);
                            }
                        }
                // .setFileOutConfigList(Collections.<FileOutConfig>singletonList(new FileOutConfig(
                // "/templates/mapper.xml" + ((1 == result) ? ".ftl" : ".vm")) {
                // // 自定义输出文件目录
                // @Override
                // public String outputFile(TableInfo tableInfo) {
                // return "/Users/stone/Downloads/mybatis/xml/" + tableInfo.getEntityName() + ".xml";
                // }
                // }))
                //)
                //.setTemplate(
                        // 关闭默认 xml 生成，调整生成 至 根目录
                 //       new TemplateConfig().setXml("/templates/mapper.xml.vm")
        // 自定义模板配置，模板可以参考源码 /mybatis-plus/src/main/resources/template 使用 copy
        // 至您项目 src/main/resources/template 目录下，模板名称也可自定义如下配置：
        // .setController("...");
        // .setEntity("...");
        // .setMapper("...");
        // .setXml("...");
        // .setService("...");
        // .setServiceImpl("...");
        );
        // 执行生成
        
        mpg.execute();

        // 打印注入设置，这里演示模板里面怎么获取注入内容【可无】
        System.err.println(mpg.getCfg().getMap().get("abc"));
    }

}
