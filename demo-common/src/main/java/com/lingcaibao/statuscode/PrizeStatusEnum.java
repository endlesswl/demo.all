package com.lingcaibao.statuscode;

public enum PrizeStatusEnum {
	NOLOTTERY("未开奖"),
	LOTTERYFAILD("未中奖"),
	LOTTERYSUCCESS("中奖 "),
	SENTPRIZE("已派奖")
	;
    private String name;

    private PrizeStatusEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    /**
     * 根据编码获取中文名称
     * @param value
     * @return
     */
    public static String getName(int value) {
        for (PrizeStatusEnum prizeStatusEnum : PrizeStatusEnum.values()) {
            if (prizeStatusEnum.ordinal() == value) {
                return prizeStatusEnum.name;
            }
        }
        return "";
    }
}
