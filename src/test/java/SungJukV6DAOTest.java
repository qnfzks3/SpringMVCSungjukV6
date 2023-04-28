import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import qnfzks3.spring4.sungjukv6.dao.SungJukV4DAO;
import qnfzks3.spring4.sungjukv6.model.SungJukVO;
import qnfzks3.spring4.sungjukv6.service.SungJukV6Service;
import qnfzks3.spring4.sungjukv6.service.SungJukV6ServiceImpl;

import java.util.List;

import static junit.framework.TestCase.*;


/*
단위unit 테스트
        하나의 모듈을 기준으로 독립적으로 진행되는 가장 작은 단위의 테스트
        모듈은 애플리케이션에서 작동하는 하나의 기능 또는 메소드를 의미
        애플리케이션을 구성하는 하나의 기능이 올바르게 동작하는지를 독립적으로 테스트하는 것
        "어떤 기능이 실행되면 어떤 결과가 나온다" 수준으로 테스트 함
        테스트 결과 확인은 assertXxx 메서드를 이용함
        =>1. 값을 확인할 때 사용  , ~equals, ~notequals, ~ null, notnull, ~ true , ~false 
          즉, 같다 ,같지않다. 참이다, 거짓이다 null이다 null이 아니다 판별함 - 이상 없으면 아무것도 출력안됨 -이 방식으로 테스트를 할 수 있다.

*/


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ={"classpath:root-context.xml","classpath:servlet-context.xml "}) //설정파일들을test/resource 혹은 메인 자바 파일안에 두면 쓰임
public class SungJukV6DAOTest {



    @Autowired
    SungJukV4DAO sjdao;  //jdbc 값이 자동으로 들어옴



    @Test       //테스트 슈트
    public void test1(){
        System.out.println(">> Test 1 시작<<");
        List<SungJukVO> sjs =sjdao.selectSungJuk();    //오류가 날수도 있다.
        //System.out.println(sjs);

        assertNotNull(sjs);  // 조건이 맞으면 아무것도 출력안함, 안맞으면 오류 출력  assertNotNull
                            //조건이 맞으면 오류 출력함 , 안맞으면 아무것도 출력안함 assertNull
    }


    @Test       //테스트 슈트
    public void test2(){
        System.out.println(">> Test 2 시작<<");
        int sjno=4; //4번 학생에 대한 내용
        SungJukVO sj=sjdao.selectOneSungJuk(sjno);
        //System.out.println(sj);
        assertNotNull(sj);
    }

    @Test       //테스트 슈트
    public void test3(){
        System.out.println(">> Test 3 시작<<");

        SungJukVO sj =new SungJukVO("a",99,88,75);
        sj.setTot(297); sj.setAvg(99.0);sj.setGrd('수');
        int cnt =sjdao.insertSungJuk(sj);
        //System.out.println(cnt);
        assertEquals(1,cnt); //cnt 값이 1 이야?
    }

    @Test //테스트 슈트
    public void test4(){
        System.out.println(">>Test 시작<<");

        SungJukVO sj =new SungJukVO(null, 11,22,33);
        sj.setSjno(9); sj.setTot(0);
        sj.setAvg(0.0);
        sj.setGrd('ㅋ');


        assertEquals(1,sjdao.updateSungJuk(sj));
    }




}
