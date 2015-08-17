package com.lingcaibao.plugin.page;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.binding.MapperMethod.ParamMap;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;



/**
 * Mybatis - 通用分页拦截器
 *
 * @author liuzh/abel533/isea533
 * @version 3.3.0
 *          项目地址 : http://git.oschina.net/free/Mybatis_PageHelper
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Intercepts(@Signature(type = Executor.class, method = "query", args = {
		MappedStatement.class, Object.class, RowBounds.class,
		ResultHandler.class }))
public class PageHelper implements Interceptor {
    private static final ThreadLocal<Page> LOCAL_PAGE = new ThreadLocal<Page>();
    //sql工具类
    private PluginSqlUtil SQLUTIL;
    //RowBounds参数offset作为PageNum使用 - 默认不使用
    private boolean offsetAsPageNum = false;
    //RowBounds是否进行count查询 - 默认不查询
    private boolean rowBoundsWithCount = false;
    //当设置为true的时候，如果pagesize设置为0（或RowBounds的limit=0），就不执行分页
    private boolean pageSizeZero = false;
    //分页合理化
    private boolean reasonable = false;

    /**
     * 开始分页
     *
     * @param pageNum  页码
     * @param pageSize 每页显示数量
     */
    public static void startPage(int pageNum, int pageSize) {
        startPage(pageNum, pageSize, true);
    }

    /**
     * 开始分页
     *
     * @param pageNum  页码
     * @param pageSize 每页显示数量
     * @param count    是否进行count查询
     */
    public static void startPage(int pageNum, int pageSize, boolean count) {
        LOCAL_PAGE.set(new Page(pageNum, pageSize, count));
    }

    /**
     * 获取分页参数
     *
     * @param rowBounds RowBounds参数
     * @return 返回Page对象
     */
    private Page getPage(RowBounds rowBounds) {
        Page page = LOCAL_PAGE.get();
        //移除本地变量
        LOCAL_PAGE.remove();
        if (page == null) {
            if (offsetAsPageNum) {
                page = new Page(rowBounds.getOffset(), rowBounds.getLimit(), rowBoundsWithCount);
            } else {
                page = new Page(rowBounds, rowBoundsWithCount);
            }
        }
        //分页合理化
        page.setReasonable(reasonable);
        return page;
    }

    /**
     * Mybatis拦截器方法
     *
     * @param invocation 拦截器入参
     * @return 返回执行结果
     * @throws Throwable 抛出异常
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        final Object[] args = invocation.getArgs();
       
        
        
        // 查找方法参数中的 分页请求对象
        Page pageRequest = this.findPageableObject(args[1]);
        
        if (LOCAL_PAGE.get() == null && pageRequest == null) {
            return invocation.proceed();
        } else {
            //获取原始的ms
            MappedStatement ms = (MappedStatement) args[0];
            //忽略RowBounds-否则会进行Mybatis自带的内存分页
            args[2] = RowBounds.DEFAULT;
			//分页信息
            Page page = pageRequest;
            //pageSizeZero的判断
            if (pageSizeZero && page.getPageSize() == 0) {
                //执行正常（不分页）查询
                Object result = invocation.proceed();
                //得到处理结果
                page.addAll((List) result);
                //相当于查询第一页
                page.setPageNum(1);
                //这种情况相当于pageSize=total
                page.setPageSize(page.size());
                //仍然要设置total
                page.setTotal(page.size());
                //返回结果仍然为Page类型 - 便于后面对接收类型的统一处理
                return page;
            }
            SqlSource sqlSource = ((MappedStatement) args[0]).getSqlSource();
            //简单的通过total的值来判断是否进行count查询
            if (page.isCount()) {
                //将参数中的MappedStatement替换为新的qs
                SQLUTIL.processCountMappedStatement(ms, sqlSource, args);
                //查询总数
                Object result = invocation.proceed();
                //设置总数
                page.setTotal((Integer) ((List) result).get(0));
                if (page.getTotal() == 0) {
                    return page;
                }
            }
            //pageSize>0的时候执行分页查询，pageSize<=0的时候不执行相当于可能只返回了一个count
            if (page.getPageSize() > 0 && page.getPageNum() > 0)
            {
                //将参数中的MappedStatement替换为新的qs
                SQLUTIL.processPageMappedStatement(ms, sqlSource, page, args);
                //执行分页查询
                Object result = invocation.proceed();
                //得到处理结果
                page.addAll((List) result);
            }
            
            //返回结果
            return page;
        }
    }

        
        /**
         * 在方法参数中查找 分页请求对象
         *
         * @param params Mapper接口方法中的参数对象
         * @return
         */
        private Page findPageableObject(Object params) {

            if (params == null) {
                return null;
            }

            if (Page.class.isAssignableFrom(params.getClass())) {
                return (Page) params;
            } else if (params instanceof ParamMap) {
                ParamMap<Object> paramMap = (ParamMap<Object>) params;
                for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
                    Object paramValue = entry.getValue();
                    if (null != paramValue && Page.class.isAssignableFrom(paramValue.getClass())) {
                        return (Page) paramValue;
                    }
                }
            }

            return null;
        }    
        
    /**
     * 只拦截Executor
     *
     * @param target
     * @return
     */
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    /**
     * 设置属性值
     *
     * @param p 属性值
     */
    public void setProperties(Properties p) {
        //数据库方言
        String dialect = p.getProperty("dialect");
        SQLUTIL = new PluginSqlUtil(dialect);
        //offset作为PageNum使用
        String offsetAsPageNum = p.getProperty("offsetAsPageNum");
        this.offsetAsPageNum = Boolean.parseBoolean(offsetAsPageNum);
        //RowBounds方式是否做count查询
        String rowBoundsWithCount = p.getProperty("rowBoundsWithCount");
        this.rowBoundsWithCount = Boolean.parseBoolean(rowBoundsWithCount);
        //当设置为true的时候，如果pagesize设置为0（或RowBounds的limit=0），就不执行分页
        String pageSizeZero = p.getProperty("pageSizeZero");
        this.pageSizeZero = Boolean.parseBoolean(pageSizeZero);
        //分页合理化，true开启，如果分页参数不合理会自动修正。默认false不启用
        String reasonable = p.getProperty("reasonable");
        this.reasonable = Boolean.parseBoolean(reasonable);
    }
    
    public static void main(String[] args) {
		System.out.println(Boolean.parseBoolean("true"));
	}
}
