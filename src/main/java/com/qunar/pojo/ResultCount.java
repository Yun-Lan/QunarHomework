package com.qunar.pojo;

/**
 * @author: lymtics
 * @date: 2022/7/7 17:04
 * @description:
 */
public class ResultCount {
    private final int allCharCount;
    private final int chineseCount;
    private final int letterCount;
    private final int charCount;

    public ResultCount(int allCharCount, int chineseCount, int letterCount, int charCount) {
        this.allCharCount = allCharCount;
        this.chineseCount = chineseCount;
        this.letterCount = letterCount;
        this.charCount = charCount;
    }

    @Override
    public String toString() {
        return "字符总数量为[" + allCharCount + "], 中文总数量为[" + chineseCount + "], 英文字符数量为[" + letterCount
                + "], 标点符号数量为[" + charCount + "]";
    }
}
