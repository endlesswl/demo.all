package com.lingcaibao.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.lingcaibao.plugin.page.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lingcaibao.conf.Conf;
import com.lingcaibao.entity.Bill;
import com.lingcaibao.entity.User;
import com.lingcaibao.entity.Withdraw;
import com.lingcaibao.exception.LotteryException;
import com.lingcaibao.repository.WithdrawDao;
import com.lingcaibao.service.util.SaltUtils;
import com.lingcaibao.statuscode.BillChannelEnum;
import com.lingcaibao.statuscode.BillIndecrEnum;
import com.lingcaibao.statuscode.BillMethodEnum;
import com.lingcaibao.statuscode.BillReturnCodeEnum;
import com.lingcaibao.statuscode.TrueFalseEnum;
import com.lingcaibao.statuscode.WithdrawStatusEnum;
import com.lingcaibao.util.MathUtil;
import com.palm.commom.uitl.DateFormatUtil;
import com.palm.commom.uitl.UUIDUtils;
/**
 * <p>标题：用户提现服务 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年4月14日 下午7:54:45</p>
 * <p>类全名：com.lingcaibao.service.WithdrawService</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
@Service
@Transactional
public class WithdrawService
{
	final static private String	AUDIT_AMOUNT	= Conf.get("lingcai.audit_amount");
	private static Logger		logger			= LoggerFactory.getLogger(WithdrawService.class);
	@Autowired
	private WithdrawDao			withdrawDao;
	@Autowired
	private BillService			billService;
	@Autowired
	private UserService			userService;

	/**
	 * 分页查询
	 * @param searchParams
	 * @param pageable
	 * @return
	 */
	public Page<Map<String,Object>> searchPage(Map<String,Object> searchParams, Page<Map<String,Object>> pageable)
	{
		return withdrawDao.searchPage(searchParams, pageable);
	}

	/**
	 * 不分页查询
	 * @param searchParas
	 * @return
	 */
	public List<Map<String,Object>> search(Map<String,Object> searchParas)
	{
		return withdrawDao.search(searchParas);
	}
	
	/**
	 * 不分页查询
	 * @param searchParas
	 * @return
	 */
	public List<Withdraw> getUntreatedList(Map<String,Object> searchParas)
	{
		return withdrawDao.getUntreatedList(searchParas);
	}

	public Withdraw get(Long id)
	{
		return withdrawDao.get(id);
	}

	public void insert(Withdraw withdraw)
	{
		withdraw.setCreatetime(DateFormatUtil.toDate(DateFormatUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss"));
		withdrawDao.insert(withdraw);
	}

	public void update(Withdraw withdraw)
	{
		withdrawDao.update(withdraw);
	}

	public void delete(Long id)
	{
		withdrawDao.delete(id);
	}

	/**
	 * 用户提现
	 * @param userid
	 * @param bankid
	 * @param amount
	 * @param ip
	 * @throws Exception 
	 */
	public void doWithDraw(Long userid, Long bankid, BigDecimal amount, String ip) throws Exception
	{
		try
		{
			// 用户账户扣费
			User user = userService.get(userid);
			userService.doPay(userid, amount);
			// 生成提现记录
			Withdraw withdraw = new Withdraw();
			withdraw.setOrderid(UUIDUtils.getSerialID());
			withdraw.setUserid(userid);
			withdraw.setBankid(bankid);
			withdraw.setAmount(amount);
			withdraw.setOperator(Long.valueOf(Conf.get("lingcai.userid")));
			withdraw.setStatus(amount.compareTo(MathUtil.toDecimal(AUDIT_AMOUNT)) >= 0 ? WithdrawStatusEnum.APPLICATIONSSUBMITTED.ordinal() : WithdrawStatusEnum.APPROVE.ordinal());
			//withdraw.setStatus(WithdrawStatusEnum.APPLICATIONSSUBMITTED.ordinal());
			withdraw.setSource("网利宝跳转到零彩宝WEB");
			withdraw.setRemark("网利宝跳转到零彩宝用户提现");
			withdraw.setDeleteFlag(0);
			withdraw.setPrebalance(user.getAmount());
			withdraw.setAfterbalance(user.getAmount().subtract(amount));
			insert(withdraw);
			// 生成流水记录
			Bill bill = new Bill();
			bill.setAmount(withdraw.getAmount());
			bill.setBillchannel(BillChannelEnum.WITHDRAW.ordinal());//支付
			bill.setBillret(BillReturnCodeEnum.NORMAL.ordinal());//待处理
			bill.setConfirmed(1);
			bill.setExpiretime(null);// 订单过期时间
			bill.setIndecr(BillIndecrEnum.SUBTRACT.ordinal());
			bill.setInformation("网利宝跳转到零彩宝用户提现");
			bill.setLocked(TrueFalseEnum.FALSE.ordinal());
			bill.setModifytime(null);
			bill.setBilltime(new Date().getTime());
			bill.setPackets("");
			bill.setSerialno(UUIDUtils.getSerialID());
			bill.setMethod(BillMethodEnum.ALIPAY.ordinal());
			bill.setIpaddr(ip);
			bill.setOrderid(withdraw.getOrderid());
			bill.setSubject("网利宝跳转到零彩宝账户提现");
			bill.setUserid(user.getId());
			bill.setPrebalance(user.getAmount());
			bill.setAfterbalance(user.getAmount().subtract(withdraw.getAmount()));
			SaltUtils.buildBillSalt(bill);
			billService.insert(bill);
		} catch (Exception ex)
		{
			ex.printStackTrace();
			logger.error("------------- do deposit err -------------:{}", ex.getMessage());
			throw (ex instanceof LotteryException ? ex : new RuntimeException(ex.getMessage()));
		}
	}

	/**
	 * 提现详情
	 * @param id
	 * @return
	 */
	public Map<String,Object> withdrawDetail(Long id)
	{
		return withdrawDao.withdrawDetail(id);
	}
}
