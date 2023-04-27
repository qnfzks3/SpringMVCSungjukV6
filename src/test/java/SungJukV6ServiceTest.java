import org.junit.Before;
import org.junit.Test;
import qnfzks3.spring4.sungjukv6.model.SungJukVO;
import qnfzks3.spring4.sungjukv6.service.SungJukV6Service;
import qnfzks3.spring4.sungjukv6.service.SungJukV6ServiceImpl;

import static junit.framework.TestCase.assertEquals;

public class SungJukV6ServiceTest { //이렇게 부분 부분 테스트를 진행할 수 있다.

    SungJukV6Service sjsrv;
    @Before  //테스트 전에 초기 작업 할 때 우선적으로 밑 작업할 때 사용함
    public void setup(){
        sjsrv= new SungJukV6ServiceImpl(null);  //sjsrv안에 있는 컴퓨터 성적만 확인할것이기 때문에
    }


    @Test
    public void test1(){
        SungJukVO sj = new SungJukVO("test1",99,99,99);

        sjsrv.computeSungJuk(sj);

        assertEquals(297,sj.getTot());
        assertEquals(99,sj.getAvg(),0.0);
        assertEquals('수',sj.getGrd());


    }

}
