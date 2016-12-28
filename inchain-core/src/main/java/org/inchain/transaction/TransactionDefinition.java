package org.inchain.transaction;

import java.util.HashMap;
import java.util.Map;

/**
 * 交易定义
 * @author ln
 *
 */
public class TransactionDefinition {


	public static final long VERSION = 1;
	
	public static final int TYPE_COINBASE = 1;			//coinbase交易
	public static final int TYPE_PAY = 2;				//普通支付交易
	public static final int TYPE_REG_CONSENSUS = 3;		//注册成为共识节点
	public static final int TYPE_REM_CONSENSUS = 4;		//注销共识节点
	public static final int TYPE_REGISTER = 11;			//帐户注册
	public static final int TYPE_CHANGEPWD = 12;		//修改密码
	
	public static final int TYPE_INIT_CREDIT = 49;		//初始化信用，只在创世块里有用
	
	
	public static final Map<Integer, String> TRANSACTION_RELATION = new HashMap<Integer, String>();
	
	public static final Map<Integer, String> PROCESS_RELATION = new HashMap<Integer, String>();
	
	static {
		TRANSACTION_RELATION.put(TYPE_COINBASE, "org.inchain.transaction.Transaction");
		TRANSACTION_RELATION.put(TYPE_PAY, "org.inchain.transaction.Transaction");
		TRANSACTION_RELATION.put(TYPE_REG_CONSENSUS, "org.inchain.transaction.RegConsensusTransaction");
		TRANSACTION_RELATION.put(TYPE_REM_CONSENSUS, "");
		TRANSACTION_RELATION.put(TYPE_REGISTER, "org.inchain.transaction.RegisterTransaction");
		TRANSACTION_RELATION.put(TYPE_CHANGEPWD, "");
		TRANSACTION_RELATION.put(TYPE_INIT_CREDIT, "org.inchain.transaction.CreditTransaction");
	}
	
}