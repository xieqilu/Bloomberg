check一系列byte是不是合法的utf8编码。utf8的定义大家可以去网上搜一下，具体我记不太清了。这轮主要是看代码是否简介清晰，无奈写出了一个bug。情况是这样，有一种byte组合是永远不会出现在utf8的编码里的，我忘记考虑了这种情况，结果就是跪在这个上面
Write a function to validate whether the input is valid UTF-8. Input will be string or byte array, output should be true or false.
UTF-8是一种变长的编码方式。它可以使用1~4个字节表示一个符号，根据不同的符号而变化字节长度。UTF-8的编码规则很简单，只有二条：
1）对于单字节的符号，字节的第一位设为0，后面7位为这个符号的unicode码。因此对于英语字母，UTF-8编码和ASCII码是相同的。
2）对于n字节的符号（n>1），第一个字节的前n位都设为1，第n+1位设为0，后面字节的前两位一律设为10。剩下的没有提及的二进制位，全部为这个符号的unicode码。

比如：
0xxxxxxx是一个合法的单字节UTF8编码。
110xxxxx 10xxxxxx是一个合法的2字节UTF8编码。
1110xxxx 10xxxxxx 10xxxxxx是一个合法的3字节UTF8编码。
11110xxx 10xxxxxx 10xxxxxx 10xxxxxx是一个合法的4字节UTF8编码。

Solution：

bool valid_utf8(const vector<unsigned char>& data) {
    int size = 0;
    for(auto c : data) {
        if(size == 0) {
	//size is the number of rest bytes for this specific
	//possible UTF-8 sequence
            if((c >> 5) == 0b110) size = 1; //0b means binary number
            else if((c >> 4) == 0b1110) size = 2;
            else if((c >> 3) == 0b11110) size = 3;
            else if(c >> 7) return false;
        } else {
            if((c >> 6) != 0b10) return false;
            --size;
        }
    }
    return size == 0;
}
