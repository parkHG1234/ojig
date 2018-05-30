package test.ojig.Uitility;

/**
 * Created by 박효근 on 2018-05-28.
 */

public class convertHangul {
    public String convertHangul(String money){
        String[] han1 = {"","1","2","3","4","5","6","7","8","9"};
        String[] han2 = {"","십","백","천"}; String[] han3 = {"","만","억","조","경"};
        StringBuffer result = new StringBuffer(); int len = money.length();
        for(int i=len-1; i>=0; i--){
            result.append(han1[Integer.parseInt(money.substring(len-i-1, len-i))]);
            if(Integer.parseInt(money.substring(len-i-1, len-i)) > 0) result.append(han2[i%4]);
            if(i%4 == 0) result.append(han3[i/4]); } return result.toString();
    }
}
