package qnfzks3.spring4.sungjukv6.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import qnfzks3.spring4.sungjukv6.model.SungJukVO;
import qnfzks3.spring4.sungjukv6.service.SungJukV6Service;




@Controller
public class SungjukController {
    private SungJukV6Service sjsrv;

    @Autowired   //@Autowired는 해당 클래스가 의존하고 있는 다른 객체를 찾아서 자동으로 주입
    public SungjukController(SungJukV6Service sjsrv) {
        this.sjsrv = sjsrv;
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET) //@RequestMapping은 value로 url패턴을 지정하고 http에 get이나 post로 지정한다.-
    public String list(Model model){                                       //@RequestMapping는  HTTP 요청을 처리하고, 결과를 응답합니다.

        System.out.println(sjsrv.readSungJuk());

        model.addAttribute("sjs",sjsrv.readSungJuk());//sungjuklist.jsp에 성정 조회결과 데이터를  sjs에 넣는다.
        return "sungjuklist";                  // 이 데이터를 sjs에 넣는 걸 sungjuklist 로 보낸다. - return으로 sungjuklist에서도 사용가능하게 함
                                                //추천 책 읽어봐도된다.
    }

}
