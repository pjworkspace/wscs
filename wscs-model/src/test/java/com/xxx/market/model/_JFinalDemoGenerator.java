package com.xxx.market.model;

import javax.sql.DataSource;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.dialect.OracleDialect;
import com.jfinal.plugin.activerecord.dialect.SqlServerDialect;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.druid.DruidPlugin;

/**
 * 在数据库表有任何变动时，运行一下 main 方法，极速响应变化进行代码重构
 */
public class _JFinalDemoGenerator {

    public static DataSource getDataSource() {
        PropKit.use("db.properties");
        DruidPlugin druidPlugin = new DruidPlugin(PropKit.get("jdbc.url"), PropKit.get("jdbc.username"), PropKit.get("jdbc.password"), PropKit.get("jdbc.driverClassName"));
//        C3p0Plugin c3p0Plugin = new C3p0Plugin(PropKit.get("jdbc.url"), PropKit.get("jdbc.username"), PropKit.get("jdbc.password"), PropKit.get("jdbc.driverClassName"));
//        c3p0Plugin.start();
//        return c3p0Plugin.getDataSource();
        druidPlugin.start();
        System.out.println(druidPlugin.getDataSource()+"----------------");
        return druidPlugin.getDataSource();
    }

    public static void main(String[] args) {
        // base model 所使用的包名
        String baseModelPackageName = "com.xxx.market.model.base.sys";
        // base model 文件保存路径
        String baseModelOutputDir = PathKit.getWebRootPath() + "/../src/com/xxx/market/model/base/sys";

        // model 所使用的包名 (MappingKit 默认使用的包名)
        String modelPackageName = "com.xxx.market.model.base.sys";
        // model 文件保存路径 (MappingKit 与 DataDictionary 文件默认保存路径)
        String modelOutputDir = baseModelOutputDir + "/..";

        // 创建生成器
        Generator gernerator = new Generator(getDataSource(), baseModelPackageName, baseModelOutputDir, modelPackageName, modelOutputDir);
        //System.out.println("数据库类型:"+PropKit.get("dataSource.type"));
        String dataType=PropKit.get("jdbc.dbType");

        // 添加不需要生成的表名
        //gernerator.addExcludedTable("adv");
        // 设置是否在 Model 中生成 dao 对象
        gernerator.setGenerateDaoInModel(true);
        // 设置是否生成字典文件
        gernerator.setGenerateDataDictionary(false);
        // 设置需要被移除的表名前缀用于生成modelName。例如表名 "osc_user"，移除前缀 "osc_"后生成的model名为 "User"而非 OscUser
        //gernerator.setRemovedTableNamePrefixes("t_");

        ModelBulid modelBulid=new ModelBulid(getDataSource(), "pt_menu",dataType);
        gernerator.setMetaBuilder(modelBulid);
        MappingKitBulid mappingKitBulid=new MappingKitBulid(modelPackageName, modelOutputDir);
        gernerator.setMappingKitGenerator(mappingKitBulid);
        // 生成
        gernerator.generate();
    }
}