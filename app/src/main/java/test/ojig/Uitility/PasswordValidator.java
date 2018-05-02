package test.ojig.Uitility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 박효근 on 2018-02-13.
 */

public class PasswordValidator {
    public String PasswordValidator(String passwd){
        String returnValue = "succed";
//.*[!,@,#,$,%,^,&amp;,*,?,_,~])|([!,@,#,$,%,^,&amp;,*,?,_,~].*[a-zA-Z0-9]
        Pattern p = Pattern.compile("([a-zA-Z].*[0-9])|([0-9].*[a-zA-Z])");
        Matcher m = p.matcher(passwd);

        Pattern p2 = Pattern.compile("(\\w)\\1\\1\\1");
        Matcher m2 = p2.matcher(passwd);

        Pattern p3 = Pattern.compile("([\\{\\}\\[\\]\\/?.,;:|\\)*~`!^\\-_+&lt;&gt;@\\#$%&amp;\\\\\\=\\(\\'\\\"])\\1\\1\\1");
        Matcher m3 = p3.matcher(passwd);

        if(spaceCheck(passwd)){	//패스워드 공백 문자열 체크
            returnValue = "공백을 제거해주세요";
        }else if(passwd.length() < 6 || passwd.length() > 16){	//자릿수 검증
            returnValue = "6자리 이상 입력하세요";
        }else if(!m.find()) {    //정규식 이용한 패턴 체크
            returnValue = "영문, 숫자를 조합하세요";
//        }else if(m2.find() || m3.find()){	// 동일 문자 4번 입력 시 패턴 체크
//            returnValue = "비밀번호에 동일문자를 4번 이상 사용할 수 없습니다.";
        }

        return returnValue;
    }
    public boolean spaceCheck(String spaceCheck){
        for(int i = 0 ; i < spaceCheck.length() ; i++) {
            if(spaceCheck.charAt(i) == ' ')
                return true;
        }

        return false;
    }
}
